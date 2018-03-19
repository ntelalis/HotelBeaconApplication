package com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonListener {
    void getSuccessResult(String url, JSONObject json) throws JSONException;
    void getErrorResult(String url, String error);
}