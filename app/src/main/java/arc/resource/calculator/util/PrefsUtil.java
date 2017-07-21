package arc.resource.calculator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.views.MainSwitcher;

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

// TODO: 5/16/2017 Why continue to use string resources instead of static constants?
public class PrefsUtil {
    private static final String TAG = PrefsUtil.class.getSimpleName();

    private static PrefsUtil sInstance;

    private final SharedPreferences mSharedPreferences;

    private String DLCIdKey;
    private long DLCIdDefaultValue;

    private String RefinedFilterKey;
    private boolean RefinedFilterDefaultValue;

    private String CategoryFilterKey;
    private boolean CategoryFilterDefaultValue;

    private String StationFilterKey;
    private boolean StationFilterDefaultValue;

    private String RequiredLevelFilterKey;
    private boolean RequiredLevelFilterDefaultValue;

    private String RequiredLevelKey;
    private int RequiredLevelDefaultValue;

    private static String JSONVersionKey;

    private String LastCategoryLevelKey;
    private String LastCategoryParentKey;
    private long LastCategoryLevelDefaultValue;

    private String LastStationIdKey;
    private long LastStationIdDefaultValue;

    private String CraftingQueueKey;

    private String CraftableViewSizeKey;
    private String MainSwitcherScreenIdKey;

    private String SearchQueryKey;

    private String PurchasableRemoveAdsKey;
    private boolean PurchasableRemoveAdsDefaultValue;

    public static String FirstUseKey = "first_use";
    public static boolean FirstUseDefaultValue = true;

    public static PrefsUtil getInstance( Context context ) {
        if ( sInstance == null )
            sInstance = new PrefsUtil( context );

        return sInstance;
    }

    private PrefsUtil( Context context ) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        DLCIdKey = context.getString( R.string.pref_dlc_key );
        DLCIdDefaultValue = Long.parseLong( context.getString( R.string.pref_dlc_value_default ) );

        RefinedFilterKey = context.getString( R.string.pref_filter_refined_key );
        RefinedFilterDefaultValue = Boolean.parseBoolean( context.getString( R.string.pref_filter_refined_value_default ) );

        CategoryFilterKey = context.getString( R.string.pref_filter_category_key );
        CategoryFilterDefaultValue = Boolean.parseBoolean( context.getString( R.string.pref_filter_category_value_default ) );

        StationFilterKey = context.getString( R.string.pref_filter_station_key );
        StationFilterDefaultValue = Boolean.parseBoolean( context.getString( R.string.pref_filter_station_value_default ) );

        RequiredLevelFilterKey = context.getString( R.string.pref_filter_level_key );
        RequiredLevelFilterDefaultValue = Boolean.parseBoolean( context.getString( R.string.pref_filter_level_value_default ) );

        JSONVersionKey = context.getString( R.string.pref_json_version_key );

        LastCategoryLevelKey = context.getString( R.string.pref_category_level_key );
        LastCategoryParentKey = context.getString( R.string.pref_category_parent_key );
        LastCategoryLevelDefaultValue = Long.parseLong( context.getString( R.string.pref_category_default_value ) );

        LastStationIdKey = context.getString( R.string.pref_station_id_key );
        LastStationIdDefaultValue = Long.parseLong( context.getString( R.string.pref_station_id_default_value ) );

        RequiredLevelKey = context.getString( R.string.pref_edit_text_level_key );
        RequiredLevelDefaultValue = Integer.parseInt( context.getString( R.string.pref_edit_text_level_value_default ) );

        CraftingQueueKey = context.getString( R.string.pref_crafting_queue_key );

        CraftableViewSizeKey = context.getString( R.string.pref_craftable_view_size_key );

        MainSwitcherScreenIdKey = context.getString( R.string.pref_switcher_main_screen_id_key );

        SearchQueryKey = context.getString( R.string.pref_search_query_key );

        PurchasableRemoveAdsKey = context.getString( R.string.pref_purchasable_remove_ads_key );
        PurchasableRemoveAdsDefaultValue = Boolean.parseBoolean( context.getString( R.string.pref_purchasable_remove_ads_value_default ) );
    }

    private String getPreference( String key, String defaultValue ) {
        return mSharedPreferences.getString( key, defaultValue );
    }

    private long getPreference( String key, long defaultValue ) {
        return Long.parseLong( getPreference( key, String.valueOf( defaultValue ) ) );
    }

    private int getPreference( String key, int defaultValue ) {
        return Integer.parseInt( getPreference( key, String.valueOf( defaultValue ) ) );
    }

    private boolean getPreference( String key, boolean defaultValue ) {
        try {
            return mSharedPreferences.getBoolean( key, defaultValue );
        } catch ( ClassCastException e ) {
            Log.e( TAG, "getPreference: ClassCastException: " + defaultValue + " for " + mSharedPreferences.getString( key, String.valueOf( defaultValue ) ), e );
            return Boolean.parseBoolean( mSharedPreferences.getString( key, String.valueOf( defaultValue ) ) );
        }
    }

    private float getPreference( String key, float defaultValue ) {
        return Float.parseFloat( getPreference( key, String.valueOf( defaultValue ) ) );
    }

    private void editPreference( String key, String value ) {
        mSharedPreferences.edit().putString( key, value ).apply();
    }

    private void editPreference( String key, long value ) {
        editPreference( key, String.valueOf( value ) );
    }

    private void editPreference( String key, int value ) {
        editPreference( key, String.valueOf( value ) );
    }

    private void editPreference( String key, boolean value ) {
        mSharedPreferences.edit().putBoolean( key, value ).apply();
    }

    private void editPreference( String key, float value ) {
        editPreference( key, String.valueOf( value ) );
    }

    private void removePreference( String key ) {
        mSharedPreferences.edit().remove( key ).commit();
    }

    public long getDLCPreference() {
        return getPreference( DLCIdKey, DLCIdDefaultValue );
    }

    public boolean getRefinedFilterPreference() {
        return getPreference( RefinedFilterKey, RefinedFilterDefaultValue );
    }

    public boolean getCategoryFilterPreference() {
        return getPreference( CategoryFilterKey, CategoryFilterDefaultValue );
    }

    public boolean getStationFilterPreference() {
        return getPreference( StationFilterKey, StationFilterDefaultValue );
    }

    public boolean getLevelFilterPreference() {
        return getPreference( RequiredLevelFilterKey, RequiredLevelFilterDefaultValue );
    }

    public String getJSONVersion() {
        return getPreference( JSONVersionKey, null );
    }

    public void updateJSONVersion( String value ) {
        editPreference( JSONVersionKey, value );
    }

    public String getCraftingQueueJSONString() {
        return getPreference( CraftingQueueKey, null );
    }

    public void saveCraftingQueueJSONString( String jsonString ) {
        editPreference( CraftingQueueKey, jsonString );
    }

    public long getLastCategoryLevel() {
        if ( getStationFilterPreference() )
            return getPreference( LastCategoryLevelKey, LastStationIdDefaultValue );
        else
            return getPreference( LastCategoryLevelKey, LastCategoryLevelDefaultValue );
    }

    public long getLastCategoryParent() {
        if ( getStationFilterPreference() )
            return getPreference( LastCategoryParentKey, LastStationIdDefaultValue );
        else
            return getPreference( LastCategoryParentKey, LastCategoryLevelDefaultValue );
    }

    public long getLastStationId() {
        return getPreference( LastStationIdKey, LastStationIdDefaultValue );
    }

    public int getRequiredLevel() {
        return getPreference( RequiredLevelKey, RequiredLevelDefaultValue );
    }

    public int getCraftableViewSize() {
        return getPreference( CraftableViewSizeKey, Util.NO_SIZE );
    }

    public int getMainSwitcherScreenId() {
        return getPreference( MainSwitcherScreenIdKey, MainSwitcher.DEFAULT_SCREEN );
    }

    public String getSearchQuery() {
        return getPreference( SearchQueryKey, null );
    }

    public boolean getPurchasableRemoveAds() {
        return getPreference( PurchasableRemoveAdsKey, PurchasableRemoveAdsDefaultValue );
    }

    public void saveCraftableViewSize( int size ) {
        editPreference( CraftableViewSizeKey, size );
    }

    public void saveCategoryLevels( long level, long parent ) {
        editPreference( LastCategoryLevelKey, level );
        editPreference( LastCategoryParentKey, parent );
    }

    public void saveRequiredLevel( int level ) {
        editPreference( RequiredLevelKey, level );
    }

    public void saveStationId( long station_id ) {
        editPreference( LastStationIdKey, station_id );
    }

    private void saveDLCId( long dlc_id ) {
        editPreference( DLCIdKey, dlc_id );
    }

    public void saveRefinedFilterPreference( boolean isRefined ) {
        editPreference( RefinedFilterKey, isRefined );

        PrefsObserver.getInstance().notifyPreferencesChanged( false, false, false, false, false, true );
    }

    public void saveToDefault() {
        saveStationIdBackToDefault();
        saveCategoryLevelsBackToDefault();
    }

    public void saveToDefaultFullReset() {
        resetFiltersBackToDefault();

        removePreference( DLCIdKey );
        saveDLCIdBackToDefault();

        removePreference( LastStationIdKey );
        saveStationIdBackToDefault();

        removePreference( LastCategoryLevelKey );
        removePreference( LastCategoryParentKey );
        saveCategoryLevelsBackToDefault();

        removePreference( RequiredLevelKey );
        saveRequiredLevelBackToDefault();

        PrefsObserver.getInstance().notifyPreferencesChanged( true, true, true, true, true, true );
    }

    private void saveDLCIdBackToDefault() {
        saveDLCId( DLCIdDefaultValue );
    }

    private void resetFiltersBackToDefault() {
        removePreference( StationFilterKey );
        editPreference( StationFilterKey, StationFilterDefaultValue );

        removePreference( CategoryFilterKey );
        editPreference( CategoryFilterKey, CategoryFilterDefaultValue );

        removePreference( RequiredLevelFilterKey );
        editPreference( RequiredLevelFilterKey, RequiredLevelFilterDefaultValue );

        removePreference( RefinedFilterKey );
        editPreference( RefinedFilterKey, RefinedFilterDefaultValue );
    }

    private void saveCategoryLevelsBackToDefault() {
        if ( getStationFilterPreference() )
            saveCategoryLevels( LastStationIdDefaultValue, LastStationIdDefaultValue );
        else
            saveCategoryLevels( LastCategoryLevelDefaultValue, LastCategoryLevelDefaultValue );
    }

    private void saveStationIdBackToDefault() {
        saveStationId( LastStationIdDefaultValue );
    }

    private void saveRequiredLevelBackToDefault() {
        saveRequiredLevel( RequiredLevelDefaultValue );
    }

    public void saveMainSwitcherScreenId( int id ) {
        editPreference( MainSwitcherScreenIdKey, id );
    }

    public void saveSearchQuery( String query ) {
        editPreference( SearchQueryKey, query );
    }

    public void savePurchasableRemoveAds( boolean removeAds ) {
        editPreference( PurchasableRemoveAdsKey, removeAds );
    }
}