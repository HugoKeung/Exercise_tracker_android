package com.example.hugo.exercisetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hugo on 01/05/17.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseAdapterViewHolder>{

    private List<Exercise> exList;
    final private ExerciseAdapterOnClickHandler mClickHandler;

    public ExerciseAdapter(List<Exercise> exList, ExerciseAdapterOnClickHandler clickHandler){
        this.exList = exList;
        mClickHandler = clickHandler;
    }


    public interface ExerciseAdapterOnClickHandler {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }


    public class ExerciseAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public final TextView mExercisetv;
        public final ToggleButton addStay;
        public ExerciseAdapterViewHolder(View view){
            super(view);
            mExercisetv = (TextView) view.findViewById(R.id.exercise_single);
            addStay = (ToggleButton) view.findViewById(R.id.add_stay);
            view.setOnClickListener(this);
            addStay.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {



            mClickHandler.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            mClickHandler.onLongClick(v, getAdapterPosition());
            return true;
        }
    }


    //making view holder
    @Override
    public ExerciseAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context ctx = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        int layoutId = R.layout.rv_layout;
        View view = inflater.inflate(layoutId, viewGroup, false);

        ExerciseAdapterViewHolder viewHolder = new ExerciseAdapterViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ExerciseAdapterViewHolder holder, int position) {
        Exercise exerciseInPosition =  exList.get(position);
        String data = exerciseInPosition.getExerciseName();

        holder.addStay.setChecked(exerciseInPosition.getStatus());

        holder.mExercisetv.setText(data);

    }

    @Override
    public int getItemCount() {
        if (exList == null){
            return 0;
        }
        return exList.size();
    }

}