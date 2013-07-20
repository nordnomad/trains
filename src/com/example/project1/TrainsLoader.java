package com.example.project1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class TrainsLoader extends AsyncTaskLoader<List<String>> {

    private List<String> mData;

    public TrainsLoader(Context ctx) {
        super(ctx);
    }
    @Override
    public List<String> loadInBackground() {
//        ServerConnector.searchTrains();

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
