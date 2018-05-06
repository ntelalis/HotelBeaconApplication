package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;
import android.os.Build;

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

public class NotificationWelcome implements JsonListener {

    private String lastName;
    private String title;
    private Context contexT;

    //ask the server whether the customer has stayed at the hotel before
    public void hasCustomerStayedBefore(Context context, int customerID) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.welcomeNotificationCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(context).jsonRequest(this, URL.welcomeNotificationUrl, params);
    }

    //method to be called from outside class
    public void notifyWelcome(Context context, int customerID) {

        if(!LocalVariables.readBoolean(context, R.string.is_notified_Welcome)) {
            lastName = LocalVariables.readString(context, R.string.saved_lastName);
            title = LocalVariables.readString(context, R.string.saved_title);

            contexT = context;

            hasCustomerStayedBefore(context, customerID);
        }
    }

    //create notification
    public void notifyCustomer(boolean hasStayedBefore, Context context) {

        String notificationContent = Params.notificationWelcomeGreeting + title + ". " + lastName
                + Params.notificationWelcomeGreeting3;
        String notificationTitle;
        if (!hasStayedBefore) {
            notificationTitle = Params.getNotificationWelcomeTitle + Params.HotelName;

        } else {
            notificationTitle = Params.getNotificationWelcomeTitle1 + Params.HotelName;
        }
        int icon;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon = R.drawable.ic_welcome;

        } else {
            icon = R.drawable.ic_welcome_png;
        }
        NotificationCreation.notification(context, Params.NOTIFICATION_CHANNEL_ID, Params.notificationWelcomeID
                , notificationTitle, notificationContent, icon, notificationContent);

        //update the variable for welcome notification
        UpdateStoredVariables.welcomeNotified(context);

    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.welcomeNotificationUrl)) {
            boolean hasStayedBefore = json.getBoolean(POST.welcomeNotificationHasStayed);
            notifyCustomer(hasStayedBefore, contexT);
        }

    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
