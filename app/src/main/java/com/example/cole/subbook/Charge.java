package com.example.cole.subbook;

import android.util.Log;

/**
 * A representation of a monthly charge for a given subscription
 */
public class Charge{
    private int dollars;
    private int cents;

    /**
     * Constructs a Charge instance
     * @param dollars The amount of dollars this charge has
     * @param cents The amount of cents this charge has
     */
    public Charge(int dollars, int cents){
        this.dollars = dollars;
        if(cents > 99){
            Log.e("Charge", "Too many cents!");
        }
        else {
            this.cents = cents;
        }
    }

    /**
     * Returns a formatted representation of this Charge
     * @return The money-formatted string of this Charge
     */
    @Override
    public String toString(){
        if(cents < 10){ return this.dollars + ".0" + this.cents; }
        else { return this.dollars + "." + this.cents; }
    }

    /**
     * Returns the number of cents this charge has
     * @return Cents in this charge
     */
    public int getCents() { return this.cents; }

    /**
     * Returns the number of dollars this charge has
     * @return
     */
    public int getDollars() { return  this.dollars; }
}
