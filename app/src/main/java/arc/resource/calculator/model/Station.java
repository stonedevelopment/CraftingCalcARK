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
package arc.resource.calculator.model;

import androidx.annotation.NonNull;

/**
 * Base Station object
 */
public class Station {
    // Primary Key used to track hierarchy when filtering folders
    private final long mId;

    // String literal of Crafting Station taken verbatim from in-game
    private final String mName;

    // String literal of Station's image folder location
    private final String mFolder;

    // String literal of Station's image filename
    private final String mFile;

    public Station(long _id, String name, String folder, String file) {
        mId = _id;
        mName = name;
        mFolder = folder;
        mFile = file;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    private String getFolder() {
        return mFolder;
    }

    private String getFile() {
        return mFile;
    }

    public String getImagePath() {
        return getFolder() + getFile();
    }

    @NonNull
    @Override
    public String toString() {
        return "mEngramId=" + mId + ", mName=" + mName + ", mFolder=" + mFolder + ", mFile=" + mFile;
    }
}
