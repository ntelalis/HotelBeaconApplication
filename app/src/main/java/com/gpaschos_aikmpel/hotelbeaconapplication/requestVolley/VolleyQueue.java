package com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class VolleyQueue {
    private static RequestQueue requestQueue;
    private static VolleyQueue instance = null;

    private static final String successString = "success";
    private static final String failString = "errorMessage";
    private static final String successNotSetString = "Server \"success\" variable is not set";
    private static final String successWrongValueStr = "Server \"success\" variable is not 0/1";
    private static final String errorMsgNotSetStr = "Server \"errorMessage\" variable is not set";

    private VolleyQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static VolleyQueue getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyQueue(context.getApplicationContext());
        }
        return instance;
    }

    public void jsonRequest(final JsonListener jsonListener,
                            final String url,
                            Map<String, String> params) {
        CustomRequest request = new CustomRequest(url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int result = response.getInt(successString);
                            response.remove(successString);
                            switch (result) {
                                case 1:
                                    jsonListener.getSuccessResult(url, response);
                                    break;
                                case 0:
                                    try {
                                        String failMsg = response.getString(failString);
                                        jsonListener.getErrorResult(url, failMsg);
                                    } catch (JSONException e) {
                                        jsonListener.getErrorResult(url, errorMsgNotSetStr);
                                    }
                                    break;
                                default:
                                    jsonListener.getErrorResult(url, successWrongValueStr);
                                    break;
                            }
                        } catch (JSONException e) {
                            jsonListener.getErrorResult(url, successNotSetString);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        jsonListener.getErrorResult(url, error.toString());

                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(25000,
                                                    3, 1f));
        requestQueue.add(request);
    }
}