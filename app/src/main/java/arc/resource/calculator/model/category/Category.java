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
package arc.resource.calculator.model.category;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import arc.resource.calculator.db.DatabaseContract;

/**
 * Base Category object
 */
public class Category {
    // Primary Key used to track hierarchy when filtering folders
    private final long mId;

    // String literal of Category's taken verbatim from in-game
    private final String mName;

    // Foreign Key that references a Category's Primary Key used to track hierarchy when filtering folders
    private final long mParent;

    // String literal of Resource Drawable to use for icon
    private final String mFile;

    public Category(long _id, @Nullable String name, long parent) {
        mId = _id;
        mName = name;
        mParent = parent;
        mFile = "folder.webp";
    }

    public Category(long _id, @Nullable String name, long parent, String file) {
        mId = _id;
        mName = name;
        mParent = parent;
        mFile = file;
    }

    public static Category fromCursor(Cursor cursor) {
        long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.CategoryEntry._ID));
        String name = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.CategoryEntry.COLUMN_NAME));
        long parent_id = cursor
                .getLong(cursor.getColumnIndex(DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY));
        return new Category(_id, name, parent_id);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", getName());

        return jsonObject;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public long getParent() {
        return mParent;
    }

    public String getImagePath() {
        return mFile;
    }

    @NonNull
    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mParent=" + mParent;
    }
}
