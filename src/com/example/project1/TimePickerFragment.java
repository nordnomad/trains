package com.example.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
    NoticeDialogListener mListener;
    TimePicker timePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timePicker = new TimePicker(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(timePicker)
                .setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(TimePickerFragment.this);
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(TimePickerFragment.this);
            }
        });
        return builder.create();
    }

    public String getTime() {
        return String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
