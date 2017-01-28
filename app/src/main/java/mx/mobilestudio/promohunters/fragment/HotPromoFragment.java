package mx.mobilestudio.promohunters.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

import mx.mobilestudio.promohunters.R;
import mx.mobilestudio.promohunters.model.OnFragmentInteractionListener;
import mx.mobilestudio.promohunters.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HotPromoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotPromoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;//Crear usuarios y Autentificarnos
    private FirebaseDatabase firebaseDatabase; // Una referencia a la base de datos
    private  DatabaseReference generalDatabaseReference; // Una referencia para modificar cualquier Objeto
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HotPromoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotPromoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotPromoFragment newInstance(String param1, String param2) {
        HotPromoFragment fragment = new HotPromoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hotPromoLayout = inflater.inflate(R.layout.fragment_hot_promo, container, false);
        recyclerView = (RecyclerView) hotPromoLayout.findViewById(R.id.hot_promo_list);
        firebaseAuth =  FirebaseAuth.getInstance();// Inicializamos Firebase Auth
        firebaseDatabase = FirebaseDatabase.getInstance(); //Obtenemos la referencia a la DB
        generalDatabaseReference = firebaseDatabase.getReference();  //Obtenemos una referencia general

        //Validamos si el usuario existe
        if (firebaseAuth.getCurrentUser() == null) {
        }

        return hotPromoLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (firebaseAuth.getCurrentUser() != null) {
          //  onAuthSuccess(firebaseAuth.getCurrentUser());
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    }
