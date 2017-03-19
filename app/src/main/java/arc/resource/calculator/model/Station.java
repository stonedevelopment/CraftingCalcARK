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
package arc.resource.calculator.model;

/**
 * Base Station object
 */
class Station {
    // Primary Key used to track hierarchy when filtering folders
    private final long mId;

    // String literal of Crafting Station taken verbatim from in-game
    private final String mName;

    // String literal of Station's image folder location
    private final String mFolder;

    // String literal of Station's image filename
    private final String mFile;

    // Foreign Key that references the DLC this Station belongs to
    private final long mDLCId;

    public Station( long _id, String name, String folder, String file, long dlc_id ) {
        mId = _id;
        mName = name;
        mFolder = folder;
        mFile = file;
        mDLCId = dlc_id;
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

    public long getDLCId() {
        return mDLCId;
    }

    @Override
    public String toString() {
        return "mEngramId=" + mId + ", mName=" + mName + ", mFolder=" + mFolder + ", mFile=" + mFile + ", mDLCId=" + mDLCId;
    }
}
