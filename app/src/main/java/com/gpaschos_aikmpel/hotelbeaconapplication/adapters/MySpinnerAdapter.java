package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.IDValue;

import java.util.List;

public class MySpinnerAdapter<T extends IDValue> extends ArrayAdapter<T> implements android.widget.SpinnerAdapter {

    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView tv = convertView.findViewById(android.R.id.text1);
        T t = getItem(position);
        if (t != null) {
            tv.setText(t.getValue());
        }
        return convertView;
    }
}
