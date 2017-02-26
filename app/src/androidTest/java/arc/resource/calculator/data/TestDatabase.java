package arc.resource.calculator.data;

import android.test.AndroidTestCase;

import arc.resource.calculator.db.DatabaseHelper;
import arc.resource.calculator.util.Util;

public class TestDatabase extends AndroidTestCase {
    private static final String TAG = TestDatabase.class.getSimpleName();

    // This is called before every test.
    public void setUp() {
        deleteDatabase();
    }

    public void testEngramTable() throws Exception {
        // In order to successfully insert an Engram, we must first insert a Category that it points to.
        long categoryRowId = TestUtilities.insertCategory( getContext() );

        TestUtilities.insertEngram( getContext(), categoryRowId );

        Util.Log( TAG, "testEngramTable: " + categoryRowId );
    }

    public void testResourceTable() throws Exception {
        TestUtilities.insertResource( getContext() );

        Util.Log( TAG, "testResourceTable" );
    }

    public void testComplexResourceTable() throws Exception {
        // In order to successfully insert a Complex Resource, we must first:
        // 1. Insert an Engram, inserting a Category first.
        // 2. Insert a Resource

        // In order to successfully insert an Engram, we must first insert a Category that it points to.
        long categoryRowId = TestUtilities.insertCategory( getContext() );
        long engramRowId = TestUtilities.insertEngram( getContext(), categoryRowId );
        long resourceRowId = TestUtilities.insertResource( getContext() );

        // Finally, insert Complex Resource with provided necessities.
        TestUtilities.insertComplexResource( getContext(), engramRowId, resourceRowId );

        Util.Log( TAG, "testComplexResourceTable: " + categoryRowId + ", " + engramRowId + ", " + resourceRowId );
    }

    public void testCompositionTable() throws Exception {
        // In order to successfully insert a Composition, we must first:
        // 1. Insert an Engram, inserting a Category first.
        // 2. Insert a Resource

        // In order to successfully insert an Engram, we must first insert a Category that it points to.
        long categoryRowId = TestUtilities.insertCategory( getContext() );
        long engramRowId = TestUtilities.insertEngram( getContext(), categoryRowId );
        long resourceRowId = TestUtilities.insertResource( getContext() );
        int quantity = 40;

        // Finally, insert Complex Resource with provided necessities.
        TestUtilities.insertComposition( getContext(), engramRowId, resourceRowId, quantity );

        Util.Log( TAG, "testCompositionTable: " + categoryRowId + ", " + engramRowId + ", " + resourceRowId + ", " + quantity );
    }

    public void testCategoryTable() throws Exception {
        TestUtilities.insertCategory( getContext() );

        Util.Log( TAG, "testCategoryTable" );
    }

    public void testQueueTable() throws Exception {
        // In order to successfully insert a Queue, we must first Insert an Engram, inserting a Category first.
        // 2. Insert a Resource

        // In order to successfully insert an Engram, we must first insert a Category that it points to.
        long categoryRowId = TestUtilities.insertCategory( getContext() );
        long engramRowId = TestUtilities.insertEngram( getContext(), categoryRowId );
        int quantity = 1;

        // Finally, insert Queue with provided necessities.
        TestUtilities.insertQueue( getContext(), engramRowId, quantity );

        Util.Log( TAG, "testQueueTable: " + categoryRowId + ", " + engramRowId + ", " + quantity );
    }

    // Util method to delete the database whenever we're ready to
    void deleteDatabase() {
        getContext().deleteDatabase( DatabaseHelper.DATABASE_NAME );
    }

    // This is 'supposedly' called after every test.
    public void tearDown() {
    }
}
