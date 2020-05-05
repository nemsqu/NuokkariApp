package com.example.nuokkariapp.UserInfo;

import java.io.Serializable;

public class UserLocalStorage implements Serializable {

    public static UserLocalStorage userLocalStorage = new UserLocalStorage();
    private String name, email, phone, password;
    private int userID;
    private boolean loggedIn;
    private User loggedInUser;

    private UserLocalStorage(){
        name = "";
        email = "";
        phone = "";
        password = "";
        userID = -1;
        loggedIn = false;
    }

    public static UserLocalStorage getInstance(){
        return userLocalStorage;
    }

    public void setLoggedInUser(User user){
        name = user.getName();
        email = user.getEmail();
        phone = user.getPhoneNumber();
        password = user.getPassword();
        userID = user.getID();
        loggedIn = true;
        loggedInUser = new User(name, phone, email, password, userID);
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    public void logUserOut(){
        name = "";
        email = "";
        phone = "";
        password = "";
        userID = -1;
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
