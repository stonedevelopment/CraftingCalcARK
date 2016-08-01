package ark.resource.calculator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ark.resource.calculator.helpers.Helper;
import ark.resource.calculator.helpers.PreferenceHelper;
import ark.resource.calculator.model.Category;
import ark.resource.calculator.model.CraftableEngram;
import ark.resource.calculator.model.CraftableResource;
import ark.resource.calculator.model.DetailEngram;
import ark.resource.calculator.model.DisplayEngram;
import ark.resource.calculator.model.InitEngram;
import ark.resource.calculator.model.Queue;
import ark.resource.calculator.model.Resource;
import ark.resource.calculator.model.initializers.CategoryInitializer;
import ark.resource.calculator.model.initializers.EngramInitializer;
import ark.resource.calculator.model.initializers.ResourceInitializer;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: ARK:Resource Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class DataSource {
    private static final String LOGTAG = "DATASOURCE";

    private static DataSource sInstance;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private Context context;

    public static synchronized DataSource getInstance(Context context, String LOGTAG) {
        if (sInstance == null) {
            sInstance = new DataSource(context.getApplicationContext(), LOGTAG);
        }
        return sInstance;
    }

    private DataSource(Context context, String LOGTAG) {
        this.openHelper = DBOpenHelper.getInstance(context);
        this.context = context;

        List<String> tables = TestTablesForContent();
        if (tables.size() > 0) {
            InitializeTablesWithContent(tables);
        }

        OpenDatabase();
    }

    /**
     * -- PUBLIC DATABASE QUERY METHODS --
     */

    public SparseArray<CraftableEngram> findAllCraftableEngrams() {
        Cursor cursor = database.rawQuery(
                "SELECT *" +
                        " FROM " + DBOpenHelper.TABLE_QUEUE +
                        " INNER JOIN " + DBOpenHelper.TABLE_ENGRAM +
                        " ON " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + DBOpenHelper.TABLE_ENGRAM + "." + DBOpenHelper.COLUMN_ENGRAM_ID +
                        " ORDER BY " + DBOpenHelper.COLUMN_ENGRAM_NAME,
                null, null
        );

        Helper.Log(LOGTAG, "-- findAllCraftableEngrams() > Returned " + cursor.getCount() + " rows from tables: " + DBOpenHelper.TABLE_ENGRAM + ", " + DBOpenHelper.TABLE_QUEUE);

        return cursorToCraftableEngrams(cursor);
    }

    public SparseArray<CraftableResource> findAllCraftableResources() {
        Cursor cursor = database.rawQuery(
                "SELECT *" +
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
                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM + " ORDER BY " + DBOpenHelper.COLUMN_ENGRAM_NAME,
                null, null
        );

        if (cursor.getCount() == 0) {
            // throw exception, initialize database
        }

        return cursorToDisplayEngrams(cursor);
    }

    public SparseArray<DisplayEngram> findAllDisplayEngrams(long categoryId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM +
                        " WHERE " + DBOpenHelper.COLUMN_ENGRAM_CATEGORY_ID + " = " +
                        categoryId + " ORDER BY " + DBOpenHelper.COLUMN_ENGRAM_NAME,
                null, null
        );

        return cursorToDisplayEngrams(cursor);
    }

    public SparseArray<Category> findAllCategories() {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_CATEGORY,
                null, null
        );

        return cursorToCategories(cursor);
    }

    public HashMap<Long, Queue> findAllQueues() {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE,
                null, null
        );

        Helper.Log(LOGTAG, "-- findAllQueues() > Returned " + cursor.getCount() + " rows from table: " + DBOpenHelper.TABLE_QUEUE);

        return cursorToQueues(cursor);
    }

    public SparseArray<Category> findFilteredCategories(long categoryId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_CATEGORY +
                        " WHERE " + DBOpenHelper.COLUMN_CATEGORY_PARENT + " = " +
                        categoryId + " ORDER BY " + DBOpenHelper.COLUMN_CATEGORY_NAME,
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

    public Category findSingleCategory(long categoryId) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_CATEGORY +
                        " WHERE " + DBOpenHelper.COLUMN_CATEGORY_ID + " = " + categoryId,
                null, null
        );

        return cursorToSingleCategory(cursor);
    }

    /**
     * -- PARSE CURSOR TO OBJECT METHODS --
     */

    public DetailEngram cursorToSingleDetailEngram(Cursor cursor) {
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
            String description = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_DESCRIPTION));
            long categoryId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_CATEGORY_ID));
            int quantity;

            Queue queue = findSingleQueue(id);
            if (queue != null) {
                quantity = queue.getQuantity();
            } else {
                quantity = 0;
            }

            SparseArray<CraftableResource> composition = findEngramResources(id);

            cursor.close();
            return new DetailEngram(id, name, imageId, description, categoryId, quantity, composition);
        } else {
            Helper.Log(LOGTAG, "!!- cursorToSingleDetailEngram returns false. -!!");

            cursor.close();
            return null;
        }
    }

    public Queue cursorToSingleQueue(Cursor cursor) {
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
            int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
            long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));

            cursor.close();
            return new Queue(id, engramId, quantity);
        } else {
            cursor.close();
            return null;
        }
    }

    public Resource cursorToSingleResource(Cursor cursor) {
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_NAME));
            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID));

            cursor.close();
            return new Resource(id, name, imageId);
        } else {
            Helper.Log(LOGTAG, "!!- cursorToSingleResource returns false. -!!");

            cursor.close();
            return null;
        }
    }

    private Category cursorToSingleCategory(Cursor cursor) {
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_NAME));
            int level = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_LEVEL));
            long parent = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_PARENT));

            cursor.close();
            return new Category(level, id, name, parent);
        } else {
            Helper.Log(LOGTAG, "!!- cursorToSingleCategory returns false. -!!");

            cursor.close();
            return null;
        }
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
                engrams.put(engrams.size(), engram);

                Helper.Log(LOGTAG, " > Engram Details: " + engram.toString());
            }
        } else {
            Helper.Log(LOGTAG, "!!- cursorToCraftableEngrams returns false. -!!");
        }

        cursor.close();
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
        } else {
            Helper.Log(LOGTAG, "!!- cursorToCraftableResources returns false. -!!");
        }

        cursor.close();
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
                long categoryId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_CATEGORY_ID));

                DisplayEngram engram = new DisplayEngram(id, name, imageId, categoryId);

                engrams.put(engrams.size(), engram);
            }
        } else {
            Helper.Log(LOGTAG, "!!- cursorToDisplayEngrams returns false. -!!");
        }

        cursor.close();
        return engrams;
    }

    public SparseArray<Category> cursorToCategories(Cursor cursor) {
        SparseArray<Category> categories = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_NAME));
                int level = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_LEVEL));
                long parent = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CATEGORY_PARENT));

                Category category = new Category(level, id, name, parent);

                if (level > 0) {
                    categories.put(categories.size() + 1, category);
                } else {
                    categories.put(categories.size(), category);
                }
            }
        } else {
            Helper.Log(LOGTAG, "!!- cursorToCategories returns false. -!!");
        }

        cursor.close();
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
                queues.put(engramId, queue);
            }
        } else {
            Helper.Log(LOGTAG, "!!- cursorToQueues returns false. -!!");
        }

        cursor.close();
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
        } else {
            Helper.Log(LOGTAG, "!!- cursorToResources returns false. -!!");
        }

        cursor.close();
        return resources;
    }

    /**
     * -- PRIVATE UTILITY METHODS --
     */

    public Context getContext() {
        return this.context;
    }

    private String getVersion(String table) {
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(context);

        switch (table) {
            case DBOpenHelper.TABLE_RESOURCE:
                return preferenceHelper.getStringPreference(Helper.RESOURCE_VERSION);
            case DBOpenHelper.TABLE_CATEGORY:
                return preferenceHelper.getStringPreference(Helper.CATEGORY_VERSION);
            case DBOpenHelper.TABLE_ENGRAM:
                return preferenceHelper.getStringPreference(Helper.ENGRAM_VERSION);
            default:
                return null;
        }
    }

    public List<String> TestTablesForContent() {
        Helper.Log(LOGTAG, "** Testing tables for content..");

        List<String> tables = new ArrayList<>();

        OpenDatabase();

        int tableResourceCount = getCount(DBOpenHelper.TABLE_RESOURCE);
        String tableResourceVersion = getVersion(DBOpenHelper.TABLE_RESOURCE);
        int tableCategoryCount = getCount(DBOpenHelper.TABLE_CATEGORY);
        String tableCategoryVersion = getVersion(DBOpenHelper.TABLE_CATEGORY);
        int tableEngramCount = getCount(DBOpenHelper.TABLE_ENGRAM);
        String tableEngramVersion = getVersion(DBOpenHelper.TABLE_ENGRAM);

        Helper.Log(LOGTAG, "-> Testing Resource table..");
        if ((tableResourceCount == 0) || (tableResourceCount != ResourceInitializer.getCount()) || (!Objects.equals(tableResourceVersion, ResourceInitializer.VERSION))) {
            tables.add(DBOpenHelper.TABLE_RESOURCE);
            Helper.Log(LOGTAG, "--> Resource table needs upgrade. " +
                    "[tableCount:" + tableResourceCount + "/initializerCount:" + ResourceInitializer.getCount() + "] " +
                    "[tableVersion:" + tableResourceVersion + "/initializerVersion:" + ResourceInitializer.VERSION + "]");
        }

        Helper.Log(LOGTAG, "-> Testing Category table..");
        if ((tableCategoryCount == 0) || (tableCategoryCount != CategoryInitializer.getCount()) || (!Objects.equals(tableCategoryVersion, CategoryInitializer.VERSION))) {
            tables.add(DBOpenHelper.TABLE_CATEGORY);
            Helper.Log(LOGTAG, "--> Category table needs upgrade. " +
                    "[tableCount:" + tableCategoryCount + "/initializerCount:" + CategoryInitializer.getCount() + "] " +
                    "[tableVersion:" + tableCategoryVersion + "/initializerVersion:" + CategoryInitializer.VERSION + "]");
        }

        Helper.Log(LOGTAG, "-> Testing Engram table..");
        if ((tableEngramCount == 0) || (tableEngramCount != EngramInitializer.getCount()) || (!Objects.equals(tableEngramVersion, EngramInitializer.VERSION))) {
            tables.add(DBOpenHelper.TABLE_ENGRAM);
            Helper.Log(LOGTAG, "--> Engram table needs upgrade. " +
                    "[tableCount:" + tableEngramCount + "/initializerCount:" + EngramInitializer.getCount() + "] " +
                    "[tableVersion:" + tableEngramVersion + "/initializerVersion:" + EngramInitializer.VERSION + "]");
        }

        CloseDatabase();

        Helper.Log(LOGTAG, "** Testing complete.");

        return tables;
    }

    public void InitializeTablesWithContent(List<String> tables) {
        Helper.Log(LOGTAG, "** Initializing tables with content..");

        boolean wasInitialized = false;

        for (String table : tables) {
            switch (table) {
                case DBOpenHelper.TABLE_RESOURCE:
                    InitializeResources();
                    wasInitialized = true;
                    break;
                case DBOpenHelper.TABLE_CATEGORY:
                    InitializeCategories();
                    wasInitialized = true;
                    break;
                case DBOpenHelper.TABLE_ENGRAM:
                    InitializeEngrams();
                    wasInitialized = true;
                    break;
            }
        }

        if (wasInitialized) {
            OpenDatabase();

            ClearQueue();

            CloseDatabase();
        }

        Helper.Log(LOGTAG, "** Initializing complete.");
    }

    public void ClearQueue() {
        Helper.Log(LOGTAG, "-> Clearing Crafting Queue..");

        String table = DBOpenHelper.TABLE_QUEUE;

        // FIXME
        DeleteTableData(table);
        DropTable(table);
        CreateTable(table);

        Helper.Log(LOGTAG, "-> Crafting Queue cleared.");
    }

    private int getCount(String table) {
        Cursor cursor = database.rawQuery(
                "SELECT count(*) FROM " + table,
                null, null
        );

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();
        return count;
    }

    /**
     * -- QUEUE ADDING/REMOVING METHODS --
     */

    public boolean Delete(Queue queue) {
        return database.delete(DBOpenHelper.TABLE_QUEUE, DBOpenHelper.COLUMN_TRACK_ENGRAM + "=" + queue.getEngramId(), null) > 0;
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

    private void Insert(Category category) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_CATEGORY_ID, category.getId());
        values.put(DBOpenHelper.COLUMN_CATEGORY_LEVEL, category.getLevel());
        values.put(DBOpenHelper.COLUMN_CATEGORY_NAME, category.getName());
        values.put(DBOpenHelper.COLUMN_CATEGORY_PARENT, category.getParent());

        database.insert(DBOpenHelper.TABLE_CATEGORY, null, values);

        Helper.Log(LOGTAG, "-> Category (" + category.getName() + ") inserted at row " + category.getId());
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
        engramValues.put(DBOpenHelper.COLUMN_ENGRAM_CATEGORY_ID, engram.getCategoryId());

        engram.setId(database.insert(DBOpenHelper.TABLE_ENGRAM, null, engramValues));

        Helper.Log(LOGTAG, "-> Engram (" + engram.getName() + ") inserted at row " + engram.getId());

        for (int i = 0, size = engram.getCompositionIDs().size(); i < size; i++) {
            Resource resource = findSingleResource(engram.getCompositionIDs().keyAt(i));

            if (resource != null) {
                ContentValues compositionValues = new ContentValues();
                compositionValues.put(DBOpenHelper.COLUMN_COMPOSITION_QUANTITY, engram.getCompositionIDs().valueAt(i));
                compositionValues.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engram.getId());
                compositionValues.put(DBOpenHelper.COLUMN_TRACK_RESOURCE, resource.getId());

                long compositionId = database.insert(DBOpenHelper.TABLE_COMPOSITION, null, compositionValues);

                Helper.Log(LOGTAG, "--> Composition Resource (" + resource.getName() + "/" + resource.getId() +
                        " x" + engram.getCompositionIDs().valueAt(i) +
                        ") inserted into " + engram.getName() +
                        " at row " + compositionId);
            } else {
                Helper.Log(LOGTAG, "--> Composition Resource is null and was not inserted.");
            }
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

        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
            int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
            long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));

            Helper.Log(LOGTAG, "-> Queue inserted/replaced successfully. [" + queue.toString() + "]");
        } else {
            Helper.Log(LOGTAG, "!!- Queue updated failed. [" + queue.toString() + "] -!!");
        }

        cursor.close();
    }

    /**
     * -- DATABASE SYSTEM METHODS --
     */

    private void DeleteTableData(String table) {
        int rows = database.delete(table, "1", null);

        Helper.Log(LOGTAG, "-- Deleted " + rows + " rows from table: " + table);
    }

    private void CreateTable(String table) {
        DBOpenHelper.createTable(database, table);
    }

    private void DropTable(String table) {
        DBOpenHelper.dropTable(database, table);
    }

    private void ResetTable(String table) {
        OpenDatabase();

        DeleteTableData(table);
        DropTable(table);
        CreateTable(table);

        CloseDatabase();
    }

    private void InitializeCategories() {
        Helper.Log(LOGTAG, "** Initializing Categories..");

        ResetTable(DBOpenHelper.TABLE_CATEGORY);

        List<Category> categories = CategoryInitializer.getCategories();
        for (Category category : categories) {
            OpenDatabase();

            Insert(category);

            CloseDatabase();
        }

        // Save version persistently, future debugging helper
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(context);
        preferenceHelper.setPreference(Helper.CATEGORY_VERSION, CategoryInitializer.VERSION);

        Helper.Log(LOGTAG, "** Category initialization completed.");
    }

    private void InitializeResources() {
        Helper.Log(LOGTAG, "** Initializing Resources..");

        ResetTable(DBOpenHelper.TABLE_RESOURCE);

        SparseArray<String> resources = ResourceInitializer.getResources();
        for (int i = 0; i < resources.size(); i++) {
            int imageId = resources.keyAt(i);
            String name = resources.valueAt(i);

            OpenDatabase();

            Insert(imageId, name);

            CloseDatabase();
        }

        // Save version persistently, future debugging helper
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(context);
        preferenceHelper.setPreference(Helper.RESOURCE_VERSION, ResourceInitializer.VERSION);

        Helper.Log(LOGTAG, "** Resource initialization completed.");
    }

    private void InitializeEngrams() {
        Helper.Log(LOGTAG, "** Initializing Engrams..");

        ResetTable(DBOpenHelper.TABLE_ENGRAM);

        List<InitEngram> engrams = EngramInitializer.getEngrams();
        for (InitEngram engram : engrams) {
            OpenDatabase();

            Insert(engram);

            CloseDatabase();
        }

        // Save version persistently, future debugging helper
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(context);
        preferenceHelper.setPreference(Helper.ENGRAM_VERSION, EngramInitializer.VERSION);

        Helper.Log(LOGTAG, "** Engram initialization completed.");
    }

    private void OpenDatabase() {
        database = openHelper.getWritableDatabase();

        Helper.Log(LOGTAG, "-- Database has opened --");
    }

    private void CloseDatabase() {
        database.close();

        Helper.Log(LOGTAG, "-- Database has closed --");
    }

    public void ResetConnection() {
        CloseDatabase();
        OpenDatabase();

        Helper.Log(LOGTAG, "-- Database connection reset --");
    }
}