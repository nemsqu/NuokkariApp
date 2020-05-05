package com.example.nuokkariapp;

import java.util.Date;

public class RecurringEvent extends Event {

    private boolean recurringDaily, recurringWeekly, recurringMonthly;
    private Date recurringUntil;
    private long differenceBetweenEvents;

    public RecurringEvent(String name, String location, String date, String start, String end,
                          String age, int limit, String description, String uri, int ID, int progressValue, Date endDate) {
        super(name, location, date, start, end, age, limit, description, uri, ID);
        if(progressValue == 1){
            setRecurringDaily(true);
        }else if(progressValue == 2){
            setRecurringWeekly(true);
        }else{
            setRecurringMonthly(true);
        }
        recurringUntil = endDate;
    }

    public void setRecurringDaily(boolean recurringDaily) {
        this.recurringDaily = recurringDaily;
        differenceBetweenEvents = 24*60*60*1000; //a day in milliseconds
    }
    public void setRecurringMonthly(boolean recurringMonthly) {
        this.recurringMonthly = recurringMonthly;
        long weeklyDifference = 7*24*60*60*1000;
        differenceBetweenEvents = 4*weeklyDifference; //four weeks in milliseconds
    }

    public void setRecurringWeekly(boolean recurringWeekly) {
        this.recurringWeekly = recurringWeekly;
        differenceBetweenEvents = 7*24*60*60*1000; //a day in milliseconds
    }

    public void setRecurringUntil(Date recurringUntil) {
        this.recurringUntil = recurringUntil;
    }

    public boolean isRecurringDaily() {
        return recurringDaily;
    }

    public boolean isRecurringMonthly() {
        return recurringMonthly;
    }

    public boolean isRecurringWeekly() {
        return recurringWeekly;
    }

    public Date getRecurringUntil() {
        return recurringUntil;
    }

    public long getDifferenceBetweenEvents(){
        return differenceBetweenEvents;
    }


}
