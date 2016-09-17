package arc.resource.calculator.model;

import arc.resource.calculator.R;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
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
    private long parent;

    /**
     * Constructor for base level categories (STRUCTURES, WEAPONS...)
     */
    public Category(long id, String name) {
        this.id = id;
        this.name = name;
        this.parent = 0;
    }

    /**
     * Constructor for specific level categories
     */
    public Category( long id, String name, long parent ) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getParent() {
        return parent;
    }

    public int getImageId() {
        return R.drawable.folder;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", parent=" + parent;
    }
}
