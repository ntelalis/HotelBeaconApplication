package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class ScheduleNotifications {
    public static void checkinNotification(Context context){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myjob = dispatcher.newJobBuilder()
                //What service to call
                .setService(MyJobService.class)
                //A unique tag
                .setTag("CheckInNotification")
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
