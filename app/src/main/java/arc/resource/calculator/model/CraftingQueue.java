package arc.resource.calculator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.LongSparseArray;
import android.util.SparseArray;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.Resource;
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

    private static SparseArray<CompositeResource> mResources;
    private LongSparseArray<QueueEngram> mEngrams;

    private static CraftingQueue sInstance;

    public static CraftingQueue getInstance( Context context ) throws Exception {
        if ( sInstance == null )
            sInstance = new CraftingQueue( context );

        return sInstance;
    }

    private CraftingQueue( Context context ) throws Exception {
        mResources = new SparseArray<>();
        mEngrams = new LongSparseArray<>();

        UpdateData( context );
    }

    // -- PUBLIC GETTER METHODS --

    private boolean hasComplexResources( Context context ) {
        return new PrefsUtil( context ).getRefinedFilterPreference();
    }

    // -- PUBLIC ENGRAM METHODS --

    public QueueEngram getEngram( int position ) throws Exception {
        QueueEngram engram = mEngrams.valueAt( position );

        if ( engram == null )
            throw new ExceptionUtil.ArrayElementNullException( position, mEngrams.toString() );

        return engram;
    }

    public int getEngramItemCount() {
        return mEngrams.size();
    }

    public int getEngramQuantityWithYield( int position ) throws Exception {
        QueueEngram engram = getEngram( position );

        int quantity = engram.getQuantity();
        int yield = engram.getYield();

        return quantity * yield;
    }

    // -- PUBLIC RESOURCE METHODS --

    public CompositeResource getResource( int position ) throws Exception {
        CompositeResource resource = mResources.valueAt( position );

        if ( resource == null )
            throw new ExceptionUtil.ArrayElementNullException( position, mResources.toString() );

        return resource;
    }

    public int getResourceItemCount() {
        return mResources.size();
    }

    // -- PUBLIC QUANTITY METHODS --

    public void increaseQuantity( Context context, int position ) throws Exception {
        increaseQuantity( context, getEngram( position ).getId() );
    }

    public void increaseQuantity( Context context, long engramId ) throws Exception {
        final int AMOUNT = 1;

        Queue queue = QueryForQueue(
                context,
                QueueEntry.buildUriWithEngramId( engramId ) );

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if ( queue == null ) {
            Insert( context, engramId, AMOUNT );
        } else {
            queue.increaseQuantity( AMOUNT );
            Update( context, queue );
        }
    }

    public void setQuantity( Context context, long engramId, int quantity ) throws Exception {
        Queue queue = QueryForQueue(
                context,
                QueueEntry.buildUriWithEngramId( engramId ) );

        // if queue is empty and quantity is above 0, add new queue into database
        if ( queue == null ) {
            if ( quantity > 0 )
                Insert( context, engramId, quantity );
            return;
        }

        // if quantity is 0, remove queue from database
        if ( quantity == 0 ) {
            Delete( context,
                    DatabaseContract.buildUriWithId( QueueEntry.CONTENT_URI, queue.getId() ) );
            return;
        }

        // if quantities are not equal, update existing queue to database
        if ( queue.getQuantity() != quantity ) {
            queue.setQuantity( quantity );

            Update( context, queue );
        }
    }

    // -- DATABASE QUERY METHODS --

    private void Delete( Context context, Uri uri ) throws Exception {
        if ( context.getContentResolver().delete( uri, null, null ) > 0 )
            UpdateData( context );
    }

    public void Delete( Context context, long engramId ) throws Exception {
        Queue queue = QueryForQueue(
                context,
                QueueEntry.buildUriWithEngramId( engramId ) );

        if ( queue == null )
            return;

        Delete( context, DatabaseContract.buildUriWithId( QueueEntry.CONTENT_URI, queue.getId() ) );
    }

    private void Update( Context context, Queue queue ) throws Exception {
        ContentValues values = new ContentValues();
        values.put( QueueEntry.COLUMN_ENGRAM_KEY, queue.getEngramId() );
        values.put( QueueEntry.COLUMN_QUANTITY, queue.getQuantity() );

        context.getContentResolver().update(
                DatabaseContract.QueueEntry.CONTENT_URI,
                values,
                DatabaseContract.QueueEntry.SQL_QUERY_WITH_ID,
                new String[]{ Long.toString( queue.getId() ) } );

        UpdateData( context );
    }

    private void Insert( Context context, long engramId, int quantity ) throws Exception {
        ContentValues values = new ContentValues();
        values.put( QueueEntry.COLUMN_ENGRAM_KEY, engramId );
        values.put( QueueEntry.COLUMN_QUANTITY, quantity );

        context.getContentResolver().insert( QueueEntry.CONTENT_URI, values );

        UpdateData( context );
    }

    public void ClearContents( Context context ) throws Exception {
        context.getContentResolver().delete( QueueEntry.CONTENT_URI, null, null );

        UpdateData( context );
    }

    private LongSparseArray<QueueEngram> QueryForEngrams( Context context ) throws Exception {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();

        Cursor cursor = context.getContentResolver().query(
                QueueEntry.buildUriWithEngramTable( dlc_id ),
                null, null, null, null );

        if ( cursor == null )
            return new LongSparseArray<>();

//        Log.d( TAG, DatabaseUtils.dumpCursorToString( cursor ) );

        LongSparseArray<QueueEngram> engrams = new LongSparseArray<>();
        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( EngramEntry._ID ) );

            if ( engrams.indexOfKey( _id ) < 0 )
                engrams.put( _id,
                        new QueueEngram(
                                _id,
                                cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) ),
                                cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_DRAWABLE ) ),
                                cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) ),
                                cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) ) ) );
        }

        cursor.close();

        return engrams;
    }

    // TODO: 12/22/2016 Move all base queries to DBUtil
    private Resource QueryForResource( Context context, Uri uri ) throws Exception {

        Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        // If cursor is empty, it's possible the resource chosen for dlc_id doesn't exist, cross-
        //  reference drawable with a matching dlc_id?
        if ( !cursor.moveToFirst() )
            throw new ExceptionUtil.CursorEmptyException( uri );

        long _id = cursor.getLong( cursor.getColumnIndex( ResourceEntry._ID ) );
        String name = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_NAME ) );
        String drawable = cursor.getString( cursor.getColumnIndex( ResourceEntry.COLUMN_DRAWABLE ) );

        cursor.close();
        return new Resource( _id, name, drawable );
    }

    // Query for compositions, calculate composition quantities with queue quantities
    private SparseArray<CompositeResource> QueryForResources( Context context ) throws Exception {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();

        int level = 0;

        LongSparseArray<CompositeResource> resourceMap = new LongSparseArray<>();
        for ( int i = 0; i < mEngrams.size(); i++ ) {
            QueueEngram engram = mEngrams.valueAt( i );

//            Log.d( TAG, "** " + engram.toString() );

//            Log.d( TAG, addSpaces( level ) + "> QueryForComposition()" );

            SparseArray<CompositeResource> tempResources =
                    QueryForComposition(
                            context,
                            CompositionEntry.buildUriWithEngramId( dlc_id, engram.getId() ), level );

            for ( int j = 0; j < tempResources.size(); j++ ) {
                CompositeResource tempResource = tempResources.get( j );

                // Log.d( TAG, addSpaces( level ) + ": QueryForResources(), tempResource: " + tempResource.toString() );

                CompositeResource resource = resourceMap.get( tempResource.getId() );
                if ( resource == null )
                    resource = new CompositeResource(
                            tempResource.getId(),
                            tempResource.getName(),
                            tempResource.getDrawable(),
                            tempResource.getQuantity() * engram.getQuantity() );
                else
                    resource.increaseQuantity( tempResource.getQuantity() * engram.getQuantity() );

                // Log.d( TAG, addSpaces( level ) + ": QueryForResources(), resource: " + resource.toString() );

                resourceMap.put( resource.getId(), resource );
            }
        }

        SparseArray<CompositeResource> resources = new SparseArray<>();
        for ( int i = 0; i < resourceMap.size(); i++ )
            resources.put( i, resourceMap.valueAt( i ) );

        return resources;
    }

    private SparseArray<CompositeResource> QueryForComposition( Context context, Uri uri, int level ) throws Exception {
        long dlc_id = CompositionEntry.getDLCIdFromUri( uri );

        level++;

        // Log.d( TAG, addSpaces( level ) + ": QueryForComposition(), uri: " + uri.toString() );

        Cursor compositionCursor = context.getContentResolver().query(
                uri, null, null, null, null );

        if ( compositionCursor == null )
            return new SparseArray<>();

        LongSparseArray<CompositeResource> resourceMap = new LongSparseArray<>();
        while ( compositionCursor.moveToNext() ) {
            long resource_id = compositionCursor.getLong( compositionCursor.getColumnIndex( CompositionEntry.COLUMN_RESOURCE_KEY ) );
            int quantity = compositionCursor.getInt( compositionCursor.getColumnIndex( CompositionEntry.COLUMN_QUANTITY ) );

            long engram_id = -1;
            if ( hasComplexResources( context ) )
                engram_id = QueryForComplexResource(
                        context,
                        ComplexResourceEntry.buildUriWithResourceId( resource_id ) );

            if ( engram_id != -1 ) {

                // Log.d( TAG, addSpaces( level ) + "> QueryForComposition(), engram_id: " + engram_id );

                SparseArray<CompositeResource> tempResources =
                        QueryForComposition(
                                context,
                                CompositionEntry.buildUriWithEngramId( dlc_id, engram_id ), level );

                // Log.d( TAG, addSpaces( level ) + "  tempResources: " + tempResources );

                for ( int i = 0; i < tempResources.size(); i++ ) {
                    CompositeResource tempResource = tempResources.get( i );

                    // Log.d( TAG, addSpaces( level ) + "  tempResource: " + tempResource.toString() );

                    CompositeResource resource = resourceMap.get( tempResource.getId() );
                    if ( resource == null )
                        resource = new CompositeResource(
                                tempResource.getId(),
                                tempResource.getName(),
                                tempResource.getDrawable(),
                                tempResource.getQuantity() * quantity );

                    // Log.d( TAG, addSpaces( level ) + "- resource: " + resource.toString() );

                    resourceMap.put( resource.getId(), resource );
                }
            } else {
                Resource tempResource = QueryForResource(
                        context,
                        ResourceEntry.buildUriWithId( dlc_id, resource_id ) );

                // Log.d( TAG, addSpaces( level ) + "  tempResource: " + tempResource.toString() );

                CompositeResource resource = resourceMap.get( resource_id );
                if ( resource == null )
                    resource = new CompositeResource(
                            tempResource.getId(),
                            tempResource.getName(),
                            tempResource.getDrawable(),
                            quantity );

                // Log.d( TAG, addSpaces( level ) + "- resource: " + resource.toString() );

                resourceMap.put( resource_id, resource );
            }
        }
        compositionCursor.close();

        SparseArray<CompositeResource> resources = new SparseArray<>();
        for ( int i = 0; i < resourceMap.size(); i++ )
            resources.put( i, resourceMap.valueAt( i ) );

        // Log.d( TAG, addSpaces( level ) + "< QueryForComposition()" );

        return resources;
    }

    private Queue QueryForQueue( Context context, Uri uri ) throws Exception {
        Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null );

        if ( cursor == null )
            return null;

        Queue queue = null;
        if ( cursor.moveToFirst() ) {
            queue = new Queue(
                    cursor.getLong( cursor.getColumnIndex( QueueEntry._ID ) ),
                    cursor.getLong( cursor.getColumnIndex( QueueEntry.COLUMN_ENGRAM_KEY ) ),
                    cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) ) );
        }

        cursor.close();
        return queue;
    }

    private long QueryForComplexResource( Context context, Uri uri ) throws Exception {

        Cursor cursor = context.getContentResolver().query(
                uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        long engram_id;
        if ( cursor.moveToFirst() )
            engram_id = cursor.getLong( cursor.getColumnIndex( ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
        else
            engram_id = -1;

        cursor.close();
        return engram_id;
    }

    // -- PRIVATE UTILITY METHODS --

    private void UpdateEngrams( Context context ) throws Exception {
        mEngrams = QueryForEngrams( context );
    }

    public void UpdateResources( Context context ) throws Exception {
        mResources = sortResourcesByName( QueryForResources( context ) );
    }

    private void UpdateData( Context context ) throws Exception {
        UpdateEngrams( context );
        UpdateResources( context );
    }

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
}
