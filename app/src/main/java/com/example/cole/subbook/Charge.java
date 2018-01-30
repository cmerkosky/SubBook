package com.example.cole.subbook;

import android.util.Log;

/**
 * Created by cole on 29/01/18.
 */

public class Charge extends Object{
    private int dollars;
    private int cents;

    public void Charge(int dollars, int cents){
        this.dollars = dollars;
        if(cents > 99){
            Log.e("Charge", "Too many cents!")
        }
        else {
            this.cents = cents;
        }
    }
     @Override
    public String toString(){
        if(cents < 10){ return "$" + this.dollars + ".0" + this.cents; }
        else { return "$" + this.dollars + "." + this.cents; }
    }
}
