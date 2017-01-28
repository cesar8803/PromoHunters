package mx.mobilestudio.promohunters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import mx.mobilestudio.promohunters.fragment.HotPromoFragment;
import mx.mobilestudio.promohunters.model.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;//Crear usuarios y Autentificarnos
    private FirebaseDatabase firebaseDatabase; // Una referencia a la base de datos
    private  DatabaseReference generalDatabaseReference; // Una referencia para modificar cualquier Objeto


    //Referencias a elementos de interfaz
    private Button botonCrearCuenta;
    private TextView textViewMail;
    private TextView textViewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth =  FirebaseAuth.getInstance();// Inicializamos Firebase Auth
        firebaseDatabase = FirebaseDatabase.getInstance(); //Obtenemos la referencia a la DB
        generalDatabaseReference = firebaseDatabase.getReference();  //Obtenemos una referencia general


        botonCrearCuenta = (Button) findViewById(R.id.button3);
        textViewMail = (TextView) findViewById(R.id.email);
        textViewPassword = (TextView) findViewById(R.id.password);

        botonCrearCuenta.setOnClickListener(this);


    }
    private void signUp(String mail, String password) { // Registro
        Log.d(HotPromoFragment.class.getName(), "signUp");
        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("signUp", "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign Up Failed"+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    private void onAuthSuccess(FirebaseUser user) {
        String username = null;
        if (user.getEmail().contains("@")) {
            username = user.getEmail().split("@")[0];
        }
        // Write new user


        User mUser = new User(username, user.getEmail());

        generalDatabaseReference.child("users").child(user.getUid()).setValue(mUser);

        DatabaseReference databaseReference = firebaseDatabase.getReference("message");

        databaseReference.setValue("Registro exitoso!!");


        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("flag_success_register",true);
        startActivity(i);

    }

    @Override
    public void onClick(View v) {


        //Validamos que los datos tengan el formato correcto

        if(textViewMail.getText().toString().isEmpty() || !checkEmail(textViewMail.getText().toString())){
            Toast.makeText(this,"Es necesario capturar el campo email, en un formato valido", Toast.LENGTH_LONG).show();
            return;
        }
        if(textViewPassword.getText().toString().isEmpty() || textViewPassword.getText().toString().length()<8){
                Toast.makeText(this,"Es necesario capturar el password, minimo 8 caracteres", Toast.LENGTH_LONG).show();
                return;
        }

        signUp(textViewMail.getText().toString(), textViewPassword.getText().toString());

    }
}
