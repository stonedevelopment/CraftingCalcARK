package ark.resource.calculator.model;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: ARK:Resource Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class Resource {
    // ROWID used for Resource table in database and TRACK_RESOURCE from other objects' tables
    private long id;

    // Contains string literal of Resource's name
    private String name;

    // Drawable resource image id
    private int imageId;

    /**
     * Full constructor method for Resource, takes image id and a string literal
     *
     * @param id      ROWID given by database
     * @param imageId Drawable resource image id
     * @param name    Contains string literal of Resource's name
     */
    public Resource(long id, String name, int imageId) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setId(long id) {
        this.id = id;
    }
}

