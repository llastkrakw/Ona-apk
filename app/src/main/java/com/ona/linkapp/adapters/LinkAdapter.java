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
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.UrlElement;

import java.util.List;
import java.util.Random;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {

    private  Context context;
    private  List<Link> links;
    private  Random random;

    private final int[] RANDOM_LOGO = {
            R.drawable.ic_random_box1,
            R.drawable.ic_random_box2,
            R.drawable.ic_random_box3,
            R.drawable.ic_random_box4
    };

    public LinkAdapter(Context context, List<Link> links) {
        this.context = context;
        this.links = links;
        random = new Random();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Link link =  links.get(position);
        int next = random.nextInt((3));
        int logo = RANDOM_LOGO[next];

        holder.randomLogo.setImageResource(logo);
        holder.title.setText(link.getTitle());
        holder.desc.setText(link.getDescription());

    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView randomLogo;
        private TextView title;
        private TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            randomLogo = itemView.findViewById(R.id.link_logo);
            title = itemView.findViewById(R.id.link_title);
            desc = itemView.findViewById(R.id.link_desc);

        }
    }
}
