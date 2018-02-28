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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    String url = "http://192.168.1.101/dbConnection.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Test Comment for Github


    }

    public void login(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        EditText ETemail = (EditText) findViewById(R.id.editText);
        EditText ETpass = (EditText) findViewById(R.id.editText2);
        final String email = ETemail.getText().toString().trim();
        final String pass = ETpass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int name = jsonObject.getInt("successful");
                    if (name == 1) {
                        int customerId = jsonObject.getInt("customerID");
                        Toast.makeText(LoginActivity.this, "Login Successful! CustomerID: " + customerId, Toast.LENGTH_SHORT).show();
                    }
                    else if(name==0){
                        Toast.makeText(LoginActivity.this, String.valueOf(name), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", pass);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
