package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.content.Context;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegionFeature;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Currency;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.ExclusiveOffer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.GeneralOffer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCashPoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Title;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;

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
        if (context instanceof SyncCallbacks) {
            syncCallbacks = (SyncCallbacks) context;
        } else {
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
        return new SyncServerData(context);
    }

    public void getDataFromServer() {
        Log.d(TAG, "GetDataFromServer");
        getBeaconRegion();
        getTitles();
        getCountries();
        getCurrencies();
        getGeneralOffers();
        getRoomTypes();
    }

    public void getCustomerDataFromServer(Customer customer) {
        Log.d(TAG, "GetCustomerDataFromServer");
        getReservations(customer);
        //getRoomBeaconRegion();
    }


    private void getBeaconRegion() {
        Log.i(TAG, "Check BeaconRegions");
        List<BeaconRegion> regionList = roomDB.beaconRegionDao().getRegions();
        Map<String, String> params = null;
        if (!regionList.isEmpty()) {
            params = new HashMap<>();
            String beaconRegionCheckJSON = sync(regionList);
            params.put(POST.beaconRegionCheck, beaconRegionCheckJSON);
        }
        volleyQueue.jsonRequest(this, URL.beaconRegionsUrl, params);
    }


    private void getRoomBeaconRegion() {
        Log.i(TAG, "Check RoomBeaconRegions");
        Reservation current = roomDB.reservationDao().getCurrentReservation();
        //todo: take into consideration the remaining room regions from previous stays + fix it
        if (current != null && current.isCheckedInNotCheckedOut()) {

            //FIXME
            List<BeaconRegion> roomRegionList = roomDB.beaconRegionDao().getRegionsByType(Params.roomRegionType);
            Map<String, String> params = new HashMap<>();
            params.put(POST.roomBeaconRegionReservationID, String.valueOf(current.getId()));
            if (!roomRegionList.isEmpty()) {
                String beaconRegionCheckJSON = sync(roomRegionList);
                params.put(POST.beaconRegionCheck, beaconRegionCheckJSON);
            }
            volleyQueue.jsonRequest(this, URL.roomBeaconRegionsUrl, params);
        }
        getBeaconRegionFeature();
    }

    private void getBeaconRegionFeature() {
        Log.i(TAG, "Check BeaconRegionFeature");
        Map<String, String> params = new HashMap<>();

        List<BeaconRegion> regionList = roomDB.beaconRegionDao().getRegions();
        JSONArray jsonArray = new JSONArray();
        for (BeaconRegion beaconRegion : regionList) {
            jsonArray.put(beaconRegion.getId());
        }
        Log.d(TAG, jsonArray.toString());
        params.put(POST.regionIDForFeaturesArray, jsonArray.toString());

        List<BeaconRegionFeature> regionFeatureList = roomDB.beaconRegionFeatureDao().getRegionFeatureList();
        if (!regionFeatureList.isEmpty()) {
            String beaconRegionFeatureCheckJSON = sync(regionFeatureList);
            params.put(POST.beaconRegionFeatureCheck, beaconRegionFeatureCheckJSON);
        }
        volleyQueue.jsonRequest(this, URL.beaconRegionFeatureUrl, params);
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

    private void getReservations(Customer customer) {
        Log.i(TAG, "Get reservations");
        if (customer != null) {
            int customerID = customer.getCustomerId();
            Map<String, String> params = new HashMap<>();
            params.put(POST.reservationCustomerID, String.valueOf(customerID));
            List<Reservation> reservationList = roomDB.reservationDao().getReservations();
            String reservationCheckJSON = sync(reservationList);
            params.put(POST.reservationCheck, reservationCheckJSON);
            volleyQueue.jsonRequest(this, URL.reservationsUrl, params);
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

    private void getGeneralOffers() {
        Log.i(TAG, "Check GeneralOffers");
        List<GeneralOffer> generalOfferList = roomDB.generalOfferDao().getGeneralOffers();
        Map<String, String> params = null;
        if (!generalOfferList.isEmpty()) {
            params = new HashMap<>();
            String generalOfferCheckJSON = sync(generalOfferList);
            params.put(POST.generalOfferCheck, generalOfferCheckJSON);
        }
        volleyQueue.jsonRequest(this, URL.generalOffersUrl, params);
    }

    private void getExclusiveOffers() {
        Log.i(TAG, "Check ExclusiveOffers");
        List<ExclusiveOffer> exclusiveOfferList = roomDB.exclusiveOfferDao().getExclusiveOffers();
        Map<String, String> params = new HashMap<>();
        int customerID = roomDB.customerDao().getCustomer().getCustomerId();
        params.put(POST.exclusiveOfferCustomerID, String.valueOf(customerID));
        if (!exclusiveOfferList.isEmpty()) {
            String exclusiveOfferCheckJSON = sync(exclusiveOfferList);
            params.put(POST.exclusiveOfferCheck, exclusiveOfferCheckJSON);
        }
        volleyQueue.jsonRequest(this, URL.exclusiveOffersUrl, params);
    }

    private void resolveBeaconRegionReply(JSONObject json, String url) {
        try {
            JSONArray jsonArrayBeaconRegion = json.getJSONArray(POST.beaconRegionArray);
            List<BeaconRegion> beaconRegionList = new ArrayList<>();
            for (int i = 0; i < jsonArrayBeaconRegion.length(); i++) {
                JSONObject jsonObject = jsonArrayBeaconRegion.getJSONObject(i);
                int id = jsonObject.getInt(POST.beaconRegionID);
                String modified = JSONHelper.getString(jsonObject, POST.beaconRegionModified);
                if (modified == null) {
                    roomDB.beaconRegionDao().deleteRegionByID(id);
                    Log.i(TAG, "BeaconRegion: " + id + " deleted");
                    continue;
                }
                String uniqueID = JSONHelper.getString(jsonObject, POST.beaconRegionUniqueID);
                String uuid = JSONHelper.getString(jsonObject, POST.beaconRegionUUID);
                String major = JSONHelper.getString(jsonObject, POST.beaconRegionMajor);
                String minor = JSONHelper.getString(jsonObject, POST.beaconRegionMinor);
                boolean exclusive = jsonObject.getBoolean(POST.beaconRegionExclusive);
                boolean background = jsonObject.getBoolean(POST.beaconRegionBackground);
                String type = null;
                if (url.equalsIgnoreCase(URL.roomBeaconRegionsUrl)) {
                    type = "room";
                }
                beaconRegionList.add(new BeaconRegion(id, uniqueID, uuid, major, minor, exclusive, background, type, modified));
            }
            roomDB.beaconRegionDao().insertAll(beaconRegionList);

        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
    }


    @Override
    public void getSuccessResult(String url, JSONObject json) {

        //TODO Overhaul ALL Syncing

        switch (url) {
            case URL.titlesUrl:
                List<Title> titleList = new ArrayList<>();
                try {
                    JSONArray jsonArrayTitle = json.getJSONArray(POST.titlesTitleList);
                    for (int i = 0; i < jsonArrayTitle.length(); i++) {
                        JSONObject jsonObject = jsonArrayTitle.getJSONObject(i);
                        int id = jsonObject.getInt(POST.titlesID);
                        String title = JSONHelper.getString(jsonObject, POST.titlesTitle);
                        titleList.add(new Title(id, title));
                    }
                    roomDB.titleDao().insertAll(titleList);
                    Log.i(TAG, "Title results OK!");
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                break;
            case URL.countriesUrl:
                try {
                    JSONArray jsonArrayCountries = json.getJSONArray(POST.countryArray);
                    List<Country> countryList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayCountries.length(); i++) {
                        JSONObject jsonObject = jsonArrayCountries.getJSONObject(i);
                        int id = jsonObject.getInt(POST.countryID);
                        String name = JSONHelper.getString(jsonObject, POST.countryName);
                        countryList.add(new Country(id, name));
                    }

                    roomDB.countryDao().insertAll(countryList);
                    Log.i(TAG, "Country results OK!");
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                break;
            case URL.currenciesUrl:
                try {
                    JSONArray jsonArrayCurrency = json.getJSONArray(POST.currencyArray);
                    List<Currency> currencyList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayCurrency.length(); i++) {
                        JSONObject jsonObject = jsonArrayCurrency.getJSONObject(i);
                        int id = jsonObject.getInt(POST.currencyID);
                        String name = JSONHelper.getString(jsonObject, POST.currencyName);
                        String code = JSONHelper.getString(jsonObject, POST.currencyCode);
                        String symbol = JSONHelper.getString(jsonObject, POST.currencySymbol);
                        currencyList.add(new Currency(id, name, code, symbol));
                    }
                    roomDB.currencyDao().insertAll(currencyList);
                    Log.i(TAG, "Currencies results OK!");
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }

                break;

            case URL.beaconRegionsUrl:
                Log.i(TAG, "Check BeaconRegions");
                resolveBeaconRegionReply(json, URL.beaconRegionsUrl);
                Log.i(TAG, "BeaconRegions OK!");
                break;

            case URL.roomTypesUrl:
                try {

                    JSONArray jsonArrayRoomTypes = json.getJSONArray(POST.roomTypeArray);
                    List<RoomType> roomTypeList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayRoomTypes.length(); i++) {
                        JSONObject jsonObject = jsonArrayRoomTypes.getJSONObject(i);
                        int id = jsonObject.getInt(POST.roomTypeID);
                        String name = JSONHelper.getString(jsonObject, POST.roomTypeName);
                        int capacity = jsonObject.getInt(POST.roomTypeCapacity);
                        int adults = jsonObject.getInt(POST.roomTypeAdults);
                        boolean childrenSupported = jsonObject.getBoolean(POST.roomTypeChildrenSupported);
                        String img = JSONHelper.getString(jsonObject, POST.roomTypeImage);
                        String description = JSONHelper.getString(jsonObject, POST.roomTypeDescription);
                        String modified = JSONHelper.getString(jsonObject, POST.roomTypeModified);
                        LocalVariables.storeImageFromBase64(context, name, img);
                        roomTypeList.add(new RoomType(id, name, capacity, adults, childrenSupported, name, description, modified));
                    }
                    roomDB.roomTypeDao().insertAll(roomTypeList);
                    Log.i(TAG, "RoomTypes results OK!");

                    JSONArray jsonArrayRoomTypeCash = json.getJSONArray(POST.roomTypeCashArray);
                    List<RoomTypeCash> roomTypeCashList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayRoomTypeCash.length(); i++) {
                        JSONObject jsonObject = jsonArrayRoomTypeCash.getJSONObject(i);
                        int roomTypeID = jsonObject.getInt(POST.roomTypeCashID);
                        int adults = jsonObject.getInt(POST.roomTypeCashAdults);
                        int children = jsonObject.getInt(POST.roomTypeCashChildren);
                        int currencyID = jsonObject.getInt(POST.roomTypeCashCurrencyID);
                        int price = jsonObject.getInt(POST.roomTypeCashCash);
                        roomTypeCashList.add(new RoomTypeCash(roomTypeID, adults, children, currencyID, price));
                    }
                    roomDB.roomTypeCashDao().insertAll(roomTypeCashList);
                    Log.i(TAG, "RoomTypeCash results OK!");

                    JSONArray jsonArrayRoomTypePoints = json.getJSONArray(POST.roomTypePointsArray);
                    List<RoomTypePoints> roomTypePointsList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayRoomTypePoints.length(); i++) {
                        JSONObject jsonObject = jsonArrayRoomTypePoints.getJSONObject(i);
                        int roomTypeID = jsonObject.getInt(POST.roomTypePointsRoomTypeID);
                        int adults = jsonObject.getInt(POST.roomTypePointsAdults);
                        int children = jsonObject.getInt(POST.roomTypePointsChildren);
                        int spendingPoints = jsonObject.getInt(POST.roomTypePointsSpendingPoints);
                        int gainingPoints = jsonObject.getInt(POST.roomTypePointsGainingPoints);
                        roomTypePointsList.add(new RoomTypePoints(roomTypeID, adults, children, spendingPoints, gainingPoints));
                    }
                    roomDB.roomTypePointsDao().insertAll(roomTypePointsList);
                    Log.i(TAG, "RoomTypePoints results OK!");

                    JSONArray jsonArrayRoomTypeCashPoints = json.getJSONArray(POST.roomTypeCashPointsArray);
                    List<RoomTypeCashPoints> roomTypeCashPointsList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayRoomTypeCashPoints.length(); i++) {
                        JSONObject jsonObject = jsonArrayRoomTypeCashPoints.getJSONObject(i);
                        int roomTypeID = jsonObject.getInt(POST.roomTypeCashPointsRoomTypeID);
                        int adults = jsonObject.getInt(POST.roomTypeCashPointsAdults);
                        int children = jsonObject.getInt(POST.roomTypeCashPointsChildren);
                        int currencyID = jsonObject.getInt(POST.roomTypeCashPointsCurrencyID);
                        int cash = jsonObject.getInt(POST.roomTypeCashPointsCash);
                        int points = jsonObject.getInt(POST.roomTypeCashPointsPoints);
                        roomTypeCashPointsList.add(new RoomTypeCashPoints(roomTypeID, adults, children, currencyID, cash, points));
                    }
                    roomDB.roomTypeCashPointsDao().insertAll(roomTypeCashPointsList);

                    Log.i(TAG, "RoomTypeCashPoints results OK!");
                    //todo:manage the case where roomTypes() is finished before getCountries() etc.
                    syncCallbacks.dataSynced();

                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }

                break;

            case URL.reservationsUrl:
                //todo: syncing beaconRegions for the room
                try {
                    JSONArray jsonArrayReservations = json.getJSONArray(POST.reservationArray);
                    List<Reservation> reservationList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayReservations.length(); i++) {
                        JSONObject jsonObject = jsonArrayReservations.getJSONObject(i);
                        int id = jsonObject.getInt(POST.reservationReservationID);
                        String modified = JSONHelper.getString(jsonObject, POST.reservationModified);

                        if (modified == null) {
                            roomDB.reservationDao().delete(id);
                            Log.i(TAG, "Reservation: " + id + " deleted");
                            continue;
                        }

                        int roomTypeID = jsonObject.getInt(POST.reservationRoomTypeID);
                        int adults = jsonObject.getInt(POST.reservationAdults);
                        int children = jsonObject.getInt(POST.reservationChildren);
                        String bookedDate = JSONHelper.getString(jsonObject, POST.reservationBookedDate);
                        String arrivalDate = JSONHelper.getString(jsonObject, POST.reservationArrival);
                        String departureDate = JSONHelper.getString(jsonObject, POST.reservationDeparture);
                        String checkIn = JSONHelper.getString(jsonObject, POST.reservationCheckIn);
                        String checkOut = JSONHelper.getString(jsonObject, POST.reservationCheckOut);
                        //int roomBeaconID = -1;
                        int roomNumber = -1;
                        int floor = -1;
                        try {
                            //roomBeaconID = jsonObject.getInt(POST.reservationRoomBeaconID);
                            roomNumber = jsonObject.getInt(POST.reservationRoomNumber);
                            floor = jsonObject.getInt(POST.reservationRoomFloor);
                        } catch (JSONException e) {
                            Log.d(TAG, "Not checked IN");
                        }

                        Reservation r = roomDB.reservationDao().getReservationByID(id);
                        //todo: take password issue into consideration
                        if (r != null) {
                            r.update(id, roomTypeID, adults, children, bookedDate, arrivalDate, departureDate, checkIn, checkOut, roomNumber, floor, modified);
                        } else {
                            r = new Reservation(id, roomTypeID, adults, children, bookedDate, arrivalDate, departureDate, checkIn, checkOut, roomNumber, floor, modified);
                        }
                        reservationList.add(r);
                    }
                    roomDB.reservationDao().insertAll(reservationList);
                    Log.i(TAG, "Reservations OK!");

                    //sync the roombeaconRegions since the reservation syncing is finished
                    getRoomBeaconRegion();
                    //syncCallbacks.customerDataSynced();
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                break;

            case URL.roomBeaconRegionsUrl:
                resolveBeaconRegionReply(json, URL.roomBeaconRegionsUrl);
                Log.i(TAG, "RoomBeaconRegions OK!");
                ((BeaconApplication) context.getApplicationContext()).registerBeaconRegion();
                syncCallbacks.customerDataSynced();
                break;
            case URL.generalOffersUrl:
                try {
                    JSONArray generalOfferJsonArray = json.getJSONArray(POST.generalOfferArray);
                    List<GeneralOffer> generalOfferList = new ArrayList<>();
                    for (int i = 0; i < generalOfferJsonArray.length(); i++) {
                        JSONObject jsonObject = generalOfferJsonArray.getJSONObject(i);
                        int id = jsonObject.getInt(POST.generalOfferID);
                        String modified = JSONHelper.getString(jsonObject, POST.generalOfferModified);
                        if (modified == null) {
                            roomDB.generalOfferDao().deleteGeneralOfferByID(id);
                            Log.i(TAG, "GeneralOffer: " + id + " deleted");
                            continue;
                        }
                        String price = JSONHelper.getString(jsonObject, POST.generalOfferPrice);
                        String discount = JSONHelper.getString(jsonObject, POST.generalOfferDiscount);
                        String description = JSONHelper.getString(jsonObject, POST.generalOfferDescription);
                        String startDate = JSONHelper.getString(jsonObject, POST.generalOfferStartDate);
                        String endDate = JSONHelper.getString(jsonObject, POST.generalOfferEndDate);
                        generalOfferList.add(new GeneralOffer(id, price, discount, description, startDate, endDate, modified));
                    }
                    roomDB.generalOfferDao().insertAll(generalOfferList);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                break;
            case URL.exclusiveOffersUrl:
                try {
                    JSONArray exclusiveOfferJsonArray = json.getJSONArray(POST.exclusiveOfferArray);
                    List<ExclusiveOffer> exclusiveOfferList = new ArrayList<>();
                    for (int i = 0; i < exclusiveOfferJsonArray.length(); i++) {
                        JSONObject jsonObject = exclusiveOfferJsonArray.getJSONObject(i);
                        int id = jsonObject.getInt(POST.exclusiveOfferID);
                        String modified = JSONHelper.getString(jsonObject, POST.exclusiveOfferModified);
                        if (modified == null) {
                            roomDB.exclusiveOfferDao().deleteExclusiveOfferByID(id);
                            Log.i(TAG, "ExclusiveOffer: " + id + " deleted");
                            continue;
                        }
                        int serviceID = jsonObject.getInt(POST.exclusiveOfferServiceID);
                        String price = JSONHelper.getString(jsonObject, POST.exclusiveOfferPrice);
                        String discount = JSONHelper.getString(jsonObject, POST.exclusiveOfferDiscount);
                        String description = JSONHelper.getString(jsonObject, POST.exclusiveOfferDescription);
                        boolean special = jsonObject.getBoolean(POST.exclusiveOfferSpecial);
                        String startDate = JSONHelper.getString(jsonObject, POST.exclusiveOfferStartDate);
                        String endDate = JSONHelper.getString(jsonObject, POST.exclusiveOfferEndDate);
                        String code = JSONHelper.getString(jsonObject, POST.exclusiveOfferCode);
                        boolean codeClaimed = jsonObject.getBoolean(POST.exclusiveOfferCodeClaimed);
                        String codeCreated = JSONHelper.getString(jsonObject, POST.exclusiveOfferCodeCreated);
                        exclusiveOfferList.add(new ExclusiveOffer(id, serviceID, price, discount, description, special, startDate, endDate, code, codeClaimed, codeCreated, modified));
                    }
                    roomDB.exclusiveOfferDao().insertAll(exclusiveOfferList);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                break;
            case URL.beaconRegionFeatureUrl:
                try {
                    JSONArray regionFeatureJsonArray = json.getJSONArray(POST.beaconRegionFeatureArray);
                    List<BeaconRegionFeature> beaconRegionFeatureList = new ArrayList<>();
                    for (int i = 0; i < regionFeatureJsonArray.length(); i++) {
                        JSONObject jsonObject = regionFeatureJsonArray.getJSONObject(i);
                        int id = jsonObject.getInt(POST.beaconRegionFeatureID);
                        String modified = JSONHelper.getString(jsonObject, POST.beaconRegionFeatureModified);
                        if (modified == null) {
                            roomDB.beaconRegionFeatureDao().deleteRegionFeatureByID(id);
                            Log.i(TAG, "BeaconRegionFeature: " + id + " deleted");
                            continue;
                        }
                        int regionID = jsonObject.getInt(POST.beaconRegionFeatureRegionID);
                        String regionType = JSONHelper.getString(jsonObject, POST.beaconRegionFeatureFeature);

                        beaconRegionFeatureList.add(new BeaconRegionFeature(id, regionID, regionType, modified));
                    }
                    roomDB.beaconRegionFeatureDao().insertAll(beaconRegionFeatureList);

                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                Log.i(TAG, "BeaconRegionFeature OK!");

                ((BeaconApplication) context.getApplicationContext()).registerBeaconRegion();
                syncCallbacks.customerDataSynced();
                break;

        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Log.e(TAG, url + ": " + error);
    }

    public interface SyncCallbacks {
        void dataSynced();

        void customerDataSynced();

    }

    private static <T extends SyncModel> String sync(List<T> tList) {
        JSONArray jsonArray = new JSONArray();
        for (T t : tList) {
            int id = t.getId();
            String modified = t.getModified();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(POST.syncId, id);
                jsonObject.put(POST.syncModified, modified);
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                Log.e(TAG, e.toString());
                return "";
            }
        }
        Log.d(TAG, "JSON to server for syncing " + jsonArray.toString());
        return jsonArray.toString();
    }


}
