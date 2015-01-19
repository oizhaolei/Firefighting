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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ruptech.firefighting.R;

import java.util.Map;


public class ChoiceDialog extends DialogFragment {

    private final String TAG = getClass().getSimpleName();
    private Map<Integer, String> choices;
    private OnChangeListener onChangeListener = null;
    private String title = null;
    private int value;

    private int newValue;

    public static ChoiceDialog newInstance(String title, Map<Integer, String> choices, int value, OnChangeListener onChangeListener) {
        ChoiceDialog dialog = new ChoiceDialog();
        dialog.setTitle(title);
        dialog.setValue(value);
        dialog.setChoices(choices);
        dialog.setOnChangeListener(onChangeListener);
        return dialog;
    }

    public void setChoices(Map<Integer, String> choices) {
        this.choices = choices;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        View view = inflater.inflate(R.layout.dialog_radio, (ViewGroup) ((Activity) context).findViewById(R.id.dialog_singlechoice));
        final RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.dialog_radiogroup);

        for (int key : choices.keySet()) {
            String v = choices.get(key);
            RadioButton radio = new RadioButton(context);
            radio.setId(key);
            radio.setText(key + " : " + v);
            radio.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            radiogroup.addView(radio);

            if (key == value) {
                radio.setChecked(true);
            }
        }

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                newValue = checkedId;
            }
        });

        builder.setView(view);


        builder.setPositiveButton(context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        onChangeListener.onChange(Integer.valueOf(value).toString(), Integer.valueOf(newValue).toString());

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

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public void setTitle(String title) {
        this.title = title;
    }

}
