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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;

public class DatabaseProvider extends ContentProvider {
    private static final String TAG = DatabaseProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;

    static final int ENGRAM = 100;
    static final int ENGRAM_ID = 101;
    static final int ENGRAM_WITH_CATEGORY = 110;
    static final int ENGRAM_WITH_CATEGORY_ID = 111;
    static final int ENGRAM_WITH_DRAWABLE = 112;

    static final int RESOURCE = 200;
    static final int RESOURCE_ID = 201;
    static final int RESOURCE_WITH_DRAWABLE = 202;

    static final int COMPLEX_RESOURCE = 300;
    static final int COMPLEX_RESOURCE_ID = 301;
    static final int COMPLEX_RESOURCE_WITH_ENGRAM_TABLE = 310;
    static final int COMPLEX_RESOURCE_WITH_ENGRAM_ID = 311;
    static final int COMPLEX_RESOURCE_WITH_RESOURCE_TABLE = 320;
    static final int COMPLEX_RESOURCE_WITH_RESOURCE_ID = 321;
    static final int COMPLEX_RESOURCE_WITH_DRAWABLE = 322;

    static final int CATEGORY = 400;
    static final int CATEGORY_ID = 401;
    static final int CATEGORY_WITH_PARENT = 410;
    static final int CATEGORY_WITH_PARENT_ID = 411;

    static final int COMPOSITION = 500;
    static final int COMPOSITION_ID = 501;
    static final int COMPOSITION_WITH_ENGRAM = 510;
    static final int COMPOSITION_WITH_ENGRAM_ID = 511;
    static final int COMPOSITION_WITH_RESOURCE = 520;
    static final int COMPOSITION_WITH_RESOURCE_ID = 521;

    static final int QUEUE = 600;
    static final int QUEUE_ID = 601;
    static final int QUEUE_WITH_ENGRAM_TABLE = 610;
    static final int QUEUE_WITH_ENGRAM_ID = 611;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        final String contentAuthority = DatabaseContract.CONTENT_AUTHORITY;

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY, CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/#", CATEGORY_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/" + DatabaseContract.PATH_CATEGORY_PARENT, CATEGORY_WITH_PARENT );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/" + DatabaseContract.PATH_CATEGORY_PARENT + "/#", CATEGORY_WITH_PARENT_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE, COMPLEX_RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/#", COMPLEX_RESOURCE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" + DatabaseContract.PATH_ENGRAM, COMPLEX_RESOURCE_WITH_ENGRAM_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" + DatabaseContract.PATH_ENGRAM + "/#", COMPLEX_RESOURCE_WITH_ENGRAM_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" + DatabaseContract.PATH_RESOURCE, COMPLEX_RESOURCE_WITH_RESOURCE_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" + DatabaseContract.PATH_RESOURCE + "/#", COMPLEX_RESOURCE_WITH_RESOURCE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" + DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_RESOURCE, COMPLEX_RESOURCE_WITH_DRAWABLE );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION, COMPOSITION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/#", COMPOSITION_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_ENGRAM, COMPOSITION_WITH_ENGRAM_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_ENGRAM + "/#", COMPOSITION_WITH_ENGRAM_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_RESOURCE, COMPOSITION_WITH_RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_RESOURCE + "/#", COMPOSITION_WITH_RESOURCE_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM, ENGRAM );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/#", ENGRAM_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_CATEGORY, ENGRAM_WITH_CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_CATEGORY + "/#", ENGRAM_WITH_CATEGORY_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_DRAWABLE + "/*", ENGRAM_WITH_DRAWABLE );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE, QUEUE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/#", QUEUE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" + DatabaseContract.PATH_ENGRAM, QUEUE_WITH_ENGRAM_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" + DatabaseContract.PATH_ENGRAM + "/#", QUEUE_WITH_ENGRAM_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE, RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/#", RESOURCE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/" + DatabaseContract.PATH_DRAWABLE + "/*", RESOURCE_WITH_DRAWABLE );

        return uriMatcher;
    }

    @Override
    public String getType( Uri uri ) {
        final int match = sUriMatcher.match( uri );

        switch ( match ) {
            case ENGRAM:
            case ENGRAM_WITH_CATEGORY:
                return EngramEntry.CONTENT_DIR_TYPE;

            case ENGRAM_ID:
            case ENGRAM_WITH_CATEGORY_ID:
            case ENGRAM_WITH_DRAWABLE:
                return EngramEntry.CONTENT_ITEM_TYPE;

            case RESOURCE:
                return ResourceEntry.CONTENT_DIR_TYPE;

            case RESOURCE_ID:
            case RESOURCE_WITH_DRAWABLE:
                return ResourceEntry.CONTENT_ITEM_TYPE;

            case COMPLEX_RESOURCE:
            case COMPLEX_RESOURCE_WITH_ENGRAM_TABLE:
            case COMPLEX_RESOURCE_WITH_RESOURCE_TABLE:
            case COMPLEX_RESOURCE_WITH_DRAWABLE:
                return ComplexResourceEntry.CONTENT_DIR_TYPE;

            case COMPLEX_RESOURCE_ID:
            case COMPLEX_RESOURCE_WITH_ENGRAM_ID:
            case COMPLEX_RESOURCE_WITH_RESOURCE_ID:
                return ComplexResourceEntry.CONTENT_ITEM_TYPE;

            case CATEGORY:
                return CategoryEntry.CONTENT_DIR_TYPE;

            case CATEGORY_ID:
            case CATEGORY_WITH_PARENT_ID:
                return CategoryEntry.CONTENT_ITEM_TYPE;

            case COMPOSITION:
                return CompositionEntry.CONTENT_DIR_TYPE;

            case COMPOSITION_ID:
            case COMPOSITION_WITH_ENGRAM_ID:
            case COMPOSITION_WITH_RESOURCE_ID:
                return CompositionEntry.CONTENT_ITEM_TYPE;

            case QUEUE:
            case QUEUE_WITH_ENGRAM_TABLE:
                return QueueEntry.CONTENT_DIR_TYPE;

            case QUEUE_ID:
            case QUEUE_WITH_ENGRAM_ID:
                return QueueEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {
        final int match = sUriMatcher.match( uri );

        Cursor cursor;

        long _id = 0;
        String tableName;

        switch ( match ) {
            case CATEGORY:
                tableName = CategoryEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE:
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPOSITION:
                tableName = CompositionEntry.TABLE_NAME;
                break;

            case ENGRAM:
                tableName = EngramEntry.TABLE_NAME;
                break;

            case QUEUE:
                tableName = QueueEntry.TABLE_NAME;
                break;

            case RESOURCE:
                tableName = ResourceEntry.TABLE_NAME;
                break;

            case CATEGORY_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = CategoryEntry.SQL_QUERY_WITH_ID;
                tableName = CategoryEntry.TABLE_NAME;
                break;

            case CATEGORY_WITH_PARENT_ID:
                _id = CategoryEntry.getParentIdFromUri( uri );
                selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID;
                tableName = CategoryEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ID;
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_ENGRAM_ID:
                _id = ComplexResourceEntry.getEngramIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_RESOURCE_ID:
                _id = ComplexResourceEntry.getResourceIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_RESOURCE_KEY;
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPOSITION_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_ID;
                tableName = CompositionEntry.TABLE_NAME;
                break;

            case COMPOSITION_WITH_ENGRAM_ID:
                _id = CompositionEntry.getEngramIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                tableName = CompositionEntry.TABLE_NAME;
                break;

            case COMPOSITION_WITH_RESOURCE_ID:
                _id = CompositionEntry.getResourceIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_RESOURCE_KEY;
                tableName = CompositionEntry.TABLE_NAME;
                break;

            case ENGRAM_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = EngramEntry.SQL_QUERY_WITH_ID;
                tableName = EngramEntry.TABLE_NAME;
                break;

            case ENGRAM_WITH_CATEGORY_ID:
                _id = EngramEntry.getCategoryIdFromUri( uri );
                selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY;
                tableName = EngramEntry.TABLE_NAME;
                break;

            case QUEUE_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = QueueEntry.SQL_QUERY_WITH_ID;
                tableName = QueueEntry.TABLE_NAME;
                break;

            case QUEUE_WITH_ENGRAM_ID:
                _id = QueueEntry.getEngramIdFromUri( uri );
                selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                tableName = QueueEntry.TABLE_NAME;
                break;

            case RESOURCE_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = ResourceEntry.SQL_QUERY_WITH_ID;
                tableName = ResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_ENGRAM_TABLE:
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ENGRAM_TABLE;
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_RESOURCE_TABLE:
                selection = ComplexResourceEntry.SQL_QUERY_WITH_RESOURCE_TABLE;
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_DRAWABLE:
                projection = ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_PROJECTION;
                selection = ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_SELECTION;
                tableName = ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_TABLE;
                break;

            case QUEUE_WITH_ENGRAM_TABLE:
                selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE;
                tableName = QueueEntry.TABLE_NAME;
                break;

            case ENGRAM_WITH_DRAWABLE:
                selection = EngramEntry.SQL_QUERY_WITH_DRAWABLE;
                selectionArgs = new String[]{ EngramEntry.getDrawableFromUri( uri ) };
                tableName = EngramEntry.TABLE_NAME;
                break;

            case RESOURCE_WITH_DRAWABLE:
                selection = ResourceEntry.SQL_QUERY_WITH_DRAWABLE;
                selectionArgs = new String[]{ ResourceEntry.getDrawableFromUri( uri ) };
                tableName = ResourceEntry.TABLE_NAME;
                break;

            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }

        // Override selectionArgs if switch matched a case that provides an 'id'
        if ( _id != 0 ) {
            selectionArgs = new String[]{ Long.toString( _id ) };
        }

        // Query database with provided args, null or not.
        cursor = mOpenHelper.getReadableDatabase().query(
                tableName,
                projection,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );

//        Helper.Log( TAG, selection + ", " + Arrays.toString( selectionArgs ) );

        cursor.setNotificationUri( getContext().getContentResolver(), uri );
        return cursor;
    }

    @Override
    public Uri insert( Uri uri, ContentValues values ) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match( uri );

        String tableName;

        switch ( match ) {
            case ENGRAM:
                tableName = EngramEntry.TABLE_NAME;
                break;
            case RESOURCE:
                tableName = ResourceEntry.TABLE_NAME;
                break;
            case CATEGORY:
                tableName = CategoryEntry.TABLE_NAME;
                break;
            case COMPOSITION:
                tableName = CompositionEntry.TABLE_NAME;
                break;
            case COMPLEX_RESOURCE:
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;
            case QUEUE:
                tableName = QueueEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }

        long _id = db.insert( tableName, null, values );

        Uri returnUri;
        if ( _id > 0 )
            returnUri = DatabaseContract.buildUriWithId( uri, _id );
        else
            throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );

        getContext().getContentResolver().notifyChange( uri, null );
        return returnUri;
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs ) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match( uri );

        int rowsDeleted;

        if ( selection == null ) selection = "1";

        switch ( match ) {
            case ENGRAM:
                rowsDeleted = db.delete( EngramEntry.TABLE_NAME, selection, selectionArgs );
                break;
            case RESOURCE:
                rowsDeleted = db.delete( ResourceEntry.TABLE_NAME, selection, selectionArgs );
                break;
            case CATEGORY:
                rowsDeleted = db.delete( CategoryEntry.TABLE_NAME, selection, selectionArgs );
                break;
            case COMPOSITION:
                rowsDeleted = db.delete( CompositionEntry.TABLE_NAME, selection, selectionArgs );
                break;
            case COMPLEX_RESOURCE:
                rowsDeleted = db.delete( ComplexResourceEntry.TABLE_NAME, selection, selectionArgs );
                break;
            case QUEUE:
                rowsDeleted = db.delete( QueueEntry.TABLE_NAME, selection, selectionArgs );
                break;
            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }

        if ( rowsDeleted != 0 ) {
            getContext().getContentResolver().notifyChange( uri, null );
        }

        return rowsDeleted;
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs ) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match( uri );

        int rowsUpdated;

        if ( selection == null ) selection = "1";

        switch ( match ) {
            case ENGRAM:
                rowsUpdated = db.update( EngramEntry.TABLE_NAME, values, selection, selectionArgs );
                break;
            case RESOURCE:
                rowsUpdated = db.update( ResourceEntry.TABLE_NAME, values, selection, selectionArgs );
                break;
            case CATEGORY:
                rowsUpdated = db.update( CategoryEntry.TABLE_NAME, values, selection, selectionArgs );
                break;
            case COMPOSITION:
                rowsUpdated = db.update( CompositionEntry.TABLE_NAME, values, selection, selectionArgs );
                break;
            case COMPLEX_RESOURCE:
                rowsUpdated = db.update( ComplexResourceEntry.TABLE_NAME, values, selection, selectionArgs );
                break;
            case QUEUE:
                rowsUpdated = db.update( QueueEntry.TABLE_NAME, values, selection, selectionArgs );
                break;
            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }

        if ( rowsUpdated != 0 ) {
            getContext().getContentResolver().notifyChange( uri, null );
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert( Uri uri, ContentValues[] values ) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match( uri );

        String tableName;
        switch ( match ) {
            case ENGRAM:
                tableName = EngramEntry.TABLE_NAME;
                break;
            case RESOURCE:
                tableName = ResourceEntry.TABLE_NAME;
                break;
            case CATEGORY:
                tableName = CategoryEntry.TABLE_NAME;
                break;
            case COMPLEX_RESOURCE:
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;
            default:
                return super.bulkInsert( uri, values );
        }

        int rowsInserted = 0;
        db.beginTransaction();
        try {
            for ( ContentValues value : values ) {
                long _id = db.insert( tableName, null, value );

                if ( _id > 0 )
                    rowsInserted++;
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange( uri, null );
        return rowsInserted;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper( getContext() );

        return true;
    }
}
