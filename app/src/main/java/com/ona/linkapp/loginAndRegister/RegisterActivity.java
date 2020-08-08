package com.ona.linkapp.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ona.linkapp.R;
import com.ona.linkapp.main.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        updateUi();
    }

    public void updateUi(){

        signUp = (Button) findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(main);
            }
        });

    }
}