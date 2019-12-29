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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
