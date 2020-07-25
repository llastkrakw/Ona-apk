package com.ona.linkapp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import com.ona.linkapp.helpers.ImageResize;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;

public class MainActivity extends AppCompatActivity {

    private CircularImageView circularImageView;

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

    }
}