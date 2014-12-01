package com.moviloft.motoapp.Motoclasificados;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviloft.motoapp.Data.SPreferences;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nuevoMotoclasificadoFragment.OnFragmentInteractionListener} interface
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Subiendo clasificado..");
        pDialog.setCancelable(false);

        return V;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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

        JSONObject jsonObject2 = new JSONObject();

        jsonObject2.put("clasificado_nombre",nombre);
        jsonObject2.put("clasificado_valor",valor);
        jsonObject2.put("clasificado_marca",marca);
        jsonObject2.put("clasificado_modelo",modelo);
        jsonObject2.put("clasificado_ano",año);
        jsonObject2.put("clasificado_cilindrada",cilindrada);
        jsonObject2.put("clasificado_kilometraje",kilometraje);
        jsonObject2.put("clasificado_descripcion",descripcion);
        jsonObject2.put("user_id", SPreferences.getPreference(SPreferences.ID,getActivity()));


       JSONObject jsonObject = new JSONObject();
       jsonObject.put("clasificado",jsonObject2);
       Log.d("data: ",jsonObject.toString());

       PostReq(jsonObject);

    }


    private void PostReq( JSONObject jsonObject){

        String URL = "http://104.131.32.54/clasificados.json";

        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("Respuesta de registro: ",response.toString());

                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hidepDialog();
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
