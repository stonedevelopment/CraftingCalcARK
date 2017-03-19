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
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.listeners.CraftingQueueListener;
import arc.resource.calculator.listeners.DisplayCaseListener;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.ListenerUtil;
import arc.resource.calculator.util.PrefsUtil;

public class DisplayCase
        implements DisplayCaseListener, CraftingQueueListener {
    private static final String TAG = DisplayCase.class.getSimpleName();
    private static final long ROOT = 0;
    private static final long STATION_ROOT = -1;
    private static final long SEARCH_ROOT = -2;

    private final int INVALID_ITEM_ID = -1;
    private final int INVALID_ITEM_POSITION = -1;

    private long mLastCategoryLevel;
    private long mLastCategoryParent;
    private long mStationId;

    private final List<Long> mKeys;
    private final List<Object> mValues;

    private final ListenerUtil mCallback;

    private String mSearchQuery;

    private static DisplayCase sInstance;

    public static DisplayCase getInstance( Context context, boolean didUpdate ) {
        if ( sInstance == null ) {
            sInstance = new DisplayCase( context, didUpdate );
        }

        return sInstance;
    }

    private DisplayCase( Context context, boolean didUpdate ) {
        PrefsUtil prefs = new PrefsUtil( context );

        if ( didUpdate ) {
            // Reset category levels
            setCategoryLevelsToRoot( context );
        } else {
            // Retrieve last viewed category level and parent from previous use
            mLastCategoryLevel = prefs.getLastCategoryLevel();
            mLastCategoryParent = prefs.getLastCategoryParent();
        }

        // Retrieve last viewed Station id
        mStationId = prefs.getLastStationId();

        mKeys = new ArrayList<>();
        mValues = new ArrayList<>();

        mCallback = ListenerUtil.getInstance();
        mCallback.addDisplayCaseListener( this );
        mCallback.addCraftingQueueListener( this );

        mSearchQuery = null;

        mCallback.requestDisplayCaseDataSetChange( context );
    }

    private boolean isFilteredByCategory( Context context ) {
        return new PrefsUtil( context ).getCategoryFilterPreference();
    }

    private boolean isFilteredByStation( Context context ) {
        return new PrefsUtil( context ).getStationFilterPreference();
    }

    private boolean isFilteredByLevel( Context context ) {
        return new PrefsUtil( context ).getLevelFilterPreference();
    }

    private boolean isCurrentCategoryLevelRoot() {
        return getCurrentCategoryLevel() == ROOT;
    }

    private boolean isCurrentCategoryLevelStationRoot() {
        return getCurrentCategoryLevel() == STATION_ROOT;
    }

    private boolean isCurrentCategoryParentLevelRoot() {
        return getCurrentCategoryParent() == ROOT;
    }

    private boolean isCurrentCategoryParentLevelStationRoot() {
        return getCurrentCategoryParent() == STATION_ROOT;
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

    private void setCategoryLevelsToRoot( Context context ) {
        if ( isFilteredByStation( context ) ) {
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

    private void saveCategoryLevelsToPref( Context context ) {
        PrefsUtil prefs = new PrefsUtil( context );

        if ( prefs.getCategoryFilterPreference() )
            prefs.saveCategoryLevels( getCurrentCategoryLevel(), getCurrentCategoryParent() );

        if ( prefs.getStationFilterPreference() )
            prefs.saveStationId( getCurrentStationId() );

        Log.d( TAG, "SaveSettings(): level: " + getCurrentCategoryLevel() +
                ", parent: " + getCurrentCategoryParent() +
                ", station: " + getCurrentStationId()
        );
    }

    public long getCurrentStationId() {
        return mStationId;
    }

    private void setCurrentStationId( long stationId ) {
        mStationId = stationId;
    }

    private int getRequiredLevelPref( Context context ) {
        return new PrefsUtil( context ).getRequiredLevel();
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    public long getIdByPosition( int position ) {
        try {
            Object o = mValues.get( position );

            if ( isStation( o ) )
                return ( ( Station ) o ).getId();

            if ( isCategory( o ) )
                return ( ( Category ) o ).getId();

            if ( isEngram( o ) )
                return ( ( DisplayEngram ) o ).getId();

            throw new IndexOutOfBoundsException();
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return INVALID_ITEM_ID;
    }

    public String getImagePathByPosition( int position ) {
        try {
            Object o = mValues.get( position );

            if ( isStation( o ) )
                return ( ( Station ) o ).getImagePath();

            if ( isCategory( o ) )
                return ( ( Category ) o ).getImagePath();

            if ( isEngram( o ) )
                return ( ( DisplayEngram ) o ).getImagePath();

            throw new IndexOutOfBoundsException();
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return null;
    }

    public String getNameByPosition( int position ) {
        try {
            Object o = mValues.get( position );

            if ( isStation( o ) )
                return ( ( Station ) o ).getName();

            if ( isCategory( o ) )
                return ( ( Category ) o ).getName();

            if ( isEngram( o ) )
                return ( ( DisplayEngram ) o ).getName();

            throw new IndexOutOfBoundsException();
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return null;
    }

    public int getQuantityWithYieldByPosition( int position ) {
        return getEngram( position ).getQuantityWithYield();
    }

    public int getCount() {
        return mValues.size();
    }

    /**
     * ENGRAM METHODS
     */

    public boolean isEngram( int position ) {
        return position > INVALID_ITEM_POSITION && mValues.get( position ) instanceof DisplayEngram;
    }

    private boolean isEngram( Object o ) {
        return o instanceof DisplayEngram;
    }

    public DisplayEngram getEngram( int position ) {
        return ( DisplayEngram ) mValues.get( position );
    }

    private List<DisplayEngram> queryForEngrams( Context context, Uri uri ) {
        List<Long> keys = new ArrayList<>();
        List<DisplayEngram> values = new ArrayList<>();

        long dlc_id = EngramEntry.getDLCIdFromUri( uri );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            LongSparseArray<Queue> queues = queryForQueues( context );

            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( EngramEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) );
                String folder = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FOLDER ) );
                String file = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FILE ) );
                int yield = cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) );
                long category_id = cursor.getLong( cursor.getColumnIndex( EngramEntry.COLUMN_CATEGORY_KEY ) );

                if ( !keys.contains( _id ) ) {
                    int quantity = 0;

                    if ( queues.indexOfKey( _id ) > INVALID_ITEM_POSITION )
                        quantity = queues.get( _id ).getQuantity();

                    keys.add( _id );
                    values.add( new DisplayEngram( _id, name, folder, file, yield, category_id, quantity ) );
                }
            }
        } catch ( Exception e ) {
            mCallback.emitSendErrorReport( TAG, e );
        }

        return values;
    }

    private List<DisplayEngram> querySearchForEngrams( Context context, Uri uri ) {
        List<Long> keys = new ArrayList<>();
        List<DisplayEngram> values = new ArrayList<>();

        long dlc_id = EngramEntry.getDLCIdFromUri( uri );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                return new ArrayList<>();

            LongSparseArray<Queue> queues = queryForQueues( context );

            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( EngramEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) );
                String folder = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FOLDER ) );
                String file = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FILE ) );
                int yield = cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) );
                long category_id = cursor.getLong( cursor.getColumnIndex( EngramEntry.COLUMN_CATEGORY_KEY ) );

                if ( !keys.contains( _id ) ) {
                    int quantity = 0;

                    if ( queues.indexOfKey( _id ) > INVALID_ITEM_POSITION )
                        quantity = queues.get( _id ).getQuantity();

                    keys.add( _id );
                    values.add( new DisplayEngram( _id, name, folder, file, yield, category_id, quantity ) );
                }
            }
        } catch ( Exception e ) {
            mCallback.emitSendErrorReport( TAG, e );
        }

        return values;
    }

    private DisplayEngram queryForEngram( Context context, Uri uri, int quantity ) {
        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                return null;

            if ( !cursor.moveToFirst() )
                return null;

            long _id = cursor.getLong( cursor.getColumnIndex( EngramEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_NAME ) );
            String folder = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( EngramEntry.COLUMN_IMAGE_FILE ) );
            int yield = cursor.getInt( cursor.getColumnIndex( EngramEntry.COLUMN_YIELD ) );
            long category_id = cursor.getLong( cursor.getColumnIndex( EngramEntry.COLUMN_CATEGORY_KEY ) );

            return new DisplayEngram( _id, name, folder, file, yield, category_id, quantity );
        }
    }

    /**
     * CATEGORY METHODS
     */

    public boolean isCategory( int position ) {
        return position > INVALID_ITEM_POSITION && mValues.get( position ) instanceof Category;
    }

    private boolean isCategory( Object o ) {
        return o instanceof Category;
    }

    private Category getCategory( int position ) {
        return ( Category ) mValues.get( position );
    }

    public void changeCategory( Context context, int position ) {
        try {
            Category category = getCategory( position );

            Log.d( TAG, "Changing category to [" + position + "] " + category.toString() );

            long dlc_id = new PrefsUtil( context ).getDLCPreference();
            if ( position == 0 ) {
                if ( category.getParent() == SEARCH_ROOT ) {
                    // backing out of search view
                    mSearchQuery = null;
                } else {
                    if ( isCurrentCategoryLevelRoot() ) {
                        if ( isFilteredByStation( context ) ) {
                            // Back button to station list
                            setCurrentCategoryLevelsToStationRoot();
                        } else {
                            // Normal Category object
                            // Grabbing ID is the best way to track its location.
                            setCurrentCategoryLevels( category.getId(), category.getParent() );
                        }
                    } else {
                        if ( isCurrentCategoryParentLevelRoot() ) {
                            // Back button to category list
                            setCurrentCategoryLevelsToRoot();
                        } else {
                            // Normal Back Category object
                            // Query for details via its Parent Level
                            setCurrentCategoryLevels( category.getParent(),
                                    queryForCategory( context,
                                            CategoryEntry.buildUriWithId( dlc_id, category.getParent() ) ).getParent() );
                        }
                    }
                }
            } else {
                // Normal Category object
                // Grabbing ID is the best way to track its location.
                setCurrentCategoryLevels( category.getId(), category.getParent() );
            }

            mCallback.requestDisplayCaseDataSetChange( context );
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }
    }

    private Category queryForCategory( Context context, Uri uri ) {
        Category category = null;

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            category = new Category(
                    CategoryEntry.getIdFromUri( uri ),
                    cursor.getString( cursor.getColumnIndex( CategoryEntry.COLUMN_NAME ) ),
                    cursor.getLong( cursor.getColumnIndex( CategoryEntry.COLUMN_PARENT_KEY ) ) );
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return category;
    }

    private List<Category> queryForCategories( Context context, Uri uri ) {
        List<Category> values = new ArrayList<>();

        // Build 'Back Buttons' if need be
        long dlc_id = CategoryEntry.getDLCIdFromUri( uri );
        if ( isFilteredByStation( context ) ) {
            if ( isCurrentCategoryLevelRoot() ) {
                // If going back to station root (list of stations)
                values.add( BuildBackCategoryToStationRoot( context ) );
            } else {
                if ( isRoot( getCurrentCategoryParent() ) ) {
                    // If going back to a station (list of categories)
                    Uri stationUri = StationEntry.buildUriWithId( dlc_id, getCurrentStationId() );
                    String stationName = queryForStation( context, stationUri ).getName();

                    values.add( BuildBackCategoryToStation( context, stationName ) );
                } else {
                    // If just going back to previous category
                    Uri categoryUri = CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryParent() );
                    String categoryName = queryForCategory( context, categoryUri ).getName();

                    values.add( BuildBackCategory( context, categoryName ) );
                }
            }
        } else {
            if ( !isCurrentCategoryLevelRoot() ) {
                if ( isRoot( getCurrentCategoryParent() ) ) {
                    // If going back to root (list of categories)
                    values.add( BuildBackCategoryToRoot( context ) );
                } else {
                    // If just going back to previous category
                    Uri categoryUri = CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryParent() );
                    String categoryName = queryForCategory( context, categoryUri ).getName();
                    values.add( BuildBackCategory( context, categoryName ) );
                }
            }
        }

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            List<Long> keys = new ArrayList<>();
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( CategoryEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( CategoryEntry.COLUMN_NAME ) );
                long parent_id = cursor.getLong( cursor.getColumnIndex( CategoryEntry.COLUMN_PARENT_KEY ) );

                if ( !keys.contains( _id ) ) {
                    keys.add( _id );
                    values.add( new Category( _id, name, parent_id ) );
                }
            }
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return values;
    }

    private Category BuildBackCategory( Context context, String name ) {
        return new BackCategory(
                getCurrentCategoryLevel(),
                String.format( context.getString( R.string.format_category_go_back ), name ),
                getCurrentCategoryParent() );
    }

    private Category BuildBackCategoryToRoot( Context context ) {
        return new BackCategory(
                getCurrentCategoryLevel(),
                context.getString( R.string.go_back ),
                getCurrentCategoryParent() );
    }

    private Category BuildBackCategoryToStation( Context context, String name ) {
        return new BackCategory(
                getCurrentCategoryLevel(),
                String.format( context.getString( R.string.format_station_go_back ), name ),
                getCurrentCategoryParent() );
    }

    private Category BuildBackCategoryToStationRoot( Context context ) {
        return new BackCategory(
                getCurrentStationId(),
                context.getString( R.string.go_back_to_stations ),
                STATION_ROOT );
    }

    private Category BuildBackCategoryOutOfSearch( Context context ) {
        return new BackCategory(
                SEARCH_ROOT,
                context.getString( R.string.category_go_back ),
                SEARCH_ROOT );
    }

    /**
     * STATION METHODS
     */

    public boolean isStation( int position ) {
        return position > INVALID_ITEM_POSITION && mValues.get( position ) instanceof Station;
    }

    private boolean isStation( Object o ) {
        return o instanceof Station;
    }

    private Station getStation( int position ) {
        return ( Station ) mValues.get( position );
    }

    public void changeStation( Context context, int position ) {
        Station station = getStation( position );

        Log.d( TAG, "Changing station to [" + position + "] " + station.toString() );

        setCurrentStationId( station.getId() );
        setCurrentCategoryLevelsToRoot();

        mCallback.requestDisplayCaseDataSetChange( context );
    }

    private Station queryForStation( Context context, Uri uri ) {
        Station station = null;

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            station = new Station(
                    StationEntry.getIdFromUri( uri ),
                    cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_NAME ) ),
                    cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_IMAGE_FOLDER ) ),
                    cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_IMAGE_FILE ) ),
                    StationEntry.getDLCIdFromUri( uri ) );
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return station;
    }

    private List<Station> queryForStations( Context context, Uri uri ) {
        List<Station> values = new ArrayList<>();

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( StationEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_NAME ) );
                String folder = cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_IMAGE_FOLDER ) );
                String file = cursor.getString( cursor.getColumnIndex( StationEntry.COLUMN_IMAGE_FILE ) );
                long dlc_id = cursor.getLong( cursor.getColumnIndex( StationEntry.COLUMN_DLC_KEY ) );

                values.add( new Station( _id, name, folder, file, dlc_id ) );
            }
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return values;
    }

    /**
     * QUEUE METHODS
     */

    private LongSparseArray<Queue> queryForQueues( Context context ) {
        LongSparseArray<Queue> queues = new LongSparseArray<>();

        try ( Cursor cursor = context.getContentResolver().query(
                QueueEntry.CONTENT_URI, null, null, null, null ) ) {

            if ( cursor == null )
                return new LongSparseArray<>();

            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( QueueEntry._ID ) );
                long engramId = cursor.getLong( cursor.getColumnIndex( QueueEntry.COLUMN_ENGRAM_KEY ) );
                int quantity = cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) );

                queues.put( engramId, new Queue( _id, engramId, quantity ) );
            }
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return queues;
    }

    private Queue queryForQueueByEngramId( Context context, long engram_id ) {
        Queue queue = null;

        try ( Cursor cursor = context.getContentResolver().query(
                QueueEntry.buildUriWithEngramId( engram_id ), null, null, null, null ) ) {

            if ( cursor == null )
                return null;

            if ( !cursor.moveToFirst() )
                return null;

            long _id = cursor.getLong( cursor.getColumnIndex( QueueEntry._ID ) );
            long engramId = cursor.getLong( cursor.getColumnIndex( QueueEntry.COLUMN_ENGRAM_KEY ) );
            int quantity = cursor.getInt( cursor.getColumnIndex( QueueEntry.COLUMN_QUANTITY ) );

            queue = new Queue( _id, engramId, quantity );
        } catch ( Exception e ) {
            mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
        }

        return queue;
    }

    /**
     *
     */

    public String buildHierarchicalText( Context context ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();

        StringBuilder builder = new StringBuilder();
        if ( mSearchQuery == null ) {
            if ( isFilteredByStation( context ) ) {
                builder.append( "Crafting Stations/" );

                if ( !isCurrentCategoryLevelStationRoot() ) {
                    builder.append( queryForStation( context, StationEntry.buildUriWithId( dlc_id, getCurrentStationId() ) )
                            .getName() ).append( "/" );
                    if ( isFilteredByCategory( context ) ) {
                        if ( !isCurrentCategoryLevelRoot() )
                            builder.append( buildCategoryHierarchicalText(
                                    context,
                                    CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryLevel() ) ) );
                    }
                }
            } else if ( isFilteredByCategory( context ) ) {
                builder.append( "Folders/" );
                if ( !isCurrentCategoryLevelRoot() )
                    builder.append( buildCategoryHierarchicalText(
                            context,
                            CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryLevel() ) ) );
            } else {
                builder.append( "Engrams/" );
            }
        } else {
            builder.append( String.format( context.getString( R.string.format_search_results ), mSearchQuery ) );
        }

        return builder.toString();
    }

    private String buildCategoryHierarchicalText( Context context, Uri uri ) {
        Category category = queryForCategory( context, uri );

        StringBuilder builder = new StringBuilder( category.getName() ).append( "/" );

        while ( !isRoot( category.getParent() ) ) {
            category = queryForCategory(
                    context,
                    CategoryEntry.buildUriWithId(
                            CategoryEntry.getDLCIdFromUri( uri ), category.getParent() ) );

            builder.insert( 0, category.getName() + "/" );
        }

        return builder.toString();
    }

    /**
     *
     */

    private void RefreshData( Context context ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();

        List<Station> stationMap = new ArrayList<>();
        List<Category> categoryMap = new ArrayList<>();
        List<DisplayEngram> engramMap = new ArrayList<>();

        Uri engramUri;
        if ( isFilteredByCategory( context ) ) {
            if ( isFilteredByStation( context ) ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    stationMap = queryForStations( context, StationEntry.buildUriWithDLCId( dlc_id ) );
                } else {
                    if ( isFilteredByLevel( context ) ) {
                        engramUri = EngramEntry.buildUriWithCategoryIdStationIdAndLevel(
                                dlc_id,
                                getCurrentCategoryLevel(),
                                getCurrentStationId(),
                                getRequiredLevelPref( context ) );
                    } else {
                        engramUri = EngramEntry.buildUriWithCategoryIdAndStationId(
                                dlc_id,
                                getCurrentCategoryLevel(),
                                getCurrentStationId() );
                    }
                    engramMap = queryForEngrams( context, engramUri );

                    Uri categoryUri = CategoryEntry.buildUriWithStationId( dlc_id, getCurrentCategoryLevel(), getCurrentStationId() );
                    categoryMap = queryForCategories( context, categoryUri );
                }
            } else {
                if ( isFilteredByLevel( context ) ) {
                    engramUri = EngramEntry.buildUriWithCategoryIdAndLevel(
                            dlc_id,
                            getCurrentCategoryLevel(),
                            getRequiredLevelPref( context ) );
                } else {
                    engramUri = EngramEntry.buildUriWithCategoryId(
                            dlc_id,
                            getCurrentCategoryLevel() );
                }
                engramMap = queryForEngrams( context, engramUri );

                Uri categoryUri = CategoryEntry.buildUriWithParentId( dlc_id, getCurrentCategoryLevel() );
                categoryMap = queryForCategories( context, categoryUri );
            }
        } else {
            if ( isFilteredByStation( context ) ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    stationMap = queryForStations( context, StationEntry.buildUriWithDLCId( dlc_id ) );
                } else {
                    if ( isFilteredByLevel( context ) ) {
                        engramUri = EngramEntry.buildUriWithStationIdAndLevel(
                                dlc_id,
                                getCurrentStationId(),
                                getRequiredLevelPref( context ) );
                    } else {
                        engramUri = EngramEntry.buildUriWithStationId(
                                dlc_id,
                                getCurrentStationId() );
                    }
                    engramMap = queryForEngrams( context, engramUri );

                    categoryMap.add( BuildBackCategoryToStationRoot( context ) );
                }
            } else {
                if ( isFilteredByLevel( context ) ) {
                    engramUri = EngramEntry.buildUriWithLevel( dlc_id, getRequiredLevelPref( context ) );
                } else {
                    engramUri = EngramEntry.buildUriWithDLCId( dlc_id );
                }
                engramMap = queryForEngrams( context, engramUri );
            }
        }

        mKeys.clear();

        for ( Station station : stationMap ) {
            mKeys.add( station.getId() );
        }

        for ( Category category : categoryMap ) {
            mKeys.add( category.getId() );
        }

        for ( DisplayEngram engram : engramMap ) {
            mKeys.add( engram.getId() );
        }

        mValues.clear();
        mValues.addAll( stationMap );
        mValues.addAll( categoryMap );
        mValues.addAll( engramMap );
    }

    private void SearchData( Context context ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();
        Uri uri = EngramEntry.buildUriWithSearchQuery( dlc_id, mSearchQuery );

        List<DisplayEngram> engramMap = querySearchForEngrams( context, uri );

        List<Category> categoryMap = new ArrayList<>();
        categoryMap.add( BuildBackCategoryOutOfSearch( context ) );

        mKeys.clear();

        for ( Category category : categoryMap ) {
            mKeys.add( category.getId() );
        }

        for ( DisplayEngram engram : engramMap ) {
            mKeys.add( engram.getId() );
        }

        mValues.clear();
        mValues.addAll( categoryMap );
        mValues.addAll( engramMap );
    }

    @Override
    public void onRequestRemoveOneFromQueue( Context context, long engram_id ) {

    }

    @Override
    public void onRequestRemoveAllFromQueue( Context context ) {

    }

    @Override
    public void onRequestIncreaseQuantity( Context context, int position ) {

    }

    @Override
    public void onRequestIncreaseQuantity( Context context, long engram_id ) {

    }

    @Override
    public void onRequestUpdateQuantity( Context context, long engram_id, int quantity ) {

    }

    @Override
    public void onRowInserted( Context context, long queueId, long engramId, int quantity,
                               boolean wasQueueEmpty ) {
        int position = mKeys.indexOf( engramId );

        if ( isEngram( position ) ) {
            getEngram( position ).setQuantity( quantity );
            mCallback.notifyItemChanged( position );
        }
    }

    @Override
    public void onRowUpdated( Context context, long queueId, long engramId, int quantity ) {
        int position = mKeys.indexOf( engramId );

        if ( isEngram( position ) ) {
            getEngram( position ).setQuantity( quantity );
            mCallback.notifyItemChanged( position );
        }
    }

    @Override
    public void onRowDeleted( Context context, Uri uri, int positionStart, int itemCount, boolean isQueueEmpty ) {
        if ( isQueueEmpty ) {
            mCallback.requestDisplayCaseDataSetChange( context );
        } else {
            long engramId = QueueEntry.getEngramIdFromUri( uri );
            int position = mKeys.indexOf( engramId );

            if ( isEngram( position ) ) {
                getEngram( position ).resetQuantity();
                mCallback.notifyItemChanged( position );
            }
        }
    }

    @Override
    public void onRequestResetCategoryLevels( Context context ) {
        setCategoryLevelsToRoot( context );
        saveCategoryLevelsToPref( context );
    }

    @Override
    public void onRequestSaveCategoryLevels( Context context ) {
        saveCategoryLevelsToPref( context );
    }

    @Override
    public void onRequestCategoryHierarchy( Context context ) {
        mCallback.notifyCategoryHierarchyResolved( buildHierarchicalText( context ) );
    }

    @Override
    public void onRequestSearch( Context context, String query ) {
        mSearchQuery = query;

        new QueryForDataTask( context ).executeOnExecutor( AsyncTask.SERIAL_EXECUTOR );
    }

    @Override
    public void onItemChanged( int position ) {
        // do nothing
    }

    @Override
    public void onRequestDisplayCaseDataSetChange( Context context ) {
        new QueryForDataTask( context ).executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
    }

    @Override
    public void onDisplayCaseDataSetChanged( Context context ) {
        mCallback.notifyCategoryHierarchyResolved( buildHierarchicalText( context ) );
    }

    private class QueryForDataTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = QueryForDataTask.class.getSimpleName();

        final Context mContext;

        QueryForDataTask( Context context ) {
            mContext = context;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            mCallback.notifyDataSetChanged( mContext );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            if ( mSearchQuery == null )
                RefreshData( mContext );
            else
                SearchData( mContext );
            return null;
        }
    }
}