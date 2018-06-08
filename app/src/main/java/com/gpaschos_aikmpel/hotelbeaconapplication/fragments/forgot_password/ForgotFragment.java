package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotFragment extends Fragment implements JsonListener {


    private ForgotCallbacks listener;

    private TextInputEditText tietEmail;
    private Button btnReset;

    public ForgotFragment() {
        // Required empty public constructor
    }

    public static ForgotFragment newInstance() {

        Bundle args = new Bundle();

        ForgotFragment fragment = new ForgotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ForgotCallbacks) {
            listener = (ForgotCallbacks) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnForgotFinished");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forgot, container, false);
        tietEmail = v.findViewById(R.id.tietForgotEmail);
        btnReset = v.findViewById(R.id.btnForgotReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = tietEmail.getText().toString().trim();

                Map<String, String> params = new HashMap<>();

                params.put(POST.forgotEmail, email);

                VolleyQueue.getInstance(getContext()).jsonRequest(ForgotFragment.this, URL.forgotUrl, params);

            }
        });
        return v;
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        String email = tietEmail.getText().toString().trim();
        listener.finishedForgot(email);
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }

}
