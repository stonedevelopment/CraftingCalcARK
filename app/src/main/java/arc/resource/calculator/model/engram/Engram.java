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
 * Base Engram object
 */
public class Engram {
    // Primary Key created by Database to track its row location.
    private long mId;

    // String literal of an Engram's mName verbatim from in-game
    private String mName;

    // Filename of mDrawable resource
    private String mDrawable;

    // Yield per crafted item
    private int mYield;

    public Engram( long id, String name, String drawable, int yield ) {
        mId = id;
        mName = name;
        mDrawable = drawable;
        mYield = yield;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDrawable() {
        return mDrawable;
    }

    public int getYield() {
        return mYield;
    }

    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mDrawable=" + mDrawable;
    }
}
