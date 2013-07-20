package com.example.project1;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import static android.R.layout.simple_dropdown_item_1line;
import static android.widget.AdapterView.OnItemClickListener;

public class MyActivity extends Activity implements NoticeDialogListener {

    AutoCompleteTextView stationFromView;
    AutoCompleteTextView stationTillView;
    Button dateButton;
    Button timeButton;
    WebView webView;
    int stationFromId;
    int stationTillId;
    String dateDep;
    String timeDep;
    String gvToken;
    String cookie;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        stationFromView = (AutoCompleteTextView) findViewById(R.id.city_from);
        CitiesAutoCompleteAdapter adapterFrom = new CitiesAutoCompleteAdapter(this, simple_dropdown_item_1line);
        stationFromView.setAdapter(adapterFrom);
        stationFromView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationFromId = ((Station) parent.getAdapter().getItem(position)).id;
            }
        });

        stationTillView = (AutoCompleteTextView) findViewById(R.id.city_to);
        CitiesAutoCompleteAdapter adapterTo = new CitiesAutoCompleteAdapter(this, simple_dropdown_item_1line);
        stationTillView.setAdapter(adapterTo);
        stationTillView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationTillId = ((Station) parent.getAdapter().getItem(position)).id;
            }
        });
        dateButton = (Button) findViewById(R.id.date);
        timeButton = (Button) findViewById(R.id.time);

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
                gvToken = cm.message();
                return true;
            }
        });
        webView.loadUrl("http://booking.uz.gov.ua/ru/");

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookie = cookieManager.getCookie("http://booking.uz.gov.ua/ru/");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");

    }

    public void showTimePickerDialog(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void searchTrains(View v) {
        TrainsListFragment trains = new TrainsListFragment(stationFromId, stationTillId, dateDep, timeDep, gvToken, cookie);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.frgmCont, trains, "trains");
        ft.commit();
     //   ServerConnector.searchTrains(stationFromId, stationTillId, dateDep, timeDep, gvToken, cookie);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        switch (dialog.getTag()) {
            case "datePicker":
                dateDep = ((DatePickerFragment) dialog).getDate();
                dateButton.setText(dateDep);
                break;
            case "timePicker":
                timeDep = ((TimePickerFragment) dialog).getTime();
                timeButton.setText(timeDep);
                break;
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
