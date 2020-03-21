/*
 * Copyright (c) 2020 Jared Stone
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

package arc.resource.calculator.repository.explorer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.listeners.NavigationObserver;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.model.Station;
import arc.resource.calculator.model.category.BackCategory;
import arc.resource.calculator.model.category.Category;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.map.CategoryMap;
import arc.resource.calculator.model.map.CraftableMap;
import arc.resource.calculator.model.map.StationMap;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.repository.queue.QueueRepository;
import arc.resource.calculator.tasks.fetch.explorer.FetchExplorerDataTask;
import arc.resource.calculator.tasks.fetch.explorer.FetchExplorerDataTaskObservable;
import arc.resource.calculator.tasks.fetch.explorer.FetchExplorerDataTaskObserver;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;

import static arc.resource.calculator.util.Util.NO_ID;
import static arc.resource.calculator.util.Util.NO_NAME;
import static arc.resource.calculator.util.Util.NO_PATH;
import static arc.resource.calculator.util.Util.NO_QUANTITY;

/**
 * Repository for Explorer Data
 * <p>
 * Communicates changes to explorer navigation through {@ExplorerObserver}.
 */
public class ExplorerRepository implements QueueObserver {
    public static final String TAG = ExplorerRepository.class.getSimpleName();

    public static final long ROOT = 0;
    public static final long NO_STATION = -1;

    private long mLastCategoryLevel;
    private long mLastCategoryParent;
    private long mStationId;

    private StationMap mStationMap;
    private CategoryMap mCategoryMap;
    private CraftableMap mCraftableMap;

    private ExceptionObservable mExceptionObservable;
    private ExplorerObservable mExplorerObservable;
    private PrefsUtil mPrefs;
    private FetchExplorerDataTask mFetchDataTask;
    private boolean mIsFetching;

    private WeakReference<Context> mContext;

    private static ExplorerRepository sInstance;

    public static ExplorerRepository getInstance() {
        if (sInstance == null)
            sInstance = new ExplorerRepository();

        return sInstance;
    }

    private ExplorerRepository() {
        setupMaps();
        setupObservables();
    }

    private void setupMaps() {
        setStations(new StationMap());
        setCategories(new CategoryMap());
        setCraftables(new CraftableMap());
    }

    private void setupMaps(StationMap stationMap, CategoryMap categoryMap, CraftableMap craftableMap) {
        setStations(stationMap);
        setCategories(categoryMap);
        setCraftables(craftableMap);
    }

    private void setupObservables() {
        mExceptionObservable = ExceptionObservable.getInstance();
        mExplorerObservable = ExplorerObservable.getInstance();
    }

    public void addObserver(String key, ExplorerObserver observer) {
        mExplorerObservable.addObserver(key, observer);
    }

    public void removeObserver(String key) {
        mExplorerObservable.removeObserver(key);
    }

    private void resetFetchDataTask() {
        mFetchDataTask = new FetchExplorerDataTask(getContext(), new FetchExplorerDataTaskObservable(new FetchExplorerDataTaskObserver() {
            @Override
            public void onPreFetch() {
                mIsFetching = true;
            }

            @Override
            @MainThread
            public void onFetching() {
                mExplorerObservable.notifyExplorerDataPopulating();
            }

            @Override
            public void onFetchSuccess() {
                mIsFetching = false;
            }

            @Override
            public void onFetchSuccess(StationMap stationMap, CategoryMap categoryMap, CraftableMap craftableMap) {
                mIsFetching = false;
                setupMaps(stationMap, categoryMap, craftableMap);
                mExplorerObservable.notifyExplorerDataPopulated();
            }

            @Override
            public void onFetchException(Exception e) {
                mIsFetching = false;
                mExceptionObservable.notifyExceptionCaught(TAG, e);
            }

            @Override
            public void onFetchFail() {
                // TODO: 1/27/2020 handle onFetchFail()
                mIsFetching = false;
            }

            @Override
            public void onFetchCancel(boolean didCancel) {
                //  attempt to fetch again
                if (mIsFetching) {
                    mIsFetching = false;
                    fetch();
                }
            }
        }));
    }

    private void setupPrefs() {
        mPrefs = PrefsUtil.getInstance(getContext());

        // Retrieve last viewed category level and parent from previous use
        setCurrentCategoryLevels(mPrefs.getLastCategoryLevel(), mPrefs.getLastCategoryParent());

        // Retrieve last viewed Station id
        setCurrentStationId(mPrefs.getLastStationId());
    }

    private void savePrefs() {
        if (mPrefs.getCategoryFilterPreference()) {
            mPrefs.saveCategoryLevels(getCurrentCategoryLevel(), getCurrentCategoryParent());
        }

        if (mPrefs.getStationFilterPreference()) {
            mPrefs.saveStationId(getCurrentStationId());
        }
    }

    private void registerListeners() {
        QueueRepository.getInstance().addObserver(TAG, this);

        PrefsObserver.getInstance().registerListener(TAG, new PrefsObserver.Listener() {
            @Override
            public void onPreferencesChanged(boolean dlcValueChange, boolean categoryPrefChange,
                                             boolean stationPrefChange, boolean levelPrefChange, boolean levelValueChange,
                                             boolean refinedPrefChange) {
                if (dlcValueChange || categoryPrefChange || stationPrefChange || levelPrefChange
                        || levelValueChange) {
                    setCategoryLevelsToRoot();
                    fetch();
                }
            }
        });
    }

    private void unregisterListeners() {
        PrefsObserver.getInstance().unregisterListener(TAG);
        QueueRepository.getInstance().removeObserver(TAG);
    }

    public void resume(Context context) {
        setContext(context);
        setupPrefs();
        registerListeners();

        fetch();
    }

    public void pause() {
        savePrefs();
        unregisterListeners();
    }

    private void setContext(Context context) {
        mContext = new WeakReference<>(context);
    }

    private Context getContext() {
        return mContext.get();
    }

    private StationMap getStationMap() {
        return mStationMap;
    }

    private void setStations(StationMap map) {
        this.mStationMap = map;
    }

    private CategoryMap getCategoryMap() {
        return mCategoryMap;
    }

    private void setCategories(CategoryMap map) {
        this.mCategoryMap = map;
    }

    private CraftableMap getCraftableMap() {
        return mCraftableMap;
    }

    private void setCraftables(CraftableMap map) {
        this.mCraftableMap = map;
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

    private boolean isCategoryParentLevelRoot(long parent) {
        return parent == ROOT;
    }

    private boolean isCategoryParentLevelStationRoot(long parent) {
        return parent == NO_STATION;
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

    private long getCurrentStationId() {
        return mStationId;
    }

    private void setCurrentStationId(long stationId) {
        mStationId = stationId;
    }

    private boolean isFilteredByCategory() {
        return mPrefs.getCategoryFilterPreference();
    }

    private boolean isFilteredByStation() {
        return mPrefs.getStationFilterPreference();
    }

    private boolean isFilteredByLevel() {
        return mPrefs.getLevelFilterPreference();
    }

    private int getRequiredLevelPref() {
        return mPrefs.getRequiredLevel();
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    private String getItemContents() {
        return getStationMap().toString() + ", " + getCategoryMap().toString() + ", "
                + getCraftableMap().toString();
    }

    public int getItemCount() {
        return (getStationMap().size() + getCategoryMap().size() + getCraftableMap().size());
    }

    public long getItemIdByPosition(int position) {
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
            mExceptionObservable.notifyExceptionCaught(TAG, e);

            return NO_ID;
        }
    }

    public String getImagePathByPosition(int position) {
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
            mExceptionObservable.notifyExceptionCaught(TAG, e);

            return NO_PATH;
        }
    }

    public String getNameByPosition(int position) {
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
            mExceptionObservable.notifyExceptionCaught(TAG, e);

            return NO_NAME;
        }
    }

    public int getQuantityWithYieldByPosition(int position) {
        try {
            return getCraftable(adjustPositionForCraftable(position)).getQuantityWithYield();
        } catch (Exception e) {
            return NO_QUANTITY;
        }
    }

    /**
     * CRAFTABLE METHODS
     */

    private int getPositionOfCraftable(long engramId) {
        return mCraftableMap.indexOfKey(engramId);
    }

    private int adjustPositionForCraftable(int position) {
        return position - (getStationMap().size() + getCategoryMap().size());
    }

    private int getAdjustedPositionFromCraftable(int position) {
        return position + (getStationMap().size() + getCategoryMap().size());
    }

    public int getAdjustedPositionFromCraftable(long engramId) {
        return getAdjustedPositionFromCraftable(getPositionOfCraftable(engramId));
    }

    public boolean isCraftable(int position) {
        return Util.isValidPosition(adjustPositionForCraftable(position), getCraftableMap().size());
    }

    private boolean doesContainCraftable(long engramId) {
        return mCraftableMap.contains(engramId);
    }

    public DisplayEngram getCraftableByGlobalPosition(int globalPosition) {
        return getCraftable(adjustPositionForCraftable(globalPosition));
    }

    private DisplayEngram getCraftable(int position) {
        return mCraftableMap.valueAt(position);
    }

    private DisplayEngram getCraftable(long engramId) {
        return mCraftableMap.get(engramId);
    }

    private void updateCraftable(int position, DisplayEngram engram) {
        mCraftableMap.setValueAt(position, engram);
    }

    private void update(@NonNull QueueEngram engram) {
        //  get engram id
        long engramId = engram.getId();
        int quantity = engram.getQuantity();

        //  get engram position in list
        int position = getPositionOfCraftable(engramId);

        //  check if engram position is valid, if so: update, if not: disregard
        if (position > -1) {
            update(position, quantity);
        } else {
            //  do nothing
        }
    }

    private void update(int engramPosition, int quantity) {
        //  get engram from list by position
        DisplayEngram engram = getCraftable(engramPosition);

        //  set engram quantity
        engram.setQuantity(quantity);

        // update engram in craftable list
        updateCraftable(engramPosition, engram);

        //  get global position of engram
        int position = getAdjustedPositionFromCraftable(engramPosition);

        // notify outside listeners of changes
        mExplorerObservable.notifyEngramUpdated(position);
    }

    public void increaseQuantity(int position) {
        QueueRepository.getInstance().increaseQuantity(getCraftable(adjustPositionForCraftable(position)));
    }

    private void updateQuantities() {
        QueueRepository queueRepository = QueueRepository.getInstance();

        for (int i = 0; i < getCraftableMap().size(); i++) {
            DisplayEngram engram = getCraftable(i);

            if (queueRepository.doesContainEngram(engram.getId())) {
                int quantity = queueRepository.getEngramQuantity(engram.getId());

                update(i, quantity);
            }
        }
    }

    private void clearQuantities() {
        for (int i = 0; i < getCraftableMap().size(); i++) {
            DisplayEngram engram = getCraftable(i);

            if (engram.getQuantity() > 0)
                update(i, 0);
        }
    }

    private DisplayEngram queryForEngram(Context context, Uri uri, int quantity) {
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return null;
            if (!cursor.moveToFirst()) return null;

            DisplayEngram engram = DisplayEngram.fromCursor(cursor);
            engram.setQuantity(quantity);

            return engram;
        }
    }

    /**
     * CATEGORY METHODS
     */

    private int adjustPositionForCategory(int position) {
        return position - getStationMap().size();
    }

    public boolean isCategory(int position) {
        return Util.isValidPosition(adjustPositionForCategory(position), getCategoryMap().size());
    }

    public Category getCategory(int position) {
        return getCategoryMap().valueAt(position);
    }

    public void changeCategory(int position)
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {
        Category category = getCategory(position);

        long dlc_id = mPrefs.getDLCPreference();
        if (position == 0) {
            // position 0 will always be a back category to the previous level
            long parent = category.getParent();
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
                            queryForCategory(DatabaseContract.CategoryEntry.buildUriWithId(
                                    dlc_id, category.getParent())).getParent());
                }
            }
        } else {
            // Normal Category object
            // Grabbing ID is the best way to track its location.
            setCurrentCategoryLevels(category.getId(), category.getParent());
        }

        savePrefs();
        fetch();
    }

    private Category queryForCategory(Uri uri)
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) throw new ExceptionUtil.CursorNullException(uri);

            if (!cursor.moveToFirst()) throw new ExceptionUtil.CursorEmptyException(uri);

            return Category.fromCursor(cursor);
        }
    }

    private BackCategory BuildBackCategory() {
        return new BackCategory(getCurrentCategoryParent(), getCurrentCategoryParent());
    }

    private BackCategory BuildBackCategoryToStationRoot() {
        return new BackCategory(NO_STATION, NO_STATION);
    }

    /**
     * STATION METHODS
     */

    public boolean isStation(int position) {
        return Util.isValidPosition(position, getStationMap().size());
    }

    public Station getStation(int position) {
        return getStationMap().valueAt(position);
    }

    public void changeStation(int position) {
        Station station = getStation(position);

        setCurrentStationId(station.getId());
        setCurrentCategoryLevelsToRoot();

        savePrefs();
        fetch();
    }

    private Station queryForStation(Uri uri)
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {

        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) throw new ExceptionUtil.CursorNullException(uri);

            if (!cursor.moveToFirst()) throw new ExceptionUtil.CursorEmptyException(uri);
            return Station.fromCursor(cursor);
        }

    }

    private void buildHierarchy() {
        try {
            long dlc_id = mPrefs.getDLCPreference();

            StringBuilder builder = new StringBuilder();
            if (isFilteredByStation()) {
                if (isCurrentCategoryLevelStationRoot() || getCurrentStationId() == NO_STATION) {
                    builder
                            .append(getContext().getString(R.string.display_case_hierarchy_text_all_stations));
                } else {
                    String name = queryForStation(DatabaseContract.StationEntry.buildUriWithId(dlc_id, getCurrentStationId()))
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
                    builder.append("/").append(buildCategoryHierarchy(DatabaseContract.CategoryEntry.buildUriWithId(dlc_id, getCurrentCategoryLevel())));
                }
            } else {
                builder.append(getContext().getString(R.string.display_case_hierarchy_text_all_engrams));
            }

            NavigationObserver.getInstance().update(builder.toString());
        } catch (ExceptionUtil.CursorNullException | ExceptionUtil.CursorEmptyException e) {
            ExceptionObservable.getInstance().notifyFatalExceptionCaught(TAG, e);
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

    public static StationMap queryForStations(Context context, Uri uri)
            throws ExceptionUtil.CursorNullException {

        if (uri == null) return new StationMap();

        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null)
                throw new ExceptionUtil.CursorNullException(uri);   // TODO: 1/26/2020 Why do we throw exception here, but not other full map queries?

            StationMap stationMap = new StationMap();
            while (cursor.moveToNext()) {
                Station station = Station.fromCursor(cursor);
                stationMap.put(station.getId(), station);
            }

            stationMap.sort();  // TODO: 1/26/2020 Sort during query?

            return stationMap;
        }

    }

    public static CategoryMap queryForCategories(Context context, Uri uri) {
        if (uri == null) return new CategoryMap();

        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null)
                return new CategoryMap();   // TODO: 1/26/2020 Why don't we throw exception here, but we do during station query?

            CategoryMap categoryMap = new CategoryMap();
            while (cursor.moveToNext()) {
                Category category = Category.fromCursor(cursor);
                categoryMap.add(category.getId(), category);
            }

            categoryMap.sort(); // TODO: 1/26/2020 Sort during query, or as we inserted?

            return categoryMap;
        }
    }

    public static CraftableMap queryForEngrams(Context context, Uri uri) {
        if (uri == null) return new CraftableMap();

        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null)
                return new CraftableMap();   // TODO: 1/26/2020 Why don't we throw exception here, but we do during station query?

            QueueRepository queueRepository = QueueRepository.getInstance();

            CraftableMap craftables = new CraftableMap();
            while (cursor.moveToNext()) {
                DisplayEngram engram = DisplayEngram.fromCursor(cursor);
                engram.setQuantity(queueRepository.getEngramQuantity(engram.getId()));

                craftables.add(engram.getId(), engram);
            }

            craftables.sort();  // TODO: 4/30/2017 What if we sorted as we inserted?

            return craftables;
        }
    }

    public Bundle gatherUrisForDataTask(long dlc_id) {
        Uri craftableUri = null;
        Uri categoryUri = null;
        Uri stationUri = null;
        BackCategory backCategory = null;

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

        Bundle bundle = new Bundle();
        bundle.putParcelable("station", stationUri);
        bundle.putParcelable("category", categoryUri);
        bundle.putParcelable("craftable", craftableUri);
        if (backCategory != null) {
            bundle.putLong("backCategoryId", backCategory.getId());
            bundle.putLong("backCategoryParent", backCategory.getParent());
        }

        return bundle;
    }

    private void fetch() {
        Log.d(TAG, "fetch: " + mIsFetching);
        if (mIsFetching) {
            mFetchDataTask.cancel(true);
        } else {
            resetFetchDataTask();
            mFetchDataTask.execute();
        }
    }

    @Override
    public void onItemAdded(@NonNull QueueEngram engram) {
        update(engram);
    }

    @Override
    public void onItemRemoved(@NonNull QueueEngram engram) {
        update(engram);
    }

    @Override
    public void onItemChanged(@NonNull QueueEngram engram) {
        update(engram);
    }

    @Override
    public void onQueueDataPopulating() {
        //  do nothing
    }

    @Override
    public void onQueueDataPopulated() {
        updateQuantities();
    }

    @Override
    public void onQueueDataEmpty() {
        clearQuantities();
    }

}