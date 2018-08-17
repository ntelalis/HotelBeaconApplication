package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class Params {

    public static final int maxReservationDateInYears = 1;
    public static final int daysForCheckInUnlock=0;
    public static final int eligibleForCheckin=2;
    public static final int NOTeligibleForCheckin=1;
    public static final int eligibleForCheckout=4;
    public static final int NOTeligibleForCheckout=3;
    public static final int CheckedOut=5;
    public static final String beaconUUID = "3d8f1dc0-1b23-42f5-9fc1-849f161b2c0e";

    //hotel name
    public static final String HotelName = "My Hotel";
    public static final String HotelCheckoutTime = "12:00";

    //beacon region Types
    public static final String roomRegionType = "room";

    //~~~~~~~~~~~~~~~~~Notifications~~~~~~~~~~~~~~~~~~~~~~

    //check in- check out default times
    public static final String defaultCheckInTime = "16:00";
    public static final String defaultCheckOutTime = "12:00";

    //channels
    public static final String NOTIFICATION_CHANNEL_ID = "CHANNEL1";
    public static final String NOTIFICATION_CHANNEL_NAME = "Default channel";

    //welcome messages
    public static final int notificationWelcomeID = 2;
    public static final String notificationWelcomeTitle = "Welcome to ";
    public static final String notificationWelcomeBackTitle = "Welcome back to ";

    public static final String notificationWelcomeGreeting = "Dear ";
    public static final String notificationWelcomeGreeting3 = ", we are delighted with the opportunity" +
            " to have you as our guest. We wish you a pleasant stay.";

    //farewell messages
    public static final int notificationFarewellID = 5;
    public static final String notificationFarewellTitle = "Goodbye";
    public static final String notificationFarewellGreeting1 = "We thank you for your stay and hope " +
            "that you had an enjoyable experience.";

    //checkin messages
    public static final int notificationCheckinID = 3;
    public static final int notificationCheckinReminderID = 1;
    public static final String notificationCheckinReminderTitle = "Reminder for Check-in";
    public static final String notificationCheckinTitle = "Check-in";
    public static final String notificationCheckinReminderMsg = "You can check in for your reservation at ";
    public static final String notificationCheckinReminderMsg2 = " whenever you are ready";
    public static final String notificationCheckinMsg = "You can check-in using your smartphone to get" +
            " your room number and key";

    //checkout messages
    public static final int notificationCheckoutID = 4;
    public static final String notificationCheckoutTitle = "Reminder for Check-out";
    public static final String notificationCheckoutReminder = "You can check-out using your smartphone." +
            "Just remember that check-out time is ";

    //OFFERS messages
    public static final int notificationOfferID = 6;

    //Features
    public static final String DOOR_UNLOCK = "doorUnlock";
    public static final String WELCOME = "welcome";
    public static final String FAREWELL = "farewell";
    public static final String OFFER = "offer";
    public static final String ROOM = "room";

}
