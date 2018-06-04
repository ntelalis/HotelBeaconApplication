package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.DoorUnlockActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.RoomServiceActivity;

public class CustomerServicesFragment extends Fragment {

    private static final String CHECK_IN_DATE = "date";
    private static final String RESERVATION_ID = "ID";
    private static final String ROOM_NO = "Room Number";
    private static final String FLOOR = "ROom Floor";

    private String checkInDate;
    private int reservationID;
    private int floor;
    private int roomNo;

    private Button btnDoorUnlock;
    private Button btnRoomService;
    private Button btnCheckOut;
    private TextView tvReservationID;
    private TextView tvCheckInDate;
    private TextView tvRoomNo;
    private TextView tvRoomFloor;


    public CustomerServicesFragment() {
    }

    public static CustomerServicesFragment newInstance(String date, int reservID,
                                                       int roomFloor, int roomNumber) {
        CustomerServicesFragment fragment = new CustomerServicesFragment();
        Bundle args = new Bundle();
        args.putString(CHECK_IN_DATE, date);
        args.putInt(RESERVATION_ID, reservID);
        args.putInt(FLOOR, roomFloor);
        args.putInt(ROOM_NO, roomNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkInDate = getArguments().getString(CHECK_IN_DATE);
            reservationID = getArguments().getInt(RESERVATION_ID);
            floor = getArguments().getInt(FLOOR);
            roomNo = getArguments().getInt(ROOM_NO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_services, container, false);

        tvCheckInDate = view.findViewById(R.id.tvCustomerServicesCheckedIn);
        tvReservationID = view.findViewById(R.id.tvCustomerServicesReservationID);
        tvRoomFloor = view.findViewById(R.id.tvCustomerServicesFloor);
        tvRoomNo = view.findViewById(R.id.tvCustomerServicesRoomNo);

        tvCheckInDate.setText(checkInDate);
        tvReservationID.setText(String.valueOf(reservationID));
        tvRoomNo.setText(String.valueOf(roomNo));
        tvRoomFloor.setText(String.valueOf(floor));

        btnCheckOut = view.findViewById(R.id.btnCustomerServicesCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), CheckOutActivity.class);
                startActivity(intent);
            }
        });

        btnRoomService = view.findViewById(R.id.btnCustomerServicesRoomService);
        btnRoomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), RoomServiceActivity.class);
                startActivity(intent);
            }
        });

        btnDoorUnlock = view.findViewById(R.id.btnCustomerServicesUnlockDoor);
        btnDoorUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), DoorUnlockActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
