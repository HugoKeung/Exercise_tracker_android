package com.example.hugo.exercisetracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by hugo on 01/05/17.
 */

public class Exercise implements Serializable{

    private String exerciseName;
    private LinkedHashMap<String, Double> record;
    private boolean status;


    public Exercise(String name){
        this.exerciseName = name;
        record = new LinkedHashMap<>();

    }


    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setRecord(String  date, double value) {

        record.put(date, value);
    }

    public String getExerciseName() {

        return exerciseName;
    }

    public HashMap<String, Double> getRecord() {
        return record;
    }

    void setStatus(boolean status){
        this.status = status;
    }

    boolean getStatus(){
        return status;
    }
}
