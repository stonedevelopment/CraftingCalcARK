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
public class Showcase {
    private static final String TAG = Showcase.class.getSimpleName();

    private long mId;
    private String mName;
    private String mDescription;
    private String mDrawable;
    private long mCategoryId;
    private List<Long> mStationIds;
    private long mStationId;
    private int mRequiredLevel;
    private long mDLCId;

    private SparseArray<CompositeResource> mComposition;

    private int mYield;
    private int mQuantity;

    private Context mContext;

    public Showcase( Context context, long engramId ) {
        mContext = context;
        mId = engramId;

        mStationIds = new ArrayList<>();

        mDLCId = new PrefsUtil( mContext ).getDLCPreference();

        QueryForEngramDetails();
    }

    private Context getContext() {
        return mContext;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDrawable() {
        return mDrawable;
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

    public int getQuantityWithYield() {
        return mQuantity * mYield;
    }

    public String getQuantityText() {
        return String.valueOf( getQuantityWithYield() );
    }

    public SparseArray<CompositeResource> getComposition() {
        return mComposition;
    }

    public SparseArray<QueueResource> getQuantifiableComposition() {
        SparseArray<QueueResource> returnableComposition = new SparseArray<>();

        SparseArray<CompositeResource> baseComposition = getComposition();

        for ( int i = 0; i < baseComposition.size(); i++ ) {
            CompositeResource resource = baseComposition.valueAt( i );

            QueueResource queueResource = new QueueResource(
                    resource.getId(),
                    resource.getName(),
                    resource.getDrawable(),
                    resource.getQuantity(),
                    getQuantity()
            );
            returnableComposition.append( i, queueResource );
        }

        return Helper.sortResourcesByName( returnableComposition );
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public String getCategoryHierarchy() {
        Category category = QueryForCategoryDetails( mCategoryId );

        if ( category == null ) return null;

        long parent_id = category.getParent();

        StringBuilder builder = new StringBuilder( category.getName() );
        while ( parent_id > 0 ) {
            category = QueryForCategoryDetails( parent_id );
            if ( category == null ) break;

            parent_id = category.getParent();

            builder.insert( 0, category.getName() + "/" );
        }

        return builder.toString();
    }

    public long getStationId() {
        return mStationId;
    }

    public String getStationName() {
        return QueryForStationName( mStationId );
    }

    public String getStations() {
        List<String> names = new ArrayList<>();

        for ( Long _id : mStationIds ) {
            String name = QueryForStationName( _id );

            names.add( name );
        }

        return names.toString();
    }

    public long getDLCId() {
        return mDLCId;
    }

    public String getDLCName() {
        return QueryForDLCName();
    }

    public void setQuantity( int quantity ) {
        mQuantity = quantity;
    }

    public void increaseQuantity() {
        mQuantity += 1;
    }

    public void decreaseQuantity() {
        if ( mQuantity > 0 ) {
            mQuantity -= 1;
        }
    }

    public void increaseQuantityBy10() {
        int amount = getQuantity() + 10;
        int floor = amount / 10;

        setQuantity( floor * 10 );
    }

    public void decreaseQuantityBy10() {
        if ( mQuantity >= 10 ) {
            int amount, floor;

            if ( getQuantity() % 10 == 0 ) {
                amount = getQuantity() - 10;
            } else {
                amount = getQuantity();
            }

            floor = amount / 10;

            setQuantity( floor * 10 );
        } else {
            if ( mQuantity > 0 ) {
                mQuantity = 0;
            }
        }
    }

    // Query Methods
    private void QueryForEngramDetails() {
        // First, let's grab Engram full details.
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.EngramEntry.buildUriWithId( mDLCId, mId ),
                null, null, null, null
        );

        if ( cursor == null ) {
            Log.e( TAG, "QueryForEngramDetails(" + mId + ") returned null." );
            return;
        }

        if ( cursor.moveToFirst() ) {
            mName = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
            mDrawable = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) );
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
        cursor = getContext().getContentResolver().query(
                DatabaseContract.QueueEntry.buildUriWithEngramId( mId ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            mQuantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_QUANTITY ) );

            cursor.close();
        } else {
            mQuantity = 0;
        }

        // Finally, let's grab Engram Composition details.
        cursor = getContext().getContentResolver().query(
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
                Cursor resourceCursor = mContext.getContentResolver().query(
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
                    String resourceDrawable = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) );

                    CompositeResource resource = new CompositeResource( resourceId, resourceName, resourceDrawable, resourceQuantity );
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

    private Category QueryForCategoryDetails( long _id ) {
        Cursor cursor = getContext().getContentResolver().query(
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

    private String QueryForStationName( long _id ) {
        Cursor cursor = getContext().getContentResolver().query(
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

    private String QueryForDLCName() {
        long _id = getDLCId();

        Cursor cursor = getContext().getContentResolver().query(
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