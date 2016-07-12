//package com.gmail.jaredstone1982.craftingcalcark.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.SparseArray;
//
//import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
//import com.gmail.jaredstone1982.craftingcalcark.model.CraftableEngram;
//import com.gmail.jaredstone1982.craftingcalcark.model.CraftableResource;
//import com.gmail.jaredstone1982.craftingcalcark.model.DetailEngram;
//import com.gmail.jaredstone1982.craftingcalcark.model.Queue;
//import com.gmail.jaredstone1982.craftingcalcark.model.Resource;
//
//import java.util.HashMap;
//
//public class QueueDataSource {
//    private static final String LOGTAG = "QUEUEDATA";
//
//    private SQLiteOpenHelper openHelper;
//    private SQLiteDatabase database;
//
//    public QueueDataSource(Context context, String LOGTAG) {
//        this.openHelper = new DBOpenHelper(context);
//    }
//
////    public void Insert(long engramId, int quantity) {
////        ContentValues values = new ContentValues();
////        values.put(DBOpenHelper.COLUMN_QUEUE_QUANTITY, quantity);
////        values.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engramId);
////
////        database.insert(DBOpenHelper.TABLE_QUEUE, null, values);
////    }
////
////    public boolean Delete(long engramId) {
////        return database.delete(DBOpenHelper.TABLE_QUEUE, DBOpenHelper.COLUMN_TRACK_ENGRAM + "=" + engramId, null) > 0;
////    }
////
////    /**
////     * Updates 'queue' table with new quantity. TODO: No error checking
////     *
////     * @param queue
////     */
////    public void Update(Queue queue) {
////        Cursor cursor = database.rawQuery(
////                "INSERT OR REPLACE INTO " + DBOpenHelper.TABLE_QUEUE +
////                        " (" + DBOpenHelper.COLUMN_QUEUE_ID +
////                        ", " + DBOpenHelper.COLUMN_QUEUE_QUANTITY +
////                        ", " + DBOpenHelper.COLUMN_TRACK_ENGRAM + ")" +
////                        " VALUES (" + queue.getId() + ", " + queue.getQuantity() + ", " + queue.getEngramId() + ")",
////                null, null
////        );
////
////        if (cursor.getCount() > 0) {
////            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
////            int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
////            long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
////        }
////
////        cursor.close();
////    }
////
//////    public SparseArray<CraftableEngram> findAllCraftableEngrams() {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT " + DBOpenHelper.TABLE_ENGRAM + ".*, " + DBOpenHelper.TABLE_QUEUE + ".*" +
//////                        " FROM " + DBOpenHelper.TABLE_QUEUE +
//////                        " INNER JOIN " + DBOpenHelper.TABLE_ENGRAM +
//////                        " ON " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
//////                        " = " + DBOpenHelper.TABLE_ENGRAM + "." + DBOpenHelper.COLUMN_ENGRAM_ID,
//////                null, null
//////        );
//////
//////        Helper.Log(LOGTAG, "-- findAllCraftableEngrams() > Returned " + cursor.getCount() + " rows from tables: " + DBOpenHelper.TABLE_ENGRAM + ", " + DBOpenHelper.TABLE_QUEUE);
//////
//////        return cursorToCraftableEngrams(cursor);
//////    }
////
//////    public HashMap<Long, Queue> findAllQueues() {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE,
//////                null, null
//////        );
//////
//////        Helper.Log(LOGTAG, "-- findAllQueues() > Returned " + cursor.getCount() + " rows from table: " + DBOpenHelper.TABLE_QUEUE);
//////
//////        return cursorToQueues(cursor);
//////    }
////
//////    public SparseArray<CraftableResource> findAllCraftableResources() {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT " + DBOpenHelper.TABLE_COMPOSITION + ".*, " + DBOpenHelper.TABLE_QUEUE + ".* " +
//////                        " FROM " + DBOpenHelper.TABLE_COMPOSITION +
//////                        " INNER JOIN " + DBOpenHelper.TABLE_QUEUE +
//////                        " ON " + DBOpenHelper.TABLE_COMPOSITION + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
//////                        " = " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM,
//////                null, null
//////        );
//////
//////        return cursorToResources(cursor);
//////    }
////
//////    public SparseArray<CraftableResource> findEngramResources(Long id) {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT * FROM " + DBOpenHelper.TABLE_COMPOSITION +
//////                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM +
//////                        " = " + id,
//////                null, null
//////        );
//////
//////        return cursorToCraftableResources(cursor);
//////    }
////
//////    public DetailEngram findSingleDetailEngram(long id) {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM +
//////                        " WHERE " + DBOpenHelper.COLUMN_ENGRAM_ID +
//////                        " = " + id,
//////                null, null
//////        );
//////
//////        Helper.Log(LOGTAG, "Returned " + cursor.getCount() + " rows from findSingleDetailEngram");
//////
//////        return cursorToSingleDetailEngram(cursor);
//////    }
////
//////    public Queue findSingleQueue(Long engramId) {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE +
//////                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM +
//////                        " = " + engramId,
//////                null, null
//////        );
//////
//////        return cursorToSingleQueue(cursor);
//////    }
////
//////    /**
//////     * Returns a Resource object, found by its imageId
//////     *
//////     * @param id long value used to find requested Resource object
//////     * @return Resource
//////     */
//////    private Resource findSingleResource(long id) {
//////        Cursor cursor = database.rawQuery(
//////                "SELECT * FROM " + DBOpenHelper.TABLE_RESOURCE +
//////                        " WHERE " + DBOpenHelper.COLUMN_RESOURCE_ID +
//////                        " = " + id,
//////                null, null
//////        );
//////
//////        return cursorToSingleResource(cursor);
//////    }
////
//////    public SparseArray<CraftableEngram> cursorToCraftableEngrams(Cursor cursor) {
//////        SparseArray<CraftableEngram> engrams = new SparseArray<>();
//////
//////        if (cursor.getCount() > 0) {
//////            while (cursor.moveToNext()) {
//////                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
//////                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
//////                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
//////                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
//////
//////                CraftableEngram engram = new CraftableEngram(id, name, imageId, quantity);
//////                engrams.put(imageId, engram);
//////
//////                Helper.Log(LOGTAG, " > Engram Details: " + engram.toString());
//////            }
//////        }
//////        return engrams;
//////    }
////
//////    private HashMap<Long, Queue> cursorToQueues(Cursor cursor) {
//////        HashMap<Long, Queue> queues = new HashMap<>();
//////
//////        if (cursor.getCount() > 0) {
//////            while (cursor.moveToNext()) {
//////                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
//////                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
//////                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
//////
//////                Queue queue = new Queue(id, engramId, quantity);
//////
//////                Helper.Log(LOGTAG, " > Queue Details: " + queue.toString());
//////
//////                queues.put(engramId, queue);
//////            }
//////        }
//////        return queues;
//////    }
////
//////    private SparseArray<CraftableResource> cursorToResources(Cursor cursor) {
//////        SparseArray<CraftableResource> resources = new SparseArray<>();
//////
//////        if (cursor.getCount() > 0) {
//////            while (cursor.moveToNext()) {
//////                long compositionId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_ID));
//////                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_QUANTITY));
//////                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
//////                long resourceId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_RESOURCE));
//////                int quantityPer = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
//////
//////                CraftableResource resource = new CraftableResource(findSingleResource(resourceId));
//////                if (resources.indexOfKey(resource.getImageId()) > -1) {
//////                    resource = resources.get(resource.getImageId());
//////                    resource.increaseQuantity(quantity * quantityPer);
//////                } else {
//////                    resource.setQuantity(quantity * quantityPer);
//////                }
//////
//////                resources.put(resource.getImageId(), resource);
//////            }
//////        }
//////
//////        return resources;
//////    }
////
//////    private SparseArray<CraftableResource> cursorToCraftableResources(Cursor cursor) {
//////        SparseArray<CraftableResource> resources = new SparseArray<>();
//////
//////        if (cursor.getCount() > 0) {
//////            while (cursor.moveToNext()) {
//////                long compositionId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_ID));
//////                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPOSITION_QUANTITY));
//////                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
//////                long resourceId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_RESOURCE));
//////
//////                CraftableResource resource = new CraftableResource(findSingleResource(resourceId), quantity);
//////                resource.setQuantity(quantity);
//////
//////                resources.put(resource.getImageId(), resource);
//////            }
//////        }
//////
//////        return resources;
//////    }
////
//////    public DetailEngram cursorToSingleDetailEngram(Cursor cursor) {
//////        DetailEngram engram = null;
//////        if (cursor.getCount() > 0) {
//////            cursor.moveToFirst();
//////
//////            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
//////            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
//////            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
//////            String description = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_DESCRIPTION));
//////            int quantity = findSingleQueue(id).getQuantity();
//////            SparseArray<CraftableResource> composition = findEngramResources(id);
//////
//////            engram = new DetailEngram(id, name, imageId, description, quantity, composition);
//////        }
//////        return engram;
//////    }
////
//////    public Queue cursorToSingleQueue(Cursor cursor) {
//////        Queue queue = null;
//////
//////        if (cursor.getCount() > 0) {
//////            cursor.moveToFirst();
//////            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
//////            int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
//////            long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
//////
//////            queue = new Queue(id, engramId, quantity);
//////        }
//////
//////        return queue;
//////    }
////
//////    public Resource cursorToSingleResource(Cursor cursor) {
//////        Resource resource = null;
//////
//////        if (cursor.getCount() > 0) {
//////            cursor.moveToFirst();
//////
//////            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_ID));
//////            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_NAME));
//////            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID));
//////
//////            resource = new Resource(id, name, imageId);
//////        }
//////
//////        return resource;
//////    }
////
////    public void CreateTable() {
////        database.execSQL(DBOpenHelper.TABLE_QUEUE_CREATE);
////
////        Helper.Log(LOGTAG, "-- Created table: " + DBOpenHelper.TABLE_QUEUE);
////    }
////
////    public void DeleteTableData() {
////        int rows = database.delete(DBOpenHelper.TABLE_QUEUE, "1", null);
////
////        Helper.Log(LOGTAG, "-- Deleted " + rows + " rows from table: " + DBOpenHelper.TABLE_QUEUE);
////    }
////
////    public void DropTable() {
////        DBOpenHelper.dropTable(database, DBOpenHelper.TABLE_QUEUE);
////
////        Helper.Log(LOGTAG, "-- Dropped table: " + DBOpenHelper.TABLE_QUEUE);
////    }
////
////    public void Open() {
////        database = openHelper.getWritableDatabase();
////        Helper.Log(LOGTAG, "Database open");
////    }
////
////    public void Close() {
////        database.close();
////        Helper.Log(LOGTAG, "Database closed");
////    }
//}