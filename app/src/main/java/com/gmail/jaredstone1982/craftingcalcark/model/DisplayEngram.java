package com.gmail.jaredstone1982.craftingcalcark.model;

import android.util.SparseArray;

/**
 * Description: DisplayableEngram object that extends Engram to include a list of categories
 * Usage: Store displayable Engram data to easily display objects pulled from Database
 * Used by: EngramListAdapter
 * Variables: id, imageId, name, categories
 */
public class DisplayEngram extends Engram {

    private SparseArray<Category> categories; // TODO: Convert to HashMap<Long, Object> to better track category ROWID from database

    public DisplayEngram(long id, String name, Integer imageId) {
        super(id, name, imageId);

        categories = new SparseArray<>();
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
}