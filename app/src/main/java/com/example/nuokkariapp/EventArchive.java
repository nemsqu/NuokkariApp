package com.example.nuokkariapp;


import java.util.ArrayList;

public class EventArchive {

    private ArrayList<Event> pastEventsList;
    private static EventArchive eventArchive = new EventArchive();

    static EventArchive getInstance(){
        return eventArchive;
    }

    private EventArchive() {
        pastEventsList = new ArrayList<>();
    }

    public void addEventToArchive(Event event){
        event.setIndex(pastEventsList.size());
        pastEventsList.add(event);
        XmlHandler.getInstance().writeToFile(EventCollection.getInstance().getEventArrayList(), pastEventsList);
    }

    public ArrayList<Event> getPastEventsList() {
        return pastEventsList;
    }

    public void removeEvent(Event event){
        pastEventsList.remove(event.getIndex());
        for(int i=0; i<pastEventsList.size(); i++){
            pastEventsList.get(i).setIndex(i);
        }
    }
}

