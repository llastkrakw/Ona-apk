package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.BuildConfig;
import com.ona.linkapp.R;
import com.ona.linkapp.databinding.ActivityCollectionDetailBinding;
import com.ona.linkapp.databinding.ActivityLinkDetailBinding;
import com.ona.linkapp.helpers.ImageResize;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LinkDetailActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private ImageButton back;
    private ImageButton copy;
    private ImageButton share;
    private Link link;
    private User user = null;
    private Session session;

    private FloatingActionButton qrCode;
    private FloatingActionButton go;
    private ClipboardManager cm;
    private ClipData pData;

    private TextView username;
    private TextView type_value;
    private TextView secure;
    private EditText link_value;

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

        Picasso.get().
                load((Uri.parse("https://unavatar.now.sh/").buildUpon().appendPath(user.getEmail()).toString()))
                .into(circularImageView);


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

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getUrl()));
                startActivity(browserIntent);
            }
        });

        link_value = (EditText) findViewById(R.id.link_value);

        username = (TextView) findViewById(R.id.username);
        username.setText(user.getUsername());

        copy = (ImageButton) findViewById(R.id.copy);
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pData = ClipData.newPlainText("text", link_value.getText());
                cm.setPrimaryClip(pData);

                Snackbar snackbar = Snackbar.make(view, "Copy Successful !", Snackbar.LENGTH_LONG);
                View snackBarLayout = snackbar.getView();
                TextView textView = (TextView) snackBarLayout.findViewById(R.id.snackbar_text);
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_copy, 0, 0, 0);
                textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
                snackBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary) );
                textView.setTextColor(Color.WHITE);
                snackbar.show();

            }
        });

        share = (ImageButton) findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share with ona");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, link.getUrl());
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        type_value = (TextView) findViewById(R.id.type_value);
        secure = (TextView) findViewById(R.id.sec_value);

        if(URLUtil.isHttpsUrl(link.getUrl()) || URLUtil.isHttpUrl(link.getUrl())){
            type_value.setText(R.string.web_site_type);
        }
        else if(URLUtil.isFileUrl(link.getUrl())){
            type_value.setText(R.string.file_type);
        }

        if (URLUtil.isHttpsUrl(link.getUrl()))
            secure.setText(R.string.secured);
        else if(URLUtil.isHttpUrl(link.getUrl()))
            secure.setText(R.string.not_secured);

    }

}