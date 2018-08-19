package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

import java.util.List;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder> {

    private List<ChargeModel> pricesList;

    public CheckOutAdapter(List<ChargeModel> pricesList) {
        this.pricesList = pricesList;
    }

    @NonNull
    @Override
    public CheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.viewholder_checkout;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        return new CheckOutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return pricesList.size();
    }

    public class CheckOutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvService;
        private TextView tvPrice;


        CheckOutViewHolder(View itemView) {
            super(itemView);

            tvService = itemView.findViewById(R.id.tvVieHCheckoutService);
            tvPrice = itemView.findViewById(R.id.tvViewHCheckoutPrice);
            //btnCheckInCheckOut.setOnClickListener(this);

        }

        void bind(int position) {
            tvService.setText(pricesList.get(position).service);
            tvPrice.setText(String.valueOf(pricesList.get(position).price));
        }

        @Override
        public void onClick(View view) {

        }

    }

    public static class ChargeModel {
        public String service;
        public double price;

        public ChargeModel(String service, double price) {
            this.service = service;
            this.price = price;
        }
    }


}