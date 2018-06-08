package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CustomerDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements JsonListener {

    private EditText etEmail;
    private EditText etPass;
    private EditText etPassConf;
    private EditText etFirstName;
    private EditText etLastName;
    private TextInputEditText tietBirthDate;
    private Spinner spTitle, spCountry;

    private int adultAge = 18;
    private int targetAge = 30;
    private int maxAge = 100;

    private String email, pass, firstName, lastName, title, birthDate, country;

    private SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private SimpleDateFormat localFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
    private Calendar cal;

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cal.set(year, month, dayOfMonth);

            tietBirthDate.setText(String.valueOf(localFormat.format(cal.getTime())));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etRegisterEmail);
        etPass = findViewById(R.id.etRegisterPassword);
        etPassConf = findViewById(R.id.etRegisterPasswordConfirm);
        etFirstName = findViewById(R.id.etRegisterFirstName);
        etLastName = findViewById(R.id.etRegisterLastName);
        spTitle = findViewById(R.id.spRegisterTitle);
        spCountry = findViewById(R.id.spRegisterCountry);

        cal = Calendar.getInstance();


        tietBirthDate = findViewById(R.id.tietRegisterBirthDate);
        tietBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.YEAR, -adultAge);
                Calendar minDate = Calendar.getInstance();
                minDate.add(Calendar.YEAR, -maxAge);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        tietBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, dateSetListener, cal.get(Calendar.YEAR) - targetAge, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                    Calendar maxDate = Calendar.getInstance();
                    maxDate.add(Calendar.YEAR, -adultAge);
                    Calendar minDate = Calendar.getInstance();
                    minDate.add(Calendar.YEAR, -maxAge);
                    datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                    datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                    datePickerDialog.show();
                }
            }
        });

        List<Country> countryListObj = RoomDB.getInstance(this).countryDao().getCountries();
        List<String> countryList = new ArrayList<>();
        for(Country country: countryListObj){
            countryList.add(country.getName());
        }
        spCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList));

        loadSpinnerData();
    }

    //fill the spTitle with the Titles data.
    private void loadSpinnerData() {
        VolleyQueue.getInstance(this).jsonRequest(this, URL.titlesUrl, null);
    }


    public void register(View view) {

        email = etEmail.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        String passConf = etPassConf.getText().toString().trim();
        firstName = etFirstName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        title = spTitle.getSelectedItem().toString().trim();
        try {
            birthDate =sqlFormat.format(localFormat.parse(tietBirthDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        country = spCountry.getSelectedItem().toString();

        boolean flag = true;

        if (!Validation.checkEmail(email)) {
            etEmail.setError("Please enter a valid email address");
            flag = false;
        }

        //FIXME email changing doesn't fix error for pass contains email
        if (!Validation.checkPassword(pass, 6, Validation.PasswordType.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)) {
            etPass.setError("Invalid password");
            flag = false;
        } else {
            if (!Validation.checkNotContains(pass, email)) {
                etPass.setError("Password contains part of email");
                flag = false;
            }
        }

        if (!pass.equals(passConf)) {
            etPassConf.setError("Password confirmation doesn't match");
            flag = false;
        }

        if (!Validation.checkLength(firstName, 2, null)) {
            etFirstName.setError("Minimum Length is 2");
            flag = false;
        }

        if (!Validation.checkLength(lastName, 2, null)) {
            etLastName.setError("Minimum Length is 2");
            flag = false;
        }

        if (!flag) {
            return;
        }

        Map<String, String> params = new HashMap<>();

        params.put(POST.registerTitle, title);
        params.put(POST.registerFirstName, firstName);
        params.put(POST.registerLastName, lastName);
        params.put(POST.registerEmail, email);
        params.put(POST.registerPassword, pass);
        params.put(POST.registerCountry,country);
        params.put(POST.registerBirthDate,birthDate);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.registerUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.registerUrl:
                int customerID = json.getInt(POST.registerCustomerID);


                Customer customer = new Customer(customerID, title, firstName, lastName, birthDate, country, email, pass);
                CustomerDao customerDao = RoomDB.getInstance(this).customerDao();
                customerDao.insert(customer);
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case URL.titlesUrl:
                List<String> titleList = new ArrayList<>();
                JSONArray jsonArray = json.getJSONArray(POST.titlesTitleList);
                for (int i = 0; i < jsonArray.length(); i++) {
                    titleList.add(jsonArray.getString(i));
                }
                spTitle.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titleList));
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}