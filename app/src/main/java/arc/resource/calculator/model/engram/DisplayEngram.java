/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */
package arc.resource.calculator.model.engram;

import androidx.annotation.NonNull;

/**
 * Engram object that holds the id of the category (level) it lies on
 */
public class DisplayEngram extends QueueEngram {
    private long mCategoryId;

    public DisplayEngram(long id, String name, String folder, String file, int yield, long categoryId, int quantity) {
        super(id, name, folder, file, yield, quantity);

        setCategoryId(categoryId);
    }

    private void setCategoryId(long category_id) {
        this.mCategoryId = category_id;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + ", mCategoryId=" + getCategoryId();
    }
}