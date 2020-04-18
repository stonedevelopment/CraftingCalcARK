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

package arc.resource.calculator.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.LongSparseArray;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Vector;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;

import static arc.resource.calculator.db.AppDatabase.cParentId;
import static arc.resource.calculator.util.Util.NO_ID;

public class InitializationTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = InitializationTask.class.getSimpleName();
    private final SparseArray<Long> mDlcIds = new SparseArray<>(0);
    private LongSparseArray<TotalConversion> mTotalConversion = new LongSparseArray<>(0);
    private Listener mListener;
    private JSONObject mJSONObject;
    private WeakReference<Context> mContext;
    private AppDatabase database;

    // caught exception
    private Exception mException;

    public InitializationTask(Context context, JSONObject object, Listener listener) {
        database = AppDatabase.getInstance(context);

        setContext(context);
        setJSONObject(object);
        setListener(listener);
    }

    private Context getContext() {
        return mContext.get();
    }

    private void setContext(Context context) {
        mContext = new WeakReference<>(context);
    }

    private JSONObject getJSONObject() {
        return mJSONObject;
    }

    private void setJSONObject(JSONObject object) {
        mJSONObject = object;
    }

    private Listener getListener() {
        return mListener;
    }

    private void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        getListener().onInit();
    }

    @Override
    protected void onPostExecute(Boolean didUpdate) {
        if (mException == null)
            getListener().onFinish(didUpdate);
        else
            getListener().onError(mException);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // let user know that we've begun the process.
            getListener().onStart();

            // first, let's remove all records from database
            deleteAllRecords();

            // finally, let's onResume inserting some json data into our database!
            //  engrams > list of stations
            //  station > list of folders, list of engrams
            insertStations(getJSONObject().getJSONArray("stations"));
            insertResources(getJSONObject().getJSONArray("resources"));

            return true;
        }

        // If error, send error signal, onPause service
        catch (Exception e) {
            mException = e;
            return false;
        }
    }

    private void updateStatus(String statusMessage) {
        getListener().onUpdate(statusMessage);
    }

    private int delete(Uri uri) throws Exception {
        int rowsDeleted = getContext().getContentResolver().delete(uri, null, null);

        if (rowsDeleted == -1)
            throw new Exception("delete failed! uri:" + uri.toString());

        return rowsDeleted;
    }

    private void deleteAllRecords() {
        database.stationDao().deleteAll();
        database.folderDao().deleteAll();
        database.engramDao().deleteAll();
    }

    private void bulkInsertWithUri(Uri uri, Vector<ContentValues> vector)
            throws Exception {
        ContentValues[] contentValues = new ContentValues[vector.size()];

        int rowsInserted = getContext().getContentResolver().bulkInsert(uri, vector.toArray(contentValues));

        if (rowsInserted == NO_ID)
            throw new Exception("bulkInsert failed! uri:" + uri.toString() + ", contents:" + vector.toString());
    }

    private void insertComplexResources(JSONArray jsonArray) throws Exception {
        updateStatus(".");

        Vector<ContentValues> vector = new Vector<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            long resource_id = jsonObject.getLong(DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY);
            long engram_id = jsonObject.getLong(DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY);

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resource_id);
            values.put(DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engram_id);

            vector.add(values);
        }

        bulkInsertWithUri(DatabaseContract.ComplexResourceEntry.CONTENT_URI, vector);
    }

    private void insertResources(JSONArray jsonArray) throws Exception {
        updateStatus(".");

        Vector<ContentValues> vector = new Vector<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            long _id = jsonObject.getLong(DatabaseContract.ResourceEntry._ID);
            String name = jsonObject.getString(DatabaseContract.ResourceEntry.COLUMN_NAME);
            String imageFolder = jsonObject.getString(DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER);
            String imageFile = jsonObject.getString(DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE);
            JSONArray dlc_ids = jsonObject.getJSONArray(DatabaseContract.ResourceEntry.COLUMN_DLC_KEY);

            for (int j = 0; j < dlc_ids.length(); j++) {
                long dlc_id = dlc_ids.getLong(j);

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.ResourceEntry._ID, _id);
                values.put(DatabaseContract.ResourceEntry.COLUMN_NAME, name);
                values.put(DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER, imageFolder);
                values.put(DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE, imageFile);
                values.put(DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, dlc_id);

                vector.add(values);
            }
        }

        bulkInsertWithUri(DatabaseContract.ResourceEntry.CONTENT_URI, vector);
    }

    private void insertStations(JSONArray stationArray) throws JSONException {
        updateStatus(".");

        for (int i = 0; i < stationArray.length(); i++) {
            JSONObject stationObject = stationArray.getJSONObject(i);

            String name = stationObject.getString("name");
            String description = stationObject.getString("description");
            String imageFile = stationObject.getString("image_file");

            StationEntity stationEntity = database.stationDao().insert(new StationEntity(name, description, imageFile));

            insertEngrams(stationEntity.getRowId(), cParentId, stationObject.getJSONArray("engrams");
            insertFolders(stationEntity.getRowId(), cParentId, stationObject.getJSONArray("folders"));
        }
    }

    private void insertFolders(int stationId, int parentId, JSONArray folderArray) throws JSONException {
        for (int i = 0; i < folderArray.length(); i++) {
            JSONObject folderObject = folderArray.getJSONObject(i);

            String name = folderObject.getString("name");

            FolderEntity folderEntity = database.folderDao().insert(new FolderEntity(name, stationId, parentId));

            insertEngrams(stationId, folderEntity.getRowId(), folderObject.getJSONArray("engrams");
            insertFolders(stationId, folderEntity.getRowId(), folderObject.getJSONArray("folders"));
        }
    }

    private void insertEngrams(int stationId, int parentId, JSONArray engramArray) throws JSONException {
        for (int i = 0; i < engramArray.length(); i++) {
            JSONObject engramObject = engramArray.getJSONObject(i);

            String name = engramObject.getString("name");
            String description = engramObject.getString("description");
            String imageFile = engramObject.getString("image_file");
            int yield = engramObject.getInt("yield");
            int level = engramObject.getInt("level");
            int craftingTime = engramObject.getInt("crafting_time");

            database.engramDao().insert(new EngramEntity(name, description, imageFile, yield, level, craftingTime, parentId, stationId));
        }
    }

    private void insertEngrams(JSONArray jsonArray) throws Exception {
        updateStatus(".");

        Vector<ContentValues> vector = new Vector<>(jsonArray.length());
        Vector<ContentValues> compositionVector = new Vector<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            long _id = jsonObject.getLong(DatabaseContract.EngramEntry._ID);
            String name = jsonObject.getString(DatabaseContract.EngramEntry.COLUMN_NAME);
            String description = jsonObject.getString(DatabaseContract.EngramEntry.COLUMN_DESCRIPTION);
            String imageFolder = jsonObject.getString(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER);
            String imageFile = jsonObject.getString(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE);
            int yield = jsonObject.getInt(DatabaseContract.EngramEntry.COLUMN_YIELD);
            int level = jsonObject.getInt(DatabaseContract.EngramEntry.COLUMN_LEVEL);
            long category_id = jsonObject.getLong(DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY);
            JSONArray station_ids = jsonObject.getJSONArray(DatabaseContract.EngramEntry.COLUMN_STATION_KEY);
            JSONArray dlc_ids = jsonObject.getJSONArray(DatabaseContract.EngramEntry.COLUMN_DLC_KEY);

            // first check dlc_id
            for (int j = 0; j < dlc_ids.length(); j++) {
                long dlc_id = dlc_ids.getLong(j);

                // against station_ids
                for (int s = 0; s < station_ids.length(); s++) {
                    long station_id = station_ids.getLong(s);

                    // check if dlc_id and station_id's dlc_id match
                    Cursor cursor = getContext().getContentResolver().query(
                            DatabaseContract.StationEntry.buildUriWithId(dlc_id, station_id),
                            null, null, null, null);

                    // no match found, try next station_id
                    if (cursor == null)
                        continue;

                    // match found, add values into vector
                    if (cursor.moveToFirst()) {
                        ContentValues values = new ContentValues();
                        values.put(DatabaseContract.EngramEntry._ID, _id);
                        values.put(DatabaseContract.EngramEntry.COLUMN_NAME, name);
                        values.put(DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, description);
                        values.put(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER, imageFolder);
                        values.put(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE, imageFile);
                        values.put(DatabaseContract.EngramEntry.COLUMN_YIELD, yield);
                        values.put(DatabaseContract.EngramEntry.COLUMN_LEVEL, level);
                        values.put(DatabaseContract.EngramEntry.COLUMN_STATION_KEY, station_id);
                        values.put(DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, category_id);
                        values.put(DatabaseContract.EngramEntry.COLUMN_DLC_KEY, dlc_id);

                        vector.add(values);

                        compositionVector.addAll(insertComposition(_id, dlc_id, jsonObject.getJSONArray(DatabaseContract.CompositionEntry.TABLE_NAME)));
                    }

                    cursor.close();
                }
            }
        }

        bulkInsertWithUri(DatabaseContract.EngramEntry.CONTENT_URI, vector);
        bulkInsertWithUri(DatabaseContract.CompositionEntry.CONTENT_URI, compositionVector);
    }

    private Vector<ContentValues> insertComposition(long engram_id, long dlc_id, JSONArray jsonArray)
            throws JSONException {
        Vector<ContentValues> vector = new Vector<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            long resource_id = jsonObject.getLong(DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY);
            int quantity = jsonObject.getInt(DatabaseContract.CompositionEntry.COLUMN_QUANTITY);

            // convert resource id if specific dlc version calls for it
            if (hasConversion(dlc_id, resource_id))
                resource_id = getConversion(dlc_id, resource_id);

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY, engram_id);
            values.put(DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resource_id);
            values.put(DatabaseContract.CompositionEntry.COLUMN_DLC_KEY, dlc_id);
            values.put(DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity);

            vector.add(values);
        }

        return vector;
    }

    private LongSparseArray<TotalConversion> mapTotalConversion(JSONArray jsonArray)
            throws JSONException {
        updateStatus(".");

        LongSparseArray<TotalConversion> map = new LongSparseArray<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            long dlc_id = jsonObject.getLong(DatabaseContract.TotalConversionEntry.COLUMN_DLC_KEY);
            JSONArray resourceArray = jsonObject.getJSONArray(DatabaseContract.ResourceEntry.TABLE_NAME);

            TotalConversion totalConversion = new TotalConversion();
            for (int j = 0; j < resourceArray.length(); j++) {
                JSONObject object = resourceArray.getJSONObject(j);

                long from = object.getLong(DatabaseContract.TotalConversionEntry.COLUMN_FROM);
                long to = object.getLong(DatabaseContract.TotalConversionEntry.COLUMN_TO);

                totalConversion.conversionIds.put(from, to);
            }

            map.put(dlc_id, totalConversion);
        }

        return map;
    }

    private boolean hasConversion(long dlc_id, long resource_id) {
        return mTotalConversion.get(dlc_id) != null &&
                mTotalConversion.get(dlc_id).conversionIds.get(resource_id) != null;
    }

    private long getConversion(long dlc_id, long resource_id) {
        return mTotalConversion.get(dlc_id).conversionIds.get(resource_id);
    }

    public interface Listener {
        void onError(Exception e);

        void onInit();

        void onStart();

        void onUpdate(String message);

        void onFinish(boolean didUpdate);
    }

    private class TotalConversion {
        final LongSparseArray<Long> conversionIds;

        TotalConversion() {
            conversionIds = new LongSparseArray<>();
        }
    }
}
