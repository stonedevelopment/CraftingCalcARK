package com.gmail.jaredstone1982.craftingcalcark.model;

import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * Description: InitEngram object that extends Engram with a description, composition array, and categories
 * Usage: Store instantiated data used to initialize Database
 * Used by: EngramInitializer, EngramDataSource
 * Variables: id, imageId, name, description, composition, category
 */
public class InitEngram extends Engram {
    // String literal of the description used in game
    private String description;

    // SparseIntArray where keys are Resource.imageIDs and values are relevant quantities
    private SparseIntArray compositionIDs;

    // String array of categories in hierarchical arrangement
    private SparseArray<String> category;

    public InitEngram(Integer imageId, String name, String description, SparseIntArray compositionIDs, SparseArray<String> category) {
        super(name, imageId);
        this.description = description;
        this.compositionIDs = compositionIDs;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public SparseIntArray getCompositionIDs() {
        return compositionIDs;
    }

    public SparseArray<String> getCategory() {
        return category;
    }
}
