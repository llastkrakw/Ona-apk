package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllCollActivity extends AppCompatActivity {

    private ImageButton back;
    private RecyclerView recyclerView;
    private CollectionAdapter collAdapter;
    private TextView your_col_number;
    private TextView download_col_number;

    private int yn = 0;
    private int dn = 0;

    private User user = null;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_coll);

        session = new Session(AllCollActivity.this);
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();

    }

    private void updateUi() {

        your_col_number = (TextView) findViewById(R.id.your_col_number);
        download_col_number = (TextView) findViewById(R.id.download_col_number);

        for(Collection col : user.getCollections()){

            if(col.getAuthor().equals(user.getId()))
                yn++;
            else
                dn++;

        }

        your_col_number.setText(String.valueOf(yn));
        download_col_number.setText(String.valueOf(dn));

        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCollActivity.this.onBackPressed();
            }
        });

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(AllCollActivity.this, R.anim.layout_animation_fall_down);


        if(user != null){
            collAdapter = new CollectionAdapter(user.getCollections(), AllCollActivity.this);
            recyclerView = (RecyclerView) findViewById(R.id.all_coll_recycler);
            recyclerView.setLayoutManager(new GridLayoutManager(AllCollActivity.this,2, LinearLayoutManager.VERTICAL,false));
            recyclerView.setLayoutAnimation(controller);
            recyclerView.setAdapter(collAdapter);
        }

    }

}