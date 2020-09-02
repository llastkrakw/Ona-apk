package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ona.linkapp.R;
import com.ona.linkapp.datas.online.LinkDAO;
import com.ona.linkapp.datas.online.ShortenDAO;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.ShortenLink;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddLinkActivity extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private EditText link;
    private EditText title;
    private EditText description;
    private EditText path;
    private Button addButton;
    private FloatingActionButton scan;
    private int SCAN_RESULT = 10;

    private User user = null;
    private Session session;
    private LinkDAO linkDAO;
    private ShortenDAO shortenDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        session = new Session(AddLinkActivity.this);
        linkDAO = new LinkDAO();
        shortenDAO = new ShortenDAO();
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String uri = (String)bundle.get(Intent.EXTRA_TEXT);
            if(uri != null){
                 if (Objects.equals(intent.getType(), "text/plain")) {
                     link.setText(uri);
                }
            }
        }

    }

    private void updateUi(){

        link = (EditText) findViewById(R.id.link);
        title = (EditText) findViewById(R.id.title_edt);
        description = (EditText) findViewById(R.id.desc_edt);
        path = (EditText) findViewById(R.id.path_edt);
        addButton = (Button) findViewById(R.id.save_link);
        scan = (FloatingActionButton) findViewById(R.id.fab_scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(AddLinkActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(AddLinkActivity.this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }else {
                    Intent startScan = new Intent(AddLinkActivity.this, ScanActivity.class);
                    startActivityForResult(startScan, SCAN_RESULT);
                }
            }
        });

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

                    new AddLinkTask().execute(title.getText().toString(), description.getText().toString(), link.getText().toString(), path.getText().toString());

                }

            }
        });

    }

    public class AddLinkTask extends AsyncTask<String, Void, String>{

        String url;
        String title;
        String description;
        String hash;
        @Override
        protected String doInBackground(String... strings) {

            title = strings[0];
            description = strings[1];
            url = strings[2];
            hash = strings[3];

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

                    Link result = mapper.readValue(linkResult, Link.class);

                    if(!TextUtils.isEmpty(hash)){

                        SimpleFilterProvider filterProvider2 = new SimpleFilterProvider();
                        filterProvider2.addFilter("shortFilter",
                                SimpleBeanPropertyFilter.filterOutAllExcept("hash"));

                        ObjectMapper mapper2 = new ObjectMapper();
                        mapper2.setFilterProvider(filterProvider2);

                        ShortenLink mShort = new ShortenLink();
                        mShort.setHash(hash);

                        String mShortString =  mapper2.writeValueAsString(mShort);

                        shortenDAO.addShort(mShortString, result.getId());

                    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SCAN_RESULT)
        {
            if(data != null){
                String url = data.getStringExtra("URL");
                link.setText(url);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent startScan = new Intent(AddLinkActivity.this, ScanActivity.class);
                startActivityForResult(startScan, SCAN_RESULT);
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}