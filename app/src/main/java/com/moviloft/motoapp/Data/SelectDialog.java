package com.moviloft.motoapp.Data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.util.Log;

/**
 * Created by manuel on 06/12/14.
 */
public class SelectDialog extends DialogFragment {

    public static int LOAD_IMAGE_RESULTS = 1;

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
                                startActivityForResult(i, 0);


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