package com.example.nuokkariapp.UserInfo;

public class User {

    String name;
    String phoneNumber;
    String email;
    String password;

    public User(String name, String number, String email, String password){
        this.name = name;
        this.phoneNumber = number;
        this.email = email;
        this.password = password;

    }

    public User(String email, String password){

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
}
