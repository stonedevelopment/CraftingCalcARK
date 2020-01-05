/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.listeners.NavigationObserver;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.category.BackCategory;
import arc.resource.calculator.model.category.Category;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.SortableMap;
import arc.resource.calculator.model.Station;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;
import arc.resource.calculator.views.ExplorerRecyclerView;

import static arc.resource.calculator.adapters.CraftableAdapter.Status.HIDDEN;
import static arc.resource.calculator.adapters.CraftableAdapter.Status.INIT;
import static arc.resource.calculator.adapters.CraftableAdapter.Status.VISIBLE;
import static arc.resource.calculator.util.Util.NO_ID;
import static arc.resource.calculator.util.Util.NO_NAME;
import static arc.resource.calculator.util.Util.NO_PATH;
import static arc.resource.calculator.util.Util.NO_QUANTITY;

public class CraftableAdapter extends RecyclerView.Adapter<CraftableAdapter.ViewHolder> {

    private static final String TAG = CraftableAdapter.class.getSimpleName();

    public static final long ROOT = 0;
    private static final long NO_STATION = -1;
    private static final long SEARCH_ROOT = -2;

    private long mLastCategoryLevel;
    private long mLastCategoryParent;
    private long mStationId;

    private String mSearchQuery;

    private StationMap mStationMap;
    private CategoryMap mCategoryMap;
    private CraftableMap mCraftableMap;

    private Context mContext;

    private ExplorerRecyclerView.Observer mViewObserver;

    private Status mViewStatus;

    private FetchDataTask mFetchDataTask;
    private SearchDataTask mSearchDataTask;

    private boolean mNeedsUpdate;

    private boolean mNeedsFullUpdate;

    enum Status {INIT, VISIBLE, HIDDEN}

    public CraftableAdapter(Context context, ExplorerRecyclerView.Observer observer) {
        setContext(context);
        setObserver(observer);

        PrefsUtil prefs = PrefsUtil.getInstance(context);

        // Retrieve last viewed category level and parent from previous use
        setCurrentCategoryLevels(prefs.getLastCategoryLevel(), prefs.getLastCategoryParent());

        // Retrieve last viewed Station id
        setCurrentStationId(prefs.getLastStationId());

        // Setup custom SparseArrays
        setStations(new StationMap());
        setCategories(new CategoryMap());
        setCraftables(new CraftableMap());

        String searchQuery = PrefsUtil.getInstance(context).getSearchQuery();
        setSearchQuery(searchQuery);

        mNeedsUpdate = false;

        QueueObserver.getInstance().registerListener(TAG, new QueueObserver.Listener() {
            public void onDataSetPopulated() {
                if (mViewStatus == VISIBLE) {
                    updateQuantities();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onItemChanged(long craftableId, int quantity) {
                Log.d(TAG, "onItemChanged: ");
                if (mViewStatus == VISIBLE) {
                    Log.d(TAG, "onItemChanged: visible");
                    if (getCraftableMap().contains(craftableId)) {
                        int position = getCraftableMap().indexOfKey(craftableId);
                        getCraftable(position).setQuantity(quantity);
                        notifyItemChanged(adjustPositionFromCraftable(position));
                    }
                } else {
                    Log.d(TAG, "onItemChanged: not visible: needs  updating");
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onItemRemoved(long craftableId) {
                Log.d(TAG, "onItemRemoved: ");
                if (mViewStatus == VISIBLE) {
                    if (getCraftableMap().contains(craftableId)) {
                        int position = getCraftableMap().indexOfKey(craftableId);
                        getCraftable(position).resetQuantity();
                        notifyItemChanged(adjustPositionFromCraftable(position));
                    }
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onDataSetEmpty() {
                Log.d(TAG, "onDataSetEmpty: ");
                if (mViewStatus == VISIBLE) {
                    clearQuantities();
                } else {
                    mNeedsUpdate = true;
                }
            }
        });

        PrefsObserver.getInstance().registerListener(TAG, new PrefsObserver.Listener() {
            @Override
            public void onPreferencesChanged(boolean dlcValueChange, boolean categoryPrefChange,
                                             boolean stationPrefChange, boolean levelPrefChange, boolean levelValueChange,
                                             boolean refinedPrefChange) {
                if (dlcValueChange || categoryPrefChange || stationPrefChange || levelPrefChange
                        || levelValueChange) {
                    setCategoryLevelsToRoot();

                    unsetSearchQuery();

                    if (mViewStatus == VISIBLE) {
                        fetchData();
                    } else {
                        mNeedsFullUpdate = true;
                    }
                }
            }
        });

        mViewStatus = INIT;

        mFetchDataTask = new FetchDataTask();
        mSearchDataTask = new SearchDataTask();

        if (getSearchQuery() == null) {
            fetchData();
        } else {
            searchData();
        }
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        try {
            super.registerAdapterDataObserver(observer);
        } catch (IllegalStateException e) {
            // do nothing
        }
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        try {
            super.unregisterAdapterDataObserver(observer);
        } catch (IllegalStateException e) {
            // do nothing
        }
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private ExplorerRecyclerView.Observer getObserver() {
        return mViewObserver;
    }

    private void setObserver(ExplorerRecyclerView.Observer observer) {
        mViewObserver = observer;
    }

    private String getSearchQuery() {
        return mSearchQuery;
    }

    private void setSearchQuery(String query) {
        mSearchQuery = query;
    }

    private void unsetSearchQuery() {
        setSearchQuery(null);

        PrefsUtil.getInstance(getContext()).saveSearchQuery(null);
    }

    private StationMap getStationMap() {
        return mStationMap;
    }

    private void setStations(StationMap map) {
        Log.d(TAG, "setStations: " + map.size());
        this.mStationMap = map;
    }

    private CategoryMap getCategoryMap() {
        return mCategoryMap;
    }

    private void setCategories(CategoryMap map) {
        Log.d(TAG, "setCategories: " + map.size());
        this.mCategoryMap = map;
    }

    private CraftableMap getCraftableMap() {
        return mCraftableMap;
    }

    private void setCraftables(CraftableMap map) {
        Log.d(TAG, "setCraftables: " + map.size());
        this.mCraftableMap = map;
    }

    public void resume() {
        if (mViewStatus != INIT) {
            mViewStatus = VISIBLE;

            if (mNeedsFullUpdate) {
                fetchData();
            } else if (mNeedsUpdate) {
                updateQuantities();
            }
        }
    }

    public void pause() {
        mViewStatus = HIDDEN;
        savePrefs();
    }

    public void destroy() {
        QueueObserver.getInstance().unregisterListener(TAG);
        PrefsObserver.getInstance().unregisterListener(TAG);
    }

    private boolean isFilteredByCategory() {
        return PrefsUtil.getInstance(getContext()).getCategoryFilterPreference();
    }

    private boolean isFilteredByStation() {
        return PrefsUtil.getInstance(getContext()).getStationFilterPreference();
    }

    private boolean isFilteredByLevel() {
        return PrefsUtil.getInstance(getContext()).getLevelFilterPreference();
    }

    private boolean isCurrentCategoryLevelRoot() {
        return getCurrentCategoryLevel() == ROOT;
    }

    private boolean isCurrentCategoryLevelStationRoot() {
        return getCurrentCategoryLevel() == NO_STATION;
    }

    private boolean isCurrentCategoryParentLevelRoot() {
        return getCurrentCategoryParent() == ROOT;
    }

    private boolean isCurrentCategoryParentLevelStationRoot() {
        return getCurrentCategoryParent() == NO_STATION;
    }

    private boolean isCurrentCategoryParentLevelSearchRoot() {
        return getCurrentCategoryParent() == SEARCH_ROOT;
    }

    private boolean isCategoryParentLevelRoot(long parent) {
        return parent == ROOT;
    }

    private boolean isCategoryParentLevelStationRoot(long parent) {
        return parent == NO_STATION;
    }

    private boolean isCategoryParentLevelSearchRoot(long parent) {
        return parent == SEARCH_ROOT;
    }

    private boolean isRoot(long level) {
        return level == ROOT;
    }

    private boolean isStationRoot(long level) {
        return level == NO_STATION;
    }

    private long getCurrentCategoryLevel() {
        return mLastCategoryLevel;
    }

    private long getCurrentCategoryParent() {
        return mLastCategoryParent;
    }

    private void setCurrentCategoryLevels(long level, long parent) {
        mLastCategoryLevel = level;
        mLastCategoryParent = parent;
    }

    private void setCategoryLevelsToRoot() {
        if (isFilteredByStation()) {
            setCurrentStationId(NO_STATION);
            setCurrentCategoryLevelsToStationRoot();
        } else {
            setCurrentCategoryLevelsToRoot();
        }
    }

    private void setCurrentCategoryLevelsToRoot() {
        setCurrentCategoryLevels(ROOT, ROOT);
    }

    private void setCurrentCategoryLevelsToStationRoot() {
        setCurrentCategoryLevels(NO_STATION, NO_STATION);
    }

    private void savePrefs() {
        PrefsUtil prefs = PrefsUtil.getInstance(getContext());

        if (prefs.getCategoryFilterPreference()) {
            prefs.saveCategoryLevels(getCurrentCategoryLevel(), getCurrentCategoryParent());
        }

        if (prefs.getStationFilterPreference()) {
            prefs.saveStationId(getCurrentStationId());
        }

        prefs.saveSearchQuery(getSearchQuery());
    }

    private long getCurrentStationId() {
        return mStationId;
    }

    private void setCurrentStationId(long stationId) {
        mStationId = stationId;
    }

    private int getRequiredLevelPref() {
        return PrefsUtil.getInstance(getContext()).getRequiredLevel();
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    private String getItemContents() {
        return getStationMap().toString() + ", " + getCategoryMap().toString() + ", "
                + getCraftableMap().toString();
    }

    private int getSize() {
        return (getStationMap().size() + getCategoryMap().size() + getCraftableMap().size());
    }

    private long getIdByPosition(int position) {
        try {
            if (isStation(position)) {
                return getStation(position).getId();
            }

            if (isCategory(position)) {
                return getCategory(adjustPositionForCategory(position)).getId();
            }

            if (isCraftable(position)) {
                return getCraftable(adjustPositionForCraftable(position)).getId();
            }

            throw new ExceptionUtil.PositionOutOfBoundsException(position, getItemCount(),
                    getItemContents());
        } catch (ExceptionUtil.PositionOutOfBoundsException e) {
            ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);

            return NO_ID;
        }
    }

    private String getImagePathByPosition(int position) {
        try {
            if (isStation(position)) {
                return getStation(position).getImagePath();
            }

            if (isCategory(position)) {
                return getCategory(adjustPositionForCategory(position)).getImagePath();
            }

            if (isCraftable(position)) {
                return getCraftable(adjustPositionForCraftable(position)).getImagePath();
            }

            throw new ExceptionUtil.PositionOutOfBoundsException(position, getItemCount(),
                    getItemContents());
        } catch (ExceptionUtil.PositionOutOfBoundsException e) {
            ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);

            return NO_PATH;
        }
    }

    private String getNameByPosition(int position) {
        try {
            if (isStation(position)) {
                return getStation(position).getName();
            }

            if (isCategory(position)) {
                return getCategory(adjustPositionForCategory(position)).getName();
            }

            if (isCraftable(position)) {
                return getCraftable(adjustPositionForCraftable(position)).getName();
            }

            throw new ExceptionUtil.PositionOutOfBoundsException(position, getItemCount(),
                    getItemContents());
        } catch (ExceptionUtil.PositionOutOfBoundsException e) {
            ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);

            return NO_NAME;
        }
    }

    private int getQuantityWithYieldByPosition(int position) {
        try {
            return getCraftable(adjustPositionForCraftable(position)).getQuantityWithYield();
        } catch (Exception e) {
            return NO_QUANTITY;
        }
    }

    /**
     * CRAFTABLE METHODS
     */

    private int adjustPositionForCraftable(int position) {
        return position - (getStationMap().size() + getCategoryMap().size());
    }

    private int adjustPositionFromCraftable(int position) {
        return position + (getStationMap().size() + getCategoryMap().size());
    }

    private boolean isCraftable(int position) {
        return Util.isValidPosition(adjustPositionForCraftable(position), getCraftableMap().size());
    }

    private DisplayEngram getCraftable(int position) {
        return getCraftableMap().valueAt(position);
    }

    private void increaseQuantity(int position) {
        CraftingQueue.getInstance()
                .increaseQuantity(getCraftable(adjustPositionForCraftable(position)));
    }

    private void updateQuantities() {
        CraftingQueue craftingQueue = CraftingQueue.getInstance();

        for (int i = 0; i < getCraftableMap().size(); i++) {
            DisplayEngram craftable = getCraftable(i);

            long id = craftable.getId();

            // if queue contains this craftable's _id, check if quantity is different, if so, update
            // if queue does not contain and if quantity is above 0, reset quantity back to 0, update
            if (craftingQueue.contains(id)) {
                int quantity = craftingQueue.getCraftable(id).getQuantity();

                if (craftable.getQuantity() != quantity) {
                    craftable.setQuantity(quantity);

                    getCraftableMap().setValueAt(i, craftable);
                }
            } else if (craftable.getQuantity() > 0) {
                craftable.resetQuantity();

                getCraftableMap().setValueAt(i, craftable);
            }
        }

        notifyDataSetChanged();

        mNeedsUpdate = false;
    }

    private void clearQuantities() {
        boolean didUpdate = false;

        for (int i = 0; i < getCraftableMap().size(); i++) {
            getCraftable(i).resetQuantity();

            didUpdate = true;
        }

        if (didUpdate) {
            notifyDataSetChanged();
        }
    }

    private CraftableMap queryForEngrams(Uri uri) {
        if (uri == null) {
            return new CraftableMap();
        }

        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) {
                return new CraftableMap();
            }

            CraftingQueue craftingQueue = CraftingQueue.getInstance();

            CraftableMap craftables = new CraftableMap();
            while (cursor.moveToNext()) {
                long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry._ID));
                String name = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME));
                String folder = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER));
                String file = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE));
                int yield = cursor.getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD));
                long category_id = cursor
                        .getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY));

                int quantity = 0;

                if (craftingQueue.contains(_id)) {
                    quantity = craftingQueue.getCraftable(_id).getQuantity();
                }

                craftables
                        .add(_id, new DisplayEngram(_id, name, folder, file, yield, category_id, quantity));
            }

            // TODO: 4/30/2017 What if we sorted as we inserted?
            craftables.sort();

            return craftables;
        }
    }

    private CraftableMap querySearchForEngrams(Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) {
                return new CraftableMap();
            }

            CraftingQueue craftingQueue = CraftingQueue.getInstance();

            CraftableMap craftables = new CraftableMap();
            while (cursor.moveToNext()) {
                long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry._ID));
                String name = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME));
                String folder = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER));
                String file = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE));
                int yield = cursor.getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD));
                long category_id = cursor
                        .getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY));

                int quantity = 0;

                if (craftingQueue.contains(_id)) {
                    quantity = craftingQueue.getCraftable(_id).getQuantity();
                }

                craftables
                        .add(_id, new DisplayEngram(_id, name, folder, file, yield, category_id, quantity));
            }

            craftables.sort();

            return craftables;
        }
    }

    private DisplayEngram queryForEngram(Uri uri, int quantity) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {

            if (cursor == null) {
                return null;
            }

            if (!cursor.moveToFirst()) {
                return null;
            }

            long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry._ID));
            String name = cursor
                    .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME));
            String folder = cursor
                    .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER));
            String file = cursor
                    .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE));
            int yield = cursor.getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD));
            long category_id = cursor
                    .getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY));

            return new DisplayEngram(_id, name, folder, file, yield, category_id, quantity);
        }
    }

    /**
     * CATEGORY METHODS
     */

    private int adjustPositionForCategory(int position) {
        return position - getStationMap().size();
    }

    private boolean isCategory(int position) {
        return Util.isValidPosition(adjustPositionForCategory(position), getCategoryMap().size());
    }

    private Category getCategory(int position) {
        return getCategoryMap().valueAt(position);
    }

    private void changeCategory(int position)
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {
        Category category = getCategory(position);

        long dlc_id = PrefsUtil.getInstance(getContext()).getDLCPreference();
        if (position == 0) {
            // position 0 will always be a back category to the previous level
            long parent = category.getParent();
            if (isCategoryParentLevelSearchRoot(parent)) {
                // backing out of search view
                unsetSearchQuery();
            } else {
                if (isCategoryParentLevelStationRoot(parent)) {
                    // Back button to station list
                    setCurrentStationId(NO_STATION);
                    setCurrentCategoryLevelsToStationRoot();
                } else {
                    if (isCurrentCategoryLevelRoot()) {
                        // Normal Category object
                        // Grabbing ID is the best way to track its location.
                        setCurrentCategoryLevels(category.getId(), category.getParent());
                    } else if (isCategoryParentLevelRoot(parent)) {
                        // Back button to category list
                        setCurrentCategoryLevelsToRoot();
                    } else {
                        // Normal Back Category object
                        // Query for details via its Parent Level
                        setCurrentCategoryLevels(category.getParent(),
                                queryForCategory(
                                        DatabaseContract.CategoryEntry.buildUriWithId(dlc_id, category.getParent()))
                                        .getParent());
                    }
                }
            }
        } else {
            // Normal Category object
            // Grabbing ID is the best way to track its location.
            setCurrentCategoryLevels(category.getId(), category.getParent());
        }

        savePrefs();

        fetchData();
    }

    private Category queryForCategory(Uri uri)
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {

        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) {
                throw new ExceptionUtil.CursorNullException(uri);
            }

            if (!cursor.moveToFirst()) {
                throw new ExceptionUtil.CursorEmptyException(uri);
            }

            return new Category(
                    DatabaseContract.CategoryEntry.getIdFromUri(uri),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CategoryEntry.COLUMN_NAME)),
                    cursor.getLong(cursor.getColumnIndex(DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY)));
        }

    }

    private CategoryMap queryForCategories(Uri uri) {
        if (uri == null) {
            return new CategoryMap();
        }

        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) {
                return new CategoryMap();
            }

            CategoryMap categoryMap = new CategoryMap();
            while (cursor.moveToNext()) {
                long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.CategoryEntry._ID));
                String name = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.CategoryEntry.COLUMN_NAME));
                long parent_id = cursor
                        .getLong(cursor.getColumnIndex(DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY));

                categoryMap.add(_id, new Category(_id, name, parent_id));
            }

            categoryMap.sort();

            return categoryMap;
        }
    }

    private Category BuildBackCategory() {
        return new BackCategory(getCurrentCategoryParent(), getCurrentCategoryParent());
    }

    private Category BuildBackCategoryToStationRoot() {
        return new BackCategory(NO_STATION, NO_STATION);
    }

    private Category BuildBackCategoryOutOfSearch() {
        return new BackCategory(SEARCH_ROOT, SEARCH_ROOT);
    }

    /**
     * STATION METHODS
     */

    private boolean isStation(int position) {
        return Util.isValidPosition(position, getStationMap().size());
    }

    private Station getStation(int position) {
        return getStationMap().valueAt(position);
    }

    private void changeStation(int position) {
        Station station = getStation(position);

        setCurrentStationId(station.getId());
        setCurrentCategoryLevelsToRoot();

        savePrefs();

        fetchData();
    }

    private Station queryForStation(Uri uri)
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {

        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) {
                throw new ExceptionUtil.CursorNullException(uri);
            }

            if (!cursor.moveToFirst()) {
                throw new ExceptionUtil.CursorEmptyException(uri);
            }

            return new Station(
                    DatabaseContract.StationEntry.getIdFromUri(uri),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.StationEntry.COLUMN_NAME)),
                    cursor
                            .getString(cursor.getColumnIndex(DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.StationEntry.COLUMN_IMAGE_FILE)));
        }

    }

    private StationMap queryForStations(Uri uri)
            throws ExceptionUtil.CursorNullException {

        if (uri == null) {
            return new StationMap();
        }

        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) {
                throw new ExceptionUtil.CursorNullException(uri);
            }

            StationMap stationMap = new StationMap();
            while (cursor.moveToNext()) {
                long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.StationEntry._ID));
                String name = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.StationEntry.COLUMN_NAME));
                String folder = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER));
                String file = cursor
                        .getString(cursor.getColumnIndex(DatabaseContract.StationEntry.COLUMN_IMAGE_FILE));

                stationMap.put(_id, new Station(_id, name, folder, file));
            }

            stationMap.sort();

            return stationMap;
        }

    }

    private void buildHierarchy() {

        try {
            long dlc_id = PrefsUtil.getInstance(getContext()).getDLCPreference();

            StringBuilder builder = new StringBuilder();
            if (getSearchQuery() == null) {
                if (isFilteredByStation()) {
                    if (isCurrentCategoryLevelStationRoot() || getCurrentStationId() == NO_STATION) {
                        builder
                                .append(getContext().getString(R.string.display_case_hierarchy_text_all_stations));
                    } else {
                        String name = queryForStation(
                                DatabaseContract.StationEntry.buildUriWithId(dlc_id, getCurrentStationId()))
                                .getName();
                        if (isFilteredByCategory()) {
                            if (isCurrentCategoryLevelRoot()) {
                                builder.append(String.format(getContext()
                                        .getString(R.string.display_case_hierarchy_text_contents_of_station), name));
                            } else {
                                builder.append(
                                        String.format(getContext().getString(
                                                R.string.display_case_hierarchy_text_contents_of_station_in_folder),
                                                name, buildCategoryHierarchy(DatabaseContract.CategoryEntry
                                                        .buildUriWithId(dlc_id, getCurrentCategoryLevel()))));
                            }
                        } else {
                            builder.append(String.format(
                                    getContext().getString(R.string.display_case_hierarchy_text_contents_of_station),
                                    name));
                        }
                    }
                } else if (isFilteredByCategory()) {
                    if (isCurrentCategoryLevelRoot()) {
                        builder.append(
                                getContext().getString(R.string.display_case_hierarchy_text_all_categories));
                    } else {
                        builder.append("/").append(buildCategoryHierarchy(
                                DatabaseContract.CategoryEntry.buildUriWithId(dlc_id, getCurrentCategoryLevel())));
                    }
                } else {
                    builder.append(getContext().getString(R.string.display_case_hierarchy_text_all_engrams));
                }
            } else {
                builder.append(String.format(
                        getContext().getString(R.string.display_case_hierarchy_text_search_results_format),
                        (getItemCount() - 1), getSearchQuery()));
            }

            NavigationObserver.getInstance().update(builder.toString());
        } catch (ExceptionUtil.CursorNullException | ExceptionUtil.CursorEmptyException e) {
            ExceptionObserver.getInstance().notifyFatalExceptionCaught(TAG, e);
        }

    }

    private String buildCategoryHierarchy(Uri uri)
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {

        Category category = queryForCategory(uri);

        StringBuilder builder = new StringBuilder(category.getName());

        while (!isRoot(category.getParent())) {
            category = queryForCategory(DatabaseContract.CategoryEntry.buildUriWithId(
                    DatabaseContract.CategoryEntry.getDLCIdFromUri(uri), category.getParent()));

            builder.insert(0, category.getName() + "/");
        }

        return builder.toString();
    }

    private void fetchData() {
        mFetchDataTask.cancel(true);
        mSearchDataTask.cancel(true);

        mFetchDataTask = new FetchDataTask();
        mFetchDataTask.execute();
    }

    private void searchData() {
        mFetchDataTask.cancel(true);
        mSearchDataTask.cancel(true);

        mSearchDataTask = new SearchDataTask();
        mSearchDataTask.execute();
    }

    public void searchData(String searchQuery) {
        setSearchQuery(searchQuery);
        new SearchDataTask().execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Boolean> {

        private final String TAG = FetchDataTask.class.getSimpleName();

        private StationMap mTempStationMap;
        private CategoryMap mTempCategoryMap;
        private CraftableMap mTempCraftableMap;

        private Exception mException;

        FetchDataTask() {
        }

        @Override
        protected void onPreExecute() {
            if (getObserver() != null) {
                getObserver().notifyInitializing();
            }
        }

        @Override
        protected void onPostExecute(Boolean querySuccessful) {
            if (querySuccessful) {
                setStations(mTempStationMap);
                setCategories(mTempCategoryMap);
                setCraftables(mTempCraftableMap);

                notifyDataSetChanged();

                if (getObserver() != null) {
                    getObserver().notifyDataSetPopulated();
                }

                mNeedsFullUpdate = false;

                if (mViewStatus == INIT) {
                    mViewStatus = VISIBLE;

                    updateQuantities();
                }

                buildHierarchy();
            } else {
                if (mException == null) {
                    mException = new Exception("Nullified Exception caught.");
                }

                if (getObserver() != null) {
                    getObserver().notifyExceptionCaught(mException);
                }
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                long dlc_id = PrefsUtil.getInstance(getContext()).getDLCPreference();

                mTempStationMap = new StationMap();
                mTempCategoryMap = new CategoryMap();
                mTempCraftableMap = new CraftableMap();

                Uri craftableUri = null;
                Uri categoryUri = null;
                Uri stationUri = null;
                Category backCategory = null;
                if (isFilteredByCategory()) {
                    if (isFilteredByStation()) {
                        if (isCurrentCategoryLevelStationRoot() || getCurrentStationId() == NO_STATION) {
                            stationUri = DatabaseContract.StationEntry.buildUriWithDLCId(dlc_id);
                        } else {
                            if (isFilteredByLevel()) {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdStationIdAndLevel(
                                        dlc_id,
                                        getCurrentCategoryLevel(),
                                        getCurrentStationId(),
                                        getRequiredLevelPref());
                            } else {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdAndStationId(
                                        dlc_id,
                                        getCurrentCategoryLevel(),
                                        getCurrentStationId());
                            }

                            if (isCurrentCategoryLevelRoot()) {
                                backCategory = BuildBackCategoryToStationRoot();
                            } else {
                                backCategory = BuildBackCategory();
                            }

                            categoryUri = DatabaseContract.CategoryEntry
                                    .buildUriWithStationId(dlc_id, getCurrentCategoryLevel(), getCurrentStationId());
                        }
                    } else {
                        if (isFilteredByLevel()) {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryIdAndLevel(
                                    dlc_id,
                                    getCurrentCategoryLevel(),
                                    getRequiredLevelPref());
                        } else {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithCategoryId(
                                    dlc_id,
                                    getCurrentCategoryLevel());
                        }

                        if (!isCurrentCategoryLevelRoot()) {
                            backCategory = BuildBackCategory();
                        }

                        categoryUri = DatabaseContract.CategoryEntry
                                .buildUriWithParentId(dlc_id, getCurrentCategoryLevel());
                    }
                } else {
                    if (isFilteredByStation()) {
                        if (isCurrentCategoryLevelStationRoot() || getCurrentStationId() == NO_STATION) {
                            stationUri = DatabaseContract.StationEntry.buildUriWithDLCId(dlc_id);
                        } else {
                            if (isFilteredByLevel()) {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithStationIdAndLevel(
                                        dlc_id,
                                        getCurrentStationId(),
                                        getRequiredLevelPref());
                            } else {
                                craftableUri = DatabaseContract.EngramEntry.buildUriWithStationId(
                                        dlc_id,
                                        getCurrentStationId());
                            }

                            backCategory = BuildBackCategoryToStationRoot();
                        }
                    } else {
                        if (isFilteredByLevel()) {
                            craftableUri = DatabaseContract.EngramEntry
                                    .buildUriWithLevel(dlc_id, getRequiredLevelPref());
                        } else {
                            craftableUri = DatabaseContract.EngramEntry.buildUriWithDLCId(dlc_id);
                        }
                    }
                }

                mTempStationMap = queryForStations(stationUri);

                if (backCategory != null) {
                    mTempCategoryMap.add(backCategory.getId(), backCategory);
                }

                mTempCategoryMap.addAll(queryForCategories(categoryUri));

                mTempCraftableMap = queryForEngrams(craftableUri);
                return true;
            } catch (Exception e) {
                mException = e;
                return false;
            }
        }
    }

    private class SearchDataTask extends AsyncTask<Void, Void, Boolean> {

        private final String TAG = SearchDataTask.class.getSimpleName();

        private CategoryMap mTempCategoryMap;
        private CraftableMap mTempCraftableMap;

        private Exception mException;

        SearchDataTask() {
        }

        @Override
        protected void onPreExecute() {
            if (getObserver() != null) {
                getObserver().notifyInitializing();
            }
        }

        @Override
        protected void onPostExecute(Boolean querySuccessful) {
            if (querySuccessful) {
                setStations(new StationMap());
                setCategories(mTempCategoryMap);
                setCraftables(mTempCraftableMap);

                notifyDataSetChanged();

                mNeedsUpdate = false;

                if (getObserver() != null) {
                    getObserver().notifyDataSetPopulated();
                }

                buildHierarchy();
            } else {
                if (mException == null) {
                    mException = new Exception("Nullified Exception caught.");
                }

                if (getObserver() != null) {
                    getObserver().notifyExceptionCaught(mException);
                }
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                long dlc_id = PrefsUtil.getInstance(getContext()).getDLCPreference();

                Uri searchUri;
                if (getSearchQuery() == null || getSearchQuery().equals("")) {
                    searchUri = DatabaseContract.EngramEntry.buildUriWithDLCId(dlc_id);
                } else {
                    searchUri = DatabaseContract.EngramEntry
                            .buildUriWithSearchQuery(dlc_id, getSearchQuery());
                }

                mTempCategoryMap = new CategoryMap();

                Category backCategory = BuildBackCategoryOutOfSearch();
                mTempCategoryMap.add(backCategory.getId(), backCategory);

                mTempCraftableMap = querySearchForEngrams(searchUri);

                return true;
            } catch (Exception e) {
                mException = e;
                return false;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_craftable, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        View view = holder.itemView;

        final String imagePath = "file:///android_asset/" + getImagePathByPosition(position);
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(holder.getImageView());

        holder.getNameView().setText(getNameByPosition(position));

        if (isCraftable(position)) {
            int quantity = getQuantityWithYieldByPosition(position);

            if (quantity > 0) {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.frame_queue_item));
                holder.getQuantityView().setText(String.format(Locale.getDefault(), "x%d", quantity));
//                holder.getNameView().setMaxLines( 1 );
            } else {
                view.setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.frame_craftable_item));
                holder.getQuantityView().setText(null);
//                holder.getNameView().setMaxLines( 3 );
            }
        } else {
            view.setBackground(
                    ContextCompat.getDrawable(getContext(), R.drawable.frame_craftable_folder));
            holder.getQuantityView().setText(null);
//            holder.getNameView().setMaxLines( 3 );
        }
    }

    @Override
    public long getItemId(int position) {
        if (isStation(position)) {
            return getStation(position).getId();
        }

        if (isCategory(position)) {
            return getCategory(position).getId();
        }

        if (isCraftable(position)) {
            return getCraftable(adjustPositionForCraftable(position)).getId();
        }

        return super.getItemId(position);
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

        ViewHolder(View view) {
            super(view);

            mImageView = view.findViewById(R.id.image_view);
            mNameView = view.findViewById(R.id.name_view);
            mQuantityView = view.findViewById(R.id.quantity_view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
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
        public void onClick(View view) {
            int position = getAdapterPosition();

            try {
                if (isCraftable(position)) {
                    increaseQuantity(position);
                } else if (isCategory(position)) {
                    changeCategory(position);
                } else if (isStation(position)) {
                    changeStation(position);
                }
            } catch (ExceptionUtil.CursorEmptyException | ExceptionUtil.CursorNullException e) {
                ExceptionObserver.getInstance().notifyFatalExceptionCaught(TAG, e);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return !isCraftable(getAdapterPosition());
        }
    }

    private class CraftableMap extends SortableMap {

        CraftableMap() {
            super();
        }

        @Override
        public DisplayEngram valueAt(int position) {
            return (DisplayEngram) super.valueAt(position);
        }

        @Override
        public Comparable getComparable(int position) {
            return valueAt(position).getName();
        }
    }

    private class StationMap extends SortableMap {

        StationMap() {
            super();
        }

        @Override
        public Station valueAt(int position) {
            return (Station) super.valueAt(position);
        }

        @Override
        public Comparable getComparable(int position) {
            return valueAt(position).getName();
        }
    }

    private class CategoryMap extends SortableMap {

        CategoryMap() {
            super();
        }

        @Override
        public Category valueAt(int position) {
            return (Category) super.valueAt(position);
        }

        @Override
        public Comparable getComparable(int position) {
            return valueAt(position).getName();
        }
    }
}