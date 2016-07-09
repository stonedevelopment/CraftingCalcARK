package com.gmail.jaredstone1982.craftingcalcark.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableResource;
import com.gmail.jaredstone1982.craftingcalcark.model.DetailEngram;
import com.gmail.jaredstone1982.craftingcalcark.model.Queue;

import java.util.HashMap;

public class QueueDataSource {

    private String LOGTAG;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    public QueueDataSource(Context context, String tag) {
        this.LOGTAG = tag;
        this.openHelper = new DBOpenHelper(context);
    }

    public void Open() {
        database = openHelper.getWritableDatabase();
        Log.d(LOGTAG, "Database open");
    }

    public void Close() {
        database.close();
        Log.d(LOGTAG, "Database closed");
    }

    public Queue Insert(long engramId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_QUEUE_QUANTITY, quantity);
        values.put(DBOpenHelper.COLUMN_TRACK_ENGRAM, engramId);

        Queue queue = new Queue();
        queue.setId(database.insert(DBOpenHelper.TABLE_QUEUE, null, values));
        queue.setQuantity(quantity);
        queue.setEngramId(engramId);

        return queue;
    }

    public boolean Delete(long engramId) {
        return database.delete(DBOpenHelper.TABLE_QUEUE, DBOpenHelper.COLUMN_TRACK_ENGRAM + "=" + engramId, null) > 0;
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

    public SparseArray<CraftableEngram> findAllEngrams() {
        Cursor cursor = database.rawQuery(
                "SELECT " + DBOpenHelper.TABLE_ENGRAM + ".*, " + DBOpenHelper.TABLE_QUEUE + ".*" +
                        " FROM " + DBOpenHelper.TABLE_QUEUE +
                        " INNER JOIN " + DBOpenHelper.TABLE_ENGRAM +
                        " ON " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + DBOpenHelper.TABLE_ENGRAM + "." + DBOpenHelper.COLUMN_ENGRAM_ID,
                null, null
        );

        Log.d(LOGTAG, "Returned " + cursor.getCount() + " rows from findAllEngrams");

        return cursorToEngrams(cursor);
    }

    /**
     * Instantiates an array of CraftableEngrams to include their relevant quantities
     *
     * @param cursor Holds cursor data pulled from Database
     * @return SparseArray of CraftableEngram objects
     */
    public SparseArray<CraftableEngram> cursorToEngrams(Cursor cursor) {
        SparseArray<CraftableEngram> engrams = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID);
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));

                CraftableEngram engram = new CraftableEngram(id, name, imageId, quantity);
                engrams.put(engrams.size(), engram);
            }
        }
        return engrams;
    }

    public SparseArray<CraftableResource> findAllResources() {
        Cursor cursor = database.rawQuery(
                "SELECT " + DBOpenHelper.TABLE_RESOURCE + ".*, " + DBOpenHelper.TABLE_QUEUE + ".* " +
                        " FROM " + DBOpenHelper.TABLE_RESOURCE +
                        " INNER JOIN " + DBOpenHelper.TABLE_QUEUE +
                        " ON " + DBOpenHelper.TABLE_RESOURCE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + DBOpenHelper.TABLE_QUEUE + "." + DBOpenHelper.COLUMN_TRACK_ENGRAM,
                null, null
        );

        return cursorToResources(cursor);
    }

    private SparseArray<CraftableResource> cursorToResources(Cursor cursor) {
        SparseArray<CraftableResource> resources = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_ID);
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_QUANTITY));
                int quantityPer = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));

                CraftableResource resource;
                if (resources.indexOfKey(imageId) > -1) {
                    resource = resources.get(imageId);
                    resource.increaseQuantity(quantity * quantityPer);
                } else {
                    resource = new CraftableResource(id, name, imageId);
                    resource.setQuantity(quantity * quantityPer);
                }

                resources.put(imageId, resource);
            }
        }

        return resources;
    }

    public SparseArray<CraftableResource> findEngramResources(Long id) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_RESOURCE +
                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + id,
                null, null
        );

        return cursorToEngramResources(cursor);
    }

    private SparseArray<CraftableResource> cursorToEngramResources(Cursor cursor) {
        SparseArray<CraftableResource> resources = new SparseArray<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_ID);
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_IMAGE_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_RESOURCE_QUANTITY));

                CraftableResource resource = new CraftableResource(id, name, imageId, quantity);
                resources.put(imageId, resource);
            }
        }

        return resources;
    }

    public DetailEngram findSingleEngram(long id) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_ENGRAM +
                        " WHERE " + DBOpenHelper.COLUMN_ENGRAM_ID +
                        " = " + id,
                null, null
        );

        Log.d(LOGTAG, "Returned " + cursor.getCount() + " rows from findSingleEngram");

        return cursorToSingleEngram(cursor);
    }

    public DetailEngram cursorToSingleEngram(Cursor cursor) {
        DetailEngram engram = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_NAME));
            int imageId = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_IMAGE_ID));
            String description = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENGRAM_DESCRIPTION));
            int quantity = findSingleQuantity(id);
            SparseArray<CraftableResource> composition = findEngramResources(id);

            engram = new DetailEngram(id, name, imageId, description, quantity, composition);
        }
        return engram;
    }

    public int findSingleQuantity(Long id) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE +
                        " WHERE " + DBOpenHelper.COLUMN_TRACK_ENGRAM +
                        " = " + id,
                null, null
        );

        return cursorToSingleQuantity(cursor);
    }

    public int cursorToSingleQuantity(Cursor cursor) {
        int quantity;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));
        } else {
            quantity = 0;
        }

        return quantity;
    }

    public HashMap<Long, Queue> findAllQueues() {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DBOpenHelper.TABLE_QUEUE,
                null, null
        );

        Log.d(LOGTAG, "Returned " + cursor.getCount() + " rows from findAllQueues");

        return cursorToQueues(cursor);
    }

    private HashMap<Long, Queue> cursorToQueues(Cursor cursor) {
        HashMap<Long, Queue> queues = new HashMap<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_ID));
                long engramId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_TRACK_ENGRAM));
                int quantity = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_QUEUE_QUANTITY));

                Queue queue = new Queue(id, engramId, quantity);
                queues.put(id, queue);
            }
        }
        return queues;
    }

    public void ResetData() {
        DBOpenHelper.dropTable(database, DBOpenHelper.TABLE_QUEUE);
        InitializeData();
    }

    public void DeleteTableData() {
        int rows = database.delete(DBOpenHelper.TABLE_QUEUE, "1", null);
        Helper.Log(LOGTAG,"Deleted " + rows + " rows from queue.");
    }

    public void InitializeData() {
        database.execSQL(DBOpenHelper.TABLE_QUEUE_CREATE);
    }
}