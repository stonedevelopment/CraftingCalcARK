package arc.resource.calculator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import arc.resource.calculator.R;

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

// TODO: 4/5/2017 Make PrefsUtil listen for changes in prefs, allow for variable access to replace preference access
// TODO: 4/6/2017 Make SharedPrefs take in Long, Int, Float instead of converting to String everytime.
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

    private String CraftableThumbnailImageSizeKey;

    public static void createInstance( Context context ) {
        sInstance = new PrefsUtil( context );
    }

    public static PrefsUtil getInstance() {
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

        CraftableThumbnailImageSizeKey = context.getString( R.string.pref_craftable_image_size_key );
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
        return mSharedPreferences.getBoolean( key, defaultValue );
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

    public int getCraftableThumbnailImageSize() {
        return getPreference( CraftableThumbnailImageSizeKey, Util.NO_SIZE );
    }

    public void saveCraftableThumbnailImageSize( int size ) {
        editPreference( CraftableThumbnailImageSizeKey, size );
    }

    public void saveCategoryLevels( long level, long parent ) {
        editPreference( LastCategoryLevelKey, level );
        editPreference( LastCategoryParentKey, parent );
    }

    public void saveRequiredLevel( int level ) {
        editPreference( RequiredLevelFilterKey, level );
    }

    public void saveStationId( long station_id ) {
        editPreference( LastStationIdKey, station_id );
    }

    private void saveDLCId( long dlc_id ) {
        editPreference( DLCIdKey, dlc_id );
    }

    public void saveToDefault() {
        saveStationIdBackToDefault();
        saveCategoryLevelsBackToDefault();
    }

    public void saveToDefaultFullReset() {
        clearPreferences();

        saveFiltersBackToDefault();

        saveDLCIdBackToDefault();
        saveStationIdBackToDefault();
        saveCategoryLevelsBackToDefault();
        saveRequiredLevelBackToDefault();
    }

    private void saveDLCIdBackToDefault() {
        saveDLCId( DLCIdDefaultValue );
    }

    private void saveFiltersBackToDefault() {
        editPreference( StationFilterKey, StationFilterDefaultValue );
        editPreference( CategoryFilterKey, CategoryFilterDefaultValue );
        editPreference( RequiredLevelFilterKey, RequiredLevelFilterDefaultValue );
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

    private void clearPreferences() {
        mSharedPreferences.edit().clear().apply();
    }
}