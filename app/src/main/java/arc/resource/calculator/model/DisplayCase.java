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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.util.DbUtil;
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
    private HashMap<Long, Queue> mQueues;

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
        mQueues = new HashMap<>();

        UpdateData();
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

    public String getDrawableByPosition( int position ) {
        if ( isStation( position ) ) {
            return mStations.valueAt( position ).getDrawable();
        }

        if ( isCategory( position ) ) {
            return mCategories.valueAt( position ).getDrawable();
        }

        if ( isEngram( position ) ) {
            position -= ( mStations.size() + mCategories.size() );

            return mEngrams.valueAt( position ).getDrawable();
        }

        // Not a station, category, or engram
        Log.e( TAG, "getDrawableByPosition(): position is not recognized: " + position );
        return null;
    }

    public String getNameByPosition( int position ) {
        if ( isStation( position ) ) {
            return mStations.valueAt( position ).getName();
        }

        if ( isCategory( position ) ) {
            return mCategories.valueAt( position ).getName();
        }

        if ( isEngram( position ) ) {
            position -= ( mStations.size() + mCategories.size() );

            return mEngrams.valueAt( position ).getName();
        }

        // Not a station, category, or engram
        Log.e( TAG, "getNameByPosition(): position is not recognized: " + position );
        return null;
    }

    private String getNameByCategoryId( long categoryId ) {
        Category category = getCategoryById( categoryId );

        if ( category != null ) {
            return String.format( getContext().getString( R.string.format_category_go_back ), category.getName() );
        }

        return getContext().getString( R.string.category_go_back );
    }

    private String getNameByStationId( long stationId ) {
        Station station = getStationById( stationId );

        if ( station != null ) {
            return String.format( getContext().getString( R.string.format_station_go_back ), station.getName() );
        }

        return getContext().getString( R.string.station_go_back );
    }

    public int getQuantityWithYield( int position ) {
        DisplayEngram engram = getEngramByPosition( position );

        Queue queue = mQueues.get( engram.getId() );
        if ( queue == null ) {
            return 0;
        }

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

    public long getEngramId( int position ) {
        return getEngramByPosition( position ).getId();
    }

    public DisplayEngram getEngramByPosition( int position ) {
        position -= ( mStations.size() + mCategories.size() );

        return mEngrams.valueAt( position );
    }

    private SparseArray<DisplayEngram> getEngrams( Uri uri ) {
        if ( isCurrentCategoryLevelStationRoot() ) {
            return new SparseArray<>();
        }

        Log.d( TAG, uri.toString() );

        SparseArray<DisplayEngram> engrams = new SparseArray<>();
        List<String> engramNameList = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(
                uri, null, null, null, null );

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        while ( cursor.moveToNext() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );

            if ( !engramNameList.contains( name ) ) {
                engramNameList.add( name );
                engrams.put(
                        engrams.size(),
                        new DisplayEngram(
                                cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) ),
                                cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) ),
                                cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DRAWABLE ) ),
                                cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) ),
                                cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) )
                        )
                );
            }
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

    public void changeCategory( int position ) {
        Category category = mCategories.valueAt( position );

        Log.d( TAG, "Changing category to [" + position + "] " + category.toString() );

        if ( isFilteredByStation() ) {
            if ( isCurrentCategoryLevelRoot() ) {
                if ( position == 0 ) {
                    // Back button to station list
                    setCurrentCategoryLevelsToStationRoot();
                } else {
                    // Normal Category object
                    // Grabbing ID is the best way to track its location.
                    setCurrentCategoryLevels(
                            category.getId(),
                            category.getParent()
                    );
                }
            } else {
                if ( position == 0 ) {
                    // Back button to previous category list
                    if ( isRoot( category.getParent() ) ) {
                        setCurrentCategoryLevelsToRoot();
                    } else {
                        setCurrentCategoryLevels(
                                category.getParent(),
                                getCategoryById( category.getParent() ).getParent()
                        );
                    }
                } else {
                    // Normal Category object
                    // Grabbing ID is the best way to track its location.
                    setCurrentCategoryLevels(
                            category.getId(),
                            category.getParent()
                    );
                }
            }
        } else {
            if ( isCurrentCategoryLevelRoot() ) {
                // Normal Category object
                // Grabbing ID is the best way to track its location.
                setCurrentCategoryLevels(
                        category.getId(),
                        category.getParent()
                );
            } else {
                if ( position == 0 ) {
                    // Back button to previous category list
                    if ( isRoot( category.getParent() ) ) {
                        setCurrentCategoryLevelsToRoot();
                    } else {
                        setCurrentCategoryLevels(
                                category.getParent(),
                                getCategoryById( category.getParent() ).getParent()
                        );
                    }
                } else {
                    // Normal Category object
                    // Grabbing ID is the best way to track its location.
                    setCurrentCategoryLevels(
                            category.getId(),
                            category.getParent()
                    );
                }
            }
        }

        // Update lists with new data
        UpdateData();
    }

    public Category getCategoryById( long _id ) {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        // FIXME:   There are instances where categoryId = -1

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, _id ),
                null, null, null, null );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            cursor.close();
            return new Category(
                    _id,
                    name,
                    parent_id );
        }

        return null;
    }

    private SparseArray<Category> getCategories() {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        // Check to make sure parent isn't -1, happens when you press Stop in Android Studio, bleh.
        if ( getCurrentCategoryParent() < 0 ) {
            return new SparseArray<>();
        }

        SparseArray<Category> categories = new SparseArray<>();
        List<Long> categoryIdList = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithParentId( dlc_id, getCurrentCategoryLevel() ),
                null, null, null, null );

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        if ( !isCurrentCategoryLevelRoot() ) {
            categories.put(
                    categories.size(),
                    new Category(
                            getCurrentCategoryLevel(),
                            getNameByCategoryId( getCurrentCategoryParent() ),
                            getCurrentCategoryParent(),
                            "back" )
            );
        }

        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            if ( !categoryIdList.contains( _id ) ) {
                categoryIdList.add( _id );
                categories.put( categories.size(), new Category( _id, name, parent_id ) );
            }
        }

        cursor.close();

        return categories;
    }

    private SparseArray<Category> getCategoriesByStation() {
        if ( isCurrentCategoryLevelStationRoot() ) {
            return new SparseArray<>();
        }

        // Check to make sure parent isn't -1, happens when you press Stop in Android Studio, bleh.
        if ( getCurrentCategoryParent() < 0 ) {
            return new SparseArray<>();
        }

        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        SparseArray<Category> categories = new SparseArray<>();
        List<Long> categoryIdList = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithStationId( dlc_id, getCurrentCategoryLevel(), getCurrentStationId() ),
                null, null, null, null );

        if ( isCurrentCategoryLevelRoot() ) {
            categories.put(
                    categories.size(),
                    new Category(
                            getCurrentStationId(),
                            getContext().getString( R.string.format_go_back_to_stations ),
                            STATION_ROOT,
                            "back" )
            );
        } else {
            if ( isRoot( getCurrentCategoryParent() ) ) {
                categories.put(
                        categories.size(),
                        new Category(
                                getCurrentCategoryLevel(),
                                getNameByStationId( getCurrentStationId() ),
                                getCurrentCategoryParent(),
                                "back" )
                );
            } else {
                categories.put(
                        categories.size(),
                        new Category(
                                getCurrentCategoryLevel(),
                                getNameByCategoryId( getCurrentCategoryParent() ),
                                getCurrentCategoryParent(),
                                "back" )
                );
            }
        }

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            if ( !categoryIdList.contains( _id ) ) {
                categoryIdList.add( _id );
                categories.put( categories.size(), new Category( _id, name, parent_id ) );
            }
        }

        cursor.close();

        return categories;
    }

    private SparseArray<Category> getBackCategoryAsStation() {
        SparseArray<Category> categories = new SparseArray<>();

        categories.append( 0,
                new Category(
                        getCurrentStationId(),
                        getContext().getString( R.string.format_go_back_to_stations ),
                        STATION_ROOT,
                        "back" )
        );

        return categories;
    }

    /**
     * STATION METHODS
     */

    public boolean isStation( int position ) {
        return position < mStations.size();
    }

    public void changeStation( int position ) {
        Station station = mStations.valueAt( position );

        Log.d( TAG, "Changing station to [" + position + "] " + station.toString() );

        // Set as current station
        setCurrentStationId( station.getId() );

        // Set category back to root in order to display categories with this station
        setCurrentCategoryLevelsToRoot();
    }

    private Station getStationById( long _id ) {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.StationEntry.buildUriWithId( dlc_id, _id ),
                null, null, null, null );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );
            String drawable = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_DRAWABLE ) );

            cursor.close();
            return new Station(
                    _id,
                    name,
                    drawable,
                    dlc_id );
        }

        return null;
    }

    private SparseArray<Station> getStations() {
        long dlcId = new PrefsUtil( getContext() ).getDLCPreference();

        SparseArray<Station> stations = new SparseArray<>();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.StationEntry.buildUriWithDLCId( dlcId ),
                null, null, null, null );

        if ( cursor == null ) {
            return new SparseArray<>();
        }

        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.StationEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );
            String drawable = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_DRAWABLE ) );
            long dlc_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_DLC_KEY ) );

            stations.append( stations.size(), new Station( _id, name, drawable, dlc_id ) );
        }

        cursor.close();

        return stations;
    }

    /**
     * QUEUE METHODS
     */

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

    public String getHierarchicalText() {
        StringBuilder builder = new StringBuilder();

        if ( isFilteredByStation() ) {
            builder.append( "Crafting Stations/" );

            if ( !isCurrentCategoryLevelStationRoot() ) {
                builder.append( getStationById( getCurrentStationId() ).getName() ).append( "/" );
                if ( isFilteredByCategory() ) {
                    if ( !isCurrentCategoryLevelRoot() ) {
                        builder.append( getCategoryHierarchicalText() );
                    }
                }
            }
        } else if ( isFilteredByCategory() ) {
            builder.append( "../" );
            if ( !isCurrentCategoryLevelRoot() ) {
                builder.append( getCategoryHierarchicalText() );
            }
        } else {
            builder.append( "Engrams/" );
        }

        return builder.toString();
    }

    private String getCategoryHierarchicalText() {
        Category category = DbUtil.QueryForCategoryDetails( getContext(), getCurrentCategoryLevel() );

        StringBuilder builder = new StringBuilder();
        if ( category != null ) {
            builder.append( category.getName() ).append( "/" );

            long parent_id = category.getParent();
            while ( parent_id > 0 ) {
                category = DbUtil.QueryForCategoryDetails( getContext(), parent_id );
                if ( category == null ) break;

                parent_id = category.getParent();

                builder.insert( 0, category.getName() + "/" );
            }
        }

        return builder.toString();
    }

    public void UpdateData() {
        long dlc_id = new PrefsUtil( getContext() ).getDLCPreference();

        Uri engramUri;
        if ( isFilteredByCategory() ) {
            if ( isFilteredByStation() ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    mEngrams = new SparseArray<>();
                    mCategories = new SparseArray<>();
                    mStations = getStations();
                } else {
                    if ( isFilteredByLevel() ) {
                        engramUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdStationIdAndLevel(
                                dlc_id,
                                getCurrentCategoryLevel(),
                                getCurrentStationId(),
                                getRequiredLevelPref()
                        );
                    } else {
                        engramUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdAndStationId(
                                dlc_id,
                                getCurrentCategoryLevel(),
                                getCurrentStationId()
                        );
                    }

                    mEngrams = getEngrams( engramUri );
                    mCategories = getCategoriesByStation();
                    mStations = new SparseArray<>();
                }
            } else {
                if ( isFilteredByLevel() ) {
                    engramUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdAndLevel(
                            dlc_id,
                            getCurrentCategoryLevel(),
                            getRequiredLevelPref()
                    );
                } else {
                    engramUri = DatabaseContract.EngramEntry.buildUriWithCategoryId(
                            dlc_id,
                            getCurrentCategoryLevel()
                    );
                }

                mEngrams = getEngrams( engramUri );
                mCategories = getCategories();
                mStations = new SparseArray<>();
            }
        } else {
            if ( isFilteredByStation() ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    mEngrams = new SparseArray<>();
                    mCategories = new SparseArray<>();
                    mStations = getStations();
                } else {
                    if ( isFilteredByLevel() ) {
                        engramUri = DatabaseContract.EngramEntry.buildUriWithStationIdAndLevel(
                                dlc_id,
                                getCurrentStationId(),
                                getRequiredLevelPref()
                        );
                    } else {
                        engramUri = DatabaseContract.EngramEntry.buildUriWithStationId(
                                dlc_id,
                                getCurrentStationId()
                        );
                    }

                    mEngrams = getEngrams( engramUri );
                    mCategories = getBackCategoryAsStation();
                    mStations = new SparseArray<>();
                }
            } else {
                if ( isFilteredByLevel() ) {
                    engramUri = DatabaseContract.EngramEntry.buildUriWithLevel(
                            dlc_id,
                            getRequiredLevelPref()
                    );
                } else {
                    engramUri = DatabaseContract.EngramEntry.buildUriWithDLCId(
                            dlc_id
                    );
                }

                mEngrams = getEngrams( engramUri );
                mCategories = new SparseArray<>();
                mStations = new SparseArray<>();
            }
        }

        mQueues = getQueues();
    }
}
