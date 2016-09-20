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

import android.util.SparseIntArray;

public class InitEngram {
    // String literal of an Engram's name verbatim from in-game
    private String mName;

    // Filename of drawable resource
    private int mDrawable;

    // String literal of description taken verbatim from in-game
    private String mDescription;

    // Foreign key that references a Category's Primary Key created by Database to track its row location
    private long mCategoryId;

    // SparseArray of CompositeResources required to compose Engram per in-game recipe
    private SparseIntArray mComposition;

    public InitEngram( int drawableId, String name, String description, SparseIntArray compositonIds, long categoryId ) {
        this.mName = name;
        this.mDescription = description;
        this.mDrawable = drawableId;
        this.mCategoryId = categoryId;
        this.mComposition = compositonIds;
    }

    public String getName() {
        return mName;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public String getDescription() {
        return mDescription;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public SparseIntArray getComposition() {
        return mComposition;
    }
}