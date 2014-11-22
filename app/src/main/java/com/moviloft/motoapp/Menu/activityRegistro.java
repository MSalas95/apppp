package com.moviloft.motoapp.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.json.JSONException;
import org.json.JSONObject;


public class activityRegistro extends ActionBarActivity implements View.OnClickListener {

    Button btRespuesta;
    ImageView ivPhotos;
    EditText etNombre, etApellido, etCiudad,etTelefono, etCorreo, etClave, etConfirmarClave, etMarca, etModelo;
    RadioButton rbMoto1, rbMoto2;
    CheckBox cbTerminos;

    String imagePath;

    private static int LOAD_IMAGE_RESULTS = 1;

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




        JSONObject jsonObject = new JSONObject();

        try {


           jsonObject.put("moto","true");




            jsonObject.put("nombre",nombre);
            jsonObject.put("apellido",apellido);
            jsonObject.put("cuidad",ciudad);
            jsonObject.put("telefono",telefono);
            jsonObject.put("telefono",correo);
            jsonObject.put("password",clave);
            jsonObject.put("password_confirmation",confirmarclave);
            jsonObject.put("marca",marca);
            jsonObject.put("modelo",modelo);
            jsonObject.put("avatar",imagePath);

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("user",jsonObject);


            postReq(jsonObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


   public void postReq(JSONObject jsonObject){

        String URL = "http://104.131.32.54/users.json";

        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                           // String id = response.getString("id");

                          //  if (id.equals("0")){
                                hidepDialog();
                                Toast.makeText(getApplicationContext(), response.get("mensaje").toString(), Toast.LENGTH_LONG).show();

                           // } else {

                                Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();

                          //  }
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            hidepDialog();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hidepDialog();
            }

        });

        AppController.getInstance().addToRequestQueue(req);

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

            if (requestCode == LOAD_IMAGE_RESULTS) {

                Uri pickedImage = data.getData();

                // Let's read picked image path using content resolver
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                Log.d("ImagePath",imagePath);

                // Now we need to set the GUI ImageView data with data read from the picked file.
                ivPhotos.setImageBitmap(BitmapFactory.decodeFile(imagePath));

                // At the end remember to close the cursor or you will end with the RuntimeException!
                cursor.close();


            } else {
                Bundle ext = data.getExtras();

                Bitmap bmp = (Bitmap) ext.get("data");

                Log.d("La imagen es: ", bmp.toString());

                ivPhotos.setImageBitmap(bmp);
            }
        }


    }


    /*****Dialog****/

     public static class SelectDialog extends DialogFragment {

        Bitmap bmp;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final String[] items = {"Tomar foto", "Ir a galería"};

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setTitle("Seleccionar")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            Log.i("Dialogos", "Opción elegida: " + items[item]);

                            switch (item){
                                case 0:

                                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    getActivity().startActivityForResult(i, 0);


                                    break;
                                case 1:
                                    Intent j = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    j.setType("image/*");
                                    getActivity().startActivityForResult(j, LOAD_IMAGE_RESULTS);


                                    break;
                            }



                        }
                    });

            return builder.create();
        }


    }



}
