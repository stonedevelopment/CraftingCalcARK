package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.QueueResource;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;

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
public class Showcase {
    private static final String TAG = Showcase.class.getSimpleName();

    private final long mId;
    private String mName;
    private String mDescription;
    private String mImagePath;
    private long mCategoryId;
    private final List<Long> mStationIds;
    private long mStationId;
    private int mRequiredLevel;
    private final long mDLCId;

    // TODO: 1/22/2017 SparseLongArray
    private SparseArray<CompositeResource> mComposition;

    private int mYield;
    private int mQuantity;

    public Showcase( Context context, long _id ) {
        mId = _id;
        mStationIds = new ArrayList<>();
        mDLCId = new PrefsUtil( context ).getDLCPreference();

        mQuantity = 0;

        QueryForEngramDetails( context );
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getRequiredLevel() {
        return mRequiredLevel;
    }

    public int getYield() {
        return mYield;
    }

    public int getQuantity() {
        return mQuantity;
    }

    private int getQuantityWithYield() {
        return mQuantity * mYield;
    }

    public String getQuantityText() {
        return String.valueOf( getQuantityWithYield() );
    }

    public QueueResource getResource( int position ) throws Exception {
        QueueResource resource = getQuantifiableComposition().valueAt( position );

        if ( resource == null )
            throw new ExceptionUtil.ArrayElementNullException( position, mComposition.toString() );

        return resource;
    }

    public SparseArray<CompositeResource> getComposition() {
        return mComposition;
    }

    private SparseArray<QueueResource> getQuantifiableComposition() {
        SparseArray<QueueResource> returnableComposition = new SparseArray<>();

        SparseArray<CompositeResource> baseComposition = getComposition();

        for ( int i = 0; i < baseComposition.size(); i++ ) {
            CompositeResource resource = baseComposition.valueAt( i );

            QueueResource queueResource = new QueueResource(
                    resource.getId(),
                    resource.getName(),
                    resource.getFolder(),
                    resource.getFile(),
                    resource.getQuantity(),
                    getQuantity()
            );
            returnableComposition.append( i, queueResource );
        }

        return Util.sortResourcesByName( returnableComposition );
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public String getCategoryHierarchy( Context context ) {
        Category category = QueryForCategoryDetails( context, mCategoryId );

        if ( category == null ) return null;

        long parent_id = category.getParent();

        StringBuilder builder = new StringBuilder( category.getName() );
        while ( parent_id > 0 ) {
            category = QueryForCategoryDetails( context, parent_id );
            if ( category == null ) break;

            parent_id = category.getParent();

            builder.insert( 0, category.getName() + "/" );
        }

        return builder.toString();
    }

    public long getStationId() {
        return mStationId;
    }

    public String getStationName( Context context ) {
        return QueryForStationName( context, mStationId );
    }

    public String getStations( Context context ) {
        List<String> names = new ArrayList<>();

        for ( Long _id : mStationIds ) {
            String name = QueryForStationName( context, _id );

            names.add( name );
        }

        return names.toString();
    }

    private long getDLCId() {
        return mDLCId;
    }

    public void setQuantity( int quantity ) {
        mQuantity = quantity;
    }

    public void increaseQuantity() {
        mQuantity += 1;
    }

    public void increaseQuantity( int increment ) {
        int amount = getQuantity() + increment;
        int floor = amount / increment;

        setQuantity( floor * increment );
    }

    public void decreaseQuantity() {
        if ( mQuantity > 1 ) {
            mQuantity -= 1;
        }
    }

    public void decreaseQuantity( int decrement ) {
        if ( mQuantity >= decrement ) {
            int amount;
            if ( getQuantity() % decrement == 0 ) {
                amount = getQuantity() - decrement;
            } else {
                amount = getQuantity();
            }

            int floor = amount / decrement;

            setQuantity( floor * decrement );
        } else {
            if ( mQuantity > 1 ) {
                mQuantity = 1;
            }
        }
    }

    // Query Methods
    private void QueryForEngramDetails( Context context ) {
        // First, let's grab Engram full details.
        Cursor cursor = context.getContentResolver().query(
                DatabaseContract.EngramEntry.buildUriWithId( mDLCId, mId ),
                null, null, null, null
        );

        if ( cursor == null ) {
            Log.e( TAG, "QueryForEngramDetails(" + mId + ") returned null." );
            return;
        }

        if ( cursor.moveToFirst() ) {
            mName = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );

            String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );
            mImagePath = folder + file;

            mDescription = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION ) );
            mYield = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
            mCategoryId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) );
            mStationId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_STATION_KEY ) );
            mRequiredLevel = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_LEVEL ) );

            mStationIds.add( mStationId );
            while ( cursor.moveToNext() ) {
                mStationIds.add( cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_STATION_KEY ) ) );
            }

            cursor.close();
        } else {
            // Engram does not exist!?
            Log.e( TAG, "QueryForEngramDetails(" + mId + "): moveToFirst did not fire!" );
            return;
        }

        // Next, let's grab matching Queue details, if there are any.
        cursor = context.getContentResolver().query(
                DatabaseContract.QueueEntry.buildUriWithEngramId( mId ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            mQuantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_QUANTITY ) );

            cursor.close();
        }

        // Finally, let's grab Engram Composition details.
        cursor = context.getContentResolver().query(
                DatabaseContract.CompositionEntry.buildUriWithEngramId( mDLCId, mId ),
                null, null, null, null
        );

        if ( cursor == null ) {
            Log.e( TAG, "QueryForEngramDetails(" + mId + "), Composition cursor returned null." );
            return;
        }

        mComposition = new SparseArray<>();
        List<Long> resourceIds = new ArrayList<>();
        while ( cursor.moveToNext() ) {
            long resourceId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
            int resourceQuantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

            if ( !resourceIds.contains( resourceId ) ) {
                Cursor resourceCursor = context.getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithId( mDLCId, resourceId ),
                        null, null, null, null
                );

                if ( resourceCursor == null ) {
                    Log.e( TAG, "QueryForEngramDetails(" + mId + "), Resource cursor returned null." );
                    return;
                }

                if ( resourceCursor.moveToFirst() ) {
                    resourceIds.add( resourceId );

                    String resourceName = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                    String resourceFolder = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
                    String resourceFile = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );

                    CompositeResource resource = new CompositeResource( resourceId, resourceName, resourceFolder, resourceFile, resourceQuantity );
                    mComposition.put( mComposition.size(), resource );

                    Log.d( TAG, "QueryForEngramDetails, CompositeResource: " + resource.toString() );

                    resourceCursor.close();
                } else {
                    Log.e( TAG, "QueryForEngramDetails(" + mId + "), Resource cursor.moveToFirst() did not fire!" );
                    return;
                }
            }
        }

        cursor.close();
    }

    private Category QueryForCategoryDetails( Context context, long _id ) {
        Cursor cursor = context.getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithId( mDLCId, _id ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            cursor.close();

            return new Category(
                    _id,
                    name,
                    parent_id
            );
        } else {
            // Category does not exist!?
            Log.e( TAG, "QueryForCategoryDetails(" + _id + ") returned null?" );
            return null;
        }
    }

    private String QueryForStationName( Context context, long _id ) {
        Cursor cursor = context.getContentResolver().query(
                DatabaseContract.StationEntry.buildUriWithId( mDLCId, _id ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );

            cursor.close();

            return name;
        } else {
            // Station does not exist!?
            Log.e( TAG, "QueryForStationName(" + _id + ") returned null?" );
            return null;
        }
    }

    private String QueryForDLCName( Context context ) {
        long _id = getDLCId();

        Cursor cursor = context.getContentResolver().query(
                DatabaseContract.buildUriWithId( DatabaseContract.DLCEntry.CONTENT_URI, _id ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.DLCEntry.COLUMN_NAME ) );

            cursor.close();
            return name;
        } else {
            // DLC does not exist!?
            Log.e( TAG, "QueryForDLCName(" + _id + ") returned null?" );
            return null;
        }
    }
}