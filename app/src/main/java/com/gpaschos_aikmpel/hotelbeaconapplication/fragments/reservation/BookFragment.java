package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCashPoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.ScheduleNotifications;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookFragment extends Fragment implements JsonListener {

    public static final int LOYALTY_FRAGMENT = 1;

    public static final String ROOM_TYPE_ID_KEY = "room_type_id_KEY";
    public static final String ROOM_ARRIVAL_KEY = "arrival_KEY";
    public static final String ROOM_DEPARTURE_KEY = "departure_KEY";
    public static final String ROOM_ADULTS_KEY = "adults_KEY";
    public static final String ROOM_CHILDREN_KEY = "Children_KEY";

    private int roomTypeID;
    private String arrival, departure;
    private int adults, children;

    private int days, roomPrice;

    private int cashPrice;
    private int freeNights = 0, cashNights = 0;

    private Button btnBook;
    private TextView tvTotalPrice, tvPoints;
    private Group group;

    private ReservationCallbacks listener;

    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance(int roomTypeID, String arrival, String departure, int adults, int children) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putInt(ROOM_TYPE_ID_KEY, roomTypeID);
        args.putString(ROOM_ARRIVAL_KEY, arrival);
        args.putString(ROOM_DEPARTURE_KEY, departure);
        args.putInt(ROOM_ADULTS_KEY, adults);
        args.putInt(ROOM_CHILDREN_KEY, children);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomTypeID = getArguments().getInt(ROOM_TYPE_ID_KEY);
            arrival = getArguments().getString(ROOM_ARRIVAL_KEY);
            departure = getArguments().getString(ROOM_DEPARTURE_KEY);
            adults = getArguments().getInt(ROOM_ADULTS_KEY);
            children = getArguments().getInt(ROOM_CHILDREN_KEY);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReservationCallbacks) {
            listener = (ReservationCallbacks) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement ReservationCallbacks");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book, container, false);

        TextView tvCheckIn = v.findViewById(R.id.tvBookCheckIn);
        TextView tvCheckOut = v.findViewById(R.id.tvBookCheckOut);
        TextView tvRoomTitle = v.findViewById(R.id.tvBookRoomTitle);
        TextView tvAdults = v.findViewById(R.id.tvBookAdults);
        TextView tvChildren = v.findViewById(R.id.tvBookChildren);
        TextView tvRoomPrice = v.findViewById(R.id.tvBookRoomPrice);
        TextView tvDays = v.findViewById(R.id.tvBookDays);
        tvPoints = v.findViewById(R.id.tvBookPoints);
        tvTotalPrice = v.findViewById(R.id.tvBookTotalPrice);
        ImageView ivRoomImage = v.findViewById(R.id.ivBookRoomImage);
        btnBook = v.findViewById(R.id.btnBookConfirm);
        btnBook.setOnClickListener(clickListener);
        Button btnLoyalty = v.findViewById(R.id.btnBookLoyalty);
        btnLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int customerID = RoomDB.getInstance(getContext()).customerDao().getCustomer().getCustomerId();

                Map<String, String> params = new HashMap<>();
                params.put(POST.loyaltyProgramCustomerID, String.valueOf(customerID));
                VolleyQueue.getInstance(getContext()).jsonRequest(BookFragment.this, URL.totalPointsUrl, params);
            }
        });
        CheckBox cbTerms = v.findViewById(R.id.cbBookTerms);
        group = v.findViewById(R.id.groupBookPoints);

        cbTerms.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnBook.setEnabled(true);
                } else {
                    btnBook.setEnabled(false);
                }
            }
        });

        RoomType roomType = RoomDB.getInstance(getContext()).roomTypeDao().getRoomTypeById(roomTypeID);
        RoomTypeCash roomTypeCash = RoomDB.getInstance(getContext()).roomTypeCashDao().getRoomTypeCash(roomTypeID, adults, children, 1);

        SimpleDateFormat localizedFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar calendarArrival = Calendar.getInstance();
        Calendar calendarDeparture = Calendar.getInstance();
        String arrivalLocalString = "", departureLocalString = "";
        try {
            calendarArrival.setTime(sqlFormat.parse(arrival));
            calendarDeparture.setTime(sqlFormat.parse(departure));
            arrivalLocalString = localizedFormat.format(calendarArrival.getTime());
            departureLocalString = localizedFormat.format(calendarDeparture.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long reservationDaysInMillis = calendarDeparture.getTimeInMillis() - calendarArrival.getTimeInMillis();
        days = (int) (reservationDaysInMillis / 1000 / 60 / 60 / 24);
        roomPrice = roomTypeCash.getCash();
        tvRoomTitle.setText(roomType.getName());
        tvTotalPrice.setText(String.valueOf(roomPrice * days));
        tvDays.setText(String.valueOf(days));
        tvRoomPrice.setText(String.valueOf(roomPrice));
        tvAdults.setText(String.valueOf(adults));
        tvChildren.setText(String.valueOf(children));
        tvCheckIn.setText(arrivalLocalString);
        tvCheckOut.setText(departureLocalString);
        ivRoomImage.setImageBitmap(LocalVariables.readImage(getContext(), roomType.getImage()));

        return v;
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {

        switch (url) {
            case URL.bookUrl:

                int resID = json.getInt(POST.bookRoomReservationID);
                String bookedDate = JSONHelper.getString(json, POST.bookRoomBookedDate);
                String modified = JSONHelper.getString(json, POST.bookRoomModified);

                RoomDB.getInstance(getContext()).reservationDao().insert(new Reservation(resID, roomTypeID, adults, children, bookedDate, arrival, departure, modified));

                ScheduleNotifications.checkInNotification(getContext(), resID);
                ScheduleNotifications.checkoutNotification(getContext(), departure);

                listener.showBooked(resID);
                break;
            case URL.totalPointsUrl:

                int totalPoints = json.getInt(POST.totalPoints);

                RoomDB roomDB = RoomDB.getInstance(getContext());
                RoomTypePoints roomTypeFreeNightsPoints = roomDB.roomTypePointsDao().getRoomTypePoints(roomTypeID, adults);
                int freeNightPoints = roomTypeFreeNightsPoints.getSpendingPoints();

                RoomTypeCashPoints roomTypePointsAndCash = roomDB.roomTypeCashPointsDao().getRoomTypeCashPoints(roomTypeID, adults, 1);
                int cashPoints = roomTypePointsAndCash.getPoints();
                cashPrice = roomTypePointsAndCash.getCash();

                DialogFragment dialogFragment = UseLoyaltyPointsFragment.newInstance(totalPoints, freeNightPoints, cashPoints, cashPrice, this.days);
                dialogFragment.setTargetFragment(this, LOYALTY_FRAGMENT);
                if (getFragmentManager() != null) {
                    dialogFragment.show(getFragmentManager(), UseLoyaltyPointsFragment.TAG);
                }
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    public void onLoyaltyPicked(int freeNights, int cashNights, int totalPoints) {
        this.freeNights = freeNights;
        this.cashNights = cashNights;
        int price = roomPrice * days - roomPrice * freeNights + cashNights * (cashPrice - roomPrice);
        tvTotalPrice.setText(String.valueOf(price));
        tvPoints.setText(String.valueOf(totalPoints));
        if (totalPoints > 0) {
            group.setVisibility(View.VISIBLE);
        } else {
            group.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View fragmentView = getChildFragmentManager().findFragmentById(R.id.fmBookCreditCard).getView();

            if (fragmentView == null) {
                throw new RuntimeException("Fragment is not found");
            }

            EditText etCreditCard = fragmentView.findViewById(R.id.etCreditCardCard);
            EditText etHoldersName = fragmentView.findViewById(R.id.etCreditCardHoldersName);
            EditText etCVV = fragmentView.findViewById(R.id.etCreditCardCVV);

            Spinner spMonth = fragmentView.findViewById(R.id.spCreditCardExpMonth);
            Spinner spYear = fragmentView.findViewById(R.id.spCreditCardExpYear);

            etCreditCard.setError(null);
            etHoldersName.setError(null);
            boolean flag = false;

            String creditCard = etCreditCard.getText().toString().replaceAll("\\s", "");
            String holdersName = etHoldersName.getText().toString();
            if (!Validation.checkCreditCard(creditCard)) {
                etCreditCard.setError("Please enter a valid card");
                flag = true;
            }

            if (!Validation.checkLength(holdersName, 4, null)) {
                etHoldersName.setError("Please enter a valid name");
                flag = true;
            }
            if (flag) return;

            String month = String.valueOf(spMonth.getSelectedItem());
            String year = String.valueOf(spYear.getSelectedItem());
            String cvv = etCVV.getText().toString();

            Map<String, String> values = new HashMap<>();

            values.put(POST.bookRoomTypeID, String.valueOf(roomTypeID));
            values.put(POST.bookRoomArrival, arrival);
            values.put(POST.bookRoomDeparture, departure);
            values.put(POST.bookRoomAdults, (String.valueOf(adults)));
            values.put(POST.bookRoomChildren, (String.valueOf(children)));
            values.put(POST.bookRoomFreeNights, String.valueOf(freeNights));
            values.put(POST.bookRoomCashNights, String.valueOf(cashNights));

            values.put(POST.bookRoomCreditCard, creditCard);
            values.put(POST.bookRoomHoldersName, holdersName);
            values.put(POST.bookRoomExpMonth, month);
            values.put(POST.bookRoomExpYear, year);
            values.put(POST.bookRoomCVV, cvv);

            int customerID = RoomDB.getInstance(getContext()).customerDao().getCustomer().getCustomerId();

            values.put(POST.bookRoomCustomerID, String.valueOf(customerID));

            VolleyQueue.getInstance(getContext()).jsonRequest(BookFragment.this, URL.bookUrl, values);
        }
    };
}