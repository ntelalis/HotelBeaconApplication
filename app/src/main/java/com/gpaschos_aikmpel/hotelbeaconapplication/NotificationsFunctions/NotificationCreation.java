package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.gpaschos_aikmpel.hotelbeaconapplication.activities.HomeActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.UpcomingReservationActivity;

public class NotificationCreation {

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
    public static void notification(Context context, String channelID, int notificationID
            , String notificationTitle, String notificationContent, int smallIcon, Class activity){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelID);

        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(notificationContent);
        builder.setDefaults(Notification.DEFAULT_SOUND);

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
        notificationManagerCompat.notify(notificationID,notification);
    }


    //creates a notification
    public static void notification(Context context, String channelID, int notificationID
            , String notificationTitle, String notificationContent, int smallIcon, String bigText){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelID);

        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(notificationContent);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        //notification id is a unique int for each notification
        notificationManagerCompat.notify(notificationID,builder.build());
    }

}
