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
public class PrefsUtil {
    private static final String TAG = PrefsUtil.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public PrefsUtil( Context context ) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
    }

    public long getDLCPreference() {
        return Long.parseLong( mSharedPreferences.getString(
                mContext.getString( R.string.pref_dlc_key ),
                mContext.getString( R.string.pref_dlc_value_default ) )
        );
    }

    public boolean getRefinedFilterPreference() {
        return mSharedPreferences.getBoolean(
                mContext.getString( R.string.pref_filter_refined_key ),
                Boolean.parseBoolean( mContext.getString( R.string.pref_filter_refined_value_default ) )
        );
    }

    public boolean getCategoryFilterPreference() {
        return mSharedPreferences.getBoolean(
                mContext.getString( R.string.pref_filter_category_key ),
                Boolean.parseBoolean( mContext.getString( R.string.pref_filter_category_value_default ) )
        );
    }

    public boolean getStationFilterPreference() {
        return mSharedPreferences.getBoolean(
                mContext.getString( R.string.pref_filter_station_key ),
                Boolean.parseBoolean( mContext.getString( R.string.pref_filter_station_value_default ) )
        );
    }

    public boolean getLevelFilterPreference() {
        return mSharedPreferences.getBoolean(
                mContext.getString( R.string.pref_filter_level_key ),
                Boolean.parseBoolean( mContext.getString( R.string.pref_filter_level_value_default ) )
        );
    }

    public String getJSONVersion() {
        return mSharedPreferences.getString(
                mContext.getString( R.string.pref_json_version ),
                null
        );
    }

    public void updateJSONVersion( String value ) {
        mSharedPreferences
                .edit()
                .putString( mContext.getString( R.string.pref_json_version ), value )
                .apply();
    }

    public long getLastCategoryLevel() {
        if ( getStationFilterPreference() ) {
            return mSharedPreferences.getLong(
                    mContext.getString( R.string.pref_category_level ),
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value_with_stations ) )
            );
        } else {
//            return 0;
            return mSharedPreferences.getLong(
                    mContext.getString( R.string.pref_category_level ),
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value ) )
            );
        }
    }

    public long getLastCategoryParent() {
        if ( getStationFilterPreference() ) {
            return mSharedPreferences.getLong(
                    mContext.getString( R.string.pref_category_parent ),
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value_with_stations ) )
            );
        } else {
            return mSharedPreferences.getLong(
                    mContext.getString( R.string.pref_category_parent ),
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value ) )
            );
//            return 0;
        }
    }

    public long getLastStationId() {
        return mSharedPreferences.getLong(
                mContext.getString( R.string.pref_station_id ),
                Long.parseLong( mContext.getString( R.string.pref_station_id_default_value ) )
        );
    }

    public int getRequiredLevel() {
        return Integer.parseInt( mSharedPreferences.getString(
                mContext.getString( R.string.pref_edit_text_level_key ),
                mContext.getString( R.string.pref_edit_text_level_value_default ) )
        );
    }

    public void saveCategoryLevels( long level, long parent ) {
        mSharedPreferences
                .edit()
                .putLong( mContext.getString( R.string.pref_category_level ), level )
                .putLong( mContext.getString( R.string.pref_category_parent ), parent )
                .apply();
    }

    public void saveCategoryLevelsBackToDefault() {
        if ( getStationFilterPreference() ) {
            saveCategoryLevels(
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value_with_stations ) ),
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value_with_stations ) )
            );
        } else {
            saveCategoryLevels(
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value ) ),
                    Long.parseLong( mContext.getString( R.string.pref_category_default_value ) )
            );
        }
    }

    public void saveStationId( long station_id ) {
        mSharedPreferences
                .edit()
                .putLong( mContext.getString( R.string.pref_station_id ), station_id )
                .apply();
    }

    public void saveStationIdBackToDefault() {
        saveStationId(
                Long.parseLong( mContext.getString( R.string.pref_station_id_default_value ) )
        );
    }

    public boolean getPurchasePref( String sku ) {
        return mSharedPreferences.getBoolean( sku, false );
    }

    public void setPurchasePref( String sku, boolean value ) {
        mSharedPreferences
                .edit()
                .putBoolean( sku, value )
                .apply();
    }
}