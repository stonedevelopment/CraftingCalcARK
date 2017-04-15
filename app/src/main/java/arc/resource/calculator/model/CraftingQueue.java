package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.LongSparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arc.resource.calculator.adapters.QueueAdapter;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.util.ExceptionUtil;
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

    private QueueSparseArray mQueue;

    private static QueueAdapter.Observer mViewObserver;


    private CraftingQueue( Context context ) {
        setQueue( new QueueSparseArray() );

        fetchData( context );
    }

    public static CraftingQueue createInstance( Context context, QueueAdapter.Observer viewObserver ) {
        mViewObserver = viewObserver;

        sInstance = new CraftingQueue( context.getApplicationContext() );

        return sInstance;
    }

    public static CraftingQueue getInstance() {
        return sInstance;
    }

    public QueueSparseArray getQueue() {
        return mQueue;
    }

    private void setQueue( QueueSparseArray queue ) {
        this.mQueue = queue;
    }

    public int getSize() {
        return getQueue().size();
    }

    public boolean contains( long id ) {
        return getQueue().contains( id );
    }

    public QueueEngram getCraftable( int position ) {
        return getQueue().valueAt( position );
    }

    public QueueEngram getCraftable( long id ) {
        return getQueue().get( id );
    }

    private void updateCraftable( int position, QueueEngram craftable ) {
        getQueue().setValueAt( position, craftable );
    }

    private void addCraftable( QueueEngram craftable ) {
        getQueue().put( craftable.getId(), craftable );
    }

    private void removeCraftable( int position ) {
        getQueue().removeAt( position );
    }

    private void removeAllCraftables() {
        getQueue().clear();
    }

    private int getPosition( long id ) {
        return getQueue().indexOfKey( id );
    }

    public void increaseQuantity( int position ) {
        QueueEngram craftable = getCraftable( position );

        craftable.increaseQuantity();

        // update item in list
        updateCraftable( position, craftable );

        // notify list adapter of changes
        mViewObserver.notifyItemChanged( position );

        // notify outside listeners of changes
        QueueObserver.getInstance().notifyItemChanged( craftable.getId(), craftable.getQuantity() );
    }

    public void increaseQuantity( QueueEngram craftable ) {
        long id = craftable.getId();

        craftable.increaseQuantity();

        if ( getQueue().contains( id ) ) {
            int position = getPosition( id );

            // update item in list
            updateCraftable( position, craftable );

            // notify list adapter of changes
            mViewObserver.notifyItemChanged( position );

            // notify outside listeners of changes
            QueueObserver.getInstance().notifyItemChanged( id, craftable.getQuantity() );
        } else {
            insert( craftable );
        }
    }

    public void setQuantity( Context context, long id, int quantity ) {
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
                mViewObserver.notifyItemChanged( position );

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
    }

    private void insert( Context context, long id, int quantity ) {
        try {
            insert( QueryForCraftable( context, id, quantity ) );
        } catch ( Exception e ) {
            // notify ViewSwitcher of error
            mViewObserver.notifyExceptionCaught( e );
        }
    }

    private void insert( QueueEngram craftable ) {
        // add craftable to list
        addCraftable( craftable );

        // sort list by name
        getQueue().sort();

        // retrieve position, after sorted
        int position = getPosition( craftable.getId() );

        // notify list adapter of changes
        mViewObserver.notifyItemInserted( position );

        // notify outside listeners of changes
        QueueObserver.getInstance().notifyItemChanged( craftable.getId(), craftable.getQuantity() );
    }

    private void delete( int position ) {
        // remove craftable from list
        removeCraftable( position );

        if ( getQueue().size() > 0 ) {
            // notify list adapter of changes
            mViewObserver.notifyItemRemoved( position );

            // notify outside listeners of changes
            QueueObserver.getInstance().notifyItemRemoved( getCraftable( position ).getId() );
        } else {
            // notify ViewSwitcher of empty status
            mViewObserver.notifyDataSetEmpty();

            // notify outside listeners of changes
            QueueObserver.getInstance().notifyDataSetEmpty();
        }
    }

    public void delete( long id ) {
        if ( getQueue().contains( id ) ) {
            delete( getPosition( id ) );
        }
    }

    public void clearQueue() {
        // clear list of values
        removeAllCraftables();

        // notify ViewSwitcher of empty status
        mViewObserver.notifyDataSetEmpty();

        // notify outside listeners of changes
        QueueObserver.getInstance().notifyDataSetEmpty();
    }

    public void saveQueueToPrefs() {
        PrefsUtil.getInstance().saveCraftingQueueJSONString( convertQueueToJSONString() );
    }

    private String convertQueueToJSONString() {
        try {
            JSONArray json = new JSONArray();

            for ( int i = 0; i < getQueue().size(); i++ ) {
                QueueEngram craftable = getCraftable( i );

                JSONObject object = new JSONObject();
                object.put( DatabaseContract.EngramEntry._ID, craftable.getId() );
                object.put( DatabaseContract.QueueEntry.COLUMN_QUANTITY, craftable.getQuantity() );

                json.put( object );
            }

            return json.toString();
        } catch ( JSONException e ) {
            return null;
        }
    }

    private LongSparseArray<Integer> convertJSONStringToQueue() {
        String jsonString = PrefsUtil.getInstance().getCraftingQueueJSONString();

        if ( jsonString == null )
            return new LongSparseArray<>( 0 );

        try {
            JSONArray json = new JSONArray( jsonString );

            LongSparseArray<Integer> convertedQueueArray = new LongSparseArray<>( json.length() );
            for ( int i = 0; i < json.length(); i++ ) {
                JSONObject object = json.getJSONObject( i );

                long engramId = object.getLong( DatabaseContract.EngramEntry._ID );
                int quantity = object.getInt( DatabaseContract.QueueEntry.COLUMN_QUANTITY );

                convertedQueueArray.put( engramId, quantity );
            }

            return convertedQueueArray;
        } catch ( JSONException e ) {
            return new LongSparseArray<>( 0 );
        }
    }

    private QueueEngram QueryForCraftable( Context context, long engramId, int quantity ) throws Exception {
        long dlc_id = PrefsUtil.getInstance().getDLCPreference();
        Uri uri = DatabaseContract.EngramEntry.buildUriWithId( dlc_id, engramId );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return new QueueEngram(
                    engramId,
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) ),
                    cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) ),
                    quantity );
        }
    }

    private void fetchData( Context context ) {
        new FetchDataTask( context ).execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Boolean> {
        private final String TAG = FetchDataTask.class.getSimpleName();

        private Context mContext;
        private QueueSparseArray mArray;

        FetchDataTask( Context context ) {
            this.mContext = context.getApplicationContext();
            this.mArray = new QueueSparseArray();
        }

        @Override
        protected void onPreExecute() {
            mViewObserver.notifyInitializing();
        }

        @Override
        protected void onPostExecute( Boolean querySuccessful ) {
            if ( querySuccessful ) {
                if ( mArray.size() > 0 ) {
                    mViewObserver.notifyDataSetPopulated();
                    QueueObserver.getInstance().notifyDataSetChanged();
                } else {
                    mViewObserver.notifyDataSetEmpty();
                }
            }
        }

        @Override
        protected Boolean doInBackground( Void... params ) {

            try {
                LongSparseArray<Integer> savedQueue = convertJSONStringToQueue();

                if ( savedQueue.size() > 0 ) {
                    QueueSparseArray queue = new QueueSparseArray();
                    for ( int i = 0; i < savedQueue.size(); i++ ) {
                        long id = savedQueue.keyAt( i );
                        int quantity = savedQueue.valueAt( i );

                        QueueEngram craftable = QueryForCraftable( mContext, id, quantity );

                        queue.put( id, craftable );
                    }

                    mArray = queue;
                }

                return true;
            } catch ( Exception e ) {
                mViewObserver.notifyExceptionCaught( e );

                return false;
            }

        }
    }

    private class QueueSparseArray extends LongSparseArray<QueueEngram> {

        /**
         * Constructor that, by default, will set its element size to 0.
         */
        QueueSparseArray() {
            super( 0 );
        }

        /**
         * Constructor that sets its element size per the supplied amount.
         *
         * @param size Amount of elements the array requires to allocate.
         */
        QueueSparseArray( int size ) {
            super( size );
        }

        /**
         * Sorts objects by Name
         */
        void sort() {
            boolean swapped = true;
            while ( swapped ) {
                swapped = false;
                for ( int i = 0; i < size() - 1; i++ ) {
                    String first = valueAt( i ).getName();
                    String second = valueAt( i + 1 ).getName();
                    if ( first.compareTo( second ) > 0 ) {
                        // swap
                        QueueEngram temp = valueAt( i + 1 );
                        setValueAt( i + 1, valueAt( i ) );
                        setValueAt( i, temp );
                        swapped = true;
                    }
                }
            }
        }

        boolean contains( long key ) {
            return !( indexOfKey( key ) < 0 );
        }
    }

}
