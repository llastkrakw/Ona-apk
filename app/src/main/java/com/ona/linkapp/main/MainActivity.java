package com.ona.linkapp.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.pwittchen.swipe.library.rx2.SimpleSwipeListener;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.adapters.GroupAdapter;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.helpers.ImageResize;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.helpers.SwipCallback;
import com.ona.linkapp.main.activities.AddCollActivity;
import com.ona.linkapp.main.activities.AddGroupActivity;
import com.ona.linkapp.main.activities.AddLinkActivity;
import com.ona.linkapp.main.activities.AllCollActivity;
import com.ona.linkapp.main.activities.AllLinkActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Group;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.splash.OnboardingActivity;
import com.ona.linkapp.splash.SplashScreen;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private ShimmerFrameLayout container;
    private FloatingActionButton fab;
    private CardView shimmer1;
    private CardView shimmer2;
    private CardView shimmer3;
    private CardView shimmer4;
    private CardView shimmer5;
    private CardView shimmer6;

    private ImageButton menu;
    private Swipe swipe;

    private RecyclerView linkRecyclerView;
    private RecyclerView collRecyclerView;
    private LinkAdapter linkAdapter;
    private CollectionAdapter collAdapter;
    private TextView link_view_all;
    private TextView coll_view_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUiProfile();
    }

    private void setUiProfile(){

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


        circularImageView.setImageBitmap(ImageResize.decodeSampledBitmapFromResource(MainActivity.this.getResources(),
                R.drawable.image_profile, 60, 60));


        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();

        shimmer1 = (CardView) findViewById(R.id.card_fake1);
        shimmer2 = (CardView) findViewById(R.id.card_fake2);
        shimmer3 = (CardView) findViewById(R.id.card_fake_link1);
        shimmer4 = (CardView) findViewById(R.id.card_fake_link2);
        shimmer5 = (CardView) findViewById(R.id.card_fake_link3);
        shimmer6 = (CardView) findViewById(R.id.card_fake_link4);

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideShimmer();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.bringToFront();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog addDialog = new Dialog(MainActivity.this);
                addDialog.setContentView(R.layout.main_bottom_add_dialog);

                Button addLink = (Button) addDialog.findViewById(R.id.button_new_link);
                Button addGroup = (Button) addDialog.findViewById(R.id.button_new_group);
                Button addColl = (Button) addDialog.findViewById(R.id.button_new_coll);

                addLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent addLinkInt = new Intent(MainActivity.this, AddLinkActivity.class);
                        startActivity(addLinkInt);

                    }
                });

                addColl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent addGroupInt = new Intent(MainActivity.this, AddCollActivity.class);
                        startActivity(addGroupInt);

                    }
                });

                addGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent addGroupInt = new Intent(MainActivity.this, AddGroupActivity.class);
                        startActivity(addGroupInt);

                    }
                });

                Objects.requireNonNull(addDialog.getWindow()).setGravity(Gravity.BOTTOM);
                addDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(addDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                addDialog.getWindow().setAttributes(lp);

                addDialog.show();

            }
        });

        menu = (ImageButton) findViewById(R.id.main_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNavigationDrawer();
            }
        });

/*        swipe = new Swipe();
        swipe.setListener(new SimpleSwipeListener() {
            @Override
            public boolean onSwipedRight(MotionEvent event) {
                makeNavigationDrawer();
                return super.onSwipedRight(event);
            }
        });*/

        linkRecyclerView = (RecyclerView) findViewById(R.id.link_recyclerView);
        collRecyclerView = (RecyclerView) findViewById(R.id.coll_recyclerView);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                hideShimmer();
                linkRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_fall_down);

                linkAdapter = new LinkAdapter(MainActivity.this, createFakeLink());
                linkRecyclerView.setLayoutAnimation(controller);
                linkRecyclerView.setAdapter(linkAdapter);

                SwipCallback swipCallback = new SwipCallback(MainActivity.this){

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        final int position = viewHolder.getAdapterPosition();
                        final Link item = linkAdapter.getData().get(position);

                        linkAdapter.removeItem(position);

                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipCallback);
                itemTouchhelper.attachToRecyclerView(linkRecyclerView);

                collAdapter = new CollectionAdapter(createFakeCollection(), MainActivity.this);
                collRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                collRecyclerView.setLayoutAnimation(controller);
                collRecyclerView.setAdapter(collAdapter);


            }
        }, 4000);


        link_view_all = (TextView) findViewById(R.id.link_view_all);
        link_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent view_all = new Intent(MainActivity.this, AllLinkActivity.class);
                startActivity(view_all);
            }
        });

        coll_view_all = (TextView) findViewById(R.id.col_view_all);
        coll_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent view_all = new Intent(MainActivity.this, AllCollActivity.class);
                startActivity(view_all);
            }
        });

    }

    private void hideShimmer(){

        shimmer1.setVisibility(View.INVISIBLE);
        shimmer2.setVisibility(View.INVISIBLE);
        shimmer3.setVisibility(View.INVISIBLE);
        shimmer4.setVisibility(View.INVISIBLE);
        shimmer5.setVisibility(View.INVISIBLE);
        shimmer6.setVisibility(View.INVISIBLE);
        container.stopShimmer();

    }

    private void showShimmer(){
        shimmer1.setVisibility(View.VISIBLE);
        shimmer2.setVisibility(View.VISIBLE);
        shimmer3.setVisibility(View.VISIBLE);
        shimmer4.setVisibility(View.VISIBLE);
        shimmer5.setVisibility(View.VISIBLE);
        shimmer6.setVisibility(View.VISIBLE);
        container.stopShimmer();
    }

    private void makeNavigationDrawer(){

        final Dialog navigationDialog = new Dialog(MainActivity.this);
        navigationDialog.setContentView(R.layout.navigation_drawer);

        RecyclerView recyclerView_group = navigationDialog.findViewById(R.id.group_recycler);
        GroupAdapter adapter = new GroupAdapter(createFakeGroup(), MainActivity.this);
        recyclerView_group.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView_group.setAdapter(adapter);

        Objects.requireNonNull(navigationDialog.getWindow()).setGravity(Gravity.START);
        navigationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(navigationDialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        navigationDialog.getWindow().setAttributes(lp);

        navigationDialog.show();

    }

/*    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        swipe.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }*/

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

    public List<Collection>  createFakeCollection(){

        List<Collection> collections = new ArrayList<>();

        Collection link1 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link2 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link3 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link4 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link5 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link6 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link7 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());
        Collection link8 = new Collection("Android", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink());


        collections.add(link1);
        collections.add(link2);
        collections.add(link3);
        collections.add(link4);
        collections.add(link5);
        collections.add(link6);
        collections.add(link7);
        collections.add(link8);

        return collections;
    }

    public List<Group> createFakeGroup(){

        List<Group> groups = new ArrayList<>();

        Group group1 = new Group("Android Community", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink(), null, createFakeCollection());
        Group group2 = new Group("Android Community", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink(), null, createFakeCollection());
        Group group3 = new Group("Android Community", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink(), null, createFakeCollection());
        Group group4 = new Group("Android Community", "documentatio pour les dev android", "Bakarri Potter", "21/10/2001", createFakeLink(), null, createFakeCollection());

        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);

        return groups;

    }
}