package com.moviloft.motoapp.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.moviloft.motoapp.Motoclasificados.Motoclasificados;
import com.moviloft.motoapp.R;


public class MainActivity extends ActionBarActivity {

    Button btClasificados;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        String data = sharedpreferences.getString("userData",null);
        Log.d("SP data: ",data);

        btClasificados = (Button) findViewById(R.id.btClasificados);
        btClasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Motoclasificados.class);
                startActivity(i);
            }
        });

    }


}
