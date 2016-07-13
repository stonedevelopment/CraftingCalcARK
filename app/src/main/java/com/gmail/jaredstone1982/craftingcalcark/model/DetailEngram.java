package com.gmail.jaredstone1982.craftingcalcark.model;

import android.util.SparseArray;

/**
 * Description: DetailEngram object that extends CraftableEngram to include description, categoryId, and its composition
 * Usage: Store detailed Engram data to easily display objects' details pulled from Database
 * Used by: EngramDetailActivityFragment
 * Variables: id, imageId, name, description, categoryId, quantity, composition
 */
public class DetailEngram extends CraftableEngram {
    private String description;
    private long categoryId;
    private SparseArray<CraftableResource> composition;

    public DetailEngram(long id, String name, int imageId, String description, long categoryId, int quantity, SparseArray<CraftableResource> composition) {
        super(id, name, imageId, quantity);

        this.description = description;
        this.composition = composition;
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SparseArray<CraftableResource> getComposition() {
        return composition;
    }
}