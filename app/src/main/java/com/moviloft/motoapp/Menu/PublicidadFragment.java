package com.moviloft.motoapp.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicidadFragment extends Fragment {


    public PublicidadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_publicidad, container, false);

        NetworkImageView ivPublicidad = (NetworkImageView)v.findViewById(R.id.ivPublicidad);

        Bundle bundle = getArguments();

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        ivPublicidad.setImageUrl(bundle.getString("imagenPrincipal"), imageLoader);

        return v;
    }


}
