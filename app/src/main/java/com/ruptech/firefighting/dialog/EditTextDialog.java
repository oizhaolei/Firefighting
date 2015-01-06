package com.ruptech.firefighting.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ruptech.firefighting.R;


public class EditTextDialog extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private int inputType = InputType.TYPE_CLASS_TEXT;
    private String title = null;
    private String value = null;
    private OnChangeListener onChangeListener = null;

    public static EditTextDialog newInstance(String title, String value, OnChangeListener dismissListener) {
        EditTextDialog editTextDialog = new EditTextDialog();
        editTextDialog.setTitle(title);
        editTextDialog.setValue(value);
        editTextDialog.setOnChangeListener(dismissListener);
        return editTextDialog;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        View v = inflater.inflate(R.layout.fragment_dialog_edittext, (ViewGroup) ((Activity) context).findViewById(R.id.dialog_edittext));
        final EditText editText = (EditText) v.findViewById(R.id.dialog_edittext_edittext);
        editText.setText(value);
        editText.setInputType(inputType);

        builder.setView(v);

        builder.setPositiveButton(context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        String newValue = editText.getText().toString();
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

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public void setTitle(String title) {
        this.title = title;
    }

}
