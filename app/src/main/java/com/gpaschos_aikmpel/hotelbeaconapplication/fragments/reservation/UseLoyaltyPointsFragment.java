package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.PickNumber;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class UseLoyaltyPointsFragment extends DialogFragment {

    private static final String TOTAL_POINTS = "total_points";
    private static final String TOTAL_DAYS = "total_days";
    private static final String POINTS_FREE = "free_night_points";
    private static final String POINTS_CASH = "cash_and_points";
    private static final String PRICE_CASH = "price_for_cash_and_points";

    public static final String TAG = UseLoyaltyPointsFragment.class.getSimpleName();


    private PickNumber pnFreeValue, pnCashValue;

    private TextView tvSelectedDays;
    private TextView tvSelectedTotal;

    private int totalPoints;
    private int freePoints, cashPoints;
    private int totalDays;
    private int cashPrice;

    public UseLoyaltyPointsFragment() {
        // Required empty public constructor
    }

    public static UseLoyaltyPointsFragment newInstance(int totalPoints, int freeNightPoints, int cashPoints, int cashPrice,int days) {
        Bundle args = new Bundle();
        args.putInt(TOTAL_POINTS, totalPoints);
        args.putInt(POINTS_FREE, freeNightPoints);
        args.putInt(POINTS_CASH, cashPoints);
        args.putInt(PRICE_CASH, cashPrice);
        args.putInt(TOTAL_DAYS, days);
        UseLoyaltyPointsFragment fragment = new UseLoyaltyPointsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            totalPoints = getArguments().getInt(TOTAL_POINTS);
            freePoints = getArguments().getInt(POINTS_FREE);
            cashPoints = getArguments().getInt(POINTS_CASH);
            cashPrice = getArguments().getInt(PRICE_CASH);
            totalDays = getArguments().getInt(TOTAL_DAYS);
        }

    }


    @NonNull
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = View.inflate(getContext(), R.layout.fragment_use_loyalty_points, null);

        TextView tvTotalPoints = v.findViewById(R.id.tvUsePointsTotalPoints);
        tvTotalPoints.setText(String.valueOf(totalPoints));

        TextView tvFreePoints = v.findViewById(R.id.tvUsePointsFreeNight);
        tvFreePoints.setText(String.valueOf(freePoints));

        TextView tvCashPoints = v.findViewById(R.id.tvUsePointsCashAndPoints);
        tvCashPoints.setText(String.valueOf(cashPoints));

        TextView tvTotalDays = v.findViewById(R.id.tvUsePointsTotalDays);
        tvTotalDays.setText(String.valueOf(totalDays));

        TextView tvCashPrice = v.findViewById(R.id.tvUsePointsCashPointsPrice);
        tvCashPrice.setText(String.valueOf(cashPrice));

        tvSelectedDays = v.findViewById(R.id.tvUsePointsSelectedDays);
        tvSelectedTotal = v.findViewById(R.id.tvUsePointsSelectedTotal);

        pnFreeValue = v.findViewById(R.id.pnUsePointsFreeNight);
        pnCashValue = v.findViewById(R.id.pnUsePointsCashAndPoints);



        int maxFreeNightsByPoints = (totalPoints / freePoints);
        pnFreeValue.setMaxValue(maxFreeNightsByPoints < totalDays ? maxFreeNightsByPoints : totalDays);
        int maxCashNightsByPoints = (totalPoints / cashPoints);
        pnCashValue.setMaxValue(maxCashNightsByPoints < totalDays ? maxCashNightsByPoints : totalDays);

        pnFreeValue.setOnValueChangedListener(new PickNumber.OnValueChangedListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                setValues(newValue,pnCashValue.getValue());
            }
        });

        pnCashValue.setOnValueChangedListener(new PickNumber.OnValueChangedListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                setValues(pnFreeValue.getValue(),newValue);
            }
        });

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Choose your loyalty reward");
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getTargetFragment() != null) {
                    ((BookFragment)getTargetFragment()).onLoyaltyPicked(pnFreeValue.getValue(), pnCashValue.getValue(), Integer.parseInt(tvSelectedTotal.getText().toString()));
                }
            }
        });
        dialog.setNegativeButton("cancel", null);
        dialog.setView(v);
        return dialog.create();

    }

    private void setValues(int freeValue, int cashValue){

        tvSelectedDays.setText(String.valueOf(freeValue+cashValue));

        int freeTotal = freeValue * freePoints;
        int cashTotal = cashValue * cashPoints;

        int maxFreeNights = (totalPoints - cashTotal) / freePoints;
        int freeSelectedDays = totalDays - cashValue;
        pnFreeValue.setMaxValue(maxFreeNights < freeSelectedDays ? maxFreeNights : freeSelectedDays);
        int maxCashNights = (totalPoints - freeTotal) / cashPoints;
        int cashSelectedDays = totalDays - freeValue;
        pnCashValue.setMaxValue(maxCashNights < cashSelectedDays ? maxCashNights : cashSelectedDays);

        tvSelectedTotal.setText(String.valueOf(freeTotal + cashTotal));
    }

}
