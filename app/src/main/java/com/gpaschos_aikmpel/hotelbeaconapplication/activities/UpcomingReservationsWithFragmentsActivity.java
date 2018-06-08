/*package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_reservations_with_fragments);
    }
}*/
package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.UpcomingReservationNoneFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.UpcomingReservationRecyclerViewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpcomingReservationsWithFragmentsActivity extends AppCompatActivity implements JsonListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_reservations_with_fragments);


        //myReservations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myReservations();
    }

    //query to the server about my reservation list
    public void myReservations() {
        //SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.spfile), Context.MODE_PRIVATE);
        //int customerID = sharedPreferences.getInt(getString(R.string.saved_customerId), 0);
        int customerID = RoomDB.getInstance(this).customerDao().getCustomer().getCustomerId();

        Map<String, String> params = new HashMap<>();
        params.put(POST.upcomingreservationsCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.upcomingreservationsUrl, params);
    }

    public void fillRecyclerView(List<MyReservationsAdapter.ReservationModel> list) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        UpcomingReservationRecyclerViewFragment fragment = UpcomingReservationRecyclerViewFragment.newInstance((ArrayList<MyReservationsAdapter.ReservationModel>) list);
        transaction.replace(R.id.upcomingReservationsFragmentContainer, fragment);
        transaction.commit();
    }


    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.upcomingreservationsUrl)) {
            JSONArray response = json.getJSONArray(POST.upcomingreservationsResponseArray);

            List<MyReservationsAdapter.ReservationModel> reservations = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                int adults = response.getJSONObject(i).getInt(POST.upcomingreservationsAdults);
                int reservationID = response.getJSONObject(i).getInt(POST.upcomingreservationsReservationID);
                String arrival = response.getJSONObject(i).getString(POST.upcomingreservationsArrival);
                String departure = response.getJSONObject(i).getString(POST.upcomingreservationsDeparture);
                String roomTitle = response.getJSONObject(i).getString(POST.upcomingreservationsRoomTitle);
                String room = response.getJSONObject(i).getString(POST.upcomingreservationsCheckedinRoom);
                int statusCode = response.getJSONObject(i).getInt(POST.upcomingreservationsEligibleForCheckinCheckout);

                reservations.add(new MyReservationsAdapter.ReservationModel(adults, roomTitle, reservationID,
                        arrival, departure, statusCode, room));
            }
            if(reservations.isEmpty()){
                /*Toast.makeText(this, "You don't have any upcoming reservations", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                finish();*/

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                UpcomingReservationNoneFragment fragment = UpcomingReservationNoneFragment.newInstance();
                transaction.replace(R.id.upcomingReservationsFragmentContainer, fragment);
                transaction.commit();
            }
            else{
                fillRecyclerView(reservations);
            }
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}