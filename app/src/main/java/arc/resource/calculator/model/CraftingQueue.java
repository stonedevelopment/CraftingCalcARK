package arc.resource.calculator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.listeners.CraftingQueueListener;
import arc.resource.calculator.listeners.QueueEngramListener;
import arc.resource.calculator.listeners.QueueResourceListener;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.Resource;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.ListenerUtil;
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
public class CraftingQueue
        implements CraftingQueueListener, QueueEngramListener, QueueResourceListener {
    private static final String TAG = CraftingQueue.class.getSimpleName();

    private final int INVALID_ITEM_POSITION = -1;
    private final int INVALID_ITEM_ID = -1;

    private SparseArray<CompositeResource> mResources;
    private LongSparseArray<QueueEngram> mEngrams;

    private static CraftingQueue sInstance;

    private ListenerUtil mCallback;

    public static CraftingQueue getInstance( Context context ) {
        if ( sInstance == null )
            sInstance = new CraftingQueue( context );

        return sInstance;
    }

    private CraftingQueue( Context context ) {
        mResources = new SparseArray<>();
        mEngrams = new LongSparseArray<>();

        mCallback = ListenerUtil.getInstance();
        mCallback.addCraftingQueueListener( this );
        mCallback.addCraftingQueueEngramListener( this );
        mCallback.addCraftingQueueResourceListener( this );

        mCallback.requestQueueEngramDataSetChange( context );
    }

    // -- PUBLIC GETTER METHODS --

    private boolean hasComplexResources( Context context ) {
        return new PrefsUtil( context ).getRefinedFilterPreference();
    }

    // -- PUBLIC ENGRAM METHODS --

    public int getPositionByEngramId( long _id ) {
        return mEngrams.indexOfKey( _id );
    }

    public QueueEngram getEngramByPosition( int position ) {
        try {
            QueueEngram engram = mEngrams.valueAt( position );

            if ( engram == null )
                throw new ExceptionUtil.ArrayElementNullException( position, mEngrams.toString() );

            return engram;
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
            return null;
        }
    }

    public int getEngramItemCount() {
        return mEngrams.size();
    }

    // -- PUBLIC RESOURCE METHODS --

    public CompositeResource getResourceByPosition( int position ) {
        try {
            CompositeResource resource = mResources.valueAt( position );

            if ( resource == null )
                throw new ExceptionUtil.ArrayElementNullException( position, mResources.toString() );

            return resource;
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
            return null;
        }
    }

    public int getResourceItemCount() {
        return mResources.size();
    }

    // -- PUBLIC DATABASE METHODS --

    private void increaseQuantity( Context context, int position ) {
        QueueEngram engram = getEngramByPosition( position );
        long _id = engram.getId();
        int quantity = engram.getQuantity() + 1;

        updateQueueById( context, _id, quantity );
    }

    private void increaseQuantity( Context context, long engramId ) {
        if ( mEngrams.indexOfKey( engramId ) < 0 ) {
            insertQueueById( context, engramId );
        } else {
            int quantity = mEngrams.get( engramId ).getQuantity() + 1;
            updateQueueById( context, engramId, quantity );
        }
    }

    private void setQuantity( final Context context, long engramId, int quantity ) {
        if ( quantity > 0 )
            updateQueueById( context, engramId, quantity );
        else
            removeQueueById( context, engramId );
    }

    private void insertQueueById( Context context, long engramId ) {
        new InsertRowTask( context, engramId, 1 ).executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
    }

    private void insertQueueById( Context context, long engramId, int quantity ) {
        new InsertRowTask( context, engramId, quantity ).executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
    }

    private void updateQueueById( Context context, long engramId, int quantity ) {
        new UpdateRowTask( context, engramId, quantity ).executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
    }

    private void clearQueue( Context context ) {
        new DeleteRowTask( context, QueueEntry.CONTENT_URI ).executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
    }

    private void removeQueueById( Context context, long engramId ) {
        new DeleteRowTask( context, QueueEntry.buildUriWithEngramId( engramId ) ).executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
    }

    // -- PRIVATE DATABASE METHODS --

    private int Delete( Context context, Uri uri ) {
        int rowsDeleted = context.getContentResolver().delete( uri, null, null );

        if ( rowsDeleted > 0 ) {
            if ( uri == QueueEntry.CONTENT_URI ) {
                mEngrams = new LongSparseArray<>();
            } else {
                long engram_id = QueueEntry.getEngramIdFromUri( uri );
                mEngrams.remove( engram_id );
            }
        }

        return rowsDeleted;
    }

    private int Update( Context context, Queue queue ) {
        Log.d( TAG, "Update(): " + queue.toString() );

        long engram_id = queue.getEngramId();
        int quantity = queue.getQuantity();

        ContentValues values = new ContentValues();
        values.put( QueueEntry.COLUMN_ENGRAM_KEY, engram_id );
        values.put( QueueEntry.COLUMN_QUANTITY, quantity );

        int rowsUpdated = context.getContentResolver().update(
                QueueEntry.CONTENT_URI,
                values,
                QueueEntry.SQL_QUERY_WITH_ID,
                new String[]{ Long.toString( queue.getId() ) } );

        if ( rowsUpdated > 0 ) {
            QueueEngram engram = mEngrams.get( engram_id );
            engram.setQuantity( quantity );

            mEngrams.put( engram_id, engram );
        } else {
            Log.e( TAG, addSpaces( 2 ) + ", 0 rowsUpdated" );
        }

//        Log.d( TAG, addSpaces( 2 ) + mEngrams );
//        Log.d( TAG, addSpaces( 2 ) + QueryForQueues( context ).toString() );

        return rowsUpdated;
    }

    private Uri Insert( Context context, long engram_id, int quantity ) {
        Log.d( TAG, "Insert(): " + engram_id + ", " + quantity );

        ContentValues values = new ContentValues();
        values.put( QueueEntry.COLUMN_ENGRAM_KEY, engram_id );
        values.put( QueueEntry.COLUMN_QUANTITY, quantity );

        Uri uri = context.getContentResolver().insert( QueueEntry.CONTENT_URI, values );

        if ( uri == null )
            return null;

//        Log.d( TAG, addSpaces( 2 ) + uri.toString() );

        QueueEngram engram = QueryForEngram( context, engram_id, quantity );

        if ( engram == null )
            return null;

        engram.setQuantity( quantity );
        mEngrams.put( engram_id, engram );

        return uri;
    }

    private QueueEngram QueryForEngram( Context context, long engram_id, int quantity ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();
        Uri uri = EngramEntry.buildUriWithId( dlc_id, engram_id );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return new QueueEngram(
                    engram_id,
                    cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) ),
                    cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FOLDER ) ),
                    cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FILE ) ),
                    cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) ),
                    quantity );
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );

            return null;
        }
    }

    private LongSparseArray<QueueEngram> QueryForEngrams( Context context ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();
        Uri uri = QueueEntry.buildUriWithEngramTable( dlc_id );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            LongSparseArray<QueueEngram> engrams = new LongSparseArray<>();
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( EngramEntry._ID ) );

                if ( engrams.indexOfKey( _id ) < 0 )
                    engrams.put( _id,
                            new QueueEngram(
                                    _id,
                                    cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) ),
                                    cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FOLDER ) ),
                                    cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FILE ) ),
                                    cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) ),
                                    cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) ) ) );
            }

            return engrams;
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );

            return new LongSparseArray<>();
        }
    }

    private Resource QueryForResource( Context context, Uri uri ) throws Exception {
        try ( Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            long _id = cursor.getLong( cursor.getColumnIndex( ResourceEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_NAME ) );
            String folder = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_IMAGE_FILE ) );

            return new Resource( _id, name, folder, file );
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );

            return null;
        }
    }

    private SparseArray<CompositeResource> QueryForAllResources( Context context ) {
        Cursor cursor = context.getContentResolver().query(
                ResourceEntry.CONTENT_URI, null, null, null, null );

        if ( cursor != null ) {
            try {
                SparseArray<CompositeResource> resources = new SparseArray<>();
                while ( cursor.moveToNext() ) {
                    long _id = cursor.getLong( cursor.getColumnIndex( ResourceEntry._ID ) );
                    String name = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_NAME ) );
                    String folder = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_IMAGE_FOLDER ) );
                    String file = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_IMAGE_FILE ) );

                    resources.put( resources.size(), new CompositeResource( _id, name, folder, file, 1 ) );
                }

                return resources;
            } catch ( Exception e ) {
                mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
            } finally {
                cursor.close();
            }
        }

        return new SparseArray<>();
    }

    // Query for compositions, calculate composition quantities with queue quantities
    private SparseArray<CompositeResource> QueryForResources( Context context ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();

        int level = 0;

        LongSparseArray<CompositeResource> resourceMap = new LongSparseArray<>();
        for ( int i = 0; i < mEngrams.size(); i++ ) {
            QueueEngram engram = mEngrams.valueAt( i );

            SparseArray<CompositeResource> tempResources =
                    QueryForComposition(
                            context,
                            CompositionEntry.buildUriWithEngramId( dlc_id, engram.getId() ), level );

            for ( int j = 0; j < tempResources.size(); j++ ) {
                CompositeResource tempResource = tempResources.get( j );

                CompositeResource resource = resourceMap.get( tempResource.getId() );
                if ( resource == null )
                    resource = new CompositeResource(
                            tempResource.getId(),
                            tempResource.getName(),
                            tempResource.getFolder(),
                            tempResource.getFile(),
                            tempResource.getQuantity() * engram.getQuantity() );
                else
                    resource.increaseQuantity( tempResource.getQuantity() * engram.getQuantity() );

                resourceMap.put( resource.getId(), resource );
            }
        }

        SparseArray<CompositeResource> resources = new SparseArray<>();
        for ( int i = 0; i < resourceMap.size(); i++ )
            resources.put( i, resourceMap.valueAt( i ) );

        return resources;
    }

    private SparseArray<CompositeResource> QueryForComposition( Context context, Uri uri, int level ) {
        long dlc_id = CompositionEntry.getDLCIdFromUri( uri );

        level++;

        try ( Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            LongSparseArray<CompositeResource> resourceMap = new LongSparseArray<>();
            while ( cursor.moveToNext() ) {
                long resource_id = cursor.getLong( cursor.getColumnIndex( CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int quantity = cursor.getInt( cursor.getColumnIndex( CompositionEntry.COLUMN_QUANTITY ) );

                long engram_id = INVALID_ITEM_ID;
                if ( hasComplexResources( context ) )
                    engram_id = QueryForComplexResource(
                            context,
                            ComplexResourceEntry.buildUriWithResourceId( resource_id ) );

                if ( engram_id != INVALID_ITEM_ID ) {
                    SparseArray<CompositeResource> tempResources =
                            QueryForComposition(
                                    context,
                                    CompositionEntry.buildUriWithEngramId( dlc_id, engram_id ), level );

                    for ( int i = 0; i < tempResources.size(); i++ ) {
                        CompositeResource tempResource = tempResources.get( i );

                        CompositeResource resource = resourceMap.get( tempResource.getId() );
                        if ( resource == null )
                            resource = new CompositeResource(
                                    tempResource.getId(),
                                    tempResource.getName(),
                                    tempResource.getFolder(),
                                    tempResource.getFile(),
                                    tempResource.getQuantity() * quantity );

                        resourceMap.put( resource.getId(), resource );
                    }
                } else {
                    Resource tempResource = QueryForResource(
                            context,
                            ResourceEntry.buildUriWithId( dlc_id, resource_id ) );

                    if ( tempResource == null )
                        continue;

                    CompositeResource resource = resourceMap.get( resource_id );
                    if ( resource == null )
                        resource = new CompositeResource(
                                tempResource.getId(),
                                tempResource.getName(),
                                tempResource.getFolder(),
                                tempResource.getFile(),
                                quantity );

                    resourceMap.put( resource_id, resource );
                }
            }

            SparseArray<CompositeResource> resources = new SparseArray<>();
            for ( int i = 0; i < resourceMap.size(); i++ )
                resources.put( i, resourceMap.valueAt( i ) );

            return resources;
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );

            return new SparseArray<>();
        }
    }

    private Queue QueryForQueue( Context context, Uri uri ) {
        try ( Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return new Queue(
                    cursor.getLong( cursor.getColumnIndex( QueueEntry._ID ) ),
                    cursor.getLong( cursor.getColumnIndex( QueueEntry.COLUMN_ENGRAM_KEY ) ),
                    cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) ) );
        } catch ( Exception e ) {
            return null;
        }
    }

    private long QueryForComplexResource( Context context, Uri uri ) {
        try ( Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return cursor.getLong( cursor.getColumnIndex( ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
        } catch ( Exception e ) {
            return INVALID_ITEM_ID;
        }
    }

    // -- PRIVATE UTILITY METHODS --

    private static SparseArray<CompositeResource> sortResourcesByName( SparseArray<CompositeResource> resources ) {
        boolean swapped = true;
        while ( swapped ) {

            swapped = false;
            for ( int i = 0; i < resources.size() - 1; i++ ) {
                String first = resources.valueAt( i ).getName();
                String second = resources.valueAt( i + 1 ).getName();
                if ( first.compareTo( second ) > 0 ) {
                    // swap
                    CompositeResource tempResource = resources.valueAt( i + 1 );
                    resources.put( i + 1, resources.valueAt( i ) );
                    resources.put( i, tempResource );
                    swapped = true;
                }
            }
        }

        return resources;
    }

    private String addSpaces( int n ) {
        return new String( new char[n * 2] ).replace( "\0", " " );
    }

    @Override
    public void onEngramDataSetChangeRequest( Context context ) {
        new QueryForEngramsTask( context ).executeOnExecutor( AsyncTask.SERIAL_EXECUTOR );
    }

    @Override
    public void onEngramDataSetChanged( boolean isQueueEmpty ) {
        // do nothing
    }

    @Override
    public void onResourceDataSetChangeRequest( Context context ) {
        new QueryForResourcesTask( context ).executeOnExecutor( AsyncTask.SERIAL_EXECUTOR );
    }

    @Override
    public void onResourceDataSetChanged() {
        // do nothing
    }

    @Override
    public void onRequestRemoveOneFromQueue( Context context, long engram_id ) {
        removeQueueById( context, engram_id );
    }

    @Override
    public void onRequestRemoveAllFromQueue( Context context ) {
        clearQueue( context );
    }

    @Override
    public void onRequestIncreaseQuantity( Context context, int position ) {
        increaseQuantity( context, position );
    }

    @Override
    public void onRequestIncreaseQuantity( Context context, long engram_id ) {
        increaseQuantity( context, engram_id );
    }

    @Override
    public void onRequestUpdateQuantity( Context context, long engram_id, int quantity ) {
        setQuantity( context, engram_id, quantity );
    }

    @Override
    public void onRowInserted( Context context, long queue_id, long engram_id, int quantity, boolean wasQueueEmpty ) {

    }

    @Override
    public void onRowUpdated( Context context, long queue_id, long engram_id, int quantity ) {

    }

    @Override
    public void onRowDeleted( Context context, Uri uri, int positionStart, int itemCount, boolean isQueueEmpty ) {

    }

    private class UpdateRowTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = UpdateRowTask.class.getSimpleName();

        private Context mContext;

        private long mId;
        private long mEngramId;
        private int mQuantity;

        private boolean mWasUpdated;

        UpdateRowTask( Context context, long engram_id, int quantity ) {
            mContext = context;
            mEngramId = engram_id;
            mQuantity = quantity;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            if ( mWasUpdated )
                mCallback.notifyRowUpdated( mContext, mId, mEngramId, mQuantity );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            Queue queue = QueryForQueue( mContext, QueueEntry.buildUriWithEngramId( mEngramId ) );

            if ( queue != null ) {
                queue.setQuantity( mQuantity );
                mId = queue.getId();
                Update( mContext, queue );

                mWasUpdated = true;
            } else {
                insertQueueById( mContext, mEngramId, mQuantity );

                mWasUpdated = false;
            }

            return null;
        }
    }

    private class InsertRowTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = InsertRowTask.class.getSimpleName();

        private Context mContext;

        private long mId;
        private long mEngramId;
        private int mQuantity;

        private boolean mIsQueueEmpty;

        InsertRowTask( Context context, long engram_id, int quantity ) {
            mContext = context;
            mEngramId = engram_id;
            mQuantity = quantity;

            mIsQueueEmpty = getEngramItemCount() == 0;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            if ( mId != INVALID_ITEM_ID )
                mCallback.notifyRowInserted( mContext, mId, mEngramId, mQuantity, mIsQueueEmpty );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            Uri uri = Insert( mContext, mEngramId, mQuantity );

            if ( uri == null )
                mId = INVALID_ITEM_ID;
            else
                mId = DatabaseContract.getIdFromUri( uri );

            return null;
        }
    }

    private class DeleteRowTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = DeleteRowTask.class.getSimpleName();

        private Context mContext;
        private Uri mUri;

        private int mPositionStart;
        private int mItemCount;

        private boolean mIsQueueEmpty;

        DeleteRowTask( Context context, Uri uri ) {
            mContext = context;
            mUri = uri;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            if ( mItemCount > 0 )
                mCallback.notifyRowDeleted( mContext, mUri, mPositionStart, mItemCount, mIsQueueEmpty );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            int size = mEngrams.size();

            if ( mUri == QueueEntry.CONTENT_URI )
                mPositionStart = 0;
            else
                mPositionStart = getPositionByEngramId(
                        DatabaseContract.QueueEntry.getEngramIdFromUri( mUri ) );

            mItemCount = Delete( mContext, mUri );

            mIsQueueEmpty = mEngrams.size() == 0;

            Log.d( TAG, mPositionStart + ", " + mItemCount + ", " + mIsQueueEmpty + ", engrams: " + mEngrams );
            return null;
        }
    }

    private class QueryForEngramsTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = QueryForEngramsTask.class.getSimpleName();

        Context mContext;

        boolean mIsQueueEmpty;

        QueryForEngramsTask( Context context ) {
            mContext = context;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            mCallback.notifyQueueEngramDataSetChanged( mIsQueueEmpty );
            mCallback.requestResourceDataSetChange( mContext );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            mIsQueueEmpty = mEngrams.size() == 0;
            mEngrams = QueryForEngrams( mContext );
            return null;
        }
    }

    private class QueryForResourcesTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = QueryForResourcesTask.class.getSimpleName();

        Context mContext;

        QueryForResourcesTask( Context context ) {
            mContext = context;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            mCallback.notifyResourceDataSetChanged();
        }

        @Override
        protected Void doInBackground( Void... params ) {
            mResources = sortResourcesByName( QueryForResources( mContext ) );
            return null;
        }
    }
}
