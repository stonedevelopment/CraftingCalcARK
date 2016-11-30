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
package arc.resource.calculator.model;

/**
 * Base Station object
 */
public class Station {
    // Primary Key used to track hierarchy when filtering folders
    private long mId;

    // String literal of Crafting Station taken verbatim from in-game
    private String mName;

    // String literal of Resource Drawable to use for thumbnail
    private String mDrawable;

    // Foreign Key that references the DLC this Station belongs to
    private long mDLCId;

    public Station( long _id, String name, String drawable, long dlc_id ) {
        this.mId = _id;
        this.mName = name;
        this.mDLCId = dlc_id;
        this.mDrawable = drawable;
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

    public long getDLCId() {
        return mDLCId;
    }

    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mDrawable=" + mDrawable + ", mDLCId=" + mDLCId;
    }
}
