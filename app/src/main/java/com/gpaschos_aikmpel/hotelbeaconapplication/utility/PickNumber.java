package com.gpaschos_aikmpel.hotelbeaconapplication.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class PickNumber extends ConstraintLayout {

    private OnValueChangedListener onValueChangedListener;
    private TypedArray typedArray;

    private int minValue;
    private int maxValue;
    private TextView tvNumber;
    private TextView tvLabel;

    private static final int DEFAULT_MIN_VALUE = 0;

    private static final int DEFAULT_MAX_VALUE = 99;

    private static final int DEFAULT_VALUE = 0;

    private static final int DEFAULT_TEXT_SIZE = 14; //<--SP Value

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


        int minValue = typedArray.getInt(R.styleable.PickNumber_minValue, DEFAULT_MIN_VALUE);
        setMinValue(minValue);

        int maxValue = typedArray.getInt(R.styleable.PickNumber_maxValue, DEFAULT_MAX_VALUE);
        setMaxValue(maxValue);

        int defaultValue = typedArray.getInt(R.styleable.PickNumber_defaultValue, DEFAULT_VALUE);
        setValue(defaultValue);

        String label = typedArray.getString(R.styleable.PickNumber_label);
        if (label != null && !label.isEmpty()) {
            setLabel(label);
        }

        //tvNumber.setText(String.valueOf(DEFAULT_VALUE));

        int defaultTextSizeInPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE, context.getResources().getDisplayMetrics());
        float textSizeInPX = typedArray.getDimensionPixelSize(R.styleable.PickNumber_textSize, defaultTextSizeInPX);

        tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPX);
        tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPX);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
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
        if (onValueChangedListener != null) {
            int oldValue = getValue();
            onValueChangedListener.onValueChanged(oldValue, value);
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

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    public interface OnValueChangedListener {
        void onValueChanged(int oldValue, int newValue);
    }

}
