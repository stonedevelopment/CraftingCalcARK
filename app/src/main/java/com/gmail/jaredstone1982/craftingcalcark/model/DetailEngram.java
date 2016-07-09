package com.gmail.jaredstone1982.craftingcalcark.model;

import android.util.SparseArray;

/**
 * Description: DetailEngram object that extends CraftableEngram to include description, categories, and its composition
 * Usage: Store detailed Engram data to easily display objects' details pulled from Database
 * Used by: EngramDetailActivityFragment
 * Variables: id, imageId, name, description, categories, quantity, composition
 */
public class DetailEngram extends CraftableEngram {
    private String description;
    private SparseArray<Category> categories;
    private SparseArray<CraftableResource> composition;

    public DetailEngram(long id, String name, int imageId, String description, int quantity, SparseArray<CraftableResource> composition) {
        super(id, name, imageId, quantity);

        this.description = description;
        this.composition = composition;
        this.categories = new SparseArray<>();
    }

    public DetailEngram(long id, String name, int imageId, String description, SparseArray<CraftableResource> composition) {
        super(id, name, imageId, 0);

        this.description = description;
        this.composition = composition;
        this.categories = new SparseArray<>();
    }

    public DetailEngram(long id, String name, int imageId) {
        super(id, name, imageId);
    }

    public String getDescription() {
        return description;
    }

    public SparseArray<Category> getCategories() {
        return categories;
    }

    public void setCategories(SparseArray<Category> categories) {
        this.categories = categories;
    }

    public void insertCategory(Category category) {
        categories.append(categories.size(), category);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SparseArray<CraftableResource> getComposition() {
        return composition;
    }
}