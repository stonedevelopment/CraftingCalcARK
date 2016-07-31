package ark.resource.calculator.model;

import android.util.SparseArray;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone, Stone Development
 * Title: ARK:Crafting Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
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