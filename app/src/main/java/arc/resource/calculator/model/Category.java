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
package arc.resource.calculator.model;

/**
 * Base Category object
 */
public class Category {
    private static final long ROOT = 0;

    // Primary Key used to track hierarchy when filtering folders
    private long id;

    // String literal of Category's taken verbatim from in-game
    private String name;

    // Foreign Key that references a Category's Primary Key used to track hierarchy when filtering folders
    private long parent;

    public Category( long id, String name ) {
        this.id = id;
        this.name = name;
        this.parent = ROOT;
    }

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

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", parent=" + parent;
    }
}
