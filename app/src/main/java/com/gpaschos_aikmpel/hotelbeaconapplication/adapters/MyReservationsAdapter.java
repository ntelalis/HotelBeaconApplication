package com.gpaschos_aikmpel.hotelbeaconapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;

import java.util.Calendar;
import java.util.List;

public class MyReservationsAdapter extends RecyclerView.Adapter<MyReservationsAdapter.MyViewHolder> {

    private List<ReservationModel> reservationsList;
    private ClickCallbacks clickCallbacks;

    public MyReservationsAdapter(ClickCallbacks clickCallbacks, List<ReservationModel> reservationsList) {
        this.clickCallbacks = clickCallbacks;
        this.reservationsList = reservationsList;
    }

    public MyReservationsAdapter(List<ReservationModel> reservationsList) {
        this.reservationsList = reservationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.viewholder_upcomingreservations;
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
        return reservationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvReservationID;
        private TextView tvRoomTitle;
        private TextView tvArrival;
        private TextView tvDeparture;
        private TextView tvAdults;
        private Button btnCheckInCheckOut;
        private TextView tvCheckedinRoomLabel;
        private TextView tvCheckedinRoom;
        private TextView tvCheckinCheckoutInstructions;
        private String roomNo;


        MyViewHolder(View itemView) {
            super(itemView);
            tvReservationID = itemView.findViewById(R.id.tvUpcomingReservationsReservationID);
            tvRoomTitle = itemView.findViewById(R.id.tvUpcomingReservationsRoomTitle);
            tvAdults = itemView.findViewById(R.id.tvUpcomingReservationsAdults);
            tvArrival = itemView.findViewById(R.id.tvUpcomingReservationsArrival);
            tvDeparture = itemView.findViewById(R.id.tvUpcomingReservationsDeparture);
            btnCheckInCheckOut = itemView.findViewById(R.id.btnUpcomingReservationsCheckinCheckOut);
            tvCheckinCheckoutInstructions = itemView.findViewById(R.id.tvUpcomingReservationsInstructions);
            tvCheckedinRoomLabel = itemView.findViewById(R.id.tvViewHmyReservationsRoomNoLabel);
            tvCheckedinRoom = itemView.findViewById(R.id.tvViewHUpcomingReservationsRoomNo);
            btnCheckInCheckOut.setOnClickListener(this);

            //ivRoomImage.setOnClickListener(this);
        }

        void bind(int position) {
            tvRoomTitle.setText(reservationsList.get(position).roomTitle);
            tvReservationID.setText(String.valueOf(reservationsList.get(position).reservationID));
            tvArrival.setText(reservationsList.get(position).arrival);
            tvDeparture.setText(reservationsList.get(position).departure);
            tvAdults.setText(String.valueOf(reservationsList.get(position).adults));

            buttonAndTextViewsHandler(reservationsList.get(position).checkincheckoutbtn,
                    reservationsList.get(position).room);

            roomNo=reservationsList.get(position).room;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == btnCheckInCheckOut.getId()) {
                if(roomNo==null) {
                    clickCallbacks.checkIn(reservationsList.get(getAdapterPosition()));
                }
                else{
                    clickCallbacks.checkOut(reservationsList.get(getAdapterPosition()));
                }
            }
        }

        public void buttonAndTextViewsHandler(int checkincheckoutbtn, String room){
            switch(checkincheckoutbtn){
                case Params.NOTeligibleForCheckin:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckin);
                    btnCheckInCheckOut.setEnabled(false);
                    tvCheckedinRoom.setVisibility(View.INVISIBLE);
                    tvCheckedinRoomLabel.setVisibility(View.INVISIBLE);
                    tvCheckinCheckoutInstructions.setText(R.string.tvViewHmyReservationsCheckInInstructionsFALSE);
                    break;
                case Params.eligibleForCheckin:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckin);
                    btnCheckInCheckOut.setEnabled(true);
                    tvCheckedinRoom.setVisibility(View.INVISIBLE);
                    tvCheckedinRoomLabel.setVisibility(View.INVISIBLE);
                    tvCheckinCheckoutInstructions.setText(R.string.tvViewHmyReservationsCheckInInstructionsTRUE);
                    break;
                case Params.NOTeligibleForCheckout:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckout);
                    btnCheckInCheckOut.setEnabled(false);
                    tvCheckedinRoom.setText(room);
                    tvCheckedinRoom.setVisibility(View.VISIBLE);
                    tvCheckedinRoomLabel.setVisibility(View.VISIBLE);
                    tvCheckinCheckoutInstructions.setText(R.string.tvviewHmyReservationsCheckOutInstructionsFALSE);
                    break;
                case Params.eligibleForCheckout:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckout);
                    btnCheckInCheckOut.setEnabled(true);
                    tvCheckedinRoom.setText(room);
                    tvCheckedinRoom.setVisibility(View.VISIBLE);
                    tvCheckedinRoomLabel.setVisibility(View.VISIBLE);
                    tvCheckinCheckoutInstructions.setText(R.string.tvviewHmyReservationsCheckOutInstructionsTRUE);
                    break;
                case Params.CheckedOut:
                    btnCheckInCheckOut.setText(R.string.btnUpcomingReservationsCheckedOut);
                    btnCheckInCheckOut.setEnabled(false);
                    tvCheckedinRoom.setText(room);
                    tvCheckedinRoom.setVisibility(View.VISIBLE);
                    tvCheckedinRoomLabel.setVisibility(View.VISIBLE);
                    tvCheckinCheckoutInstructions.setVisibility(View.INVISIBLE);

            }
        }
    }

    public interface ClickCallbacks {
        void checkOut(ReservationModel obj);
        void checkIn(ReservationModel obj);
    }

    public static class ReservationModel {
        public int adults;
        public String roomTitle;
        public int reservationID;
        public String arrival;
        public String departure;
        public String room;
        public int checkincheckoutbtn;

        public ReservationModel(int adults, String roomTitle, int reservationID, String arrival,
                                String departure, int checkinCheckoutbtn, String room) {
            this.adults = adults;
            this.roomTitle = roomTitle;
            this.reservationID = reservationID;
            this.arrival = arrival;
            this.departure = departure;
            this.checkincheckoutbtn = checkinCheckoutbtn;
            this.room = room;
        }
    }


}