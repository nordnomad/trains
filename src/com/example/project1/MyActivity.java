package com.example.project1;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.UUID;

public class MyActivity extends Activity {

    AutoCompleteTextView cityFrom;
    AutoCompleteTextView cityTo;
    WebView webView;
    String token;

    int stationFromId;
    int stationTillId;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cityFrom = (AutoCompleteTextView) findViewById(R.id.city_from);
        CitiesAutoCompleteAdapter adapterFrom = new CitiesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        cityFrom.setAdapter(adapterFrom);

        cityTo = (AutoCompleteTextView) findViewById(R.id.city_to);
        CitiesAutoCompleteAdapter adapterTo = new CitiesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        cityTo.setAdapter(adapterTo);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Intel Mac OS X 10.6; rv:7.0.1) Gecko/20100101 Firefox/7.0.1");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                webView.loadUrl("javascript:(function() { " +
                        "console.log(window.localStorage.getItem('gv-token')) " +
                        "})()");
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cm) {
                token = cm.message();
                Toast.makeText(MyActivity.this, cm.message(), 1).show();
                return true;
            }
        });
//        ServerConnector.load();
        ServerConnector.sendRequest("http://booking.uz.gov.ua/ru/purchase/search/", "");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void searchTrains(View v) {

    }
}
