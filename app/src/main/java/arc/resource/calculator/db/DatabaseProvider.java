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
import android.support.annotation.NonNull;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.DLCEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;

public class DatabaseProvider extends ContentProvider {
    private static final String TAG = DatabaseProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;

    static final int ENGRAM = 100;
    static final int ENGRAM_ID = 101;
    static final int ENGRAM_WITH_CATEGORY = 102;
    static final int ENGRAM_WITH_DRAWABLE = 103;
    static final int ENGRAM_WITH_DLC = 104;

    static final int RESOURCE = 200;
    static final int RESOURCE_ID = 201;
    static final int RESOURCE_WITH_DRAWABLE = 202;

    static final int COMPLEX_RESOURCE = 300;
    static final int COMPLEX_RESOURCE_ID = 301;
    static final int COMPLEX_RESOURCE_WITH_DRAWABLES = 322;

    static final int CATEGORY = 400;
    static final int CATEGORY_ID = 401;
    static final int CATEGORY_WITH_PARENT = 403;

    static final int COMPOSITION = 500;
    static final int COMPOSITION_ID = 501;
    static final int COMPOSITION_WITH_ENGRAM = 511;

    static final int QUEUE = 600;
    static final int QUEUE_ID = 601;
    static final int QUEUE_WITH_ENGRAM_TABLE = 610;
    static final int QUEUE_WITH_ENGRAM_ID = 611;

    static final int DLC = 700;
    static final int DLC_ID = 701;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        final String contentAuthority = DatabaseContract.CONTENT_AUTHORITY;

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY, CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/#", CATEGORY_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/#/" + DatabaseContract.PATH_DLC + "/#", CATEGORY_WITH_PARENT );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE, COMPLEX_RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/#", COMPLEX_RESOURCE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" + DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_RESOURCE, COMPLEX_RESOURCE_WITH_DRAWABLES );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION, COMPOSITION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/#", COMPOSITION_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_ENGRAM + "/#", COMPOSITION_WITH_ENGRAM );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM, ENGRAM );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/#", ENGRAM_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_DLC + "/#", ENGRAM_WITH_DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_CATEGORY + "/#/" + DatabaseContract.PATH_DLC + "/#", ENGRAM_WITH_CATEGORY );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE, QUEUE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/#", QUEUE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" + DatabaseContract.PATH_ENGRAM, QUEUE_WITH_ENGRAM_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" + DatabaseContract.PATH_ENGRAM + "/#", QUEUE_WITH_ENGRAM_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE, RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/#", RESOURCE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/" + DatabaseContract.PATH_DRAWABLE + "/*", RESOURCE_WITH_DRAWABLE );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_DLC, DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_DLC + "/#", DLC_ID );

        return uriMatcher;
    }

    @Override
    public String getType( Uri uri ) {
        final int match = sUriMatcher.match( uri );

        switch ( match ) {
            case ENGRAM:
            case ENGRAM_WITH_CATEGORY:
            case ENGRAM_WITH_DLC:
                return EngramEntry.CONTENT_DIR_TYPE;

            case RESOURCE:
                return ResourceEntry.CONTENT_DIR_TYPE;

            case COMPLEX_RESOURCE:
            case COMPLEX_RESOURCE_WITH_DRAWABLES:
                return ComplexResourceEntry.CONTENT_DIR_TYPE;

            case CATEGORY:
            case CATEGORY_WITH_PARENT:
                return CategoryEntry.CONTENT_DIR_TYPE;

            case COMPOSITION:
                return CompositionEntry.CONTENT_DIR_TYPE;

            case QUEUE:
            case QUEUE_WITH_ENGRAM_TABLE:
                return QueueEntry.CONTENT_DIR_TYPE;

            case DLC:
                return DLCEntry.CONTENT_DIR_TYPE;

            case ENGRAM_ID:
                return EngramEntry.CONTENT_ITEM_TYPE;

            case RESOURCE_ID:
            case RESOURCE_WITH_DRAWABLE:
                return ResourceEntry.CONTENT_ITEM_TYPE;

            case COMPLEX_RESOURCE_ID:
                return ComplexResourceEntry.CONTENT_ITEM_TYPE;

            case CATEGORY_ID:
                return CategoryEntry.CONTENT_ITEM_TYPE;

            case COMPOSITION_ID:
            case COMPOSITION_WITH_ENGRAM:
                return CompositionEntry.CONTENT_ITEM_TYPE;

            case QUEUE_ID:
            case QUEUE_WITH_ENGRAM_ID:
                return QueueEntry.CONTENT_ITEM_TYPE;

            case DLC_ID:
                return DLCEntry.CONTENT_ITEM_TYPE;

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
                sortOrder = ResourceEntry.SQL_SORT_ORDER_BY_NAME;
                tableName = ResourceEntry.TABLE_NAME;
                break;

            case DLC:
                tableName = DLCEntry.TABLE_NAME;
                break;

            case DLC_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = DLCEntry.SQL_QUERY_WITH_ID;
                tableName = DLCEntry.TABLE_NAME;
                break;

            case CATEGORY_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = CategoryEntry.SQL_QUERY_WITH_ID;
                tableName = CategoryEntry.TABLE_NAME;
                break;

            case CATEGORY_WITH_PARENT:
                selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID + " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_ID;
                selectionArgs = new String[]{
                        Long.toString( CategoryEntry.getParentIdFromUri( uri ) ),
                        Long.toString( CategoryEntry.getDLCIdFromUri( uri ) )
                };
                sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                tableName = CategoryEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ID;
                tableName = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPOSITION_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_ID;
                tableName = CompositionEntry.TABLE_NAME;
                break;

            case COMPOSITION_WITH_ENGRAM:
                _id = CompositionEntry.getEngramIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                tableName = CompositionEntry.TABLE_NAME;
                break;

            case ENGRAM_ID:
                _id = DatabaseContract.getIdFromUri( uri );
                selection = EngramEntry.SQL_QUERY_WITH_ID;
                tableName = EngramEntry.TABLE_NAME;
                break;

            case ENGRAM_WITH_CATEGORY:
                selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY + " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                selectionArgs = new String[]{
                        Long.toString( EngramEntry.getCategoryIdFromUri( uri ) ),
                        Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                };
                sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                tableName = EngramEntry.TABLE_NAME;
                break;

            case ENGRAM_WITH_DLC:
                selection = EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                selectionArgs = new String[]{
                        Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                };
                sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
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

            case COMPLEX_RESOURCE_WITH_DRAWABLES:
                projection = ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_PROJECTION;
                selection = ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_SELECTION;
                tableName = ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_TABLE;
                break;

            case QUEUE_WITH_ENGRAM_TABLE:
                projection = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE_PROJECTION;
                selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE_SELECTION;
                sortOrder = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE_SORT_ORDER_BY_NAME;
                tableName = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE;
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
            case DLC:
                tableName = DLCEntry.TABLE_NAME;
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
            case DLC:
                rowsDeleted = db.delete( DLCEntry.TABLE_NAME, selection, selectionArgs );
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
    public int update( @NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs ) {
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
            case DLC:
                rowsUpdated = db.update( DLCEntry.TABLE_NAME, values, selection, selectionArgs );
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
            case DLC:
                tableName = DLCEntry.TABLE_NAME;
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
