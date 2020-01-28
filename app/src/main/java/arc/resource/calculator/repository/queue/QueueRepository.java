/*
 * Copyright (c) 2020 Jared Stone
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

package arc.resource.calculator.repository.queue;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.LongSparseArray;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.map.QueueMap;
import arc.resource.calculator.tasks.fetch.queue.FetchQueueDataTask;
import arc.resource.calculator.tasks.fetch.queue.FetchQueueDataTaskObservable;
import arc.resource.calculator.tasks.fetch.queue.FetchQueueDataTaskObserver;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

/**
 * Repository for the Queue
 * <p>
 * - Manipulates Queue
 * - Communicates changes to the Queue with {@QueueObserver}.
 */
public class QueueRepository {
    private static final String TAG = QueueRepository.class.getSimpleName();

    private QueueMap mQueueMap;
    private QueueObservable mQueueObservable;
    private ExceptionObservable mExceptionObservable;
    private boolean mIsFetching;
    private FetchQueueDataTask mFetchDataTask;

    private static QueueRepository sInstance;

    public static QueueRepository getInstance() {
        if (sInstance == null)
            sInstance = new QueueRepository();

        return sInstance;
    }

    private QueueRepository() {
        setupQueueMap();

        setupExceptionObservable();

        setupQueueObservable();
    }

    /**
     * Initialization method called by LoadScreenActivity
     *
     * @param context Application context
     */
    public void init(Context context) {
        setupFetchDataTask(context);
        fetch();
    }

    public void resume(Context context) {
        //  TODO: move PrefsObserver out of QueueRepository
        PrefsObserver.getInstance().registerListener(TAG, new PrefsObserver.Listener() {
            @Override
            public void onPreferencesChanged(boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange,
                                             boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange) {
                if (dlcValueChange)
                    clear();
            }
        });
    }

    public void pause(Context context) {
        saveQueueToPrefs(context);
        PrefsObserver.getInstance().unregisterListener(TAG);
    }

    private void setupQueueMap() {
        setQueueMap(new QueueMap());
    }

    private void setupQueueObservable() {
        mQueueObservable = QueueObservable.getInstance();
    }

    private void setupExceptionObservable() {
        mExceptionObservable = ExceptionObservable.getInstance();
    }

    public void addObserver(String key, QueueObserver observer) {
        mQueueObservable.addObserver(key, observer);
    }

    public void removeObserver(String key) {
        mQueueObservable.removeObserver(key);
    }

    private void setupFetchDataTask(Context context) {
        if (mFetchDataTask != null) return;
        Log.d(TAG, "setupFetchDataTask: ");
        mFetchDataTask = new FetchQueueDataTask(context, new FetchQueueDataTaskObservable(new FetchQueueDataTaskObserver() {
            @Override
            public void onPreFetch() {
                mIsFetching = true;
            }

            @Override
            public void onFetching() {
                mQueueObservable.notifyQueueDataPopulating();
            }

            @Override
            public void onFetchSuccess() {
                mIsFetching = false;
            }

            @Override
            public void onFetchSuccess(QueueMap fetchedQueue) {
                mIsFetching = false;
                setQueueMap(fetchedQueue);

                if (isQueueEmpty())
                    mQueueObservable.notifyQueueDataEmpty();
                else
                    mQueueObservable.notifyQueueDataPopulated();
            }

            @Override
            public void onFetchException(Exception e) {
                mIsFetching = false;
                mExceptionObservable.notifyExceptionCaught(TAG, e);
            }

            @Override
            public void onFetchFail() {
                // TODO: 1/27/2020 handle onFetchFail()
                mIsFetching = false;
            }

            @Override
            public void onFetchCancel(boolean didCancel) {
                //  attempt to fetch again
                if (mIsFetching) {
                    mIsFetching = false;
                    fetch();
                }
            }
        }));
    }

    private void setQueueMap(QueueMap queueMap) {
        mQueueMap = queueMap;
    }

    public int getItemCount() {
        return mQueueMap.size();
    }

    private boolean isQueueEmpty() {
        return getItemCount() == 0;
    }

    public boolean doesContainEngram(long engramId) {
        return mQueueMap.contains(engramId);
    }

    private QueueEngram getEngram(int position) {
        return mQueueMap.valueAt(position);
    }

    public QueueEngram getEngram(long engramId) {
        return mQueueMap.get(engramId);
    }

    public int getEngramQuantity(long engramId) {
        if (doesContainEngram(engramId)) return getEngram(engramId).getQuantity();
        return 0;
    }

    public int getItemPosition(@NonNull QueueEngram engram) {
        return getItemPosition(engram.getId());
    }

    public int getItemPosition(long engramId) {
        return mQueueMap.indexOfKey(engramId);
    }

    private void updateEngram(int position, @NonNull QueueEngram item) {
        mQueueMap.setValueAt(position, item);
    }

    private void addEngram(@NonNull QueueEngram craftable) {
        mQueueMap.add(craftable.getId(), craftable);
    }

    private void removeEngram(int position) {
        mQueueMap.removeAt(position);
    }

    private void removeAllCraftables() {
        mQueueMap.clear();
    }

    public void increaseQuantity(int position) {
        //  get engram by position
        QueueEngram engram = getEngram(position);

        //  increase quantity of engram, default quantity: 0
        engram.increaseQuantity();

        //  update engram by position in list
        update(position, engram);
    }

    public void increaseQuantity(QueueEngram engram) {
        //  increase quantity of engram, default quantity: 0
        engram.increaseQuantity();

        //  update engram
        update(engram);
    }

    /**
     * Public method that allows outsiders to set the quantity of a specific engram.
     *
     * @param context  Context object used to pass through to insert();
     * @param engramId id value used to update engram
     * @param quantity quantity value used to update engram
     */
    public boolean requestToUpdateQuantity(@NonNull Context context, long engramId, int quantity) {
        boolean updated = false;

        //  check if engram exists in list
        if (doesContainEngram(engramId)) {
            //  get position of engram in list
            int position = getItemPosition(engramId);

            //  check if quantity is above zero, if so: update, if not: delete
            if (quantity > 0) {
                // get engram from list
                QueueEngram engram = getEngram(position);

                // update engram's quantity
                engram.setQuantity(quantity);

                // update engram in list
                update(position, engram);
            } else {
                delete(position);
            }

            updated = true;
        } else {
            //  check if quantity is above zero, if so: insert, if not: do nothing
            if (quantity > 0) {
                insert(context, engramId, quantity);

                updated = true;
            }
        }

        return updated;
    }

    private void insert(@NonNull Context context, long engramId, int quantity) {
        try {
            QueueEngram engram = query(context, engramId, quantity);
            insert(engram);
        } catch (ExceptionUtil.CursorNullException | ExceptionUtil.CursorEmptyException e) {
            mExceptionObservable.notifyExceptionCaught(TAG, e);
        }
    }

    private void insert(@NonNull QueueEngram engram) {
        // add engram to list
        addEngram(engram);

        // sort list by name
        mQueueMap.sort();

        // notify outside listeners of changes
        mQueueObservable.notifyEngramAdded(engram);
    }

    private void update(@NonNull QueueEngram engram) {
        //  get engram id
        long engramId = engram.getId();

        //  get engram position in list
        int position = getItemPosition(engramId);

        //  check if engram position is valid, if so: update, if not: insert
        if (position > -1) {
            update(position, engram);
        } else {
            insert(engram);
        }
    }

    private void update(int position, @NonNull QueueEngram engram) {
        // update engram in list
        updateEngram(position, engram);

        // notify outside listeners of changes
        mQueueObservable.notifyEngramChanged(engram);
    }

    private void delete(int position) {
        //  get engram by position in list
        QueueEngram engram = getEngram(position);

        // remove engram from list
        removeEngram(position);

        //  check if queue is empty, if so: notify empty, if not: notify that engram was removed
        if (!isQueueEmpty()) {
            mQueueObservable.notifyEngramRemoved(engram);
        } else {
            mQueueObservable.notifyQueueDataEmpty();
        }
    }

    public boolean requestToRemoveEngram(long engramId) {
        //  check if engram exists in list, if so: delete, if not: return false
        if (doesContainEngram(engramId)) {
            //  get position of engram in list
            int position = getItemPosition(engramId);

            //  delete engram by position
            delete(position);

            //  engram was deleted successfully
            return true;
        }

        //  engram was not deleted successfully, does not exist in list
        return false;
    }

    private void clear() {
        // clear list of values
        removeAllCraftables();

        // notify outside listeners of changes
        mQueueObservable.notifyQueueDataEmpty();
    }

    public boolean requestToClearQueue() {
        clear();

        return true;
    }

    private void saveQueueToPrefs(Context context) {
        PrefsUtil.getInstance(context).saveCraftingQueueJSONString(convertQueueToJSONString());
    }

    private String convertQueueToJSONString() {
        try {
            JSONArray json = new JSONArray();

            for (int i = 0; i < mQueueMap.size(); i++) {
                QueueEngram craftable = getEngram(i);

                JSONObject object = new JSONObject();
                object.put(DatabaseContract.EngramEntry._ID, craftable.getId());
                object.put(DatabaseContract.QueueEntry.COLUMN_QUANTITY, craftable.getQuantity());

                json.put(object);
            }

            return json.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    public static LongSparseArray<Integer> convertJSONStringToQueue(Context context) {
        try {
            String jsonString = PrefsUtil.getInstance(context).getCraftingQueueJSONString();

            if (jsonString == null)
                return new LongSparseArray<>(0);

            JSONArray json = new JSONArray(jsonString);

            LongSparseArray<Integer> convertedQueueArray = new LongSparseArray<>(json.length());
            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);

                long engramId = object.getLong(DatabaseContract.EngramEntry._ID);
                int quantity = object.getInt(DatabaseContract.QueueEntry.COLUMN_QUANTITY);

                convertedQueueArray.put(engramId, quantity);
            }

            return convertedQueueArray;
        } catch (Exception e) {
            return new LongSparseArray<>(0);
        }
    }

    public static QueueEngram query(Context context, long engramId, int quantity) throws
            ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        long dlc_id = PrefsUtil.getInstance(context).getDLCPreference();
        Uri uri = DatabaseContract.EngramEntry.buildUriWithId(dlc_id, engramId);

        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {

            if (cursor == null)
                throw new ExceptionUtil.CursorNullException(uri);

            if (!cursor.moveToFirst())
                throw new ExceptionUtil.CursorEmptyException(uri);

            return new QueueEngram(
                    engramId,
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD)),
                    quantity);
        }
    }

    private void fetch() {
        Log.d(TAG, "fetch: " + mIsFetching);
        if (mIsFetching) {
            mFetchDataTask.cancel(true);
        } else {
            mFetchDataTask.execute();
        }
    }
}