package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import com.ona.linkapp.R;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.adapters.LinkAdapter;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Link;

import java.util.ArrayList;
import java.util.List;

public class AllLinkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private ImageButton back;
    private CollectionAdapter collAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_link);

        updateUi();

    }

    private void updateUi(){

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

        linkAdapter = new LinkAdapter(AllLinkActivity.this, createFakeLink());
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