package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.GeneralOffer;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert.ViewOfferDetailsFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;

import java.util.List;

public class OfferGeneralFragment extends Fragment implements SyncServerData.SyncCallbacksSwipeRefresh {

    private static final String TAG = OfferGeneralFragment.class.getSimpleName();
    private List<GeneralOffer> generalOfferList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    public OfferGeneralFragment() {
        // Required empty public constructor
    }

    public static OfferGeneralFragment newInstance() {
        OfferGeneralFragment fragment = new OfferGeneralFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generalOfferList = RoomDB.getInstance(getContext()).generalOfferDao().getGeneralOffers();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offer_general, container, false);
        recyclerView = v.findViewById(R.id.rvOfferGeneral);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new GeneralOfferAdapter(generalOfferList));

        swipeRefreshLayout = v.findViewById(R.id.srOfferGeneral);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                SyncServerData.getInstance(OfferGeneralFragment.this, getContext()).getGeneralOffers();
            }
        });

        return v;
    }

    @Override
    public void syncingFinished() {
        Log.d(TAG,"syncing done");
        swipeRefreshLayout.setRefreshing(false);
        refreshData();
    }

    public void refreshData() {
        generalOfferList = RoomDB.getInstance(getContext()).generalOfferDao().getGeneralOffers();
        recyclerView.setAdapter(new OfferGeneralFragment.GeneralOfferAdapter(generalOfferList));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private class GeneralOfferAdapter extends RecyclerView.Adapter<GeneralOfferAdapter.GeneralOfferViewHolder>{

        private List<GeneralOffer> generalOfferList;

        GeneralOfferAdapter(List<GeneralOffer> generalOfferList) {
            this.generalOfferList = generalOfferList;
        }

        @NonNull
        @Override
        public GeneralOfferAdapter.GeneralOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.viewholder_offers_general, parent, false);
            return new GeneralOfferViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GeneralOfferAdapter.GeneralOfferViewHolder holder, int position) {
            GeneralOffer generalOffer = generalOfferList.get(position);
            holder.tvOfferTitle.setText(generalOffer.getTitle());
            holder.tvOfferDiscountPrice.setText(generalOffer.getPriceDiscount());
            holder.description = generalOffer.getDescription();
        }

        @Override
        public int getItemCount() {
            return generalOfferList.size();
        }

        class GeneralOfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView tvOfferTitle;
            TextView tvOfferDetails;
            TextView tvOfferDiscountPrice;
            String description;

            GeneralOfferViewHolder(View itemView) {
                super(itemView);
                tvOfferTitle = itemView.findViewById(R.id.tvOfferGeneralTitle);
                tvOfferDetails = itemView.findViewById(R.id.tvOfferGeneralDetails);
                tvOfferDiscountPrice = itemView.findViewById(R.id.tvOfferGeneralDiscountPrice);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String title = tvOfferTitle.getText().toString();
                DialogFragment descriptionFragment = ViewOfferDetailsFragment.newInstance(description,title);
                if(getFragmentManager() !=null) {
                    descriptionFragment.show(getFragmentManager(), ViewOfferDetailsFragment.TAG);
                }
                else{
                    Log.e(TAG,"SupportFragmentManager is null");
                }
            }
        }
    }

}
