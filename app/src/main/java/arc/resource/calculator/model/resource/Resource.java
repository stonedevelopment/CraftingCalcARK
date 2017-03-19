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
package arc.resource.calculator.model.resource;

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

    public Resource( long id, String name, String folder, String file ) {
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

    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mFolder=" + mFolder + ", mFile=" + mFile;
    }
}

