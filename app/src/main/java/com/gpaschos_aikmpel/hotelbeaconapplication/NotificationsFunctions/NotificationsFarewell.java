package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;

public class NotificationsFarewell{

    //to be called from outside class
    public static void notifyFarewell(Context context) {

        if(!LocalVariables.readBoolean(context, R.string.is_notified_Farewell)) {
            String lastName = LocalVariables.readString(context, R.string.saved_lastName);
            String title = LocalVariables.readString(context, R.string.saved_title);

            String notificationTitle = Params.notificationFarewellTitle + " "+title+ " "+ lastName;
            NotificationCreation.notification(context, Params.NOTIFICATION_CHANNEL_ID
                    ,Params.notificationFarewellID, notificationTitle
                    ,Params.notificationFarewellGreeting1, R.drawable.ic_welcome
                    ,Params.notificationFarewellGreeting1);

            UpdateStoredVariables.farewellNotified(context);
        }
    }
}