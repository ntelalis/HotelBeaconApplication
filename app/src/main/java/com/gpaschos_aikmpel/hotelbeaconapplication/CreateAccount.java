package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

    }

    public void register(View view){
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etPass = (EditText) findViewById(R.id.etPassReg);
        EditText etPassConf = (EditText) findViewById(R.id.etPassRegConf);
        EditText etFirstName= (EditText) findViewById(R.id.etFirstName);
        EditText etLastName= (EditText) findViewById(R.id.etLastName);
        final String email = etEmail.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();
        final String passConf = etPassConf.getText().toString().trim();
        final String firstName = etFirstName.getText().toString().trim();
        final String lastName = etLastName.getText().toString().trim();

        //check if any of the fields is left empty
        if(email.isEmpty()||pass.isEmpty()||passConf.isEmpty()||firstName.isEmpty()||lastName.isEmpty()){
            Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
        }
        //check if the password in both fields matches
        else if(!pass.equals(passConf)){
            Toast.makeText(this, "Password and Password Confirmation don't match", Toast.LENGTH_SHORT).show();
        }
        //TODO:make sure the password is of certain 'type'(for a stronger password)

        else{
            String url = "http://192.168.1.101/dbConnection.php";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("pass", pass);
                    params.put("firstName", firstName);
                    params.put("lastName", lastName);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
}
