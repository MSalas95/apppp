package com.moviloft.motoapp.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moviloft.motoapp.Data.Images;
import com.moviloft.motoapp.Data.SelectDialog;
import com.moviloft.motoapp.R;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class activityRegistro extends ActionBarActivity implements View.OnClickListener {

    Button btRespuesta;
    ImageView ivPhotos;
    EditText etNombre, etApellido, etCiudad,etTelefono, etCorreo, etClave, etConfirmarClave, etMarca, etModelo;
    RadioButton rbMoto1;
    CheckBox cbTerminos;
    String imagePath;

    Bitmap rotatedBitmap;



    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ivPhotos = (ImageView)findViewById(R.id.ivAvatar);
        ivPhotos.setOnClickListener(this);

        etNombre = (EditText)findViewById(R.id.etNombre);
        etApellido = (EditText)findViewById(R.id.etApellido);
        etCiudad = (EditText)findViewById(R.id.etCiudad);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        etCorreo = (EditText)findViewById(R.id.etCorreo);
        etClave = (EditText)findViewById(R.id.etClave);
        etConfirmarClave = (EditText)findViewById(R.id.etConfirmarClave);
        etMarca = (EditText)findViewById(R.id.etMarca);
        etModelo = (EditText)findViewById(R.id.etModelo);

        rbMoto1 = (RadioButton)findViewById(R.id.rbMoto1);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Registrando..");
        pDialog.setCancelable(false);

        cbTerminos = (CheckBox)findViewById(R.id.cbTerminos);

        btRespuesta = (Button)findViewById(R.id.btRegister);

        btRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataGet();
            }
        });

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.ivAvatar:

                FragmentManager fragmentManager = getSupportFragmentManager();
                SelectDialog dialog = new SelectDialog();
                dialog.show(fragmentManager,"Seleccion");
                dialog.setCancelable(true);

                break;



        }

    }

    public void dataGet(){

        String nombre = String.valueOf(etNombre.getText());
        String apellido = String.valueOf(etApellido.getText());
        String ciudad = String.valueOf(etCiudad.getText());
        String telefono = String.valueOf(etTelefono.getText());
        String correo = String.valueOf(etCorreo.getText());
        String clave = String.valueOf(etClave.getText());
        String confirmarclave = String.valueOf(etConfirmarClave.getText());
        String marca = String.valueOf(etMarca.getText());
        String modelo = String.valueOf(etModelo.getText());



        RequestParams params = new RequestParams();


        params.put("user[moto]","true");
        params.put("user[nombre]",nombre);
        params.put("user[apellido]",apellido);
        params.put("user[cuidad]",ciudad);
        params.put("user[telefono]",telefono);
        params.put("user[correo]",correo);
        params.put("user[password]",clave);
        params.put("user[password_confirmation]",confirmarclave);
        params.put("user[marca]",marca);
        params.put("user[modelo]",modelo);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            InputStream in = new ByteArrayInputStream(bos.toByteArray());
            params.put("user[avatar]",in,"imagen.jpg");
        } catch (Exception e) {
            Log.e("FileNotFound","No se encontro el archivo");
            e.printStackTrace();
        }

        postReq(params);


    }


   public void postReq(RequestParams params){

        String URL = "http://104.131.32.54/users.json";

        showpDialog();

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(URL, params, new JsonHttpResponseHandler() {

           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               String id;
               hidepDialog();
               Log.d("Response: ",response.toString());
               try {
                   id = response.getString("id");
                   Log.d("Response id:",id);
                   hidepDialog();

                   if (id.equals("0")){
                       hidepDialog();
                       Toast.makeText(activityRegistro.this,response.getString("mensaje"),Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(activityRegistro.this,"Cuenta registrada",Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(activityRegistro.this,MainActivity.class);
                       i.putExtra("userData",response.toString());
                       hidepDialog();
                       startActivity(i);

                   }



               } catch (JSONException e) {
                   e.printStackTrace();
                   hidepDialog();
               }
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject response) {
               Toast.makeText(activityRegistro.this,"No se ha podido crear la cuenta. Status: "+statusCode,Toast.LENGTH_SHORT).show();
               hidepDialog();
               Log.d("Response: ", response.toString());
           }
       });



   }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("ResultCode", String.valueOf(resultCode));

        if (resultCode == Activity.RESULT_OK && data != null) {

            Uri pickedImage = data.getData();
            Log.d("pickedImage: ",pickedImage.toString());
            rotatedBitmap = Images.getRotatedBitmap(activityRegistro.this,pickedImage);
            ivPhotos.setImageBitmap(rotatedBitmap);

        }
    }

}
