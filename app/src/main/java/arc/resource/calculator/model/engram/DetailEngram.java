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

import android.util.SparseArray;

import arc.resource.calculator.model.resource.CompositeResource;

public class DetailEngram extends Engram {
    // String literal of description taken verbatim from in-game
    private String mDescription;

    // Foreign key that references a Category's Primary Key created by Database to track its row location
    private long mCategoryId;

    // SparseArray of CompositeResources required to compose Engram per in-game recipe
    private SparseArray<CompositeResource> mComposition;

    public DetailEngram( long id, String name, String drawable, String description, SparseArray<CompositeResource> composition, long categoryId ) {
        super( id, name, drawable );

        this.mDescription = description;
        this.mComposition = composition;
        this.mCategoryId = categoryId;
    }

    public String getDescription() {
        return mDescription;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public SparseArray<CompositeResource> getComposition() {
        return mComposition;
    }
}