package com.ona.linkapp.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ona.linkapp.R;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.databinding.ActivityMainBinding;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.helpers.SwipCallback;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class AllLinkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private ImageButton back;
    private TextView your_link_number;
    private TextView download_link_number;

    private int yn = 0;
    private int dn = 0;

    private User user = null;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_link);

        session = new Session(AllLinkActivity.this);
        try {
            user = session.getUser();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        updateUi();

    }

    private void updateUi(){

        your_link_number = (TextView) findViewById(R.id.your_link_number);
        download_link_number = (TextView) findViewById(R.id.download_link_number);

        for(Link link : user.getLinks()){

            if(link.getAuthor().equals(user.getId()))
                yn++;
            else
                dn++;

        }

        your_link_number.setText(String.valueOf(yn));
        download_link_number.setText(String.valueOf(dn));

        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllLinkActivity.this.onBackPressed();
            }
        });

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(AllLinkActivity.this, R.anim.layout_animation_fall_down);
        recyclerView = (RecyclerView) findViewById(R.id.all_link_recycler);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllLinkActivity.this));

        if(user != null)
             linkAdapter = new LinkAdapter(AllLinkActivity.this, user.getLinks());
        SwipCallback swipCallback = new SwipCallback(AllLinkActivity.this){

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

}