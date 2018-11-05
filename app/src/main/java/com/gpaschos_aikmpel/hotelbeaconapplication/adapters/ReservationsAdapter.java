package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.MyViewHolder> {

    private List<ReservationModel> reservationsList;
    private ClickCallbacks clickCallbacks;

    public ReservationsAdapter(ClickCallbacks clickCallbacks, List<ReservationModel> reservationsList) {
        this.clickCallbacks = clickCallbacks;
        this.reservationsList = reservationsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        //int layoutId = R.layout.viewholder_upcomingreservations;
        int layoutId = R.layout.viewholder_upcomingreservations2;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return reservationsList.size();
    }

    public static int getReservationStatus(Reservation r)  {
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long startDate = 0;
        long endDate = 0;
        try {
            startDate = sqlFormat.parse(r.getStartDate()).getTime();
            endDate = sqlFormat.parse(r.getEndDate()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int reservationStatus;

        if (!r.isCheckedIn())
            if (currentTime < startDate)
                reservationStatus = ReservationsAdapter.ReservationModel.CANNOT_CHECK_IN;
            else
                reservationStatus = ReservationsAdapter.ReservationModel.CAN_CHECK_IN;

        else {
            if (!r.isCheckedOut())
                if (currentTime < endDate)
                    reservationStatus = ReservationsAdapter.ReservationModel.CANNOT_CHECK_OUT;
                else
                    reservationStatus = ReservationsAdapter.ReservationModel.CAN_CHECK_OUT;
            else
                reservationStatus = ReservationsAdapter.ReservationModel.IS_CHECKED_OUT;
        }

        return reservationStatus;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvReservationID;
        private TextView tvRoomTitle;
        private TextView tvArrival;
        private TextView tvDeparture;
        private TextView tvAdults;
        private ImageView ivRoomImage;
        private Button btnCheckInCheckOut;
        private TextView tvCheckInCheckOutInstructions;
        private String roomNo;


        MyViewHolder(View itemView) {
            super(itemView);
            tvReservationID = itemView.findViewById(R.id.tvUpcomingReservationsReservationID);
            tvRoomTitle = itemView.findViewById(R.id.tvUpcomingReservationsRoomTitle);
            ivRoomImage = itemView.findViewById(R.id.ivUpcomingReservation);
            tvAdults = itemView.findViewById(R.id.tvUpcomingReservationsAdults);
            tvArrival = itemView.findViewById(R.id.tvUpcomingReservationsArrival);
            tvDeparture = itemView.findViewById(R.id.tvUpcomingReservationsDeparture);
            btnCheckInCheckOut = itemView.findViewById(R.id.btnUpcomingReservationsCheckInCheckOut);
            tvCheckInCheckOutInstructions = itemView.findViewById(R.id.tvUpcomingReservationsInstructions);
            btnCheckInCheckOut.setOnClickListener(this);
        }

        void bind(int position) {
            tvRoomTitle.setText(reservationsList.get(position).roomTitle);
            tvReservationID.setText(String.valueOf(reservationsList.get(position).reservationID));
            tvArrival.setText(reservationsList.get(position).arrival);
            tvDeparture.setText(reservationsList.get(position).departure);
            tvAdults.setText(String.valueOf(reservationsList.get(position).adults));
            ivRoomImage.setImageBitmap(LocalVariables.readImage(itemView.getContext(),reservationsList.get(position).roomImgFile));
            roomNo = String.valueOf(reservationsList.get(position).room);
            buttonAndTextViewsHandler(reservationsList.get(position).reservationStatus, roomNo);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == btnCheckInCheckOut.getId()) {
                int resID = Integer.parseInt(tvReservationID.getText().toString());
                Reservation r = RoomDB.getInstance(view.getContext()).reservationDao().getReservationByID(resID);
                if (!r.isCheckedIn()) {
                    //clickCallbacks.checkIn(reservationsList.get(getAdapterPosition()));
                    int reservationID = reservationsList.get(getAdapterPosition()).reservationID;
                    clickCallbacks.checkIn(reservationID);
                } else if (r.isCheckedInNotCheckedOut()) {
                    clickCallbacks.checkOut(reservationsList.get(getAdapterPosition()));
                }
            }
        }

        void buttonAndTextViewsHandler(int reservationStatus, String room) {
            Context context = itemView.getContext();
            switch (reservationStatus) {
                case ReservationModel.CANNOT_CHECK_IN:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckin);
                    btnCheckInCheckOut.setEnabled(false);
                    tvCheckInCheckOutInstructions.setText(R.string.tvViewHmyReservationsCheckInInstructionsFALSE);
                    break;
                case ReservationModel.CAN_CHECK_IN:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckin);
                    btnCheckInCheckOut.setEnabled(true);
                    tvCheckInCheckOutInstructions.setText(R.string.tvViewHmyReservationsCheckInInstructionsTRUE);
                    break;
                case ReservationModel.CANNOT_CHECK_OUT:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckout);
                    btnCheckInCheckOut.setEnabled(false);
                    tvCheckInCheckOutInstructions.setText(R.string.tvviewHmyReservationsCheckOutInstructionsFALSE);
                    break;
                case ReservationModel.CAN_CHECK_OUT:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckout);
                    btnCheckInCheckOut.setEnabled(true);
                    tvCheckInCheckOutInstructions.setText(R.string.tvviewHmyReservationsCheckOutInstructionsTRUE);
                    break;
                case ReservationModel.IS_CHECKED_OUT:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckedOut);
                    btnCheckInCheckOut.setEnabled(false);
                    tvCheckInCheckOutInstructions.setVisibility(View.INVISIBLE);
            }
        }


    }

    public interface ClickCallbacks {
        void checkOut(ReservationModel obj);

        //void checkIn(ReservationModel obj);
        void checkIn(int reservationID);
    }

    public static class ReservationModel implements Parcelable {

        public static final int CAN_CHECK_IN = 2;
        public static final int CANNOT_CHECK_IN = 1;
        public static final int CAN_CHECK_OUT = 4;
        public static final int CANNOT_CHECK_OUT = 3;
        public static final int IS_CHECKED_OUT = 5;


        public int adults;
        public String roomTitle;
        public String roomImgFile;
        public int reservationID;
        public String arrival;
        public String departure;
        public int room;
        public int reservationStatus;

        public ReservationModel(int adults, String roomTitle, String roomImgFile, int reservationID, String arrival,
                                String departure, int reservationStatus, int room) {
            this.adults = adults;
            this.roomTitle = roomTitle;
            this.roomImgFile = roomImgFile;
            this.reservationID = reservationID;
            this.arrival = arrival;
            this.departure = departure;
            this.reservationStatus = reservationStatus;
            this.room = room;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.adults);
            dest.writeString(this.roomTitle);
            dest.writeString(this.roomImgFile);
            dest.writeInt(this.reservationID);
            dest.writeString(this.arrival);
            dest.writeString(this.departure);
            dest.writeInt(this.room);
            dest.writeInt(this.reservationStatus);
        }

        ReservationModel(Parcel in) {
            this.adults = in.readInt();
            this.roomTitle = in.readString();
            this.roomImgFile = in.readString();
            this.reservationID = in.readInt();
            this.arrival = in.readString();
            this.departure = in.readString();
            this.room = in.readInt();
            this.reservationStatus = in.readInt();
        }

        public static final Creator<ReservationModel> CREATOR = new Creator<ReservationModel>() {
            @Override
            public ReservationModel createFromParcel(Parcel source) {
                return new ReservationModel(source);
            }

            @Override
            public ReservationModel[] newArray(int size) {
                return new ReservationModel[size];
            }
        };
    }


}