package com.ona.linkapp.datas.online;

import android.net.Uri;

import com.ona.linkapp.apiUtils.Globals;
import com.ona.linkapp.apiUtils.Repository;

import java.io.IOException;

public class ShortenDAO {

    private Repository repository;

    public ShortenDAO(){

        repository = new Repository();

    }

    public String getShorts() throws IOException {

        String shortsJson = repository.get(Globals.GET_SHORT_URL);

        return shortsJson;

    }

    public String addShort(String mShort, String linkId) throws IOException {

        String shortJson = repository.post(Uri.parse(Globals.POST_SHORT_URL).buildUpon().appendPath(linkId).toString(), mShort);

        return shortJson;

    }

}
