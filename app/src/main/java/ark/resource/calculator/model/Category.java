package ark.resource.calculator.model;

import ark.resource.calculator.R;

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
public class Category {
    private long id;
    private String name;
    private int level;
    private long parent;
    private int imageId;

    /**
     * Constructor for base level categories (STRUCTURES, WEAPONS...)
     */
    public Category(long id, String name) {
        this.level = 0;
        this.id = id;
        this.name = name;
        this.parent = 0;
        this.imageId = R.drawable.folder;
    }

    public Category(int level, long id, String name, long parent, int imageId) {
        this.level = level;
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.imageId = imageId;
    }

    /**
     * Constructor for specific level categories
     */
    public Category(int level, long id, String name, long parent) {
        this.level = level;
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.imageId = R.drawable.folder;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public long getParent() {
        return parent;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", level=" + level + ", parent=" + parent;
    }
}
