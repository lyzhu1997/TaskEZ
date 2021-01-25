package com.example.compile1.HelperClasses;

import android.content.Intent;

public class TaskItem {
    String task_item_name;
    String task_assigned;
    Boolean is_Done;
    Integer files;

    public TaskItem(){
        //empty contrcutor for Firebase
    }

    public TaskItem(String task_item_name, String task_assigned, Boolean is_Done) {
        this.task_item_name = task_item_name;
        this.task_assigned = task_assigned;
        this.is_Done = is_Done;
    }

    public String getTask_item_name() {
        return task_item_name;
    }

    public void setTask_item_name(String task_item_name) {
        this.task_item_name = task_item_name;
    }

    public String getTask_assigned() {
        return task_assigned;
    }

    public void setTask_assigned(String task_assigned) {
        this.task_assigned = task_assigned;
    }

    public Boolean getIs_Done() {
        return is_Done;
    }

    public void setIs_Done(Boolean is_Done) {
        this.is_Done = is_Done;
    }

    public Integer getFiles() {
        return files;
    }

    public void setFiles(Integer files) {
        this.files = files;
    }




}
