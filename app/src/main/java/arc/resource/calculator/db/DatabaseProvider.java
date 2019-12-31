/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */
package arc.resource.calculator.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.DLCEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.util.ExceptionUtil.URIUnknownException;

import static arc.resource.calculator.util.Util.NO_ID;
import static arc.resource.calculator.util.Util.NO_SIZE;

public class DatabaseProvider extends ContentProvider {
    private static final String TAG = DatabaseProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;
    private ExceptionObserver mExceptionObserver;

    private static final int ENGRAM = 100;
    private static final int ENGRAM_ID_WITH_DLC = 101;
    private static final int ENGRAM_WITH_CATEGORY = 102;
    private static final int ENGRAM_WITH_CATEGORY_AND_STATION = 103;
    private static final int ENGRAM_WITH_DLC = 105;
    private static final int ENGRAM_WITH_STATION = 106;
    private static final int ENGRAM_WITH_LEVEL = 107;
    private static final int ENGRAM_WITH_LEVEL_AND_CATEGORY = 108;
    private static final int ENGRAM_WITH_LEVEL_AND_STATION = 109;
    private static final int ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION = 110;
    private static final int ENGRAM_WITH_SEARCH = 111;

    private static final int RESOURCE = 200;
    private static final int RESOURCE_ID_WITH_DLC = 201;

    private static final int COMPLEX_RESOURCE = 300;
    private static final int COMPLEX_RESOURCE_ID = 301;
    private static final int COMPLEX_RESOURCE_WITH_RESOURCE = 302;

    private static final int CATEGORY = 400;
    private static final int CATEGORY_ID_WITH_DLC = 401;
    private static final int CATEGORY_WITH_PARENT = 402;
    private static final int CATEGORY_WITH_PARENT_AND_STATION = 403;

    private static final int COMPOSITION = 500;
    private static final int COMPOSITION_ID = 501;
    private static final int COMPOSITION_WITH_ENGRAM = 502;

    private static final int QUEUE = 600;
    private static final int QUEUE_ID = 601;
    private static final int QUEUE_WITH_ENGRAM_TABLE = 602;
    private static final int QUEUE_WITH_ENGRAM_ID = 603;

    private static final int DLC = 700;
    private static final int DLC_ID = 701;

    private static final int STATION = 800;
    private static final int STATION_ID_WITH_DLC = 801;
    private static final int STATION_WITH_DLC = 802;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        mExceptionObserver = ExceptionObserver.getInstance();
        return true;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String contentAuthority = DatabaseContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_CATEGORY,
                CATEGORY);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                CATEGORY_ID_WITH_DLC);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_CATEGORY + "/" +
                        DatabaseContract.PATH_CATEGORY_PARENT + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                CATEGORY_WITH_PARENT);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_CATEGORY + "/" +
                        DatabaseContract.PATH_CATEGORY_PARENT + "/#/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                CATEGORY_WITH_PARENT_AND_STATION);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE,
                COMPLEX_RESOURCE);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/#",
                COMPLEX_RESOURCE_ID);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_COMPLEX_RESOURCE + "/" +
                        DatabaseContract.PATH_RESOURCE + "/#",
                COMPLEX_RESOURCE_WITH_RESOURCE);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_COMPOSITION,
                COMPOSITION);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_COMPOSITION + "/#",
                COMPOSITION_ID);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" +
                        DatabaseContract.PATH_ENGRAM + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                COMPOSITION_WITH_ENGRAM);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM,
                ENGRAM);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_ID_WITH_DLC);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_DLC);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_CATEGORY);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_CATEGORY_AND_STATION);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_STATION);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL_AND_CATEGORY);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL_AND_STATION);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_CATEGORY + "/#/" +
                        DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_LEVEL + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_SEARCH + "/*/" +
                        DatabaseContract.PATH_DLC + "/#",
                ENGRAM_WITH_SEARCH);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_QUEUE,
                QUEUE);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_QUEUE + "/#",
                QUEUE_ID);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_QUEUE + "/" +
                        DatabaseContract.PATH_ENGRAM + "/" +
                        DatabaseContract.PATH_DLC + "/#",
                QUEUE_WITH_ENGRAM_TABLE);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_QUEUE + "/" +
                        DatabaseContract.PATH_ENGRAM + "/#",
                QUEUE_WITH_ENGRAM_ID);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_RESOURCE,
                RESOURCE);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_RESOURCE + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                RESOURCE_ID_WITH_DLC);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_DLC,
                DLC);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_DLC + "/#",
                DLC_ID);

        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_STATION,
                STATION);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_STATION + "/#/" +
                        DatabaseContract.PATH_DLC + "/#",
                STATION_ID_WITH_DLC);
        uriMatcher.addURI(contentAuthority, DatabaseContract.PATH_STATION + "/" +
                        DatabaseContract.PATH_DLC + "/#",
                STATION_WITH_DLC);

        return uriMatcher;
    }

    private SQLiteDatabase getWritableDatabase() {
        return mOpenHelper.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        return mOpenHelper.getReadableDatabase();
    }

    @Override
    public String getType(@NonNull Uri uri) {
        try {
            switch (sUriMatcher.match(uri)) {
                case ENGRAM:
                case ENGRAM_WITH_CATEGORY:
                case ENGRAM_WITH_CATEGORY_AND_STATION:
                case ENGRAM_WITH_DLC:
                case ENGRAM_WITH_LEVEL:
                case ENGRAM_WITH_LEVEL_AND_CATEGORY:
                case ENGRAM_WITH_LEVEL_AND_STATION:
                case ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION:
                case ENGRAM_WITH_SEARCH:
                case ENGRAM_WITH_STATION:
                    return EngramEntry.CONTENT_DIR_TYPE;

                case RESOURCE:
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
                    throw new URIUnknownException(uri);
            }
        } catch (URIUnknownException e) {
            mExceptionObserver.notifyFatalExceptionCaught(TAG, e);

            return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        try {
            String tableName = getTableNameFromUriMatch(uri);
            switch (sUriMatcher.match(uri)) {
                case CATEGORY:
                    sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case COMPLEX_RESOURCE:
                    break;

                case COMPOSITION:
                    break;

                case ENGRAM:
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case QUEUE:
                    break;

                case RESOURCE:
                    sortOrder = ResourceEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case DLC:
                    sortOrder = DLCEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case STATION:
                    sortOrder = StationEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case DLC_ID:
                    selection = DLCEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString(DatabaseContract.getIdFromUri(uri))
                    };
                    break;

                case CATEGORY_ID_WITH_DLC:
                    selection = CategoryEntry.SQL_QUERY_WITH_ID +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(CategoryEntry.getIdFromUri(uri)),
                            Long.toString(CategoryEntry.getDLCIdFromUri(uri))
                    };
                    break;

                case CATEGORY_WITH_PARENT:
                    selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(CategoryEntry.getParentIdFromUri(uri)),
                            Long.toString(CategoryEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case CATEGORY_WITH_PARENT_AND_STATION:
                    selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + CategoryEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(CategoryEntry.getParentIdFromUri(uri)),
                            Long.toString(CategoryEntry.getStationIdFromUri(uri)),
                            Long.toString(CategoryEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = CategoryEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case COMPLEX_RESOURCE_ID:
                    selection = ComplexResourceEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString(DatabaseContract.getIdFromUri(uri))
                    };
                    break;

                case COMPOSITION_ID:
                    selection = CompositionEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString(DatabaseContract.getIdFromUri(uri))
                    };
                    break;

                case COMPOSITION_WITH_ENGRAM:
                    selection = CompositionEntry.SQL_QUERY_WITH_ENGRAM_KEY +
                            " AND " + CompositionEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(CompositionEntry.getEngramIdFromUri(uri)),
                            Long.toString(CompositionEntry.getDLCIdFromUri(uri))
                    };
                    break;

                case ENGRAM_ID_WITH_DLC:
                    selection = EngramEntry.SQL_QUERY_WITH_ID +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getIdFromUri(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    break;

                case ENGRAM_WITH_CATEGORY:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getCategoryIdFromUri(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_CATEGORY_AND_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getCategoryIdFromUri(uri)),
                            Long.toString(EngramEntry.getStationIdFromUriWithCategory(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_DLC:
                    selection = EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getStationIdFromUri(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_LEVEL:
                    selection = EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Integer.toString(EngramEntry.getLevelFromUri(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_LEVEL_AND_CATEGORY:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getCategoryIdFromUri(uri)),
                            Integer.toString(EngramEntry.getLevelFromUriWithCategory(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_LEVEL_AND_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getStationIdFromUri(uri)),
                            Integer.toString(EngramEntry.getLevelFromUriWithStation(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION:
                    selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_STATION_KEY +
                            " AND " + EngramEntry.SQL_QUERY_WITH_LEVEL +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(EngramEntry.getCategoryIdFromUri(uri)),
                            Long.toString(EngramEntry.getStationIdFromUriWithCategory(uri)),
                            Integer.toString(EngramEntry.getLevelFromUriWithCategoryAndStation(uri)),
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case ENGRAM_WITH_SEARCH:
                    selection = EngramEntry.SQL_QUERY_WITH_SEARCH +
                            " AND " + EngramEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            "%" + EngramEntry.getSearchQueryFromUri(uri) + "%",
                            Long.toString(EngramEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_SORT_ORDER_BY_NAME;
                    break;

                case QUEUE_ID:
                    selection = QueueEntry.SQL_QUERY_WITH_ID;
                    selectionArgs = new String[]{
                            Long.toString(DatabaseContract.getIdFromUri(uri))
                    };
                    break;

                case QUEUE_WITH_ENGRAM_ID:
                    selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                    selectionArgs = new String[]{
                            Long.toString(QueueEntry.getEngramIdFromUri(uri))
                    };
                    break;

                case RESOURCE_ID_WITH_DLC:
                    selection = ResourceEntry.SQL_QUERY_WITH_ID +
                            " AND " + ResourceEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(ResourceEntry.getIdFromUri(uri)),
                            Long.toString(ResourceEntry.getDLCIdFromUri(uri))
                    };
                    break;

                case COMPLEX_RESOURCE_WITH_RESOURCE:
                    selection = ComplexResourceEntry.SQL_QUERY_WITH_RESOURCE_KEY;
                    selectionArgs = new String[]{
                            Long.toString(ComplexResourceEntry.getResourceIdFromUri(uri))
                    };
                    break;

                case QUEUE_WITH_ENGRAM_TABLE:
                    projection = QueueEntry.SQL_PROJECTION;
                    selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE_SELECTION;
                    selectionArgs = new String[]{
                            Long.toString(QueueEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = EngramEntry.SQL_COLUMN_NAME + " ASC";
                    break;

                case STATION_ID_WITH_DLC:
                    selection = StationEntry.SQL_QUERY_WITH_ID +
                            " AND " + StationEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(StationEntry.getIdFromUri(uri)),
                            Long.toString(StationEntry.getDLCIdFromUri(uri))
                    };
                    break;

                case STATION_WITH_DLC:
                    selection = StationEntry.SQL_QUERY_WITH_DLC_KEY;
                    selectionArgs = new String[]{
                            Long.toString(StationEntry.getDLCIdFromUri(uri))
                    };
                    sortOrder = StationEntry.SQL_COLUMN_NAME + " ASC";
                    break;

                default:
                    throw new URIUnknownException(uri);
            }

            // Query database with provided args, null or not.
            Cursor cursor = getReadableDatabase().query(
                    tableName, projection, selection, selectionArgs, null, null, sortOrder);

            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);

            return cursor;
        } catch (Exception e) {
            mExceptionObserver.notifyExceptionCaught(TAG, e);

            return null;
        }

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        try {
            String tableName = getTableNameFromUriMatch(uri);
            int rowsDeleted = getWritableDatabase().delete(tableName, selection, selectionArgs);

            if (rowsDeleted > 0)
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

            return rowsDeleted;
        } catch (URIUnknownException e) {
            mExceptionObserver.notifyExceptionCaught(TAG, e);

            return 0;
        } catch (NullPointerException | SQLiteException e) {
            mExceptionObserver.notifyFatalExceptionCaught(TAG, e);

            return NO_SIZE;
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();

            String table = getTableNameFromUriMatch(uri);

            int rowsInserted = 0;
            for (ContentValues value : values) {
                long _id = db.insert(table, null, value);

                if (_id == NO_ID)
                    throw new SQLiteException();

                rowsInserted++;
            }

            db.setTransactionSuccessful();

            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

            return rowsInserted;
        } catch (Exception e) {
            return NO_SIZE;
        } finally {
            db.endTransaction();
        }

    }

    private String getTableNameFromUriMatch(@NonNull Uri uri) throws URIUnknownException {
        switch (sUriMatcher.match(uri)) {
            case CATEGORY:
            case CATEGORY_ID_WITH_DLC:
            case CATEGORY_WITH_PARENT:
            case CATEGORY_WITH_PARENT_AND_STATION:
                return CategoryEntry.TABLE_NAME;

            case COMPLEX_RESOURCE:
            case COMPLEX_RESOURCE_ID:
            case COMPLEX_RESOURCE_WITH_RESOURCE:
                return ComplexResourceEntry.TABLE_NAME;

            case COMPOSITION:
            case COMPOSITION_ID:
            case COMPOSITION_WITH_ENGRAM:
                return CompositionEntry.TABLE_NAME;

            case DLC:
            case DLC_ID:
                return DLCEntry.TABLE_NAME;

            case ENGRAM:
            case ENGRAM_ID_WITH_DLC:
            case ENGRAM_WITH_CATEGORY:
            case ENGRAM_WITH_CATEGORY_AND_STATION:
            case ENGRAM_WITH_DLC:
            case ENGRAM_WITH_LEVEL:
            case ENGRAM_WITH_LEVEL_AND_CATEGORY:
            case ENGRAM_WITH_LEVEL_AND_STATION:
            case ENGRAM_WITH_LEVEL_CATEGORY_AND_STATION:
            case ENGRAM_WITH_SEARCH:
            case ENGRAM_WITH_STATION:
                return EngramEntry.TABLE_NAME;

            case QUEUE:
            case QUEUE_ID:
            case QUEUE_WITH_ENGRAM_ID:
            case QUEUE_WITH_ENGRAM_TABLE:
                return QueueEntry.TABLE_NAME;

            case RESOURCE:
            case RESOURCE_ID_WITH_DLC:
                return ResourceEntry.TABLE_NAME;

            case STATION:
            case STATION_ID_WITH_DLC:
            case STATION_WITH_DLC:
                return StationEntry.TABLE_NAME;

            default:
                throw new URIUnknownException(uri);
        }
    }
}