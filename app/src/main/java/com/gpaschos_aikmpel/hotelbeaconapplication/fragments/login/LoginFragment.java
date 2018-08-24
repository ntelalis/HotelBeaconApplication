package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment implements JsonListener {

    private EditText etEmail;
    private EditText etPass;

    private LoginCallbacks listener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = v.findViewById(R.id.etLoginEmail);
        etPass = v.findViewById(R.id.etLoginPassword);
        Button btnLogin = v.findViewById(R.id.btnLoginLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                Map<String, String> params = new HashMap<>();

                params.put(POST.loginEmail, email);
                params.put(POST.loginPassword, pass);

                VolleyQueue.getInstance(getContext()).jsonRequest(LoginFragment.this, URL.loginUrl, params);

            }
        });
        Button btnRegister = v.findViewById(R.id.btnLoginRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.register();
            }
        });
        Button btnForgot = v.findViewById(R.id.btnLoginForgot);
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showForgot();
            }
        });
        return v;
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.loginUrl)) {

            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            int customerId = json.getInt(POST.loginCustomerID);
            String title = JSONHelper.getString(json,POST.loginTitle);
            String firstName = JSONHelper.getString(json,POST.loginFirstName);
            String lastName = JSONHelper.getString(json,POST.loginLastName);
            String birthDate = JSONHelper.getString(json,POST.loginBirthDate);
            String country = JSONHelper.getString(json,POST.loginCountry);
            String phone = JSONHelper.getString(json,POST.loginPhone);
            String address1 = JSONHelper.getString(json,POST.loginAddress1);
            String address2 = JSONHelper.getString(json,POST.loginAddress2);
            String city = JSONHelper.getString(json,POST.loginCity);
            String postalCode = JSONHelper.getString(json,POST.loginPostalCode);
            boolean oldCustomer = json.getBoolean(POST.loginOldCustomer);
            String modified = JSONHelper.getString(json,POST.loginModified);

            //TODO Implement OAUTH2 Token Maybe??

            Customer customer = new Customer(customerId, email, password, title, firstName, lastName, birthDate, country, phone, address1, address2, city, postalCode, oldCustomer, modified);
            RoomDB.getInstance(getContext()).customerDao().insert(customer);

            if (getContext() != null)
                LocalVariables.storeBoolean(getContext(), R.string.is_old_customer, oldCustomer);

            listener.login();
        }

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}