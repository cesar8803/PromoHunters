package mx.mobilestudio.promohunters;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import mx.mobilestudio.promohunters.dialog.OptionsImageDialog;
import mx.mobilestudio.promohunters.model.NewPromo;

public class CreatePromoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title;
    private Spinner categoria;
    private Spinner tienda;
    private TextView descripcion;
    private TextView precio;
    private Switch link_switch;
    private  TextView link;

    private Button boton_send;
    private ImageButton image_promo_button;

    private OptionsImageDialog   dialog;


    private FirebaseAuth firebaseAuth;//Crear usuarios y Autentificarnos
    private FirebaseDatabase firebaseDatabase; // Una referencia a la base de datos
    private DatabaseReference generalDatabaseReference; // Una referencia para modificar cualquier Objeto



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_promo);


        title = (TextView) findViewById(R.id.title_item);

        categoria = (Spinner) findViewById(R.id.spinnerCategorias);

        tienda = (Spinner) findViewById(R.id.spinnerTiendas);

        descripcion = (TextView) findViewById(R.id.description_item);

        precio = (TextView) findViewById(R.id.precio_item);

        link_switch = (Switch) findViewById(R.id.switch_link);

        link = (TextView) findViewById(R.id.link_item);

        boton_send = (Button) findViewById(R.id.button_enviar);

        image_promo_button = (ImageButton) findViewById(R.id.imageButton);


        boton_send.setOnClickListener(this);
        image_promo_button.setOnClickListener(this);

        firebaseAuth =  FirebaseAuth.getInstance();// Inicializamos Firebase Auth
        firebaseDatabase = FirebaseDatabase.getInstance(); //Obtenemos la referencia a la DB
        generalDatabaseReference = firebaseDatabase.getReference();  //Obtenemos una referencia general

    }

    @Override
    public void onClick(View v) {
//    public NewPromo(String categoria, String descripcion, String link, float precio, String title) {

        switch (v.getId()){
            case R.id.button_enviar:
                createNewPromo();
                break;
            case R.id.imageButton:
                showOptionsDialog();
                break;
        }

    }

    public void createNewPromo(){
        NewPromo newPromo = new NewPromo(categoria.getSelectedItem().toString(),
                descripcion.getText().toString(),
                link.getText().toString(),
                new Float(precio.getText().toString()),
                title.getText().toString(),
                tienda.getSelectedItem().toString());

        generalDatabaseReference.child("promotion").child( title.getText().toString()).setValue(newPromo);

    }

    private void showOptionsDialog(){
        FragmentManager manager = getSupportFragmentManager();


        dialog = new OptionsImageDialog();

        dialog.show(manager,"");


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,"ola!!! regresamos de un viaje a la camara..",Toast.LENGTH_LONG).show();

            galleryAddPic();
    }


    private void galleryAddPic(){

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File file = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

    }


}
