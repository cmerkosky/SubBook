package com.example.cole.subbook;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A representation of Subscription
 */
public class Subscription {
    private final static String TAG = "Subscription";
    private String name;
    private Date date;
    private Charge charge;
    private String comment;

    /**
     * Constructs a Subscription with the specified details
     * @param name The name of the subscription
     * @param date The date of the subscription
     * @param charge The charge of the subscription
     * @param comment The comment on the subscription
     */
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

    /**
     * Returns the name of the subscription
     * @return Subscription name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the comment of the subscription
     * @return Subscription comment
     */
    public String getComment() { return this.comment; }

    /**
     * Returns a string representation of the charge of the subscription
     * @return Subscription charge in the form 'dollars'.'cents'
     */
    public String getChargeString() { return this.charge.toString(); }

    /**
     * Returns the Charge object of the subscription
     * @return Subscription charge
     */
    public Charge getCharge() { return this.charge; }

    /**
     * Returns the date of the subscription
     * @return Subscription date
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Sets the name of the subscription
     * @param name Name to be set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the comment of the subscription
     * @param comment Comment to be set
     */
    public void setComment(String comment) { this.comment = comment; }

    /**
     * Sets the charge of the subscription
     * @param charge Charge to be set
     */
    public void setCharge(Charge charge) { this.charge = charge; }

    /**
     * Sets the date of the subscription
     * @param date Date to be set
     */
    public void setDate(Date date) { this.date = date; }

    /**
     * Returns a string representation of the Subscription
     * @return String representation of the subscription
     */
    public String toString(){
        return name + " | " + date + " | " + charge + " | " + comment;
    }

    /**
     * Returns the Charge, Date, and comment (if there is one) of this subscription
     * @return String representation of the details of the subscription
     */
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
