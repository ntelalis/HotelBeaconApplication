package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password;


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
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
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
public class ForgotNewPasswordFragment extends Fragment implements JsonListener {

    public static final String email_KEY = "email";
    public static final String code_KEY = "code";

    private TextInputEditText tietPass;
    private TextInputEditText tietPassVerify;

    private String code;
    private String email;

    private ForgotCallbacks listener;

    public ForgotNewPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotNewPasswordFragment newInstance(String email, String code) {

        ForgotNewPasswordFragment fragment = new ForgotNewPasswordFragment();
        Bundle args = new Bundle();
        args.putString(email_KEY, email);
        args.putString(code_KEY, code);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getString(email_KEY);
            email = getArguments().getString(code_KEY);
        }
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
        View v = inflater.inflate(R.layout.fragment_forgot_new_password, container, false);

        tietPass = v.findViewById(R.id.tietForgotNewPassPassword);
        tietPassVerify = v.findViewById(R.id.tietForgotNewPassConfirm);
        Button btnConfirm = v.findViewById(R.id.btnForgotNewPassConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = tietPass.getText().toString();
                String passVerify = tietPassVerify.getText().toString();

                if (Validation.checkPassword(pass, 6, Validation.PasswordType.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)) {
                    if (pass.equals(passVerify)) {
                        Map<String, String> params = new HashMap<>();

                        params.put(POST.forgotNewPassEmail, email);
                        params.put(POST.forgotNewPassPassword, pass);
                        params.put(POST.forgotNewPassVerification, code);

                        VolleyQueue.getInstance(getContext()).jsonRequest(ForgotNewPasswordFragment.this, URL.forgotNewPassUrl, params);
                    } else {
                        Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Please enter a valid password", Toast.LENGTH_SHORT).show();
                    tietPass.setError("Please enter a valid password");
                }
            }
        });
        return v;
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        listener.finishedNewPassword();
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}


