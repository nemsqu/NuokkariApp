package com.example.nuokkariapp;

class Feedback {

    String givenFeedback, writer;


    public Feedback(String text, String writer){
        this.givenFeedback = text;
        this.writer = writer;
    }

    public void setGivenFeedback(String givenFeedback) {
        this.givenFeedback = givenFeedback;
    }

    public String getGivenFeedback() {
        return givenFeedback;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }
}
