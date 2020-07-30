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
import com.ona.linkapp.models.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> groups;
    private List<Group> groups_helper;
    private Context context;
    private Random random;

    private final int[] RANDOM_LOGO = {
            R.drawable.ic_random_box1,
            R.drawable.ic_random_box2,
            R.drawable.ic_random_box3,
            R.drawable.ic_random_box4
    };

    public GroupAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
        random = new Random();

        this.groups_helper = new ArrayList<>();
        this.groups_helper.addAll(groups);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Group group = groups.get(position);
        int next = random.nextInt((3));
        int logo = RANDOM_LOGO[next];

        holder.group_logo.setImageResource(logo);
        holder.group_name.setText(group.getTitle());
        holder.group_nb_share.setText(String.valueOf(group.getLinks().size() + group.getCollections().size()));

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView group_logo;
        private TextView  group_name;
        private TextView  group_nb_share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            group_logo = (ImageView) itemView.findViewById(R.id.group_logo);
            group_name = (TextView) itemView.findViewById(R.id.group_name);
            group_nb_share = (TextView) itemView.findViewById(R.id.group_nb_share);
        }
    }

    public void removeItem(int position) {
        groups.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Group item, int position) {
        groups.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Group> getData() {
        return new ArrayList<>(groups);
    }
}
