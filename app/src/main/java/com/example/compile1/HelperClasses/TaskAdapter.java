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


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    public static ArrayList<com.example.compile1.HelperClasses.Task> taskLocations = new ArrayList<>();


    //when task completed it will be put in this arraylist
    public static ArrayList<com.example.compile1.HelperClasses.Task> checkedTask = new ArrayList<>();
    Context context;



    public TaskAdapter(ArrayList<com.example.compile1.HelperClasses.Task> taskLocations, Context context) {
        this.context = context;
        this.taskLocations = taskLocations;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        TaskAdapter.TaskViewHolder taskViewHolder = new TaskAdapter.TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.taskName.setText(taskLocations.get(position).taskName);
        holder.task_due.setText(taskLocations.get(position).taskDue);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                CheckBox chk = (CheckBox) v.findViewById(R.id.task_checked);
                TextView isDone =  v.findViewById(R.id.task_isDone);
                if(chk.isChecked()){
                    //checked item goes to a list which will then be used for progress
                    checkedTask.add(taskLocations.get(position));
                    isDone.setVisibility(View.VISIBLE);

                }else{
                    isDone.setVisibility(View.GONE);

                }


            }
        });

        holder.taskName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("taskName",taskLocations.get(position).taskName);
                intent.putExtra("taskDesc",taskLocations.get(position).taskDesc);
                intent.putExtra("taskDue",taskLocations.get(position).taskDue);
                intent.putExtra("teamID", taskLocations.get(position).teamID);
                intent.putExtra("taskID",taskLocations.get(position).taskID);
//                Bundle args = new Bundle();
                //get taskitems in Task.java
//                args.putSerializable("taskItems",(Serializable)taskLocations.get(position).getTaskItems());
//                intent.putExtra("BUNDLE",args);
                intent.putExtra("TaskDesc",taskLocations.get(position).taskDesc);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        if(taskLocations == null){
            return 0;
        }else{
            return taskLocations.size();
        }
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskName,status,task_due;
        CheckBox taskChecked;
        TextView isTaskDone;
        ItemClickListener itemClickListener;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskChecked = itemView.findViewById(R.id.task_checked);
            isTaskDone = itemView.findViewById(R.id.task_isDone);

            taskName = itemView.findViewById(R.id.task_name);
            task_due = itemView.findViewById(R.id.task_due);
            status = itemView.findViewById(R.id.task_status);



        }


        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }

    //when recycleview checkbox is clicked
    public interface ItemClickListener{
        void onItemClick(View v,int position);
    }
}
