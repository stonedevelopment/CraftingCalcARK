package arc.resource.calculator.db;

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
public class DataSource {
//    private static final String LOGTAG = "DATASOURCE";
//
//    private static DataSource sInstance;
//
//    private SQLiteOpenHelper openHelper;
//    private SQLiteDatabase database;
//    private Context context;
//
//    public static synchronized DataSource getInstance(Context context, String LOGTAG) {
//        if (sInstance == null) {
//            sInstance = new DataSource(context.getApplicationContext(), LOGTAG);
//        }
//        return sInstance;
//    }
//
//    private DataSource(Context context, String LOGTAG) {
//        this.openHelper = DatabaseHelper.getInstance(context);
//        this.context = context;
//
//        List<String> tables = TestTablesForContent();
//        if (tables.size() > 0) {
//            InitializeTablesWithContent(tables);
//        }
//
//        OpenDatabase();
//    }
//
//    // -- PUBLIC DATABASE QUERY METHODS --
//
//    public SparseArray<QueueEngram> findAllCraftableEngrams() {
//        Cursor cursor = database.rawQuery(
//                "SELECT *" +
//                        " FROM " + DatabaseHelper.TABLE_QUEUE +
//                        " INNER JOIN " + DatabaseHelper.TABLE_ENGRAM +
//                        " ON " + DatabaseHelper.TABLE_QUEUE + "." + DatabaseHelper.COLUMN_TRACK_ENGRAM +
//                        " = " + DatabaseHelper.TABLE_ENGRAM + "." + DatabaseHelper.COLUMN_ENGRAM_ID +
//                        " ORDER BY " + DatabaseHelper.COLUMN_ENGRAM_NAME,
//                null, null
//        );
//
//        Helper.Log(LOGTAG, "-- findAllCraftableEngrams() > Returned " + cursor.getCount() + " rows from tables: " + DatabaseHelper.TABLE_ENGRAM + ", " + DatabaseHelper.TABLE_QUEUE);
//
//        return cursorToCraftableEngrams(cursor);
//    }
//
//    public SparseArray<QueueResource> findAllCraftableResourcesFromQueue() {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_COMPOSITION +
//                        " INNER JOIN " + DatabaseHelper.TABLE_QUEUE +
//                        " ON " + DatabaseHelper.TABLE_COMPOSITION + "." + DatabaseHelper.COLUMN_TRACK_ENGRAM +
//                        " = " + DatabaseHelper.TABLE_QUEUE + "." + DatabaseHelper.COLUMN_TRACK_ENGRAM,
//                null, null
//        );
//
//        return cursorToResources(cursor);
//    }
//
//    public SparseArray<DisplayEngram> findAllDisplayEngrams() {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_ENGRAM + " ORDER BY " + DatabaseHelper.COLUMN_ENGRAM_NAME,
//                null, null
//        );
//
//        if (cursor.getCount() == 0) {
//            // throw exception, initialize database
//        }
//
//        return cursorToDisplayEngrams(cursor);
//    }
//
//    public SparseArray<DisplayEngram> findAllDisplayEngrams(long categoryId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_ENGRAM +
//                        " WHERE " + DatabaseHelper.COLUMN_ENGRAM_CATEGORY_ID + " = " +
//                        categoryId + " ORDER BY " + DatabaseHelper.COLUMN_ENGRAM_NAME,
//                null, null
//        );
//
//        return cursorToDisplayEngrams(cursor);
//    }
//
//    public SparseArray<Category> findAllCategories() {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY,
//                null, null
//        );
//
//        return cursorToCategories(cursor);
//    }
//
//    public HashMap<Long, Queue> findAllQueues() {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_QUEUE,
//                null, null
//        );
//
//        Helper.Log(LOGTAG, "-- findAllQueues() > Returned " + cursor.getCount() + " rows from table: " + DatabaseHelper.TABLE_QUEUE);
//
//        return cursorToQueues(cursor);
//    }
//
//    public SparseArray<Category> findFilteredCategories(long categoryId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY +
//                        " WHERE " + DatabaseHelper.COLUMN_CATEGORY_PARENT + " = " +
//                        categoryId + " ORDER BY " + DatabaseHelper.COLUMN_CATEGORY_NAME,
//                null, null
//        );
//
//        return cursorToCategories(cursor);
//    }
//
//    public SparseArray<QueueResource> findEngramResources(long engramId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_COMPOSITION +
//                        " WHERE " + DatabaseHelper.COLUMN_TRACK_ENGRAM +
//                        " = " + engramId,
//                null, null
//        );
//
//        return cursorToCraftableResources(cursor);
//    }
//
//    public Engram findSingleEngramByImageId(int imageId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_ENGRAM +
//                        " WHERE " + DatabaseHelper.COLUMN_ENGRAM_IMAGE_ID +
//                        " = " + imageId,
//                null, null
//        );
//
//        return cursorToSingleEngram(cursor);
//    }
//
//    public DetailEngram findSingleDetailEngram(long engramId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_ENGRAM +
//                        " WHERE " + DatabaseHelper.COLUMN_ENGRAM_ID +
//                        " = " + engramId,
//                null, null
//        );
//
//        return cursorToSingleDetailEngram(cursor);
//    }
//
//    public Queue findSingleQueue(Long engramId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_QUEUE +
//                        " WHERE " + DatabaseHelper.COLUMN_TRACK_ENGRAM +
//                        " = " + engramId,
//                null, null
//        );
//
//        return cursorToSingleQueue(cursor);
//    }
//
//    private Resource findSingleResource(long resourceId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_RESOURCE +
//                        " WHERE " + DatabaseHelper.COLUMN_RESOURCE_ID +
//                        " = " + resourceId,
//                null, null
//        );
//
//        return cursorToSingleResource(cursor);
//    }
//
//    private Resource findSingleResourceByImageId(int imageId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_RESOURCE +
//                        " WHERE " + DatabaseHelper.COLUMN_RESOURCE_IMAGE_ID +
//                        " = " + imageId,
//                null, null
//        );
//
//        return cursorToSingleResource(cursor);
//    }
//
//    public Category findSingleCategory(long categoryId) {
//        Cursor cursor = database.rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY +
//                        " WHERE " + DatabaseHelper.COLUMN_CATEGORY_ID + " = " + categoryId,
//                null, null
//        );
//
//        return cursorToSingleCategory(cursor);
//    }
//
//    // -- CURSOR TO OBJECT METHODS --
//
//    public Engram cursorToSingleEngram(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_ID));
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_NAME));
//            int imageId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_IMAGE_ID));
//
//            cursor.close();
//            return new Engram(id, name, imageId);
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToSingleEngram returns false. -!!");
//
//            cursor.close();
//            return null;
//        }
//    }
//
//    public DetailEngram cursorToSingleDetailEngram(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_ID));
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_NAME));
//            int imageId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_IMAGE_ID));
//            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_DESCRIPTION));
//            long categoryId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_CATEGORY_ID));
//            int quantity;
//
//            Queue queue = findSingleQueue(id);
//            if (queue != null) {
//                quantity = queue.getQuantity();
//            } else {
//                quantity = 0;
//            }
//
//            SparseArray<QueueResource> composition = findEngramResources(id);
//
//            cursor.close();
//            return new DetailEngram(id, name, imageId, description, categoryId, quantity, composition);
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToSingleDetailEngram returns false. -!!");
//
//            cursor.close();
//            return null;
//        }
//    }
//
//    public Queue cursorToSingleQueue(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_ID));
//            int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_QUANTITY));
//            long engramId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_ENGRAM));
//
//            cursor.close();
//            return new Queue(id, engramId, quantity);
//        } else {
//            cursor.close();
//            return null;
//        }
//    }
//
//    public Resource cursorToSingleResource(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESOURCE_ID));
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESOURCE_NAME));
//            int imageId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESOURCE_IMAGE_ID));
//
//            cursor.close();
//            return new Resource(id, name, imageId);
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToSingleResource returns false. -!!");
//
//            cursor.close();
//            return null;
//        }
//    }
//
//    private Category cursorToSingleCategory(Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
//            int level = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_LEVEL));
//            long parent = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_PARENT));
//
//            cursor.close();
//            return new Category(level, id, name, parent);
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToSingleCategory returns false. -!!");
//
//            cursor.close();
//            return null;
//        }
//    }
//
//    public SparseArray<QueueEngram> cursorToCraftableEngrams(Cursor cursor) {
//        SparseArray<QueueEngram> engrams = new SparseArray<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_ID));
//                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_NAME));
//                int imageId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_IMAGE_ID));
//                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_QUANTITY));
//
//                QueueEngram engram = new QueueEngram(id, name, imageId, quantity);
//                engrams.put(engrams.size(), engram);
//
//                Helper.Log(LOGTAG, " > Engram Details: " + engram.toString());
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToCraftableEngrams returns false. -!!");
//        }
//
//        cursor.close();
//        return engrams;
//    }
//
//    private SparseArray<QueueResource> cursorToCraftableResources(Cursor cursor) {
//        SparseArray<QueueResource> resources = new SparseArray<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long compositionId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPOSITION_ID));
//                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPOSITION_QUANTITY));
//                long engramId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_ENGRAM));
//                long resourceId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_RESOURCE));
//
//                QueueResource resource = new QueueResource(findSingleResource(resourceId), quantity);
//
//                resources.put(resource.getDrawable(), resource);
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToCraftableResources returns false. -!!");
//        }
//
//        cursor.close();
//        return resources;
//    }
//
//    public SparseArray<DisplayEngram> cursorToDisplayEngrams(Cursor cursor) {
//        SparseArray<DisplayEngram> engrams = new SparseArray<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_ID));
//                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_NAME));
//                int imageId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_IMAGE_ID));
//                long categoryId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENGRAM_CATEGORY_ID));
//
//                DisplayEngram engram = new DisplayEngram(id, name, imageId, categoryId);
//
//                engrams.put(engrams.size(), engram);
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToDisplayEngrams returns false. -!!");
//        }
//
//        cursor.close();
//        return engrams;
//    }
//
//    public SparseArray<Category> cursorToCategories(Cursor cursor) {
//        SparseArray<Category> categories = new SparseArray<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
//                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
//                int level = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_LEVEL));
//                long parent = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_PARENT));
//
//                Category category = new Category(level, id, name, parent);
//
//                if (level > 0) {
//                    categories.put(categories.size() + 1, category);
//                } else {
//                    categories.put(categories.size(), category);
//                }
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToCategories returns false. -!!");
//        }
//
//        cursor.close();
//        return categories;
//    }
//
//    private HashMap<Long, Queue> cursorToQueues(Cursor cursor) {
//        HashMap<Long, Queue> queues = new HashMap<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_ID));
//                long engramId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_ENGRAM));
//                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_QUANTITY));
//
//                Queue queue = new Queue(id, engramId, quantity);
//                queues.put(engramId, queue);
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToQueues returns false. -!!");
//        }
//
//        cursor.close();
//        return queues;
//    }
//
//    private SparseArray<QueueResource> cursorToResources(Cursor cursor) {
//        SparseArray<QueueResource> resources = new SparseArray<>();
//        HashMap<Long, QueueResource> resourceMap = new HashMap<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long compositionId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPOSITION_ID));
//                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPOSITION_QUANTITY));
//                long engramId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_ENGRAM));
//                long resourceId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_RESOURCE));
//                int quantityPer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_QUANTITY));
//
//                QueueResource resource = resourceMap.get(resourceId);
//
//                // If Resource does not exist in list, create new one, otherwise increase its quantity.
//                if (resource == null) {
//                    resource = new QueueResource(findSingleResource(resourceId), quantity * quantityPer);
//                } else {
//                    resource.increaseQuantity(quantity * quantityPer);
//                }
//
//                resourceMap.put(resourceId, resource);
//                resources.put(resource.getDrawable(), resource);
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToResources returns false. -!!");
//        }
//
//        cursor.close();
//        return resources;
//    }
//
//    private SparseArray<QueueResource> cursorToComplexResources(Cursor cursor) {
//        SparseArray<QueueResource> resources = new SparseArray<>();
//        HashMap<Long, QueueResource> resourceMap = new HashMap<>();
//
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                long compositionId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPOSITION_ID));
//                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPOSITION_QUANTITY));
//                long engramId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_ENGRAM));
//                long resourceId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_RESOURCE));
//                int quantityPer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_QUANTITY));
//
//                QueueResource resource = resourceMap.get(resourceId);
//
//                // If Resource does not exist in list, create new one, otherwise increase its quantity.
//                if (resource == null) {
//                    resource = new QueueResource(findSingleResource(resourceId), quantity * quantityPer);
//                } else {
//                    resource.increaseQuantity(quantity * quantityPer);
//                }
//
//                resourceMap.put(resourceId, resource);
//                resources.put(resource.getDrawable(), resource);
//            }
//        } else {
//            Helper.Log(LOGTAG, "!!- cursorToResources returns false. -!!");
//        }
//
//        cursor.close();
//        return resources;
//    }
//
//    // -- PRIVATE UTILITY METHODS --
//
//    public Context getContext() {
//        return this.context;
//    }
//
//    private String getVersion(String table) {
//        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(context);
//
//        switch (table) {
//            case DatabaseHelper.TABLE_RESOURCE:
//                return preferenceHelper.getStringPreference(Helper.RESOURCE_VERSION);
//            case DatabaseHelper.TABLE_COMPLEX_RESOURCE:
//                return preferenceHelper.getStringPreference(Helper.COMPLEX_RESOURCE_VERSION);
//            case DatabaseHelper.TABLE_CATEGORY:
//                return preferenceHelper.getStringPreference(Helper.CATEGORY_VERSION);
//            case DatabaseHelper.TABLE_ENGRAM:
//                return preferenceHelper.getStringPreference(Helper.ENGRAM_VERSION);
//            default:
//                return null;
//        }
//    }
//
//    public List<String> TestTablesForContent() {
//        Helper.Log(LOGTAG, "** Testing tables for content..");
//
//        List<String> tables = new ArrayList<>();
//
//        OpenDatabase();
//
//        int tableResourceCount = getCount(DatabaseHelper.TABLE_RESOURCE);
//        String tableResourceVersion = getVersion(DatabaseHelper.TABLE_RESOURCE);
//
//        int tableComplexResourceCount = getCount(DatabaseHelper.TABLE_COMPLEX_RESOURCE);
//        String tableComplexResourceVersion = getVersion(DatabaseHelper.TABLE_COMPLEX_RESOURCE);
//
//        int tableCategoryCount = getCount(DatabaseHelper.TABLE_CATEGORY);
//        String tableCategoryVersion = getVersion(DatabaseHelper.TABLE_CATEGORY);
//
//        int tableEngramCount = getCount(DatabaseHelper.TABLE_ENGRAM);
//        String tableEngramVersion = getVersion(DatabaseHelper.TABLE_ENGRAM);
//
//        Helper.Log(LOGTAG, "-> Testing Resource table..");
//        if ((tableResourceCount == 0) || (tableResourceCount != ResourceInitializer.getCount()) || (!Objects.equals(tableResourceVersion, ResourceInitializer.VERSION))) {
//            tables.add(DatabaseHelper.TABLE_RESOURCE);
//            Helper.Log(LOGTAG, "--> Resource table needs upgrade. " +
//                    "[tableCount:" + tableResourceCount + "/initializerCount:" + ResourceInitializer.getCount() + "] " +
//                    "[tableVersion:" + tableResourceVersion + "/initializerVersion:" + ResourceInitializer.VERSION + "]");
//        }
//
//        Helper.Log(LOGTAG, "-> Testing Engram table..");
//        if ((tableEngramCount == 0) || (tableEngramCount != EngramInitializer.getCount()) || (!Objects.equals(tableEngramVersion, EngramInitializer.VERSION))) {
//            tables.add(DatabaseHelper.TABLE_ENGRAM);
//            Helper.Log(LOGTAG, "--> Engram table needs upgrade. " +
//                    "[tableCount:" + tableEngramCount + "/initializerCount:" + EngramInitializer.getCount() + "] " +
//                    "[tableVersion:" + tableEngramVersion + "/initializerVersion:" + EngramInitializer.VERSION + "]");
//        }
//
//        Helper.Log(LOGTAG, "-> Testing Category table..");
//        if ((tableCategoryCount == 0) || (tableCategoryCount != CategoryInitializer.getCount()) || (!Objects.equals(tableCategoryVersion, CategoryInitializer.VERSION))) {
//            tables.add(DatabaseHelper.TABLE_CATEGORY);
//            Helper.Log(LOGTAG, "--> Category table needs upgrade. " +
//                    "[tableCount:" + tableCategoryCount + "/initializerCount:" + CategoryInitializer.getCount() + "] " +
//                    "[tableVersion:" + tableCategoryVersion + "/initializerVersion:" + CategoryInitializer.VERSION + "]");
//        }
//
//        Helper.Log(LOGTAG, "-> Testing Complex Resource table..");
//        if ((tableComplexResourceCount == 0) || (tableComplexResourceCount != ComplexResourceInitializer.getCount()) || (!Objects.equals(tableComplexResourceVersion, ComplexResourceInitializer.VERSION))) {
//            tables.add(DatabaseHelper.TABLE_COMPLEX_RESOURCE);
//            Helper.Log(LOGTAG, "--> Complex Resource table needs upgrade. " +
//                    "[tableCount:" + tableComplexResourceCount + "/initializerCount:" + ComplexResourceInitializer.getCount() + "] " +
//                    "[tableVersion:" + tableComplexResourceVersion + "/initializerVersion:" + ComplexResourceInitializer.VERSION + "]");
//        }
//
//        CloseDatabase();
//
//        Helper.Log(LOGTAG, "** Testing complete.");
//
//        return tables;
//    }
//
//    public void InitializeTablesWithContent(List<String> tables) {
//        Helper.Log(LOGTAG, "** Initializing tables with content..");
//
//        boolean wasInitialized = false;
//
//        for (String table : tables) {
//            switch (table) {
//                case DatabaseHelper.TABLE_RESOURCE:
//                    InitializeResources();
//                    wasInitialized = true;
//                    break;
//                case DatabaseHelper.TABLE_COMPLEX_RESOURCE:
//                    InitializeComplexResources();
//                    wasInitialized = true;
//                    break;
//                case DatabaseHelper.TABLE_CATEGORY:
//                    InitializeCategories();
//                    wasInitialized = true;
//                    break;
//                case DatabaseHelper.TABLE_ENGRAM:
//                    InitializeEngrams();
//                    wasInitialized = true;
//                    break;
//            }
//        }
//
//        if (wasInitialized) {
//            OpenDatabase();
//
//            ClearQueue();
//
//            CloseDatabase();
//        }
//
//        Helper.Log(LOGTAG, "** Initializing complete.");
//    }
//
//    public void ClearQueue() {
//        Helper.Log(LOGTAG, "-> Clearing Crafting Queue..");
//
//        String tableQueue = DatabaseHelper.TABLE_QUEUE;
//
//        DeleteTableData(tableQueue);
//        DropTable(tableQueue);
//        CreateTable(tableQueue);
//
//        Helper.Log(LOGTAG, "-> Crafting Queue cleared.");
//    }
//
//    private int getCount(String table) {
//        Cursor cursor = database.rawQuery(
//                "SELECT count(*) FROM " + table,
//                null, null
//        );
//
//        cursor.moveToFirst();
//
//        int count = cursor.getInt(0);
//
//        cursor.close();
//        return count;
//    }
//
//    // -- CONTROL METHODS --
//
//    public boolean DeleteFromQueue(Queue queue) {
//        return queue != null && database.delete(DatabaseHelper.TABLE_QUEUE, DatabaseHelper.COLUMN_TRACK_ENGRAM + "=" + queue.getEngramId(), null) > 0;
//    }
//
//    public void InsertToQueue(Queue queue) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_QUEUE_QUANTITY, queue.getQuantity());
//        values.put(DatabaseHelper.COLUMN_TRACK_ENGRAM, queue.getEngramId());
//
//        database.insert(DatabaseHelper.TABLE_QUEUE, null, values);
//    }
//
//    public void InsertToQueueWithEngramId(long engramId, int quantity) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_QUEUE_QUANTITY, quantity);
//        values.put(DatabaseHelper.COLUMN_TRACK_ENGRAM, engramId);
//
//        database.insert(DatabaseHelper.TABLE_QUEUE, null, values);
//    }
//
//    private void InsertResource(int imageId, String name) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_RESOURCE_IMAGE_ID, imageId);
//        values.put(DatabaseHelper.COLUMN_RESOURCE_NAME, name);
//
//        long id = database.insert(DatabaseHelper.TABLE_RESOURCE, null, values);
//
//        Helper.Log(LOGTAG, "-> Resource (" + name + ") inserted at row " + id);
//    }
//
//    private void InsertComplexResource(Resource resource, long engramId) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_TRACK_RESOURCE, resource.getId());
//        values.put(DatabaseHelper.COLUMN_TRACK_ENGRAM, engramId);
//
//        long id = database.insert(DatabaseHelper.TABLE_COMPLEX_RESOURCE, null, values);
//
//        Helper.Log(LOGTAG, "-> Complex Resource (" + resource.getName() + ") inserted at row " + id);
//    }
//
//    private void InsertCategory(Category category) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_CATEGORY_ID, category.getId());
//        values.put(DatabaseHelper.COLUMN_CATEGORY_LEVEL, category.getLevel());
//        values.put(DatabaseHelper.COLUMN_CATEGORY_NAME, category.getName());
//        values.put(DatabaseHelper.COLUMN_CATEGORY_PARENT, category.getParent());
//
//        database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
//
//        Helper.Log(LOGTAG, "-> Category (" + category.getName() + ") inserted at row " + category.getId());
//    }
//
//    private void InsertEngramByInitEngram(InitEngram engram) {
//        ContentValues engramValues = new ContentValues();
//        engramValues.put(DatabaseHelper.COLUMN_ENGRAM_NAME, engram.getName());
//        engramValues.put(DatabaseHelper.COLUMN_ENGRAM_DESCRIPTION, engram.getDescription());
//        engramValues.put(DatabaseHelper.COLUMN_ENGRAM_IMAGE_ID, engram.getDrawable());
//        engramValues.put(DatabaseHelper.COLUMN_ENGRAM_CATEGORY_ID, engram.getCategoryId());
//
//        engram.setId(database.insert(DatabaseHelper.TABLE_ENGRAM, null, engramValues));
//
//        Helper.Log(LOGTAG, "-> Engram (" + engram.getName() + ") inserted at row " + engram.getId());
//
//        InsertCompositionByInitEngram(engram);
//    }
//
//    private void InsertCompositionByInitEngram(InitEngram engram) {
//        for (int i = 0, size = engram.getCompositionIDs().size(); i < size; i++) {
//            int imageId = engram.getCompositionIDs().keyAt(i);
//            int quantity = engram.getCompositionIDs().valueAt(i);
//
//            Resource resource = findSingleResourceByImageId(imageId);
//
//            if (resource != null) {
//                ContentValues values = new ContentValues();
//                values.put(DatabaseHelper.COLUMN_COMPOSITION_QUANTITY, quantity);
//                values.put(DatabaseHelper.COLUMN_TRACK_ENGRAM, engram.getId());
//                values.put(DatabaseHelper.COLUMN_TRACK_RESOURCE, resource.getId());
//
//                long compositionId = database.insert(DatabaseHelper.TABLE_COMPOSITION, null, values);
//
//                Helper.Log(LOGTAG, "--> Composition Resource (" + resource.getName() + "/" + resource.getId() +
//                        " x" + engram.getCompositionIDs().valueAt(i) +
//                        ") inserted into " + engram.getName() +
//                        " at row " + compositionId);
//            } else {
//                Helper.Log(LOGTAG, "--> Composition Resource is null and was not inserted.");
//            }
//        }
//    }
//
//    public void UpdateQueue(Queue queue) {
//        Cursor cursor = database.rawQuery(
//                "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_QUEUE +
//                        " (" + DatabaseHelper.COLUMN_QUEUE_ID +
//                        ", " + DatabaseHelper.COLUMN_QUEUE_QUANTITY +
//                        ", " + DatabaseHelper.COLUMN_TRACK_ENGRAM + ")" +
//                        " VALUES (" + queue.getId() + ", " + queue.getQuantity() + ", " + queue.getEngramId() + ")",
//                null, null
//        );
//
//        if (cursor.moveToFirst()) {
//            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_ID));
//            int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUEUE_QUANTITY));
//            long engramId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRACK_ENGRAM));
//
//            Helper.Log(LOGTAG, "-> Queue inserted/replaced successfully. [" + queue.toString() + "]");
//        } else {
//            Helper.Log(LOGTAG, "!!- Queue updated failed. [" + queue.toString() + "] -!!");
//        }
//
//        cursor.close();
//    }
//
//    // -- DATABASE SYSTEM METHODS --
//
//    private void DeleteTableData(String table) {
//        int rows = database.delete(table, "1", null);
//
//        Helper.Log(LOGTAG, "-- Deleted " + rows + " rows from table: " + table);
//    }
//
//    private void CreateTable(String table) {
//        DatabaseHelper.createTable(database, table);
//    }
//
//    private void DropTable(String table) {
//        DatabaseHelper.DropTable(database, table);
//    }
//
//    private void ResetTable(String table) {
//        OpenDatabase();
//
//        DeleteTableData(table);
//        DropTable(table);
//        CreateTable(table);
//
//        CloseDatabase();
//    }
//
//    private void InitializeCategories() {
//        Helper.Log(LOGTAG, "** Initializing Categories..");
//
//        ResetTable(DatabaseHelper.TABLE_CATEGORY);
//
//        List<Category> categories = CategoryInitializer.getCategories();
//        for (Category category : categories) {
//            OpenDatabase();
//
//            InsertCategory(category);
//
//            CloseDatabase();
//        }
//
//        // Save version persistently, future debugging helper
//        PreferenceHelper.getInstance(context).setPreference(Helper.CATEGORY_VERSION, CategoryInitializer.VERSION);
//
//        Helper.Log(LOGTAG, "** Category initialization completed.");
//    }
//
//    private void InitializeResources() {
//        Helper.Log(LOGTAG, "** Initializing Resources..");
//
//        ResetTable(DatabaseHelper.TABLE_RESOURCE);
//
//        OpenDatabase();
//
//        SparseArray<String> resources = ResourceInitializer.getResources();
//        for (int i = 0; i < resources.size(); i++) {
//            int imageId = resources.keyAt(i);
//            String name = resources.valueAt(i);
//
//
//            InsertResource(imageId, name);
//
//        }
//
//        CloseDatabase();
//
//        // Save version persistently, future debugging helper
//        PreferenceHelper.getInstance(context).setPreference(Helper.RESOURCE_VERSION, ResourceInitializer.VERSION);
//
//        Helper.Log(LOGTAG, "** Resource initialization completed.");
//    }
//
//    private void InitializeComplexResources() {
//        Helper.Log(LOGTAG, "** Initializing Complex Resources..");
//
//        ResetTable(DatabaseHelper.TABLE_COMPLEX_RESOURCE);
//
//        SparseArray<String> resources = ComplexResourceInitializer.getResources();
//        for (int i = 0; i < resources.size(); i++) {
//            int imageId = resources.keyAt(i);
//            String name = resources.valueAt(i);
//
//            OpenDatabase();
//
//            Resource resource = findSingleResourceByImageId(imageId);
//            Engram engram = findSingleEngramByImageId(imageId);
//
//            if (engram != null && resource != null) {
//                InsertComplexResource(resource, engram.getId());
//            } else {
//                if (engram == null) {
//                    Helper.Log(LOGTAG, "InitializeComplexResources() engram is null, imageId:" + resource.getDrawable() + " name:" + resource.getName());
//                }
//                if (resource == null) {
//                    Helper.Log(LOGTAG, "InitializeComplexResources() resource is null, imageId:" + engram.getDrawable() + " name:" + engram.getName());
//                }
//            }
//
//            CloseDatabase();
//        }
//
//        // Save version persistently, future debugging helper
//        PreferenceHelper.getInstance(getContext()).setPreference(Helper.COMPLEX_RESOURCE_VERSION, ComplexResourceInitializer.VERSION);
//
//        Helper.Log(LOGTAG, "** Complex Resource initialization completed.");
//    }
//
//    private void InitializeEngrams() {
//        Helper.Log(LOGTAG, "** Initializing Engrams..");
//
//        ResetTable(DatabaseHelper.TABLE_ENGRAM);
//
//        List<InitEngram> engrams = EngramInitializer.getEngrams();
//        for (InitEngram engram : engrams) {
//            OpenDatabase();
//
//            InsertEngramByInitEngram(engram);
//
//            CloseDatabase();
//        }
//
//        // Save version persistently, future debugging helper
//        PreferenceHelper.getInstance(context).setPreference(Helper.ENGRAM_VERSION, EngramInitializer.VERSION);
//
//        Helper.Log(LOGTAG, "** Engram initialization completed.");
//    }
//
//    private void OpenDatabase() {
//        database = openHelper.getWritableDatabase();
//
//        Helper.Log(LOGTAG, "-- Database has opened --");
//    }
//
//    private void CloseDatabase() {
//        database.close();
//
//        Helper.Log(LOGTAG, "-- Database has closed --");
//    }
//
//    public void ResetConnection() {
//        CloseDatabase();
//        OpenDatabase();
//
//        Helper.Log(LOGTAG, "-- Database connection reset --");
//    }
}