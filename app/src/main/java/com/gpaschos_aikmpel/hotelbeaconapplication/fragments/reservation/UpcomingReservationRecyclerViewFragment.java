package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckInActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCallbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UpcomingReservationRecyclerViewFragment extends Fragment implements MyReservationsAdapter.ClickCallbacks {

    private static final String ARG_PARAM1 = "ReservationModel_Parcelable";

    private ArrayList<MyReservationsAdapter.ReservationModel> list;
    private RecyclerView recyclerView;
    private MyReservationsAdapter adapter;
    private BeaconApplication application;
    private NotificationCallbacks listener;

    public UpcomingReservationRecyclerViewFragment() {
        // Required empty public constructor
    }

    public static UpcomingReservationRecyclerViewFragment newInstance(List<MyReservationsAdapter.ReservationModel> list) {
        UpcomingReservationRecyclerViewFragment fragment = new UpcomingReservationRecyclerViewFragment();
        Bundle args = new Bundle();
        if(list instanceof ArrayList){
            args.putParcelableArrayList(ARG_PARAM1, (ArrayList<MyReservationsAdapter.ReservationModel>) list);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
        adapter = new MyReservationsAdapter(this, list);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NotificationCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NotificationCallbacks");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_reservation_recycler_view, container, false);
        recyclerView = view.findViewById(R.id.rvFragmentUpcomingRevervation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.application = ((BeaconApplication) Objects.requireNonNull(getActivity()).getApplication());
    }

    //sends the reservationID to the server in order to check in
    @Override
    public void checkIn(MyReservationsAdapter.ReservationModel obj) {
        listener.checkIn(obj.reservationID);
    }

    @Override
    public void checkOut(MyReservationsAdapter.ReservationModel obj) {
        Intent intent = new Intent(getContext(), CheckInActivity.class);
        startActivity(intent);

    }
}