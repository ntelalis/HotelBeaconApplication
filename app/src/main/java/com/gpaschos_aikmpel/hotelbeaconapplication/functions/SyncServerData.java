package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.content.Context;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Currency;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeFreeNightsPoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePointsAndCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Title;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncServerData implements JsonListener {

    private static final String TAG = SyncServerData.class.getSimpleName();

    private static SyncServerData instance;
    private final Context context;
    private SyncCallbacks syncCallbacks;

    private RoomDB roomDB;
    private VolleyQueue volleyQueue;

    private SyncServerData(Context context) {
        if(context instanceof SyncCallbacks) {
            syncCallbacks = (SyncCallbacks) context;
        }
        else{
            throw new ClassCastException("interface SyncCallbacks must be implemented");
        }
        this.context = context.getApplicationContext();
        this.roomDB = RoomDB.getInstance(context);
        this.volleyQueue = VolleyQueue.getInstance(context);
    }

    public static SyncServerData getInstance(Context context) {
        if (instance == null) {
            instance = new SyncServerData(context);

        }
        return instance;
    }

    public void getDataFromServer() {
        Log.d(TAG,"GetDataFromServer");
        getTitles();
        getCountries();
        getCurrencies();
        getRoomTypes();
    }

    private void getTitles() {
        Log.i(TAG, "Check Titles");
        if (roomDB.titleDao().getTitles().isEmpty()) {
            Log.i(TAG, "Get Titles");
            volleyQueue.jsonRequest(this, URL.titlesUrl, null);
        }

    }

    private void getCountries() {
        Log.i(TAG, "Check countries");
        if (roomDB.countryDao().getCountries().isEmpty()) {
            Log.i(TAG, "Get Countries");
            volleyQueue.jsonRequest(this, URL.countriesUrl, null);
        }
    }

    private void getCurrencies() {
        Log.i(TAG, "Check currencies");
        if (roomDB.currencyDao().getCurrencies().isEmpty()) {
            Log.i(TAG, "Get currencies");
            volleyQueue.jsonRequest(this, URL.currenciesUrl, null);
        }
    }

    private void getRoomTypes() {
        Log.i(TAG, "Check if RoomTypes have Changed");
        Map<String, String> params = new HashMap<>();
        List<RoomType> roomTypeList = roomDB.roomTypeDao().getRoomTypes();
        JSONArray jsonArray = new JSONArray();
        for (RoomType roomType : roomTypeList) {
            int id = roomType.getId();
            String modified = roomType.getModified();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(POST.roomTypeID, id);
                jsonObject.put(POST.roomTypeModified, modified);
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.d(TAG, jsonArray.toString());
        params.put(POST.roomTypeCheck, jsonArray.toString());
        volleyQueue.jsonRequest(this, URL.roomTypesUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {

        //TODO Overhaul ALL Syncing

        switch (url) {
            case URL.titlesUrl:
                List<Title> titleList = new ArrayList<>();
                JSONArray jsonArrayTitle = json.getJSONArray(POST.titlesTitleList);
                for (int i = 0; i < jsonArrayTitle.length(); i++) {
                    int id = jsonArrayTitle.getJSONObject(i).getInt(POST.titlesID);
                    String title = jsonArrayTitle.getJSONObject(i).getString(POST.titlesTitle);
                    titleList.add(new Title(id,title));
                }
                roomDB.titleDao().insertAll(titleList);
                Log.i(TAG, "Title results OK!");
            case URL.countriesUrl:

                JSONArray jsonArrayCountries = json.getJSONArray(POST.countryArray);
                List<Country> countryList = new ArrayList<>();
                for (int i = 0; i < jsonArrayCountries.length(); i++) {
                    int id = jsonArrayCountries.getJSONObject(i).getInt(POST.countryID);
                    String name = jsonArrayCountries.getJSONObject(i).getString(POST.countryName);
                    countryList.add(new Country(id,name));
                }

                roomDB.countryDao().insertAll(countryList);
                Log.i(TAG, "Country results OK!");

                break;
            case URL.currenciesUrl:

                JSONArray jsonArrayCurrency = json.getJSONArray(POST.currencyArray);
                List<Currency> currencyList = new ArrayList<>();
                for (int i = 0; i < jsonArrayCurrency.length(); i++) {
                    int id = jsonArrayCurrency.getJSONObject(i).getInt(POST.currencyID);
                    String name = jsonArrayCurrency.getJSONObject(i).getString(POST.currencyName);
                    String code = jsonArrayCurrency.getJSONObject(i).getString(POST.currencyCode);
                    String symbol = jsonArrayCurrency.getJSONObject(i).getString(POST.currencySymbol);
                    currencyList.add(new Currency(id, name, code, symbol));
                }
                roomDB.currencyDao().insertAll(currencyList);
                Log.i(TAG,"Currencies results OK!");
                break;
            case URL.roomTypesUrl:

                JSONArray jsonArrayRoomTypes = json.getJSONArray(POST.roomTypeArray);
                List<RoomType> roomTypeList = new ArrayList<>();
                for (int i = 0; i < jsonArrayRoomTypes.length(); i++) {
                    int id = jsonArrayRoomTypes.getJSONObject(i).getInt(POST.roomTypeID);
                    String name = jsonArrayRoomTypes.getJSONObject(i).getString(POST.roomTypeName);
                    int capacity = jsonArrayRoomTypes.getJSONObject(i).getInt(POST.roomTypeCapacity);
                    int price = jsonArrayRoomTypes.getJSONObject(i).getInt(POST.roomTypePrice);
                    String img = jsonArrayRoomTypes.getJSONObject(i).getString(POST.roomTypeImage);
                    String description = jsonArrayRoomTypes.getJSONObject(i).getString(POST.roomTypeDescription);
                    String modified = jsonArrayRoomTypes.getJSONObject(i).getString(POST.roomTypeModified);
                    LocalVariables.storeImageFromBase64(context, name, img);
                    roomTypeList.add(new RoomType(id, name, capacity, price, name, description, modified));
                }
                roomDB.roomTypeDao().insertAll(roomTypeList);
                Log.i(TAG, "RoomTypes results OK!");

                JSONArray jsonArrayRoomTypeCash = json.getJSONArray(POST.roomTypeCashArray);
                List<RoomTypeCash> roomTypeCashList = new ArrayList<>();
                for (int i = 0; i < jsonArrayRoomTypeCash.length(); i++) {
                    int roomTypeID = jsonArrayRoomTypeCash.getJSONObject(i).getInt(POST.roomTypeCashID);
                    int persons = jsonArrayRoomTypeCash.getJSONObject(i).getInt(POST.roomTypeCashPersons);
                    int currencyID = jsonArrayRoomTypeCash.getJSONObject(i).getInt(POST.roomTypeCashCurrencyID);
                    int price = jsonArrayRoomTypeCash.getJSONObject(i).getInt(POST.roomTypeCashPrice);
                    roomTypeCashList.add(new RoomTypeCash(roomTypeID, persons, currencyID, price));
                }
                roomDB.roomTypeCashDao().insertAll(roomTypeCashList);
                Log.i(TAG, "RoomTypeCash results OK!");

                JSONArray jsonArrayRoomTypeFreeNightsPoints = json.getJSONArray(POST.roomTypeFreeNightsPointsArray);
                List<RoomTypeFreeNightsPoints> roomTypeFreeNightsPointsList = new ArrayList<>();
                for (int i = 0; i < jsonArrayRoomTypeFreeNightsPoints.length(); i++) {
                    int roomTypeID = jsonArrayRoomTypeFreeNightsPoints.getJSONObject(i).getInt(POST.roomTypeFreeNightsPointsRoomTypeID);
                    int persons = jsonArrayRoomTypeFreeNightsPoints.getJSONObject(i).getInt(POST.roomTypeFreeNightsPointsPersons);
                    int points = jsonArrayRoomTypeFreeNightsPoints.getJSONObject(i).getInt(POST.roomTypeFreeNightsPointsPoints);
                    roomTypeFreeNightsPointsList.add(new RoomTypeFreeNightsPoints(roomTypeID, persons, points));
                }
                roomDB.roomTypeFreeNightsPointsDao().insertAll(roomTypeFreeNightsPointsList);
                Log.i(TAG, "RoomTypeFreeNightsPoints results OK!");

                JSONArray jsonArrayRoomTypePointsAndCash = json.getJSONArray(POST.roomTypePointsAndCashArray);
                List<RoomTypePointsAndCash> roomTypePointsAndCashList = new ArrayList<>();
                for (int i = 0; i < jsonArrayRoomTypePointsAndCash.length(); i++) {
                    int roomTypeID = jsonArrayRoomTypePointsAndCash.getJSONObject(i).getInt(POST.roomTypePointsAndCashRoomTypeID);
                    int persons = jsonArrayRoomTypePointsAndCash.getJSONObject(i).getInt(POST.roomTypePointsAndCashPersons);
                    int currencyID = jsonArrayRoomTypePointsAndCash.getJSONObject(i).getInt(POST.roomTypePointsAndCashCurrencyID);
                    int cash = jsonArrayRoomTypePointsAndCash.getJSONObject(i).getInt(POST.roomTypePointsAndCashCash);
                    int points = jsonArrayRoomTypePointsAndCash.getJSONObject(i).getInt(POST.roomTypePointsAndCashPoints);
                    roomTypePointsAndCashList.add(new RoomTypePointsAndCash(roomTypeID, persons, currencyID, cash, points));
                }
                roomDB.roomTypePointsAndCashDao().insertAll(roomTypePointsAndCashList);

                Log.i(TAG, "RoomTypePointsAndCash results OK!");

                syncCallbacks.synced();
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Log.e(TAG, url+": "+error);
    }

    public interface SyncCallbacks{
        void synced();
    }



}
