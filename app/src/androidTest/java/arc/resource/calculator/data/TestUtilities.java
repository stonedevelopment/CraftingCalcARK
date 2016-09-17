package arc.resource.calculator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.db.DatabaseHelper;
import arc.resource.calculator.utils.PollingCheck;

public class TestUtilities extends AndroidTestCase {

    // Dummy objects
    static class TestCategory {
        long mId = 200;
        String mName = "Structures";
        long mParent = 0;
    }

    static class TestEngram {
        long mId = 1;
        String mName = "Stone Wall";
        String mDescription = "A brick-and-mortar wall that insulates the inside from the outside and separates rooms.";
        int mDrawable = R.drawable.structures_stone_stone_wall;
    }

    static class TestResource {
        long mId = 1;
        String mName = "Stone";
        int mDrawable = R.drawable.stone;
    }

    static final TestCategory testCategory = new TestCategory();
    static final TestEngram testEngram = new TestEngram();
    static final TestResource testResource = new TestResource();

    static void validateCursor( String error, Cursor valueCursor, ContentValues expectedValues ) {
        assertTrue( "Empty cursor returned. " + error, valueCursor.moveToFirst() );
        validateCurrentRecord( error, valueCursor, expectedValues );
        valueCursor.close();
    }

    static void validateCurrentRecord( String error, Cursor valueCursor, ContentValues expectedValues ) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for ( Map.Entry<String, Object> entry : valueSet ) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex( columnName );
            assertFalse( "Column '" + columnName + "' not found. " + error, idx == -1 );
            String expectedValue = entry.getValue().toString();
            assertEquals( "Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString( idx ) );
        }
    }

    static ContentValues createEngramDummy( long categoryId ) {
        ContentValues testValues = new ContentValues();

        testValues.put( EngramEntry.COLUMN_NAME, testEngram.mName );
        testValues.put( EngramEntry.COLUMN_DESCRIPTION, testEngram.mDescription );
        testValues.put( EngramEntry.COLUMN_DRAWABLE, testEngram.mDrawable );
        testValues.put( EngramEntry.COLUMN_CATEGORY_KEY, categoryId );

        return testValues;
    }

    static ContentValues createResourceDummy() {
        ContentValues testValues = new ContentValues();

        testValues.put( ResourceEntry.COLUMN_NAME, testResource.mName );
        testValues.put( ResourceEntry.COLUMN_DRAWABLE, testResource.mDrawable );

        return testValues;
    }

    static ContentValues createComplexResourceDummy( long engramId, long resourceId ) {
        ContentValues testValues = new ContentValues();

        testValues.put( ComplexResourceEntry.COLUMN_ENGRAM_KEY, engramId );
        testValues.put( ComplexResourceEntry.COLUMN_RESOURCE_KEY, resourceId );

        return testValues;
    }

    static ContentValues createCompositionDummy( long engramId, long resourceId, int quantity ) {
        ContentValues testValues = new ContentValues();

        testValues.put( CompositionEntry.COLUMN_ENGRAM_KEY, engramId );
        testValues.put( CompositionEntry.COLUMN_RESOURCE_KEY, resourceId );
        testValues.put( CompositionEntry.COLUMN_QUANTITY, quantity );

        return testValues;
    }

    static ContentValues createCategoryDummy() {
        ContentValues testValues = new ContentValues();

        testValues.put( CategoryEntry._ID, testCategory.mId );
        testValues.put( CategoryEntry.COLUMN_NAME, testCategory.mName );
        testValues.put( CategoryEntry.COLUMN_PARENT, testCategory.mParent );

        return testValues;
    }

    static ContentValues createQueueDummy( long engramId, int quantity ) {
        ContentValues testValues = new ContentValues();

        testValues.put( QueueEntry.COLUMN_ENGRAM_KEY, engramId );
        testValues.put( QueueEntry.COLUMN_QUANTITY, quantity );

        return testValues;
    }

    static long insertEngram( Context context, long categoryId ) {
        return insertDummyValues( context, EngramEntry.TABLE_NAME, createEngramDummy( categoryId ) );
    }

    static long insertResource( Context context ) {
        return insertDummyValues( context, ResourceEntry.TABLE_NAME, createResourceDummy() );
    }

    static long insertComplexResource( Context context, long engramId, long resourceId ) {
        return insertDummyValues( context, ComplexResourceEntry.TABLE_NAME, createComplexResourceDummy( engramId, resourceId ) );
    }

    static long insertComposition( Context context, long engramId, long resourceId, int quantity ) {
        return insertDummyValues( context, CompositionEntry.TABLE_NAME, createCompositionDummy( engramId, resourceId, quantity ) );
    }

    static long insertQueue( Context context, long engramId, int quantity ) {
        return insertDummyValues( context, QueueEntry.TABLE_NAME, createQueueDummy( engramId, quantity ) );
    }

    static long insertCategory( Context context ) {
        long rowId = insertDummyValues( context, CategoryEntry.TABLE_NAME, createCategoryDummy() );
        assertTrue( "Error: Custom Category ID and Resulting ID are different.", rowId == testCategory.mId );

        return rowId;
    }

    static long insertDummyValues( Context context, String table, ContentValues testValues ) {
        DatabaseHelper databaseHelper = new DatabaseHelper( context );
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        long rowId = database.insert( table, null, testValues );
        assertTrue( "Error: Failure to insert '" + table + "' test values.", rowId != -1L );

        Cursor cursor = database.query( table, null, null, null, null, null, null );
        assertTrue( "Error: No Records returned from '" + table + "' query.", cursor.moveToFirst() );

        // Validate row to make sure it has all its required columns/values
        validateCurrentRecord( "Error: Row validation for table '" + table + "' failed.", cursor, testValues );

        // Test to make sure that no other rows were returned
        assertFalse( "Error: More than one row returned from '" + table + "' query.", cursor.moveToNext() );

        cursor.close();
        database.close();
        return rowId;
    }

    /*
        The functions provided inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.

        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread( "ContentObserverThread" );
            ht.start();
            return new TestContentObserver( ht );
        }

        private TestContentObserver( HandlerThread ht ) {
            super( new Handler( ht.getLooper() ) );
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange( boolean selfChange ) {
            onChange( selfChange, null );
        }

        @Override
        public void onChange( boolean selfChange, Uri uri ) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck( 5000 ) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
