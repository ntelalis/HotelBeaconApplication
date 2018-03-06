package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    Spinner spinner;
    String title;
    EditText etEmail;
    EditText etPass;
    EditText etPassConf;
    EditText etFirstName;
    EditText etLastName;
    TextView tvErrorMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etEmail = (EditText) findViewById(R.id.etEmailReg);
        etPass = (EditText) findViewById(R.id.etPassReg);
        etPassConf = (EditText) findViewById(R.id.etPassRegConf);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        tvErrorMessages = (TextView) findViewById(R.id.tvErrorMessages);
        //tvErrorMessages.setVisibility(View.INVISIBLE);
        spinner = findViewById(R.id.spTitle);
        loadSpinnerData(GlobalVars.titlesUrl);
        setSpinnerListener();
        setEtListener(etPass);
        setEtListener(etEmail);
        setEtListener(etFirstName);
        setEtListener(etLastName);
        setEtListener(etPassConf);

    }

    private void setEtListener(final TextView textView) {
        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!tvErrorMessages.getText().toString().isEmpty()) {
                    tvErrorMessages.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                title = spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //fill the spinner with the Titles data.
    private void loadSpinnerData(String url) {
        StringRequest stringRequest = ServerFunctions.getSpinnerDataRequest(this, url, spinner);
        RequestQueueVolley.getInstance(this).add(stringRequest);
    }

    public void register(View view) {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String passConf = etPassConf.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String title = this.title;

        boolean register = checkInputFields(pass, email, passConf, firstName, lastName, title);
        if (register) {
            StringRequest stringRequest = ServerFunctions.getRegisterRequest(this, email, pass
                    , firstName, lastName, title);
            RequestQueueVolley.getInstance(this).add(stringRequest);
        }
    }

    //return true if all fields are good for registration
    private boolean checkInputFields(String pass, String email, String passConf,
                                     String firstName, String lastName, String title) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";

        //check if any of the fields is left empty
        if (email.isEmpty() || pass.isEmpty() || passConf.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty() || title.isEmpty()) {
            //Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_LONG).show();
            tvErrorMessages.setText("Please fill in the fields");
            tvErrorMessages.setVisibility(View.VISIBLE);
            return false;
        }

        //check if the email address is valid
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setText("");
            tvErrorMessages.setText("Please enter a valid email address");
            tvErrorMessages.setVisibility(View.VISIBLE);
            return false;
        }

        //make sure the password is of certain 'type'(for a stronger password)
        else if (!pass.matches(passwordPattern)) {
            tvErrorMessages.setText("Password must be from 8 to 15 characters, with at least one symbol, " +
                    "one lower case and one upper case letter, and it must not contain the email");
            etPass.setText("");
            etPassConf.setText("");
            tvErrorMessages.setVisibility(View.VISIBLE);
            return false;
        }

        //check if the password in both fields matches
        else if (!pass.equals(passConf)) {
            //Toast.makeText(this, "Password and Password Confirmation don't match", Toast.LENGTH_LONG).show();
            etPass.setText("");
            etPassConf.setText("");
            tvErrorMessages.setText("Password and Password Confirmation don't match");
            tvErrorMessages.setVisibility(View.VISIBLE);
            return false;
        }

        //make sure the password is of certain 'type'(for a stronger password)
        else if (pass.matches(passwordPattern)) {
            for (int i = 0; (i + 4) < email.length(); i++) {
                if (pass.contains(email.substring(i, i + 4))) {
                    //Toast.makeText(this, "Attention:"+R.string.passwordInstructions, Toast.LENGTH_LONG).show();
                    tvErrorMessages.setText("Password must be from 8 to 15 characters, with at least one symbol, " +
                            "one lower case and one upper case letter, and it must not contain the email");
                    etPass.setText("");
                    etPassConf.setText("");
                    tvErrorMessages.setVisibility(View.VISIBLE);
                    return false;
                }
            }
            return true;
        } else
            return true;
    }
}