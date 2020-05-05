package com.example.nuokkariapp;


import java.io.Serializable;
import java.util.ArrayList;

public class EventCollection implements Serializable {

    private ArrayList<Event> eventArrayList;
    private ArrayList<RecurringEvent> recurringEventArrayList;
    private static EventCollection eventCollection = new EventCollection();
    private Event temporaryStore;

    static EventCollection getInstance(){
        return eventCollection;
    }

    private EventCollection() {
        eventArrayList = new ArrayList<>();
        recurringEventArrayList = new ArrayList<>();
        temporaryStore = null;
    }

    public void addEventToList(Event event){
        eventArrayList.add(event);
        event.setIndex(eventArrayList.size()-1);
        XmlHandler.getInstance().writeToFile(eventArrayList, EventArchive.getInstance().getPastEventsList());
    }

    public void addEventToList(RecurringEvent event){
        recurringEventArrayList.add(event);
        event.setIndex(recurringEventArrayList.size()-1);
    }

    public void modifyEventOnList(Event event, int index){
        eventArrayList.add(index, event);
        eventArrayList.remove(index + 1);
        XmlHandler.getInstance().writeToFile(eventArrayList, EventArchive.getInstance().getPastEventsList());
    }

    public void modifyEventOnList(RecurringEvent event, int index){
        recurringEventArrayList.add(index, event);
        recurringEventArrayList.remove(index + 1);
    }

    public void endEvent(int index){
        eventArrayList.remove(index);
        for(int i=0; i<eventArrayList.size(); i++){
            eventArrayList.get(i).setIndex(i);
        }
    }

    public ArrayList<Event> getEventArrayList() {
        return eventArrayList;
    }


    public ArrayList<RecurringEvent> getRecurringEventArrayList() {
        return recurringEventArrayList;
    }

    public void setTemporaryStore(Event temporaryStore) {
        this.temporaryStore = temporaryStore;
    }

    public Event getTemporaryStore() {
        return temporaryStore;
    }
}

