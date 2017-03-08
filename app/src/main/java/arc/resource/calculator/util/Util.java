package arc.resource.calculator.util;

import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

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
public class Util {
    public static final int REQUEST_CODE_DETAIL_ACTIVITY = 1000;
    public static final int REQUEST_CODE_SETTINGS_ACTIVITY = 2000;

    public static final String DETAIL_RESULT_CODE = "detail";
    public static final String DETAIL_ID = "DETAIL_ID";
    public static final String DETAIL_QUANTITY = "DETAIL_QUANTITY";
    public static final String DETAIL_REMOVE = "DETAIL_REMOVE";
    public static final String DETAIL_SAVE = "DETAIL_SAVE";

    public static final int MIN = 0;

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

    public static List<Long> sortEngramsByName( LongSparseArray<DisplayEngram> engrams ) {
        List<Long> keyList = new ArrayList<>( engrams.size() );

//        Long[] keySet = new Long[map.size()];
//        map.keySet().toArray( keySet );
//
//        boolean swapped = true;
//        while ( swapped ) {
//            swapped = false;
//            for ( int i = 0; i < map.size() - 1; i++ ) {
//                DisplayEngram first = map.get( keySet[i] );
//                DisplayEngram second = map.get( keySet[i + 1] );
//                if ( first.getName().compareTo( second.getName() ) > 0 ) {
//                    // swap
//                    keyList.set( i + 1, first.getId() );
//                    keyList.set( i, second.getId() );
//                    swapped = true;
//                }
//            }
//        }
//
//        return keyList;

//        LongSparseArray<DisplayEngram> returnEngrams = new LongSparseArray<>();
//        for ( DisplayEngram engram : engramList ) {
//            returnEngrams.put( engram.getId(), engram );
//        }

//        for ( int i = 0; i < engrams.size(); i++ ) {
//            engramList.add( engrams.valueAt( i ) );
//        }
//
//        boolean swapped = true;
//        while ( swapped ) {
//            swapped = false;
//            for ( int i = 0; i < engrams.size() - 1; i++ ) {
//                DisplayEngram first = engramList.get( i );
//                DisplayEngram second = engramList.get( i + 1 );
//                if ( first.getName().compareTo( second.getName() ) > 0 ) {
//                    // swap
//                    engramList.set( i + 1, first );
//                    engramList.set( i, second );
//                    swapped = true;
//                }
//            }
//        }
//
//        LongSparseArray<DisplayEngram> returnEngrams = new LongSparseArray<>();
//        for ( DisplayEngram engram : engramList ) {
//            returnEngrams.put( engram.getId(), engram );
//        }


//        boolean swapped = true;
//        while ( swapped ) {
//            swapped = false;
//            for ( int i = 0; i < engrams.size() - 1; i++ ) {
//                DisplayEngram first = engrams.valueAt( i );
//                DisplayEngram second = engrams.valueAt( i + 1 );
//                if ( first.getName().compareTo( second.getName() ) > 0 ) {
//                    // swap
//                    engrams.setValueAt( i + 1, first );
//                    engrams.setValueAt( i, second );
//                    swapped = true;
//                }
//            }
//        }
        boolean swapped = true;
        while ( swapped ) {
            swapped = false;
            for ( int i = 0; i < engrams.size() - 1; i++ ) {
                DisplayEngram first = engrams.valueAt( i );
                DisplayEngram second = engrams.valueAt( i + 1 );
                if ( first.getName().compareTo( second.getName() ) > 0 ) {
                    // swap
                    keyList.set( i + 1, first.getId() );
                    keyList.set( i, second.getId() );
                    swapped = true;
                }
            }
        }

        return keyList;
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

    private String addSpaces( int n ) {
        return new String( new char[n * 2] ).replace( "\0", " " );
    }
}