package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desktop on 9/3/2018.
 */

public class MyRoomsAdapter extends RecyclerView.Adapter<MyRoomsAdapter.MyViewHolder> {
    private List<ModelRoomView> listItems;

    public MyRoomsAdapter(List<ModelRoomView> mhtsos){
        listItems = mhtsos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.reservation_room_view;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.tvRoomDescription.setText(arrayString[position]);
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRoomImage;
        TextView tvRoomTitle;
        TextView tvRoomDescription;
        TextView tvRoomPrice;
        Button btnRoomBook;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnRoomBook = itemView.findViewById(R.id.btnRoomBook);
            tvRoomDescription = itemView.findViewById(R.id.tvRoomShortDescription);
            tvRoomPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvRoomTitle = itemView.findViewById(R.id.tvRoomTitle);
            ivRoomImage = itemView.findViewById(R.id.ivRoomImage);

        }

        public void bind(int position){
            tvRoomDescription.setText(listItems.get(position).description);
            tvRoomTitle.setText(listItems.get(position).title);
            ivRoomImage.setImageBitmap(listItems.get(position).imgBitmap);
            tvRoomPrice.setText(String.valueOf(listItems.get(position).price));
        }
    }

    public static class ModelRoomView {
        String description;
        String title;
        int price;
        Bitmap imgBitmap;

        public ModelRoomView(String description, String title, int price,Bitmap imgBitmap){
            this.description= description;
            this.price=price;
            this.title=title;
            this.imgBitmap=imgBitmap;
        }

    }
}