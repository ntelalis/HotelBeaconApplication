package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.util.Log;

import org.json.JSONObject;

public class JSONHelper {

    private static final String TAG = JSONHelper.class.getSimpleName();

    public static String getString(JSONObject jso, String field) {
        if(jso.isNull(field))
            return null;
        else
            try {
                return jso.getString(field);
            }
            catch(Exception ex) {
                Log.e(TAG, "Error parsing value");
                return null;
            }
    }
}
