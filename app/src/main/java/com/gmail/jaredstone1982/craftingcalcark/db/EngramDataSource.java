package com.gmail.jaredstone1982.craftingcalcark.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.model.Category;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableResource;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.InitEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.initializers.EngramInitializer;
import com.gmail.jaredstone1982.craftingcalcark.model.initializers.ResourceInitializer;

import java.util.List;

/**
 * Description: Source of data pulled from Database
 * Usage: Handle actions of all Database tables excluding TABLE_QUEUE
 * Used by: EngramActivityFrament
 * Variables: LOGTAG, openHelper, database
 */
public class EngramDataSource {
    private String LOGTAG;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    public EngramDataSource(Context context, String LOGTAG) {
        this.openHelper = new DBOpenHelper(context);
        this.LOGTAG = LOGTAG;
    }

    /**
     * Pulls entire Engram table, alongside Category table, to fill ListView with proper objects
     *
     * @return Array of Engram objects suitable for a displayable, sortable view
     */
    public SparseArray<DisplayEngram> findAll() {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM +
                        " INNER JOIN " + DBOpenHelper.TABLE_CATEGORY +
                        " ON " + DBOpenHelper.TABLE_CATEGORY + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + DBOpenHelper.TABLE_ENGRAM + "." + DBOpenHelper.COLUMN_ENGRAM_ID,
                null, null
        );

        return cursorToEngrams(cursor);
    }

    /**
     * Parse data pulled from cursor
     *
     * @param cursor Holds data pulled from Database Query
     * @return Array of DisplayEngram objects
     */
    public SparseArray<DisplayEngram> cursorToEngrams(Cursor cursor) {
        SparseArray<DisplayEngram> engrams = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DisplayEngram engram;

                // Grab imageId from cursor, this will be used to test if an object has been added or not
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));

                // Test to see if 'engram' has already been added
                if (engrams.indexOfKey(imageId) > -1) {
                    // If so, copy found object into 'engram'
                    engram = engrams.get(imageId);
                } else {
                    // If not, instantiate 'engram' with default values
                    long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));

                    engram = new DisplayEngram(id, name, imageId);
                }

                // Instantiate a Category object with data pulled from cursor
                Category category = new Category();
                category.setId(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_ID));
                category.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_NAME)));
                category.setImageId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_IMAGE_ID)));

                // Add new Category object to DisplayEngram object
                if (!category.equals(null)) {
                    engram.insertCategory(category);
                }

                // Insert/Update DisplayEngram object in Array
                if (!engram.equals(null)) {
                    engrams.put(imageId, engram);
                }
            }
        }

        return engrams;
    }

    /**
     * Method that drops all tables, creates all tables, and then re-initializes data TODO: Strictly for DEBUG purposes
     */
    public void Reset() {
        DBOpenHelper.dropAllTables(database);
        DBOpenHelper.createAllTables(database);

        Initialize();
    }

    /**
     * Method that initializes database with InitEngram objects from EngramInitializer
     */
    public SparseArray<DisplayEngram> Initialize() {
        List<InitEngram> engrams = EngramInitializer.getEngrams();
        for (InitEngram engram : engrams) {
            Insert(engram);
        }

        return findAll();
    }

    /**
     * Inserts an InitEngram object into database, used strictly for initializing
     *
     * @param engram InitEngram object that holds all data needed for initialization
     */
    private void Insert(InitEngram engram) {
        ContentValues engramValues = new ContentValues();
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_NAME, engram.getName());
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_DESCRIPTION, engram.getDescription());
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID, engram.getImageId());
        engram.setId(database.insert(DBOpenHelper.TABLE_ENGRAM, null, engramValues));

        Log.d(LOGTAG, "Engram (" + engram.getName() + ") inserted at " + engram.getId());

        for (int i = 0; i < engram.getCompositionIDs().size(); i++) {
            ContentValues resourceValues = new ContentValues();
            CraftableResource resource = ResourceInitializer.getCraftableResource(engram.getCompositionIDs().keyAt(i)); // FIXME: 6/15/2016 Pull data from Resource Database

            resourceValues.put(DBOpenHelper.COLUMN_RESOURCE_NAME, resource.getName());
            resourceValues.put(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID, resource.getImageId());
            resourceValues.put(DBOpenHelper.COLUMN_RESOURCE_QUANTITY, engram.getCompositionIDs().valueAt(i));
            resourceValues.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engram.getId());
            resource.setId(database.insert(DBOpenHelper.TABLE_RESOURCE, null, resourceValues));

            Log.d(LOGTAG, "Resource (" + resource.getName() +
                    " x" + engram.getCompositionIDs().valueAt(i) +
                    ") inserted into " + engram.getName() +
                    " at " + resource.getId());
        }

        for (int i = 0; i < engram.getCategory().size(); i++) {
            ContentValues categoryValues = new ContentValues();
            String categoryName = engram.getCategory().valueAt(i);

            categoryValues.put(DBOpenHelper.COLUMN_CATEGORY_NAME, categoryName);
            categoryValues.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engram.getId());

            long categoryId = database.insert(DBOpenHelper.TABLE_CATEGORY, null, categoryValues);

            Log.d(LOGTAG, "Category (" + categoryName +
                    ") inserted into " + engram.getName() +
                    " at " + categoryId);
        }
    }

    public void Open() {
        database = openHelper.getWritableDatabase();
        Log.d(LOGTAG, "Database open");
    }

    public void Close() {
        Log.d(LOGTAG, "Database closed");
        database.close();
    }
}