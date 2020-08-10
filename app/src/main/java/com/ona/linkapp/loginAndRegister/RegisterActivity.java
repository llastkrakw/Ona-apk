package com.ona.linkapp.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.R;
import com.ona.linkapp.datas.online.UserDao;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.User;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private Button signUp;
    private EditText username;
    private EditText email;
    private EditText password;

    private UserDao userDao = new UserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        updateUi();
    }

    public void updateUi(){

        signUp = (Button) findViewById(R.id.sign_up);
        username =  (EditText) findViewById(R.id.name_edt);
        email =  (EditText) findViewById(R.id.email_edt);
        password =  (EditText) findViewById(R.id.password_edt);

        signUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(username.getText().toString())
                        || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){

                    username.setError("Is required !", getDrawable(R.drawable.ic_warning));
                    email.setError("Is required !", getDrawable(R.drawable.ic_warning));
                    password.setError("Is required !", getDrawable(R.drawable.ic_warning));

                }
                else {

                    new RegisterTask().execute(username.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString());

                }

            }
        });

    }

    public class RegisterTask extends AsyncTask<String, Void, String>{

        private String mName;
        private String mEmail;
        private String mPassword;

        @Override
        protected String doInBackground(String... strings) {

            mName = strings[0];
            mEmail = strings[1];
            mPassword = strings[2];

            ObjectMapper mapper = new ObjectMapper();
            String userJson = null;


            User user = new User(mName, mPassword, mEmail);
            try {
                 userJson =  mapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (userJson != null){

                try {
                    String resultUser = userDao.addUser(userJson);
                    return resultUser;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){

                Log.d("value", s);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    User user = mapper.readValue(s, User.class);
                    if(TextUtils.isEmpty(user.getId())){
                        Log.d("value", "Tes noyaux");
                    }
                    Intent main = new Intent(RegisterActivity.this, MainActivity.class);
                    main.putExtra("User", user);
                    startActivity(main);
                    RegisterActivity.this.finish();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}