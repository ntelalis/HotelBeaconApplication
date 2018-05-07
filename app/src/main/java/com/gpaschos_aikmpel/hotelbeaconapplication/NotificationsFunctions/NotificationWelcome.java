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

    private static NotificationWelcome instance = null;

    private String lastName;
    private String title;
    private Context context;

    private NotificationWelcome(Context context){
        this.context = context;
    }

    public static NotificationWelcome getInstance(Context context){
        if(instance==null){
            instance = new NotificationWelcome(context);
        }
        return instance;

    }

    //ask the server whether the customer has stayed at the hotel before
    private void hasCustomerStayedBefore(Context context, int customerID) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.welcomeNotificationCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(context).jsonRequest(this, URL.welcomeNotificationUrl, params);
    }

    public void notifyWelcome() {
        int customerID = LocalVariables.readInt(context,R.string.saved_customerId);
        lastName = LocalVariables.readString(context, R.string.saved_lastName);
        title = LocalVariables.readString(context, R.string.saved_title);

        hasCustomerStayedBefore(context, customerID);
    }

    //create notification
    private void notifyCustomer(boolean hasStayedBefore, Context context) {

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

    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.welcomeNotificationUrl)) {
            boolean hasStayedBefore = json.getBoolean(POST.welcomeNotificationHasStayed);
            notifyCustomer(hasStayedBefore, context);
        }

    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
