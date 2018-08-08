package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.ExclusiveOffer;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferExclusiveFragment extends Fragment {

    private List<ExclusiveOffer> offersList;
    public OfferExclusiveFragment() {
        // Required empty public constructor
    }

    public static OfferExclusiveFragment newInstance() {

        Bundle args = new Bundle();

        OfferExclusiveFragment fragment = new OfferExclusiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offersList = RoomDB.getInstance(getContext()).exclusiveOfferDao().getExclusiveOffers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_exclusive, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvOfferExclusive);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter myAdapter = new MyExclusiveOffersAdapter(offersList);
        recyclerView.setAdapter(myAdapter);

        return view;
    }

    private class MyExclusiveOffersAdapter extends RecyclerView.Adapter<MyExclusiveOffersAdapter.MyExclusiveOffersViewHolder>{

        private List<ExclusiveOffer> offersList;

        public MyExclusiveOffersAdapter(List<ExclusiveOffer> offersList){
            this.offersList = offersList;
        }

        @NonNull
        @Override
        public MyExclusiveOffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            int layoutId = R.layout.viewholder_offers;
            View view = layoutInflater.inflate(layoutId,parent, false);
            return new MyExclusiveOffersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyExclusiveOffersViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return offersList.size();
        }

        class MyExclusiveOffersViewHolder extends RecyclerView.ViewHolder{

            TextView tvTitle;
            TextView tvDiscountPrice;

            public MyExclusiveOffersViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvOfferTitle);
                tvDiscountPrice = itemView.findViewById(R.id.tvOfferDiscountPrice);
            }

            public void bind(int position){
                ExclusiveOffer offer = offersList.get(position);
                tvDiscountPrice.setText(offer.getPriceDiscount());
                tvTitle.setText(offer.getDescription());

            }
        }
    }

}
