package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.RoomServiceActivity;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodChooseFragment extends DialogFragment {

    private static final String PARCEL_KEY = "name_kEY";

    private TextView tvPrice;
    private TextView tvTotalPrice;
    private TextView tvQuantity;

    private RoomServiceActivity.RoomServiceModel.FoodModel parcelable;
    private FragmentCallBack listener;

    public FoodChooseFragment() {
        // Required empty public constructor
    }

    public static FoodChooseFragment newInstance(RoomServiceActivity.RoomServiceModel.FoodModel foodModel) {
        FoodChooseFragment foodChooseFragment = new FoodChooseFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCEL_KEY, foodModel);
        foodChooseFragment.setArguments(args);
        return foodChooseFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            parcelable = args.getParcelable(PARCEL_KEY);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FragmentCallBack) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_choose, container, false);

        tvQuantity = rootView.findViewById(R.id.tvChooseFoodQuantity);
        TextView tvName = rootView.findViewById(R.id.tvChooseFoodName);
        tvPrice = rootView.findViewById(R.id.tvChooseFoodPrice);
        TextView tvDesc = rootView.findViewById(R.id.tvChooseFoodDescription);
        tvTotalPrice = rootView.findViewById(R.id.tvChooseFoodTotalPrice);
        ImageView ivMinusButton = rootView.findViewById(R.id.ivChooseFoodMinusButton);
        ImageView ivPlusButton = rootView.findViewById(R.id.ivChooseFoodPlusButton);
        FloatingActionButton fab = rootView.findViewById(R.id.fabChooseFood);

        ivMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(tvQuantity.getText().toString());

                if (quantity > 1) {
                    tvQuantity.setText(String.valueOf(--quantity));
                    double price = Double.parseDouble(tvPrice.getText().toString());
                    tvTotalPrice.setText(String.valueOf(Math.round(price * quantity * 100) / 100.0));
                }


            }
        });

        ivPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(tvQuantity.getText().toString());

                if (quantity < 10) {
                    tvQuantity.setText(String.valueOf(++quantity));
                    double price = Double.parseDouble(tvPrice.getText().toString());
                    tvTotalPrice.setText(String.valueOf(Math.round(price * quantity * 100) / 100.0));
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.receiveFoodQuantity(parcelable, Integer.parseInt(tvQuantity.getText().toString()));
                dismiss();
            }
        });
        tvName.setText(parcelable.getName());
        tvDesc.setText(parcelable.getDescription());
        tvPrice.setText(String.format(Locale.getDefault(), "%.2f", parcelable.getPrice()));
        tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f", parcelable.getPrice()));

        return rootView;
    }

    public interface FragmentCallBack {
        void receiveFoodQuantity(RoomServiceActivity.RoomServiceModel.FoodModel foodModel, int quantity);
    }

}
