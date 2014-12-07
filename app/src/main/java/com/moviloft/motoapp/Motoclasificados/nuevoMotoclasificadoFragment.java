package com.moviloft.motoapp.Motoclasificados;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moviloft.motoapp.Data.Images;
import com.moviloft.motoapp.Data.SPreferences;
import com.moviloft.motoapp.Data.SelectDialog;
import com.moviloft.motoapp.Menu.MainActivity;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link nuevoMotoclasificadoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nuevoMotoclasificadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevoMotoclasificadoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProgressDialog pDialog;
    private Button btGuardar;

    public ImageView iv_imagen_clasificado;


    private String mParam1;
    private String mParam2;

    private Bitmap rotatedBitmap;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     */
    // TODO: Rename and change types and number of parameters
    public static nuevoMotoclasificadoFragment newInstance() {
        nuevoMotoclasificadoFragment fragment = new nuevoMotoclasificadoFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public nuevoMotoclasificadoFragment() {
        // Required empty public constructor
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
        final View V =  inflater.inflate(R.layout.fragment_nuevo_motoclasificado, container, false);

        btGuardar = (Button)V.findViewById(R.id.btGuardar);

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getData(V);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_imagen_clasificado = (ImageView)V.findViewById(R.id.iv_imagen_clasificado);

        iv_imagen_clasificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SelectDialog dialog = new SelectDialog();
                dialog.show(fragmentManager,"Seleccion");
                dialog.setCancelable(true);
            }
        });


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Subiendo clasificado..");
        pDialog.setCancelable(false);

        return V;

    }


    public void getData(View v) throws JSONException {

        EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
        EditText etValor = (EditText)v.findViewById(R.id.etValor);
        EditText etMarca = (EditText)v.findViewById(R.id.etMarca);
        EditText etModelo = (EditText)v.findViewById(R.id.etModelo);
        EditText etAño = (EditText)v.findViewById(R.id.etAño);
        EditText etCilindrada = (EditText)v.findViewById(R.id.etCilindrada);
        EditText etKilometraje = (EditText)v.findViewById(R.id.etKilometraje);
        EditText etDescripcion = (EditText)v.findViewById(R.id.etDescripcion);


        String nombre = String.valueOf(etNombre.getText());
        String valor = String.valueOf(etValor.getText());
        String marca = String.valueOf(etMarca.getText());
        String modelo = String.valueOf(etModelo.getText());
        String año = String.valueOf(etAño.getText());
        String cilindrada = String.valueOf(etCilindrada.getText());
        String kilometraje = String.valueOf(etKilometraje.getText());
        String descripcion = String.valueOf(etDescripcion.getText());

        RequestParams params = new RequestParams();


        params.put("clasificado[clasificado_nombre]",nombre);
        params.put("clasificado[clasificado_valor]",valor);
        params.put("clasificado[clasificado_marca]",marca);
        params.put("clasificado[clasificado_modelo]",modelo);
        params.put("clasificado[clasificado_ano]",año);
        params.put("clasificado[clasificado_cilindrada]",cilindrada);
        params.put("clasificado[clasificado_kilometraje]",kilometraje);
        params.put("clasificado[clasificado_descripcion]",descripcion);
        params.put("clasificado[user_id]",SPreferences.getPreference(SPreferences.ID,getActivity()));
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            InputStream in = new ByteArrayInputStream(bos.toByteArray());
            params.put("clasificado[clasificado_imagen]",in,"imagen.jpg");
        } catch (Exception e) {
            Log.e("FileNotFound","No se encontro el archivo");
            e.printStackTrace();
        }

        Log.d("Datos subidos: ",params.toString());

        postReq(params);

    }


    public void postReq(RequestParams params){

        String URL = "http://104.131.32.54/clasificados.json";

        showpDialog();

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                hidepDialog();
                Log.d("Response: ",response.toString());

                Toast.makeText(getActivity(),"Su clasificado ha sido publicado",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject response) {

                Log.d("Response: ", response.toString());
                hidepDialog();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super .onActivityResult(requestCode, resultCode, data);

        Log.d("ResultCode", String.valueOf(resultCode));

        if (resultCode == Activity.RESULT_OK && data != null) {

            Uri pickedImage = data.getData();
            rotatedBitmap = Images.getRotatedBitmap(getActivity(), pickedImage);
            iv_imagen_clasificado.setImageBitmap(rotatedBitmap);

        }
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
