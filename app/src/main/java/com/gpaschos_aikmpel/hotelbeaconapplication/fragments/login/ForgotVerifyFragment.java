package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotVerifyFragment extends Fragment implements JsonListener {

    private static final String email_KEY = "email";

    private String email;
    private String code;

    private TextInputEditText tietVerify;
    private Button btnVerify;

    private LoginCallbacks listener;

    public ForgotVerifyFragment() {
        // Required empty public constructor
    }


    public static ForgotVerifyFragment newInstance(String email) {

        ForgotVerifyFragment fragment = new ForgotVerifyFragment();
        Bundle args = new Bundle();
        args.putString(email_KEY, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.email = getArguments().getString(email_KEY);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginCallbacks) {
            listener = (LoginCallbacks) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnForgotFinished");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_forgot_verify, container, false);
        tietVerify = v.findViewById(R.id.tietForgotVerifyCode);
        btnVerify = v.findViewById(R.id.btnForgotVerifyConfirm);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = tietVerify.getText().toString().trim();

                if (!code.isEmpty()) {
                    Map<String, String> params = new HashMap<>();

                    params.put(POST.forgotVerifyEmail, email);
                    params.put(POST.forgotVerifyVerification, code);

                    VolleyQueue.getInstance(getContext()).jsonRequest(ForgotVerifyFragment.this, URL.forgotVerifyUrl, params);
                } else {
                    Toast.makeText(getContext(), "Please enter the verification code", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return v;
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        listener.finishedVerify(email, code);
    }


    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}
