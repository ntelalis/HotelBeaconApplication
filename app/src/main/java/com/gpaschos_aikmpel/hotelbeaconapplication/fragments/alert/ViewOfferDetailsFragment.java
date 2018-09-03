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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = View.inflate(getContext(), R.layout.fragment_exclusive_offer_description, null);
        //View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_exclusive_offer_description, false);

        // TextView tvDescription = view.findViewById(R.id.tvExclusiveOfferDescription);
        Log.d("OnCreateDialog/TITLE ", TITLE);
        //tvDescription.setText(getArguments().getString(DESCRIPTION));
        //builder.setMessage(getArguments().getString(DESCRIPTION));
        //builder.setMessage("The tradition of afternoon tea began in the nineteenth century, created by the Duchess of Bedford for a mainly female audience. Our Gentleman’s afternoon tea draws on the traditions of the club house and appeals to those with a love for tea with less of sweet tooth. A selection of sandwiches accompany a Goosnargh duck Scotch egg, Gentleman’s relish &amp; toast, sausage roll and Lancashire bomb rarebit &amp; English crumpet followed by cheese scones and Eccles cakes.");
        // builder.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);
        builder.setTitle(getArguments().getString(TITLE));
        builder.setPositiveButton("OK",null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onResume() {

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        window.setLayout((int)(size.x*0.85), (int)(size.y*0.5));
        //window.setLayout((int)(size.y*0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        super.onResume();
    }
}
