package com.example.cole.subbook;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class Subscription {
    private final static String TAG = "Subscription";
    private String name;
    private Date date;
    private Charge charge;
    private String comment;

    Subscription(String name, Date date, Charge charge, String comment){
        this.date = date;
        this.charge = charge;

        if(name.length() <= 20) {
            this.name = name;
        }
        else{
            Log.e(TAG, "Name too long!");
        }


        if(comment.length() <= 30) {
            this.comment = comment;
        }
        else{
            Log.e(TAG, "Comment too long!");
        }
    }

    Subscription(String name, Charge charge, String comment){
        this.date = new Date();
        this.charge = charge;

        if(name.length() <= 20) {
            this.name = name;
        }
        else{
            Log.e(TAG, "Name too long!");
        }


        if(comment.length() <= 30) {
            this.comment = comment;
        }
        else{
            Log.e(TAG, "Comment too long!");
        }
    }

    public String getName(){
        return this.name;
    }

    public Date getDate(){
        return this.date;
    }

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return name + " | " + date + " | " + charge + " | " + comment;
    }

    String getDetails(){

        // TODO: Format in a more appealing way
        String dateString = date.toString();

        return charge + " | " + dateString + " | " + comment;
    }
}
