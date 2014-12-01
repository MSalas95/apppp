package com.moviloft.motoapp.Data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Manuel on 24/11/2014.
 */
public class SPreferences {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String NOMBRE = "nombre" ;
    public static final String APELLIDO = "apellido" ;
    public static final String CORREO = "correo" ;
    public static final String AVATAR = "avatar" ;
    public static final String CIUDAD = "ciudad" ;
    public static final String TELEFONO = "telefono" ;
    public static final String MOTO = "moto" ;
    public static final String MARCA = "telefono" ;
    public static final String MODELO = "telefono" ;
    public static final String ID = "id" ;

    public static String getPreference(String preference,Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);

        return  sharedPreferences.getString(preference,null);

    }

    public static void clearPreferences(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.commit();

    }




}
