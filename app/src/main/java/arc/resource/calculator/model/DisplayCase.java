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
import android.util.SparseArray;

import java.util.HashMap;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.helpers.PreferenceHelper;
import arc.resource.calculator.model.engram.DisplayEngram;

public class DisplayCase {
    private static final String TAG = DisplayCase.class.getSimpleName();

    private static final long ROOT = 0;

    private boolean isFiltered;
    private long level;
    private long parent;

    private Context mContext;

    private SparseArray<DisplayEngram> engrams = null;
    private SparseArray<Category> categories = null;
    private HashMap<Long, Queue> queues = null;

    public DisplayCase( Context context ) {
        PreferenceHelper preferenceHelper = new PreferenceHelper( context );
        isFiltered = preferenceHelper.getBooleanPreference( Helper.FILTERED, true );

        // Retrieve saved level and parent from previous use
        level = preferenceHelper.getLongPreference( Helper.APP_LEVEL );
        parent = preferenceHelper.getLongPreference( Helper.APP_PARENT );
//        level = 0;
//        parent = 0;

        mContext = context;

        UpdateData();
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public long getLevel() {
        return level;
    }

    public long getParent() {
        return parent;
    }

    public boolean setIsFiltered( boolean filtered ) {
        if ( isFiltered() != filtered ) {
            PreferenceHelper preferenceHelper = new PreferenceHelper( getContext() );
            preferenceHelper.setPreference( Helper.FILTERED, filtered );

            isFiltered = filtered;

            UpdateData();

            return true;
        }
        return false;
    }

    public void setLevel( long level ) {
        this.level = level;
    }

    public void setParent( long parent ) {
        this.parent = parent;
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    public String getDrawable( int position ) {
        if ( isFiltered ) {
            if ( position >= categories.size() ) {
                position -= categories.size();

                DisplayEngram engram = engrams.valueAt( position );

                return engram.getDrawable();
            } else {
                Category category = categories.valueAt( position );

                return category.getDrawable();
            }
        } else {
            DisplayEngram engram = engrams.valueAt( position );

            return engram.getDrawable();
        }
    }

    public String getName( int position ) {
        if ( isFiltered ) {
            if ( position >= categories.size() ) {
                position -= categories.size();

                DisplayEngram engram = engrams.valueAt( position );

                return engram.getName();
            } else {
                Category category = categories.valueAt( position );

                return category.getName();
            }
        } else {
            DisplayEngram engram = engrams.valueAt( position );

            return engram.getName();
        }
    }

    public String getName( long categoryId ) {
        if ( categoryId > ROOT ) {
            Category category = getCategory( categoryId );
            return "Go Back to " + category.getName();
        } else {
            return "Go Back";
        }
    }

    public int getQuantity( int position ) {
        if ( isFiltered ) {
            if ( position < categories.size() ) {
                return 0;
            }

            position -= categories.size();
        }

        Queue queue = queues.get( engrams.valueAt( position ).getId() );

        if ( queue != null ) {
            return queue.getQuantity();
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
        return position >= categories.size();
    }

    public int getCount() {
        if ( engrams != null && categories != null ) {
            return engrams.size() + categories.size();
        }
        return 0;
    }

    public long getEngramId( int position ) {
        if ( isFiltered ) {
            if ( position >= categories.size() ) {
                position -= categories.size();

                return engrams.valueAt( position ).getId();
            }
        } else {
            return engrams.valueAt( position ).getId();
        }

        return 0;
    }

    public void changeCategory( int position ) {
        if ( position < categories.size() ) {
            // position within bounds
            Category category = categories.valueAt( position );

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
            // position out of bounds
        }
    }

    public void UpdateQueues() {
        queues = getQueues();
    }

    /**
     * -- PRIVATE UTILITY METHODS --
     */

    private Category getCategory( long categoryId ) {

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithId( categoryId ),
                null, null, null, null );


        if ( cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            cursor.close();
            return new Category(
                    categoryId,
                    name,
                    parent );
        }

        cursor.close();
        return null;
    }

    private SparseArray<Category> getCategories() {
        SparseArray<Category> categories = new SparseArray<>();

        if ( isFiltered() ) {
            Cursor cursor = getContext().getContentResolver().query(
                    DatabaseContract.CategoryEntry.buildUriWithParentId( getLevel() ),
                    null, null, null, null );

            while ( cursor.moveToNext() ) {
                long id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
                long parent = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

                categories.append( categories.size(), new Category( id, name, parent ) );
            }

            cursor.close();

            if ( getLevel() > 0 ) {
                Category category = new Category( getLevel(), getName( getParent() ), getParent(), "back" );
                categories.put( 0, category );
            }

            debugCategories( categories );
        }

        return categories;
    }

    private SparseArray<DisplayEngram> getEngrams() {
        Uri uri;
        if ( isFiltered() ) {
            uri = DatabaseContract.EngramEntry.buildUriWithCategoryId( getLevel() );
        } else {
            uri = DatabaseContract.EngramEntry.CONTENT_URI;
        }

        Cursor cursor = getContext().getContentResolver().query(
                uri, null, null, null, null );

        SparseArray<DisplayEngram> engrams = new SparseArray<>();
        while ( cursor.moveToNext() ) {
            engrams.put( engrams.size(),
                    new DisplayEngram(
                            cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) ),
                            cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                            cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) ),
                            cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) ) ) );
        }
        cursor.close();

        return engrams;
    }

    private HashMap<Long, Queue> getQueues() {
        queues = QueryForQueues();
        return queues;
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
        Helper.Log( TAG, "-- Displaying categories at level " + getLevel() + ".." );

        for ( int i = 0; i < categories.size(); i++ ) {
            Category category = categories.valueAt( i );
            Helper.Log( TAG, "-> [" + i + "/" + categories.keyAt( i ) + "] " + category.toString() );
        }

        Helper.Log( TAG, "-- Display completed." );
    }

    private void UpdateData() {
        engrams = getEngrams();
        categories = getCategories();
        queues = getQueues();
    }
}
