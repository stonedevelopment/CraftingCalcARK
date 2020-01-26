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

import android.database.Cursor;

import androidx.annotation.NonNull;

import arc.resource.calculator.db.DatabaseContract;

/**
 * Engram object that holds the id of the category (level) it lies on
 */
public class DisplayEngram extends QueueEngram {
    private long mCategoryId;

    private DisplayEngram(long id, String name, String folder, String file, int yield, long categoryId) {
        super(id, name, folder, file, yield, 0);

        setCategoryId(categoryId);
    }

    public DisplayEngram(long id, String name, String folder, String file, int yield, long categoryId, int quantity) {
        super(id, name, folder, file, yield, quantity);

        setCategoryId(categoryId);
    }

    public static DisplayEngram fromCursor(Cursor cursor) {
        long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry._ID));
        String name = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME));
        String folder = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER));
        String file = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE));
        int yield = cursor
                .getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD));
        long category_id = cursor
                .getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY));

        return new DisplayEngram(_id, name, folder, file, yield, category_id);
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