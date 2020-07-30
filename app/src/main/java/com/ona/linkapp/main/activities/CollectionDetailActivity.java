package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.helpers.ImageResize;
import com.ona.linkapp.helpers.SwipCallback;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Link;

import java.util.ArrayList;
import java.util.List;

public class CollectionDetailActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

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

        linkAdapter = new LinkAdapter(CollectionDetailActivity.this, createFakeLink());
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

    }


    public List<Link> createFakeLink(){

        List<Link> links = new ArrayList<>();

        Link link1 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link2 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link3 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link4 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link5 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link6 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link7 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);
        Link link8 = new Link("Android documentation", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", 0);


        links.add(link1);
        links.add(link2);
        links.add(link3);
        links.add(link4);
        links.add(link5);
        links.add(link6);
        links.add(link7);
        links.add(link8);

        return links;

    }
}