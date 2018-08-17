package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInFragment extends Fragment implements JsonListener{

    public static final String RESERVATION_ID = "RESERVATION_ID";
    public static final String TAG = CheckInFragment.class.getSimpleName();

    private Button btnCheckIn;
    private TextView tvArrivalDate;
    private TextView tvDepartureDate;
    private TextView tvReservationNo;
    private NotificationCallbacks listener;

    private int reservationID;
    private Reservation reservation;

    public CheckInFragment() {
        // Required empty public constructor
    }

    public static CheckInFragment newInstance(int reservationID) {
        CheckInFragment fragment = new CheckInFragment();
        Bundle args = new Bundle();
        args.putInt(CheckInFragment.RESERVATION_ID, reservationID);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservationID = getArguments().getInt(RESERVATION_ID);
            reservation = RoomDB.getInstance(getContext()).reservationDao().getReservationByID(reservationID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_check_in, container, false);
        btnCheckIn = v.findViewById(R.id.btnCheckInCheckin);
        tvArrivalDate = v.findViewById(R.id.tvCheckInReservationArrival);
        tvDepartureDate = v.findViewById(R.id.tvCheckInReservationDeparture);
        tvReservationNo = v.findViewById(R.id.tvCheckInReservationNo);
        tvReservationNo.setText(String.valueOf(reservation.getId()));
        tvArrivalDate.setText(reservation.getStartDate());
        tvDepartureDate.setText(reservation.getEndDate());
        if(reservation.isCheckedIn()){
            btnCheckIn.setEnabled(false);
            btnCheckIn.setText(R.string.btnCheckInCheckedin);
        }

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIn();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotificationCallbacks) {
            listener = (NotificationCallbacks) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement NotificationCallbacks");
        }
    }

    private void checkIn(){
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkInReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(getContext()).jsonRequest(this, URL.checkInUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.checkInUrl)) {
            int reservationID = json.getInt(POST.checkInReservationID);
            int roomNumber = json.getInt(POST.checkInRoomNumber);
            String checkInDate = json.getString(POST.checkInDate);
            int roomFloor = json.getInt(POST.checkInRoomFloor);
            String roomPassword = json.getString(POST.checkInRoomPassword);
            String modified = json.getString(POST.checkInModified);
            //update Room with the checked-in information
            JSONArray roomRegionArray = json.getJSONArray(POST.checkInRoomBeaconRegionArray);
            List<BeaconRegion> roomRegions = new ArrayList<>();
            for (int i = 0; i < roomRegionArray.length(); i++) {
                JSONObject roomRegionObject = roomRegionArray.getJSONObject(i);
                int roomBeaconRegionID = roomRegionObject.getInt(POST.checkInRoomBeaconRegionID);
                String roomBeaconRegionUniqueID = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionUniqueID);
                String roomBeaconRegionUUID = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionUUID);
                String roomBeaconRegionMajor = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionMajor);
                String roomBeaconRegionMinor = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionMinor);
                boolean roomBeaconRegionExclusive = roomRegionObject.getBoolean(POST.checkInRoomBeaconRegionExclusive);
                boolean roomBeaconRegionBackground = roomRegionObject.getBoolean(POST.checkInRoomBeaconRegionBackground);
                String roomBeaconRegionModified = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionModified);

                roomRegions.add(new BeaconRegion(roomBeaconRegionID, roomBeaconRegionUniqueID, roomBeaconRegionUUID,
                        roomBeaconRegionMajor, roomBeaconRegionMinor, roomBeaconRegionExclusive, roomBeaconRegionBackground,
                        "room", roomBeaconRegionModified));
            }

            Reservation r = RoomDB.getInstance(getContext()).reservationDao().getReservationByID(reservationID);
            r.checkIn(checkInDate, roomNumber, roomFloor, roomPassword, modified);
            RoomDB.getInstance(getContext()).reservationDao().update(r);
            RoomDB.getInstance(getContext()).beaconRegionDao().insertAll(roomRegions);

            listener.checkedIn(reservationID);
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Log.e(TAG,url+": "+error);
    }
}