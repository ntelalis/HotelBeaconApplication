package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodOrderView> foodList;

    public FoodAdapter() {
        this.foodList = new ArrayList<>();
    }

    public void addFood(FoodOrderView foodOrderView) {
        this.foodList.add(foodOrderView);
        notifyDataSetChanged();
    }

    public List<FoodOrderView> getFoodList() {
        return foodList;
    }

    public double totalCost() {
        double total = 0;
        for (FoodOrderView foodOrderView : foodList) {
            total += Math.round(foodOrderView.price * foodOrderView.quantity * 100.0) / 100.0;
        }
        return Math.round(total * 100.0) / 100.0;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.viewholder_food_order;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFoodOrderTitle;
        private TextView tvFoodOrderQuantity;
        private TextView tvFoodOrderPrice;

        FoodViewHolder(View itemView) {
            super(itemView);
            tvFoodOrderTitle = itemView.findViewById(R.id.tvBottomSheetHolderFoodName);
            tvFoodOrderQuantity = itemView.findViewById(R.id.tvBottomSheetHolderQuantity);
            tvFoodOrderPrice = itemView.findViewById(R.id.tvBottomSheetHolderPrice);
        }

        void bind(int position) {
            tvFoodOrderTitle.setText(foodList.get(position).title);
            tvFoodOrderQuantity.setText(String.valueOf(foodList.get(position).quantity));
            tvFoodOrderPrice.setText(String.valueOf(foodList.get(position).price));
        }
    }

    public static class FoodOrderView {

        public int id;
        public String title;
        public int quantity;
        public double price;

        public FoodOrderView(int id, String title, int quantity, double price) {
            this.id = id;
            this.title = title;
            this.quantity = quantity;
            this.price = price;
        }
    }
}