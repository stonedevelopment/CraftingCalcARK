package arc.resource.calculator.util;

import android.util.Log;
import android.util.LongSparseArray;

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
public class Util {

    public static final int NO_POSITION = -1;
    public static final int NO_ID = -1;
    public static final int NO_SIZE = -1;
    public static final int NO_QUANTITY = 0;
    public static final String NO_PATH = null;
    public static final String NO_NAME = "ERROR";

    public static final int MIN = 0;

    public static void Log( String tag, String message ) {
        Log.d( tag, message );
    }

    public static void Log( String tag, String tag2, String message ) {
        Log.d( tag, tag2 + "> " + message );
    }

    public static boolean isValidPosition( int position, int size ) {
        return position > NO_POSITION && position < size;
    }

    public static LongSparseArray<QueueResource> sortResourcesByName( LongSparseArray<QueueResource> resources ) {
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
