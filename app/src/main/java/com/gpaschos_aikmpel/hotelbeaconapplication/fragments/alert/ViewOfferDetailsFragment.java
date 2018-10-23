package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
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
import android.widget.Button;
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
        ViewOfferDetailsFragment fragment = new ViewOfferDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //View view = View.inflate(getContext(), R.layout.fragment_exclusive_offer_description, null);

        //TextView tvDescription = view.findViewById(R.id.tvExclusiveOfferDescription);
        //Button btnOk = view.findViewById(R.id.btnDFOK);



        //TextView tvTitle =new TextView(getContext());
        //Log.d("OnCreateDialog/TITLE ", TITLE);
        if (getArguments() != null && getArguments().containsKey(DESCRIPTION) && getArguments().containsKey(TITLE)) {
            //tvDescription.setText(getArguments().getString(DESCRIPTION));
            builder.setTitle(getArguments().getString(TITLE));
            builder.setMessage(getArguments().getString(DESCRIPTION));
            builder.setPositiveButton("OK", null);
        }
        //tvTitle.setText(getArguments().getString(TITLE));
        //tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
        //builder.setMessage(getArguments().getString(DESCRIPTION));
        // builder.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);

        //builder.setCustomTitle(tvTitle);
        //builder.setPositiveButton("OK", null);

        //builder.setView(view);

        /*btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });*/

        return builder.create();
    }

    @Override
    public void onResume() {

        //Window window = getDialog().getWindow();
        //Point size = new Point();

        //Display display = window.getWindowManager().getDefaultDisplay();
        //display.getSize(size);

        //window.setLayout((int) (size.x * 0.97), (int) (size.x * 0.65));
        //window.setLayout((int)(size.y*0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setGravity(Gravity.CENTER);

        super.onResume();
    }
}
