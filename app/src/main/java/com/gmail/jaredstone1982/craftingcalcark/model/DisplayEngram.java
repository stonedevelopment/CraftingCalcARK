package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Description: DisplayableEngram object that extends Engram to include a list of categories
 * Usage: Store displayable Engram data to easily display objects pulled from Database
 * Used by: EngramListAdapter
 * Variables: id, imageId, name, categories
 */
public class DisplayEngram extends Engram {

    private long categoryId;

    public DisplayEngram(long id, String name, Integer imageId, long categoryId) {
        super(id, name, imageId);

        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }
}