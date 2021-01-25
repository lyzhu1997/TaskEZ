package com.example.compile1.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.R;
import com.example.compile1.Team.TaskActivity;

import java.io.Serializable;
import java.util.ArrayList;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    public static ArrayList<String> taskLocations = new ArrayList<>();


    Context context;



    public MemberAdapter(ArrayList<String> taskLocations, Context context) {
        this.context = context;
        this.taskLocations = taskLocations;
    }


    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_item,parent,false);
        MemberAdapter.MemberViewHolder taskViewHolder = new MemberAdapter.MemberViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {

        holder.name.setText(taskLocations.get(position));
    }

    @Override
    public int getItemCount() {
        if(taskLocations == null){
            return 0;
        }else{
            return taskLocations.size();
        }
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.member_name);
        }
    }

    //when recycleview checkbox is clicked
    public interface ItemClickListener{
        void onItemClick(View v,int position);
    }
}
