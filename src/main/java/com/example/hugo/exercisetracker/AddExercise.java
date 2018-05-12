package com.example.hugo.exercisetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hugo on 01/05/17.
 */

public class AddExercise extends AppCompatActivity {

    private ArrayList<Exercise> exList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);
        exList =  MainActivity.loadData();
    }

    public void backToMainScreen(View target){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        MainActivity.saveData(exList);

        startActivity(myIntent);
        finish();
    }

    public void addExercise(View target){

        EditText etNewEntry = (EditText) findViewById(R.id.newEntry);
        String newEntry = etNewEntry.getText().toString();
        if (!newEntry.isEmpty()) {
            Exercise newExercise = new Exercise(newEntry);
            exList.add(newExercise);
        }

        backToMainScreen(target);

    }


}
