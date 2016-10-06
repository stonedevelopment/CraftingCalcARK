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
 * Base Category object
 */
public class Category {
    private static final long ROOT = 0;

    // Primary Key used to track hierarchy when filtering folders
    private long mId;

    // String literal of Category's taken verbatim from in-game
    private String mName;

    // Foreign Key that references a Category's Primary Key used to track hierarchy when filtering folders
    private long mParent;

    // String literal of Resource Drawable to use for icon
    private String mDrawable;

    // Foreign Key that references the Game Version this Category belongs to
    private long mVersion;

    public Category( long id, String name, long parent, long version ) {
        this.mId = id;
        this.mName = name;
        this.mParent = parent;
        this.mVersion = version;
        this.mDrawable = "folder";
    }

    // Constructor used only for inserting a "back" category to go back one level
    public Category( long id, String name, long parent, String drawable ) {
        this.mId = id;
        this.mName = name;
        this.mParent = parent;
        this.mDrawable = drawable;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public long getParent() {
        return mParent;
    }

    public String getDrawable() {
        return mDrawable;
    }

    public long getVersion() {
        return mVersion;
    }

    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mParent=" + mParent;
    }
}
