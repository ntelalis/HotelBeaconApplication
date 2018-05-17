package com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions;


import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    //This gets called when the job begins executing
    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d("meow","jobservice"+params.getTag());

        switch (params.getTag()) {
            case ScheduleNotifications.CHECKIN_TAG:
                NotificationCreation.notifyCheckin(getApplicationContext(),NotificationCreation.CHECKIN_REMINDER);
                Log.d("meow","insidejobservice");
                break;
            case ScheduleNotifications.CHECKOUT_TAG:
                NotificationCreation.notifyCheckout(getApplicationContext());
                Log.d("meow","insidejobservicecheckout");
                break;
        }

        return false;
        //Meaning "Is there still work going on?" This is used as trye if you start a complex job such as a background thread
        // and you need to return true because the job will be finished when thread finishes in order to le the system hold a wakelock for a while longer
        //(used with jobFinished() only)
    }

    //This gets called if system forcefully stop the execution (such as constraints no longer met) of the job before it gets finished. Used for cleanup
    @Override
    public boolean onStopJob(JobParameters params) {
        return false; //Meaning "Should this job be retried?"
    }
}
