package com.moviloft.motoapp.Motoclasificados;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

public class Clasificado extends ActionBarActivity {

    TextView tvValor, tvMarca, tvModelo, tvAño,tvCilindrada,tvKilometraje,tvDescripcion, tvNombre,tvUserNombre,tvLugar,tvCorreo;

    NetworkImageView ivAvatar, ivImagen;

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
        tvUserNombre = ( TextView)findViewById(R.id.tvUserNombre);
        tvLugar = ( TextView)findViewById(R.id.tvLugar);
        tvCorreo = (TextView)findViewById(R.id.tvCorreo);
        ivAvatar = (NetworkImageView)findViewById(R.id.ivAvatar);
        ivImagen = (NetworkImageView)findViewById(R.id.ivImagen);


        Bundle bundle = getIntent().getBundleExtra("extras");

        String nombre = bundle.getString("clasificado_nombre");
        String valor = bundle.getString("clasificado_valor");
        String marca = bundle.getString("clasificado_marca");
        String modelo = bundle.getString("clasificado_modelo");
        String año = bundle.getString("clasificado_ano");
        String cilindrada = bundle.getString("clasificado_cilindrada");
        String descripcion = bundle.getString("clasificado_descripcion");
        String kilometraje = bundle.getString("clasificado_kilometraje");
        String user_nombre = bundle.getString("nombre");
        String user_apellido = bundle.getString("apellido");
        String user_correo = bundle.getString("correo");
        String ciudad = bundle.getString("ciudad");

        String avatar = bundle.getString("avatar");
        String imagen = bundle.getString("clasificadoImagen");

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        ivAvatar.setImageUrl("http://motoapp.com.co"+avatar,imageLoader);
        ivImagen.setImageUrl("http://motoapp.com.co"+imagen,imageLoader);

        tvValor.setText(valor);
        tvMarca.setText(marca);
        tvModelo.setText(modelo);
        tvAño.setText(año);
        tvCilindrada.setText(cilindrada);
        tvKilometraje.setText(kilometraje);
        tvDescripcion.setText(descripcion);
        tvNombre.setText(nombre);
        tvUserNombre.setText(user_nombre+" "+user_apellido);
        tvCorreo.setText(user_correo);
        tvLugar.setText(ciudad);


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
