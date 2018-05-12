package com.example.hugo.exercisetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

/**
 * Created by hugo on 01/05/17.
 */

public class AddInput extends AppCompatActivity {

    ArrayList<Exercise> exList;
    TextView exName;
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_input);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        exList = MainActivity.loadData();

        exName = (TextView) findViewById(R.id.exName);
        exName.setText(exList.get(position).getExerciseName());
    }

    public void backToMainScreen(View target){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        MainActivity.saveData(exList);
        startActivity(myIntent);
        finish();
    }

     public void addInput(View target){

        EditText etNewEntry = (EditText) findViewById(R.id.num_newEntry);
        String entry = etNewEntry.getText().toString();
        Double numInput;
        try {
            numInput = Double.parseDouble(entry);
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            Exercise selectedExercise = exList.get(position);
            selectedExercise.setRecord(date, numInput);
        }catch (Exception e ){
            e.printStackTrace();
        }



        backToMainScreen(target);

    }

}
