package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.content.Context;
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
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.RoomServiceActivity;

import java.util.List;

public class FoodFragment extends Fragment implements OnClickAddToBasket {

    private static final String listKEY = "model_KEY";

    private List<RoomServiceActivity.RoomServiceModel.FoodModel> foodList;
    private OnClickAddToBasket listener;

    public FoodFragment() {
    }

    public static FoodFragment newInstance(RoomServiceActivity.RoomServiceModel model) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putParcelable(listKEY, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnClickAddToBasket) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Parcelable parcelable = getArguments().getParcelable(listKEY);
            if (parcelable != null) {
                foodList = ((RoomServiceActivity.RoomServiceModel) parcelable).getFoodList();
            }
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rvFoodFragmentRecycler);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        FoodAdapter foodAdapter = new FoodAdapter(foodList, this);
        recyclerView.setAdapter(foodAdapter);
        return v;
    }

    @Override
    public void addBasket(RoomServiceActivity.RoomServiceModel.FoodModel foodModel) {
        listener.addBasket(foodModel);
    }


    public static class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

        private List<RoomServiceActivity.RoomServiceModel.FoodModel> foodModelList;
        private OnClickAddToBasket listener;

        FoodAdapter(List<RoomServiceActivity.RoomServiceModel.FoodModel> foodModelList, OnClickAddToBasket listener) {
            this.foodModelList = foodModelList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.viewholder_food_pick, parent, false);
            return new FoodViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
            holder.name.setText(foodModelList.get(position).getName());
            holder.description.setText(foodModelList.get(position).getDescription());
            holder.price.setText(String.valueOf(foodModelList.get(position).getPrice()));
        }

        @Override
        public int getItemCount() {
            return foodModelList.size();
        }

        public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView price, name, description;

            FoodViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.tvFoodItemName);
                description = itemView.findViewById(R.id.tvFoodItemDescription);
                price = itemView.findViewById(R.id.tvFoodItemPrice);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                listener.addBasket(foodModelList.get(getAdapterPosition()));
            }

        }

    }

}

