package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestNotificationsActivity extends AppCompatActivity implements JsonListener {

    private String lastName;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notifications);



        notifyWelcome(LocalVariables.readInt(this,R.string.saved_customerId));
    }


    //method to be called from outside class
    public void notifyWelcome(int customerID) {

        lastName = LocalVariables.readString(this, R.string.saved_lastName);
        title = LocalVariables.readString(this, R.string.saved_title);

        hasCustomerStayedBefore(this, customerID);
    }


    //ask the server whether the customer has stayed at the hotel before
    public void hasCustomerStayedBefore(Context context, int customerID) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.welcomeNotificationCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(context).jsonRequest(this, URL.welcomeNotificationUrl, params);
    }

    //create notification
    public void notifyCustomer(boolean hasStayedBefore) {

        String notificationContent =Params.notificationWelcomeGreeting + title +". "+ lastName
                + Params.notificationWelcomeGreeting3;
        String notificationTitle;
        if (!hasStayedBefore) {
            notificationTitle = Params.notificationWelcomeTitle + Params.HotelName;

        } else {
            notificationTitle = Params.notificationWelcomeBackTitle + Params.HotelName;
        }
      //  NotificationCreation.notification(this, Params.NOTIFICATION_CHANNEL_ID, Params.notificationWelcomeID
           //     , notificationTitle, notificationContent, R.drawable.ic_welcome, notificationContent);
    }

    /*public void farewell(View view){
        //NotificationsFarewell.notifyFarewell(this);
    }*/


    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.welcomeNotificationUrl)) {
            boolean hasStayedBefore = json.getBoolean(POST.welcomeNotificationHasStayed);
            notifyCustomer(hasStayedBefore);
        }

    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
