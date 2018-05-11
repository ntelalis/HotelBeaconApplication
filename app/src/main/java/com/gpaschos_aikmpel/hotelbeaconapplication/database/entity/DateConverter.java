package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long dateLong) {
        return new Date(dateLong);
    }

    @TypeConverter
    public static Long toLong(Date date) {
        return date.getTime();
    }
}
