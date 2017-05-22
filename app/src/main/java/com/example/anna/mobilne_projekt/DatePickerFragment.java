package com.example.anna.mobilne_projekt;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;

    private static int flag = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker



        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH) ;
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month++, day);



    }

    public static void setFlag(int i) {
        flag = i;
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {

        {
            EditText editText;
            if (flag == FLAG_START_DATE) {
                editText = (EditText) getActivity().findViewById(R.id.arDateEditText);
            } else {
                editText = (EditText) getActivity().findViewById(R.id.depDateEditText);
            }
            month++;
            view.getId();
            String monthString = String.valueOf(month);
            if (monthString.length()<2) monthString = "0" + monthString;

            String dayString = String.valueOf(day);
            if (dayString.length()<2) dayString = "0" + dayString;

            editText.setText(year + "-" + monthString + "-" + dayString);


        }

    }
}