package com.tecmanic.servicepartner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.tecmanic.servicepartner.Constants.Session_management;
import com.tecmanic.servicepartner.R;

public class Splash extends AppCompatActivity {

    public  static int SPLASH_DISPLAY_LENGTH=3000;
    Session_management sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().hide();
        sessionManagement=new Session_management(this);
        redirectionScreen();
    }



    private void redirectionScreen(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(sessionManagement.isLoggedIn()){
                    Intent intent1 =new Intent(Splash.this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
                else {
                    Intent intent1 =new Intent(Splash.this,LoginSignupActivity.class);
                    startActivity(intent1);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
