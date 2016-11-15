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
package arc.resource.calculator.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import arc.resource.calculator.BuildConfig;

/**
 * Defines table and column names for the database.
 */
public class DatabaseContract {

    // Unique address key to be used within the base content uri
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    // Base URI which apps will use to contact content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse( "content://" + CONTENT_AUTHORITY );

    // Established paths that restricts outside queries
    public static final String PATH_ENGRAM = "engram";
    public static final String PATH_CATEGORY = "category";
    public static final String PATH_CATEGORY_PARENT = "parent";
    public static final String PATH_COMPLEX_RESOURCE = "complex_resource";
    public static final String PATH_COMPOSITION = "composition";
    public static final String PATH_DLC = "dlc";
    public static final String PATH_DRAWABLE = "drawable";
    public static final String PATH_LEVEL = "level";
    public static final String PATH_QUEUE = "queue";
    public static final String PATH_RESOURCE = "resource";
    public static final String PATH_STATION = "station";

    // Inner class that defines the table contents of the engram table
    public static final class EngramEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ENGRAM;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ENGRAM;

        // Table name, duh.
        public static final String TABLE_NAME = "engram";

        // Engram name and its description verbatim from game
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";

        // String value that Android Studio uses as its Drawable resource
        public static final String COLUMN_DRAWABLE = "drawable";

        // Integer value that describes the yield per engram crafted
        public static final String COLUMN_YIELD = "yield";

        // Integer value indicating required level to craft
        public static final String COLUMN_LEVEL = "level";

        // Foreign key from Crafting Station table
        public static final String COLUMN_STATION_KEY = "station_id";

        // Foreign key from Category table
        public static final String COLUMN_CATEGORY_KEY = "category_id";

        // Foreign key from Game DLC table
        public static final String COLUMN_DLC_KEY = "dlc_id";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;
        public static final String SQL_COLUMN_DRAWABLE = TABLE_NAME + "." + COLUMN_DRAWABLE;
        public static final String SQL_COLUMN_YIELD = TABLE_NAME + "." + COLUMN_YIELD;
        public static final String SQL_COLUMN_LEVEL = TABLE_NAME + "." + COLUMN_LEVEL;
        public static final String SQL_COLUMN_CATEGORY_KEY = TABLE_NAME + "." + COLUMN_CATEGORY_KEY;
        public static final String SQL_COLUMN_DLC_KEY = TABLE_NAME + "." + COLUMN_DLC_KEY;
        public static final String SQL_COLUMN_STATION_KEY = TABLE_NAME + "." + COLUMN_STATION_KEY;

        // SQL query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_DRAWABLE = SQL_COLUMN_DRAWABLE + " = ?";
        public static final String SQL_QUERY_WITH_LEVEL = SQL_COLUMN_LEVEL + " <= ?";
        public static final String SQL_QUERY_WITH_CATEGORY_KEY = SQL_COLUMN_CATEGORY_KEY + " = ?";
        public static final String SQL_QUERY_WITH_DLC_KEY = SQL_COLUMN_DLC_KEY + " = ?";
        public static final String SQL_QUERY_WITH_STATION_KEY = SQL_COLUMN_STATION_KEY + " = ?";

        // SQL sort helpers
        public static final String SQL_SORT_ORDER_BY_NAME = SQL_COLUMN_NAME + " ASC";

        // Returns /engram/dlc/#
        public static Uri buildUriWithDLCId( long dlc_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_DLC )
                    .appendPath( Long.toString( dlc_id ) ).build();
        }

        // Returns /engram/level/#/dlc/#
        public static Uri buildUriWithLevel( long dlc_id, int level ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_LEVEL ).appendPath( Integer.toString( level ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) ).build();
        }

        // Returns /engram/category/#/dlc/#, used when filtering by category, but not by station
        public static Uri buildUriWithCategoryId( long dlc_id, long category_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_CATEGORY ).appendPath( Long.toString( category_id ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        // Returns /engram/category/#/station/#/dlc/#, used when filtering by category and station
        public static Uri buildUriWithCategoryIdAndStationId( long dlc_id, long category_id, long station_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_CATEGORY ).appendPath( Long.toString( category_id ) )
                    .appendPath( PATH_STATION ).appendPath( Long.toString( station_id ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        // Returns /engram/station/#/dlc/#, used when filtering by station only
        public static Uri buildUriWithStationId( long dlc_id, long station_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_STATION ).appendPath( Long.toString( station_id ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        // Returns /engram/drawable/*/dlc/#, used during initialization to check composition
        public static Uri buildUriWithDrawable( String drawable, long dlc_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_DRAWABLE ).appendPath( drawable )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        public static long getCategoryIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }

        public static String getDrawableFromUri( Uri uri ) {
            return uri.getPathSegments().get( 2 );
        }

        public static long getLevelFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }

        // Used when filtering by station only
        public static long getStationIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }

        // Used when filtering by category and station
        public static long getStationIdFromUriWithCategory( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 3 ) );
        }

        public static long getDLCIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getLastPathSegment() );
        }
    }

    // Inner class that defines the table contents of the resource table
    public static final class ResourceEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_RESOURCE ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESOURCE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESOURCE;

        // Table name, duh.
        public static final String TABLE_NAME = "resource";

        // Resource name verbatim from game
        public static final String COLUMN_NAME = "name";

        // String value that Android Studio uses as its Drawable resource
        public static final String COLUMN_DRAWABLE = "drawable";

        // Foreign key from Game DLC table
        public static final String COLUMN_DLC_KEY = "dlc_id";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;
        public static final String SQL_COLUMN_DRAWABLE = TABLE_NAME + "." + COLUMN_DRAWABLE;
        public static final String SQL_COLUMN_DLC_KEY = TABLE_NAME + "." + COLUMN_DLC_KEY;

        // SQL query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_DRAWABLE = SQL_COLUMN_DRAWABLE + " = ?";
        public static final String SQL_QUERY_WITH_DLC_KEY = SQL_COLUMN_DLC_KEY + " = ?";

        // SQL sort helpers
        public static final String SQL_SORT_ORDER_BY_NAME = SQL_COLUMN_NAME + " ASC";

        // Returns /resource/drawable/*/dlc/#
        public static Uri buildUriWithDrawable( String drawable, long dlc_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_DRAWABLE ).appendPath( drawable )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        public static String getDrawableFromUri( Uri uri ) {
            return uri.getPathSegments().get( 2 );
        }

        public static long getDLCIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getLastPathSegment() );
        }
    }

    // Inner class that defines the table contents of the complex resource table
    public static final class ComplexResourceEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_COMPLEX_RESOURCE ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMPLEX_RESOURCE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMPLEX_RESOURCE;

        // Table name, duh.
        public static final String TABLE_NAME = "complex_resource";

        // Foreign key from Engram table
        public static final String COLUMN_ENGRAM_KEY = "engram_id";

        // Foreign key from Resource table
        public static final String COLUMN_RESOURCE_KEY = "resource_id";

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = TABLE_NAME + "." + _ID + " = ?";
        public static final String SQL_QUERY_WITH_ENGRAM_KEY = TABLE_NAME + "." + COLUMN_ENGRAM_KEY + " = ?";
        public static final String SQL_QUERY_WITH_RESOURCE_KEY = TABLE_NAME + "." + COLUMN_RESOURCE_KEY + " = ?";

        public static final String SQL_QUERY_WITH_ENGRAM_TABLE = TABLE_NAME + " INNER JOIN " + EngramEntry.TABLE_NAME +
                " ON " + TABLE_NAME + "." + COLUMN_ENGRAM_KEY + " = " + EngramEntry.TABLE_NAME + "." + EngramEntry._ID;
        public static final String SQL_QUERY_WITH_RESOURCE_TABLE = TABLE_NAME + " INNER JOIN " + ResourceEntry.TABLE_NAME +
                " ON " + TABLE_NAME + "." + COLUMN_RESOURCE_KEY + " = " + ResourceEntry.TABLE_NAME + "." + ResourceEntry._ID;

        public static final String SQL_QUERY_WITH_DRAWABLE_SELECTION =
                EngramEntry.TABLE_NAME + "." + EngramEntry.COLUMN_DRAWABLE + " = " + ResourceEntry.TABLE_NAME + "." + ResourceEntry.COLUMN_DRAWABLE;
        public static final String SQL_QUERY_WITH_DRAWABLE_TABLE =
                EngramEntry.TABLE_NAME + " INNER JOIN " + ResourceEntry.TABLE_NAME;
        public static final String[] SQL_QUERY_WITH_DRAWABLE_PROJECTION = new String[]{
                EngramEntry.TABLE_NAME + "." + EngramEntry._ID + " AS " + COLUMN_ENGRAM_KEY,
                ResourceEntry.TABLE_NAME + "." + ResourceEntry._ID + " AS " + COLUMN_RESOURCE_KEY
        };

        // Returns /complex_resource/engram/resource/
        public static Uri buildUriWithDrawable() {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_ENGRAM )
                    .appendPath( PATH_RESOURCE ).build();
        }
    }

    // Inner class that defines the table contents of the category table
    public static final class CategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_CATEGORY ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;

        // Table name, duh.
        public static final String TABLE_NAME = "category";

        // Category name verbatim from game
        public static final String COLUMN_NAME = "name";

        // Parent level, tied to category_id of another Category
        public static final String COLUMN_PARENT_KEY = "parent_id";

        // Foreign key from Crafting Station table
        public static final String COLUMN_STATION_KEY = "station_id";

        // Foreign key from Game DLC table
        public static final String COLUMN_DLC_KEY = "dlc_id";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;
        public static final String SQL_COLUMN_PARENT_KEY = TABLE_NAME + "." + COLUMN_PARENT_KEY;
        public static final String SQL_COLUMN_DLC_KEY = TABLE_NAME + "." + COLUMN_DLC_KEY;
        public static final String SQL_COLUMN_STATION_KEY = TABLE_NAME + "." + COLUMN_STATION_KEY;

        // SQL query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_PARENT_ID = SQL_COLUMN_PARENT_KEY + " = ?";
        public static final String SQL_QUERY_WITH_DLC_KEY = SQL_COLUMN_DLC_KEY + " = ?";
        public static final String SQL_QUERY_WITH_DLC_KEYS = SQL_COLUMN_DLC_KEY + " IN (?, ?)";
        public static final String SQL_QUERY_WITH_STATION_KEY = SQL_COLUMN_STATION_KEY + " = ?";

        // SQL sort helpers
        public static final String SQL_SORT_ORDER_BY_NAME = SQL_COLUMN_NAME + " ASC";

        // Returns /category/#/dlc/#, used when not filtering by Crafting Station
        public static Uri buildUriWithParentId( long dlc_id, long parent_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( Long.toString( parent_id ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        // Returns /category/#/station/#/dlc/#, used when filtering by Crafting Station
        public static Uri buildUriWithStationId( long dlc_id, long parent_id, long station_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( Long.toString( parent_id ) )
                    .appendPath( PATH_STATION ).appendPath( Long.toString( station_id ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) ).build();
        }

        public static long getParentIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 1 ) );
        }

        public static long getStationIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 3 ) );
        }

        public static long getDLCIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getLastPathSegment() );
        }
    }

    // Inner class that defines the table contents of the station table
    public static final class StationEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_STATION ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION;

        // Table name, duh.
        public static final String TABLE_NAME = "station";

        // Crafting station name verbatim from game
        public static final String COLUMN_NAME = "name";

        // String value that Android Studio uses as its Drawable resource
        public static final String COLUMN_DRAWABLE = "drawable";

        // Foreign key from Game DLC table
        public static final String COLUMN_DLC_KEY = "dlc_id";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;
        public static final String SQL_COLUMN_DRAWABLE = TABLE_NAME + "." + COLUMN_DRAWABLE;
        public static final String SQL_COLUMN_DLC_KEY = TABLE_NAME + "." + COLUMN_DLC_KEY;

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_DRAWABLE = SQL_COLUMN_DRAWABLE + " = ?";
        public static final String SQL_QUERY_WITH_DLC_KEY = SQL_COLUMN_DLC_KEY + " = ?";
        public static final String SQL_QUERY_WITH_DLC_KEYS = SQL_COLUMN_DLC_KEY + " IN (?, ?)";

        // Returns /station/#/dlc/#, used in initialization when comparing stations to dlcs
        public static Uri buildUriWithId( long _id, long dlc_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( Long.toString( _id ) )
                    .appendPath( PATH_DLC ).appendPath( Long.toString( dlc_id ) )
                    .build();
        }

        // Returns /station/dlc/#
        public static Uri buildUriWithDLCId( long dlc_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_DLC )
                    .appendPath( Long.toString( dlc_id ) ).build();
        }

        public static long getDLCIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getLastPathSegment() );
        }

        // Used in initialization when comparing stations to dlcs
        public static long getIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 1 ) );
        }
    }

    // Inner class that defines the table contents of the dlc table.
    public static final class DLCEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_DLC ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DLC;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DLC;

        // Table name, duh.
        public static final String TABLE_NAME = "dlc";

        // Category name verbatim from game
        public static final String COLUMN_NAME = "name";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_SORT_ORDER_BY_NAME = SQL_COLUMN_NAME + " ASC";
    }

    // Inner class that defines the table contents of the composition table
    public static final class CompositionEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_COMPOSITION ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMPOSITION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMPOSITION;

        // Table name, duh.
        public static final String TABLE_NAME = "composition";

        // Quantity of resource_id used in composing engram_id
        public static final String COLUMN_QUANTITY = "quantity";

        // Foreign key from Engram table
        public static final String COLUMN_ENGRAM_KEY = "engram_id";

        // Foreign key from Resource table
        public static final String COLUMN_RESOURCE_KEY = "resource_id";

        // Initialization helpers
        public static final String COLUMN_DRAWABLE = "drawable";
        public static final String COLUMN_ENGRAM_DRAWABLE = "engram_drawable";
        public static final String COLUMN_RESOURCE_DRAWABLE = "resource_drawable";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_ENGRAM_KEY = TABLE_NAME + "." + COLUMN_ENGRAM_KEY;
        public static final String SQL_COLUMN_RESOURCE_KEY = TABLE_NAME + "." + COLUMN_RESOURCE_KEY;
        public static final String SQL_COLUMN_QUANTITY = TABLE_NAME + "." + COLUMN_QUANTITY;

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_ENGRAM_KEY = SQL_COLUMN_ENGRAM_KEY + " = ?";
        public static final String SQL_QUERY_WITH_RESOURCE_KEY = SQL_COLUMN_RESOURCE_KEY + " = ?";

        // Returns /composition/engram/#
        public static Uri buildUriWithEngramId( long engram_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_ENGRAM )
                    .appendPath( Long.toString( engram_id ) ).build();
        }

        public static long getEngramIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getLastPathSegment() );
        }
    }

    // Inner class that defines the table contents of the complex resource table
    public static final class QueueEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath( PATH_QUEUE ).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUEUE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUEUE;

        // Table name, duh.
        public static final String TABLE_NAME = "queue";

        // Foreign key from Resource table
        public static final String COLUMN_QUANTITY = "quantity";

        // Foreign key from Engram table
        public static final String COLUMN_ENGRAM_KEY = "engram_id";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_QUANTITY = TABLE_NAME + "." + COLUMN_QUANTITY;
        public static final String SQL_COLUMN_ENGRAM_KEY = TABLE_NAME + "." + COLUMN_ENGRAM_KEY;

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_ENGRAM_KEY = SQL_COLUMN_ENGRAM_KEY + " = ?";

        public static final String SQL_QUERY_WITH_ENGRAM_TABLE = TABLE_NAME + " INNER JOIN " + EngramEntry.TABLE_NAME;
        public static final String SQL_QUERY_WITH_ENGRAM_TABLE_SELECTION =
                SQL_COLUMN_ENGRAM_KEY + " = " + EngramEntry.SQL_COLUMN_ID;
        public static final String SQL_QUERY_WITH_ENGRAM_TABLE_SORT_ORDER_BY_NAME = EngramEntry.SQL_COLUMN_NAME + " ASC";

        public static final String[] SQL_PROJECTION = new String[]{
                EngramEntry.SQL_COLUMN_ID,
                EngramEntry.SQL_COLUMN_NAME,
                EngramEntry.SQL_COLUMN_DRAWABLE,
                EngramEntry.SQL_COLUMN_YIELD,
                SQL_COLUMN_QUANTITY
        };

        // Returns /queue/engram/
        public static Uri buildUriWithEngramTable() {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM ).build();
        }

        // Returns /queue/engram/#
        public static Uri buildUriWithEngramId( long engram_id ) {
            return CONTENT_URI.buildUpon()
                    .appendPath( PATH_ENGRAM ).appendPath( Long.toString( engram_id ) ).build();
        }

        public static long getEngramIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getLastPathSegment() );
        }
    }

    public static Uri buildUriWithId( Uri uri, long _id ) {
        return ContentUris.withAppendedId( uri, _id );
    }

    public static long getIdFromUri( Uri uri ) {
        return ContentUris.parseId( uri );
    }
}
