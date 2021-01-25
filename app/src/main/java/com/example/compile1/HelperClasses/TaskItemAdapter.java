package com.example.compile1.HelperClasses;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder> {
    ArrayList<TaskItem> taskItems = new ArrayList<>();
    Context context;

    public TaskItemAdapter(ArrayList<TaskItem> taskItems, Context context) {
        this.taskItems = taskItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use taskitem list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskitem_list_item,parent,false);
        TaskItemAdapter.TaskItemViewHolder taskItemViewHolder = new TaskItemAdapter.TaskItemViewHolder(view);
        return taskItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskItemViewHolder holder, int position) {
        holder.task_itemName.setText(taskItems.get(position).task_item_name);
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    

    public class TaskItemViewHolder extends RecyclerView.ViewHolder {
        TextView task_itemName;
        public TaskItemViewHolder(@NonNull View itemView) {
            super(itemView);
            task_itemName = itemView.findViewById(R.id.task_item);
        }
    }
}
