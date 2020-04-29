package com.example.nuokkariapp;


import java.io.Serializable;
import java.util.ArrayList;

public class EventCollection implements Serializable {

    private ArrayList<Event> eventArrayList = new ArrayList<>();
    private static EventCollection eventCollection = new EventCollection(new Event("Ei tapahtumia", "", "", "", "", "", ""));

    static EventCollection getInstance(){
        return eventCollection;
    }

    private EventCollection(Event event) {

        eventArrayList.add(event);
    }

    public void addEventToList(Event event){
        eventArrayList.add(event);
        event.setIndex(eventArrayList.size()-1);
    }

    public void modifyEventOnList(Event event, int index){
        eventArrayList.add(index, event);
        eventArrayList.remove(index + 1);
    }

    public ArrayList<Event> getEventArrayList() {
        return eventArrayList;
    }
}

