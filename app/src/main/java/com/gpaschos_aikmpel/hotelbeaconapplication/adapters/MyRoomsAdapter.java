package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

import java.util.List;

public class MyRoomsAdapter extends RecyclerView.Adapter<MyRoomsAdapter.MyViewHolder> {

    private List<ModelRoomView> roomList;
    private ClickCallbacks clickCallbacks;

    public MyRoomsAdapter(ClickCallbacks clickCallbacks, List<ModelRoomView> roomList) {
        this.roomList = roomList;
        this.clickCallbacks = clickCallbacks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.reservation_room_view;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivRoomImage;
        private TextView tvRoomTitle;
        private TextView tvRoomDescription;
        private TextView tvRoomPrice;
        private Button btnRoomBook;

        MyViewHolder(View itemView) {
            super(itemView);
            btnRoomBook = itemView.findViewById(R.id.btnRoomBook);
            tvRoomDescription = itemView.findViewById(R.id.tvRoomShortDescription);
            tvRoomPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvRoomTitle = itemView.findViewById(R.id.tvRoomTitle);
            ivRoomImage = itemView.findViewById(R.id.ivRoomImage);
            ivRoomImage.setOnClickListener(this);
            btnRoomBook.setOnClickListener(this);
        }

        void bind(int position) {
            tvRoomDescription.setText(roomList.get(position).description);
            tvRoomTitle.setText(roomList.get(position).title);
            ivRoomImage.setImageBitmap(roomList.get(position).imgBitmap);
            tvRoomPrice.setText(String.valueOf(roomList.get(position).price));
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == ivRoomImage.getId()) {
                BitmapDrawable drawable = (BitmapDrawable) ((ImageView) view).getDrawable();
                clickCallbacks.imgClicked(drawable.getBitmap());
            } else if (view.getId() == btnRoomBook.getId()) {
                int positionInList = getAdapterPosition();
                clickCallbacks.bookRoom(roomList.get(positionInList));
            }
        }
    }

    public interface ClickCallbacks {
        void imgClicked(Bitmap bitmap);

        void bookRoom(ModelRoomView obj);
    }

    public static class ModelRoomView {

        String description;
        public String title;
        public int price;
        public Bitmap imgBitmap;

        public ModelRoomView(String description, String title, int price, Bitmap imgBitmap) {
            this.description = description;
            this.price = price;
            this.title = title;
            this.imgBitmap = imgBitmap;
        }
    }
}