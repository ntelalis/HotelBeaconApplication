package com.gpaschos_aikmpel.hotelbeaconapplication.notifications;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NotificationJobService extends JobService {

    public static final String TAG = NotificationJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {

        if (params.getTag().contains(ScheduleNotifications.CHECK_IN_TAG)) {
            if(params.getExtras()!=null){
                int reservationID = params.getExtras().getInt(ScheduleNotifications.RESERVATION_ID);
                NotificationCreation.notifyCheckIn(getApplicationContext(), NotificationCreation.CHECK_IN_REMINDER, reservationID);
            }
        } else if (params.getTag().contains(ScheduleNotifications.CHECK_OUT_TAG)) {
            NotificationCreation.notifyCheckout(getApplicationContext());
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
