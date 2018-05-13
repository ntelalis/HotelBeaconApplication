package com.gpaschos_aikmpel.hotelbeaconapplication.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBHelperFunctions {

    public static Date timestamp(String timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            return simpleDateFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void meow(){

    }
}
