package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ona.linkapp.R;
import com.ona.linkapp.datas.online.LinkDAO;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

public class AddLinkActivity extends AppCompatActivity {

    private EditText link;
    private EditText title;
    private EditText description;
    private Button addButton;

    private User user = null;
    private Session session;
    private LinkDAO linkDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        session = new Session(AddLinkActivity.this);
        linkDAO = new LinkDAO();
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();

    }

    private void updateUi(){

        link = (EditText) findViewById(R.id.link);
        title = (EditText) findViewById(R.id.title_edt);
        description = (EditText) findViewById(R.id.desc_edt);
        addButton = (Button) findViewById(R.id.save_link);

        link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void afterTextChanged(Editable editable) {

                String url = editable.toString();

                if( !URLUtil.isValidUrl(url) || !URLUtil.isValidUrl(url)){

                    link.setError("Invalid Url", getDrawable(R.drawable.ic_warning));

                }

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(link.getText().toString())){

                    link.setError("Is required");

                }
                else if(TextUtils.isEmpty(title.getText().toString())){

                    title.setError("Is required");

                }
                else if(TextUtils.isEmpty(description.getText().toString())){

                    description.setError("Is required");

                }

                else {

                    new AddLinkTask().execute(title.getText().toString(), description.getText().toString(), link.getText().toString());

                }

            }
        });

    }

    public class AddLinkTask extends AsyncTask<String, Void, String>{

        String url;
        String title;
        String description;
        @Override
        protected String doInBackground(String... strings) {

            title = strings[0];
            description = strings[1];
            url = strings[2];

            if(user != null){

                SimpleFilterProvider filterProvider = new SimpleFilterProvider();
                filterProvider.addFilter("linkFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("title", "description", "url", "author"));

                ObjectMapper mapper = new ObjectMapper();
                mapper.setFilterProvider(filterProvider);

                Link link = new Link(title, description, url, user.getId());
                try {
                    String linkJson = mapper.writeValueAsString(link);

                    Log.d("value", linkJson);
                    String linkResult = linkDAO.addLink(linkJson);

                    return linkResult;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){

                    Intent intent = new Intent(AddLinkActivity.this, MainActivity.class);
                    startActivity(intent);


            }
        }
    }
}