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
 * Engram object that holds the id of the category (level) it lays on
 */
public class DisplayEngram extends Engram {
    private long mCategoryId;

    public DisplayEngram( long id, String name, String drawable, int yield, long categoryId ) {
        super( id, name, drawable, yield );

        this.mCategoryId = categoryId;
    }

    public long getCategoryId() {
        return mCategoryId;
    }
}