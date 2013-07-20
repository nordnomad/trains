package com.example.project1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.app.TimePickerDialog.OnTimeSetListener;

/**
 * Created with IntelliJ IDEA.
 * User: COMFY
 * Date: 7/14/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {
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
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
