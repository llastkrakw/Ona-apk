package com.ona.linkapp.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.R;
import com.ona.linkapp.databinding.ActivityMainBinding;
import com.ona.linkapp.datas.online.UserDao;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button signIn;
    private TextView newAccount;
    private UserDao userDao = new UserDao();
    private TextView userName;
    private TextView password;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(LoginActivity.this);

        updateUi();
    }

    public void updateUi(){

        signIn = (Button) findViewById(R.id.sign_in);
        newAccount = (TextView) findViewById(R.id.new_account);
        userName = (EditText) findViewById(R.id.name_edt);
        password = (EditText) findViewById(R.id.password_edt);

        signIn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){

                    userName.setError("Is required", getDrawable(R.drawable.ic_warning));
                    password.setError("Is required", getDrawable(R.drawable.ic_warning));

                }
                else {
                    new LoginTask().execute(userName.getText().toString(), password.getText().toString());
                }
/*                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);*/
            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

    }

    public class LoginTask extends AsyncTask<String, Void, String>{

        private String name;
        private String pass;

        @Override
        protected String doInBackground(String... strings) {

            try {
                name = strings[0];
                pass = strings[1];
                return userDao.Login(name, pass);
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){

                ObjectMapper usermapper = new ObjectMapper();

                try {


                    User mUser = usermapper.readValue(s, User.class);

                    if(mUser.getUsername() != null){

                        Toast.makeText(LoginActivity.this, "Success !", Toast.LENGTH_SHORT).show();

                        Intent main = new Intent(LoginActivity.this, MainActivity.class);

                        try {
                            session.setUser(mUser);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                        session.setUser(mUser);
                        startActivity(main);

                        LoginActivity.this.finish();

                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Access Denied !", Toast.LENGTH_SHORT).show();
                    }


                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}