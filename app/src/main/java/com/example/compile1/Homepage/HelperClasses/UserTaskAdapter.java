package com.example.compile1.Homepage.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.R;

import java.util.ArrayList;

public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.UserTaskViewHolder> {
    public static ArrayList<UserTaskHelperClass> taskLocations = new ArrayList<>();
    Context context;


    public UserTaskAdapter(ArrayList<UserTaskHelperClass> taskLocations,Context context){
        this.taskLocations = taskLocations;
        this.context = context;

    }

    @NonNull
    @Override
    public UserTaskAdapter.UserTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_assigned_list,parent,false);
        UserTaskAdapter.UserTaskViewHolder userTaskViewHolder = new UserTaskAdapter.UserTaskViewHolder(view);

        return userTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserTaskAdapter.UserTaskViewHolder holder, int position) {
        UserTaskHelperClass userTaskHelperClass = taskLocations.get(position);
        holder.taskName.setText(userTaskHelperClass.getTaskName());
        holder.taskStatus.setText(userTaskHelperClass.getStatus());
    }

    @Override
    public int getItemCount() {
        if(taskLocations == null){
            return 0;
        }else{
            return taskLocations.size();
        }
    }

    public static class UserTaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskName,taskStatus;

        public UserTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.task_name);
            taskStatus = itemView.findViewById(R.id.task_status);



        }


    }
}
