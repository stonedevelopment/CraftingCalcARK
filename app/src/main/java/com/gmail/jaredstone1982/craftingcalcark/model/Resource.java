package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Description: Base Resource object
 * Usage: Store easily retrievable Resource data
 * Used by: ResourceInitializer
 * Variables: name, imageId
 */
public class Resource {
    // Contains string literal of Resource's name
    private String name;

    // Drawable resource image id
    private int imageId;

    /**
     * Full constructor method for Resource, takes image id and a string literal
     *
     * @param imageId Drawable resource image id
     * @param name    Contains string literal of Resource's name
     */
    public Resource(String name, int imageId) {
        this.imageId = imageId;
        this.name = name;
    }
    public Resource(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

