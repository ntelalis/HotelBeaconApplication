package com.gpaschos_aikmpel.hotelbeaconapplication.utility;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class PickNumber extends ConstraintLayout {


    private OnPickedNumber listener;

    private View rootView;

    private TextView tvNumber;
    private View btnMinus, btnPlus;

    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;
    private int defaultValue = 0;

    public PickNumber(Context context) {
        super(context);
        init(context);
    }

    public PickNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PickNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.picknumber, this);
        tvNumber = rootView.findViewById(R.id.tvNumber);
        btnMinus = rootView.findViewById(R.id.ivMinus);
        btnPlus = rootView.findViewById(R.id.ivPlus);


        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
                Log.d("MMMMMM", getId() + "");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        setValue(defaultValue);
    }


    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue() {
        return Integer.valueOf(tvNumber.getText().toString());
    }

    public void setValue(int newValue) {
        int value = newValue;
        if (newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }
        tvNumber.setText(String.valueOf(value));
        if(listener!=null){
            listener.value(getId(), value);
        }
    }


    private void decrement() {
        int value = getValue();
        if (value > minValue) {
            setValue(value - 1);
        }
    }

    private void increment() {
        int value = getValue();
        if (value < maxValue) {
            setValue(value + 1);
        }
    }

    public void setListener(OnPickedNumber onPickedNumber) {

        listener = onPickedNumber;

    }

    public interface OnPickedNumber {
        void value(int id, int value);
    }


}
