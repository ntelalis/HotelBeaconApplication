package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.GeneralOffer;

import java.util.List;

public class OfferGeneralFragment extends Fragment {

    private List<GeneralOffer> generalOfferList;

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
        RecyclerView recyclerView = v.findViewById(R.id.rvOfferGeneral);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new GeneralOfferAdapter(generalOfferList));

        return v;
    }

    private class GeneralOfferAdapter extends RecyclerView.Adapter<GeneralOfferAdapter.GeneralOfferViewHolder> {

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
            holder.tvOfferTitle.setText(generalOffer.getDescription());
            holder.tvOfferDiscountPrice.setText(generalOffer.getPriceDiscount());
        }

        @Override
        public int getItemCount() {
            return generalOfferList.size();
        }

        class GeneralOfferViewHolder extends RecyclerView.ViewHolder {

            TextView tvOfferTitle;
            TextView tvOfferDetails;
            TextView tvOfferDiscountPrice;

            GeneralOfferViewHolder(View itemView) {
                super(itemView);
                tvOfferTitle = itemView.findViewById(R.id.tvOfferGeneralTitle);
                tvOfferDetails = itemView.findViewById(R.id.tvOfferGeneralDetails);
                tvOfferDiscountPrice = itemView.findViewById(R.id.tvOfferGeneralDiscountPrice);
            }
        }
    }

}
