package com.example.cole.subbook;

import android.util.Log;

public class Charge{
    private int dollars;
    private int cents;

    Charge(int dollars, int cents){
        this.dollars = dollars;
        if(cents > 99){
            Log.e("Charge", "Too many cents!");
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
