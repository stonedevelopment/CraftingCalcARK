package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.helpers.Helper;
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
public class Showcase {
    private static final String TAG = Showcase.class.getSimpleName();

    private long mId;
    private String mName;
    private String mDescription;
    private String mDrawable;
    private long mCategoryId;
    private long mVersionId;

    private SparseArray<CompositeResource> mComposition;

    private int mQuantity;

    private Context mContext;

    public Showcase( Context context, long engramId ) {
        this.mContext = context;
        this.mId = engramId;

        QueryForEngramDetails();
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

    public int getQuantity() {
        return mQuantity;
    }

    public String getQuantityText() {
        return String.valueOf( mQuantity );
    }

    public SparseArray<CompositeResource> getComposition() {
        return mComposition;
    }

    public SparseArray<QueueResource> getQuantifiableComposition() {
        SparseArray<QueueResource> returnableComposition = new SparseArray<>();

        SparseArray<CompositeResource> baseComposition = mComposition;

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

    public String getVersionName() {
        return QueryForVersionName( mVersionId );
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

    // Query Methods
    private void QueryForEngramDetails() {
        // First, let's grab Engram full details.
        Cursor cursor = mContext.getContentResolver().query(
                DatabaseContract.EngramEntry.buildUriWithId( mId ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            mName = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
            mDrawable = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) );
            mDescription = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION ) );
            mCategoryId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) );
            mVersionId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_VERSION_KEY ) );

            cursor.close();
        } else {
            // Engram does not exist!?
            Log.e( TAG, "QueryForEngramDetails(" + mId + ") returned null?" );
            return;
        }

        // Next, let's grab matching Queue details, if there are any.
        cursor = mContext.getContentResolver().query(
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
        cursor = mContext.getContentResolver().query(
                DatabaseContract.CompositionEntry.buildUriWithEngramId( mId ),
                null, null, null, null
        );

        mComposition = new SparseArray<>();
        if ( cursor != null && cursor.getCount() > 0 ) {
            while ( cursor.moveToNext() ) {
                long resourceId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int resourceQuantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                Cursor resourceCursor = mContext.getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithId( resourceId ),
                        null, null, null, null
                );

                if ( resourceCursor != null && resourceCursor.moveToFirst() ) {
                    String resourceName = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                    String resourceDrawable = resourceCursor.getString( resourceCursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) );

                    CompositeResource resource = new CompositeResource( resourceId, resourceName, resourceDrawable, resourceQuantity );
                    mComposition.put( mComposition.size(), resource );

                    resourceCursor.close();
                }
            }

            cursor.close();
        } else {
            // Engram composition does not exist!?
            Log.e( TAG, "QueryForEngramDetails(" + mId + ") composition returned null?" );
        }
    }

    private Category QueryForCategoryDetails( long _id ) {
        Cursor cursor = mContext.getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithId( _id ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );
            long version_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_VERSION_KEY ) );

            cursor.close();

            return new Category(
                    _id,
                    name,
                    parent_id,
                    version_id
            );
        } else {
            // Category does not exist!?
            Log.e( TAG, "QueryForCategoryDetails(" + _id + ") returned null?" );
            return null;
        }
    }

    private String QueryForVersionName( long _id ) {
        Cursor cursor = mContext.getContentResolver().query(
                DatabaseContract.VersionEntry.buildUriWithId( _id ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.VersionEntry.COLUMN_NAME ) );

            cursor.close();
            return name;
        } else {
            // Version does not exist!?
            Log.e( TAG, "QueryForVersionName(" + _id + ") returned null?" );
            return null;
        }
    }
}
