package com.gpaschos_aikmpel.hotelbeaconapplication.reworkRequest;

import android.content.Context;

import org.json.JSONObject;

public interface JsonListener {
    void getSuccessResult(Context context, JSONObject json);

    void getErrorResult(Context context, String error);
}