package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.databinding.ActivityCollectionDetailBinding;
import com.ona.linkapp.helpers.ImageResize;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.helpers.SwipCallback;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CollectionDetailActivity extends AppCompatActivity {

    private CircularImageView circularImageView;
    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private ImageButton back;
    private ImageButton copy;
    private ImageButton share;
    private FloatingActionButton fab;
    private Collection collection;
    private FloatingActionButton qrCode;

    private User user = null;
    private Session session;

    private ClipboardManager cm;
    private ClipData pData;

    private TextView username;
    private EditText link_value;

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


        Picasso.get().
                load((Uri.parse("https://unavatar.now.sh/").buildUpon().appendPath(user.getEmail()).toString()))
                .into(circularImageView);


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
                Intent newLink = new Intent(CollectionDetailActivity.this, AddLinkToCollectionActivity.class);
                newLink.putExtra("Collection", collection);
                startActivity(newLink);
            }
        });

        qrCode = (FloatingActionButton) findViewById(R.id.fab_qr);

        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qrIntent = new Intent(CollectionDetailActivity.this, QrCodeActivity.class);
                qrIntent.putExtra("Collection", collection);
                startActivity(qrIntent);
            }
        });

        username = (TextView) findViewById(R.id.username);
        username.setText(user.getUsername());

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
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared with ona");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, collection.getUrl());
                    startActivity(Intent.createChooser(shareIntent, "Send towards"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });


    }

}