package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.AddLinkToCollectionAdapter;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.User;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView colRecycler;
    private RecyclerView linkRecycler;
    private LinkAdapter linkAdapter;
    private CollectionAdapter colAdapter;
    private FloatingSearchView searchView;

    private LayoutAnimationController controller;

    private User user = null;
    private Session session;
    private Collection collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        session = new Session(SearchActivity.this);
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();

    }

    private void updateUi(){

        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(SearchActivity.this, R.anim.layout_animation_fall_down);

        linkRecycler = (RecyclerView) findViewById(R.id.link_recyclerView);
        linkRecycler.setLayoutAnimation(controller);
        linkRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        colRecycler = (RecyclerView) findViewById(R.id.coll_recyclerView);
        colRecycler.setLayoutAnimation(controller);
        colRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false));

        if(user != null){
            linkAdapter = new LinkAdapter(SearchActivity.this, user.getLinks());
            colAdapter = new CollectionAdapter( user.getCollections(), SearchActivity.this);
        }

        linkRecycler.setAdapter(linkAdapter);
        colRecycler.setAdapter(colAdapter);

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery

                linkAdapter.filter(newQuery);
                colAdapter.filter(newQuery);

                //pass them on to the search view
                //searchView.swapSuggestions(newSuggestions);
            }
        });

    }
}