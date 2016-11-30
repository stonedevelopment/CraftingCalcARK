package arc.resource.calculator.util;

import android.util.Log;
import android.util.SparseArray;

import arc.resource.calculator.model.Category;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.model.resource.QueueResource;

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
public class Helper {
    public static final int REQUEST_CODE_DETAIL_ACTIVITY = 1000;
    public static final int REQUEST_CODE_SETTINGS_ACTIVITY = 2000;

    public static final String RESULT_CODE_KEY = "result";

    public static final String RESULT_CODE_DETAIL_ACTIVITY = "detail";
    public static final boolean RESULT_CODE_SETTINGS_ACTIVITY_TRUE = true;
    public static final boolean RESULT_CODE_SETTINGS_ACTIVITY_FALSE = false;

    public static final String DETAIL_ID = "DETAIL_ID";
    public static final String DETAIL_QUANTITY = "DETAIL_QUANTITY";
    public static final String DETAIL_REMOVE = "DETAIL_REMOVE";
    public static final String DETAIL_SAVE = "DETAIL_SAVE";

    // Preference key constants
    public static final String APP_LEVEL = "APP_LEVEL";
    public static final String APP_PARENT = "APP_PARENT";

    // Min and Max quantity allowed
    public static final int MIN = 0;
    public static final int MAX = 1000;

    public static void Log( String tag, String message ) {
        Log.d( tag, message );
    }

    public static void Log( String tag, String tag2, String message ) {
        Log.d( tag, tag2 + "> " + message );
    }

    public static SparseArray<QueueResource> sortResourcesByName( SparseArray<QueueResource> resources ) {
        boolean swapped = true;
        while ( swapped ) {

            swapped = false;
            for ( int i = 0; i < resources.size() - 1; i++ ) {
                String first = resources.valueAt( i ).getName();
                String second = resources.valueAt( i + 1 ).getName();
                if ( first.compareTo( second ) > 0 ) {
                    // swap
                    QueueResource tempResource = resources.valueAt( i + 1 );
                    resources.put( i + 1, resources.valueAt( i ) );
                    resources.put( i, tempResource );
                    swapped = true;
                }
            }
        }

        return resources;
    }

    public static SparseArray<DisplayEngram> sortEngramsByName( SparseArray<DisplayEngram> engrams ) {
        boolean swapped = true;
        while ( swapped ) {

            swapped = false;
            for ( int i = 0; i < engrams.size() - 1; i++ ) {
                String first = engrams.valueAt( i ).getName();
                String second = engrams.valueAt( i + 1 ).getName();
                if ( first.compareTo( second ) > 0 ) {
                    // swap
                    DisplayEngram tempEngram = engrams.valueAt( i + 1 );
                    engrams.put( i + 1, engrams.valueAt( i ) );
                    engrams.put( i, tempEngram );
                    swapped = true;
                }
            }
        }

        return engrams;
    }

    public static SparseArray<Category> sortCategoriesByName( SparseArray<Category> categories ) {
        boolean swapped = true;
        while ( swapped ) {

            swapped = false;
            for ( int i = 0; i < categories.size() - 1; i++ ) {
                String first = categories.valueAt( i ).getName();
                String second = categories.valueAt( i + 1 ).getName();
                if ( first.compareTo( second ) > 0 ) {
                    // swap
                    Category tempCategory = categories.valueAt( i + 1 );
                    categories.put( i + 1, categories.valueAt( i ) );
                    categories.put( i, tempCategory );
                    swapped = true;
                }
            }
        }

        return categories;
    }
}
