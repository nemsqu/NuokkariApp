package com.example.nuokkariapp;

import java.util.Date;
import java.util.Locale;

public class Timer {

    private Date startTime = null, endTime = null;
    private String duration;

    public static Timer timer = new Timer();

    private Timer(){
    }
    public static Timer getInstance(){
        return timer;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        int d = (int) (endTime.getTime() - startTime.getTime());
        long seconds = d / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        duration =  String.format(Locale.ROOT, "Tapahtuman kesto oli %d tuntia, %02d minuuttia ja %02d sekuntia.", h,m,s);
        return duration;
    }

    public void initializeTimer(){
        startTime = null;
        endTime = null;
    }
}
