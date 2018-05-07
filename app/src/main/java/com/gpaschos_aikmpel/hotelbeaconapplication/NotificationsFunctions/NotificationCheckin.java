package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;

import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationCheckin implements JsonListener{


    //ask the server whether the customer has stayed at the hotel before
    public void checkInReminder(Context context, int customerID) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.welcomeNotificationCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(context).jsonRequest(this, URL.welcomeNotificationUrl, params);
    }


    public static void requirements(){

    }

    public static void serverRequest(){

    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {

    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
