package com.example.compile1.Homepage.HelperClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import com.example.compile1.Login.UserDetail;

public class TeamHelperClass implements Serializable {

    //user team table
    String ID;
    String title;
    String description;
    String date;
    ArrayList<UserDetail> users;
    ArrayList<UserTaskHelperClass> tasks;
    String chat;

    public TeamHelperClass(){

    }

    public TeamHelperClass(String title, String description, String ID) {
        this.title = title;
        this.description = description;
        this.ID = ID;
    }

    public TeamHelperClass(String ID,String title, String description, ArrayList<UserDetail> users) {
        this.title = title;
        this.description = description;
        this.users = users;
        this.ID = ID;
    }

    public ArrayList<UserTaskHelperClass> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<UserTaskHelperClass> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<UserDetail> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserDetail> users) {
        this.users = users;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
