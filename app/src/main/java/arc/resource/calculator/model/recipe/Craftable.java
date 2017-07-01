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

public class Craftable {
    // Primary Key created by Database to track its row location.
    private final long mId;

    // String literal of an Engram's name verbatim from in-game
    private final String mName;

    // String literal of Engram's image folder
    private final String mFolder;

    // String literal of Engram's image filename
    private final String mFile;

    // Yield per crafted item
    private final int mYield;

    Craftable( long id, String name, String folder, String file, int yield ) {
        mId = id;
        mName = name;
        mFolder = folder;
        mFile = file;
        mYield = yield;
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

    int getYield() {
        return mYield;
    }

    @Override
    public String toString() {
        return "mId=" + mId + ", " +
                "mName=" + mName + ", " +
                "mFolder=" + mFolder + ", " +
                "mFile=" + mFile;
    }
}
