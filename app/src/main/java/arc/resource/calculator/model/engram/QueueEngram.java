/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */
package arc.resource.calculator.model.engram;

import androidx.annotation.NonNull;

/**
 * Engram object that uses a yielded quantity for working with totals in the QueueRepository
 */
public class QueueEngram extends Engram {

    // Quantity used to keep track of how many Engrams in QueueRepository
    private int mQuantity;

    public QueueEngram(long id, String name, String folder, String file, int yield, int quantity) {
        super(id, name, folder, file, yield);

        mQuantity = quantity;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void increaseQuantity() {
        mQuantity++;
    }

    public void increaseQuantity(int increment) {
        int amount = getQuantity() + increment;
        int floor = amount / increment;

        setQuantity(floor * increment);
    }

    public void decreaseQuantity() {
        if (getQuantity() > 0)
            mQuantity--;
    }

    public void decreaseQuantity(int decrement) {
        if (getQuantity() >= decrement) {
            int amount;

            if (getQuantity() % decrement == 0)
                amount = getQuantity() - decrement;
            else
                amount = getQuantity();

            int floor = amount / decrement;

            setQuantity(floor * decrement);
        } else {
            if (getQuantity() > 1) {
                setQuantity(1);
            }
        }
    }

    public void resetQuantity() {
        setQuantity(0);
    }

    public Integer getQuantityWithYield() {
        return mQuantity * getYield();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + ", mQuantity=" + mQuantity;
    }
}
