package com.ona.linkapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import com.ona.linkapp.R;
import com.ona.linkapp.adapters.CollectionAdapter;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;

import java.util.ArrayList;
import java.util.List;

public class AllCollActivity extends AppCompatActivity {

    private ImageButton back;
    private RecyclerView recyclerView;
    private CollectionAdapter collAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_coll);

        updateUi();

    }

    private void updateUi() {

        back = (ImageButton) findViewById(R.id.back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCollActivity.this.onBackPressed();
            }
        });

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(AllCollActivity.this, R.anim.layout_animation_fall_down);
        collAdapter = new CollectionAdapter(createFakeCollection(), AllCollActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.all_coll_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(AllCollActivity.this,2, LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(collAdapter);


    }

    public List<Collection> createFakeCollection(){

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