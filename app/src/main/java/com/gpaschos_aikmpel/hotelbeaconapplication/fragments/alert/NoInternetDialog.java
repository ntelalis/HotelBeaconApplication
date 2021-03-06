package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Objects;

public class NoInternetDialog extends DialogFragment {

    public static final String TAG = NoInternetDialog.class.getSimpleName();
    private NoInternetDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoInternetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(listener.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("No internet access")
                .setMessage("An active connection to the internet is required")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(NoInternetDialog.this);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(NoInternetDialog.this);
                    }
                });
        return builder.create();
    }

    public NoInternetDialog() {
    }

    public static NoInternetDialog newInstance() {
        NoInternetDialog fragment = new NoInternetDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface NoInternetDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

}
