package com.ruptech.firefighting.dialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruptech.firefighting.R;
/**
 * Created by ls_gao on 2015/2/26.
 */
public class ComfirmDialog extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private String title = null;
    private String message = null;
    private OnClickListener onClickPositiveListener = null;

    public static ComfirmDialog newInstance(String title, String message, OnClickListener positiveBtnListener) {
        ComfirmDialog comfirmDialog = new ComfirmDialog();
        comfirmDialog.setTitle(title);
        comfirmDialog.setMessage(message);
        comfirmDialog.setOnClickPositiveListener(positiveBtnListener);
        return comfirmDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        onClickPositiveListener.onClick(dialog, whichButton);

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

    public void setOnClickPositiveListener(OnClickListener onClickListener) {
        this.onClickPositiveListener = onClickListener;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setTitle(String title) {
        this.title = title;
    }

}
