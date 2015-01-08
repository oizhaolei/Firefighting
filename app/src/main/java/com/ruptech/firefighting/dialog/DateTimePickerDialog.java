package com.ruptech.firefighting.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.ruptech.firefighting.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimePickerDialog extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String title = null;
    private String value = null;
    private Calendar c = Calendar.getInstance();
    private OnChangeListener onChangeListener = null;

    public static DateTimePickerDialog newInstance(String title, String value, OnChangeListener dismissListener) {
        DateTimePickerDialog editTextDialog = new DateTimePickerDialog();
        editTextDialog.setTitle(title);
        editTextDialog.setValue(value);
        editTextDialog.setOnChangeListener(dismissListener);
        return editTextDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        View v = inflater.inflate(R.layout.dialog_datepicker, (ViewGroup) ((Activity) context).findViewById(R.id.dialog_datepicker));
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        datePicker.updateDate(getYear(), getMonth(), getDayOfMonth());
        final TimePicker timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        timePicker.setCurrentHour(getHour());
        timePicker.setCurrentMinute(getMinute());

        builder.setView(v);

        builder.setPositiveButton(context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                        String newValue = sdf.format(c.getTime());
                        onChangeListener.onChange(value, newValue);
                    }
                });
        builder.setNegativeButton(context.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        dismiss();
                    }
                });
        Dialog dialog = builder.create();
        return dialog;
    }

    private int getYear() {
        return c.get(Calendar.YEAR);
    }

    private int getMonth() {
        return c.get(Calendar.MONTH);
    }

    private int getDayOfMonth() {
        return c.get(Calendar.DAY_OF_MONTH);
    }

    private int getHour() {
        return c.get(Calendar.HOUR);
    }

    private int getMinute() {
        return c.get(Calendar.MINUTE);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void setValue(String value) {
        this.value = value;
        Date d;

        try {
            d = sdf.parse(value);
        } catch (ParseException e) {
            d = new Date();
        }
        this.c.setTime(d);
    }


    public void setTitle(String title) {
        this.title = title;
    }

}
