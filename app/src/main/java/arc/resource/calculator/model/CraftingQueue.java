package arc.resource.calculator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.QueueResource;
import arc.resource.calculator.util.Helper;
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

    private SparseArray<QueueResource> mResources;
    private SparseArray<QueueEngram> mEngrams;

    private Context mContext;

    private CraftingQueue( Context context ) {
        this.mContext = context;

        mResources = new SparseArray<>();
        mEngrams = new SparseArray<>();

        UpdateData();
    }

    public static CraftingQueue getInstance( Context context ) {
        if ( sInstance == null ) {
            sInstance = new CraftingQueue( context );
        }
        return sInstance;
    }

    // -- PUBLIC GETTER METHODS --

    public boolean hasComplexResources() {
        return new PrefsUtil( getContext() ).getRefinedFilterPreference();
    }

    public Context getContext() {
        return mContext;
    }

    // -- PUBLIC ENGRAM METHODS --

    public int getEngramItemCount() {
        return mEngrams.size();
    }

    public String getEngramDrawable( int position ) {
        return mEngrams.valueAt( position ).getDrawable();
    }

    public long getEngramId( int position ) {
        return mEngrams.valueAt( position ).getId();
    }

    public String getEngramName( int position ) {
        return mEngrams.valueAt( position ).getName();
    }

    public int getEngramQuantity( int position ) {
        return mEngrams.valueAt( position ).getQuantity();
    }

    public int getEngramQuantityWithYield( int position ) {
        QueueEngram engram = mEngrams.valueAt( position );

        int quantity = engram.getQuantity();
        int yield = engram.getYield();

        return quantity * yield;
    }

    // -- PUBLIC RESOURCE METHODS --

    public int getResourceItemCount() {
        return mResources.size();
    }

    public String getResourceDrawable( int position ) {
        return mResources.valueAt( position ).getDrawable();
    }

    public String getResourceName( int position ) {
        return mResources.valueAt( position ).getName();
    }

    public int getResourceQuantity( int position ) {
        QueueResource resource = mResources.valueAt( position );
        int quantity = resource.getQuantity();
        int queueQuantity = resource.getQueueQuantity();
        return queueQuantity;
    }

    // -- PUBLIC QUANTITY METHODS --

    public void increaseQuantity( int position ) {
        QueueEngram engram = mEngrams.valueAt( position );

        if ( engram != null ) {
            increaseQuantity( engram.getId() );
        }
    }

    public void increaseQuantity( long engramId ) {
        Queue queue = QueryByEngramId( engramId );

        int amount = 1;

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if ( queue == null ) {
            Insert( engramId, amount );
        } else {
            if ( queue.getQuantity() < Helper.MAX ) {
                queue.increaseQuantity( amount );
                Update( queue );
            }
        }
    }

    public void decreaseQuantity( int position ) {
        if ( position <= mEngrams.size() ) {
            QueueEngram engram = mEngrams.valueAt( position );

            decreaseQuantity( engram.getId() );
        }
    }

    public void decreaseQuantity( long engramId ) {
        Queue queue = QueryByEngramId( engramId );

        int amount = 1;

        // if queue is empty, add new queue into system
        // if queue exists, decrease quantity by amount, update system with new object
        if ( queue != null ) {
            queue.decreaseQuantity( amount );
            if ( queue.getQuantity() >= amount ) {
                Update( queue );
            } else {
                Remove( queue );
            }
        }
    }

    public void setQuantity( long engramId, int quantity ) {
        Queue queue = QueryByEngramId( engramId );

        // if queue is empty and quantity is above 0, add new queue into database
        if ( queue == null ) {
            if ( quantity > 0 ) {
                Insert( engramId, quantity );
            }
            return;
        }

        // if quantity is 0, remove queue from database
        if ( quantity == 0 ) {
            Remove( queue );
            return;
        }

        // if quantities are not equal, update existing queue to database
        if ( queue.getQuantity() != quantity && queue.getQuantity() <= Helper.MAX ) {
            queue.setQuantity( quantity );

            Update( queue );
        }
    }

    // -- PUBLIC DATABASE QUERY METHODS --

    public void Remove( Queue queue ) {
        if ( queue != null ) {
            getContext().getContentResolver().delete(
                    DatabaseContract.QueueEntry.CONTENT_URI,
                    DatabaseContract.QueueEntry.SQL_QUERY_WITH_ID,
                    new String[]{ Long.toString( queue.getId() ) } );

            UpdateData();
        }
    }

    public void Remove( long engramId ) {
        Remove( QueryByEngramId( engramId ) );
    }

    public void Update( Queue queue ) {
        ContentValues values = new ContentValues();
        values.put( DatabaseContract.QueueEntry.COLUMN_ENGRAM_KEY, queue.getEngramId() );
        values.put( DatabaseContract.QueueEntry.COLUMN_QUANTITY, queue.getQuantity() );

        getContext().getContentResolver().update(
                DatabaseContract.QueueEntry.CONTENT_URI,
                values,
                DatabaseContract.QueueEntry.SQL_QUERY_WITH_ID,
                new String[]{ Long.toString( queue.getId() ) } );

        UpdateData();
    }

    public void Insert( long engramId, int quantity ) {
        ContentValues values = new ContentValues();
        values.put( DatabaseContract.QueueEntry.COLUMN_ENGRAM_KEY, engramId );
        values.put( DatabaseContract.QueueEntry.COLUMN_QUANTITY, quantity );

        getContext().getContentResolver().insert( DatabaseContract.QueueEntry.CONTENT_URI, values );

        UpdateData();
    }

    public void Clear() {
        getContext().getContentResolver().delete( DatabaseContract.QueueEntry.CONTENT_URI, null, null );

        UpdateData();
    }

    private SparseArray<QueueEngram> QueryForEngrams() {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.QueueEntry.buildUriWithEngramTable( dlc_id ),
                null, null, null, null );

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        List<Long> engramIds = new ArrayList<>();
        SparseArray<QueueEngram> engrams = new SparseArray<>();
        while ( cursor.moveToNext() ) {
            QueueEngram engram = new QueueEngram(
                    cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) ),
                    cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) ),
                    cursor.getInt( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_QUANTITY ) )
            );

            if ( !engramIds.contains( engram.getId() ) ) {
                engrams.put( engrams.size(), engram );
                engramIds.add( engram.getId() );
            }

        }

        cursor.close();

        return engrams;
    }

    private SparseArray<QueueResource> QueryForEngramResources() {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        HashMap<Long, QueueResource> resourceMap = new HashMap<>();

        Cursor compositionCursor;
        for ( int i = 0; i < mEngrams.size(); i++ ) {
            QueueEngram engram = mEngrams.valueAt( i );

            compositionCursor = getContext().getContentResolver().query(
                    DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, engram.getId() ),
                    null, null, null, null );

            if ( compositionCursor == null ) {
                continue;
            }

            List<Long> resourceIds = new ArrayList<>();
            while ( compositionCursor.moveToNext() ) {
                long resourceId = compositionCursor.getLong( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int quantity = compositionCursor.getInt( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                if ( !resourceIds.contains( resourceId ) ) {
                    Cursor resourceCursor = getContext().getContentResolver().query(
                            DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, resourceId ),
                            null, null, null, null );

                    if ( resourceCursor == null ) {
                        continue;
                    }

                    if ( resourceCursor.moveToFirst() ) {
                        resourceIds.add( resourceId );

                        String name = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                        String drawable = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE ) );

                        int queueQuantity = engram.getQuantity() * quantity;

                        QueueResource queueResource = resourceMap.get( resourceId );
                        if ( queueResource != null ) {
                            queueResource.increaseQueueQuantity( queueQuantity );
                        } else {
                            queueResource = new QueueResource(
                                    resourceId,
                                    name,
                                    drawable,
                                    quantity,
                                    queueQuantity
                            );
                        }
                        resourceMap.put( resourceId, queueResource );
                    }
                    resourceCursor.close();
                }
            }
            compositionCursor.close();
        }

        SparseArray<QueueResource> resources = new SparseArray<>();
        for ( long resourceId : resourceMap.keySet() ) {
            QueueResource resource = resourceMap.get( resourceId );
            resources.put( resources.size(), resource );
        }

        return resources;
    }

    private SparseArray<QueueResource> getComplexResources() {
        HashMap<Long, QueueResource> resourceMap = new HashMap<>();

        for ( int i = 0; i < mEngrams.size(); i++ ) {
            QueueEngram engram = mEngrams.valueAt( i );

            SparseArray<CompositeResource> composition = QueryForComposition( engram.getId() );

            for ( int j = 0; j < composition.size(); j++ ) {
                CompositeResource compositeResource = composition.valueAt( j );

                Long engramId = QueryForComplexResource( compositeResource.getId() );
                if ( engramId != null ) {
                    SparseArray<CompositeResource> complexComposition = QueryForComposition( engramId );
                    for ( int k = 0; k < complexComposition.size(); k++ ) {
                        CompositeResource complexCompositeResource = complexComposition.valueAt( k );

                        int queueQuantity = compositeResource.getQuantity() * ( complexCompositeResource.getQuantity() * engram.getQuantity() );

                        QueueResource queueResource = resourceMap.get( complexCompositeResource.getId() );
                        if ( queueResource != null ) {
                            queueResource.increaseQueueQuantity( queueQuantity );
                        } else {
                            queueResource = new QueueResource(
                                    complexCompositeResource.getId(),
                                    complexCompositeResource.getName(),
                                    complexCompositeResource.getDrawable(),
                                    complexCompositeResource.getQuantity(),
                                    queueQuantity
                            );
                        }

                        resourceMap.put( queueResource.getId(), queueResource );
                    }
                } else {
                    int queueQuantity = compositeResource.getQuantity() * engram.getQuantity();

                    QueueResource queueResource = resourceMap.get( compositeResource.getId() );
                    if ( queueResource != null ) {
                        queueResource.increaseQueueQuantity( queueQuantity );
                    } else {
                        queueResource = new QueueResource(
                                compositeResource.getId(),
                                compositeResource.getName(),
                                compositeResource.getDrawable(),
                                compositeResource.getQuantity(),
                                queueQuantity
                        );
                    }

                    resourceMap.put( queueResource.getId(), queueResource );
                }
            }
        }

        SparseArray<QueueResource> resources = new SparseArray<>();
        for ( Long resource_id : resourceMap.keySet() ) {
            resources.put( resources.size(), resourceMap.get( resource_id ) );
        }

        return resources;
    }

    private SparseArray<CompositeResource> QueryForComposition( long engramId ) {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        Cursor compositionCursor = getContext().getContentResolver().query(
                DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, engramId ),
                null, null, null, null );

        if ( compositionCursor == null ) {
            return new SparseArray<>();
        }

        List<Long> resourceIds = new ArrayList<>();
        HashMap<Long, CompositeResource> resourceMap = new HashMap<>();
        while ( compositionCursor.moveToNext() ) {
            long resourceId = compositionCursor.getLong( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
            int quantity = compositionCursor.getInt( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

            if ( !resourceIds.contains( resourceId ) ) {
                resourceIds.add( resourceId );

                Cursor resourceCursor = getContext().getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, resourceId ),
                        null, null, null, null );

                if ( resourceCursor == null ) {
                    continue;
                }

                if ( resourceCursor.moveToFirst() ) {
                    String name = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                    String drawable = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE ) );

                    CompositeResource resource = resourceMap.get( resourceId );
                    if ( resource != null ) {
                        resource.increaseQuantity( quantity );
                    } else {
                        resource = new CompositeResource(
                                resourceId,
                                name,
                                drawable,
                                quantity
                        );
                    }
                    resourceMap.put( resourceId, resource );
                }

                resourceCursor.close();
            }
        }
        compositionCursor.close();

        SparseArray<CompositeResource> resources = new SparseArray<>();
        for ( Long resource_id : resourceMap.keySet() ) {
            resources.put( resources.size(), resourceMap.get( resource_id ) );
        }

        return resources;
    }

    private Queue QueryByEngramId( long engramId ) {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.QueueEntry.buildUriWithEngramId( engramId ),
                null, null, null, null );

        if ( cursor == null ) {
            return null;
        }

        if ( cursor.moveToFirst() ) {
            Queue queue = new Queue(
                    cursor.getLong( cursor.getColumnIndex( DatabaseContract.QueueEntry._ID ) ),
                    cursor.getLong( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_ENGRAM_KEY ) ),
                    cursor.getInt( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_QUANTITY ) )
            );

            cursor.close();
            return queue;
        }

        return null;
    }

    private Long QueryForEngramIdByDrawable( String drawable, long dlc_id ) {
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.EngramEntry.buildUriWithDrawable( drawable, dlc_id ),
                null, null, null, null );

        if ( cursor == null ) {
            return null;
        }

        if ( cursor.moveToFirst() ) {
            Long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) );

            cursor.close();
            return _id;
        }

        return null;
    }

    private HashMap<Long, Long> QueryForComplexResources() {
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.ComplexResourceEntry.CONTENT_URI,
                null, null, null, null );

        if ( cursor == null || cursor.getCount() <= 0 ) {
            Log.e( TAG, "QueryForComplexResources() returned null?" );
            return null;
        }

        HashMap<Long, Long> idMap = new HashMap<>();
        while ( cursor.moveToNext() ) {
            long engram_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
            long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

            idMap.put( resource_id, engram_id );
        }

        cursor.close();

        return idMap;
    }

    private Long QueryForComplexResource( long resource_id ) {
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.ComplexResourceEntry.buildUriWithResourceId( resource_id ),
                null, null, null, null );

        if ( cursor != null && cursor.moveToFirst() ) {
            long engram_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );

            cursor.close();
            return engram_id;
        }

        return null;
    }

    // -- PRIVATE UTILITY METHODS --

    private SparseArray<QueueEngram> getEngrams() {
        return QueryForEngrams();
    }

    private SparseArray<QueueResource> getResources() {
        SparseArray<QueueResource> resources;

        Log.e( TAG, String.valueOf( hasComplexResources() ) );

        if ( hasComplexResources() ) {
            resources = getComplexResources();
        } else {
            resources = QueryForEngramResources();
        }

        return Helper.sortResourcesByName( resources );
    }

    private void UpdateData() {
        mEngrams = getEngrams();
        UpdateResources();
    }

    public void UpdateResources() {
        mResources = getResources();
    }
}
