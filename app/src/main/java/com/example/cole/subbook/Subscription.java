package com.example.cole.subbook;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public String getName(){
        return this.name;
    }

    public String getComment() { return this.comment; }

    public String getChargeString() { return this.charge.toString(); }

    public Charge getCharge() { return this.charge; }

    public Date getDate(){
        return this.date;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setComment(String comment) { this.comment = comment; }

    public void setCharge(Charge charge) { this.charge = charge; }

    public void setDate(Date date) { this.date = date; }

    public String toString(){
        return name + " | " + date + " | " + charge + " | " + comment;
    }

    public String getDetails(){

        String dateString = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA).format(this.date);

        if (this.comment == null || this.comment.equals("")) {
            return "$" + this.charge + " | " + dateString;
        }
        else{
            return "$" + this.charge + " | " + dateString + " | " + this.comment;
        }
    }
}
