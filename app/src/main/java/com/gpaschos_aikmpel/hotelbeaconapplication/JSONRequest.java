package com.gpaschos_aikmpel.hotelbeaconapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gpaschos on 04/03/18.
 */

public class JSONRequest extends StringRequest {

    private Param[] params;

    public JSONRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Param... params) {
        super(Request.Method.POST, url, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        Map<String,String> params = new HashMap<>();

        for(Param param: this.params){
            params.put(param.key,param.value);
        }
        return params;
    }

    public static class Param {

        private String key, value;

        public Param(String key,String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Listener implements Response.Listener<String> {

        private String successfulKey = "success";
        private boolean successful = false;
        private JSONObject jsonObject = null;

        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject =new JSONObject(response);
                if(jsonObject.getInt(successfulKey)==1){
                    successful = true;
                }
                jsonObject.remove(successfulKey);
                this.jsonObject = jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public JSONObject getJsonObject(){
            return jsonObject;
        }

        public boolean isSuccessful(){
            return successful;
        }
    }
}
