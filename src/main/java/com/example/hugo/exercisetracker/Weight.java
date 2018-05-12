package com.example.hugo.exercisetracker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by hugo on 07/05/17.
 */



public class Weight implements Serializable {

    private LinkedHashMap<String, Double> record;

    public Weight(){
        record = new LinkedHashMap<>();
    }


    void addInput(String date, Double weight){
        record.put(date, weight);
    }

    LinkedHashMap<String, Double> getRecord(){
        return record;
    }
}
