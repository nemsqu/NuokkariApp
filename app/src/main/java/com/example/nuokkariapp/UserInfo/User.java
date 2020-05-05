package com.example.nuokkariapp.UserInfo;

import java.io.Serializable;

public class User implements Serializable {

    private String name, phoneNumber, email, password;
    private int ID;

    public User(String name, String number, String email, String password){
        this.name = name;
        this.phoneNumber = number;
        this.email = email;
        this.password = password;
        ID = 0;
    }

    public User(String name, String number, String email, String password, int ID){
        this.name = name;
        this.phoneNumber = number;
        this.email = email;
        this.password = password;
        this.ID = ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
