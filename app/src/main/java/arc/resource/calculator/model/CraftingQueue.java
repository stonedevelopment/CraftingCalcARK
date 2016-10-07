package arc.resource.calculator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.helpers.PreferenceHelper;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.QueueResource;

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

    public static final String STRING_KEY_CRAFTING_QUEUE_HASCOMPLEXRESOURCES = "CRAFTING_QUEUE_HASCOMPLEXRESOURCES";

    private static CraftingQueue sInstance;

    private boolean hasComplexResources;

    private SparseArray<QueueResource> mResources;
    private SparseArray<QueueEngram> mEngrams;
    private HashMap<Long, Long> mComplexResources;

    private Context mContext;

    private CraftingQueue( Context context ) {
        this.mContext = context;
        this.hasComplexResources = new PreferenceHelper( context ).getBooleanPreference( STRING_KEY_CRAFTING_QUEUE_HASCOMPLEXRESOURCES, false );

        if ( hasComplexResources ) {
            mComplexResources = QueryForComplexResources();
        } else {
            mComplexResources = new HashMap<>();
        }

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
        return hasComplexResources;
    }

    public Context getContext() {
        return mContext;
    }

    // -- PUBLIC SETTER METHODS --

    public void setHasComplexResources( boolean hasComplexResources ) {
        if ( this.hasComplexResources != hasComplexResources ) {
            this.hasComplexResources = hasComplexResources;

            new PreferenceHelper( getContext() ).setPreference( STRING_KEY_CRAFTING_QUEUE_HASCOMPLEXRESOURCES, hasComplexResources );

            if ( hasComplexResources ) {
                mComplexResources = QueryForComplexResources();
            } else {
                mComplexResources = new HashMap<>();
            }

            UpdateData();
        }
    }

    // -- PUBLIC ENGRAM METHODS --

    public int getEngramItemCount() {
        return mEngrams.size();
    }

    public String getEngramDrawable( int position ) {
        return mEngrams.valueAt( position ).getDrawable();
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
        return quantity * queueQuantity;
    }

    // -- PUBLIC QUANTITY METHODS --

    public void increaseQuantity( int position ) {
        QueueEngram engram = mEngrams.valueAt( position );

        increaseQuantity( engram.getId(), engram.getYield() );
    }

    public void increaseQuantity( long engramId, int amount ) {
        Queue queue = QueryByEngramId( engramId );

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if ( queue == null ) {
            Insert( engramId, amount );
        } else {
            if ( queue.getQuantity() < ( Helper.MAX + amount ) ) {
                queue.increaseQuantity( amount );
                Update( queue );
            }
        }
    }

    public void decreaseQuantity( int position ) {
        if ( position <= mEngrams.size() ) {
            QueueEngram engram = mEngrams.valueAt( position );

            decreaseQuantity( engram.getId(), engram.getYield() );
        }
    }

    public void decreaseQuantity( long engramId, int amount ) {
        Queue queue = QueryByEngramId( engramId );

        // if queue is empty, add new queue into system
        // if queue exists, decrease quantity by amount, update system with new object
        if ( queue != null ) {
            if ( amount > 0 ) {
                queue.decreaseQuantity( amount );
                if ( queue.getQuantity() >= amount ) {
                    Update( queue );
                } else {
                    Remove( queue );
                }
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
        getContext().getContentResolver().delete(
                DatabaseContract.QueueEntry.CONTENT_URI,
                DatabaseContract.QueueEntry.SQL_QUERY_WITH_ID,
                new String[]{ Long.toString( queue.getId() ) } );

        UpdateData();
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
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.QueueEntry.buildUriWithEngramTable(),
                null, null, null, null );

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        SparseArray<QueueEngram> engrams = new SparseArray<>();
        while ( cursor.moveToNext() ) {
            engrams.put( engrams.size(),
                    new QueueEngram(
                            cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) ),
                            cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                            cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) ),
                            cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) ),
                            cursor.getInt( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_QUANTITY ) )
                    ) );
        }

        cursor.close();

        return engrams;
    }

    private SparseArray<QueueResource> QueryForEngramResources() {
        HashMap<Long, QueueResource> resourceMap = new HashMap<>();

        Cursor compositionCursor;
        for ( int i = 0; i < mEngrams.size(); i++ ) {
            QueueEngram engram = mEngrams.valueAt( i );

            compositionCursor = getContext().getContentResolver().query(
                    DatabaseContract.CompositionEntry.buildUriWithEngramId( engram.getId() ),
                    null, null, null, null );

            if ( compositionCursor == null ) {
                continue;
            }

            while ( compositionCursor.moveToNext() ) {
                long resourceId = compositionCursor.getLong( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int quantity = compositionCursor.getInt( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                Cursor resourceCursor = getContext().getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithId( resourceId ),
                        null, null, null, null );

                if ( resourceCursor == null ) {
                    continue;
                }

                if ( resourceCursor.moveToFirst() ) {
                    String name = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                    String drawable = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE ) );

                    QueueResource queueResource = resourceMap.get( resourceId );
                    if ( queueResource != null ) {
                        queueResource.increaseQuantity( quantity );
                        queueResource.increaseQueueQuantity( engram.getQuantity() );
                    } else {
                        queueResource = new QueueResource(
                                resourceId,
                                name,
                                drawable,
                                quantity,
                                engram.getQuantity()
                        );
                    }
                    resourceMap.put( resourceId, queueResource );
                }
                resourceCursor.close();
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

    private SparseArray<CompositeResource> QueryForComposition( long engramId ) {
        Cursor compositionCursor = getContext().getContentResolver().query(
                DatabaseContract.CompositionEntry.buildUriWithEngramId( engramId ),
                null, null, null, null );

        if ( compositionCursor == null ) {
            return new SparseArray<>();
        }

        HashMap<Long, CompositeResource> resourceMap = new HashMap<>();
        while ( compositionCursor.moveToNext() ) {
            long resourceId = compositionCursor.getLong( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
            int quantity = compositionCursor.getInt( compositionCursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

            Cursor resourceCursor = getContext().getContentResolver().query(
                    DatabaseContract.ResourceEntry.buildUriWithId( resourceId ),
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
        compositionCursor.close();

        SparseArray<CompositeResource> resources = new SparseArray<>();
        for ( long resourceId : resourceMap.keySet() ) {
            CompositeResource resource = resourceMap.get( resourceId );
            resources.put( resources.size(), resource );
        }

        return resources;
    }

    private Queue QueryByEngramId( long engramId ) {
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
            long engramId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
            long resourceId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

            idMap.put( resourceId, engramId );
        }

        cursor.close();

        return idMap;
    }

    // -- PRIVATE UTILITY METHODS --

    private SparseArray<QueueEngram> getEngrams() {
        return QueryForEngrams();
    }

    private SparseArray<QueueResource> getResources() {
        SparseArray<QueueResource> resources;

        if ( !hasComplexResources ) {
            resources = QueryForEngramResources();
        } else {
            resources = getComplexResources();
        }

        return Helper.sortResourcesByName( resources );
    }

    private SparseArray<QueueResource> getComplexResources() {
        HashMap<Long, QueueResource> resourceMap = new HashMap<>();

        for ( int i = 0; i < mEngrams.size(); i++ ) {
            QueueEngram engram = mEngrams.valueAt( i );

            SparseArray<CompositeResource> composition = QueryForComposition( engram.getId() );

            for ( int j = 0; j < composition.size(); j++ ) {
                CompositeResource compositeResource = composition.valueAt( j );

                QueueResource queueResource;
                if ( mComplexResources.containsKey( compositeResource.getId() ) ) {
                    long engramId = mComplexResources.get( compositeResource.getId() );

                    SparseArray<CompositeResource> complexComposition = QueryForComposition( engramId );
                    for ( int k = 0; k < complexComposition.size(); k++ ) {
                        CompositeResource complexCompositeResource = complexComposition.valueAt( k );

                        queueResource = resourceMap.get( complexCompositeResource.getId() );
                        if ( queueResource != null ) {
                            queueResource.increaseQuantity( complexCompositeResource.getQuantity() );
                            queueResource.increaseQueueQuantity( compositeResource.getQuantity() * engram.getQuantity() );
                        } else {
                            queueResource = new QueueResource(
                                    complexCompositeResource.getId(),
                                    complexCompositeResource.getName(),
                                    complexCompositeResource.getDrawable(),
                                    complexCompositeResource.getQuantity(),
                                    compositeResource.getQuantity() * engram.getQuantity()
                            );
                        }

                        resourceMap.put( queueResource.getId(), queueResource );
                    }
                } else {
                    queueResource = resourceMap.get( compositeResource.getId() );
                    if ( queueResource != null ) {
                        queueResource.increaseQuantity( compositeResource.getQuantity() );
                        queueResource.increaseQueueQuantity( engram.getQuantity() );
                    } else {
                        queueResource = new QueueResource(
                                compositeResource.getId(),
                                compositeResource.getName(),
                                compositeResource.getDrawable(),
                                compositeResource.getQuantity(),
                                engram.getQuantity()
                        );
                    }

                    resourceMap.put( queueResource.getId(), queueResource );
                }
            }
        }

        SparseArray<QueueResource> resources = new SparseArray<>();
        for ( long resourceId : resourceMap.keySet() ) {
            QueueResource queueResource = resourceMap.get( resourceId );
            resources.put( resources.size(), queueResource );
        }

        return resources;
    }

    private void UpdateData() {
        mEngrams = getEngrams();
        mResources = getResources();
    }
}
