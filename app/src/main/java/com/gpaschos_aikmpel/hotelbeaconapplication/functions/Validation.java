package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.util.Patterns;

public class Validation {

    public static boolean checkEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean checkPassword(String password, int minLength, PasswordType type) {

        String passwordPattern = "";

        switch (type) {
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

        passwordPattern += ".{" + minLength + ",}";

        return password.matches(passwordPattern);

    }

    public static boolean checkNotContains(String container, String content) {
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

    public static boolean checkLength(String value, Integer minimumLength, Integer maximumLength) {

        minimumLength = minimumLength != null ? minimumLength : Integer.MIN_VALUE;
        maximumLength = maximumLength != null ? maximumLength : Integer.MAX_VALUE;

        return value.length() >= minimumLength && value.length() <= maximumLength;
    }

    public static boolean checkNotEmpty(String value) {
        return checkLength(value,1,null);
    }

    public static boolean checkCreditCard(String ccNumber) {
        ccNumber = ccNumber.replaceAll("\\s","");
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }


}
