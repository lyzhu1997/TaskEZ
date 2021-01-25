package com.example.compile1.HelperClasses;

import java.util.ArrayList;

public class Task extends ArrayList<Task> {
    String taskName;
    String taskDue;
    String taskDesc;
    String teamID;
    String taskID;
    ArrayList<TaskItem> taskItems = new ArrayList<>();

    public Task(){
        //empty contructor for firebase
    }


    public Task(String taskName, String taskDue, String taskDesc, String teamID, String taskID) {
        this.taskName = taskName;
        this.taskDue = taskDue;
        this.taskDesc = taskDesc;
        this.teamID = teamID;
        this.taskID = taskID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDue() {
        return taskDue;
    }

    public void setTaskDue(String taskDue) {
        this.taskDue = taskDue;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public ArrayList<TaskItem> getTaskItems() {

        return taskItems;
    }

    public void setTaskItems(ArrayList<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }
}
