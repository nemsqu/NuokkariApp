package com.example.nuokkariapp;

import java.util.ArrayList;

public class FeedbackCollection {

    private ArrayList<Feedback> fbArrayList;
    private static FeedbackCollection eventCollection = new FeedbackCollection();

    static FeedbackCollection getInstance(){
        return eventCollection;
    }

    private FeedbackCollection() {
        fbArrayList = new ArrayList<>();
    }

    public void addFeedbackToList(Feedback feedback){
        fbArrayList.add(feedback);
    }

    public void modifyFeedbackOnList(Feedback feedback, int index){
        fbArrayList.add(index, feedback);
        fbArrayList.remove(index + 1);
    }

    public ArrayList<Feedback> getFeedbackArrayList() {
        return fbArrayList;
    }
}

