package com.gpaschos_aikmpel.hotelbeaconapplication.notifications;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NotificationJobService extends JobService {

    private static final String TAG = NotificationJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {

        if(params.getTag().contains(ScheduleNotifications.CHECKIN_TAG)){
            int reservationID = params.getExtras().getInt(ScheduleNotifications.RESERVATION_ID);
            NotificationCreation.notifyCheckin(getApplicationContext(),NotificationCreation.CHECK_IN_REMINDER,reservationID);
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
