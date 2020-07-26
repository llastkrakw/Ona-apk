package com.ona.linkapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ona.linkapp.helpers.ImageResize;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;

public class MainActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private CardView shimmer1;
    private CardView shimmer2;
    private CardView shimmer3;
    private CardView shimmer4;
    private CardView shimmer5;

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


        final ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();

        shimmer1 = (CardView) findViewById(R.id.card_fake1);
        shimmer2 = (CardView) findViewById(R.id.card_fake2);
        shimmer3 = (CardView) findViewById(R.id.card_fake_link1);
        shimmer4 = (CardView) findViewById(R.id.card_fake_link2);
        shimmer5 = (CardView) findViewById(R.id.card_fake_link3);

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideShimmer();
                container.stopShimmer();
            }
        });

    }

    private void hideShimmer(){
        shimmer1.setVisibility(View.INVISIBLE);
        shimmer2.setVisibility(View.INVISIBLE);
        shimmer3.setVisibility(View.INVISIBLE);
        shimmer4.setVisibility(View.INVISIBLE);
        shimmer5.setVisibility(View.INVISIBLE);
    }
}