package com.example.project1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import static android.app.DatePickerDialog.OnDateSetListener;

/**
 * Created with IntelliJ IDEA.
 * User: COMFY
 * Date: 7/14/13
 * Time: 8:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerFragment and return it
        return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
