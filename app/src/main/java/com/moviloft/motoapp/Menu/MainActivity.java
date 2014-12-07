package com.moviloft.motoapp.Menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviloft.motoapp.Motoclasificados.Motoclasificados;
import com.moviloft.motoapp.R;
import com.moviloft.motoapp.Volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    Button btClasificados;
    ViewPager viewPager;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btClasificados = (Button) findViewById(R.id.btClasificados);
        btClasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Motoclasificados.class);
                startActivity(i);
            }
        });

        viewPager=(ViewPager)findViewById(R.id.pager);


        try {
            setBanners(new JSONArray(getIntent().getStringExtra("array")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setBanners(JSONArray data) throws JSONException {

        mAdapter = new MyAdapter(getSupportFragmentManager(), data);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }
        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


    }


    /** ----------------------------Page transformer --------------------------**/

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void transformPage(View view, float v) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (v < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (v <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(v));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (v < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }

    }


    /** -------------------------- ADAPTADOR ------------------------------------*/

    class MyAdapter extends FragmentStatePagerAdapter {

        ArrayList<PublicidadFragment> list;

        public MyAdapter(FragmentManager fm, JSONArray array) throws JSONException {

            super(fm);

            list = new ArrayList<PublicidadFragment>();

            for (int i=0;i<array.length();i++){

                JSONObject obj = array.getJSONObject(i);
                JSONObject data = obj.getJSONObject("data");

                String imagenPrincipal = "http://motoapp.com.co"+data.getString("imagePrincipal");
                String accion_banner = data.getString("accion_banner");


                Bundle bundle = new Bundle();
                bundle.putString("imagenPrincipal", imagenPrincipal);
                bundle.putString("accion_banner",accion_banner);

                PublicidadFragment fragment =new PublicidadFragment();
                fragment.setArguments(bundle);

                list.add(fragment);

            }
        }


        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            for (int i=0;i<10;i++){

                if (position == i){
                    fragment = list.get(i);
                }

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }






}
