package com.example.project1;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.example.project1.ServerConnector.searchTrains;

public class TrainsListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<String>> {

    long fromId;
    long tillId;
    String dateDep;
    String timeDep;
    String gvToken;
    String cookie;

    public TrainsListFragment(long fromId, long tillId, String dateDep, String timeDep, String gvToken, String cookie) {
        this.fromId = fromId;
        this.tillId = tillId;
        this.dateDep = dateDep;
        this.timeDep = timeDep;
        this.gvToken = gvToken;
        this.cookie = cookie;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        getLoaderManager().initLoader(0, null, this).forceLoad();
        return inflater.inflate(R.layout.frag, null);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<String>>(getActivity()) {
            @Override
            public List<String> loadInBackground() {
                try {
                    return searchTrains(fromId, tillId, dateDep, timeDep, gvToken, cookie);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data));
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
    }
}
