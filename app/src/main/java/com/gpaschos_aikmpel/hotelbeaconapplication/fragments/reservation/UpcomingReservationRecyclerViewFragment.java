package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.ReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.OfferExclusiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCallbacks;

import java.util.ArrayList;
import java.util.List;


public class UpcomingReservationRecyclerViewFragment extends Fragment implements ReservationsAdapter.ClickCallbacks,SyncServerData.SyncCallbacksSwipeRefresh {

    private static final String ARG_PARAM1 = "ReservationModel_Parcelable";
    private static final String TAG = UpcomingReservationRecyclerViewFragment.class.getSimpleName();

    private ArrayList<ReservationsAdapter.ReservationModel> list;
    private ReservationsAdapter adapter;
    private NotificationCallbacks listener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public UpcomingReservationRecyclerViewFragment() {
        // Required empty public constructor
    }

    public static UpcomingReservationRecyclerViewFragment newInstance(List<ReservationsAdapter.ReservationModel> list) {
        UpcomingReservationRecyclerViewFragment fragment = new UpcomingReservationRecyclerViewFragment();
        Bundle args = new Bundle();
        if (list instanceof ArrayList) {
            args.putParcelableArrayList(ARG_PARAM1, (ArrayList<ReservationsAdapter.ReservationModel>) list);
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
        adapter = new ReservationsAdapter(this, list);

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
        recyclerView = view.findViewById(R.id.rvFragmentUpcomingReservation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.srUpcomingReservation);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                SyncServerData.getInstance(UpcomingReservationRecyclerViewFragment.this, getContext()).getReservations(RoomDB.getInstance(getContext()).customerDao().getCustomer());
            }
        });
        return view;
    }

    public void refreshData() {
        List<Reservation> reservationList = RoomDB.getInstance(getContext()).reservationDao().getReservations();
        List<ReservationsAdapter.ReservationModel> reservationModelList = new ArrayList<>();
        for (Reservation r : reservationList) {

            RoomType rt = RoomDB.getInstance(getContext()).roomTypeDao().getRoomTypeById(r.getRoomTypeID());

            int reservationStatus = ReservationsAdapter.getReservationStatus(r);

            reservationModelList.add(new ReservationsAdapter.ReservationModel(r.getAdults(),
                    r.getChildren(), rt.getName(), rt.getImage(), r.getId(), r.getStartDate(),
                    r.getEndDate(), reservationStatus, r.getRoomNumber()));
            recyclerView.setAdapter(new ReservationsAdapter(this, reservationModelList));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void syncingFinished() {
        Log.d(TAG, "syncing done");
        swipeRefreshLayout.setRefreshing(false);
        refreshData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //sends the reservationID to the server in order to check in
    @Override
    public void checkIn(int reservationID) {
        listener.checkIn(reservationID);
    }

    @Override
    public void checkOut(ReservationsAdapter.ReservationModel obj) {
        Intent intent = new Intent(getContext(), CheckOutActivity.class);
        startActivity(intent);
    }
}