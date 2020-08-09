package com.ona.linkapp.datas.online;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.apiUtils.Globals;
import com.ona.linkapp.apiUtils.Repository;
import com.ona.linkapp.models.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LinkDAO {

    private Repository repository;

    public LinkDAO(){

        repository = new Repository();

    }


    public String getLinks() throws IOException {

        String linksJson = repository.get(Globals.GET_LINKS_URL);

        return linksJson;

    }

}
