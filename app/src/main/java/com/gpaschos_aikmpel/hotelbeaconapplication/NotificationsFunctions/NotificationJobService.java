package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NotificationJobService extends JobService {

    private static final String TAG = NotificationJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG,"Job Hit for"+ params.getTag());
        if(params.getTag().contains(ScheduleNotifications.CHECKIN_TAG)){
            NotificationCreation.notifyCheckin(getApplicationContext(),NotificationCreation.CHECKIN_REMINDER);
        }
        else if(params.getTag().contains(ScheduleNotifications.CHECKOUT_TAG)){
            NotificationCreation.notifyCheckout(getApplicationContext());
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
