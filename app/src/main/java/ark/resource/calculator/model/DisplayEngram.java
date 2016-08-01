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
public class DisplayEngram extends Engram {

    private long categoryId;

    public DisplayEngram(long id, String name, Integer imageId, long categoryId) {
        super(id, name, imageId);

        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }
}