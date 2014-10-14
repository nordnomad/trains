package com.example.project1;

import com.example.project1.entities.Station;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    final static String ERROR = "error";
    final static String VALUE = "value";
    final static String STATION_ID = "station_id";
    final static String TITLE = "title";

    static boolean noError(JSONObject object) throws JSONException, JSONResponseException {
        if (!object.optBoolean(ERROR)) {
            return true;
        } else {
            throw new JSONResponseException(object.getString(VALUE));
        }
    }

    static Station parseStation(JSONObject object) throws JSONException {
        Station station = new Station();
        station.id = object.getInt(STATION_ID);
        station.name = object.getString(TITLE);
        return station;
    }

    public static List<Station> parseStations(JSONObject object) throws JSONException, JSONResponseException {
        List<Station> stations = new ArrayList<>();
        if (noError(object)) {
            JSONArray array = object.getJSONArray(VALUE);
            for (int i = 0; i < array.length(); i++) {
                stations.add(parseStation(array.getJSONObject(i)));
            }
        }
        return stations;
    }
}
