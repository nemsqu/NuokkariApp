package com.example.nuokkariapp;

import java.io.Serializable;

public class Event implements Serializable {

    private String name;
    private String location;
    private String date;
    private String start;
    private String end;
    private String age;
    private String description;
    private int index = 0;
    private boolean isOnGoing = false;

    public Event(String name, String location, String date, String start, String end, String age, String description){
        this.name = name;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
        this.age = age;
        this.description = description;
    }

    public void addToFile(){


    }

    public String getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getEnd() {
        return end;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isOnGoing() {
        return isOnGoing;
    }

    public void setOnGoing(boolean onGoing) {
        isOnGoing = onGoing;
    }
}
