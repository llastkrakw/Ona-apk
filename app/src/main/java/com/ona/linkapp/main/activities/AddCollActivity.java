package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ona.linkapp.R;
import com.ona.linkapp.datas.online.CollectionDAO;
import com.ona.linkapp.datas.online.LinkDAO;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.util.List;

public class AddCollActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Button addButton;

    private User user = null;
    private Session session;
    private CollectionDAO collectionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coll);

        session = new Session(AddCollActivity.this);
        collectionDAO = new CollectionDAO();
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();
    }

    private void updateUi() {

        title = (EditText) findViewById(R.id.title_edt);
        description = (EditText) findViewById(R.id.desc_edt);
        addButton = (Button) findViewById(R.id.save_link);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(TextUtils.isEmpty(title.getText().toString())){

                    title.setError("Is required");

                }
                else if(TextUtils.isEmpty(description.getText().toString())){

                    description.setError("Is required");

                }

                else {

                    new AddCollectionTask().execute(title.getText().toString(), description.getText().toString());

                }

            }
        });

    }

    public class AddCollectionTask extends AsyncTask<String, Void, String> {

        String title;
        String description;
        @Override
        protected String doInBackground(String... strings) {

            title = strings[0];
            description = strings[1];

            if(user != null){

                SimpleFilterProvider filterProvider = new SimpleFilterProvider();
                filterProvider.addFilter("colFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("title", "description", "author"));

                ObjectMapper mapper = new ObjectMapper();
                mapper.setFilterProvider(filterProvider);

                    Collection collection = new Collection(title, description, user.getId());
                try {
                    String colJson = mapper.writeValueAsString(collection);

                    Log.d("value", colJson);
                    String colResult = collectionDAO.addCol(colJson);

                    return colResult;
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

                    Intent intent = new Intent(AddCollActivity.this, MainActivity.class);
                    startActivity(intent);

            }
        }
    }

}