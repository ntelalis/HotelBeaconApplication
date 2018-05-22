package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;

import java.util.ArrayList;
import java.util.List;


public class UpcomingReservationRecyclerViewFragment extends Fragment implements MyReservationsAdapter.ClickCallbacks {

    private static final String ARG_PARAM1 = "ReservationModel_Parcelable";

    private ArrayList<MyReservationsAdapter.ReservationModel> list;
    private RecyclerView recyclerView;
    private MyReservationsAdapter adapter;
    private BeaconApplication application;

    public UpcomingReservationRecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment UpcomingReservationRecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingReservationRecyclerViewFragment newInstance(ArrayList<MyReservationsAdapter.ReservationModel> list) {
        UpcomingReservationRecyclerViewFragment fragment = new UpcomingReservationRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, list);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        this.application = ((BeaconApplication) getActivity().getApplication());
    }

    //sends the reservationID to the server in order to check in
    @Override
    public void checkIn(MyReservationsAdapter.ReservationModel obj) {
        int reservationID = obj.reservationID;
        if(application!=null){
            application.checkin(reservationID);
        }

        /*Map<String, String> params = new HashMap<>();
        params.put(POST.checkinReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this,URL.checkinUrl, params);
        */
    }

    @Override
    public void checkOut(MyReservationsAdapter.ReservationModel obj) {
        Intent intent = new Intent(getContext(), CheckOutActivity.class);
        startActivity(intent);

    }
}
