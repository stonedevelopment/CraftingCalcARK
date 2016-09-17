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

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;

    static final int ENGRAM = 100;
    static final int ENGRAM_ID = 101;
    static final int ENGRAM_WITH_CATEGORY = 110;
    static final int ENGRAM_WITH_CATEGORY_ID = 111;

    static final int RESOURCE = 200;
    static final int RESOURCE_ID = 201;

    static final int COMPLEX_RESOURCE = 300;
    static final int COMPLEX_RESOURCE_ID = 301;
    static final int COMPLEX_RESOURCE_WITH_ENGRAM_TABLE = 310;
    static final int COMPLEX_RESOURCE_WITH_ENGRAM_ID = 311;
    static final int COMPLEX_RESOURCE_WITH_RESOURCE_TABLE = 320;
    static final int COMPLEX_RESOURCE_WITH_RESOURCE_ID = 321;

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

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION, COMPOSITION );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/#", COMPOSITION_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_ENGRAM, COMPOSITION_WITH_ENGRAM );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_ENGRAM + "/#", COMPOSITION_WITH_ENGRAM_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_RESOURCE, COMPOSITION_WITH_RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_COMPOSITION + "/" + DatabaseContract.PATH_RESOURCE + "/#", COMPOSITION_WITH_RESOURCE_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM, ENGRAM );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/#", ENGRAM_ID );
//        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_CATEGORY, ENGRAM_WITH_CATEGORY );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_ENGRAM + "/" + DatabaseContract.PATH_CATEGORY + "/#", ENGRAM_WITH_CATEGORY_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE, QUEUE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/#", QUEUE_ID );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" + DatabaseContract.PATH_ENGRAM, QUEUE_WITH_ENGRAM_TABLE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_QUEUE + "/" + DatabaseContract.PATH_ENGRAM + "/#", QUEUE_WITH_ENGRAM_ID );

        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE, RESOURCE );
        uriMatcher.addURI( contentAuthority, DatabaseContract.PATH_RESOURCE + "/#", RESOURCE_ID );

        return uriMatcher;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {
        final int match = sUriMatcher.match( uri );

        Cursor cursor;

        long id = 0;
        String table = null;

        switch ( match ) {
            case CATEGORY:
                table = CategoryEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE:
                table = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPOSITION:
                table = CompositionEntry.TABLE_NAME;
                break;

            case ENGRAM:
                table = EngramEntry.TABLE_NAME;
                break;

            case QUEUE:
                table = QueueEntry.TABLE_NAME;
                break;

            case RESOURCE:
                table = ResourceEntry.TABLE_NAME;
                break;

            case CATEGORY_ID:
                id = DatabaseContract.getIdFromUri( uri );
                selection = CategoryEntry.SQL_QUERY_WITH_ID;
                table = CategoryEntry.TABLE_NAME;
                break;

            case CATEGORY_WITH_PARENT_ID:
                id = CategoryEntry.getParentIdFromUri( uri );
                selection = CategoryEntry.SQL_QUERY_WITH_PARENT_ID;
                table = CategoryEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_ID:
                id = DatabaseContract.getIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ID;
                table = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_ENGRAM_ID:
                id = ComplexResourceEntry.getEngramIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                table = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_ENGRAM_TABLE:
                selection = ComplexResourceEntry.SQL_QUERY_WITH_ENGRAM_TABLE;
                table = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_RESOURCE_ID:
                id = ComplexResourceEntry.getResourceIdFromUri( uri );
                selection = ComplexResourceEntry.SQL_QUERY_WITH_RESOURCE_KEY;
                table = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPLEX_RESOURCE_WITH_RESOURCE_TABLE:
                selection = ComplexResourceEntry.SQL_QUERY_WITH_RESOURCE_TABLE;
                table = ComplexResourceEntry.TABLE_NAME;
                break;

            case COMPOSITION_ID:
                id = DatabaseContract.getIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_ID;
                table = CompositionEntry.TABLE_NAME;
                break;

            case COMPOSITION_WITH_ENGRAM_ID:
                id = CompositionEntry.getEngramIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                table = CompositionEntry.TABLE_NAME;
                break;

            case COMPOSITION_WITH_RESOURCE_ID:
                id = CompositionEntry.getResourceIdFromUri( uri );
                selection = CompositionEntry.SQL_QUERY_WITH_RESOURCE_KEY;
                table = CompositionEntry.TABLE_NAME;
                break;

            case ENGRAM_ID:
                id = DatabaseContract.getIdFromUri( uri );
                selection = EngramEntry.SQL_QUERY_WITH_ID;
                table = EngramEntry.TABLE_NAME;
                break;

            case ENGRAM_WITH_CATEGORY_ID:
                id = EngramEntry.getCategoryIdFromUri( uri );
                selection = EngramEntry.SQL_QUERY_WITH_CATEGORY_KEY;
                table = EngramEntry.TABLE_NAME;
                break;

            case QUEUE_ID:
                id = DatabaseContract.getIdFromUri( uri );
                selection = QueueEntry.SQL_QUERY_WITH_ID;
                table = QueueEntry.TABLE_NAME;
                break;

            case QUEUE_WITH_ENGRAM_ID:
                id = QueueEntry.getEngramIdFromUri( uri );
                selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_KEY;
                table = QueueEntry.TABLE_NAME;
                break;

            case QUEUE_WITH_ENGRAM_TABLE:
                selection = QueueEntry.SQL_QUERY_WITH_ENGRAM_TABLE;
                table = QueueEntry.TABLE_NAME;
                break;

            case RESOURCE_ID:
                id = DatabaseContract.getIdFromUri( uri );
                selection = ResourceEntry.SQL_QUERY_WITH_ID;
                table = ResourceEntry.TABLE_NAME;
                break;

            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }

        // Override selectionArgs if switch matched a case that provides an 'id'
        if ( id != 0 ) {
            selectionArgs = new String[]{ Long.toString( id ) };
        }

        // Query database with provided args, null or not.
        cursor = mOpenHelper.getReadableDatabase().query(
                table,
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
    public String getType( Uri uri ) {
        final int match = sUriMatcher.match( uri );

        switch ( match ) {
            case ENGRAM:
            case ENGRAM_WITH_CATEGORY:
                return EngramEntry.CONTENT_DIR_TYPE;

            case ENGRAM_ID:
            case ENGRAM_WITH_CATEGORY_ID:
                return EngramEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }
    }

    @Override
    public Uri insert( Uri uri, ContentValues values ) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match( uri );

        Uri returnUri;
        long _id;

        switch ( match ) {
            case ENGRAM:
                _id = db.insert( EngramEntry.TABLE_NAME, null, values );

                if ( _id > 0 )
                    returnUri = EngramEntry.buildUriWithId( _id );
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
                break;
            case RESOURCE:
                _id = db.insert( ResourceEntry.TABLE_NAME, null, values );

                if ( _id > 0 )
                    returnUri = ResourceEntry.buildUriWithId( _id );
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
                break;
            case CATEGORY:
                _id = db.insert( CategoryEntry.TABLE_NAME, null, values );

                if ( _id > 0 )
                    returnUri = CategoryEntry.buildUriWithId( _id );
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
                break;
            case COMPOSITION:
                _id = db.insert( CompositionEntry.TABLE_NAME, null, values );

                if ( _id > 0 )
                    returnUri = CompositionEntry.buildUriWithId( _id );
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
                break;
            case COMPLEX_RESOURCE:
                _id = db.insert( ComplexResourceEntry.TABLE_NAME, null, values );

                if ( _id > 0 )
                    returnUri = ComplexResourceEntry.buildUriWithId( _id );
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
                break;
            case QUEUE:
                _id = db.insert( QueueEntry.TABLE_NAME, null, values );

                if ( _id > 0 )
                    returnUri = QueueEntry.buildUriWithId( _id );
                else
                    throw new android.database.SQLException( getContext().getString( R.string.exception_sql_insert ) + uri );
                break;
            default:
                throw new UnsupportedOperationException( "Unknown uri: " + uri );
        }

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
