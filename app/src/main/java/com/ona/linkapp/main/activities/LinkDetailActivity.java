package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.databinding.ActivityCollectionDetailBinding;
import com.ona.linkapp.databinding.ActivityLinkDetailBinding;
import com.ona.linkapp.helpers.ImageResize;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.util.ArrayList;

public class LinkDetailActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private ImageButton back;
    private Link link;
    private User user = null;
    private Session session;

    private FloatingActionButton qrCode;
    private FloatingActionButton go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_detail);

        session = new Session(LinkDetailActivity.this);
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        link = getIntent().getParcelableExtra("Link");

        ActivityLinkDetailBinding binding = DataBindingUtil.setContentView(LinkDetailActivity.this, R.layout.activity_link_detail);
        binding.setLink(link);

        updateUi();

    }

    private  void updateUi(){

        qrCode = (FloatingActionButton) findViewById(R.id.fab_qr);
        go = (FloatingActionButton) findViewById(R.id.fab);

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


        circularImageView.setImageBitmap(ImageResize.decodeSampledBitmapFromResource(LinkDetailActivity.this.getResources(),
                R.drawable.image_profile, 60, 60));


        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkDetailActivity.this.onBackPressed();
            }
        });

        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qrIntent = new Intent(LinkDetailActivity.this, QrCodeActivity.class);
                qrIntent.putExtra("Link", link);
                startActivity(qrIntent);
            }
        });

    }
}