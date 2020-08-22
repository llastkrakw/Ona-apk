package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.adapters.ForkCollectionAdapter;
import com.ona.linkapp.datas.online.CollectionDAO;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.dinus.com.loadingdrawable.LoadingView;

public class ForkCollectionActivity extends AppCompatActivity {

    private ImageButton back;
    private RecyclerView colRecyclerView;
    private ForkCollectionAdapter collAdapter;
    private LayoutAnimationController controller;

    private User user = null;
    private Session session;
    private CollectionDAO collectionDAO;

    private LoadingView loadingView;
    private FloatingSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork_collection);

        controller =
                AnimationUtils.loadLayoutAnimation(ForkCollectionActivity.this, R.anim.layout_animation_fall_down);

        loadingView = (LoadingView) findViewById(R.id.level_view);

        collectionDAO = new CollectionDAO();
        session = new Session(ForkCollectionActivity.this);
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();
        new GetAllColTask().execute();
    }

    private void updateUi(){

        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        colRecyclerView = (RecyclerView) findViewById(R.id.all_coll_recycler);
        colRecyclerView.setLayoutManager(new GridLayoutManager(ForkCollectionActivity.this,2, LinearLayoutManager.VERTICAL,false));

        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery
                
                collAdapter.filter(newQuery);

                //pass them on to the search view
                //searchView.swapSuggestions(newSuggestions);
            }
        });

    }

    private void updateUiCol(List<Collection> collections){


        if(collections.size() != 0){

            collAdapter = new ForkCollectionAdapter(collections, ForkCollectionActivity.this);
            colRecyclerView.setLayoutAnimation(controller);
            colRecyclerView.setAdapter(collAdapter);

        }

    }

    public class GetAllColTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {

            try {
                String colsJson = collectionDAO.getCols();
                return colsJson;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ObjectMapper mapper = new ObjectMapper();

            if(s != null){

                try {
                    List<Collection> collections = mapper.readValue(s,  new TypeReference<List<Collection>>() {});
                    List<Collection> newCollection = new ArrayList<>();

                    for(Collection collection : collections){

                        if(!collection.getAuthor().equals(user.getId()))
                            newCollection.add(collection);

                    }
                    updateUiCol(newCollection);
                    loadingView.setVisibility(View.INVISIBLE);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}