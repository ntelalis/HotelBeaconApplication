package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gpaschos on 28/02/18.
 */

public class ServerFunctions {
    // build and return the StringRequest for the login()
    public static StringRequest getLoginRequest(final Context context, final String email, final String pass) {

        return new StringRequest(Request.Method.POST, GlobalVars.loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int name = jsonObject.getInt("successful");
                    if (name == 1) {
                        int customerId = jsonObject.getInt("customerID");
                        Toast.makeText(context, "Login Successful! CustomerID: " + customerId, Toast.LENGTH_SHORT).show();
                    } else if (name == 0) {
                        Toast.makeText(context, String.valueOf(name), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
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

    }
    //TODO: build and return the StringRequest for the forgotPass()
   /* public StringRequest getforgotPassRequest() {
    }*/

    // build and return the StringRequest for register()
    public static StringRequest getRegisterRequest(final Context context, final String email, final String pass,
                                                 final String firstName, final String lastName, final String title){
        return new StringRequest(Request.Method.POST, GlobalVars.registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context,"your account is created successfully. Please check your" +
                        "email", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", pass);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("title", title);
                return params;
            }
        };
    }
    //build and return the StringRequest for loadSpinnerData()
    public static StringRequest getSpinnerDataRequest(final Context context, String url, final Spinner spinner) {
        //RequestQueue requestQueue=Volley.newRequestQueue(context);
        final ArrayList<String> titleList = new ArrayList<>();
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("successful") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("titleList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            //String country = jsonObject1.getString("Country");
                            String title = jsonArray.getString(i);
                            titleList.add(title);
                        }
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, titleList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
