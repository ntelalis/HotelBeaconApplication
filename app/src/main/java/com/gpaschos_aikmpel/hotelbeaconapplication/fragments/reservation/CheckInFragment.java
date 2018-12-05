package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
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

public class CheckInFragment extends Fragment implements JsonListener, SyncServerData.SyncCallbacksSwipeRefresh {

    public static final String RESERVATION_ID = "RESERVATION_ID";
    public static final String TAG = CheckInFragment.class.getSimpleName();

    private NotificationCallbacks listener;

    private ProgressBar progressBar;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_check_in, container, false);
        progressBar = v.findViewById(R.id.pbCheckInReservation);
        Button btnCheckIn = v.findViewById(R.id.btnCheckInCheckIn);
        TextView tvArrivalDate = v.findViewById(R.id.tvCheckInReservationArrival);
        TextView tvDepartureDate = v.findViewById(R.id.tvCheckInReservationDeparture);
        TextView tvReservationNo = v.findViewById(R.id.tvCheckInReservationNo);
        tvReservationNo.setText(String.valueOf(reservation.getId()));
        tvArrivalDate.setText(reservation.getStartDate());
        tvDepartureDate.setText(reservation.getEndDate());
        if (reservation.isCheckedIn()) {
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

    private void checkIn() {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkInReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(getContext()).jsonRequest(this, URL.checkInUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.checkInUrl)) {
            int roomNumber = json.getInt(POST.checkInRoomNumber);
            String checkInDate = json.getString(POST.checkInDate);
            int roomFloor = json.getInt(POST.checkInRoomFloor);
            String roomPassword = json.getString(POST.checkInRoomPassword);
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
            r.checkIn(checkInDate, roomNumber, roomFloor, roomPassword, checkInDate);
            RoomDB.getInstance(getContext()).reservationDao().update(r);
            RoomDB.getInstance(getContext()).beaconRegionDao().insertAll(roomRegions);
            SyncServerData.getInstance(getContext()).getBeaconRegionFeature();
            SyncServerData.getInstance(this,getContext()).getBeaconRegionFeature();
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Log.e(TAG, url + ": " + error);
    }

    @Override
    public void syncingFinished() {
        progressBar.setVisibility(View.INVISIBLE);
        listener.checkedIn(reservationID);
    }
}