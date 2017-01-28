package mx.mobilestudio.promohunters;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCrearCuenta;
    private FirebaseAuth firebaseAuth;//Crear usuarios y Autentificarnos
    private FirebaseDatabase firebaseDatabase; // Una referencia a la base de datos
    private DatabaseReference generalDatabaseReference; // Una referencia para modificar cualquier Objeto

    private TextView email;
    private TextView password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonCrearCuenta = (Button) findViewById(R.id.button3);

        buttonCrearCuenta.setOnClickListener(this);


        firebaseAuth =  FirebaseAuth.getInstance();// Inicializamos Firebase Auth
        firebaseDatabase = FirebaseDatabase.getInstance(); //Obtenemos la referencia a la DB
        generalDatabaseReference = firebaseDatabase.getReference();  //Obtenemos una referencia general


        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button2);

        login.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button2:
                singIn(email.getText().toString(),password.getText().toString());
                break;
            case R.id.button3:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;

        }



    }

    public void singIn(String email, String password){ // Para hacer log in

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(this.getClass().getName(), "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(this.getClass().getName(), "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Log in Incorrecto",
                                    Toast.LENGTH_SHORT).show();
                        }else{

                            Intent i=new Intent(LoginActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("flag_success_login",true);
                            startActivity(i);
                        }



                        // ...
                    }
                });

    }


}
