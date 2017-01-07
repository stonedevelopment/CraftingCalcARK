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
import android.database.sqlite.SQLiteException;
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
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.model.Queue;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.ExceptionUtil.URIUnknownException;

public class DatabaseProvider extends ContentProvider {
    private static final String TAG = DatabaseProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;

    static final int ENGRAM = 100;
    static final int ENGRAM_ID_WITH_DLC = 101;
    static final int ENGRAM_WITH_CATEGORY = 102;
    static final int ENGRAM_WITH_CATEGORY_AND_STATION = 103;
    static final int ENGRAM_WITH_DRAWABLE = 104;
    static final int ENGRAM_WITH_DLC = 105;
    static final int ENGRAM_WITH_STATION = 106;
    static final int ENGRAM_WITH_LEVEL = 107;
    static final int ENGRAM_WITH_LEVEL_AND_CATEGORY = 108;
    static final int ENGRAM_WITH_LEVEL_AND_STATION = 109;
    static final int ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION = 110;

    static final int RESOURCE = 200;
    static final int RESOURCE_ID_WITH_DLC = 201;
    static final int RESOURCE_WITH_DRAWABLE = 202;

    static final int COMPLEX_RESOURCE = 300;
    static final int COMPLEX_RESOURCE_ID = 301;
    static final int COMPLEX_RESOURCE_WITH_RESOURCE = 302;

    static final int CATEGORY = 400;
    static final int CATEGORY_ID_WITH_DLC = 401;
    static final int CATEGORY_WITH_PARENT = 402;
    static final int CATEGORY_WITH_PARENT_AND_STATION = 403;

    static final int COMPOSITION = 500;
    static final int COMPOSITION_ID = 501;
    static final int COMPOSITION_WITH_ENGRAM = 502;

    static final int QUEUE = 600;
    static final int QUEUE_ID = 601;
    static final int QUEUE_WITH_ENGRAM_TABLE = 602;
    static final int QUEUE_WITH_ENGRAM_ID = 603;

    static final int DLC = 700;
    static final int DLC_ID = 701;

    static final int STATION = 800;
    static final int STATION_ID_WITH_DLC = 801;
    static final int STATION_WITH_DLC = 802;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper( getContext() );
        return true;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        final String contentAuthority = DatabaseContract.CONTENT_AUTHORITY;

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY,
                CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                CATEGORY_ID_WITH_DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/" +
                        DatabaseContract.PATH_CATEGORY_PARENT + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                CATEGORY_WITH_PARENT );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_CATEGORY + "/" +
                        DatabaseContract.PATH_CATEGORY_PARENT + "/#/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                CATEGORY_WITH_PARENT_AND_STATION );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE,
                COMPLEX_RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/#",
                COMPLEX_RESOURCE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" +
                        DatabaseContract.PATH_RESOURCE + "/#",
                COMPLEX_RESOURCE_WITH_RESOURCE );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION,
                COMPOSITION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/#",
                COMPOSITION_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" +
                        DatabaseContract.PATH_ENGRAM + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                COMPOSITION_WITH_ENGRAM );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM,
                ENGRAM );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_ID_WITH_DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_DRAWABLE + "/*/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_DRAWABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_CATEGORY_AND_STATION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_STATION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL_AND_CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL_AND_STATION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE,
                QUEUE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/#",
                QUEUE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" +
                        DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_DLC + "/#",
                QUEUE_WITH_ENGRAM_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" +
                        DatabaseContract.PATH_ENGRAM + "/#",
                QUEUE_WITH_ENGRAM_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE,
                RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                RESOURCE_ID_WITH_DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/" +
                        DatabaseContract.PATH_DRAWABLE + "/*/" +
                        DatabaseContract.PATH_DLC + "/#",
                RESOURCE_WITH_DRAWABLE );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_DLC,
                DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_DLC + "/#",
                DLC_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_STATION,
                STATION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                STATION_ID_WITH_DLC );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_STATION + "/" +
                        DatabaseContract.PATH_DLC + "/#",
                STATION_WITH_DLC );

        return uriMatcher;
    }

    @Override
    public String getType( @NonNull Uri uri ) {
        final int match = sUriMatcher.match( uri );

        try {
            switch ( match ) {
                case ENGRAM:
                case ENGRAM_WITH_CATEGORY:
                case ENGRAM_WITH_CATEGORY_AND_STATION:
                case ENGRAM_WITH_DLC:
                case ENGRAM_WITH_LEVEL:
                case ENGRAM_WITH_LEVEL_AND_CATEGORY:
                case ENGRAM_WITH_LEVEL_AND_STATION:
                case ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION:
                case ENGRAM_WITH_STATION:
                    return EngramEntry.CONTENT_DIR_TYPE;

                case RESOURCE:
                case RESOURCE_WITH_DRAWABLE:
                    return ResourceEntry.CONTENT_DIR_TYPE;

                case COMPLEX_RESOURCE:
                    return ComplexResourceEntry.CONTENT_DIR_TYPE;

                case CATEGORY:
                case CATEGORY_WITH_PARENT:
                case CATEGORY_WITH_PARENT_AND_STATION:
                    return CategoryEntry.CONTENT_DIR_TYPE;

                case COMPOSITION:
                    return CompositionEntry.CONTENT_DIR_TYPE;

                case QUEUE:
                case QUEUE_WITH_ENGRAM_TABLE:
                    return QueueEntry.CONTENT_DIR_TYPE;

                case DLC:
                    return DLCEntry.CONTENT_DIR_TYPE;

                case STATION:
                case STATION_WITH_DLC:
                    return StationEntry.CONTENT_DIR_TYPE;

                case ENGRAM_ID_WITH_DLC:
                case ENGRAM_WITH_DRAWABLE:
                    return EngramEntry.CONTENT_ITEM_TYPE;

                case RESOURCE_ID_WITH_DLC:
                    return ResourceEntry.CONTENT_ITEM_TYPE;

                case COMPLEX_RESOURCE_ID:
                case COMPLEX_RESOURCE_WITH_RESOURCE:
                    return ComplexResourceEntry.CONTENT_ITEM_TYPE;

                case CATEGORY_ID_WITH_DLC:
                    return CategoryEntry.CONTENT_ITEM_TYPE;

                case COMPOSITION_ID:
                case COMPOSITION_WITH_ENGRAM:
                    return CompositionEntry.CONTENT_ITEM_TYPE;

                case QUEUE_ID:
                case QUEUE_WITH_ENGRAM_ID:
                    return QueueEntry.CONTENT_ITEM_TYPE;

                case DLC_ID:
                    return DLCEntry.CONTENT_ITEM_TYPE;

                case STATION_ID_WITH_DLC:
                    return StationEntry.CONTENT_ITEM_TYPE;

                default:
                    throw new URIUnknownException( uri );
            }
        } catch ( URIUnknownException e ) {
            ExceptionUtil.SendErrorReport( getContext(), TAG, e );
        }

        return null;
    }

    @Override
    public Cursor query( @NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {
        Cursor cursor = null;

        try {
            String tableName;
            switch ( sUriMatcher.match( uri ) ) {
                case CATEGORY:
                    sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = CategoryEntry.TABLE_NAME;
                    break;

                case COMPLEX_RESOURCE:
                    tableName = ComplexResourceEntry.TABLE_NAME;
                    break;

                case COMPOSITION:
                    tableName = CompositionEntry.TABLE_NAME;
                    break;

                case ENGRAM:
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
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
                    sortOrder = DLCEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = DLCEntry.TABLE_NAME;
                    break;

                case STATION:
                    sortOrder = StationEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = StationEntry.TABLE_NAME;
                    break;

                case DLC_ID:
                    selection = DLCEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString( DatabaseContract.getIdFromUri( uri ) )
                    };
                    tableName = DLCEntry.TABLE_NAME;
                    break;

                case CATEGORY_ID_WITH_DLC:
                    selection = CategoryEntry.SQL_QUERY_WITH_ID +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( CategoryEntry.getIdFromUri( uri ) ),
                            Long.toString( CategoryEntry.getDLCIdFromUri( uri ) )
                    };
                    tableName = CategoryEntry.TABLE_NAME;
                    break;

                case CATEGORY_WITH_PARENT:
                    selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( CategoryEntry.getParentIdFromUri( uri ) ),
                            Long.toString( CategoryEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = CategoryEntry.TABLE_NAME;
                    break;

                case CATEGORY_WITH_PARENT_AND_STATION:
                    selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( CategoryEntry.getParentIdFromUri( uri ) ),
                            Long.toString( CategoryEntry.getStationIdFromUri( uri ) ),
                            Long.toString( CategoryEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = CategoryEntry.TABLE_NAME;
                    break;

                case COMPLEX_RESOURCE_ID:
                    selection = ComplexResourceEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString( DatabaseContract.getIdFromUri( uri ) )
                    };
                    tableName = ComplexResourceEntry.TABLE_NAME;
                    break;

                case COMPOSITION_ID:
                    selection = CompositionEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString( DatabaseContract.getIdFromUri( uri ) )
                    };
                    tableName = CompositionEntry.TABLE_NAME;
                    break;

                case COMPOSITION_WITH_ENGRAM:
                    selection = CompositionEntry.SQL_QUERY_WITH_ENGRAM_KEY +
                            " AND " + CompositionEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( CompositionEntry.getEngramIdFromUri( uri ) ),
                            Long.toString( CompositionEntry.getDLCIdFromUri( uri ) )
                    };
                    tableName = CompositionEntry.TABLE_NAME;
                    break;

                case ENGRAM_ID_WITH_DLC:
                    selection = EngramEntry.SQL_QUERY_WITH_ID +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getIdFromUri( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case ENGRAM_WITH_CATEGORY:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getCategoryIdFromUri( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case ENGRAM_WITH_CATEGORY_AND_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getCategoryIdFromUri( uri ) ),
                            Long.toString( EngramEntry.getStationIdFromUriWithCategory( uri ) ),
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

                case ENGRAM_WITH_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getStationIdFromUri( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case ENGRAM_WITH_LEVEL:
                    selection = EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Integer.toString( EngramEntry.getLevelFromUri( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case ENGRAM_WITH_LEVEL_AND_CATEGORY:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getCategoryIdFromUri( uri ) ),
                            Integer.toString( EngramEntry.getLevelFromUriWithCategory( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case ENGRAM_WITH_LEVEL_AND_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getStationIdFromUri( uri ) ),
                            Integer.toString( EngramEntry.getLevelFromUriWithStation( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( EngramEntry.getCategoryIdFromUri( uri ) ),
                            Long.toString( EngramEntry.getStationIdFromUriWithCategory( uri ) ),
                            Integer.toString( EngramEntry.getLevelFromUriWithCategoryAndStation( uri ) ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case QUEUE_ID:
                    selection = QueueEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString( DatabaseContract.getIdFromUri( uri ) )
                    };
                    tableName = QueueEntry.TABLE_NAME;
                    break;

                case QUEUE_WITH_ENGRAM_ID:
                    selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                    selectionArgs = new String[]{
                            Long.toString( QueueEntry.getEngramIdFromUri( uri ) )
                    };
                    tableName = QueueEntry.TABLE_NAME;
                    break;

                case RESOURCE_ID_WITH_DLC:
                    selection = ResourceEntry.SQL_QUERY_WITH_ID +
                            " AND " + ResourceEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( ResourceEntry.getIdFromUri( uri ) ),
                            Long.toString( ResourceEntry.getDLCIdFromUri( uri ) )
                    };
                    tableName = ResourceEntry.TABLE_NAME;
                    break;

                case COMPLEX_RESOURCE_WITH_RESOURCE:
                    selection = ComplexResourceEntry.SQL_QUERY_WITH_RESOURCE_KEY;
                    selectionArgs = new String[]{
                            Long.toString( ComplexResourceEntry.getResourceIdFromUri( uri ) )
                    };
                    tableName = ComplexResourceEntry.TABLE_NAME;
                    break;

                case QUEUE_WITH_ENGRAM_TABLE:
                    projection = QueueEntry.SQL_PROJECTION;
                    selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE_SELECTION;
                    selectionArgs = new String[]{
                            Long.toString( QueueEntry.getDLCIdFromUri( uri ) )
                    };
                    sortOrder = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE_SORT_ORDER_BY_NAME;
                    tableName = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE;
                    break;

                case ENGRAM_WITH_DRAWABLE:
                    projection = new String[]{ EngramEntry.COLUMN_NAME };
                    selection = EngramEntry.SQL_QUERY_WITH_DRAWABLE +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            EngramEntry.getDrawableFromUri( uri ),
                            Long.toString( EngramEntry.getDLCIdFromUri( uri ) ) };
                    tableName = EngramEntry.TABLE_NAME;
                    break;

                case RESOURCE_WITH_DRAWABLE:
                    projection = new String[]{ ResourceEntry._ID };
                    selection = ResourceEntry.SQL_QUERY_WITH_DRAWABLE +
                            " AND " + ResourceEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            ResourceEntry.getDrawableFromUri( uri ),
                            Long.toString( ResourceEntry.getDLCIdFromUri( uri ) ) };
                    tableName = ResourceEntry.TABLE_NAME;
                    break;

                case STATION_ID_WITH_DLC:
                    selection = StationEntry.SQL_QUERY_WITH_ID +
                            " AND " + StationEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( StationEntry.getIdFromUri( uri ) ),
                            Long.toString( StationEntry.getDLCIdFromUri( uri ) )
                    };
                    tableName = StationEntry.TABLE_NAME;
                    break;

                case STATION_WITH_DLC:
                    selection = StationEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString( StationEntry.getDLCIdFromUri( uri ) )
                    };
                    tableName = StationEntry.TABLE_NAME;
                    break;

                default:
                    throw new URIUnknownException( uri );
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
        } catch ( Exception e ) {
            ExceptionUtil.SendErrorReport( getContext(), TAG, e );
        }

        return cursor;
    }

    @Override
    public Uri insert( @NonNull Uri uri, ContentValues values ) {
        Uri returnUri = null;

        try {
            long _id = mOpenHelper.getWritableDatabase()
                    .insert( getTableNameFromUriMatch( uri ), null, values );

            if ( _id >= 0 ) {
                returnUri = DatabaseContract.buildUriWithId( uri, _id );
                getContext().getContentResolver().notifyChange( uri, null );
            } else {
                throw new SQLiteException();
            }
        } catch ( Exception e ) {
            ExceptionUtil.SendErrorReport( getContext(), TAG, e );
        }

        return returnUri;
    }

    @Override
    public int delete( @NonNull Uri uri, String selection, String[] selectionArgs ) {
        int rowsDeleted = 0;

        try {
            String tableName = getTableNameFromUriMatch( uri );

            switch ( sUriMatcher.match( uri ) ) {
                case QUEUE:
                    tableName = QueueEntry.TABLE_NAME;
                    selection = "1";
                    break;

                case QUEUE_ID:
                    tableName = QueueEntry.TABLE_NAME;
                    selection = QueueEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString( DatabaseContract.getIdFromUri( uri ) )
                    };
                    break;
            }

            rowsDeleted = mOpenHelper.getWritableDatabase()
                    .delete( tableName, selection, selectionArgs );

            if ( rowsDeleted > 0 )
                getContext().getContentResolver().notifyChange( uri, null );
        } catch ( URIUnknownException e ) {
            ExceptionUtil.SendErrorReport( getContext(), TAG, e );
        }
        return rowsDeleted;
    }

    @Override
    public int update( @NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs ) {
        int rowsUpdated = 0;

        try {
            if ( selection == null )
                selection = "1";

            rowsUpdated = mOpenHelper.getWritableDatabase()
                    .update( getTableNameFromUriMatch( uri ), values, selection, selectionArgs );
            if ( rowsUpdated > 0 )
                getContext().getContentResolver().notifyChange( uri, null );
        } catch ( URIUnknownException e ) {
            ExceptionUtil.SendErrorReport( getContext(), TAG, e );
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert( @NonNull Uri uri, @NonNull ContentValues[] values ) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsInserted = 0;

        try {
            db.beginTransaction();

            for ( ContentValues value : values ) {
                long _id = db.insert( getTableNameFromUriMatch( uri ), null, value );

                if ( _id > -1 ) rowsInserted++;
                else
                    throw new SQLiteException(
                            String.format(
                                    getContext().getString( R.string.exception_sql_insert ),
                                    uri ) );
            }

            db.setTransactionSuccessful();
        } catch ( URIUnknownException | SQLiteException e ) {
            ExceptionUtil.SendErrorReport( getContext(), TAG, e );
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange( uri, null );
        return rowsInserted;
    }

    String getTableNameFromUriMatch( @NonNull Uri uri ) throws URIUnknownException {
        switch ( sUriMatcher.match( uri ) ) {
            case ENGRAM:
                return EngramEntry.TABLE_NAME;

            case RESOURCE:
                return ResourceEntry.TABLE_NAME;

            case CATEGORY:
                return CategoryEntry.TABLE_NAME;

            case COMPOSITION:
                return CompositionEntry.TABLE_NAME;

            case COMPLEX_RESOURCE:
                return ComplexResourceEntry.TABLE_NAME;

            case QUEUE:
            case QUEUE_ID:
                return QueueEntry.TABLE_NAME;

            case DLC:
                return DLCEntry.TABLE_NAME;

            case STATION:
                return StationEntry.TABLE_NAME;

            default:
                throw new URIUnknownException( uri );
        }
    }
}
