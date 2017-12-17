package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arc.resource.calculator.adapters.QueueAdapter;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.exception.CursorEmptyException;
import arc.resource.calculator.model.exception.CursorNullException;
import arc.resource.calculator.model.exception.PositionOutOfBoundsException;
import arc.resource.calculator.util.PrefsUtil;

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
public class CraftingQueue {
    private static final String TAG = CraftingQueue.class.getSimpleName();

    private static CraftingQueue sInstance;
    private QueueMap mQueue;
    private static QueueAdapter.QueueAdapterObserver mAdapterObserver;

    private CraftingQueue() {
        Log.d( TAG, "CraftingQueue: new CraftingQueue" );

        setQueue( new QueueMap() );

        PrefsObserver.getInstance().registerListener( TAG, new PrefsObserver.Listener() {
            @Override
            public void onPreferencesChanged( boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange,
                                              boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange ) {
                if ( dlcValueChange )
                    clearQueue();
            }
        } );
    }

    public static CraftingQueue getInstance() {
        if ( sInstance == null )
            sInstance = new CraftingQueue();

        return sInstance;
    }

    public void resume() {
    }

    public void pause( Context context ) {
        saveQueueToPrefs( context );
    }

    public void destroy() {
        PrefsObserver.getInstance().unregisterListener( TAG );
    }

    private QueueMap getQueue() {
        return mQueue;
    }

    private void setQueue( QueueMap queue ) {
        this.mQueue = queue;
    }

    private static QueueAdapter.QueueAdapterObserver getObserver() {
        return mAdapterObserver;
    }

    public void setObserver( QueueAdapter.QueueAdapterObserver observer ) {
        mAdapterObserver = observer;
    }

    public int getSize() {
        return getQueue().size();
    }

    public boolean contains( long id ) {
        return getQueue().contains( id );
    }

    public boolean isQueueEmpty() {
        return getSize() == 0;
    }

    public QueueEngram getCraftable( int position ) throws PositionOutOfBoundsException {
        return getQueue().getAt( position );
    }

    public QueueEngram getCraftable( long id ) {
        try {
            return getQueue().get( id );
        } catch ( PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }

        return null;
    }

    private void updateCraftable( int position, QueueEngram craftable ) throws PositionOutOfBoundsException {
        getQueue().update( position, craftable.getId(), craftable );
    }

    private void addCraftable( QueueEngram craftable ) {
        getQueue().add( craftable.getId(), craftable );
    }

    private void removeCraftable( int position ) throws PositionOutOfBoundsException {
        getQueue().removeAt( position );
    }

    private void removeAllCraftables() {
        getQueue().clear();
    }

    private int getPosition( long id ) {
        return getQueue().indexOfKey( id );
    }

    public void increaseQuantity( int position ) throws PositionOutOfBoundsException {
        QueueEngram craftable = getCraftable( position );

        craftable.increaseQuantity();

        // update item in list
        updateCraftable( position, craftable );

        // notify list adapter of changes
        if ( getObserver() != null )
            getObserver().notifyItemChanged( position );

        // notify outside listeners of changes
        QueueObserver.getInstance().notifyItemChanged( craftable.getId(), craftable.getQuantity() );
    }

    public void increaseQuantity( QueueEngram craftable ) throws PositionOutOfBoundsException {
        long id = craftable.getId();

        craftable.increaseQuantity();

        if ( getQueue().contains( id ) ) {
            int position = getPosition( id );

            // update item in list
            updateCraftable( position, craftable );

            // notify list adapter of changes
            if ( getObserver() != null )
                getObserver().notifyItemChanged( position );

            // notify outside listeners of changes
            QueueObserver.getInstance().notifyItemChanged( id, craftable.getQuantity() );
        } else {
            insert( craftable );
        }
    }

    public void setQuantity( Context context, long id, int quantity ) {
        try {
            if ( getQueue().contains( id ) ) {
                int position = getPosition( id );
                if ( quantity > 0 ) {
                    // get object from list
                    QueueEngram craftable = getCraftable( position );

                    // update object's quantity
                    craftable.setQuantity( quantity );

                    // update item in list
                    updateCraftable( position, craftable );

                    // notify list adapter of changes
                    if ( getObserver() != null )
                        getObserver().notifyItemChanged( position );

                    // notify outside listeners of changes
                    QueueObserver.getInstance().notifyItemChanged( id, craftable.getQuantity() );
                } else {
                    delete( position );
                }
            } else {
                if ( quantity > 0 ) {
                    insert( context, id, quantity );
                }
            }
        } catch ( PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }
    }

    private void insert( Context context, long id, int quantity ) throws PositionOutOfBoundsException {
        insert( queryForCraftable( context, id, quantity ) );
    }

    private void insert( QueueEngram craftable ) throws PositionOutOfBoundsException {
        Log.d( TAG, "insert: " + craftable.toString() );
        // add craftable to list
        addCraftable( craftable );

        // sort list by name
        getQueue().sort();

        // retrieve position, after sorted
//        int position = getPosition( craftable.getId() );

        // notify list adapter of changes
//        getObserver().notifyItemInserted( position );
        if ( getObserver() != null )
            getObserver().notifyDataSetPopulated();

        // notify outside listeners of changes
        QueueObserver.getInstance().notifyItemChanged( craftable.getId(), craftable.getQuantity() );
    }

    private void delete( int position ) throws PositionOutOfBoundsException {
        long id = getCraftable( position ).getId();

        // remove craftable from list
        removeCraftable( position );

        if ( getQueue().size() > 0 ) {
            // notify list adapter of changes
            getObserver().notifyItemRemoved( position );

            // notify outside listeners of changes
            QueueObserver.getInstance().notifyItemRemoved( id );
        } else {
            // notify ViewSwitcher of empty status
            getObserver().notifyDataSetEmpty();

            // notify outside listeners of changes
            QueueObserver.getInstance().notifyDataSetEmpty();
        }
    }

    public void delete( long id ) {
        try {
            delete( getPosition( id ) );
        } catch ( PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }
    }

    public void clearQueue() {
        // clear list of values
        removeAllCraftables();

        // notify ViewSwitcher of empty status
        if ( getObserver() != null )
            getObserver().notifyDataSetEmpty();

        // notify outside listeners of changes
        QueueObserver.getInstance().notifyDataSetEmpty();
    }

    private void saveQueueToPrefs( Context context ) {
        try {
            PrefsUtil.getInstance( context ).saveCraftingQueueJSONString( convertQueueToJSONString() );
        } catch ( JSONException | PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }
    }

    private String convertQueueToJSONString() throws PositionOutOfBoundsException, JSONException {
        JSONArray json = new JSONArray();

        for ( int i = 0; i < getQueue().size(); i++ ) {
            QueueEngram craftable = getCraftable( i );

            JSONObject object = new JSONObject();
            object.put( DatabaseContract.EngramEntry._ID, craftable.getId() );
            object.put( DatabaseContract.QueueEntry.COLUMN_QUANTITY, craftable.getQuantity() );

            json.put( object );
        }

        return json.toString();
    }

    private LongSparseArray<Integer> convertJSONStringToQueue( Context context ) {
        try {
            String jsonString = PrefsUtil.getInstance( context ).getCraftingQueueJSONString();

            if ( jsonString == null )
                return new LongSparseArray<>( 0 );

            JSONArray json = new JSONArray( jsonString );

            LongSparseArray<Integer> convertedQueueArray = new LongSparseArray<>( json.length() );
            for ( int i = 0; i < json.length(); i++ ) {
                JSONObject object = json.getJSONObject( i );

                long engramId = object.getLong( DatabaseContract.EngramEntry._ID );
                int quantity = object.getInt( DatabaseContract.QueueEntry.COLUMN_QUANTITY );

                convertedQueueArray.put( engramId, quantity );
            }

            return convertedQueueArray;
        } catch ( Exception e ) {
            return new LongSparseArray<>( 0 );
        }
    }

    private QueueEngram queryForCraftable( Context context, long engramId, int quantity ) {
        long dlc_id = PrefsUtil.getInstance( context ).getDLCPreference();
        Uri uri = DatabaseContract.EngramEntry.buildUriWithId( dlc_id, engramId );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new CursorEmptyException( uri );

            return new QueueEngram(
                    engramId,
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) ),
                    cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) ),
                    quantity );
        }
    }

    public void fetch( Context context ) {
        Log.d( TAG, "fetch: " );
        new FetchDataTask( context ).execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Boolean> {
        private final String TAG = FetchDataTask.class.getSimpleName();

        private Context mContext;
        private QueueMap mTempQueueMap;

        private Exception mException;

        FetchDataTask( Context context ) {
            this.mContext = context.getApplicationContext();
            this.mTempQueueMap = new QueueMap();
        }

        @Override
        protected void onPreExecute() {
            if ( getObserver() != null )
                getObserver().notifyInitializing();
        }

        @Override
        protected void onPostExecute( Boolean querySuccessful ) {
            Log.d( TAG, "onPostExecute: " + querySuccessful );
            if ( querySuccessful ) {
                if ( mTempQueueMap.size() > 0 ) {
                    setQueue( mTempQueueMap );

                    if ( getObserver() != null )
                        getObserver().notifyDataSetPopulated();

                    QueueObserver.getInstance().notifyDataSetPopulated();
                } else {
                    if ( getObserver() != null )
                        getObserver().notifyDataSetEmpty();
                }
            } else {
                if ( mException == null )
                    mException = new Exception( "Nullified Exception caught." );

                if ( getObserver() != null )
                    getObserver().notifyExceptionCaught( mException );
            }
        }

        @Override
        protected Boolean doInBackground( Void... params ) {

            try {
                LongSparseArray<Integer> savedQueue = convertJSONStringToQueue( mContext );

                if ( savedQueue.size() > 0 ) {
                    for ( int i = 0; i < savedQueue.size(); i++ ) {
                        long id = savedQueue.keyAt( i );
                        int quantity = savedQueue.valueAt( i );

                        QueueEngram craftable = queryForCraftable( mContext, id, quantity );

                        mTempQueueMap.add( id, craftable );
                    }
                }

                return true;
            } catch ( Exception e ) {
                mException = e;
                return false;
            }

        }
    }

    private class QueueMap extends SortableMap {
        QueueMap() {
            super();
        }

        @Override
        public QueueEngram get( long key ) throws PositionOutOfBoundsException {
            return ( QueueEngram ) super.get( key );
        }

        @Override
        public QueueEngram getAt( int position ) throws PositionOutOfBoundsException {
            return ( QueueEngram ) super.getAt( position );
        }

        @Override
        public Comparable getComparable( int position ) throws PositionOutOfBoundsException {
            return getAt( position ).getName();
        }
    }
}
