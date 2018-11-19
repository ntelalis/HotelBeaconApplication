package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckOutActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.CheckOutAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CheckOutFragment extends Fragment implements JsonListener {

    private RecyclerView recyclerView;
    private TextView totalPrice;
    private Button btnConfirmCheckout;
    private int reservationID;

    public CheckOutFragment() {
    }

    public static CheckOutFragment newInstance() {
        CheckOutFragment fragment = new CheckOutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_check_out, container, false);

        reservationID = RoomDB.getInstance(getActivity()).reservationDao().getCurrentReservation().getId();

        btnConfirmCheckout = view.findViewById(R.id.btnCheckoutConfirm);
        totalPrice = view.findViewById(R.id.tvCheckoutTotalPrice);
        recyclerView = view.findViewById(R.id.rvCheckout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        btnConfirmCheckout.setEnabled(false);
        btnConfirmCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put(POST.checkoutConfirmReservationID, String.valueOf(reservationID));
                VolleyQueue.getInstance(getContext()).jsonRequest(CheckOutFragment.this, URL.checkoutConfirmationUrl, params);
            }
        });

        getCharges(reservationID);
        return view;
    }


    private void getCharges(int reservationID) {

        Map<String, String> params = new HashMap<>();
        params.put(POST.checkoutReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(getContext()).jsonRequest(this, URL.checkoutUrl, params);
    }

    private void fillRecyclerViewAndTextView(List<CheckOutAdapter.ChargeModel> list, double totalPrice) {
        CheckOutAdapter adapter = new CheckOutAdapter(list);
        recyclerView.setAdapter(adapter);
        //fixing decimal digits
        DecimalFormat df = new DecimalFormat("#.##");
        totalPrice = Double.valueOf(df.format(totalPrice));
        //
        this.totalPrice.setText(String.valueOf(totalPrice));
        if (RoomDB.getInstance(getActivity()).reservationDao().getCurrentReservation().getCheckIn() != null) {
            btnConfirmCheckout.setEnabled(true);
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        JSONArray response;
        switch (url) {
            case URL.checkoutUrl:
                double totalPrice = json.getDouble(POST.checkoutTotalPrice);
                response = json.getJSONArray(POST.checkoutChargeDetails);

                List<CheckOutAdapter.ChargeModel> charges = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    String service = response.getJSONObject(i).getString(POST.checkoutService);
                    double price = response.getJSONObject(i).getDouble(POST.checkoutServicePrice);

                    charges.add(new CheckOutAdapter.ChargeModel(service, price));
                }
                fillRecyclerViewAndTextView(charges, totalPrice);
                break;
            case URL.checkoutConfirmationUrl:
                String checkoutDate = json.getString(POST.checkoutConfirmDate);
                String modified = json.getString(POST.checkoutConfirmModified);
                //update Room with the checked-out information
                Reservation r = RoomDB.getInstance(getContext()).reservationDao().getReservationByID(reservationID);
                r.checkOut(checkoutDate, modified);
                RoomDB.getInstance(getContext()).reservationDao().update(r);

                ((CheckOutActivity) Objects.requireNonNull(getActivity())).checkedOutConfirmation();


        }
    }


    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_LONG).show();
    }
}
