package com.example.nuokkariapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidityChecker {

    private boolean hasNumber, hasUpperCase, hasLowerCase, hasSpecialCharacter;
    public PasswordValidityChecker(){
        hasNumber = false;
        hasUpperCase = false;
        hasLowerCase = false;
        hasSpecialCharacter = false;
    }

    //checks password was inserted identically twice
    public boolean checkPassword(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }else{
            return false;
        }
    }

    //checks password follows rules for a good password
    public String checkPassword(String password){
        if(password.length() < 12){
            return "Salasanan täytyy olla yli 12 merkin pituinen.";
        }else{
            for(int i = 0; i<password.length(); i++){
                Character c = password.charAt(i);
                if(Character.isDigit(c)){
                    hasNumber = true;
                }
                if(Character.isUpperCase(c)){
                    hasUpperCase = true;
                }
                if(Character.isLowerCase(c)){
                    hasLowerCase = true;
                }
            }
        }
        if(!hasNumber){
            return "Salasanan täytyy sisältää ainakin yksi numero.";
        }else if(!hasUpperCase){
            return "Salsananan täytyy sisältää ainakin yksi suuri kirjain.";
        }else if (!hasLowerCase){
            return "Salasanan täytyy sisältää ainakin yksi pieni kirjain.";
        }
        Pattern p = Pattern.compile("[^A-Öa-ö0-9]");//replace this with your needs
        Matcher m = p.matcher(password);
        boolean b = m.find();
        if (b){
            return "ok";
        }else{
            return "Salasanan täytyy sisältää ainakin yksi erikoismerkki.";
        }
    }
}