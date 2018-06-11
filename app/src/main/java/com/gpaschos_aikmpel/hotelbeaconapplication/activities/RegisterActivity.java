package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MySpinnerAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CustomerDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Title;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.EditTextSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private EditTextSpinner<Title> etsTitle;
    private EditTextSpinner<Country> etsCountry;

    private int adultAge = 18;
    private int targetAge = 30;
    private int maxAge = 100;

    private String email, pass, firstName, lastName, birthDate;
    private int titleID, countryID;

    private SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private SimpleDateFormat localFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
    private Calendar cal;

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cal.set(year, month, dayOfMonth);
            tietBirthDate.setText(String.valueOf(localFormat.format(cal.getTime())));
            tietBirthDate.setError(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.tietRegisterEmail);
        etPass = findViewById(R.id.tietRegisterPassword);
        etPassConf = findViewById(R.id.tietRegisterPasswordConfirm);
        etFirstName = findViewById(R.id.tietRegisterFirstName);
        etLastName = findViewById(R.id.tietRegisterLastName);
        etsTitle = findViewById(R.id.etsRegisterTitle);
        etsCountry = findViewById(R.id.ctsetRegisterCountry);
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

        loadSpinnerData();
    }

    private void loadSpinnerData() {

        List<Title> titleList = RoomDB.getInstance(this).titleDao().getTitles();
        List<Country> countryList = RoomDB.getInstance(this).countryDao().getCountries();

        etsTitle.setAdapter(new MySpinnerAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titleList));
        etsTitle.setOnItemSelectedListener(new EditTextSpinner.OnItemSelectedListener<Title>() {
            @Override
            public void onItemSelectedListener(Title item, int selectedIndex) {
                etsTitle.setText(item.getValue());
                etsTitle.setError(null);
                titleID = item.getId();
            }
        });

        etsCountry.setAdapter(new MySpinnerAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList));
        etsCountry.setOnItemSelectedListener(new EditTextSpinner.OnItemSelectedListener<Country>() {
            @Override
            public void onItemSelectedListener(Country item, int selectedIndex) {
                etsCountry.setText(item.getValue());
                etsCountry.setError(null);
                countryID = item.getId();
            }
        });
    }


    public void register(View view) {

        email = etEmail.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        String passConf = etPassConf.getText().toString().trim();
        firstName = etFirstName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();

        boolean flag = true;

        try {
            birthDate = sqlFormat.format(localFormat.parse(tietBirthDate.getText().toString()));
        } catch (ParseException e) {
            tietBirthDate.setError("Select a date");
            flag = false;
            e.printStackTrace();
        }

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
        if (countryID==0) {
            etsCountry.setError("Select a country");
            flag = false;
        }
        if (titleID==0) {
            etsTitle.setError("Select a title");
            flag = false;
        }

        if (!flag) {
            return;
        }

        Map<String, String> params = new HashMap<>();

        params.put(POST.registerTitleID, String.valueOf(titleID));
        params.put(POST.registerFirstName, firstName);
        params.put(POST.registerLastName, lastName);
        params.put(POST.registerEmail, email);
        params.put(POST.registerPassword, pass);
        params.put(POST.registerCountryID, String.valueOf(countryID));
        params.put(POST.registerBirthDate, birthDate);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.registerUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.registerUrl:
                int customerID = json.getInt(POST.registerCustomerID);


                Customer customer = new Customer(customerID, titleID, firstName, lastName, birthDate, countryID, email, pass);
                CustomerDao customerDao = RoomDB.getInstance(this).customerDao();
                customerDao.insert(customer);
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}