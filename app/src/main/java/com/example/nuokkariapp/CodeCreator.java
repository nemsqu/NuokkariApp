package com.example.nuokkariapp;

public class CodeCreator {

    public CodeCreator(){

    }

    //randomly chooses a number between 0-9 and adds to string (length 6)
    public String createCode(){

        String numericString = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = (int)(numericString.length() * Math.random());
            sb.append(numericString.charAt(index));
        }
        return sb.toString();
    }
}
