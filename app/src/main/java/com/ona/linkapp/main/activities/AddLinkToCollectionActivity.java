package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.AddLinkToCollectionAdapter;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.helpers.SwipCallback;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

public class AddLinkToCollectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddLinkToCollectionAdapter adapter;
    private FloatingSearchView searchView;

    private User user = null;
    private Session session;
    private Collection collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link_to_collection);

        collection = getIntent().getParcelableExtra("Collection");

        session = new Session(AddLinkToCollectionActivity.this);
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
                AnimationUtils.loadLayoutAnimation(AddLinkToCollectionActivity.this, R.anim.layout_animation_fall_down);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddLinkToCollectionActivity.this));

        if(user != null)
            adapter = new AddLinkToCollectionAdapter(AddLinkToCollectionActivity.this, user.getLinks(),  collection);

        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery

                adapter.filter(newQuery);

                //pass them on to the search view
                //searchView.swapSuggestions(newSuggestions);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}