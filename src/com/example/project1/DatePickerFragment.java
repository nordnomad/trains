package com.example.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import static android.app.DatePickerDialog.OnClickListener;

public class DatePickerFragment extends DialogFragment {

    NoticeDialogListener mListener;
    DatePicker datePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        datePicker = new DatePicker(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(datePicker)
                .setPositiveButton("Готово", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(DatePickerFragment.this);
                    }
                }).setNegativeButton("Отмена", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(DatePickerFragment.this);
            }
        });
        return builder.create();
    }

    public String getDate() {
        return String.format("%02d.%02d.%d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());
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
