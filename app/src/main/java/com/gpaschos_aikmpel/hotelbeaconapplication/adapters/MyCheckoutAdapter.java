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

public class MyCheckoutAdapter extends RecyclerView.Adapter<MyCheckoutAdapter.MyViewHolder> {

    private List<ChargeModel> pricesList;
    private ClickCallbacks clickCallbacks;

    public MyCheckoutAdapter(ClickCallbacks clickCallbacks, List<ChargeModel> pricesList) {
        this.clickCallbacks = clickCallbacks;
        this.pricesList = pricesList;
    }

    public MyCheckoutAdapter(List<ChargeModel> pricesList) {
        this.pricesList = pricesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.viewholder_checkout;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return pricesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvService;
        private TextView tvPrice;


        MyViewHolder(View itemView) {
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

    public interface ClickCallbacks {
        void checkOut(ChargeModel obj);
        void checkIn(ChargeModel obj);
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