package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class ViewOfferDetailsFragment extends DialogFragment {

    private static final String DESCRIPTION = "description";
    private static final String TITLE = "title";
    public static final String TAG = "Offer description";

    public ViewOfferDetailsFragment() {

    }

    public static ViewOfferDetailsFragment newInstance(String description, String title) {

        Bundle args = new Bundle();
        args.putString(DESCRIPTION, description);
        args.putString(TITLE, title);
        //Log.d("-InDialogFragment-title", title);
        ViewOfferDetailsFragment fragment = new ViewOfferDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Dialog_Alert);

        //View view = View.inflate(getContext(), R.layout.fragment_exclusive_offer_description, null);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_exclusive_offer_description, null);

       // TextView tvDescription = view.findViewById(R.id.tvExclusiveOfferDescription);
        Log.d("OnCreateDialog/TITLE ", TITLE);
        //tvDescription.setText(getArguments().getString(DESCRIPTION));
        builder.setMessage(getArguments().getString(DESCRIPTION));
        // builder.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);
        builder.setTitle(getArguments().getString(TITLE));
        //builder.setView(view);

        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onResume() {

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

       // window.setLayout((int)(size.x*0.85), WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setLayout((int)(size.y*0.75), WindowManager.LayoutParams.WRAP_CONTENT);
       // window.setGravity(Gravity.CENTER);

        super.onResume();
    }
}
