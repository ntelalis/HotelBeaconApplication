package com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyQueue {

    private static RequestQueue requestQueue;
    private static VolleyQueue instance = null;

    private static final String successString = "success";
    private static final String failString = "errorMessage";

    private static final String invalidString = "Server \"success\" variable is not set";
    private static final String invalidString1 = "Server \"success\" variable is not 0/1";
    private static final String invalidString2 = "Server \"errorMessage\" variable is not 0/1";

    private VolleyQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static VolleyQueue getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyQueue(context.getApplicationContext());
        }
        return instance;
    }

    public static VolleyQueue getInstance() {
        if (instance == null) {
            throw new IllegalStateException(VolleyQueue.class.getSimpleName() +
                    "is not initialized, call getInstance(Context context) first");
        }
        return instance;
    }

    public void jsonRequest(final JsonListener jsonListener, final String url, Map<String, String> params) {
        CustomRequest request = new CustomRequest(url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int result = response.getInt(successString);
                            response.remove(successString);
                            if (result == 1) {
                                jsonListener.getSuccessResult(url, response);
                            } else if (result == 0) {
                                try {
                                    String failMsg = response.getString(failString);
                                    jsonListener.getErrorResult(url, failMsg);
                                } catch (JSONException e) {
                                    jsonListener.getErrorResult(url, invalidString2);
                                }
                            } else {
                                jsonListener.getErrorResult(url, invalidString1);
                            }
                        } catch (JSONException e) {
                            jsonListener.getErrorResult(url, invalidString);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        jsonListener.getErrorResult(url, error.toString());

                    }
                });
        requestQueue.add(request);
    }

}
