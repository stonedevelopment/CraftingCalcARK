/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
package arc.resource.calculator.model.recipe;

/**
 * Craftable object that uses a yielded quantity for working with totals in the CraftingQueue
 */
public class Queueable extends Craftable {

    // Quantity used to keep track of how many Engrams in CraftingQueue
    private int mQuantity;

    public Queueable( long id, String name, String folder, String file, int yield, int quantity ) {
        super( id, name, folder, file, yield );

        mQuantity = quantity;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void increaseQuantity() {
        mQuantity++;
    }

    public void increaseQuantity( int increment ) {
        int amount = getQuantity() + increment;
        int floor = amount / increment;

        setQuantity( floor * increment );
    }

    public void decreaseQuantity() {
        if ( getQuantity() > 0 )
            mQuantity--;
    }

    public void decreaseQuantity( int decrement ) {
        if ( getQuantity() >= decrement ) {
            int amount;

            if ( getQuantity() % decrement == 0 )
                amount = getQuantity() - decrement;
            else
                amount = getQuantity();

            int floor = amount / decrement;

            setQuantity( floor * decrement );
        } else {
            if ( getQuantity() > 1 ) {
                setQuantity( 1 );
            }
        }
    }

    public void setQuantity( int quantity ) {
        mQuantity = quantity;
    }

    public void resetQuantity() {
        mQuantity = 0;
    }

    public Integer getQuantityWithYield() {
        return mQuantity * getYield();
    }

    @Override
    public String toString() {
        return super.toString() + ", mQuantity=" + mQuantity;
    }
}