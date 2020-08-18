package com.ona.linkapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ona.linkapp.R;
import com.ona.linkapp.datas.online.CollectionDAO;
import com.ona.linkapp.helpers.Session;
import com.ona.linkapp.main.MainActivity;
import com.ona.linkapp.main.activities.CollectionDetailActivity;
import com.ona.linkapp.models.Collection;
import com.ona.linkapp.models.Link;
import com.ona.linkapp.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddLinkToCollectionAdapter extends RecyclerView.Adapter<AddLinkToCollectionAdapter.ViewHolder> {

    private Context context;
    private List<Link> links;
    private  List<Link> links_helper;
    private Random random;
    private Collection collection;
    private CollectionDAO collectionDAO;
    private Session session;

    private final int[] RANDOM_LOGO = {
            R.drawable.ic_random_box1,
            R.drawable.ic_random_box2,
            R.drawable.ic_random_box3,
            R.drawable.ic_random_box4
    };

    public AddLinkToCollectionAdapter(Context context, List<Link> links, Collection collection) {
        this.context = context;
        this.links = links;
        random = new Random();
        this.links_helper = new ArrayList<>();
        this.links_helper.addAll(links);
        this.collection = collection;
        collectionDAO = new CollectionDAO();
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_item, parent, false);
        return new AddLinkToCollectionAdapter.ViewHolder(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView randomLogo;
        private TextView title;
        private TextView desc;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            randomLogo = itemView.findViewById(R.id.link_logo);
            title = itemView.findViewById(R.id.link_title);
            desc = itemView.findViewById(R.id.link_desc);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Link link = links.get(getAdapterPosition());
            new AddLinkToCol().execute(collection.getId(), link.getId());

        }
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        links.clear();
        if (charText.length() == 0) {
            links.addAll(links_helper);
        } else {
            for (Link link : links_helper) {
                if (link.getTitle().toLowerCase(Locale.getDefault()).contains(charText) || link.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    links.add(link);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class AddLinkToCol extends AsyncTask<String, Void, String>{

        private String id;
        private String linkId;

        @Override
        protected String doInBackground(String... strings) {

            id = strings[0];
            linkId = strings[1];

            try {
                String newCol = collectionDAO.addLinkToCol(id, linkId);
                return newCol;
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

                Log.d("value", s);

                try {
                    Collection newCol = mapper.readValue(s, Collection.class);
                    User oldUser = session.getUser();

                    for(Collection collection : oldUser.getCollections()){
                        if(collection.getId().equals(newCol.getId())){
                            int i = oldUser.getCollections().indexOf(collection);
                            if(i != -1)
                                oldUser.getCollections().set(i, newCol);
                        }
                    }

                    session.setUser(oldUser);
                    Intent colIntent = new Intent(context, MainActivity.class);
                    context.startActivity(colIntent);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
