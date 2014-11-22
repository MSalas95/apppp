package com.moviloft.motoapp.Motoclasificados;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.moviloft.motoapp.R;

public class Motoclasificados extends ActionBarActivity implements View.OnClickListener {

    TextView bt_ver_motoclasificados, bt_publicar_motoclasificado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motoclasificados);

        //Relacion de elementos con layout
        bt_ver_motoclasificados = (TextView)findViewById(R.id.bt_ver_motoclasificados);
        bt_publicar_motoclasificado = (TextView)findViewById(R.id.bt_publicar_motoclasificado);


        //Asignar onClick a elementos
        bt_ver_motoclasificados.setOnClickListener(this);
        bt_publicar_motoclasificado.setOnClickListener(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new VerMotoclasificadosFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_motoclasificados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId(); //Obtener el id del boton presionado

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (id){

            case R.id.bt_ver_motoclasificados:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,
                                VerMotoclasificadosFragment.newInstance())
                        .commit();
                break;

            case R.id.bt_publicar_motoclasificado:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,
                                PublicarMotoClasificadosFragment.newInstance())
                        .commit();
                break;

        }


    }
}
