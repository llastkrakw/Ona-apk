package com.ona.linkapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ona.linkapp.R;
import com.ona.linkapp.models.Collection;

import java.util.List;
import java.util.Random;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private List<Collection> collections;
    private Context context;
    private Random random;

    private final int[] RANDOM_LOGO = {
            R.drawable.ic_random_box1,
            R.drawable.ic_random_box2,
            R.drawable.ic_random_box3,
            R.drawable.ic_random_box4
    };

    public CollectionAdapter(List<Collection> collections, Context context) {
        this.collections = collections;
        this.context = context;
        random = new Random();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Collection collection = collections.get(position);
        int next = random.nextInt((3));
        int logo = RANDOM_LOGO[next];

        holder.collLogo.setImageResource(logo);
        holder.collTitle.setText(collection.getTitle());
        String value = "Links " + collection.getLinks().size();
        holder.collLinkNum.setText(value);

    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView collLogo;
        private TextView  collTitle;
        private TextView  collLinkNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            collLogo = (ImageView) itemView.findViewById(R.id.coll_logo);
            collTitle = (TextView) itemView.findViewById(R.id.coll_title);
            collLinkNum = (TextView) itemView.findViewById(R.id.coll_link_numb);

        }
    }
}
