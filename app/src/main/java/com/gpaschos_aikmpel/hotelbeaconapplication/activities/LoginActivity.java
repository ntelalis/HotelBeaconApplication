package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.UpdateStoredVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements JsonListener {

    private EditText etEmail;
    private EditText etPass;
    private Customer customer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //create a notification channel
        NotificationCreation.channel(this, "basic_channel", "default channel");

        //TODO DataSyncing on Login. Is this a good choice?
        SyncServerData.getInstance(this).getDataFromServer();

        customer = RoomDB.getInstance(this).customerDao().getCustomer();

        if (customer != null) {
            loginRequest(customer.getEmail(), customer.getPassword());
        }

        etEmail = findViewById(R.id.etLoginEmail);
        etPass = findViewById(R.id.etLoginPassword);


    }

    public void loginBtn(View view) {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        loginRequest(email, pass);

        //store notification variables and set them to false
        UpdateStoredVariables.setDefaults(this);
    }

    private void loginRequest(String email, String pass) {
        Map<String, String> params = new HashMap<>();

        params.put(POST.loginEmail, email);
        params.put(POST.loginPassword, pass);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.loginUrl, params);
    }

    public void forgotBtn(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    public void registerBtn(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.loginUrl)) {

            int customerId = json.getInt(POST.loginCustomerID);

            if (customer == null) {
                String firstName = json.getString(POST.loginFirstName);
                String title = json.getString(POST.loginTitle);
                String lastName = json.getString(POST.loginLastName);
                int isOldCustomer = json.getInt(POST.loginIsOldCustomer);
                boolean isCheckedIn = json.getBoolean(POST.loginIsCheckedIn);
                boolean isCheckedOut = json.getBoolean(POST.loginIsCheckedOut);

                //TODO implement birthdate country
                String birthDate = "";
                String country = "";

                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                //TODO Implement OAUTH2 Token Maybe??

                Customer customer = new Customer(customerId, title, firstName, lastName, birthDate, country, email, password);
                RoomDB.getInstance(this).customerDao().insert(customer);

                //FIXME OldCustomer????
                if (isOldCustomer > 0) {
                    LocalVariables.storeBoolean(this, R.string.is_old_customer, true);
                } else {
                    LocalVariables.storeBoolean(this, R.string.is_old_customer, false);
                }
                Toast.makeText(this, "Login Successful! CustomerID: " + customer.getCustomerId(), Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "Login Successful! CustomerID: " + customerId, Toast.LENGTH_LONG).show();
            }



            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }

}