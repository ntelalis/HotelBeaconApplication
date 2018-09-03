package com.gpaschos_aikmpel.hotelbeaconapplication.reworkRequest;

import java.util.ArrayList;
import java.util.List;

public enum Tests1 {
    TITLES(Request.TITLES, Request.TITLES),
    TITTIES(Request.TITLES);
    private final List<Request> requestList;

    Tests1(Request... requestArgs) {
        requestList = new ArrayList<>();
        for (Request request : requestArgs) {
            requestList.add(request);
        }
    }

    public void request(RequestCallback rcb){
        for(Request request: requestList){
            request.request(rcb);
        }
    }
}
