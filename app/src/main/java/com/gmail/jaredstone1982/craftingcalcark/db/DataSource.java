package com.gmail.jaredstone1982.craftingcalcark.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.model.Category;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableResource;
import com.gmail.jaredstone1982.craftingcalcark.model.DetailEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.InitEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.Queue;
import com.gmail.jaredstone1982.craftingcalcark.model.Resource;
import com.gmail.jaredstone1982.craftingcalcark.model.initializers.EngramInitializer;
import com.gmail.jaredstone1982.craftingcalcark.model.initializers.ResourceInitializer;

import java.util.HashMap;
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

    // -- Query Methods --

    public SparseArray<CraftableEngram> findAllCraftableEngrams() {
        Cursor cursor = database.rawQuery(
                "SELECT " + DBOpenHelper.TABLE_ENGRAM + ".*, " + DBOpenHelper.TABLE_QUEUE + ".*" +
                        " FROM " + DBOpenHelper.TABLE_QUEUE +
                        " INNER JOIN " + DBOpenHelper.TABLE_ENGRAM +
                        " ON " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + DBOpenHelper.TABLE_ENGRAM + "." + DBOpenHelper.COLUMN_ENGRAM_ID,
                null, null
        );

        Helper.Log(LOGTAG, "-- findAllCraftableEngrams() > Returned " + cursor.getCount() + " rows from tables: " + DBOpenHelper.TABLE_ENGRAM + ", " + DBOpenHelper.TABLE_QUEUE);

        return cursorToCraftableEngrams(cursor);
    }

    public SparseArray<CraftableResource> findAllCraftableResources() {
        Cursor cursor = database.rawQuery(
                "SELECT " + DBOpenHelper.TABLE_COMPOSITION + ".*, " + DBOpenHelper.TABLE_QUEUE + ".* " +
                        " FROM " + DBOpenHelper.TABLE_COMPOSITION +
                        " INNER JOIN " + DBOpenHelper.TABLE_QUEUE +
                        " ON " + DBOpenHelper.TABLE_COMPOSITION + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM,
                null, null
        );

        return cursorToResources(cursor);
    }

    /**
     * Pulls entire Engram table, alongside Category table, to fill ListView with proper objects
     *
     * @return Array of Engram objects suitable for a displayable, sortable view
     */
    public SparseArray<DisplayEngram> findAllDisplayEngrams() {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM,
                null, null
        );

        return cursorToDisplayEngrams(cursor);
    }

    public HashMap<Long, Queue> findAllQueues() {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE,
                null, null
        );

        Helper.Log(LOGTAG, "-- findAllQueues() > Returned " + cursor.getCount() + " rows from table: " + DBOpenHelper.TABLE_QUEUE);

        return cursorToQueues(cursor);
    }

    public SparseArray<Category> findEngramCategories(long engramId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_CATEGORY +
                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM + " = " + engramId,
                null, null
        );

        return cursorToCategories(cursor);
    }

    public SparseArray<CraftableResource> findEngramResources(long engramId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_COMPOSITION +
                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + engramId,
                null, null
        );

        return cursorToCraftableResources(cursor);
    }

    public DetailEngram findSingleDetailEngram(long engramId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM +
                        " WHERE " + DBOpenHelper.COLUMN_ENGRAM_ID +
                        " = " + engramId,
                null, null
        );

        Helper.Log(LOGTAG, "Returned " + cursor.getCount() + " rows from findSingleDetailEngram");

        return cursorToSingleDetailEngram(cursor);
    }

    public Queue findSingleQueue(Long engramId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE +
                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + engramId,
                null, null
        );

        return cursorToSingleQueue(cursor);
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

    /**
     * Returns a Resource object, found by its imageId
     *
     * @param resourceId long value used to find requested Resource object
     * @return Resource
     */
    private Resource findSingleResource(long resourceId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_RESOURCE +
                        " WHERE " + DBOpenHelper.COLUMN_RESOURCE_ID +
                        " = " + resourceId,
                null, null
        );

        return cursorToSingleResource(cursor);
    }

    // -- Cursor Methods --

    public DetailEngram cursorToSingleDetailEngram(Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
            String description = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_DESCRIPTION));
            int quantity = findSingleQueue(id).getQuantity();
            SparseArray<CraftableResource> composition = findEngramResources(id);

            return new DetailEngram(id, name, imageId, description, quantity, composition);
        }

        return null;
    }

    public Queue cursorToSingleQueue(Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
            int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
            long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));

            return new Queue(id, engramId, quantity);
        }

        return null;
    }

    public Resource cursorToSingleResource(Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_NAME));
            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID));

            return new Resource(id, name, imageId);
        }

        return null;
    }

    /**
     * Instantiates an array of CraftableEngrams to include their relevant quantities
     *
     * @param cursor Holds cursor data pulled from Database
     * @return SparseArray of CraftableEngram objects
     */
    public SparseArray<CraftableEngram> cursorToCraftableEngrams(Cursor cursor) {
        SparseArray<CraftableEngram> engrams = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));

                CraftableEngram engram = new CraftableEngram(id, name, imageId, quantity);
                engrams.put(imageId, engram);

                Helper.Log(LOGTAG, " > Engram Details: " + engram.toString());
            }
        }
        return engrams;
    }

    private SparseArray<CraftableResource> cursorToCraftableResources(Cursor cursor) {
        SparseArray<CraftableResource> resources = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long compositionId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_QUANTITY));
                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
                long resourceId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_RESOURCE));

                CraftableResource resource = new CraftableResource(findSingleResource(resourceId), quantity);
                resource.setQuantity(quantity);

                resources.put(resource.getImageId(), resource);
            }
        }

        return resources;
    }

    /**
     * Parse data pulled from cursor
     *
     * @param cursor Holds data pulled from Database Query
     * @return Array of DisplayEngram objects
     */
    public SparseArray<DisplayEngram> cursorToDisplayEngrams(Cursor cursor) {
        SparseArray<DisplayEngram> engrams = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));

                DisplayEngram engram = new DisplayEngram(id, name, imageId);
                engram.setCategories(findEngramCategories(id));

                engrams.put(imageId, engram);
            }
        }

        return engrams;
    }

    public SparseArray<Category> cursorToCategories(Cursor cursor) {
        SparseArray<Category> categories = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_IMAGE_ID));

                Category category = new Category(id, name, imageId);

                categories.put(imageId, category);
            }
        }

        return categories;
    }

    private HashMap<Long, Queue> cursorToQueues(Cursor cursor) {
        HashMap<Long, Queue> queues = new HashMap<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));

                Queue queue = new Queue(id, engramId, quantity);

                Helper.Log(LOGTAG, " > Queue Details: " + queue.toString());

                queues.put(engramId, queue);
            }
        }
        return queues;
    }

    private SparseArray<CraftableResource> cursorToResources(Cursor cursor) {
        SparseArray<CraftableResource> resources = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long compositionId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_QUANTITY));
                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
                long resourceId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_RESOURCE));
                int quantityPer = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));

                CraftableResource resource = new CraftableResource(findSingleResource(resourceId));
                if (resources.indexOfKey(resource.getImageId()) > -1) {
                    resource = resources.get(resource.getImageId());
                    resource.increaseQuantity(quantity * quantityPer);
                } else {
                    resource.setQuantity(quantity * quantityPer);
                }

                resources.put(resource.getImageId(), resource);
            }
        }

        return resources;
    }

    // -- Utility Methods --

    public boolean Delete(long engramId) {
        return database.delete(DBOpenHelper.TABLE_QUEUE, DBOpenHelper.COLUMN_TRACK_ENGRAM + "=" + engramId, null) > 0;
    }

    public void Insert(long engramId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_QUEUE_QUANTITY, quantity);
        values.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engramId);

        database.insert(DBOpenHelper.TABLE_QUEUE, null, values);
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

    public void Update(Queue queue) {
        Cursor cursor = database.rawQuery(
                "INSERT OR REPLACE INTO " + DBOpenHelper.TABLE_QUEUE +
                        " (" + DBOpenHelper.COLUMN_QUEUE_ID +
                        ", " + DBOpenHelper.COLUMN_QUEUE_QUANTITY +
                        ", " + DBOpenHelper.COLUMN_TRACK_ENGRAM + ")" +
                        " VALUES (" + queue.getId() + ", " + queue.getQuantity() + ", " + queue.getEngramId() + ")",
                null, null
        );

        if (cursor.getCount() > 0) {
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
            int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
            long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
        }

        cursor.close();
    }

    public void CreateTable() {
        database.execSQL(DBOpenHelper.TABLE_QUEUE_CREATE);

        Helper.Log(LOGTAG, "-- Created table: " + DBOpenHelper.TABLE_QUEUE);
    }

    public void DeleteTableData() {
        int rows = database.delete(DBOpenHelper.TABLE_QUEUE, "1", null);

        Helper.Log(LOGTAG, "-- Deleted " + rows + " rows from table: " + DBOpenHelper.TABLE_QUEUE);
    }

    public void DropTable() {
        DBOpenHelper.dropTable(database, DBOpenHelper.TABLE_QUEUE);

        Helper.Log(LOGTAG, "-- Dropped table: " + DBOpenHelper.TABLE_QUEUE);
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
     * Method that drops all tables, creates all tables, and then re-initializes data TODO: Strictly for DEBUG purposes
     */
    public void Reset() {
        DBOpenHelper.dropAllTables(database);
        DBOpenHelper.createAllTables(database);

        Initialize();
    }

    public void Open() {
        database = openHelper.getWritableDatabase();

        Helper.Log(LOGTAG, "Database open");
    }

    public void Close() {
        database.close();

        Helper.Log(LOGTAG, "Database closed");
    }
}