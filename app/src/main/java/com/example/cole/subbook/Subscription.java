package com.example.cole.subbook;

import android.util.Log;

import java.util.Date;

/**
 * Created by cole on 29/01/18.
 */

public class Subscription {
    private final static String TAG = "Subscription";
    private String name;
    private Date date;
    private Charge charge;
    private String comment;

    public void Subscription(String name, Date date, Charge charge, String comment){
        this.date = date;
        this.charge = charge;

        if(name.length() >= 20) {
            this.name = name;
        }
        else{
            Log.e(TAG, "Name to long!");
        }


        if(comment.length() >= 30) {
            this.comment = comment;
        }
        else{
            Log.e(TAG, "Comment to long!");
        }
    }

    public String getName(){
        return this.name;
    }

    public Date getDate(){
        return this.date;
    }

    public Charge getCharge() {
        return this.charge;
    }

    public String getComment(){
        return this.comment;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setCharge(Charge charge){
        this.charge = charge;
    }

    public void setComment(String comment){
        this.comment = comment;
    }
}
