package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Description: Base Engram object
 * Usage: Store basic Engram
 * Used by: InitEngram, DisplayEngram
 * Variables: id, imageId, name
 */
public class Engram {
    // Holds id held in database
    private long id;

    // Holds string literal of Engram's in-game name
    private String name;

    // Holds drawable resource id
    private Integer imageId;

    public Engram(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Engram(long id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", imageId=" + imageId;
    }
}
