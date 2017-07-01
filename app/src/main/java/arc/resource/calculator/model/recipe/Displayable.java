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
package arc.resource.calculator.model.recipe;

/**
 * Craftable object that holds the id of the category (level) it lies on
 */
public class Displayable extends Queueable {
    private long mCategoryId;

    public Displayable( long id, String name, String folder, String file, int yield, long categoryId, int quantity ) {
        super( id, name, folder, file, yield, quantity );

        setCategoryId( categoryId );
    }

    private void setCategoryId( long category_id ) {
        this.mCategoryId = category_id;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    @Override
    public String toString() {
        return super.toString() + ", mCategoryId=" + getCategoryId();
    }
}