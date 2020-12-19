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

package arc.resource.calculator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Date;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.PrefsObserver;

// TODO: 5/16/2017 Why continue to use string resources instead of static constants?
public class PrefsUtil {
    public static final String TAG = PrefsUtil.class.getSimpleName();
    public static final String cVersionPrimary = "versionPrimary";
    public static final String cVersionDLC = "versionDLC_";
    private static final String cDidUpdate = "didUpdate";
    public static String FirstUseKey = "first_use";
    public static boolean FirstUseDefaultValue = true;
    private static PrefsUtil sInstance;
    private static String JSONVersionKey;

    private final SharedPreferences prefs;

    private String GameIdKey;
    private String GameIdDefaultValue;

    private String DlcFilterKey;
    private boolean DlcFilterDefaultValue;

    private String DlcIdKey;
    private String DlcIdDefaultValue;

    private String RequiredLevelFilterKey;
    private boolean RequiredLevelFilterDefaultValue;
    private String RequiredLevelKey;
    private int RequiredLevelDefaultValue;

    private String RefinedFilterKey;
    private boolean RefinedFilterDefaultValue;
    private String CategoryFilterKey;
    private boolean CategoryFilterDefaultValue;
    private String StationFilterKey;
    private boolean StationFilterDefaultValue;
    private String LastCategoryLevelKey;
    private String LastCategoryParentKey;
    private long LastCategoryLevelDefaultValue;
    private String LastStationIdKey;
    private long LastStationIdDefaultValue;
    private String CraftingQueueKey;
    private String SearchQueryKey;
    private String PurchasableRemoveAdsKey;
    private boolean PurchasableRemoveAdsDefaultValue;

    private PrefsUtil(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);

        GameIdKey = c.getString(R.string.pref_game_id_key);
        GameIdDefaultValue = null;

        DlcFilterKey = c.getString(R.string.pref_dlc_filter_key);
        DlcFilterDefaultValue = Boolean.parseBoolean(c.getString(R.string.pref_dlc_filter_default_value));

        DlcIdKey = c.getString(R.string.pref_dlc_id_key);
        DlcIdDefaultValue = null;

        RefinedFilterKey = c.getString(R.string.pref_filter_refined_key);
        RefinedFilterDefaultValue = Boolean.parseBoolean(c.getString(R.string.pref_filter_refined_value_default));

        CategoryFilterKey = c.getString(R.string.pref_filter_category_key);
        CategoryFilterDefaultValue = Boolean.parseBoolean(c.getString(R.string.pref_filter_category_value_default));

        StationFilterKey = c.getString(R.string.pref_filter_station_key);
        StationFilterDefaultValue = Boolean.parseBoolean(c.getString(R.string.pref_filter_station_value_default));

        RequiredLevelFilterKey = c.getString(R.string.pref_filter_level_key);
        RequiredLevelFilterDefaultValue = Boolean.parseBoolean(c.getString(R.string.pref_filter_level_value_default));

        JSONVersionKey = c.getString(R.string.pref_json_version_key);

        LastCategoryLevelKey = c.getString(R.string.pref_category_level_key);
        LastCategoryParentKey = c.getString(R.string.pref_category_parent_key);
        LastCategoryLevelDefaultValue = Long.parseLong(c.getString(R.string.pref_category_default_value));

        LastStationIdKey = c.getString(R.string.pref_station_id_key);
        LastStationIdDefaultValue = Long.parseLong(c.getString(R.string.pref_station_id_default_value));

        RequiredLevelKey = c.getString(R.string.pref_edit_text_level_key);
        RequiredLevelDefaultValue = Integer.parseInt(c.getString(R.string.pref_edit_text_level_value_default));

        CraftingQueueKey = c.getString(R.string.pref_crafting_queue_key);

        SearchQueryKey = c.getString(R.string.pref_search_query_key);

        PurchasableRemoveAdsKey = c.getString(R.string.pref_purchasable_remove_ads_key);
        PurchasableRemoveAdsDefaultValue = Boolean.parseBoolean(c.getString(R.string.pref_purchasable_remove_ads_value_default));
    }

    public static PrefsUtil getInstance(Context context) {
        if (sInstance == null)
            sInstance = new PrefsUtil(context);

        return sInstance;
    }

    private String getPreference(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    private long getPreference(String key, long defaultValue) {
        return Long.parseLong(getPreference(key, String.valueOf(defaultValue)));
    }

    private int getPreference(String key, int defaultValue) {
        return Integer.parseInt(getPreference(key, String.valueOf(defaultValue)));
    }

    private boolean getPreference(String key, boolean defaultValue) {
        try {
            return prefs.getBoolean(key, defaultValue);
        } catch (ClassCastException e) {
            Log.e(TAG, "getPreference: ClassCastException: " + defaultValue + " for " + prefs.getString(key, String.valueOf(defaultValue)), e);
            return Boolean.parseBoolean(prefs.getString(key, String.valueOf(defaultValue)));
        }
    }

    private float getPreference(String key, float defaultValue) {
        return Float.parseFloat(getPreference(key, String.valueOf(defaultValue)));
    }

    private void editPreference(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    private void editPreference(String key, long value) {
        editPreference(key, String.valueOf(value));
    }

    private void editPreference(String key, int value) {
        editPreference(key, String.valueOf(value));
    }

    private void editPreference(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    private void editPreference(String key, float value) {
        editPreference(key, String.valueOf(value));
    }

    private void editPreference(String uuid, Date newDate) {
        editPreference(uuid, newDate.getTime());
    }

    private void removePreference(String key) {
        prefs.edit().remove(key).apply();
    }

    public String getGameIdPreference() {
        return getPreference(GameIdKey, GameIdDefaultValue);
    }

    public void saveGameIdPreference(String gameId) {
        editPreference(GameIdKey, gameId);
    }

    public String getDlcIdPreference() {
        return getPreference(DlcIdKey, DlcIdDefaultValue);
    }

    public void saveDlcIdPreference(String dlcId) {
        editPreference(DlcIdKey, dlcId);
    }

    public boolean getDlcFilterPreference() {
        return getPreference(DlcFilterKey, DlcFilterDefaultValue);
    }

    public boolean getRefinedFilterPreference() {
        return getPreference(RefinedFilterKey, RefinedFilterDefaultValue);
    }

    public boolean getCategoryFilterPreference() {
        return getPreference(CategoryFilterKey, CategoryFilterDefaultValue);
    }

    public boolean getStationFilterPreference() {
        return getPreference(StationFilterKey, StationFilterDefaultValue);
    }

    public boolean getLevelFilterPreference() {
        return getPreference(RequiredLevelFilterKey, RequiredLevelFilterDefaultValue);
    }

    public long getLastUpdateFromUuid(String uuid) {
        return getPreference(uuid, 0L);
    }

    public void setLastUpdateByUuid(String uuid, Date lastUpdate) {
        editPreference(uuid, lastUpdate);
    }

    public boolean getDidUpdate() {
        return getPreference(cDidUpdate, false);
    }

    public void setDidUpdate(boolean didUpdate) {
        editPreference(cDidUpdate, didUpdate);
    }

    public String getCraftingQueueJSONString() {
        return getPreference(CraftingQueueKey, null);
    }

    public void saveCraftingQueueJSONString(String jsonString) {
        editPreference(CraftingQueueKey, jsonString);
    }

    public long getLastCategoryLevel() {
        if (getStationFilterPreference())
            return getPreference(LastCategoryLevelKey, LastStationIdDefaultValue);
        else
            return getPreference(LastCategoryLevelKey, LastCategoryLevelDefaultValue);
    }

    public long getLastCategoryParent() {
        if (getStationFilterPreference())
            return getPreference(LastCategoryParentKey, LastStationIdDefaultValue);
        else
            return getPreference(LastCategoryParentKey, LastCategoryLevelDefaultValue);
    }

    public long getLastStationId() {
        return getPreference(LastStationIdKey, LastStationIdDefaultValue);
    }

    public int getRequiredLevel() {
        return getPreference(RequiredLevelKey, RequiredLevelDefaultValue);
    }

    public String getSearchQuery() {
        return getPreference(SearchQueryKey, null);
    }

    public boolean getPurchasableRemoveAds() {
        return getPreference(PurchasableRemoveAdsKey, PurchasableRemoveAdsDefaultValue);
    }

    public void saveCategoryLevels(long level, long parent) {
        editPreference(LastCategoryLevelKey, level);
        editPreference(LastCategoryParentKey, parent);
    }

    private void saveRequiredLevel(int level) {
        editPreference(RequiredLevelKey, level);
    }

    public void saveStationId(long station_id) {
        editPreference(LastStationIdKey, station_id);
    }

    private void saveDlcId(String dlcId) {
        editPreference(DlcIdKey, dlcId);
    }

    public void saveRefinedFilterPreference(boolean isRefined) {
        editPreference(RefinedFilterKey, isRefined);

        PrefsObserver.getInstance().notifyPreferencesChanged(false, false, false, false, false, true);
    }

    public void saveToDefault() {
        saveStationIdBackToDefault();
        saveCategoryLevelsBackToDefault();
    }

    void saveToDefaultFullReset() {
        resetFiltersBackToDefault();

        removePreference(DlcIdKey);
        saveDLCIdBackToDefault();

        removePreference(LastStationIdKey);
        saveStationIdBackToDefault();

        removePreference(LastCategoryLevelKey);
        removePreference(LastCategoryParentKey);
        saveCategoryLevelsBackToDefault();

        removePreference(RequiredLevelKey);
        saveRequiredLevelBackToDefault();

        PrefsObserver.getInstance().notifyPreferencesChanged(true, true, true, true, true, true);
    }

    private void saveDLCIdBackToDefault() {
        saveDlcId(DlcIdDefaultValue);
    }

    private void resetFiltersBackToDefault() {
        removePreference(StationFilterKey);
        editPreference(StationFilterKey, StationFilterDefaultValue);

        removePreference(CategoryFilterKey);
        editPreference(CategoryFilterKey, CategoryFilterDefaultValue);

        removePreference(RequiredLevelFilterKey);
        editPreference(RequiredLevelFilterKey, RequiredLevelFilterDefaultValue);

        removePreference(RefinedFilterKey);
        editPreference(RefinedFilterKey, RefinedFilterDefaultValue);
    }

    private void saveCategoryLevelsBackToDefault() {
        if (getStationFilterPreference())
            saveCategoryLevels(LastStationIdDefaultValue, LastStationIdDefaultValue);
        else
            saveCategoryLevels(LastCategoryLevelDefaultValue, LastCategoryLevelDefaultValue);
    }

    private void saveStationIdBackToDefault() {
        saveStationId(LastStationIdDefaultValue);
    }

    private void saveRequiredLevelBackToDefault() {
        saveRequiredLevel(RequiredLevelDefaultValue);
    }

    public void saveSearchQuery(String query) {
        editPreference(SearchQueryKey, query);
    }

    public void savePurchasableRemoveAds(boolean removeAds) {
        editPreference(PurchasableRemoveAdsKey, removeAds);
    }
}