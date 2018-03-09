package com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley;

import java.util.HashMap;
import java.util.Map;

public class JsonParams {
    private Map<String,String> params;

    public JsonParams(){
        params = new HashMap<>();
    }

    public void add(String key, String value){
        params.put(key,value);
    }

    public Map<String,String> getParams(){
        return params;
    }
}