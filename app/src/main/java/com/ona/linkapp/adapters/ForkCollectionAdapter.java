package com.ona.linkapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.R;
import com.ona.linkapp.datas.online.CollectionDAO;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ForkCollectionAdapter extends RecyclerView.Adapter<ForkCollectionAdapter.ViewHolder> {

    private List<Collection> collections;
    private List<Collection> collections_helper;
    private Context context;
    private Random random;
    private Session session;
    private CollectionDAO collectionDAO;

    private final int[] RANDOM_LOGO = {
            R.drawable.ic_random_box1,
            R.drawable.ic_random_box2,
            R.drawable.ic_random_box3,
            R.drawable.ic_random_box4
    };

    public  ForkCollectionAdapter(List<Collection> collections, Context context){
        this.collections = collections;
        this.context = context;
        random = new Random();
        this.collections_helper = new ArrayList<>();
        this.collections_helper.addAll(collections);
        session = new Session(context);
        collectionDAO = new CollectionDAO();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new ForkCollectionAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Collection collection = collections.get(position);
        int next = random.nextInt((3));
        int logo = RANDOM_LOGO[next];

        holder.collLogo.setImageResource(logo);
        holder.collTitle.setText(collection.getTitle());
        if(collection.getLinks() != null){
            String value = "Links " + collection.getLinks().size();
            holder.collLinkNum.setText(value);
        }

    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public void removeItem(int position) {
        collections.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Collection item, int position) {
        collections.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Collection> getData() {
        return new ArrayList<>(collections);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView collLogo;
        private TextView collTitle;
        private TextView  collLinkNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            collLogo = (ImageView) itemView.findViewById(R.id.coll_logo);
            collTitle = (TextView) itemView.findViewById(R.id.coll_title);
            collLinkNum = (TextView) itemView.findViewById(R.id.coll_link_numb);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            try {
                new ForkTask().execute(collections.get(getAdapterPosition()).getId(), session.getUser().getId());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        collections.clear();
        if (charText.length() == 0) {
            collections.addAll(collections_helper);
        } else {
            for (Collection collection : collections_helper) {
                if (collection.getTitle().toLowerCase(Locale.getDefault()).contains(charText) || collection.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    collections.add(collection);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ForkTask extends AsyncTask<String, Void, String>{

        private String colId;
        private String userId;
        @Override
        protected String doInBackground(String... strings) {

            colId = strings[0];
            userId = strings[1];


            try {
                String userJson = collectionDAO.forkCol(colId, userId);
                return userJson;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){

                ObjectMapper mapper = new ObjectMapper();

                try {
                    User user = mapper.readValue(s, User.class);
                    session.setUser(user);
                    Intent main = new Intent(context, MainActivity.class);
                    context.startActivity(main);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
