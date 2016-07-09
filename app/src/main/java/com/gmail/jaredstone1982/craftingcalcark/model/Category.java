package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Created by jared on 6/17/2016.
 */
public class Category {
    private long id;
    private String name;
    private int imageId;

    public Category(long id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }
    public Category() {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
