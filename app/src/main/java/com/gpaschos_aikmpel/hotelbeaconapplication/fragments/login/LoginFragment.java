package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login;


import android.content.Context;
import android.os.Bundle;
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
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.UpdateStoredVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment implements JsonListener, SyncServerData.SyncCallbacks{

    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin, btnRegister, btnForgot;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = v.findViewById(R.id.etLoginEmail);
        etPass = v.findViewById(R.id.etLoginPassword);
        btnLogin = v.findViewById(R.id.btnLoginLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                Map<String, String> params = new HashMap<>();

                params.put(POST.loginEmail, email);
                params.put(POST.loginPassword, pass);

                VolleyQueue.getInstance(getContext()).jsonRequest(LoginFragment.this, URL.loginUrl, params);

                //store notification variables and set them to false
                //TODO maybe fix this variable mess?
                UpdateStoredVariables.setDefaults(getContext());
            }
        });
        btnRegister = v.findViewById(R.id.btnLoginRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.register();
            }
        });
        btnForgot = v.findViewById(R.id.btnLoginForgot);
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

            int customerId = json.getInt(POST.loginCustomerID);
            String firstName = json.getString(POST.loginFirstName);
            int titleID = json.getInt(POST.loginTitleID);
            String lastName = json.getString(POST.loginLastName);
            boolean oldCustomer = json.getBoolean(POST.loginOldCustomer);
            String birthDate = json.getString(POST.loginBirthDate);
            int countryID = json.getInt(POST.loginCountryID);
            String modified = json.getString(POST.loginModified);
            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            //TODO Implement OAUTH2 Token Maybe??

            Customer customer = new Customer(customerId, titleID, firstName, lastName, birthDate, countryID, email, password, modified);
            RoomDB.getInstance(getContext()).customerDao().insert(customer);

            if (getContext() != null)
                LocalVariables.storeBoolean(getContext(), R.string.is_old_customer, oldCustomer);

            SyncServerData.getInstance(getContext()).getCustomerDataFromServer(customer);
        }

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dataSynced() {
        listener.login();
    }
}