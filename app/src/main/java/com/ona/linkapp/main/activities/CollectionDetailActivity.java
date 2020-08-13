package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.databinding.ActivityCollectionDetailBinding;
import com.ona.linkapp.databinding.ActivityMainBinding;
import com.ona.linkapp.helpers.ImageResize;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.helpers.SwipCallback;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class CollectionDetailActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private ImageButton back;
    private FloatingActionButton fab;
    private Collection collection;

    private User user = null;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        session = new Session(CollectionDetailActivity.this);
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        collection = getIntent().getParcelableExtra("Collection");
        
        if(collection != null)
            if (collection.getLinks() == null)
                collection.setLinks(new ArrayList<Link>());

        ActivityCollectionDetailBinding binding = DataBindingUtil.setContentView(CollectionDetailActivity.this, R.layout.activity_collection_detail);
        binding.setCollection(collection);

        updateUi();
    }


    private void updateUi(){

        circularImageView = (CircularImageView) findViewById(R.id.main_image_profile);

        // Set Color
        circularImageView.setCircleColor(Color.WHITE);

        //set border
        circularImageView.setBorderWidth(5f);
        circularImageView.setBorderColor(Color.WHITE);


        // Add Shadow with default param
        circularImageView.setShadowEnable(true);
        // or with custom param
        circularImageView.setShadowRadius(7f);
        circularImageView.setShadowColor(Color.RED);
        circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);


        circularImageView.setImageBitmap(ImageResize.decodeSampledBitmapFromResource(CollectionDetailActivity.this.getResources(),
                R.drawable.image_profile, 60, 60));


        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionDetailActivity.this.onBackPressed();
            }
        });

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(CollectionDetailActivity.this, R.anim.layout_animation_fall_down);
        recyclerView = (RecyclerView) findViewById(R.id.coll_detail_recycler);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setLayoutManager(new LinearLayoutManager(CollectionDetailActivity.this));

        linkAdapter = new LinkAdapter(CollectionDetailActivity.this, collection.getLinks());
        SwipCallback swipCallback = new SwipCallback(CollectionDetailActivity.this){

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                final Link item = linkAdapter.getData().get(position);

                linkAdapter.removeItem(position);

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(linkAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.bringToFront();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newLink = new Intent(CollectionDetailActivity.this, AddLinkActivity.class);
                startActivity(newLink);
            }
        });

    }

}