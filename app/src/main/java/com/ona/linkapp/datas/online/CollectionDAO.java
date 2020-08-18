package com.ona.linkapp.datas.online;

import android.net.Uri;

import com.ona.linkapp.apiUtils.Globals;
import com.ona.linkapp.apiUtils.Repository;

import java.io.IOException;

public class CollectionDAO {

    private Repository repository;

    public CollectionDAO(){

        repository = new Repository();

    }


    public String getCols() throws IOException {

        String colsJson = repository.get(Globals.GET_COLLECTIONS_URL);

        return colsJson;

    }

    public String addCol(String collection) throws IOException {

        String colJson = repository.post(Globals.POST_COLLECTIONS_URL, collection);

        return colJson;

    }

    public String addLinkToCol(String id, String linkId) throws IOException {

        String colJson = repository.post(Uri.parse(Globals.GET_COLLECTIONS_URL).buildUpon().appendPath(id).appendPath(linkId).toString(), "");

        return colJson;

    }
}
