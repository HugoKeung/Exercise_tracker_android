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

//TODO AddWeight.java & AddInput.java too similar, merge

public class AddWeight extends AppCompatActivity  {


    TextView exName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_input);
        TextView tv_message = (TextView) findViewById(R.id.input_message);
        tv_message.setText("Please enter your weight");

        exName = (TextView) findViewById(R.id.exName);
        exName.setText("Weight Input");
    }

    public void backToMainScreen(View target){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(myIntent);
        finish();
    }

    public void addInput(View target){

        Weight weight = MainActivity.loadWeight();
        EditText etNewEntry = (EditText) findViewById(R.id.num_newEntry);
        String entry = etNewEntry.getText().toString();
        Double numInput;
        try {
            numInput = Double.parseDouble(entry);
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            weight.addInput(date, numInput);

            MainActivity.saveWeight(weight);


        }catch (Exception e ){
            e.printStackTrace();
        }



        backToMainScreen(target);

    }



}
