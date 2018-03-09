package com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonListener {
    void getSuccessResult(String url, JSONObject json) throws JSONException;
    void getErrorResult(String url, String error);
}