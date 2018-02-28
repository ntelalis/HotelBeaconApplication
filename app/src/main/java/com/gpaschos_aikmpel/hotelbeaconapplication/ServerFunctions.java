package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gpaschos on 28/02/18.
 */

public class ServerFunctions {

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

    public StringRequest forgotPassRequest(final Context context, final String email) {
        return new StringRequest(Request.Method.POST, GlobalVars.forgotUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Intent intent = new Intent(context,ForgotVerifyActivity.class);
                        context.startActivity(intent);
                    } else if (success == 0) {
                        Toast.makeText(context, "User Not Found", Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };
    }

}
