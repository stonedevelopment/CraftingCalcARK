package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Description: Base Resource object FIXME: This does not currently implement complex Resource objects (resources composed of resources)
 * Usage: Store retrievable Resource data
 * Used by: DataSource, ResourceListAdapter, Showcase
 * Variables: id, name, imageId
 *
 * Last Edit: Removed constructors with just name and imageId, now a true base Resource object
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
     * @param id    ROWID given by database
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

