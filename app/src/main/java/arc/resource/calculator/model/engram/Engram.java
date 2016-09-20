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

package arc.resource.calculator.model.engram;

/**
 * Base Engram object
 */
public class Engram {
    // Primary Key created by Database to track its row location.
    private long id;

    // String literal of an Engram's name verbatim from in-game
    private String name;

    // Filename of drawable resource
    private String drawable;

    public Engram( long id, String name, String drawable ) {
        this.id = id;
        this.name = name;
        this.drawable = drawable;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDrawable() {
        return drawable;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", drawable=" + drawable;
    }
}
