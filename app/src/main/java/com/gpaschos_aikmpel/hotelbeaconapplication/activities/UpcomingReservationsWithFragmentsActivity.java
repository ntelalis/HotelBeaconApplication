package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationNoneFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationRecyclerViewFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UpcomingReservationsWithFragmentsActivity extends AppCompatActivity {

    private SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_reservations_with_fragments);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //myReservations();
    }




    /*@Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.upcomingreservationsUrl)) {
            JSONArray response = json.getJSONArray(POST.upcomingReservationsArray);
            List<Reservation> reservationList = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                int adults = response.getJSONObject(i).getInt(POST.upcomingReservationsAdults);
                int reservationID = response.getJSONObject(i).getInt(POST.upcomingReservationsReservationID);
                String arrival = response.getJSONObject(i).getString(POST.upcomingReservationsArrival);
                String departure = response.getJSONObject(i).getString(POST.upcomingReservationsDeparture);
                String roomTitle = response.getJSONObject(i).getString(POST.upcomingReservationsRoomTitle);
                String room = response.getJSONObject(i).getString(POST.upcomingReservationsCheckedinRoom);
                int statusCode = response.getJSONObject(i).getInt(POST.upcomingReservationsEligibleForCheckinCheckout);

            }

        }
    }*/

}