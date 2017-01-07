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
import android.util.LongSparseArray;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

public class DisplayCase {
    private static final String TAG = DisplayCase.class.getSimpleName();
    private static final long ROOT = 0;
    private static final long STATION_ROOT = -1;

    private long mLastCategoryLevel;
    private long mLastCategoryParent;
    private long mStation;

    private Context mContext;

    private SparseArray<DisplayEngram> mEngrams;
    private SparseArray<Category> mCategories;
    private SparseArray<Station> mStations;
    private LongSparseArray<Queue> mQueues;

    public DisplayCase( Context context ) {
        PrefsUtil prefs = new PrefsUtil( context );

        // Retrieve last viewed category level and parent from previous use
        mLastCategoryLevel = prefs.getLastCategoryLevel();
        mLastCategoryParent = prefs.getLastCategoryParent();

        // Retrieve last viewed Station id
        mStation = prefs.getLastStationId();

        mContext = context;

        mEngrams = new SparseArray<>();
        mCategories = new SparseArray<>();
        mStations = new SparseArray<>();
        mQueues = new LongSparseArray<>();

        try {
            UpdateData();
        } catch ( Exception e ) {
            ExceptionUtil.SendErrorReportWithAlertDialog( context, TAG, e );
        }
    }

    private boolean isFilteredByCategory() {
        return new PrefsUtil( getContext() ).getCategoryFilterPreference();
    }

    private boolean isFilteredByStation() {
        return new PrefsUtil( getContext() ).getStationFilterPreference();
    }

    private boolean isFilteredByLevel() {
        return new PrefsUtil( getContext() ).getLevelFilterPreference();
    }

    private boolean isCurrentCategoryLevelRoot() {
        return getCurrentCategoryLevel() == ROOT;
    }

    private boolean isCurrentCategoryLevelStationRoot() {
        return getCurrentCategoryLevel() == STATION_ROOT;
    }

    private boolean isRoot( long level ) {
        return level == ROOT;
    }

    private boolean isStationRoot( long level ) {
        return level == STATION_ROOT;
    }

    public long getCurrentCategoryLevel() {
        return mLastCategoryLevel;
    }

    public long getCurrentCategoryParent() {
        return mLastCategoryParent;
    }

    private void setCurrentCategoryLevels( long level, long parent ) {
        mLastCategoryLevel = level;
        mLastCategoryParent = parent;
    }

    public void resetCategoryLevels() {
        if ( isFilteredByStation() ) {
            setCurrentCategoryLevelsToStationRoot();
        } else {
            setCurrentCategoryLevelsToRoot();
        }
    }

    private void setCurrentCategoryLevelsToRoot() {
        setCurrentCategoryLevels( ROOT, ROOT );
    }

    private void setCurrentCategoryLevelsToStationRoot() {
        setCurrentCategoryLevels( STATION_ROOT, STATION_ROOT );
    }

    public long getCurrentStationId() {
        return mStation;
    }

    private void setCurrentStationId( long stationId ) {
        mStation = stationId;
    }

    private int getRequiredLevelPref() {
        return new PrefsUtil( getContext() ).getRequiredLevel();
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    public String getDrawableByPosition( int position ) throws Exception {
        if ( isStation( position ) ) {
            return getStation( position ).getDrawable();
        }

        if ( isCategory( position ) ) {
            return getCategory( position ).getDrawable();
        }

        if ( isEngram( position ) ) {
            return getEngram( position ).getDrawable();
        }

        throw new ExceptionUtil.IndexOutOfBoundsException( position, "getDrawableByPosition()" );
    }

    public String getNameByPosition( int position ) throws Exception {
        if ( isStation( position ) ) {
            return getStation( position ).getName();
        }

        if ( isCategory( position ) ) {
            return getCategory( position ).getName();
        }

        if ( isEngram( position ) ) {
            return getEngram( position ).getName();
        }

        throw new ExceptionUtil.IndexOutOfBoundsException( position, "getNameByPosition()" );
    }

    public int getQuantityWithYield( int position ) throws Exception {
        DisplayEngram engram = getEngram( position );

        Queue queue = getQueue( engram.getId() );
        if ( queue == null )
            return 0;

        int yield = engram.getYield();
        int quantity = queue.getQuantity();

        return quantity * yield;
    }

    public int getCount() {
        return mEngrams.size() + ( mStations.size() + mCategories.size() );
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * ENGRAM METHODS
     */

    public boolean isEngram( int position ) {
        return position >= ( mStations.size() + mCategories.size() );
    }

    public DisplayEngram getEngram( int position ) throws ExceptionUtil.ArrayElementNullException,
            ExceptionUtil.IndexOutOfBoundsException {
        position -= ( mStations.size() + mCategories.size() );

        if ( position >= 0 && position < mEngrams.size() ) {
            DisplayEngram engram = mEngrams.valueAt( position );

            if ( engram == null )
                throw new ExceptionUtil.ArrayElementNullException( position, mEngrams.toString() );

            return engram;
        } else {
            throw new ExceptionUtil.IndexOutOfBoundsException( position, mEngrams.toString() );
        }
    }

    private SparseArray<DisplayEngram> getEngrams( Uri uri ) throws ExceptionUtil.CursorNullException {
        SparseArray<DisplayEngram> engrams = new SparseArray<>();

        Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        while ( cursor.moveToNext() ) {
            engrams.put(
                    engrams.size(),
                    new DisplayEngram(
                            cursor.getLong( cursor.getColumnIndex( EngramEntry._ID ) ),
                            cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) ),
                            cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_DRAWABLE ) ),
                            cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) ),
                            cursor.getLong( cursor.getColumnIndex( EngramEntry.COLUMN_CATEGORY_KEY ) )
                    )
            );
        }

        cursor.close();

        return engrams;
    }

    /**
     * CATEGORY METHODS
     */

    public boolean isCategory( int position ) {
        return position >= mStations.size() && position < mCategories.size();
    }

    public void changeCategory( int position ) throws Exception {
        Category category = getCategory( position );

        Log.d( TAG, "Changing category to [" + position + "] " + category.toString() );

        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();
        if ( isFilteredByStation() ) {
            if ( isCurrentCategoryLevelRoot() ) {
                if ( position == 0 ) {
                    // Back button to station list
                    setCurrentCategoryLevelsToStationRoot();
                } else {
                    // Normal Category object
                    // Grabbing ID is the best way to track its location.
                    setCurrentCategoryLevels( category.getId(), category.getParent() );
                }
            } else {
                if ( position == 0 ) {
                    // Back button to previous category list
                    if ( isRoot( category.getParent() ) ) {
                        setCurrentCategoryLevelsToRoot();
                    } else {
                        setCurrentCategoryLevels( category.getParent(),
                                getCategory( CategoryEntry.buildUriWithId(
                                        dlc_id, category.getParent() ) ).getParent() );
                    }
                } else {
                    // Normal Category object
                    // Grabbing ID is the best way to track its location.
                    setCurrentCategoryLevels( category.getId(), category.getParent() );
                }
            }
        } else {
            if ( isCurrentCategoryLevelRoot() ) {
                // Normal Category object
                // Grabbing ID is the best way to track its location.
                setCurrentCategoryLevels( category.getId(), category.getParent() );
            } else {
                if ( position == 0 ) {
                    // Back button to previous category list
                    if ( isRoot( category.getParent() ) ) {
                        setCurrentCategoryLevelsToRoot();
                    } else {
                        setCurrentCategoryLevels( category.getParent(),
                                getCategory( CategoryEntry.buildUriWithId(
                                        dlc_id, category.getParent() ) ).getParent() );
                    }
                } else {
                    // Normal Category object
                    // Grabbing ID is the best way to track its location.
                    setCurrentCategoryLevels( category.getId(), category.getParent() );
                }
            }
        }

        // Update lists with new data
        UpdateData();
    }

    private Category getCategory( int position ) throws ExceptionUtil.ArrayElementNullException,
            ExceptionUtil.IndexOutOfBoundsException {

        Category category;
        if ( position >= 0 && position < mCategories.size() ) {
            category = mCategories.valueAt( position );

            if ( category == null )
                throw new ExceptionUtil.ArrayElementNullException( position, mCategories.toString() );
        } else {
            throw new ExceptionUtil.IndexOutOfBoundsException( position, mCategories.toString() );
        }

        return category;
    }

    private Category getCategory( Uri uri ) throws ExceptionUtil.CursorNullException,
            ExceptionUtil.CursorEmptyException {
        Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        if ( !cursor.moveToFirst() )
            throw new ExceptionUtil.CursorEmptyException( uri );

        String name = cursor.getString( cursor.getColumnIndex( CategoryEntry.COLUMN_NAME ) );
        long parent_id = cursor.getLong( cursor.getColumnIndex( CategoryEntry.COLUMN_PARENT_KEY ) );

        Category category = new Category(
                CategoryEntry.getIdFromUri( uri ),
                name,
                parent_id );

        cursor.close();
        return category;
    }

    private SparseArray<Category> getCategories( Uri uri ) throws Exception {
        SparseArray<Category> categories = new SparseArray<>();

        // Build 'Back Buttons' if need be
        long dlc_id = CategoryEntry.getDLCIdFromUri( uri );
        if ( isFilteredByStation() ) {
            if ( isCurrentCategoryLevelRoot() ) {
                // If going back to station root (list of stations)
                categories.put( 0, BuildBackCategoryToStationRoot() );
            } else {
                if ( isRoot( getCurrentCategoryParent() ) ) {
                    // If going back to a station (list of categories)
                    Uri stationUri = StationEntry.buildUriWithId( dlc_id, getCurrentStationId() );
                    String stationName = getStation( stationUri ).getName();

                    categories.put( 0, BuildBackCategoryToStation( stationName ) );
                } else {
                    // If just going back to previous category
                    Uri categoryUri = CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryParent() );
                    String categoryName = getCategory( categoryUri ).getName();

                    categories.put( 0, BuildBackCategory( categoryName ) );
                }
            }
        } else {
            if ( !isCurrentCategoryLevelRoot() ) {
                if ( isRoot( getCurrentCategoryParent() ) ) {
                    // If going back to root (list of categories)
                    categories.put( 0, BuildBackCategoryToRoot() );
                } else {
                    // If just going back to previous category
                    Uri categoryUri = CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryParent() );
                    String categoryName = getCategory( categoryUri ).getName();
                    categories.put( 0, BuildBackCategory( categoryName ) );
                }
            }
        }

        Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        List<Long> categoryIdList = new ArrayList<>();
        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( CategoryEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( CategoryEntry.COLUMN_PARENT_KEY ) );

            if ( !categoryIdList.contains( _id ) ) {
                categoryIdList.add( _id );
                categories.put( categories.size(), new Category( _id, name, parent_id ) );
            }
        }

        cursor.close();
        return categories;
    }

    private Category BuildBackCategory( String name ) {
        return new Category(
                getCurrentCategoryLevel(),
                String.format( getContext().getString( R.string.format_category_go_back ), name ),
                getCurrentCategoryParent(),
                "back" );
    }

    private Category BuildBackCategoryToRoot() {
        return new Category(
                getCurrentCategoryLevel(),
                getContext().getString( R.string.go_back ),
                getCurrentCategoryParent(),
                "back" );
    }

    private Category BuildBackCategoryToStation( String name ) {
        return new Category(
                getCurrentCategoryLevel(),
                String.format( getContext().getString( R.string.format_station_go_back ), name ),
                getCurrentCategoryParent(),
                "back" );
    }

    private Category BuildBackCategoryToStationRoot() {
        return new Category(
                getCurrentStationId(),
                getContext().getString( R.string.go_back_to_stations ),
                STATION_ROOT,
                "back" );
    }

    /**
     * STATION METHODS
     */

    public boolean isStation( int position ) {
        return position < mStations.size();
    }

    public void changeStation( int position ) throws Exception {
        Station station = getStation( position );

        Log.d( TAG, "Changing station to [" + position + "] " + station.toString() );

        setCurrentStationId( station.getId() );
        setCurrentCategoryLevelsToRoot();
    }

    private Station getStation( int position ) throws Exception {

        Station station;
        if ( position >= 0 && position < mStations.size() ) {
            station = mStations.valueAt( position );

            if ( station == null )
                throw new ExceptionUtil.ArrayElementNullException( position, mStations.toString() );
        } else {
            throw new ExceptionUtil.IndexOutOfBoundsException( position, mStations.toString() );
        }

        return station;
    }

    private Station getStation( Uri uri ) throws Exception {
        Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        if ( !cursor.moveToFirst() )
            throw new ExceptionUtil.CursorEmptyException( uri );

        Station station = new Station(
                StationEntry.getIdFromUri( uri ),
                cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_NAME ) ),
                cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_DRAWABLE ) ),
                StationEntry.getDLCIdFromUri( uri ) );

        cursor.close();
        return station;
    }

    private SparseArray<Station> getStations( Uri uri ) throws Exception {
        SparseArray<Station> stations = new SparseArray<>();

        Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null );

        if ( cursor == null )
            throw new ExceptionUtil.CursorNullException( uri );

        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( StationEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_NAME ) );
            String drawable = cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_DRAWABLE ) );
            long dlc_id = cursor.getLong( cursor.getColumnIndex( StationEntry.COLUMN_DLC_KEY ) );

            stations.append( stations.size(), new Station( _id, name, drawable, dlc_id ) );
        }

        cursor.close();
        return stations;
    }

    /**
     * QUEUE METHODS
     */

    private Queue getQueue( long engram_id ) {
        return mQueues.get( engram_id );
    }

    private LongSparseArray<Queue> getQueues() {
        Cursor cursor = getContext().getContentResolver().query(
                QueueEntry.CONTENT_URI, null, null, null, null );

        if ( cursor == null )
            return new LongSparseArray<>();

        LongSparseArray<Queue> queues = new LongSparseArray<>();
        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( QueueEntry._ID ) );
            long engramId = cursor.getLong( cursor.getColumnIndex( QueueEntry.COLUMN_ENGRAM_KEY ) );
            int quantity = cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) );

            queues.put( engramId, new Queue( _id, engramId, quantity ) );
        }

        cursor.close();
        return queues;
    }

    /**
     *
     */

    public String getHierarchicalText() throws Exception {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        StringBuilder builder = new StringBuilder();
        if ( isFilteredByStation() ) {
            builder.append( "Crafting Stations/" );

            if ( !isCurrentCategoryLevelStationRoot() ) {
                builder.append( getStation( StationEntry.buildUriWithId( dlc_id, getCurrentStationId() ) )
                        .getName() ).append( "/" );
                if ( isFilteredByCategory() ) {
                    if ( !isCurrentCategoryLevelRoot() )
                        builder.append( getCategoryHierarchicalText( CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryLevel() ) ) );
                }
            }
        } else if ( isFilteredByCategory() ) {
            builder.append( "../" );
            if ( !isCurrentCategoryLevelRoot() )
                builder.append( getCategoryHierarchicalText( CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryLevel() ) ) );
        } else {
            builder.append( "Engrams/" );
        }

        return builder.toString();
    }

    private String getCategoryHierarchicalText( Uri uri ) throws Exception {
        Category category = getCategory( uri );

        StringBuilder builder = new StringBuilder( category.getName() ).append( "/" );

        while ( !isRoot( category.getParent() ) ) {
            category = getCategory(
                    CategoryEntry.buildUriWithId(
                            CategoryEntry.getDLCIdFromUri( uri ), category.getParent() ) );

            builder.insert( 0, category.getName() + "/" );
        }

        return builder.toString();
    }

    private boolean isIndexInBounds( int position, int size ) {
        return position >= 0 && position < size;
    }

    public void UpdateData() throws Exception {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        mEngrams = new SparseArray<>();
        mCategories = new SparseArray<>();
        mStations = new SparseArray<>();

        Uri engramUri;
        if ( isFilteredByCategory() ) {
            if ( isFilteredByStation() ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    mStations = getStations( StationEntry.buildUriWithDLCId( dlc_id ) );
                } else {
                    if ( isFilteredByLevel() ) {
                        engramUri = EngramEntry.buildUriWithCategoryIdStationIdAndLevel(
                                dlc_id,
                                getCurrentCategoryLevel(),
                                getCurrentStationId(),
                                getRequiredLevelPref() );
                    } else {
                        engramUri = EngramEntry.buildUriWithCategoryIdAndStationId(
                                dlc_id,
                                getCurrentCategoryLevel(),
                                getCurrentStationId() );
                    }
                    mEngrams = getEngrams( engramUri );

                    Uri categoryUri = CategoryEntry.buildUriWithStationId( dlc_id, getCurrentCategoryLevel(), getCurrentStationId() );
                    mCategories = getCategories( categoryUri );
                }
            } else {
                if ( isFilteredByLevel() ) {
                    engramUri = EngramEntry.buildUriWithCategoryIdAndLevel(
                            dlc_id,
                            getCurrentCategoryLevel(),
                            getRequiredLevelPref() );
                } else {
                    engramUri = EngramEntry.buildUriWithCategoryId(
                            dlc_id,
                            getCurrentCategoryLevel() );
                }
                mEngrams = getEngrams( engramUri );

                Uri categoryUri = CategoryEntry.buildUriWithParentId( dlc_id, getCurrentCategoryLevel() );
                mCategories = getCategories( categoryUri );
            }
        } else {
            if ( isFilteredByStation() ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    mStations = getStations( StationEntry.buildUriWithDLCId( dlc_id ) );
                } else {
                    if ( isFilteredByLevel() ) {
                        engramUri = EngramEntry.buildUriWithStationIdAndLevel(
                                dlc_id,
                                getCurrentStationId(),
                                getRequiredLevelPref() );
                    } else {
                        engramUri = EngramEntry.buildUriWithStationId(
                                dlc_id,
                                getCurrentStationId() );
                    }
                    mEngrams = getEngrams( engramUri );
                    mCategories.put( 0, BuildBackCategoryToStationRoot() );
                }
            } else {
                if ( isFilteredByLevel() ) {
                    engramUri = EngramEntry.buildUriWithLevel( dlc_id, getRequiredLevelPref() );
                } else {
                    engramUri = EngramEntry.buildUriWithDLCId( dlc_id );
                }
                mEngrams = getEngrams( engramUri );
            }
        }

        mQueues = getQueues();
    }
}
