package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.DoorUnlockActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.ReviewActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.RoomServiceActivity;

public class MyRoomActiveFragment extends Fragment {

    //private static final String CHECK_IN_DATE = "date";
    private static final String RESERVATION_ID = "ID";
    private static final String ROOM_NO = "Room Number";
    private static final String FLOOR = "Room Floor";

    //private String checkInDate;
    private int reservationID;
    private int floor;
    private int roomNo;


    public MyRoomActiveFragment() {
    }

    public static MyRoomActiveFragment newInstance(int reservationID,
                                                   int roomNumber, int roomFloor) {
        MyRoomActiveFragment fragment = new MyRoomActiveFragment();
        Bundle args = new Bundle();
        //args.putString(CHECK_IN_DATE, date);
        args.putInt(RESERVATION_ID, reservationID);
        args.putInt(FLOOR, roomFloor);
        args.putInt(ROOM_NO, roomNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //checkInDate = getArguments().getString(CHECK_IN_DATE);
            reservationID = getArguments().getInt(RESERVATION_ID);
            floor = getArguments().getInt(FLOOR);
            roomNo = getArguments().getInt(ROOM_NO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_room, container, false);

        //tvCheckInDate = view.findViewById(R.id.tvCustomerServicesCheckedIn);
        TextView tvReservationID = view.findViewById(R.id.tvCustomerServicesReservationID);
        TextView tvRoomFloor = view.findViewById(R.id.tvCustomerServicesFloor);
        TextView tvRoomNo = view.findViewById(R.id.tvCustomerServicesRoomNo);

        //tvCheckInDate.setText(checkInDate);
        tvReservationID.setText(String.valueOf(reservationID));
        tvRoomNo.setText(String.valueOf(roomNo));
        tvRoomFloor.setText(String.valueOf(floor));

        Button btnCheckOut = view.findViewById(R.id.btnCustomerServicesCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                startActivity(intent);
            }
        });

        Button btnRoomService = view.findViewById(R.id.btnCustomerServicesRoomService);
        btnRoomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomServiceActivity.class);
                startActivity(intent);
            }
        });

        Button btnDoorUnlock = view.findViewById(R.id.btnCustomerServicesUnlockDoor);
        btnDoorUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoorUnlockActivity.class);
                startActivity(intent);
            }
        });

        Button btnReview = view.findViewById(R.id.btnCustomerServicesReview);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        Button btnRoomControl = view.findViewById(R.id.btnCustomerServicesRoomControl);
        btnRoomControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "This feature is not supported yet", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
