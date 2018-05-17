package com.gpaschos_aikmpel.hotelbeaconapplication.functions;

import android.content.Context;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetData implements JsonListener {

    private static final String TAG = GetData.class.getSimpleName();

    private static GetData instance;
    private final Context context;

    private GetData(Context context) {
        this.context = context.getApplicationContext();
    }

    public static GetData getInstance(Context context) {
        if (instance == null) {
            instance = new GetData(context);

        }
        return instance;
    }

    public void getDataFromServer() {
        getRoomTypes(context);
    }

    private void getRoomTypes(Context context) {
        Log.i(TAG,"Check if RoomTypes have Changed");
        Map<String, String> params = new HashMap<>();
        List<RoomType> roomTypeList = RoomDB.getInstance(context).roomTypeDao().check();
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
        VolleyQueue.getInstance(context).jsonRequest(this, URL.roomTypesUrl, params);
    }


    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.roomTypesUrl:
                Log.i(TAG,"RoomType results OK!");
                JSONArray jsonArray = json.getJSONArray(POST.roomTypeArray);
                List<RoomType> roomTypeList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    int id = jsonArray.getJSONObject(i).getInt(POST.roomTypeID);
                    String name = jsonArray.getJSONObject(i).getString(POST.roomTypeName);
                    int capacity = jsonArray.getJSONObject(i).getInt(POST.roomTypeCapacity);
                    int price = jsonArray.getJSONObject(i).getInt(POST.roomTypePrice);
                    String img = jsonArray.getJSONObject(i).getString(POST.roomTypeImage);
                    String description = jsonArray.getJSONObject(i).getString(POST.roomTypeDescription);
                    String modified = jsonArray.getJSONObject(i).getString(POST.roomTypeModified);
                    LocalVariables.storeImageFromBase64(context, name, img);
                    roomTypeList.add(new RoomType(id, name, capacity, price, name, description, modified));
                }

                RoomDB.getInstance(context).roomTypeDao().insertAll(roomTypeList);
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
    Log.e(TAG,error);
    }
}
