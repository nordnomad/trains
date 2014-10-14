package com.example.project1;

import android.util.Log;
import com.example.project1.entities.Station;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.project1.IHeaders.*;
import static com.example.project1.JsonHelper.parseStations;
import static com.example.project1.RestURL.CITIES;
import static com.example.project1.RestURL.SEARCH;

public class ServerConnector {
    private static final String TAG = ServerConnector.class.getSimpleName();
    private static final String GV_AJAX_VAL = "1";
    private static final String GV_REFERER_VAL = "http://booking.uz.gov.ua/ru/";

    public static JSONObject sendRequest(String url, String... params) {
        url = parametrizeUrl(url, params);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

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
        JSONObject jsonResponse = sendRequest(CITIES, cityNamePart);
        List<Station> result = new ArrayList<>();
        try {
            result = parseStations(jsonResponse);
        } catch (JSONException | JSONResponseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return result;
    }

    public static List<String> searchTrains(long fromId, long tillId, String dateDep, String timeDep, String gvToken, String cookie) throws UnsupportedEncodingException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(SEARCH);

        post.addHeader(GV_AJAX, GV_AJAX_VAL);
        post.addHeader(GV_REFERER, GV_REFERER_VAL);
        post.addHeader(GV_TOKEN, gvToken);
        post.addHeader(COOKIE, cookie);
        post.addHeader("GV-Unique-Host", "1");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair(STATION_FROM_ID, "2204001"));
        urlParameters.add(new BasicNameValuePair(STATION_TILL_ID, "2200001"));
        urlParameters.add(new BasicNameValuePair(TIME_DEP, "00:00"));
        urlParameters.add(new BasicNameValuePair(DATE_DEP, "25.10.2014"));
        urlParameters.add(new BasicNameValuePair("Search", ""));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
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
            List<String> result = new ArrayList<>();
            JSONObject jsonResponse = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonResponse.getJSONArray("value");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                result.add(jsonObject.toString());
            }
            return result;
        } catch (Exception e) {
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
