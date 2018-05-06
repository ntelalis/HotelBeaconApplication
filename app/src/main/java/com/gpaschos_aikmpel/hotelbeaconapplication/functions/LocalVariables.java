package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.content.Context;
import android.content.SharedPreferences;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class LocalVariables {

    //public static final String  = "";

    public static void storeString(Context context, int key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(key), value);
        editor.apply();
    }

    public static void storeInt(Context context, int key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(key), value);
        editor.apply();
    }

    public static String readString(Context context, int key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(key), null);
    }

    public static int readInt(Context context, int key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        return sharedPref.getInt(context.getString(key), 0);

    }
}
