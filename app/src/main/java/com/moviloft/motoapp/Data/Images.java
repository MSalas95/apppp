package com.moviloft.motoapp.Data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by manuel on 06/12/14.
 */
public class Images {


    public static Bitmap rotarImagen(Bitmap bitmap, int orientation) {

        try{
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    return bitmap;
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }
            try {
                Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return bmRotated;
            }
            catch (OutOfMemoryError e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap getRotatedBitmap(Context context, Uri pickedImage){

        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();

        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        Log.d("ImagePath", imagePath);

        Bitmap img =  BitmapFactory.decodeFile(imagePath);

        ExifInterface exif = null ;

        try {
            exif = new ExifInterface(new File(imagePath).getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation =  exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Log.d("Orientation", String.valueOf(orientation));

        cursor.close();

        Bitmap resizedBitmap = getResizedBitmap(Images.rotarImagen(img, orientation), 200);

        return  resizedBitmap;

    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


}
