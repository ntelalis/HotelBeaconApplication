package com.gpaschos_aikmpel.hotelbeaconapplication.utility;

import java.util.LinkedList;

public class LimitedUniqueQueue<E> extends LinkedList<E> {

    private int limit;

    public LimitedUniqueQueue(int limit){
        this.limit = limit;
    }

    @Override
    public boolean add(E e){
        for(int i = 0; i < super.size(); i++){
            E item = super.get(i);
            if(item.getClass() == e.getClass()){
                super.remove(i);
                break;
            }
        }
        boolean added = super.add(e);

        while (added && size() > limit){
            super.remove();
        }
        return added;
    }
}
