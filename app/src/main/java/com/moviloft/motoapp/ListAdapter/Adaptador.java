package com.moviloft.motoapp.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Created by manuel on 19/09/14.
 */
public abstract class Adaptador extends BaseAdapter {

    public int[] boxState;
    private Context contexto;
    public ArrayList<?> entradas;
    private int R_layout_IdView;

    private LayoutInflater inflater;

    public Adaptador(Context contexto, int r_layout_IdView, ArrayList<?> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        R_layout_IdView = r_layout_IdView;

        boxState = new int[entradas.size()];

    }



    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int position) {
        return entradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {

        public TextView tvTitulo;
        public NetworkImageView ivImagen;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (inflater == null)
            inflater = (LayoutInflater) contexto
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){

            convertView = inflater.inflate(R_layout_IdView,null);
            holder = new ViewHolder();

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initHolder(holder,convertView, position, contexto);



        onEntrada(entradas.get(position), convertView, holder);
        return convertView;
    }

    public abstract void onEntrada (Object entrada, View view, ViewHolder holder);

    public abstract void initHolder (ViewHolder holder, View convertView,int position, Context context);




}
