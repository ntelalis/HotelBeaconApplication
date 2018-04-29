package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.gpaschos_aikmpel.hotelbeaconapplication.activities.HomeActivity;

public class Notifications {

    //creates a notification channel
    public static void createChannel(Context context,String channelID, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //creates a notification
    public static void notify(Context context, String channelID,  Class activity,
                              String notificationTitle, String notificationContent, int smallIcon){

        //String CHANNEL_ID = "CHANNEL1";
        //String CHANNEL_NAME = "Default Channel";
        //String notificationTitle = "HotelName";
        //String notificationContent = "Hello Mr. George! Have a nice stay!";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelID);
        //builder.setSmallIcon(R.drawable.ic_welcome);
        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(notificationContent);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        //Intent intent = new Intent(context, HomeActivity.class);
        Intent intent = new Intent(context, activity);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification notification = builder.build();

        //Oreo Notification Code
        /*NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }*/

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,notification);
    }
}
