package com.ona.linkapp.datas.online;

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


}
