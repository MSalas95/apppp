package com.moviloft.motoapp.Motoclasificados;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.moviloft.motoapp.ListAdapter.Adaptador;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MotoclasificadosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MotoclasificadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotoclasificadosFragment extends Fragment {

    private static final String ARG1 = "classifiedData";

    private String classifiedData; //

    private OnFragmentInteractionListener mListener;

    public ListView lista;

    public static MotoclasificadosFragment newInstance(String classifiedData) {
        MotoclasificadosFragment fragment = new MotoclasificadosFragment();
        Bundle args = new Bundle();
        args.putString(ARG1, classifiedData);
        fragment.setArguments(args);
        return fragment;
    }

    public MotoclasificadosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            classifiedData = getArguments().getString(ARG1);
        }
        makeJsonRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View V = inflater.inflate(R.layout.fragment_motoclasificados, container, false);

        lista = (ListView)V.findViewById(R.id.lista);

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



    private void makeJsonRequest() {

        String URL = "http://104.131.32.54/clasificados.json";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try{

                    Log.d("Clasificados", response.toString());

                    JSONArray array;
                    array = response.getJSONArray("clasificado");

                    JSONObject obj;
                    obj = array.getJSONObject(0);

                    String count = obj.optJSONObject("data").getString("count");

                    if (count.equals("0")){

                        Log.d("No hay clasificados: ","No hay clasificados para mostrar");

                    } else {

                        fillList(array);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }


    public void fillList(JSONArray jsonArray){

        ArrayList<HashMap<String, String>> Lista = new ArrayList<HashMap<String, String>>();

        for (int i=0;i<jsonArray.length();i++){

            try {
                JSONObject data = jsonArray.getJSONObject(i);
                JSONObject jsonObject = data.getJSONObject("data");

                HashMap<String,String> map = new HashMap<String, String>();


                map.put("clasificado_nombre",jsonObject.getString("clasificado_nombre"));
                map.put("clasificado_valor",jsonObject.getString("clasificado_valor"));
                map.put("clasificado_marca",jsonObject.getString("clasificado_marca"));
                map.put("clasificado_modelo",jsonObject.getString("clasificado_modelo"));
                map.put("clasificado_ano",jsonObject.getString("clasificado_ano"));
                map.put("clasificado_cilindrada",jsonObject.getString("clasificado_cilindrada"));
                map.put("clasificado_descripcion",jsonObject.getString("clasificado_descripcion"));
                map.put("clasificado_kilometraje", jsonObject.getString("clasificado_kilometraje"));
                map.put("nombre",jsonObject.getString("nombre"));
                map.put("apellido",jsonObject.getString("apellido"));
                map.put("ciudad",jsonObject.getString("cuidad"));
                map.put("correo", jsonObject.getString("correo"));
                map.put("clasificadoImagen", jsonObject.getString("clasificadoImagen"));
                map.put("avatar", jsonObject.getString("avatar"));
                map.put("id",jsonObject.getString("id"));


                Lista.add(map);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



         Adaptador adapter= new Adaptador(getActivity(),R.layout.item_classifieds,Lista) {
            @Override
            public void onEntrada(Object entrada, View view, ViewHolder holder) {
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                holder.tvTitulo.setText(((HashMap<String,String>) entrada).get("clasificado_nombre"));
                holder.ivImagen.setImageUrl("http://motoapp.com.co"+((HashMap<String,String>) entrada).get("clasificadoImagen"),imageLoader);
            }

            @Override
            public void initHolder(ViewHolder holder, View convertView, int position, Context context) {

               holder.tvTitulo = (TextView)convertView.findViewById(R.id.tvTitulo);
                holder.ivImagen = (NetworkImageView)convertView.findViewById(R.id.ivImagen);

            }
        };

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> data = (HashMap<String,String>)parent.getItemAtPosition(position);

                Intent i = new Intent(getActivity(),Clasificado.class);

                Bundle bundle = new Bundle();

                bundle.putString("clasificado_nombre",data.get("clasificado_nombre"));
                bundle.putString("clasificado_valor",data.get("clasificado_valor"));
                bundle.putString("clasificado_marca",data.get("clasificado_marca"));
                bundle.putString("clasificado_modelo",data.get("clasificado_modelo"));
                bundle.putString("clasificado_ano",data.get("clasificado_ano"));
                bundle.putString("clasificado_cilindrada",data.get("clasificado_cilindrada"));
                bundle.putString("clasificado_descripcion",data.get("clasificado_descripcion"));
                bundle.putString("clasificado_kilometraje",data.get("clasificado_kilometraje"));
                bundle.putString("nombre",data.get("nombre"));
                bundle.putString("apellido",data.get("apellido"));
                bundle.putString("correo",data.get("correo"));
                bundle.putString("ciudad",data.get("ciudad"));
                bundle.putString("id",data.get("id"));
                bundle.putString("clasificadoImagen",data.get("clasificadoImagen"));
                bundle.putString("avatar",data.get("avatar"));
                i.putExtra("extras",bundle);

                startActivity(i);

            }
        });

    }



}
