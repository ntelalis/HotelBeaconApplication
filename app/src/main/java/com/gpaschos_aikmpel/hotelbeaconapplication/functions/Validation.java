package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.util.Patterns;

public class Validation {

    public static boolean checkEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        return false;
    }

    public static boolean checkPassword(String password, int minLength, PasswordType type){

        String passwordPattern ="";

        switch (type){
            case ANY:
                passwordPattern = ".+";
                break;
            case ALPHA:
                passwordPattern = "\\w+";
                break;
            case ALPHA_MIXED_CASE:
                passwordPattern = "(?=.*[a-z])(?=.*[A-Z]).+";
                break;
            case NUMERIC:
                passwordPattern = "\\d+";
                break;
            case ALPHA_NUMERIC:
                passwordPattern = "(?=.*[a-zA-Z])(?=.*[\\d]).+";
                break;
            case ALPHA_NUMERIC_MIXED_CASE:
                passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).+";
                break;
            case ALPHA_NUMERIC_SYMBOLS:
                passwordPattern = "(?=.*[a-zA-Z])(?=.*[\\d])(?=.*([^\\w])).+";
                break;
            case ALPHA_NUMERIC_MIXED_CASE_SYMBOLS:
                passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*([^\\w])).+";
                break;
        }

        passwordPattern += ".{"+minLength+",}";

        if(password.matches(passwordPattern))
            return true;
        return false;

    }

    public static boolean checkNotContains(String container, String content){
        for (int i = 0; (i + 4) < content.length(); i++) {
            if (container.contains(content.substring(i, i + 4))) {
                return false;
            }
        }
        return true;
    }

    public enum PasswordType {
        ANY,
        ALPHA,
        ALPHA_MIXED_CASE,
        NUMERIC,
        ALPHA_NUMERIC,
        ALPHA_NUMERIC_MIXED_CASE,
        ALPHA_NUMERIC_SYMBOLS,
        ALPHA_NUMERIC_MIXED_CASE_SYMBOLS
    }

    public static boolean checkLength(String value, Integer minimumLength, Integer maximumLength){

        minimumLength = minimumLength != null ? minimumLength : Integer.MIN_VALUE;
        maximumLength = maximumLength != null ? maximumLength : Integer.MAX_VALUE;

        if(value.length()<minimumLength || value.length()>maximumLength )
            return false;
        return true;
    }




}
