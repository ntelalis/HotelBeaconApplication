package com.gpaschos_aikmpel.hotelbeaconapplication.reworkRequest;

import android.content.Context;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Title;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;

import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public enum Request implements JsonListener {

    TITLES {
        @Override
        public void makeRequest(Context ctx) {
            if (RoomDB.getInstance(ctx).titleDao().getTitles().isEmpty()) {
                VolleyQueue.jsonRequest(ctx, Request.TITLES, URL.titlesUrl, null);
            }
        }

        @Override
        public boolean receiveRequest(Context ctx, JSONObject json) {
            List<Title> titleList = new ArrayList<>();
            try {
                JSONArray jsonArrayTitle = json.getJSONArray(POST.titlesTitleList);
                for (int i = 0; i < jsonArrayTitle.length(); i++) {
                    JSONObject jsonObject = jsonArrayTitle.getJSONObject(i);
                    int id = jsonObject.getInt(POST.titlesID);
                    String title = JSONHelper.getString(jsonObject, POST.titlesTitle);
                    titleList.add(new Title(id, title));
                }
                RoomDB.getInstance(ctx).titleDao().insertAll(titleList);
                Log.i(TAG, "Title results OK!");
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }

            return true;
        }

    };

    public void request(Context ctx, RequestCallback obj) {
        requestCallback = obj;
        makeRequest(ctx);


    }

    public void request(RequestCallback obj) {
        if (obj instanceof Context) {
            request((Context) obj, obj);
        } else {
            throw new ClassCastException(obj.getClass().getSimpleName() + " is not a Context");
        }
    }

    @Override
    public void getSuccessResult(Context context, JSONObject json) {
        if (receiveRequest(context, json))
            requestCallback.success();
        else
            requestCallback.fail();
    }

    @Override
    public void getErrorResult(Context context, String error) {
        requestCallback.fail();
    }

    public abstract void makeRequest(Context ctx);

    public abstract boolean receiveRequest(Context ctx, JSONObject json);

    String TAG = Request.class.getSimpleName();
    RequestCallback requestCallback;

}