package arc.resource.calculator.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
public class PreferenceHelper {
    private SharedPreferences mSharedPreferences;

    public PreferenceHelper( Context context ) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
    }

    public int getIntPreference(String key) {
        return mSharedPreferences.getInt( key, 0 );
    }

    public String getStringPreference(String key) {
        return mSharedPreferences.getString( key, null );
    }

    public boolean getBooleanPreference(String key, boolean def) {
        return mSharedPreferences.getBoolean( key, def );
    }

    public long getLongPreference(String key) {
        return mSharedPreferences.getLong( key, 0 );
    }

    public void setPreference(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putInt(key, value);
        editor.apply();
    }

    public void setPreference(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public void setPreference(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

    public void setPreference(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putLong(key, value);
        editor.apply();
    }
}