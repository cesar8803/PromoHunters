package mx.mobilestudio.promohunters;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import mx.mobilestudio.promohunters.adapter.MainMenuAdapter;
import mx.mobilestudio.promohunters.fragment.CreatePromoFragment;
import mx.mobilestudio.promohunters.fragment.HotPromoFragment;
import mx.mobilestudio.promohunters.fragment.PopularStoresFragment;
import mx.mobilestudio.promohunters.fragment.PreferencesFragment;
import mx.mobilestudio.promohunters.model.ListMenuElement;
import mx.mobilestudio.promohunters.model.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    //Para el Hamburger menu
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;//Crear usuarios y Autentificarnos
    private Boolean flag_success_login = null;
    private Boolean flag_success_register = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Vamos a leer las variables que estan contenidas en el savedInstanceState

        if( getIntent()!=null){
            flag_success_login = getIntent().getBooleanExtra("flag_success_login",false);
            flag_success_register =  getIntent().getBooleanExtra("flag_success_register",false);


        }





        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        setupToolBar();

        getSupportActionBar().setHomeButtonEnabled(true);

        //Creamos los elementos del ListView del NavigationDrawer
        ListMenuElement[]  arrayMenuElements = new ListMenuElement[4];

        arrayMenuElements[0] = new ListMenuElement(R.drawable.main_menu_fire,"Hot Promos");
        arrayMenuElements[1] = new ListMenuElement(R.drawable.main_menu_new,"Nueva Promo");
        arrayMenuElements[2]  = new ListMenuElement(R.drawable.main_menu_stores,"Tiendas populares");
        arrayMenuElements[3]  = new ListMenuElement(R.drawable.main_menu_config,"Configuración");


        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(arrayMenuElements ,this);


        mDrawerList.setAdapter(mainMenuAdapter);

        mDrawerList.setOnItemClickListener(this);


        //Inicializamos Firebase Auth que nos permite verificar si tenemos sesión
        firebaseAuth =  FirebaseAuth.getInstance();// Inicializamos Firebase Auth

        if(flag_success_login || flag_success_register){
            selectItem(1); // 1 es la posición del fragmento CreatePromoFragment
        }

    }








    public void  setupToolBar(){
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        selectItem(i);

    }

    public void selectItem(int position){

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new HotPromoFragment().newInstance("","");
                break;
            case 1:
                //Validamos si el usuario existe
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(this,LoginActivity.class);

                    startActivity(intent);

                }else{
                    Intent intent = new Intent(this,CreatePromoActivity.class);
                    startActivity(intent);
                }

                break;
            case 2:
               // fragment = new PopularStoresFragment().newInstance("","");
                Intent intent = new Intent(this,LoginActivity.class);

                startActivity(intent);
                break;
            case 3:
                fragment = new PreferencesFragment().newInstance("","");
                break;

        }


        if(fragment != null){
            FragmentManager  fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();

            mDrawerList.setItemChecked(position,true);
            mDrawerList.setSelection(position);

            mDrawerLayout.closeDrawer(mDrawerList);
        }





    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
