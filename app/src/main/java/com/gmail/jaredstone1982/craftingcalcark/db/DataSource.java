package com.gmail.jaredstone1982.craftingcalcark.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.model.Category;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.InitEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.Resource;
import com.gmail.jaredstone1982.craftingcalcark.model.initializers.EngramInitializer;
import com.gmail.jaredstone1982.craftingcalcark.model.initializers.ResourceInitializer;

import java.util.List;

/**
 * Description: Source of data pulled from Database
 * Usage: Handle actions of all Database tables excluding TABLE_QUEUE
 * Used by: EngramActivityFrament
 * Variables: LOGTAG, openHelper, database
 */
public class DataSource {
    private static final String LOGTAG = "DATASOURCE";

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    public DataSource(Context context, String LOGTAG) {
        this.openHelper = new DBOpenHelper(context);
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

                // Grab imageId from cursor, this will be used to test if an object has been added or not FIXME why are we testing this?
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
    public void Initialize() {
        Helper.Log(LOGTAG, "** Initializing database..");

        Helper.Log(LOGTAG, "-- Initializing Resources..");
        SparseArray<String> resources = ResourceInitializer.getResources();
        for (int i = 0; i < resources.size(); i++) {
            int imageId = resources.keyAt(i);
            String name = resources.valueAt(i);
            Insert(imageId, name);
        }
        Helper.Log(LOGTAG, "-- Resource initialization completed.");

        Helper.Log(LOGTAG, "-- Initializing Engrams..");
        List<InitEngram> engrams = EngramInitializer.getEngrams();
        for (InitEngram engram : engrams) {
            Insert(engram);
        }
        Helper.Log(LOGTAG, "-- Engram initialization completed.");
    }

    /**
     * Inserts resource data into data, TODO used strictly for initializing
     *
     * @param imageId Image Resource ID of resource to insert
     * @param name    Name of resource, used for displaying its contents in a list
     */
    private void Insert(int imageId, String name) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID, imageId);
        values.put(DBOpenHelper.COLUMN_RESOURCE_NAME, name);

        long id = database.insert(DBOpenHelper.TABLE_RESOURCE, null, values);

        Helper.Log(LOGTAG, "-> Resource (" + name + ") inserted at row " + id);
    }

    /**
     * Inserts an InitEngram object into database, TODO used strictly for initializing
     *
     * @param engram InitEngram object that holds all data needed for initialization
     */
    private void Insert(InitEngram engram) {
        ContentValues engramValues = new ContentValues();
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_NAME, engram.getName());
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_DESCRIPTION, engram.getDescription());
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID, engram.getImageId());

        engram.setId(database.insert(DBOpenHelper.TABLE_ENGRAM, null, engramValues));

        Helper.Log(LOGTAG, "-> Engram (" + engram.getName() + ") inserted at row " + engram.getId());

        for (int i = 0; i < engram.getCompositionIDs().size(); i++) {
            Resource resource = findSingleResource(engram.getCompositionIDs().keyAt(i));

            ContentValues compositionValues = new ContentValues();
            compositionValues.put(DBOpenHelper.COLUMN_COMPOSITION_QUANTITY, engram.getCompositionIDs().valueAt(i));
            compositionValues.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engram.getId());
            compositionValues.put(DBOpenHelper.COLUMN_TRACK_RESOURCE, resource.getId());

            long compositionId = database.insert(DBOpenHelper.TABLE_COMPOSITION, null, compositionValues);

            Helper.Log(LOGTAG, "--> Resource (" + resource.getName() + "/" + resource.getId() +
                    " x" + engram.getCompositionIDs().valueAt(i) +
                    ") inserted into " + engram.getName() +
                    " at row " + compositionId);
        }

        for (int i = 0; i < engram.getCategory().size(); i++) {
            String categoryName = engram.getCategory().valueAt(i);

            ContentValues categoryValues = new ContentValues();
            categoryValues.put(DBOpenHelper.COLUMN_CATEGORY_NAME, categoryName);
            categoryValues.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engram.getId());

            long categoryId = database.insert(DBOpenHelper.TABLE_CATEGORY, null, categoryValues);

            Helper.Log(LOGTAG, "--> Category (" + categoryName +
                    ") inserted into " + engram.getName() +
                    " at row " + categoryId);
        }
    }

    /**
     * Returns a Resource object, found by its imageId
     *
     * @param imageId int used to find requested Resource object
     * @return Resource
     */
    private Resource findSingleResource(int imageId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_RESOURCE +
                        " WHERE " + DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID +
                        " = " + imageId,
                null, null
        );

        return cursorToSingleResource(cursor);
    }

    public Resource cursorToSingleResource(Cursor cursor) {
        Resource resource = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_NAME));
            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID));

            resource = new Resource(id, name, imageId);
        }

        return resource;
    }

    public void Open() {
        database = openHelper.getWritableDatabase();
        Helper.Log(LOGTAG, "Database open");
    }

    public void Close() {
        Helper.Log(LOGTAG, "Database closed");
        database.close();
    }
}