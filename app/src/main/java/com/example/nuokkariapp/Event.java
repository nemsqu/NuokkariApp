package com.example.nuokkariapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {

    private String name, location, date, start, end, age, description, imageUri;
    private int index, visitorLimit, visitorCount, ID;
    private boolean isOnGoing = false;
    private ArrayList<Feedback> eventFeedbacks;

    public Event(String name, String location, String date, String start, String end, String age, int limit, String description, String uri, int ID){
        this.name = name;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
        this.age = age;
        this.description = description;
        this.visitorLimit = limit;
        this.visitorCount = 0;
        eventFeedbacks = new ArrayList<>();
        imageUri = uri;
        this.ID = ID;
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

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public int getVisitorLimit() {
        return visitorLimit;
    }

    public void setVisitorLimit(int visitorLimit) {
        this.visitorLimit = visitorLimit;
    }

    public void addFeedbackToEvent(Feedback fb){
        eventFeedbacks.add(fb);
    }

    public void modifyFeedbackOnList(Feedback feedback, int index){
        eventFeedbacks.add(index, feedback);
        eventFeedbacks.remove(index + 1);
    }

    public ArrayList<Feedback> getEventFeedbacks(){
        return eventFeedbacks;
    }

    public void setEventFeedbacks(ArrayList<Feedback> eventFeedbacks) {
        this.eventFeedbacks = eventFeedbacks;
    }

    public void setImageURI(String imageURI) {
        this.imageUri = imageURI;
    }

    public String getImageURI() {
        return imageUri;
    }

    public int getID() {
        return ID;
    }
}
