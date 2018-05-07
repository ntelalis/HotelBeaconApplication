package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;

import java.util.Date;

public class UpdateStoredVariables {

    //update the variables for notifications welcome,farewell/check-in,check-out handling

    public static void checkedIn(Context context) {
        LocalVariables.storeBoolean(context, R.string.is_checked_in, true);
        LocalVariables.storeBoolean(context, R.string.is_checked_out, false);
        LocalVariables.storeBoolean(context, R.string.is_notified_Farewell, false);
    }

    public static void checkedOut(Context context) {
        LocalVariables.storeBoolean(context, R.string.is_checked_out, true);
        LocalVariables.storeBoolean(context, R.string.is_checked_in, true);
        LocalVariables.storeBoolean(context, R.string.is_notified_Welcome, true);
        LocalVariables.storeBoolean(context, R.string.is_old_customer, true);
    }

    public static void welcomeNotified(Context context) {
        LocalVariables.storeBoolean(context, R.string.is_notified_Welcome, true);
    }

    public static void farewellNotified(Context context) {
        LocalVariables.storeBoolean(context, R.string.is_checked_in, false);
        LocalVariables.storeBoolean(context, R.string.is_notified_Welcome, false);
        LocalVariables.storeBoolean(context, R.string.is_notified_Farewell, true);
        LocalVariables.storeLong(context, R.string.saved_farewell_time, System.currentTimeMillis());
    }

    public static void setDefaults(Context context) {
        LocalVariables.storeBoolean(context, R.string.is_checked_in, false);
        LocalVariables.storeBoolean(context, R.string.is_checked_out, false);
        LocalVariables.storeBoolean(context, R.string.is_notified_Welcome, false);
        LocalVariables.storeBoolean(context, R.string.is_notified_Farewell, false);
    }
}
