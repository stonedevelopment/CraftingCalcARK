package arc.resource.calculator.helpers;

import android.util.Log;
import android.util.SparseArray;

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
    private static boolean isDebug = true;

    // Temp ID used to only show prime ark until ready for others
    public static final long DLC_ID = 1;

    public static final int DETAIL_ID_CODE = 1000;

    public static final String DETAIL_ID = "DETAIL_ID";
    public static final String DETAIL_QUANTITY = "DETAIL_QUANTITY";
    public static final String DETAIL_REMOVE = "DETAIL_REMOVE";
    public static final String DETAIL_RESULT_CODE = "DETAIL_RESULT_CODE";
    public static final String DETAIL_SAVE = "DETAIL_SAVE";

    // Preference key constants
    public static final String APP_LEVEL = "APP_LEVEL";
    public static final String APP_PARENT = "APP_PARENT";

    public static final String FILTERED = "FILTERED";
    public static final String CHANGELOG = "CHANGELOG";

    // Min and Max quantity allowed
    public static final int MIN = 0;
    public static final int MAX = 1000;

    public static final int DLC_VANILLA_ID = 1;
    public static final int DLC_PRIMITIVE_PLUS_ID = 2;
    public static final int DLC_SCORCHED_EARTH_ID = 3;

    public static void Log(String tag, String message) {
        if (isDebug) Log.d(tag, message);
    }

    public static void Log(String tag, String tag2, String message) {
        if (isDebug) Log.d(tag, tag2 + "> " + message);
    }

    public static SparseArray<QueueResource> CombineSparseArrays( SparseArray<QueueResource> array1, SparseArray<QueueResource> array2 ) {
        for (int i = 0; i < array2.size(); i++) {
            array1.put(array2.keyAt(i), array2.valueAt(i));
        }
        return array1;
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
}
