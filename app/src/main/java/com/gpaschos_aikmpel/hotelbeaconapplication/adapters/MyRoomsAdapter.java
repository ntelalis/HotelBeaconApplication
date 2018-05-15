package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
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
        int layoutId = R.layout.viewholder_room_choose;
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
        private TextView tvRoomTotalPrice;
        private TextView tvRoomPricePerNight;
        private TextView tvRoomDays;
        private TextView tvRoomPersons;
        private Button btnRoomBook;
        private Button btnRoomDetails;

        MyViewHolder(View itemView) {
            super(itemView);
            btnRoomBook = itemView.findViewById(R.id.btnRoomBook);
            btnRoomDetails = itemView.findViewById(R.id.btnRoomDetails);
            tvRoomDescription = itemView.findViewById(R.id.tvRoomShortDescription);
            tvRoomTotalPrice = itemView.findViewById(R.id.tvRoomTotalPrice);
            tvRoomPricePerNight = itemView.findViewById(R.id.tvRoomPrice);
            tvRoomDays = itemView.findViewById(R.id.tvRoomDays);
            tvRoomPersons = itemView.findViewById(R.id.tvRoomPersons);
            tvRoomTitle = itemView.findViewById(R.id.tvRoomTitle);
            ivRoomImage = itemView.findViewById(R.id.ivRoomImage);
            ivRoomImage.setOnClickListener(this);
            btnRoomBook.setOnClickListener(this);
            btnRoomDetails.setOnClickListener(this);
        }

        void bind(int position) {
            ModelRoomView mrv = roomList.get(position);

            tvRoomDescription.setText(mrv.description);
            tvRoomTitle.setText(mrv.title);
            ivRoomImage.setImageBitmap(mrv.img);
            tvRoomTotalPrice.setText(String.valueOf(mrv.price * mrv.days * mrv.persons));
            tvRoomPricePerNight.setText(String.valueOf(mrv.price));
            tvRoomDays.setText(String.valueOf(mrv.days));
            tvRoomPersons.setText(String.valueOf(mrv.persons));
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == ivRoomImage.getId()) {
                String imgFileName = roomList.get(getAdapterPosition()).imgFileName;
                clickCallbacks.imgClicked(imgFileName);
            } else if (view.getId() == btnRoomBook.getId()) {
                clickCallbacks.bookRoom(roomList.get(getAdapterPosition()));
            }
        }
    }

    public interface ClickCallbacks {
        void imgClicked(String imgFileName);

        void bookRoom(ModelRoomView obj);
    }

    public static class ModelRoomView {

        public int roomTypeID;
        public String title;
        public String description;
        public int price;
        public Bitmap img;
        public String imgFileName;
        public int days;
        public int persons;

        public ModelRoomView(int roomTypeID, String title, String description, int price, int days, int persons, Bitmap img, String imgFileName) {
            this.roomTypeID = roomTypeID;
            this.title = title;
            this.description = description;
            this.price = price;
            this.img = img;
            this.imgFileName = imgFileName;
            this.days = days;
            this.persons = persons;
        }
    }
}