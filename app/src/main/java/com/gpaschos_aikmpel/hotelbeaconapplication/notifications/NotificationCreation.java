package com.gpaschos_aikmpel.hotelbeaconapplication.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckInActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class NotificationCreation implements JsonListener {


    private static final String TAG = NotificationCreation.class.getSimpleName();

    private static NotificationCreation instance = null;

    private static String lastName;
    private static String title;
    public static final String CHECKIN_REMINDER = "scheduledNotification";
    public static final String CHECKIN_BEACON_NOTIFICATION = "beaconNotification";


    private NotificationCreation() {
    }

    public static NotificationCreation getInstance() {
        if (instance == null) {
            instance = new NotificationCreation();
        }
        return instance;

    }

    public static void notifyOffers(Context context, String regionUniqueID){
        Customer customer = RoomDB.getInstance(context).customerDao().getCustomer();

        switch(regionUniqueID){
            case "restaurantBeacon":

        }
    }


    public static void notifyWelcome(Context context) {
        if (shouldNotifyWelcome(context)) {
            Customer customer = RoomDB.getInstance(context).customerDao().getCustomer();

            lastName = customer.getLastName();
            title = RoomDB.getInstance(context).titleDao().getTitleByID(customer.getTitleID()).getTitle();


            String notificationContent = Params.notificationWelcomeGreeting + title + ". " + lastName
                    + Params.notificationWelcomeGreeting3;
            String notificationTitle;
            if (!LocalVariables.readBoolean(context, R.string.is_old_customer)) {
                notificationTitle = Params.notificationWelcomeTitle + Params.HotelName;

            } else {
                notificationTitle = Params.notificationWelcomeBackTitle + Params.HotelName;
            }

            int icon;

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon = R.drawable.ic_welcome;

            } else {
                icon = R.drawable.ic_welcome_png;
            }

            notification(context, Params.NOTIFICATION_CHANNEL_ID, Params.notificationWelcomeID
                    , notificationTitle, notificationContent, icon, notificationContent);

            //update the variable for welcome notification
            UpdateStoredVariables.welcomeNotified(context);

            //notify the customer that they can check-in if they are eligible
            notifyCheckin(context, CHECKIN_BEACON_NOTIFICATION);
        }

    }


    public static void notifyFarewell(Context context) {

        String checkout = RoomDB.getInstance(context).reservationDao().getCurrentReservation().getCheckOut();
        boolean isCheckedOut = RoomDB.getInstance(context).reservationDao().getCurrentReservation().isCheckedOut();
        if (!LocalVariables.readBoolean(context, R.string.is_notified_Farewell)
                && LocalVariables.readBoolean(context, R.string.is_checked_out)) {
            Customer customer = RoomDB.getInstance(context).customerDao().getCustomer();
            lastName = customer.getLastName();
            title = RoomDB.getInstance(context).titleDao().getTitleByID(customer.getTitleID()).getTitle();

            int icon;

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon = R.drawable.ic_welcome;

            } else {
                icon = R.drawable.ic_welcome_png;
            }

            String notificationTitle = Params.notificationFarewellTitle + " " + title + " " + lastName;
            notification(context, Params.NOTIFICATION_CHANNEL_ID
                    , Params.notificationFarewellID, notificationTitle
                    , Params.notificationFarewellGreeting1, icon
                    , Params.notificationFarewellGreeting1);

            UpdateStoredVariables.farewellNotified(context);
        }
    }

    //notify the customer that he can check-in(the same day of the reservation's startDate and
    // when passing by the front door beacon-after the welcomingNotification)
    public static void notifyCheckin(Context context, String tag) {


        String notificationTitle = null;
        String notificationContent = null;
        int notificationID = 0;
        int icon;


        if (shouldNotifyCheckin(context)) {

            switch (tag) {
                case CHECKIN_BEACON_NOTIFICATION:
                    notificationTitle = Params.notificationCheckinTitle;
                    notificationContent = Params.notificationCheckinMsg;
                    notificationID = Params.notificationCheckinID;

                    break;
                case CHECKIN_REMINDER:
                    notificationTitle = Params.notificationCheckinReminderTitle;
                    notificationContent = Params.notificationCheckinReminderMsg + Params.HotelName
                            + Params.notificationCheckinReminderMsg2;
                    notificationID = Params.notificationCheckinReminderID;
                    break;
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon = R.drawable.ic_welcome;

            } else {
                icon = R.drawable.ic_welcome_png;
            }
            notification(context, Params.NOTIFICATION_CHANNEL_ID
                    , notificationID, notificationTitle
                    , notificationContent, icon
                    , notificationContent, CheckInActivity.class);
        }
    }

    public static void notifyCheckout(Context context) {
        if (shouldNotifyCheckout(context)) {
            int icon;

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon = R.drawable.ic_welcome;

            } else {
                icon = R.drawable.ic_welcome_png;
            }

            notification(context, Params.NOTIFICATION_CHANNEL_ID
                    , Params.notificationCheckoutID, Params.notificationCheckoutTitle
                    , Params.notificationCheckoutReminder + Params.HotelCheckoutTime
                    , icon, Params.notificationCheckoutReminder + Params.HotelCheckoutTime
                    , CheckOutActivity.class);
        }
    }


    //Check if a welcomeNotification has been received, and in case of a previous
    // farewellNotification reception check if 5 hours have past ever since.
    private static boolean shouldNotifyWelcome(Context context) {

        long farewellTime = LocalVariables.readLong(context, R.string.saved_farewell_time);
        long currentTime = System.currentTimeMillis();

        return !LocalVariables.readBoolean(context, R.string.is_notified_Welcome) && (farewellTime == 0 || currentTime >= farewellTime + 5 * 60 * 60 * 1000);
    }

    //check if is_checked_in is false, and if there is a reservation with a startDate<=currentDate
    //then notifyCheckin
    private static boolean shouldNotifyCheckin(Context context) {

        Reservation currentReservation = RoomDB.getInstance(context).reservationDao().getCurrentReservation();
        return currentReservation != null && currentReservation.getCheckIn() == null;


        /*Reservation r = RoomDB.getInstance(context).reservationDao().getUpcomingReservation();
        SimpleDateFormat mySQLFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        //SimpleDateFormat mySQLFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("el","GR"));

        String reservationStartDate = r.getStartDate();

        Date formattedStartDate = null;
        try {
            formattedStartDate = mySQLFormat.parse(reservationStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long lFormattedStartDate = formattedStartDate.getTime();
        long currentTime = Calendar.getInstance().getTime().getTime();

        if (!LocalVariables.readBoolean(context, R.string.is_checked_in) && (lFormattedStartDate <= currentTime)
                && (r != null)) {
            return true;
        }
        return false;*/
    }

    //check if should notify check-in/ check-out. the checkerVariable(R.string.is_checked_in)
    //differentiates the 2 different cases.
    private static boolean shouldNotify(Context context, int checkerVariable) {

        SimpleDateFormat mySQLFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Reservation r = null;
        String scheduleDate = null;

        switch (checkerVariable) {
            case R.string.is_checked_in:
                r = RoomDB.getInstance(context).reservationDao().getUpcomingReservation();
                scheduleDate = r.getStartDate();
                break;
            case R.string.is_checked_out:
                r = RoomDB.getInstance(context).reservationDao().getCurrentReservation();
                scheduleDate = r.getEndDate();
                break;
        }

        Date formattedDate;
        try {
            formattedDate = mySQLFormat.parse(scheduleDate);
            long lFormattedDate = formattedDate.getTime();
            long currentTime = Calendar.getInstance().getTime().getTime();

            return !LocalVariables.readBoolean(context, checkerVariable) && (lFormattedDate <= currentTime)
                    && (r != null);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    private static boolean shouldNotifyCheckout(Context context) {

        Reservation r = RoomDB.getInstance(context).reservationDao().getCurrentReservation();
        SimpleDateFormat mySQLFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        if (r != null) {
            String reservationEndDate = r.getEndDate();
            try {
                Date formattedEndDate = mySQLFormat.parse(reservationEndDate);
                long lFormattedEndDate = formattedEndDate.getTime();
                long currentTime = Calendar.getInstance().getTime().getTime();

                return !LocalVariables.readBoolean(context, R.string.is_checked_out) && (lFormattedEndDate <= currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("shouldNotifyCheckout exception");
            }
        }
        else
            return false;


    }


    //creates a notification channel
    public static void channel(Context context, String channelID, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            else{
                throw new RuntimeException("problem getting notificationManager");
            }
        }
    }

    //creates a notification that opens an Activity
    private static void notification(Context context, String channelID, int notificationID
            , String notificationTitle, String notificationContent, int smallIcon, String bigText
            , Class activity) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);

        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(notificationContent);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));

        Intent intent = new Intent(context, activity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //intent.setAction(Intent.ACTION_VIEW);
        //~~~Create the TaskStackBuilder and add the intent, which inflates the back stack~~~~~//
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(new Random().nextInt(20) + 100, PendingIntent.FLAG_CANCEL_CURRENT);
        //If necessary, you can add arguments to Intent objects in the stack by calling
        // TaskStackBuilder.editIntentAt().
        //stackBuilder.editIntentAt()

        //Because a "special activity" started from a notification doesn't need a back stack, you can create the PendingIntent
        // by calling getActivity(), but you should also be sure you've defined the appropriate task options in the manifest
        //PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        //automatically removes the notification when the user taps it
        builder.setAutoCancel(true);
        Notification notification = builder.build();

        //Oreo Notification Code
        /*NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,NOTIFICATION_CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }*/

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        //notification id is a unique int for each notification
        notificationManagerCompat.notify(notificationID, notification);
    }


    //creates a notification
    private static void notification(Context context, String channelID, int notificationID
            , String notificationTitle, String notificationContent, int smallIcon, String bigText) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);

        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(notificationContent);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        //notification id is a unique int for each notification
        notificationManagerCompat.notify(notificationID, builder.build());
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {

    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
