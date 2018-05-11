package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

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
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.UpcomingReservationActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationCreation implements JsonListener {


    private static NotificationCreation instance = null;

    private static String lastName;
    private static String title;

    private NotificationCreation() {
    }

    public static NotificationCreation getInstance() {
        if (instance == null) {
            instance = new NotificationCreation();
        }
        return instance;

    }

    public static void notifyWelcome(Context context) {

        if (shouldNotifyWelcome(context)) {
            lastName = LocalVariables.readString(context, R.string.saved_lastName);
            title = LocalVariables.readString(context, R.string.saved_title);


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
        }

    }


    public static void notifyFarewell(Context context) {

        if (!LocalVariables.readBoolean(context, R.string.is_notified_Farewell)
                &&LocalVariables.readBoolean(context,R.string.is_checked_out)) {
            lastName = LocalVariables.readString(context, R.string.saved_lastName);
            title = LocalVariables.readString(context, R.string.saved_title);

            String notificationTitle = Params.notificationFarewellTitle + " " + title + " " + lastName;
            notification(context, Params.NOTIFICATION_CHANNEL_ID
                    , Params.notificationFarewellID, notificationTitle
                    , Params.notificationFarewellGreeting1, R.drawable.ic_welcome
                    , Params.notificationFarewellGreeting1);

            UpdateStoredVariables.farewellNotified(context);
        }
    }


    //Check if a welcomeNotification has been received, and in case of a previous
    // farewellNotification reception check if 5 hours have past ever since.
    private static boolean shouldNotifyWelcome(Context context) {

        long farewellTime = LocalVariables.readLong(context, R.string.saved_farewell_time);
        long currentTime = System.currentTimeMillis();

        if (!LocalVariables.readBoolean(context, R.string.is_notified_Welcome)) {
            if (farewellTime == 0 || currentTime >= farewellTime + 5 * 60 * 60 * 1000) {
                return true;
            }
        }
        return false;
    }

    //creates a notification channel
    public static void channel(Context context, String channelID, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
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

        Intent intent = new Intent(context, UpcomingReservationActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        //~~~Create the TaskStackBuilder and add the intent, which inflates the back stack~~~~~//
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
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
    public void getSuccessResult(String url, JSONObject json) throws JSONException {

    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
