package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueVolley {

    private static RequestQueue requestQueue;

    private RequestQueueVolley(){

    }

    public static RequestQueue getInstance(Context context){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}
