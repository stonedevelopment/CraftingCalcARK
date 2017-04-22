package arc.resource.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.listeners.NavigationObserver;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.BackCategory;
import arc.resource.calculator.model.Category;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.Station;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;

import static arc.resource.calculator.adapters.CraftableAdapter.Status.HIDDEN;
import static arc.resource.calculator.adapters.CraftableAdapter.Status.VISIBLE;
import static arc.resource.calculator.util.Util.NO_ID;
import static arc.resource.calculator.util.Util.NO_NAME;
import static arc.resource.calculator.util.Util.NO_PATH;
import static arc.resource.calculator.util.Util.NO_QUANTITY;

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
public class CraftableAdapter extends RecyclerView.Adapter<CraftableAdapter.ViewHolder> {
    private static final String TAG = CraftableAdapter.class.getSimpleName();

    private static final long ROOT = 0;
    private static final long STATION_ROOT = -1;
    private static final long SEARCH_ROOT = -2;

    private long mLastCategoryLevel;
    private long mLastCategoryParent;
    private long mStationId;

    private String mSearchQuery;

    private StationSparseArray mStations;
    private CategorySparseArray mCategories;
    private CraftableSparseArray mCraftables;

    private Context mContext;
    private ExceptionObserver mExceptionObserver;

    private Status mViewStatus;

    enum Status {VISIBLE, HIDDEN}

    private PrefsObserver.Listener mPrefsListener = new PrefsObserver.Listener() {
        @Override
        public void onPreferencesChanged( boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange, boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange ) {
            setCategoryLevelsToRoot();
            refreshData();
        }

        @Override
        public void onSavePreferencesRequested() {
            saveCategoryLevelsToPref();
        }
    };

    private QueueObserver.Listener mListener = new QueueObserver.Listener() {
        public void onDataSetPopulated() {
            if ( mViewStatus == VISIBLE ) {
                updateQuantities();
            }
        }

        @Override
        public void onItemChanged( long craftableId, int quantity ) {
            if ( mViewStatus == VISIBLE ) {
                if ( getCraftables().contains( craftableId ) ) {
                    int position = getCraftables().indexOfKey( craftableId );
                    getCraftable( position ).setQuantity( quantity );
                    notifyItemChanged( adjustPositionFromCraftable( position ) );
                }
            }
        }

        @Override
        public void onItemRemoved( long craftableId ) {
            if ( mViewStatus == VISIBLE ) {
                if ( getCraftables().contains( craftableId ) ) {
                    int position = getCraftables().indexOfKey( craftableId );
                    getCraftable( position ).resetQuantity();
                    notifyItemChanged( adjustPositionFromCraftable( position ) );
                }
            }
        }

        @Override
        public void onDataSetEmpty() {
            Log.d( TAG, "onDataSetEmpty: " );
            if ( mViewStatus == VISIBLE ) {
                clearQuantities();
            }
        }
    };

    public CraftableAdapter( Context context ) {
        setContext( context );

        PrefsUtil prefs = PrefsUtil.getInstance();

        // Retrieve last viewed category level and parent from previous use
        setCurrentCategoryLevels( prefs.getLastCategoryLevel(), prefs.getLastCategoryParent() );

        // fixme: workaround correction for something that malforms categoryId to stationId while not in station mode
        if ( isFilteredByCategory() ) {
            if ( !isFilteredByStation() ) {
                if ( isCurrentCategoryLevelStationRoot() ) {
                    setCurrentCategoryLevelsToRoot();

                    mExceptionObserver.notifyExceptionCaught( TAG, new Exception( "Category level is Station Root, but we're not filtering by Station." ) );
                }
            }
        }

        // Retrieve last viewed Station id
        mStationId = prefs.getLastStationId();

        setStations( new StationSparseArray() );
        setCategories( new CategorySparseArray() );
        setCraftables( new CraftableSparseArray() );

        mExceptionObserver = ExceptionObserver.getInstance();

        mSearchQuery = null;

        refreshData();
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext( Context context ) {
        this.mContext = context.getApplicationContext();
    }

    private StationSparseArray getStations() {
        return mStations;
    }

    private void setStations( StationSparseArray stations ) {
        this.mStations = stations;
    }

    private CategorySparseArray getCategories() {
        return mCategories;
    }

    private void setCategories( CategorySparseArray categories ) {
        this.mCategories = categories;
    }

    private CraftableSparseArray getCraftables() {
        return mCraftables;
    }

    private void setCraftables( CraftableSparseArray craftables ) {
        this.mCraftables = craftables;
    }

    public void resume( RecyclerView.AdapterDataObserver observer ) {
        Log.d( TAG, "resume()" );

        registerAdapterDataObserver( observer );

        QueueObserver.getInstance().registerListener( mListener );
        PrefsObserver.getInstance().registerListener( mPrefsListener );

        mViewStatus = VISIBLE;

        updateQuantities();

        buildHierarchy();
    }

    public void pause( RecyclerView.AdapterDataObserver observer ) {
        Log.d( TAG, "pause()" );

        unregisterAdapterDataObserver( observer );

        QueueObserver.getInstance().unregisterListener( mListener );
        PrefsObserver.getInstance().unregisterListener( mPrefsListener );

        mViewStatus = HIDDEN;
    }

    private boolean isFilteredByCategory() {
        return PrefsUtil.getInstance().getCategoryFilterPreference();
    }

    private boolean isFilteredByStation() {
        return PrefsUtil.getInstance().getStationFilterPreference();
    }

    private boolean isFilteredByLevel() {
        return PrefsUtil.getInstance().getLevelFilterPreference();
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

    private long getCurrentCategoryLevel() {
        return mLastCategoryLevel;
    }

    private long getCurrentCategoryParent() {
        return mLastCategoryParent;
    }

    private void setCurrentCategoryLevels( long level, long parent ) {
        mLastCategoryLevel = level;
        mLastCategoryParent = parent;
    }

    private void setCategoryLevelsToRoot() {
        if ( isFilteredByStation() )
            setCurrentCategoryLevelsToStationRoot();
        else
            setCurrentCategoryLevelsToRoot();
    }

    private void setCurrentCategoryLevelsToRoot() {
        setCurrentCategoryLevels( ROOT, ROOT );
    }

    private void setCurrentCategoryLevelsToStationRoot() {
        setCurrentCategoryLevels( STATION_ROOT, STATION_ROOT );
    }

    private void saveCategoryLevelsToPref() {
        PrefsUtil prefs = PrefsUtil.getInstance();

        if ( prefs.getCategoryFilterPreference() )
            prefs.saveCategoryLevels( getCurrentCategoryLevel(), getCurrentCategoryParent() );

        if ( prefs.getStationFilterPreference() )
            prefs.saveStationId( getCurrentStationId() );
    }

    private long getCurrentStationId() {
        return mStationId;
    }

    private void setCurrentStationId( long stationId ) {
        mStationId = stationId;
    }

    private int getRequiredLevelPref() {
        return PrefsUtil.getInstance().getRequiredLevel();
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    private String getItemContents() {
        return getStations().toString() + ", " + getCategories().toString() + ", " + getCraftables().toString();
    }

    private int getSize() {
        return ( getStations().size() + getCategories().size() + getCraftables().size() );
    }

    private long getIdByPosition( int position ) {
        try {
            if ( isStation( position ) )
                return getStation( position ).getId();

            if ( isCategory( position ) )
                return getCategory( adjustPositionForCategory( position ) ).getId();

            if ( isCraftable( position ) )
                return getCraftable( adjustPositionForCraftable( position ) ).getId();

            throw new ExceptionUtil.PositionOutOfBoundsException( position, getItemCount(), getItemContents() );
        } catch ( ExceptionUtil.PositionOutOfBoundsException e ) {
            mExceptionObserver.notifyExceptionCaught( TAG, e );

            return NO_ID;
        }
    }

    private String getImagePathByPosition( int position ) {
        try {
            if ( isStation( position ) )
                return getStation( position ).getImagePath();

            if ( isCategory( position ) )
                return getCategory( adjustPositionForCategory( position ) ).getImagePath();

            if ( isCraftable( position ) )
                return getCraftable( adjustPositionForCraftable( position ) ).getImagePath();

            throw new ExceptionUtil.PositionOutOfBoundsException( position, getItemCount(), getItemContents() );
        } catch ( ExceptionUtil.PositionOutOfBoundsException e ) {
            mExceptionObserver.notifyExceptionCaught( TAG, e );

            return NO_PATH;
        }
    }

    private String getNameByPosition( int position ) {
        try {
            if ( isStation( position ) )
                return getStation( position ).getName();

            if ( isCategory( position ) )
                return getCategory( adjustPositionForCategory( position ) ).getName();

            if ( isCraftable( position ) )
                return getCraftable( adjustPositionForCraftable( position ) ).getName();

            throw new ExceptionUtil.PositionOutOfBoundsException( position, getItemCount(), getItemContents() );
        } catch ( ExceptionUtil.PositionOutOfBoundsException e ) {
            mExceptionObserver.notifyExceptionCaught( TAG, e );

            return NO_NAME;
        }
    }

    private int getQuantityWithYieldByPosition( int position ) {
        try {
            return getCraftable( adjustPositionForCraftable( position ) ).getQuantityWithYield();
        } catch ( Exception e ) {
            return NO_QUANTITY;
        }
    }

    /**
     * CRAFTABLE METHODS
     */

    private int adjustPositionForCraftable( int position ) {
        return position - ( getStations().size() + getCategories().size() );
    }

    private int adjustPositionFromCraftable( int position ) {
        return position + ( getStations().size() + getCategories().size() );
    }

    private boolean isCraftable( int position ) {
        return Util.isValidPosition( adjustPositionForCraftable( position ), getCraftables().size() );
    }

    private DisplayEngram getCraftable( int position ) {
        return getCraftables().valueAt( position );
    }

    public void increaseQuantity( int position ) {
        CraftingQueue.getInstance().increaseQuantity( getCraftable( adjustPositionForCraftable( position ) ) );
    }

    private void updateQuantities() {
        for ( int i = 0; i < getCraftables().size(); i++ ) {
            DisplayEngram craftable = getCraftable( i );

            long id = craftable.getId();

            // if queue contains this craftable's _id, check if quantity is different, if so, update
            // if queue does not contain and if quantity is above 0, reset quantity back to 0, update
            if ( CraftingQueue.getInstance().contains( id ) ) {
                int quantity = CraftingQueue.getInstance().getCraftable( id ).getQuantity();

                if ( craftable.getQuantity() != quantity ) {
                    craftable.setQuantity( quantity );

                    getCraftables().setValueAt( i, craftable );

                    notifyItemChanged( adjustPositionFromCraftable( i ) );
                }
            } else if ( craftable.getQuantity() > 0 ) {
                craftable.resetQuantity();

                notifyItemChanged( adjustPositionFromCraftable( i ) );
            }
        }
    }

    private void clearQuantities() {
        boolean didUpdate = false;

        for ( int i = 0; i < getCraftables().size(); i++ ) {
            getCraftable( i ).resetQuantity();

            didUpdate = true;
        }

        if ( didUpdate )
            notifyDataSetChanged();
    }

    private CraftableSparseArray queryForEngrams( Uri uri ) {
        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                return new CraftableSparseArray();

            CraftableSparseArray craftables = new CraftableSparseArray();
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
                String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
                String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );
                int yield = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
                long category_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) );

                int quantity = 0;

                if ( CraftingQueue.getInstance().contains( _id ) )
                    quantity = CraftingQueue.getInstance().getCraftable( _id ).getQuantity();

                craftables.put( _id, new DisplayEngram( _id, name, folder, file, yield, category_id, quantity ) );
            }

            return craftables;
        }
    }

    private CraftableSparseArray querySearchForEngrams( Uri uri ) {
        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                return new CraftableSparseArray();

            CraftableSparseArray craftables = new CraftableSparseArray();
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
                String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
                String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );
                int yield = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
                long category_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) );

                int quantity = 0;

                if ( CraftingQueue.getInstance().contains( _id ) )
                    quantity = CraftingQueue.getInstance().getCraftable( _id ).getQuantity();

                craftables.put( _id, new DisplayEngram( _id, name, folder, file, yield, category_id, quantity ) );
            }

            return craftables;
        }
    }

    private DisplayEngram queryForEngram( Uri uri, int quantity ) {
        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {

            if ( cursor == null )
                return null;

            if ( !cursor.moveToFirst() )
                return null;

            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
            String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );
            int yield = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
            long category_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) );

            return new DisplayEngram( _id, name, folder, file, yield, category_id, quantity );
        }
    }

    /**
     * CATEGORY METHODS
     */

    private int adjustPositionForCategory( int position ) {
        return position - getStations().size();
    }

    private boolean isCategory( int position ) {
        return Util.isValidPosition( adjustPositionForCategory( position ), getCategories().size() );
    }

    private Category getCategory( int position ) {
        return getCategories().valueAt( position );
    }

    private void changeCategory( int position )
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException, ExceptionUtil.PositionOutOfBoundsException {
        if ( isCategory( position ) ) {
            Category category = getCategory( position );

            Log.d( TAG, "Changing category to [" + position + "] " + category.toString() );

            long dlc_id = PrefsUtil.getInstance().getDLCPreference();
            if ( position == 0 ) {
                if ( category.getParent() == SEARCH_ROOT ) {
                    // backing out of search view
                    mSearchQuery = null;
                } else {
                    if ( isCurrentCategoryLevelRoot() ) {
                        if ( isFilteredByStation() ) {
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
                                    queryForCategory( DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, category.getParent() ) ).getParent() );
                        }
                    }
                }
            } else {
                // Normal Category object
                // Grabbing ID is the best way to track its location.
                setCurrentCategoryLevels( category.getId(), category.getParent() );
            }

            refreshData();
        } else {
            throw new ExceptionUtil.PositionOutOfBoundsException( position, getCategories().size(), getCategories().toString() );
        }
    }

    private Category queryForCategory( Uri uri )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {

        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return new Category(
                    DatabaseContract.CategoryEntry.getIdFromUri( uri ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) ),
                    cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) ) );
        }

    }

    private CategorySparseArray queryForCategories( Uri uri )
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {
        if ( uri == null )
            return new CategorySparseArray();

        long dlc_id = DatabaseContract.CategoryEntry.getDLCIdFromUri( uri );

        Category backCategory = null;
        if ( isFilteredByStation() ) {
            if ( isCurrentCategoryLevelRoot() ) {
                // If going back to station root (list of stations)
                backCategory = BuildBackCategoryToStationRoot();
            } else {
                if ( isRoot( getCurrentCategoryParent() ) ) {
                    // If going back to a station (list of categories)
                    Uri stationUri = DatabaseContract.StationEntry.buildUriWithId( dlc_id, getCurrentStationId() );
                    String stationName = queryForStation( stationUri ).getName();

                    backCategory = BuildBackCategoryToStation( stationName );
                } else {
                    // If just going back to previous category
                    Uri categoryUri = DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryParent() );
                    String categoryName = queryForCategory( categoryUri ).getName();

                    backCategory = BuildBackCategory( categoryName );
                }
            }
        } else {
            if ( !isCurrentCategoryLevelRoot() ) {
                if ( isRoot( getCurrentCategoryParent() ) ) {
                    // If going back to root (list of categories)
                    backCategory = BuildBackCategoryToRoot();
                } else {
                    // If just going back to previous category
                    Uri categoryUri = DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryParent() );
                    String categoryName = queryForCategory( categoryUri ).getName();
                    backCategory = BuildBackCategory( categoryName );
                }
            }
        }

        CategorySparseArray categories = new CategorySparseArray();

        if ( backCategory != null )
            categories.append( backCategory.getId(), backCategory );

        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                return new CategorySparseArray();

            List<Long> keys = new ArrayList<>();
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
                long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

                if ( !keys.contains( _id ) ) {
                    keys.add( _id );
                    categories.append( _id, new Category( _id, name, parent_id ) );
                }
            }

            return categories;
        }
    }

    private Category BuildBackCategory( String name ) {
        return new BackCategory(
                getCurrentCategoryLevel(),
                String.format( getContext().getString( R.string.category_go_back_format ), name ),
                getCurrentCategoryParent() );
    }

    private Category BuildBackCategoryToRoot() {
        return new BackCategory(
                getCurrentCategoryLevel(),
                getContext().getString( R.string.go_back ),
                getCurrentCategoryParent() );
    }

    private Category BuildBackCategoryToStation( String name ) {
        return new BackCategory(
                getCurrentCategoryLevel(),
                String.format( getContext().getString( R.string.station_go_back_format ), name ),
                getCurrentCategoryParent() );
    }

    private Category BuildBackCategoryToStationRoot() {
        return new BackCategory(
                getCurrentStationId(),
                getContext().getString( R.string.go_back_to_stations ),
                STATION_ROOT );
    }

    private Category BuildBackCategoryOutOfSearch() {
        return new BackCategory(
                SEARCH_ROOT,
                getContext().getString( R.string.category_go_back ),
                SEARCH_ROOT );
    }

    /**
     * STATION METHODS
     */

    private boolean isStation( int position ) {
        return Util.isValidPosition( position, getStations().size() );
    }

    private Station getStation( int position ) {
        return getStations().valueAt( position );
    }

    private void changeStation( int position ) throws ExceptionUtil.PositionOutOfBoundsException {
        if ( isStation( position ) ) {
            Station station = getStation( position );

            Log.d( TAG, "Changing station to [" + position + "] " + station.toString() );

            setCurrentStationId( station.getId() );
            setCurrentCategoryLevelsToRoot();

            refreshData();
        } else {
            throw new ExceptionUtil.PositionOutOfBoundsException( position, getStations().size(), getStations().toString() );
        }
    }

    private Station queryForStation( Uri uri )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {

        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return new Station(
                    DatabaseContract.StationEntry.getIdFromUri( uri ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER ) ),
                    cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE ) ) );
        }

    }

    private StationSparseArray queryForStations( Uri uri )
            throws ExceptionUtil.CursorNullException {

        try ( Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            StationSparseArray values = new StationSparseArray();
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.StationEntry._ID ) );
                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );
                String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER ) );
                String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE ) );

                values.put( _id, new Station( _id, name, folder, file ) );
            }

            return values;
        }

    }

    public void buildHierarchy() {
        Log.d( TAG, "buildHierarchy: " );

        try {
            long dlc_id = PrefsUtil.getInstance().getDLCPreference();

            StringBuilder builder = new StringBuilder();
            if ( mSearchQuery == null ) {
                if ( isFilteredByStation() ) {
                    if ( isCurrentCategoryLevelStationRoot() ) {
                        builder.append( getContext().getString( R.string.display_case_hierarchy_text_all_stations ) );
                    } else {
                        String name = queryForStation( DatabaseContract.StationEntry.buildUriWithId( dlc_id, getCurrentStationId() ) ).getName();
                        if ( isFilteredByCategory() ) {
                            if ( isCurrentCategoryLevelRoot() ) {
                                builder.append( String.format( getContext().getString( R.string.display_case_hierarchy_text_contents_of_station ), name ) );
                            } else {
                                builder.append(
                                        String.format( getContext().getString( R.string.display_case_hierarchy_text_contents_of_station_in_folder ),
                                                name, buildCategoryHierarchy( DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryLevel() ) ) ) );
                            }
                        }
                    }
                } else if ( isFilteredByCategory() ) {
                    if ( isCurrentCategoryLevelRoot() ) {
                        builder.append( getContext().getString( R.string.display_case_hierarchy_text_all_categories ) );
                    } else {
                        builder.append( "/" ).append( buildCategoryHierarchy( DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, getCurrentCategoryLevel() ) ) );
                    }
                } else {
                    builder.append( getContext().getString( R.string.display_case_hierarchy_text_all_engrams ) );
                }
            } else {
                builder.append( String.format( getContext().getString( R.string.display_case_hierarchy_text_search_results_format ), mSearchQuery ) );
            }

//            NavigationObserver.getInstance().update( String.format( getContext().getString( R.string.display_case_hierarchy_text_showing_format ), builder.toString() ) );
            NavigationObserver.getInstance().update( builder.toString() );
        } catch ( ExceptionUtil.CursorNullException | ExceptionUtil.CursorEmptyException e ) {
            mExceptionObserver.notifyFatalExceptionCaught( TAG, e );
        }

    }

    private String buildCategoryHierarchy( Uri uri )
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {

        Category category = queryForCategory( uri );

        StringBuilder builder = new StringBuilder( category.getName() );

        while ( !isRoot( category.getParent() ) ) {
            category = queryForCategory( DatabaseContract.CategoryEntry.buildUriWithId(
                    DatabaseContract.CategoryEntry.getDLCIdFromUri( uri ), category.getParent() ) );

            builder.insert( 0, category.getName() + "/" );
        }

        return builder.toString();
    }

    private void refreshData() {
        new QueryForDataTask().execute();
    }

    public void searchData( String searchQuery ) {
        mSearchQuery = searchQuery;
        new SearchDataTask().execute();
    }

    private class QueryForDataTask extends AsyncTask<Void, Void, Boolean> {
        private final String TAG = QueryForDataTask.class.getSimpleName();

        private StationSparseArray mStationSparseArray;
        private CategorySparseArray mCategorySparseArray;
        private CraftableSparseArray mCraftableSparseArray;

        QueryForDataTask() {
        }

        @Override
        protected void onPostExecute( Boolean querySuccessful ) {
            if ( querySuccessful ) {
                setStations( mStationSparseArray );
                setCategories( mCategorySparseArray );
                setCraftables( mCraftableSparseArray );

                notifyDataSetChanged();

                buildHierarchy();
            }
        }

        @Override
        protected Boolean doInBackground( Void... params ) {

            try {
                long dlc_id = PrefsUtil.getInstance().getDLCPreference();

                mStationSparseArray = new StationSparseArray();
                mCategorySparseArray = new CategorySparseArray();
                mCraftableSparseArray = new CraftableSparseArray();

                Uri craftableUri = null;
                Uri categoryUri = null;
                Uri stationUri = null;
                if ( isFilteredByCategory() ) {
                    if ( isFilteredByStation() ) {
                        if ( isCurrentCategoryLevelStationRoot() ) {
                            stationUri = DatabaseContract.StationEntry.buildUriWithDLCId( dlc_id );
                        } else {
                            if ( isFilteredByLevel() ) {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdStationIdAndLevel(
                                        dlc_id,
                                        getCurrentCategoryLevel(),
                                        getCurrentStationId(),
                                        getRequiredLevelPref() );
                            } else {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdAndStationId(
                                        dlc_id,
                                        getCurrentCategoryLevel(),
                                        getCurrentStationId() );
                            }

                            categoryUri = DatabaseContract.CategoryEntry.buildUriWithStationId( dlc_id, getCurrentCategoryLevel(), getCurrentStationId() );
                        }
                    } else {
                        if ( isFilteredByLevel() ) {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdAndLevel(
                                    dlc_id,
                                    getCurrentCategoryLevel(),
                                    getRequiredLevelPref() );
                        } else {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryId(
                                    dlc_id,
                                    getCurrentCategoryLevel() );
                        }

                        categoryUri = DatabaseContract.CategoryEntry.buildUriWithParentId( dlc_id, getCurrentCategoryLevel() );
                    }
                } else {
                    if ( isFilteredByStation() ) {
                        if ( isCurrentCategoryLevelStationRoot() ) {
                            stationUri = DatabaseContract.StationEntry.buildUriWithDLCId( dlc_id );
                        } else {
                            if ( isFilteredByLevel() ) {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithStationIdAndLevel(
                                        dlc_id,
                                        getCurrentStationId(),
                                        getRequiredLevelPref() );
                            } else {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithStationId(
                                        dlc_id,
                                        getCurrentStationId() );
                            }

                            // TODO: 4/13/2017 There might be an issue here, used to just add station root as a back button, should react the same way
                            categoryUri = DatabaseContract.CategoryEntry.buildUriWithParentId( dlc_id, getCurrentCategoryLevel() );
                        }
                    } else {
                        if ( isFilteredByLevel() ) {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithLevel( dlc_id, getRequiredLevelPref() );
                        } else {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithDLCId( dlc_id );
                        }
                    }
                }

                if ( stationUri != null )
                    mStationSparseArray = queryForStations( stationUri );

                if ( categoryUri != null )
                    mCategorySparseArray = queryForCategories( categoryUri );

                if ( craftableUri != null )
                    mCraftableSparseArray = queryForEngrams( craftableUri );

                return true;
            } catch ( ExceptionUtil.CursorNullException | ExceptionUtil.CursorEmptyException e ) {
                mExceptionObserver.notifyFatalExceptionCaught( TAG, e );

                return false;
            }
        }
    }

    private class SearchDataTask extends AsyncTask<Void, Void, Boolean> {
        private final String TAG = SearchDataTask.class.getSimpleName();

        private CategorySparseArray mCategorySparseArray;
        private CraftableSparseArray mCraftableSparseArray;

        SearchDataTask() {
        }

        @Override
        protected void onPostExecute( Boolean querySuccessful ) {
            if ( querySuccessful ) {
                setCategories( mCategorySparseArray );
                setCraftables( mCraftableSparseArray );

                notifyDataSetChanged();

                buildHierarchy();
            }
        }

        @Override
        protected Boolean doInBackground( Void... params ) {
            if ( mSearchQuery == null )
                return false;

            long dlc_id = PrefsUtil.getInstance().getDLCPreference();
            Uri uri = DatabaseContract.EngramEntry.buildUriWithSearchQuery( dlc_id, mSearchQuery );

            Category backCategory = BuildBackCategoryOutOfSearch();
            mCategorySparseArray = new CategorySparseArray( 1 );
            mCategorySparseArray.append( backCategory.getId(), backCategory );

            mCraftableSparseArray = querySearchForEngrams( uri );

            return true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_craftable, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position ) {
        View view = holder.itemView;

        final String imagePath = "file:///android_asset/" + getImagePathByPosition( position );
        Picasso.with( getContext() )
                .load( imagePath )
                .error( R.drawable.placeholder_empty )
                .placeholder( R.drawable.placeholder_empty )
                .into( holder.getImageView() );

        holder.getNameView().setText( getNameByPosition( position ) );

        if ( isCraftable( position ) ) {
            int quantity = getQuantityWithYieldByPosition( position );

            if ( quantity > 0 ) {
                view.setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_queue_item ) );
                holder.getQuantityView().setText( String.format( Locale.getDefault(), "x%d", quantity ) );
//                holder.getNameView().setMaxLines( 1 );
            } else {
                view.setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_craftable_item ) );
                holder.getQuantityView().setText( null );
//                holder.getNameView().setMaxLines( 3 );
            }
        } else {
            view.setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_craftable_folder ) );
            holder.getQuantityView().setText( null );
//            holder.getNameView().setMaxLines( 3 );
        }
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private final String TAG = ViewHolder.class.getSimpleName();

        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        ViewHolder( View view ) {
            super( view );

            mImageView = ( ImageView ) view.findViewById( R.id.image_view );
            mNameView = ( TextView ) view.findViewById( R.id.name_view );
            mQuantityView = ( TextView ) view.findViewById( R.id.quantity_view );

            view.setOnClickListener( this );
            view.setOnLongClickListener( this );
        }

        ImageView getImageView() {
            return mImageView;
        }

        TextView getNameView() {
            return mNameView;
        }

        TextView getQuantityView() {
            return mQuantityView;
        }

        @Override
        public void onClick( View view ) {
            int position = getAdapterPosition();

            try {
                if ( isCraftable( position ) ) {
                    increaseQuantity( position );
                } else if ( isCategory( position ) ) {
                    changeCategory( position );
                } else if ( isStation( position ) ) {
                    changeStation( position );
                }
            } catch ( ExceptionUtil.CursorEmptyException | ExceptionUtil.CursorNullException e ) {
                mExceptionObserver.notifyFatalExceptionCaught( TAG, e );
            } catch ( ExceptionUtil.PositionOutOfBoundsException e ) {
                mExceptionObserver.notifyExceptionCaught( TAG, e );
            }
        }

        @Override
        public boolean onLongClick( View view ) {
            return !isCraftable( getAdapterPosition() );
        }
    }

    private class CraftableSparseArray extends LongSparseArray<DisplayEngram> {

        /**
         * Constructor that, by default, will set its element size to 0.
         */
        CraftableSparseArray() {
            super( 0 );
        }

        /**
         * Constructor that sets its element size per the supplied amount.
         *
         * @param size Amount of elements the array requires to allocate.
         */
        CraftableSparseArray( int size ) {
            super( size );
        }

        boolean contains( long key ) {
            return !( indexOfKey( key ) < 0 );
        }
    }

    private class StationSparseArray extends LongSparseArray<Station> {

        /**
         * Constructor that, by default, will set its element size to 0.
         */
        StationSparseArray() {
            super( 0 );
        }

        /**
         * Constructor that sets its element size per the supplied amount.
         *
         * @param size Amount of elements the array requires to allocate.
         */
        StationSparseArray( int size ) {
            super( size );
        }

        boolean contains( long key ) {
            return !( indexOfKey( key ) < 0 );
        }
    }

    private class CategorySparseArray extends LongSparseArray<Category> {

        /**
         * Constructor that, by default, will set its element size to 0.
         */
        CategorySparseArray() {
            super( 0 );
        }

        /**
         * Constructor that sets its element size per the supplied amount.
         *
         * @param size Amount of elements the array requires to allocate.
         */
        CategorySparseArray( int size ) {
            super( size );
        }

        boolean contains( long key ) {
            return !( indexOfKey( key ) < 0 );
        }
    }
}