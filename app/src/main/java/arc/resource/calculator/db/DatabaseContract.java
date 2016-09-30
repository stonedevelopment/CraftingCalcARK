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

/**
 * Defines table and column names for the database.
 */
public class DatabaseContract {

    // Unique address key to be used within the base content uri
    public static final String CONTENT_AUTHORITY = "com.gmail.stonedevs.ark.resource.calculator.provider";

    // Base URI which apps will use to contact content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse( "content://" + CONTENT_AUTHORITY );

    // Established paths that restricts outside queries
    public static final String PATH_ENGRAM = "engram";
    public static final String PATH_COMPOSITION = "composition";
    public static final String PATH_RESOURCE = "resource";
    public static final String PATH_COMPLEX_RESOURCE = "complex_resource";
    public static final String PATH_CATEGORY = "category";
    public static final String PATH_CATEGORY_PARENT = "parent";
    public static final String PATH_QUEUE = "queue";
    public static final String PATH_DRAWABLE = "drawable";

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

        // Foreign key from Category table
        public static final String COLUMN_CATEGORY_KEY = "category_id";

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;
        public static final String SQL_COLUMN_DESCRIPTION = TABLE_NAME + "." + COLUMN_DESCRIPTION;
        public static final String SQL_COLUMN_DRAWABLE = TABLE_NAME + "." + COLUMN_DRAWABLE;
        public static final String SQL_COLUMN_CATEGORY_KEY = TABLE_NAME + "." + COLUMN_CATEGORY_KEY;

        public static final String SQL_QUERY_WITH_QUEUE_TABLE = TABLE_NAME + " INNER JOIN " + QueueEntry.TABLE_NAME;
        public static final String SQL_QUERY_WITH_QUEUE_TABLE_SELECTION =
                SQL_COLUMN_ID + " = " + QueueEntry.SQL_COLUMN_ENGRAM_KEY;
        public static final String[] SQL_QUERY_WITH_QUEUE_TABLE_PROJECTION = new String[]{
                SQL_COLUMN_ID,
                SQL_COLUMN_NAME,
                SQL_COLUMN_DRAWABLE,
                QueueEntry.SQL_COLUMN_ID,
                QueueEntry.SQL_COLUMN_QUANTITY
        };

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_CATEGORY_KEY = SQL_COLUMN_CATEGORY_KEY + " = ?";
        public static final String SQL_QUERY_WITH_DRAWABLE = SQL_COLUMN_DRAWABLE + " = ?";

        // Returns /engram/_id
        public static Uri buildUriWithId( long id ) {
            return ContentUris.withAppendedId( CONTENT_URI, id );
        }

        // Returns /engram/category/category_id
        public static Uri buildUriWithCategoryId( long category_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_CATEGORY )
                    .appendPath( Long.toString( category_id ) ).build();
        }

        // Returns /engram/<drawable>
        public static Uri buildUriWithDrawable( String drawable ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_DRAWABLE )
                    .appendPath( drawable ).build();
        }

        public static long getCategoryIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }

        public static String getDrawableFromUri( Uri uri ) {
            return uri.getPathSegments().get( 2 );
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

        // SQL column helpers
        public static final String SQL_COLUMN_ID = TABLE_NAME + "." + _ID;
        public static final String SQL_COLUMN_NAME = TABLE_NAME + "." + COLUMN_NAME;
        public static final String SQL_COLUMN_DRAWABLE = TABLE_NAME + "." + COLUMN_DRAWABLE;

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = SQL_COLUMN_ID + " = ?";
        public static final String SQL_QUERY_WITH_DRAWABLE = SQL_COLUMN_DRAWABLE + " = ?";

        // Returns /resource/_id
        public static Uri buildUriWithId( long id ) {
            return ContentUris.withAppendedId( CONTENT_URI, id );
        }

        // Returns /resource/drawable
        public static Uri buildUriWithDrawable( String drawable ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_DRAWABLE )
                    .appendPath( drawable ).build();
        }

        public static String getDrawableFromUri( Uri uri ) {
            return uri.getPathSegments().get( 2 );
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

        // Returns /complex_resource/engram/engram_id
        public static Uri buildUriWithEngramId( long engram_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM )
                    .appendPath( Long.toString( engram_id ) ).build();
        }

        // Returns /complex_resource/engram/
        public static Uri buildUriWithEngramTable() {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM ).build();
        }

        // Returns /complex_resource/resource/resource_id
        public static Uri buildUriWithResourceId( long resource_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_RESOURCE )
                    .appendPath( Long.toString( resource_id ) ).build();
        }

        // Returns /complex_resource/resource/
        public static Uri buildUriWithResourceTable() {
            return CONTENT_URI.buildUpon().appendPath( PATH_RESOURCE ).build();
        }

        // Returns /complex_resource/resource/
        public static Uri buildUriWithDrawable() {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM )
                    .appendPath( PATH_RESOURCE ).build();
        }

        public static long getEngramIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }

        public static long getResourceIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
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

        // Query helpers
        public static final String SQL_QUERY_WITH_ID = TABLE_NAME + "." + _ID + " = ?";
        public static final String SQL_QUERY_WITH_PARENT_ID = TABLE_NAME + "." + COLUMN_PARENT_KEY + " = ?";

        // Returns /category/_id
        public static Uri buildUriWithId( long id ) {
            return ContentUris.withAppendedId( CONTENT_URI, id );
        }

        // Returns /category/_id
        public static Uri buildUriWithParentId( long id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_CATEGORY_PARENT )
                    .appendPath( Long.toString( id ) ).build();
        }

        public static long getParentIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }
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

        public static final String SQL_QUERY_WITH_RESOURCE_TABLE =
                TABLE_NAME +
                        " INNER JOIN " + EngramEntry.TABLE_NAME +
                        " INNER JOIN " + ResourceEntry.TABLE_NAME + " ON " + ResourceEntry.SQL_COLUMN_ID + " = " + SQL_COLUMN_RESOURCE_KEY;
        public static final String SQL_QUERY_WITH_RESOURCE_TABLE_SELECTION =
                SQL_COLUMN_ENGRAM_KEY + " = " + EngramEntry.SQL_COLUMN_ID;
        public static final String[] SQL_QUERY_WITH_RESOURCE_TABLE_PROJECTION = new String[]{
                ResourceEntry.SQL_COLUMN_ID,
                ResourceEntry.SQL_COLUMN_NAME,
                ResourceEntry.SQL_COLUMN_DRAWABLE,
                SQL_COLUMN_QUANTITY
        };

        // Returns /composition/engram/engram_id
        public static Uri buildUriWithEngramId( long engram_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM )
                    .appendPath( Long.toString( engram_id ) ).build();
        }

        // Returns /composition/resource/resource_id
        public static Uri buildUriWithResourceId( long resource_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_RESOURCE )
                    .appendPath( Long.toString( resource_id ) ).build();
        }

        public static long getEngramIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }

        public static long getResourceIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
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
        public static final String[] SQL_QUERY_WITH_ENGRAM_TABLE_PROJECTION = new String[]{
                EngramEntry.SQL_COLUMN_ID,
                EngramEntry.SQL_COLUMN_NAME,
                EngramEntry.SQL_COLUMN_DRAWABLE,
                SQL_COLUMN_QUANTITY
        };

        // Returns /queue/_id
        public static Uri buildUriWithId( long id ) {
            return ContentUris.withAppendedId( CONTENT_URI, id );
        }

        // Returns /queue/engram/engram_id
        public static Uri buildUriWithEngramId( long engram_id ) {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM )
                    .appendPath( Long.toString( engram_id ) ).build();
        }

        // Returns /queue/engram/
        public static Uri buildUriWithEngramTable() {
            return CONTENT_URI.buildUpon().appendPath( PATH_ENGRAM ).build();
        }

        public static long getEngramIdFromUri( Uri uri ) {
            return Long.parseLong( uri.getPathSegments().get( 2 ) );
        }
    }

    public static Uri buildUriWithId( Uri uri, long id ) {
        return ContentUris.withAppendedId( uri, id );
    }

    public static long getIdFromUri( Uri uri ) {
        return ContentUris.parseId( uri );
    }
}
