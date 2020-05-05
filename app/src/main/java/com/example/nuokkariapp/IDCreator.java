package com.example.nuokkariapp;

public class IDCreator {

    private int ID;
    public static IDCreator idCreator = new IDCreator();

    private IDCreator(){
        ID = 0;
    }

    public static IDCreator getInstance(){
        return idCreator;
    }

    public int getID() {
        ID++;
        return ID;
    }
}
