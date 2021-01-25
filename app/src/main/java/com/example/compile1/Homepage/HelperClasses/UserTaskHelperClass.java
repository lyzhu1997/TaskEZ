package com.example.compile1.Homepage.HelperClasses;

import android.widget.ProgressBar;

import java.io.Serializable;

public class UserTaskHelperClass  implements Serializable {
    //usertask class or table
    String taskName;
    String taskID;
    String status;
    ProgressBar task_progress;
    String task_created;
    String task_due;
    String task_desc;

    public UserTaskHelperClass(String taskName, String status) {
        this.taskName = taskName;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProgressBar getTask_progress() {
        return task_progress;
    }

    public void setTask_progress(ProgressBar task_progress) {
        this.task_progress = task_progress;
    }

    public String getTask_created() {
        return task_created;
    }

    public void setTask_created(String task_created) {
        this.task_created = task_created;
    }

    public String getTask_due() {
        return task_due;
    }

    public void setTask_due(String task_due) {
        this.task_due = task_due;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }
}
