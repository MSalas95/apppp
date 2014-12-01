package com.moviloft.motoapp.Motoclasificados;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.moviloft.motoapp.R;

public class Clasificado extends ActionBarActivity {

    TextView tvValor, tvMarca, tvModelo, tvAño,tvCilindrada,tvKilometraje,tvDescripcion, tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificado);

        tvValor = ( TextView)findViewById(R.id.tvValor);
        tvMarca = ( TextView)findViewById(R.id.tvMarca);
        tvModelo = ( TextView)findViewById(R.id.tvModelo);
        tvAño = ( TextView)findViewById(R.id.tvAño);
        tvCilindrada = ( TextView)findViewById(R.id.tvCilindrada);
        tvKilometraje = ( TextView)findViewById(R.id.tvKilometraje);
        tvDescripcion = ( TextView)findViewById(R.id.tvDescripcion);
        tvNombre = (TextView)findViewById(R.id.tvNombre);


        Bundle bundle = getIntent().getBundleExtra("extras");

        String nombre = bundle.getString("clasificado_nombre");
        String valor = bundle.getString("clasificado_valor");
        String marca = bundle.getString("clasificado_marca");
        String modelo = bundle.getString("clasificado_modelo");
        String año = bundle.getString("clasificado_ano");
        String cilindrada = bundle.getString("clasificado_cilindrada");
        String descripcion = bundle.getString("clasificado_descripcion");
        String kilometraje = bundle.getString("clasificado_kilometraje");


        tvValor.setText(valor);
        tvMarca.setText(marca);
        tvModelo.setText(modelo);
        tvAño.setText(año);
        tvCilindrada.setText(cilindrada);
        tvKilometraje.setText(kilometraje);
        tvDescripcion.setText(descripcion);
        tvNombre.setText(nombre);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clasificado, menu);
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
}
