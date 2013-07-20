package com.example.project1;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.List;

public class TrainsListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<String>> {

    private final long fromId;
    private final long tillId;
    private final String dateDep;
    private final String timeDep;
    private final String gvToken;
    private final String cookie;

    public TrainsListFragment(long fromId, long tillId, String dateDep, String timeDep, String gvToken, String cookie) {
        this.fromId = fromId;
        this.tillId = tillId;
        this.dateDep = dateDep;
        this.timeDep = timeDep;
        this.gvToken = gvToken;
        this.cookie = cookie;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        //getLoaderManager().initLoader(0, null, this);
        setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new String[]{"1","2"}));
        return inflater.inflate(R.layout.frag, null);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return new TrainsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
