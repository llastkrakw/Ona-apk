package com.ona.linkapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.pwittchen.swipe.library.rx2.SimpleSwipeListener;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.helpers.ImageResize;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.splash.OnboardingActivity;
import com.ona.linkapp.splash.SplashScreen;

import java.util.ArrayList;
import java.util.Date;
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
    private LinkAdapter linkAdapter;

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

                    }
                });

                addColl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                addGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                hideShimmer();
                linkRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                linkAdapter = new LinkAdapter(MainActivity.this, createFakeLink());
                linkRecyclerView.setAdapter(linkAdapter);

            }
        }, 4000);

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
}