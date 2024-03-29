package com.example.project1;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import com.example.project1.entities.Station;

import java.util.List;

import static com.example.project1.ServerConnector.listCities;

public class StationsAutoCompleteAdapter extends ArrayAdapter<Station> implements Filterable {
    private List<Station> stations;

    public StationsAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocompleter results.
                    stations = listCities(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = stations;
                    filterResults.count = stations.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Station getItem(int index) {
        return stations.get(index);
    }
}
