package com.example.project1;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.project1.IHeaders.*;
import static com.example.project1.RestURL.SEARCH;

public class ServerConnector {
    private static final String TAG = ServerConnector.class.getSimpleName();
    private static final String GV_AJAX_VAL = "1";
    private static final String GV_REFERER_VAL = "http://booking.uz.gov.ua/ru/";

    public static JSONObject sendRequest(String url, String... params) {
        url = parametrizeUrl(url, params);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

//        BasicHttpParams httpParams = new BasicHttpParams();
//        httpParams.setLongParameter("station_id_from", 2214000);
//        httpParams.setLongParameter("station_id_till", 2204001);
//        httpParams.setParameter("date_dep", "01.08.2013");
//        httpParams.setParameter("time_dep", "00:00");
//
//        httpPost.setParams(httpParams);
//
//        httpPost.addHeader("GV-Ajax", "1");
//        httpPost.addHeader("GV-Referer", "http://booking.uz.gov.ua/ru/");
//        httpPost.addHeader("GV-Token", "f5a4f587d84e839024385fc65c6c5536");
//        httpPost.addHeader("Cookie", "_gv_sessid=57b0kvfig7va2ct6m0k9ojnqa0; _gv_lang=ru; HTTPSERVERID=server1; __utma=31515437.351867492.1374274510.1374274510.1374309850.2; __utmb=31515437.2.10.1374309850; __utmc=31515437; __utmz=31515437.1374274510.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");

        InputStream is = null;
        BufferedReader br = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            try {
                assert br != null;
                br.close();
                is.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return null;
    }

    private static String parametrizeUrl(String url, String... params) {
        return String.format(url, params);
    }

    public static List<Station> listCities(String cityNamePart) {
        JSONObject jsonCity = sendRequest(RestURL.CITIES, cityNamePart);
        List<Station> result = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonCity.getJSONArray("value");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Station station = new Station();
                station.name = jsonObject.getString("title");
                station.id = jsonObject.getInt("station_id");
                result.add(station);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return result;
    }

    public static List<String> searchTrains(long fromId, long tillId, String dateDep, String timeDep, String gvToken, String cookie) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(SEARCH);

        post.addHeader(GV_AJAX, GV_AJAX_VAL);
        post.addHeader(GV_REFERER, GV_REFERER_VAL);
        post.addHeader(GV_TOKEN, gvToken);
        post.addHeader(COOKIE, cookie);

        BasicHttpParams params = new BasicHttpParams();
        params.setLongParameter(STATION_ID_FROM, fromId);
        params.setLongParameter(STATION_ID_TILL, tillId);
        params.setParameter(DATE_DEP, dateDep);
        params.setParameter(TIME_DEP, timeDep);

        post.setParams(params);

        InputStream is = null;
        BufferedReader br = null;
        try {
            HttpResponse response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();
            is = httpEntity.getContent();
            br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
//            return new JSONObject(sb.toString());
        } catch (Exception /*| JSONException*/ e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            try {
                assert br != null;
                br.close();
                is.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return null;
    }
}
