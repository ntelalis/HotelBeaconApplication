package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
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
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Loyalty;
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

    private String phone, address1, address2, city, postalCode;
    private TextInputEditText tietPhone, tietAddress1, tietAddress2, tietCity, tietPostalCode;
    private Customer customer;
    private int days, roomPrice;

    private int cashPrice;
    private int freeNights = 0, cashNights = 0;

    private View contactInfoView, creditCardView ;

    private Button btnBook;
    private TextView tvTotalPrice, tvPoints, tvPlus;
    private Group groupPoints, groupCash;

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

        customer = RoomDB.getInstance(getContext()).customerDao().getCustomer();
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
        contactInfoView = getChildFragmentManager().findFragmentById(R.id.fmBookContactInfo).getView();
        creditCardView = getChildFragmentManager().findFragmentById(R.id.fmBookCreditCard).getView();
        TextView tvCheckIn = v.findViewById(R.id.tvBookCheckIn);
        TextView tvCheckOut = v.findViewById(R.id.tvBookCheckOut);
        TextView tvRoomTitle = v.findViewById(R.id.tvBookRoomTitle);
        TextView tvAdults = v.findViewById(R.id.tvBookAdults);
        TextView tvChildren = v.findViewById(R.id.tvBookChildren);
        TextView tvRoomPrice = v.findViewById(R.id.tvBookRoomPrice);
        TextView tvDays = v.findViewById(R.id.tvBookDays);
        tvPoints = v.findViewById(R.id.tvBookPoints);
        tvTotalPrice = v.findViewById(R.id.tvBookTotalPrice);
        tvPlus = v.findViewById(R.id.tvBookPointsLabel);
        ImageView ivRoomImage = v.findViewById(R.id.ivBookRoomImage);
        btnBook = v.findViewById(R.id.btnBookConfirm);
        btnBook.setOnClickListener(clickListener);
        Button btnLoyalty = v.findViewById(R.id.btnBookLoyalty);
        btnLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loyalty loyalty = RoomDB.getInstance(getContext()).loyaltyDao().getLoyalty();
                int totalPoints = loyalty.getCurrentPoints();

                RoomDB roomDB = RoomDB.getInstance(getContext());
                RoomTypePoints roomTypeFreeNightsPoints = roomDB.roomTypePointsDao().getRoomTypePoints(roomTypeID, adults);
                int freeNightPoints = roomTypeFreeNightsPoints.getSpendingPoints();

                RoomTypeCashPoints roomTypePointsAndCash = roomDB.roomTypeCashPointsDao().getRoomTypeCashPoints(roomTypeID, adults);
                int cashPoints = roomTypePointsAndCash.getPoints();

                cashPrice = roomTypePointsAndCash.getCash();
                int totalPrice = Integer.parseInt(tvTotalPrice.getText().toString());
                DialogFragment dialogFragment = UseLoyaltyPointsFragment.newInstance(totalPrice, totalPoints, freeNightPoints, cashPoints, cashPrice, days);
                dialogFragment.setTargetFragment(BookFragment.this, LOYALTY_FRAGMENT);
                if (getFragmentManager() != null) {
                    dialogFragment.show(getFragmentManager(), UseLoyaltyPointsFragment.TAG);
                }
            }
        });
        CheckBox cbTerms = v.findViewById(R.id.cbBookTerms);
        groupPoints = v.findViewById(R.id.groupBookPoints);
        groupCash = v.findViewById(R.id.groupBookCash);

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

        tietPhone = contactInfoView.findViewById(R.id.tietContactPhone);
        tietAddress1 = contactInfoView.findViewById(R.id.tietContactAddress1);
        tietAddress2 = contactInfoView.findViewById(R.id.tietContactAddress2);
        tietCity = contactInfoView.findViewById(R.id.tietContactCity);
        tietPostalCode = contactInfoView.findViewById(R.id.tietContactPostal);

        RoomType roomType = RoomDB.getInstance(getContext()).roomTypeDao().getRoomTypeById(roomTypeID);
        RoomTypeCash roomTypeCash = RoomDB.getInstance(getContext()).roomTypeCashDao().getRoomTypeCash(roomTypeID, adults, children);

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

        phone = customer.getPhone();
        address1 = customer.getAddress1();
        address2 = customer.getAddress2();
        city = customer.getCity();
        postalCode = customer.getPostalCode();

        tietPhone.setText(phone);
        tietAddress1.setText(address1);
        tietAddress2.setText(address2);
        tietCity.setText(city);
        tietPostalCode.setText(postalCode);

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

                boolean newValue = false;
                if(!customer.getPhone().equals(phone)){
                    customer.setPhone(phone);
                    newValue = true;
                }
                if(!customer.getAddress1().equals(address1)){
                    customer.setAddress1(address1);
                    newValue = true;
                }
                if(!customer.getAddress2().equals(address2)){
                    customer.setAddress2(address2);
                    newValue = true;
                }
                if(!customer.getCity().equals(city)){
                    customer.setCity(city);
                    newValue = true;
                }
                if(!customer.getPostalCode().equals(postalCode)){
                    customer.setPostalCode(postalCode);
                    newValue = true;
                }
                if(newValue){
                    RoomDB.getInstance(getContext()).customerDao().insert(customer);
                }

                ScheduleNotifications.checkInNotification(getContext(), resID);
                ScheduleNotifications.checkoutNotification(getContext(), departure);

                listener.showBooked(resID);
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Snackbar.make(tvPoints, error, Toast.LENGTH_SHORT).show();

    }

    public void onLoyaltyPicked(int freeNights, int cashNights, int totalPoints) {
        this.freeNights = freeNights;
        this.cashNights = cashNights;
        int price = roomPrice * days - roomPrice * freeNights + cashNights * (cashPrice - roomPrice);
        tvTotalPrice.setText(String.valueOf(price));
        tvPoints.setText(String.valueOf(totalPoints));

        if(price==0 && totalPoints>0){
            groupCash.setVisibility(View.GONE);
            groupPoints.setVisibility(View.VISIBLE);
            tvPlus.setVisibility(View.GONE);
        }
        else if(price>0 && totalPoints==0){
            groupCash.setVisibility(View.VISIBLE);
            groupPoints.setVisibility(View.GONE);
            tvPlus.setVisibility(View.GONE);
        }
        else{
            tvPlus.setVisibility(View.VISIBLE);
            groupCash.setVisibility(View.VISIBLE);
            groupPoints.setVisibility(View.VISIBLE);
        }
        /*
        tvPoints.setText(String.valueOf(totalPoints));
        if (totalPoints > 0) {
            groupPoints.setVisibility(View.VISIBLE);
        } else {
            groupPoints.setVisibility(View.GONE);
        }
        */
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (creditCardView == null || contactInfoView == null) {
                throw new RuntimeException("One of the fragments is not found");
            }

            EditText etCreditCard = creditCardView.findViewById(R.id.etCreditCardCard);
            EditText etHoldersName = creditCardView.findViewById(R.id.etCreditCardHoldersName);
            EditText etCVV = creditCardView.findViewById(R.id.etCreditCardCVV);
            Spinner spMonth = creditCardView.findViewById(R.id.spCreditCardExpMonth);
            Spinner spYear = creditCardView.findViewById(R.id.spCreditCardExpYear);

            etCreditCard.setError(null);
            etHoldersName.setError(null);
            etCVV.setError(null);
            tietPhone.setError(null);
            tietAddress1.setError(null);
            tietCity.setError(null);
            tietPostalCode.setError(null);
            boolean flag = false;

            String creditCard = etCreditCard.getText().toString().replaceAll("\\s", "");
            String holdersName = etHoldersName.getText().toString();
            String month = String.valueOf(spMonth.getSelectedItem());
            String year = String.valueOf(spYear.getSelectedItem());
            String cvv = etCVV.getText().toString();

            phone = tietPhone.getText().toString();
            address1 = tietAddress1.getText().toString();
            address2 = tietAddress2.getText().toString();
            city = tietCity.getText().toString();
            postalCode = tietPostalCode.getText().toString();

            if (!Validation.checkCreditCard(creditCard)) {
                etCreditCard.setError("Please enter a valid card");
                flag = true;
            }
            if (!Validation.checkLength(holdersName, 4, null)) {
                etHoldersName.setError("Please enter a valid name");
                flag = true;
            }
            if (!Validation.checkLength(cvv, 3, null)) {
                etCVV.setError("Please enter a CVV");
                flag = true;
            }
            if (!Validation.checkLength(phone, 5, null)) {
                tietPhone.setError("Please enter a valid phone");
                flag = true;
            }
            if (!Validation.checkLength(address1, 3, null)) {
                tietAddress1.setError("Please enter a valid address");
                flag = true;
            }
            if (!Validation.checkLength(city, 2, null)) {
                tietCity.setError("Please enter a valid city");
                flag = true;
            }
            if (!Validation.checkLength(postalCode, 3, null)) {
                tietPostalCode.setError("Please enter a valid Postal Code");
                flag = true;
            }
            if (flag) return;


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

            values.put(POST.bookRoomPhone, phone);
            values.put(POST.bookRoomAddress1, address1);
            values.put(POST.bookRoomAddress2, address2);
            values.put(POST.bookRoomCity, city);
            values.put(POST.bookRoomPostalCode, postalCode);

            int customerID = customer.getCustomerId();

            values.put(POST.bookRoomCustomerID, String.valueOf(customerID));

            VolleyQueue.getInstance(getContext()).jsonRequest(BookFragment.this, URL.bookUrl, values);
        }
    };
}