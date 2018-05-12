package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.content.Context;
import android.content.SharedPreferences;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

    public static void storeBoolean(Context context, int key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(key), value);
        editor.apply();
    }

    public static void storeLong(Context context, int key, long value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(context.getString(key), value);
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

    public static boolean readBoolean(Context context, int key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(context.getString(key), false);

    }

    public static long readLong(Context context, int key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.spfile), Context.MODE_PRIVATE);
        return sharedPref.getLong(context.getString(key), (long) 0);

    }

    public static void storeFile(Context context, String filename, byte[] data) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFile(Context context, String filename) {
        FileInputStream fileInputStream;
        byte[] buffer = null;
        try {
            fileInputStream = context.openFileInput(filename);
            buffer = new byte[(int) fileInputStream.getChannel().size()];
            int bytesRead = fileInputStream.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void storePng(Context context){

    }

}
