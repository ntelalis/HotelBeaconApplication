package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class Params {

    public static final int maxReservationDateInYears = 1;
    public static final int daysForCheckInUnlock=0;
    public static final int eligibleForCheckin=2;
    public static final int NOTeligibleForCheckin=1;
    public static final int eligibleForCheckout=4;
    public static final int NOTeligibleForCheckout=3;
    public static final int CheckedOut=5;
    public static final String beaconArea = "3d8f1dc0-1b23-42f5-9fc1-849f161b2c0e";

    //hotel name
    public static final String HotelName = "HotelName";


    //~~~~~~~~~~~~~~~~~Notifications~~~~~~~~~~~~~~~~~~~~~~

    //check in- check out default times
    public static final String defaultCheckInTime = "16:00";
    public static final String defaultCheckOutTime = "12:00";

    //channels
    public static final String NOTIFICATION_CHANNEL_ID = "CHANNEL1";
    public static final String NOTIFICATION_CHANNEL_NAME = "Default channel";

    //welcoming messages
    public static final int notificationWelcomeID = 1;
    public static final String notificationWelcomeTitle = "Welcome to ";
    public static final String notificationWelcomeBackTitle = "Welcome back to ";
    //public static final String getNotificationWelcomeTitle2 = " to";
    public static final String notificationWelcomeGreeting = "Dear ";
    //public static final String notificationWelcomeGreeting1 = ", welcome ";
    //public static final String notificationWelcomeBackGreeting1 = ", welcome back ";
    //public static final String notificationWelcomeGreeting2 = "to the hotel ";
    public static final String notificationWelcomeGreeting3 = ", we are delighted with the opportunity" +
            " to have you as our guest. We wish you a pleasant stay.";

    //farewell messages
    public static final int notificationFarewellID = 4;
    public static final String notificationFarewellTitle = "Goodbye";
    public static final String notificationFarewellGreeting1 = "We thank you for your stay and hope " +
            "that you had an enjoyable experience.";

    //checkin messages
    public static final int notificationCheckinID = 2;
    public static final String notificationCheckinTitle = "Reminder";
    public static final String notificationCheckinReminder = "You can check in anytime after ";
    public static final String notificationCheckinReminder2 = "on the ";
    public static final String notificationCheckin = "Check-in is now open!";

    //checkout messages
    public static final int notificationCheckoutID = 3;
    public static final String notificationCheckoutTitle = "Reminder";
    public static final String notificationCheckoutReminder = "You can check out anytime after ";
    public static final String notificationCheckoutReminder2 = "on the ";
    public static final String notificationCheckout = "Check-out is available now";

}
