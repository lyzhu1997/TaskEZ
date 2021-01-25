package com.example.compile1.Login;

import java.util.ArrayList;

public class UserDetail {

    private String Nickname;
    private String ContactNumber;
    private String email;
    public ArrayList<String> teamID = new ArrayList<>();


    public UserDetail(String nickname, String contactNumber, String email) {
        Nickname = nickname;
        ContactNumber = contactNumber;
        this.email = email;
    }

    public UserDetail(){

    }

    public ArrayList<String> getTeamID() {
        return teamID;
    }

    public void setTeamID(ArrayList<String> teamID) {
        this.teamID = teamID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
