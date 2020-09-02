package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.databinding.ActivityMainBinding;
import com.ona.linkapp.databinding.ActivityProfileBinding;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.User;

public class ProfileActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private ImageButton back;

    private User user = null;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new Session(ProfileActivity.this);
        try {
            user = session.getUser();
            ActivityProfileBinding binding = DataBindingUtil.setContentView(ProfileActivity.this, R.layout.activity_profile);
            binding.setUser(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        setUi();

    }

    private void setUi(){

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

        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.onBackPressed();
            }
        });

    }
}