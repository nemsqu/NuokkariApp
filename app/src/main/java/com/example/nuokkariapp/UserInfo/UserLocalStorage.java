package com.example.nuokkariapp.UserInfo;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStorage {

    public static final String sharedPreferenceName = "UserDetails";
    SharedPreferences userLocalDatabase;

    //creates shared preferences
    public UserLocalStorage(Context context){
        userLocalDatabase = context.getSharedPreferences(sharedPreferenceName, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("email", user.email);
        spEditor.putString("phone", user.phoneNumber);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getCurrentUser(){
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String phone = userLocalDatabase.getString("phone", "");
        String password = userLocalDatabase.getString("password", "");

        User loggedInUser = new User(name, email, phone, password);
        return loggedInUser;
    }

    public void setUserLoggedIn(boolean signedIn){
        SharedPreferences.Editor editor = userLocalDatabase.edit();
        editor.putBoolean("signedIn", signedIn);
        editor.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor editor = userLocalDatabase.edit();
        editor.clear();
        editor.commit();
    }
}
