package com.ona.linkapp.datas.online;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.apiUtils.Globals;
import com.ona.linkapp.models.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LinkDAO {

    private static OkHttpClient client;

    public LinkDAO(){
        client = new OkHttpClient();
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public String getLinks() throws IOException {

        String linksJson = run(Globals.GET_LINKS_URL);

        return linksJson;

    }

}
