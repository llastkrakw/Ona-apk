package com.ona.linkapp.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ona.linkapp.R;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        session = new Session(SplashScreen.this);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                try {

                    if(session.getUser() != null){

                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);

                        finish();

                    }
                    else {

                        Intent i = new Intent(SplashScreen.this, OnboardingActivity.class);
                        startActivity(i);

                        finish();

                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }, SPLASH_TIME_OUT);

    }
}