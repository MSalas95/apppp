package com.moviloft.motoapp.Menu;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moviloft.motoapp.R;


public class LoginActivity extends ActionBarActivity {

    TextView tv_forgot_pw;
    Button btSignIn, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_forgot_pw = (TextView) findViewById(R.id.tv_forgot_pw);
        btSignIn = (Button)findViewById(R.id.btSignIn);
        btLogin = (Button)findViewById(R.id.btLogin);

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
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }


}
