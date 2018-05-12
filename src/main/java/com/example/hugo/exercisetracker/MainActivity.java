package com.example.hugo.exercisetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExerciseAdapter.ExerciseAdapterOnClickHandler {


    private RecyclerView mRecyclerView;
    private ExerciseAdapter mAdapter;


    private ArrayList<Exercise> exData;
    public static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        SharedPreferences.Editor editor  = sharedPreferences.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exData =  loadData();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_exerciseList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ExerciseAdapter(exData, this);

        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.fab);


        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent myIntent = new Intent(getApplicationContext(), AddExercise.class);
                startActivity(myIntent);

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case (R.id.weight_record):
                System.out.println("go to weight page");
                Intent myIntent = new Intent(getApplicationContext(), RecordScreen.class);
                startActivity(myIntent);

                break;

            case(R.id.weight_input):
                System.out.println("go to weight input");
                Intent myIntent2 = new Intent(getApplicationContext(), AddWeight.class);
                startActivity(myIntent2);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    //doesn't work yet
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                delete(viewHolder.getAdapterPosition());
            }




        };
        return simpleCallback;
    }




    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //doesn't work yet
    void moveItem(int from, int to){
        Exercise temp = exData.get(from);
        exData.remove(from);
        exData.add(to, temp);

        mAdapter.notifyItemMoved(from, to);

    }

    void delete(int position){

        exData.remove(position);
        MainActivity.saveData(exData);
        mAdapter.notifyItemRemoved(position);
    }




    @Override
    public void onClick(View view, int position) {
        ToggleButton tb = (ToggleButton) view.findViewById(R.id.add_stay);

        if (view.getId() == tb.getId()){
            System.out.println("clicked button");
            saveToggleStatus(tb, position);

        }

        else {
            Intent myIntent = new Intent(getApplicationContext(), AddInput.class);
            myIntent.putExtra("exList", exData);
            myIntent.putExtra("position", position);
            startActivity(myIntent);
        }

    }

    void saveToggleStatus(ToggleButton tb, int position){
        if (tb.isChecked()){
            exData.get(position).setStatus(true);
        }
        else{
            exData.get(position).setStatus(false);
        }
        saveData(exData);
    }


    @Override
    public void onLongClick(View view, int position) {
        Intent myIntent = new Intent(getApplicationContext(), RecordScreen.class);
        myIntent.putExtra("position", position);
        startActivity(myIntent);
    }

    //TODO saveData, loadData, getWeight, saveWeight all too similar, merge together

    public static void saveData(ArrayList<Exercise> exList){
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exList);
        editor.putString("savedList", json);

        System.out.println(json);
        editor.commit();

    }

    public static ArrayList<Exercise> loadData(){
        ArrayList<Exercise> exData;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("savedList", "");

        if (json.isEmpty() || json == null){
            exData = new ArrayList<>();


        }
        else{
            Type type = new TypeToken<List<Exercise>>(){
            }.getType();

            exData = gson.fromJson(json, type);
        }

        return exData;

    }

    public static Weight loadWeight(){
        Gson gson = new Gson();
        Weight record;
        String json = sharedPreferences.getString("weightRecord", "");
        if (json.isEmpty() || json == null){
            record = new Weight();
        }
        else{
            Type type = new TypeToken<Weight>(){
            }.getType();

            record = gson.fromJson(json, type);
        }
        return record;
    }

    public static void saveWeight(Weight weight){

        SharedPreferences.Editor editor  = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(weight);
        editor.putString("weightRecord", json);

        editor.commit();

    }

}
