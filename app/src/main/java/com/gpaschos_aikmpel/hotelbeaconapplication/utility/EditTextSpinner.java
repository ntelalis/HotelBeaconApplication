package com.gpaschos_aikmpel.hotelbeaconapplication.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;

public class EditTextSpinner<T> extends AppCompatEditText {

    CharSequence mHint;

    OnItemSelectedListener<T> onItemSelectedListener;
    ListAdapter mSpinnerAdapter;

    public EditTextSpinner(Context context) {
        super(context);

        mHint = getHint();
    }

    public EditTextSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHint = getHint();
    }

    public EditTextSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHint = getHint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(false);
        setClickable(true);
        setLongClickable(false);
    }

    public void setAdapter(ListAdapter adapter) {
        mSpinnerAdapter = adapter;

        configureOnClickListener();
    }




    private void configureOnClickListener() {

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(mHint);
                builder.setAdapter(mSpinnerAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelectedListener((T)mSpinnerAdapter.getItem(selectedIndex), selectedIndex);
                        }
                    }
                });
                builder.setPositiveButton(android.R.string.cancel, null);
                builder.create().show();
            }
        });
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener<T> {
        void onItemSelectedListener(T item, int selectedIndex);
    }
}