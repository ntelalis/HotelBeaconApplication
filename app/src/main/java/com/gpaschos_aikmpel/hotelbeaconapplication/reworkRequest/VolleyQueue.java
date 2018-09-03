package com.gpaschos_aikmpel.hotelbeaconapplication.reworkRequest;

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

    private static final String successString = "success";
    private static final String failString = "errorMessage";

    private static final String successNotSetString = "Server \"success\" variable is not set";
    private static final String successInvalidValueString = "Server \"success\" variable is not 0/1";
    private static final String errorMsgNotSetString = "Server \"errorMessage\" variable is not set";


    public static void jsonRequest(final Context context, final JsonListener jsonListener, String url, Map<String, String> params) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());

            CustomRequest request = new CustomRequest(url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int result = response.getInt(successString);
                                response.remove(successString);
                                switch (result) {
                                    case 1:
                                        jsonListener.getSuccessResult(context, response);
                                        break;
                                    case 0:
                                        try {
                                            String failMsg = response.getString(failString);
                                            jsonListener.getErrorResult(context, failMsg);
                                        } catch (JSONException e) {
                                            jsonListener.getErrorResult(context, errorMsgNotSetString);
                                        }
                                        break;
                                    default:
                                        jsonListener.getErrorResult(context, successInvalidValueString);
                                        break;
                                }
                            } catch (JSONException e) {
                                jsonListener.getErrorResult(context, e.getLocalizedMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            jsonListener.getErrorResult(context, error.toString());

                        }
                    });
            request.setRetryPolicy(new DefaultRetryPolicy(25000, 3, 1f));
            requestQueue.add(request);
        }
    }
}