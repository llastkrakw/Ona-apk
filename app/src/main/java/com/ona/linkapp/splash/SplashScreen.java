package com.ona.linkapp.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

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
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                try {

                    if(session.getUser() != null){

                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);

                        prefs.edit().putBoolean("isFirstSession", false).apply();

                        finish();

                    }
                    else {

                        Intent i = new Intent(SplashScreen.this, OnboardingActivity.class);
                        startActivity(i);

                        prefs.edit().putBoolean("isFirstSession", true).apply();

                        finish();

                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }, SPLASH_TIME_OUT);

    }
}