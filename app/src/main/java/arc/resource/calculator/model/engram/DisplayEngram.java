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
package arc.resource.calculator.model.engram;

/**
 * Engram object that holds the id of the category (level) it lies on
 */
public class DisplayEngram extends Engram {
    private final long mCategoryId;
    private int mQuantity;

    public DisplayEngram( long id, String name, String folder, String file, int yield, long categoryId, int quantity ) {
        super( id, name, folder, file, yield );

        mCategoryId = categoryId;
        mQuantity = quantity;
    }

    private long getCategoryId() {
        return mCategoryId;
    }

    private int getQuantity() {
        return mQuantity;
    }

    public void setQuantity( int quantity ) {
        mQuantity = quantity;
    }

    public void resetQuantity() {
        mQuantity = 0;
    }

    public int getQuantityWithYield() {
        return mQuantity * getYield();
    }

    @Override
    public String toString() {
        return super.toString() + ", mCategoryId=" + getCategoryId() + ", mQuantity=" + getQuantity() +
                ", getQuantityWithYield=" + getQuantityWithYield();
    }
}