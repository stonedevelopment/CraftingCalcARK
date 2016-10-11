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
package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.helpers.PreferenceHelper;
import arc.resource.calculator.model.engram.DisplayEngram;

public class DisplayCase {
    private static final String TAG = DisplayCase.class.getSimpleName();

    private static DisplayCase sInstance;
    private static final long ROOT = 0;

    private boolean isFiltered;

    private long mLevel;
    private long mParent;

    private Context mContext;

    private SparseArray<DisplayEngram> mEngrams;
    private SparseArray<Category> mCategories;
    private HashMap<Long, Queue> mQueues;

    private DisplayCase( Context context ) {
        PreferenceHelper preferenceHelper = new PreferenceHelper( context );
        isFiltered = preferenceHelper.getBooleanPreference( Helper.FILTERED, true );

        // Retrieve saved level and parent from previous use
        mLevel = preferenceHelper.getLongPreference( Helper.APP_LEVEL );
        mParent = preferenceHelper.getLongPreference( Helper.APP_PARENT );

        mContext = context;

        mEngrams = new SparseArray<>();
        mCategories = new SparseArray<>();
        mQueues = new HashMap<>();

        UpdateData();
    }

    public static DisplayCase getInstance( Context context ) {
        if ( sInstance == null ) {
            sInstance = new DisplayCase( context );
        }

        return sInstance;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public long getLevel() {
        return mLevel;
    }

    public long getParent() {
        return mParent;
    }

    public boolean setIsFiltered( boolean filtered ) {
        if ( isFiltered() != filtered ) {
            new PreferenceHelper( getContext() ).setPreference( Helper.FILTERED, filtered );

            isFiltered = filtered;

            UpdateData();

            return true;
        }
        return false;
    }

    public void setLevel( long level ) {
        this.mLevel = level;
    }

    public void setParent( long parent ) {
        this.mParent = parent;
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    public String getDrawable( int position ) {
        if ( isFiltered ) {
            if ( position >= mCategories.size() ) {
                position -= mCategories.size();

                DisplayEngram engram = mEngrams.valueAt( position );

                return engram.getDrawable();
            } else {
                Category category = mCategories.valueAt( position );

                return category.getDrawable();
            }
        } else {
            DisplayEngram engram = mEngrams.valueAt( position );

            return engram.getDrawable();
        }
    }

    public String getNameByPosition( int position ) {
        if ( isFiltered ) {
            if ( position >= mCategories.size() ) {
                position -= mCategories.size();

                DisplayEngram engram = mEngrams.valueAt( position );

                return engram.getName();
            } else {
                Category category = mCategories.valueAt( position );

                return category.getName();
            }
        } else {
            DisplayEngram engram = mEngrams.valueAt( position );

            return engram.getName();
        }
    }

    public String getNameByCategoryId( long categoryId ) {
        if ( categoryId > ROOT ) {
            Category category = getCategory( categoryId );

            if ( category != null ) {
                return "Go Back to " + category.getName();
            }
        }

        return "Go Back";
    }

    public int getQuantity( int position ) {
        if ( isFiltered ) {
            if ( position < mCategories.size() ) {
                return 0;
            }

            position -= mCategories.size();
        }

        Queue queue = mQueues.get( mEngrams.valueAt( position ).getId() );

        if ( queue != null ) {
            return queue.getQuantity();
        }

        return 0;
    }

    public int getQuantityWithYield( int position ) {
        if ( isFiltered ) {
            if ( position < mCategories.size() ) {
                return 0;
            }

            position -= mCategories.size();
        }

        DisplayEngram engram = mEngrams.valueAt( position );
        Queue queue = mQueues.get( engram.getId() );

        if ( queue != null ) {
            int yield = engram.getYield();
            int quantity = queue.getQuantity();

            return quantity * yield;
        }

        return 0;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * -- PUBLIC METHODS --
     */

    public boolean isEngram( int position ) {
        return position >= mCategories.size();
    }

    public int getCount() {
        if ( mEngrams != null && mCategories != null ) {
            return mEngrams.size() + mCategories.size();
        }
        return 0;
    }

    public DisplayEngram getEngram( int position ) {
        if ( isFiltered ) {
            if ( position >= mCategories.size() ) {
                position -= mCategories.size();

                return mEngrams.valueAt( position );
            }
        } else {
            return mEngrams.valueAt( position );
        }

        return null;
    }

    public long getEngramId( int position ) {
        if ( isFiltered ) {
            if ( position >= mCategories.size() ) {
                position -= mCategories.size();

                return mEngrams.valueAt( position ).getId();
            }
        } else {
            return mEngrams.valueAt( position ).getId();
        }

        return 0;
    }

    public void changeCategory( int position ) {
        if ( position < mCategories.size() ) {
            // position within bounds
            Category category = mCategories.valueAt( position );

            Helper.Log( TAG, "Changing category to [" + position + "] " + category.toString() );

            if ( getLevel() == ROOT ) {
                // ROOT LEVEL
                setLevel( category.getId() );   // Grabbing ID is the best way to track its location.
                setParent( category.getParent() );
            } else {
                if ( position == 0 ) {
                    // back button or FIRST CATEGORY, revert back to parent level or root level
                    if ( category.getParent() > ROOT ) {
                        Category parentCategory = getCategory( category.getParent() );

                        setLevel( category.getParent() );
                        setParent( parentCategory.getParent() );
                    } else {
                        setLevel( ROOT );
                        setParent( ROOT );
                    }
                } else {
                    // position is > 0, making this a normal category object
                    setLevel( category.getId() );   // Grabbing ID is the best way to track its location.
                    setParent( category.getParent() );
                }
            }

            // Update lists with new data
            UpdateData();
        } else {
            Log.e( TAG, "mCategories, position out of bounds: " + position );
        }
    }

    public void UpdateQueues() {
        mQueues = getQueues();
    }

    /**
     * -- PRIVATE UTILITY METHODS --
     */

    private Category getCategory( long _id ) {
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.buildUriWithId( DatabaseContract.CategoryEntry.CONTENT_URI, _id ),
                null, null, null, null );


        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );
            long dlc_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY ) );

            cursor.close();
            return new Category(
                    _id,
                    name,
                    parent_id,
                    dlc_id );
        }

        return null;
    }

    private SparseArray<Category> getCategories() {
        PreferenceHelper preferenceHelper = new PreferenceHelper( getContext() );
        long dlcId = preferenceHelper.getIntPreference( getContext().getString( R.string.pref_dlc_setting ) );

        SparseArray<Category> categories = new SparseArray<>();

        if ( isFiltered() ) {
            Cursor cursor = getContext().getContentResolver().query(
                    DatabaseContract.CategoryEntry.buildUriWithParentId( dlcId, getLevel() ),
                    null, null, null, null );

            if ( cursor == null ) {
                return new SparseArray<>();
            }

            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
                long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );
                long dlc_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY ) );

                categories.append( categories.size(), new Category( _id, name, parent_id, dlc_id ) );
            }

            cursor.close();

            if ( getLevel() > 0 ) {
                Category category = new Category( getLevel(), getNameByCategoryId( getParent() ), getParent(), "back" );
                categories.put( 0, category );
            }

            debugCategories( categories );
        }

        return categories;
    }

    private SparseArray<DisplayEngram> getEngrams() {
        PreferenceHelper preferenceHelper = new PreferenceHelper( getContext() );
        long dlcId = preferenceHelper.getIntPreference( getContext().getString( R.string.pref_dlc_setting ) );

        Uri uri;
        if ( isFiltered() ) {
            uri = DatabaseContract.EngramEntry.buildUriWithCategoryId( dlcId, getLevel() );
        } else {
            uri = DatabaseContract.EngramEntry.buildUriWithDLCId( dlcId );
        }

        Cursor cursor = getContext().getContentResolver().query(
                uri, null, null, null, null );

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        SparseArray<DisplayEngram> engrams = new SparseArray<>();
        while ( cursor.moveToNext() ) {
            engrams.put( engrams.size(),
                    new DisplayEngram(
                            cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) ),
                            cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                            cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) ),
                            cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) ),
                            cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) ) ) );
        }
        cursor.close();

        return engrams;
    }

    private HashMap<Long, Queue> getQueues() {
        mQueues = QueryForQueues();
        return mQueues;
    }

    private HashMap<Long, Queue> QueryForQueues() {
        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.QueueEntry.CONTENT_URI,
                null, null, null, null );

        if ( cursor == null ) {
            return new HashMap<>();
        }

        HashMap<Long, Queue> queues = new HashMap<>();
        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.QueueEntry._ID ) );
            long engramId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_ENGRAM_KEY ) );
            int quantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.QueueEntry.COLUMN_QUANTITY ) );

            queues.put( engramId, new Queue( _id, engramId, quantity ) );
        }

        cursor.close();

        return queues;
    }

    private void debugCategories( SparseArray<Category> categories ) {
        Helper.Log( TAG, "-- Displaying mCategories at mLevel " + getLevel() + ".." );

        for ( int i = 0; i < categories.size(); i++ ) {
            Category category = categories.valueAt( i );
            Helper.Log( TAG, "-> [" + i + "/" + categories.keyAt( i ) + "] " + category.toString() );
        }

        Helper.Log( TAG, "-- Display completed." );
    }

    public void UpdateData() {
        mEngrams = getEngrams();
        mCategories = getCategories();
        mQueues = getQueues();
    }
}
