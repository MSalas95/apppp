package com.moviloft.motoapp.Menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity {

    TextView tv_forgot_pw;
    Button btSignIn, btLogin;
    EditText etCorreo,etClave;
    private ProgressDialog pDialog;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String name = "nameKey";
    public static final String pass = "passKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_forgot_pw = (TextView) findViewById(R.id.tv_forgot_pw);
        btSignIn = (Button)findViewById(R.id.btSignIn);
        btLogin = (Button)findViewById(R.id.btLogin);

        etCorreo = (EditText)findViewById(R.id.etCorreo);
        etClave = (EditText)findViewById(R.id.etClave);


        tv_forgot_pw.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Intent i = new Intent(LoginActivity.this,Code_Gen.class);
                    startActivity(i);
                }
                return true;
            }
        } );

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,activityRegistro.class);
                startActivity(i);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject data = new JSONObject();

                try {
                    data.put("correo",etCorreo.getText());
                    data.put("password",etClave.getText());
                    logIn(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                /*Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);*/
            }
        });

    }

    @Override
    protected void onResume() {
        sharedpreferences=getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name))
        {
            if(sharedpreferences.contains(pass)){
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }
        }
        super.onResume();
    }



    private void logIn(JSONObject data){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Iniciando sesión..");
        pDialog.setCancelable(false);

        String URL = "http://104.131.32.54/sessions.json ";

        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(URL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Respuesta de inicio de sesion: ",response.toString());
                            String id = response.getString("id");

                            if (id.equals("0")){
                                hidepDialog();
                                Toast.makeText(getApplicationContext(), response.get("error").toString(), Toast.LENGTH_SHORT).show();

                            } else {

                                String avatar = response.getString("avatar");
                                String nombre = response.getString("nombre");
                                String apellido = response.getString("apellido");
                                String cuidad = response.getString("cuidad");
                                String telefono = response.getString("telefono");
                                String correo = response.getString("correo");
                                String password = response.getString("password_digest");
                                String moto = response.getString("moto");
                                String marca = response.getString("marca");
                                String modelo = response.getString("modelo");

                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString("userData",response.toString());
                                editor.putString("avatar",avatar);
                                editor.putString("nombre",nombre);
                                editor.putString("apellido",apellido);
                                editor.putString("cuidad",cuidad);
                                editor.putString("telefono",telefono);
                                editor.putString("correo",correo);
                                editor.putString("password",password);
                                editor.putString("moto",moto);
                                editor.putString("marca",marca);
                                editor.putString("modelo",modelo);
                                editor.commit();

                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);


                            }
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

}
