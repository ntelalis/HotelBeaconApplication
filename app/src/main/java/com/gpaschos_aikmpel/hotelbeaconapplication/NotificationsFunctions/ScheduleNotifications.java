package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ScheduleNotifications {

    public static final String CHECKIN_TAG= "CheckInNotifrication";
    public static final String CHECKOUT_TAG= "CheckOutNotification";

    public static void checkinNotification(Context context, String triggerDate){

        int windowStart=0;
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            long formattedTriggerDate = simpleDateFormat.parse(triggerDate).getTime();
            windowStart = (int)(formattedTriggerDate-currentTime);
            if(windowStart<0)
                windowStart=0;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myjob = dispatcher.newJobBuilder()
                //What service to call
                .setService(MyJobService.class)
                //A unique tag
                .setTag(CHECKIN_TAG)
                //One time job
                .setRecurring(false)
                //Persist reboot
                .setLifetime(Lifetime.FOREVER)
                //start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(windowStart,windowStart+10))
                //Do not overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                //Retry strategy
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                //Some constraints
                .setConstraints(Constraint.ON_ANY_NETWORK)
                //Bundle
                //.setExtras()
                .build();
        dispatcher.mustSchedule(myjob);
    }

    public static void checkoutNotification(Context context, String triggerDate){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myjob = dispatcher.newJobBuilder()
                //What service to call
                .setService(MyJobService.class)
                //A unique tag
                .setTag("CheckOutNotification")
                //One time job
                .setRecurring(false)
                //Persist reboot
                .setLifetime(Lifetime.FOREVER)
                //start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0,60))
                //Do not overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                //Retry strategy
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                //Some constraints
                .setConstraints(Constraint.ON_ANY_NETWORK)
                //Bundle
                //.setExtras(bundle)
                .build();
        dispatcher.mustSchedule(myjob);
    }
}
