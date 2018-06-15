package com.gpaschos_aikmpel.hotelbeaconapplication.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class PickNumber extends ConstraintLayout {

    private OnValueChangedListener onValueChangedListener;

    private TextView tvNumber, tvLabel;
    private TypedArray typedArray;

    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;
    private int defaultValue = 0;

    public PickNumber(Context context) {
        super(context);
        typedArray = context.obtainStyledAttributes(R.styleable.PickNumber);
        init(context);
    }

    public PickNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PickNumber);
        init(context);
    }

    public PickNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PickNumber);
        init(context);
    }

    private void init(Context context) {
        View rootView = inflate(context, R.layout.picknumber, this);
        tvNumber = rootView.findViewById(R.id.tvPickNumberNumber);
        tvLabel = rootView.findViewById(R.id.tvPickNumberLabel);
        View btnMinus = rootView.findViewById(R.id.ivPickNumberMinus);
        View btnPlus = rootView.findViewById(R.id.ivPickNumberPlus);

        tvNumber.setText(String.valueOf(defaultValue));

        int defaultValue = typedArray.getInt(R.styleable.PickNumber_defaultValue, this.defaultValue);
        setValue(defaultValue);

        int minValue = typedArray.getInt(R.styleable.PickNumber_minValue, this.minValue);
        setMinValue(minValue);

        int maxValue = typedArray.getInt(R.styleable.PickNumber_maxValue, this.maxValue);
        setMaxValue(maxValue);

        String text = typedArray.getString(R.styleable.PickNumber_label);
        if (text != null && !text.isEmpty()){
            setLabel(text);
        }

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

    public String getLabel() {
        return tvLabel.getText().toString();
    }

    public void setLabel(String label) {
        tvLabel.setText(label);
        tvLabel.setVisibility(View.VISIBLE);
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
        if(onValueChangedListener !=null){
            int oldValue = getValue();
            onValueChangedListener.onValueChanged(oldValue,value);
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

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener){
        this.onValueChangedListener = onValueChangedListener;
    }

    public interface OnValueChangedListener{
        void onValueChanged(int oldValue, int newValue);
    }


}
