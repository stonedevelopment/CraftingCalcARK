package arc.resource.calculator.model;

import android.util.SparseIntArray;

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
public class InitEngram extends Engram {
    // String literal of the description used in game
    private String description;

    // SparseIntArray where keys are Resource.imageIDs and values are relevant quantities
    private SparseIntArray compositionIDs;

    // String array of categories in hierarchical arrangement
    private long categoryId;

    public InitEngram(Integer imageId, String name, String description, SparseIntArray compositionIDs, long categoryId) {
        super(name, imageId);

        this.description = description;
        this.compositionIDs = compositionIDs;
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public SparseIntArray getCompositionIDs() {
        return compositionIDs;
    }

    public long getCategoryId() {
        return categoryId;
    }
}
