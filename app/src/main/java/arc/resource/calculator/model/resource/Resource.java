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
package arc.resource.calculator.model.resource;

import androidx.annotation.NonNull;

/**
 * Base Resource object
 */
public class Resource {
    // Primary Key created by Database used to track its row location
    private final long mId;

    // String literal of Resource's name taken verbatim in-game
    private final String mName;

    // String literal of Resource's image folder location
    private final String mFolder;

    // String literal of Resource's image filename
    private final String mFile;

    public Resource(long id, String name, String folder, String file) {
        mId = id;
        mFolder = folder;
        mFile = file;
        mName = name;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getFolder() {
        return mFolder;
    }

    public String getFile() {
        return mFile;
    }

    public String getImagePath() {
        return getFolder() + getFile();
    }

    @NonNull
    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mFolder=" + mFolder + ", mFile=" + mFile;
    }
}

