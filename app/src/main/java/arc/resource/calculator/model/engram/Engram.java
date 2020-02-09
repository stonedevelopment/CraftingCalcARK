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

import org.json.JSONException;
import org.json.JSONObject;

import arc.resource.calculator.db.DatabaseContract;

/**
 * Base Engram object
 */
public class Engram {
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

    public Engram(long id, String name, String folder, String file, int yield) {
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

    public int getYield() {
        return mYield;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", getName());
        jsonObject.put("description", getName());
        jsonObject.put("image_path", getImagePath());
        jsonObject.put("yield", getYield());

        return jsonObject;
    }

    public static Engram fromCursor(Cursor cursor) {
        long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry._ID));
        String name = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME));
        String folder = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER));
        String file = cursor
                .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE));
        int yield = cursor
                .getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD));

        return new Engram(_id, name, folder, file, yield);
    }

    @NonNull
    @Override
    public String toString() {
        return "mId=" + mId + ", mName=" + mName + ", mFolder=" + mFolder + ", mFile=" + mFile;
    }
}
