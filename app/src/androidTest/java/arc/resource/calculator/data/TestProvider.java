package arc.resource.calculator.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import java.util.Vector;
import java.util.concurrent.ExecutionException;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.db.DatabaseProvider;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.utils.ParseJsonTask;

public class TestProvider extends AndroidTestCase {
    private static final String TAG = TestProvider.class.getSimpleName();

    private Vector<ContentValues> mResourceVector;
    private Vector<ContentValues> mCategoryVector;
    private Vector<ContentValues> mEngramVector;
    private Vector<ContentValues> mCompositionVector;

    public void deleteThenTestAllRecords() {
        deleteAllRecordsFromProvider();
        queryForAllRecordsFromProvider();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        deleteThenTestAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = getContext().getPackageManager();

        ComponentName componentName = new ComponentName( getContext().getPackageName(),
                DatabaseProvider.class.getName() );
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo( componentName, 0 );

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals( "Error: DatabaseProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + DatabaseContract.CONTENT_AUTHORITY,
                    providerInfo.authority, DatabaseContract.CONTENT_AUTHORITY );
        } catch ( PackageManager.NameNotFoundException e ) {
            // I guess the provider isn't registered correctly.
            assertTrue( "Error: DatabaseProvider not registered at " + getContext().getPackageName(), false );
        }
    }

    public void testBulkInsertResources() throws ExecutionException, InterruptedException {
        // First, let's parse raw json file into Vector objects containing ContentValues
        parseJSONIntoVectors();

        bulkInsertResources();
    }

    public void testBulkInsertCategories() throws ExecutionException, InterruptedException {
        // First, let's parse raw json file into Vector objects containing ContentValues
        parseJSONIntoVectors();

        bulkInsertCategories();
    }

    public void testBulkInsertEngrams() throws ExecutionException, InterruptedException {
        // First, let's parse raw json file into Vector objects containing ContentValues
        parseJSONIntoVectors();

        bulkInsertEngrams();
    }

    public void testBulkInsertComposition() throws ExecutionException, InterruptedException {
        // First, let's parse raw json file into Vector objects containing ContentValues
        parseJSONIntoVectors();

        // Next, we bulk insert Resources
        bulkInsertResources();

        // Next, we bulk insert Engrams
        bulkInsertEngrams();

        // Now, let's iterate through the Composition Vector and query for each id
        Vector<ContentValues> cVector = new Vector<>( mCompositionVector.size() );
        for ( ContentValues cValues : mCompositionVector ) {
//            String eDrawable = cValues.getAsString( CompositionEntry.COLUMN_ENGRAM_DRAWABLE );
//            String rDrawable = cValues.getAsString( CompositionEntry.COLUMN_RESOURCE_DRAWABLE );
//            int quantity = cValues.getAsInteger( CompositionEntry.COLUMN_QUANTITY );
//
//            ContentValues values = new ContentValues();
//            values.put( CompositionEntry.COLUMN_RESOURCE_KEY, getIDFromQuery( ResourceEntry.buildUriWithDrawable( rDrawable ) ) );
//            values.put( CompositionEntry.COLUMN_ENGRAM_KEY, getIDFromQuery( EngramEntry.buildUriWithDrawable( eDrawable ) ) );
//            values.put( CompositionEntry.COLUMN_QUANTITY, quantity );
//
//            cVector.add( values );
        }

        bulkInsertByUri( CompositionEntry.CONTENT_URI, cVector.size(), cVector, CompositionEntry.TABLE_NAME );
    }

    public void testBulkInsertComplexResources() throws ExecutionException, InterruptedException {
        // First, let's parse raw json file into Vector objects containing ContentValues
        parseJSONIntoVectors();

        // Next, we bulk insert Resources
        bulkInsertResources();

        // Next, we bulk insert Engrams
        bulkInsertEngrams();

        // Now, let us query database for matching drawable strings
        Cursor cursor = getContext().getContentResolver().query(
                ComplexResourceEntry.buildUriWithDrawable(), null, null, null, null );

        // Check if we found atleast 1
        assertTrue( cursor.getCount() > 0 );

        // Create Vector to use for bulkInsert after cursor is closed
        Vector<ContentValues> cVector = new Vector<>();
        while ( cursor.moveToNext() ) {
            long engram_id = cursor.getLong( cursor.getColumnIndex( ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
            long resource_id = cursor.getLong( cursor.getColumnIndex( ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

            ContentValues values = new ContentValues();
            values.put( ComplexResourceEntry.COLUMN_ENGRAM_KEY, engram_id );
            values.put( ComplexResourceEntry.COLUMN_RESOURCE_KEY, resource_id );

            Helper.Log( TAG, values.toString() );

            cVector.add( values );
        }
        cursor.close();

        bulkInsertByUri( ComplexResourceEntry.CONTENT_URI, cVector.size(), cVector, ComplexResourceEntry.TABLE_NAME );
    }

    private void bulkInsertResources() throws ExecutionException, InterruptedException {
//        bulkInsertByUri( ResourceEntry.CONTENT_URI, ResourceInitializer.getCount(), mResourceVector, ResourceEntry.TABLE_NAME );
    }

    private void bulkInsertCategories() throws ExecutionException, InterruptedException {
//        bulkInsertByUri( CategoryEntry.CONTENT_URI, CategoryInitializer.getCount(), mCategoryVector, CategoryEntry.TABLE_NAME );
    }

    private void bulkInsertEngrams() throws ExecutionException, InterruptedException {
//        bulkInsertByUri( EngramEntry.CONTENT_URI, EngramInitializer.getCount(), mEngramVector, EngramEntry.TABLE_NAME );
    }

    private void bulkInsertByUri( Uri testUri, int expectedCount, Vector<ContentValues> expectedValues, String tableName ) throws ExecutionException, InterruptedException {

        // Register a content observer for our bulk insert.
        TestUtilities.TestContentObserver
                testContentObserver = TestUtilities.getTestContentObserver();
        getContext().getContentResolver().registerContentObserver( testUri, true, testContentObserver );

        ContentValues[] contentValues = new ContentValues[expectedValues.size()];
        int insertCount = getContext().getContentResolver().bulkInsert( testUri, expectedValues.toArray( contentValues ) );

        // If this fails, it means that you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in your BulkInsert
        // ContentProvider method.
        testContentObserver.waitForNotificationOrFail();
        getContext().getContentResolver().unregisterContentObserver( testContentObserver );

        assertEquals( expectedCount, insertCount );

        // A cursor is your primary interface to the query results.
        Cursor cursor = getContext().getContentResolver().query(
                testUri, null, null, null, null );

        // we should have as many records in the database as we've inserted
        assertEquals( expectedCount, cursor.getCount() );

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for ( int i = 0; i < expectedCount; i++, cursor.moveToNext() ) {
            TestUtilities.validateCurrentRecord(
                    "testBulkInsert. Error validating " + tableName + " for record: " + i, cursor, contentValues[i] );
        }
        cursor.close();
    }

    private void parseJSONIntoVectors() throws ExecutionException, InterruptedException {
        // Parse JSON file for data to bulk insert.
        ParseJsonTask parseJsonTask = new ParseJsonTask( getContext() );
        parseJsonTask.execute();

        // Get a Vector of Vectors containing ContentValues, contains all Vectors of all JSON parse tables
        Vector<Vector<ContentValues>> vVector = parseJsonTask.get();

        assertTrue( vVector != null );

        this.mResourceVector = vVector.get( 0 );
        this.mCategoryVector = vVector.get( 1 );
        this.mEngramVector = vVector.get( 2 );
        this.mCompositionVector = vVector.get( 3 );
    }

    private long getIDFromQuery( Uri uri ) {
        Cursor cursor = getContext().getContentResolver().query( uri, null, null, null, null );

        // Check if we found atleast 1 drawable from database query, some drawables may share same image
        assertTrue( cursor.getCount() > 0 );

        cursor.moveToFirst();
        long _id = cursor.getLong( cursor.getColumnIndex( CompositionEntry._ID ) );
        cursor.close();

        return _id;
    }

    private void deleteAllRecordsFromProvider() {
        delete( QueueEntry.CONTENT_URI );
        delete( ComplexResourceEntry.CONTENT_URI );
        delete( CompositionEntry.CONTENT_URI );
        delete( CategoryEntry.CONTENT_URI );
        delete( EngramEntry.CONTENT_URI );
        delete( ResourceEntry.CONTENT_URI );
    }

    private void queryForAllRecordsFromProvider() {
        query( QueueEntry.CONTENT_URI, QueueEntry.TABLE_NAME );
        query( ComplexResourceEntry.CONTENT_URI, ComplexResourceEntry.TABLE_NAME );
        query( CompositionEntry.CONTENT_URI, CompositionEntry.TABLE_NAME );
        query( CategoryEntry.CONTENT_URI, CategoryEntry.TABLE_NAME );
        query( EngramEntry.CONTENT_URI, EngramEntry.TABLE_NAME );
        query( ResourceEntry.CONTENT_URI, ResourceEntry.TABLE_NAME );
    }

    private void delete( Uri contentUri ) {
        getContext().getContentResolver().delete( contentUri, null, null );
    }

    private void query( Uri contentUri, String tableName ) {
        Cursor cursor = getContext().getContentResolver().query( contentUri, null, null, null, null );
        assertEquals( "Error: Records not deleted from " + tableName + " table during delete", 0, cursor.getCount() );
        cursor.close();
    }
}
