package com.moviloft.motoapp.Motoclasificados;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.moviloft.motoapp.Menu.LoginActivity;
import com.moviloft.motoapp.Menu.MainActivity;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.json.JSONArray;

public class Motoclasificados extends ActionBarActivity implements View.OnClickListener {

    TextView bt_ver_motoclasificados, bt_publicar_motoclasificado;

    SharedPreferences sharedpreferences;

    public String classifieds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motoclasificados);



        classifieds = "";

        //Relacion de elementos con layout
        bt_ver_motoclasificados = (TextView)findViewById(R.id.bt_ver_motoclasificados);
        bt_publicar_motoclasificado = (TextView)findViewById(R.id.bt_publicar_motoclasificado);


        //Asignar onClick a elementos
        bt_ver_motoclasificados.setOnClickListener(this);
        bt_publicar_motoclasificado.setOnClickListener(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new nuevoMotoclasificadoFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId(); //Obtener el id del boton presionado

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (id){

            case R.id.bt_ver_motoclasificados:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,
                                nuevoMotoclasificadoFragment.newInstance())
                        .commit();
                break;

            case R.id.bt_publicar_motoclasificado:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,
                                MotoclasificadosFragment.newInstance(classifieds))
                        .commit();
                break;

        }


    }


    public void downloadClassifieds(){

        String URL = "http://104.131.32.54/clasificados.json";


        JsonArrayRequest req = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("Classified response: ",response.toString());

                        classifieds = response.toString();

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        if (!sharedpreferences.getString("classifieds","").equals("")){
                            editor.remove("classifieds");
                        }

                        editor.putString("classifieds",classifieds);
                        editor.commit();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);


    }



}
