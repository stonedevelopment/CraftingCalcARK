package arc.resource.calculator;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.util.JsonUtil;

public class UpdateService extends IntentService {
    private static final String TAG = UpdateService.class.getSimpleName();

    public static final int STATUS_ERROR = -1;
    public static final int STATUS_STARTED = 0;
    public static final int STATUS_UPDATING = 1;
    public static final int STATUS_FINISHED = 2;

    public static final String PARAM_TIME = "start_time";
    public static final String PARAM_MESSAGE = "status_message";
    public static final String PARAM_RECEIVER = "receiver";
    public static final String PARAM_REQUEST_ID = "request_id";

    private Bundle mBundle;
    private ResultReceiver mReceiver;

    public UpdateService() {
        super( TAG );
    }

    @Override
    protected void onHandleIntent( Intent intent ) {
        Log.d( TAG, "Service has started!" );

        // Register receiver to receive progress signals
        mReceiver = intent.getParcelableExtra( PARAM_RECEIVER );

        // Fill bundle with data used to display status
        mBundle = new Bundle();
        mBundle.putLong( PARAM_TIME, intent.getLongExtra( PARAM_TIME, System.currentTimeMillis() ) );

        // Send an update to LoadActivity to tell user that initialization has started!
        sendStatusUpdate( STATUS_STARTED );

        // Remove all records from database
        deleteAllRecordsFromProvider();

        // Read local json file into a string
        String jsonString = JsonUtil.readRawJsonFileToJsonString( this );

        // Attempt to parse json string
        try {
            // Tell user that database initialization is in progress
            sendStatusUpdate( STATUS_UPDATING );

            JSONObject jsonObject = new JSONObject( jsonString );

            insertDLC( jsonObject.getJSONArray( DatabaseContract.DLCEntry.TABLE_NAME ) );
            insertStations( jsonObject.getJSONArray( DatabaseContract.StationEntry.TABLE_NAME ) );
            insertCategories( jsonObject.getJSONArray( DatabaseContract.CategoryEntry.TABLE_NAME ) );
            insertResources( jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME ) );
            insertEngrams( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) );
            insertComplexResources( jsonObject.getJSONArray( DatabaseContract.ComplexResourceEntry.TABLE_NAME ) );

            sendStatusUpdate( STATUS_FINISHED );
        }

        // If error, send error signal, stop service
        catch ( JSONException e ) {
            mBundle.putString( PARAM_MESSAGE, e.getLocalizedMessage() );
            sendStatusUpdate( STATUS_ERROR );
        }

        stopSelf();
        Log.d( TAG, "Service has finished." );
    }

    void sendStatusUpdate( int statusCode ) {
        mReceiver.send( statusCode, mBundle );
    }

    void deleteAllRecordsFromProvider() {
        delete( DatabaseContract.DLCEntry.CONTENT_URI );
        delete( DatabaseContract.ResourceEntry.CONTENT_URI );
        delete( DatabaseContract.ComplexResourceEntry.CONTENT_URI );
        delete( DatabaseContract.CompositionEntry.CONTENT_URI );
        delete( DatabaseContract.CategoryEntry.CONTENT_URI );
        delete( DatabaseContract.StationEntry.CONTENT_URI );
        delete( DatabaseContract.EngramEntry.CONTENT_URI );
        delete( DatabaseContract.QueueEntry.CONTENT_URI );
    }

    void delete( Uri uri ) {
        getContentResolver().delete( uri, null, null );
    }

    void bulkInsertWithUri( Uri insertUri, Vector<ContentValues> vector ) {
        ContentValues[] contentValues = new ContentValues[vector.size()];

        int inserted = getContentResolver().bulkInsert( insertUri, vector.toArray( contentValues ) );

        Log.d( TAG, "Inserted " + inserted + " records. Uri: " + insertUri.toString() );
    }

    void insertComplexResources( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long resource_id = jsonObject.getLong( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY );
            long engram_id = jsonObject.getLong( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY );

            //Log.d( TAG, "-- insertComplexResources > resource_id: " + resource_id + ", engram_id: " + engram_id );

            ContentValues values = new ContentValues();
            values.put( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resource_id );
            values.put( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engram_id );

            //Log.d( TAG, "   ++ insertComplexResources Success!" );

            vector.add( values );
        }

        bulkInsertWithUri( DatabaseContract.ComplexResourceEntry.CONTENT_URI, vector );
    }

    void insertDLC( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long _id = jsonObject.getLong( DatabaseContract.DLCEntry._ID );
            String name = jsonObject.getString( DatabaseContract.DLCEntry.COLUMN_NAME );

            //Log.d( TAG, "-- insertDLC > _id: " + _id + ", name: " + name );

            ContentValues values = new ContentValues();
            values.put( DatabaseContract.DLCEntry._ID, _id );
            values.put( DatabaseContract.DLCEntry.COLUMN_NAME, name );

            //Log.d( TAG, "   ++ insertDLC Success!" );

            vector.add( values );
        }

        bulkInsertWithUri( DatabaseContract.DLCEntry.CONTENT_URI, vector );
    }

    void insertResources( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long _id = jsonObject.getLong( DatabaseContract.ResourceEntry._ID );
            String name = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_NAME );
            String drawable = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY );

            //Log.d( TAG, "-- insertResources > " + _id + " name: " + name + ", drawable: " + drawable );

            for ( int d = 0; d < dlc_ids.length(); d++ ) {
                long dlc_id = dlc_ids.getLong( d );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.ResourceEntry._ID, _id );
                values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, drawable );
                values.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, dlc_id );

                //Log.d( TAG, "   ++ insertResources Success! dlc_id: " + dlc_id );

                vector.add( values );
            }
        }

        bulkInsertWithUri( DatabaseContract.ResourceEntry.CONTENT_URI, vector );
    }

    void insertStations( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long _id = jsonObject.getLong( DatabaseContract.StationEntry._ID );
            String name = jsonObject.getString( DatabaseContract.StationEntry.COLUMN_NAME );
            String drawable = jsonObject.getString( DatabaseContract.StationEntry.COLUMN_DRAWABLE );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.StationEntry.COLUMN_DLC_KEY );

            //Log.d( TAG, "-- insertStations > _id: " + _id + ", name: " + name + ", drawable: " + drawable );

            for ( int d = 0; d < dlc_ids.length(); d++ ) {
                long dlc_id = dlc_ids.getLong( d );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.StationEntry._ID, _id );
                values.put( DatabaseContract.StationEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.StationEntry.COLUMN_DRAWABLE, drawable );
                values.put( DatabaseContract.StationEntry.COLUMN_DLC_KEY, dlc_id );

                //Log.d( TAG, "   ++ insertStations Success! dlc_id: " + dlc_id );

                vector.add( values );
            }
        }

        bulkInsertWithUri( DatabaseContract.StationEntry.CONTENT_URI, vector );
    }

    void insertCategories( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long _id = jsonObject.getLong( DatabaseContract.CategoryEntry._ID );
            String name = jsonObject.getString( DatabaseContract.CategoryEntry.COLUMN_NAME );
            long parent_id = jsonObject.getLong( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY );
            JSONArray station_ids = jsonObject.getJSONArray( DatabaseContract.CategoryEntry.COLUMN_STATION_KEY );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY );

            //Log.d( TAG, "-- insertCategories > _id: " + _id + ", name: " + name + ", parent_id: " + parent_id );

            // first go through dlc_ids...
            for ( int d = 0; d < dlc_ids.length(); d++ ) {
                long dlc_id = dlc_ids.getLong( d );

                // ...while going through station_ids
                for ( int s = 0; s < station_ids.length(); s++ ) {
                    long station_id = station_ids.getLong( s );

                    //Log.d( TAG, "   -? dlc_id: " + dlc_id + ", station_id: " + station_id );

                    // check if dlc_id and station_id's dlc_id match
                    Cursor cursor = getContentResolver().query(
                            DatabaseContract.StationEntry.buildUriWithId( dlc_id, station_id ),
                            null, null, null, null );

                    // no match found, try next station_id
                    if ( cursor == null ) {
                        //Log.e( TAG, "   -! cursor == null! (possibly because it wasn't added with proper dlc_id)" );
                        continue;
                    }

                    // match found, adding values into vector
                    if ( cursor.moveToFirst() ) {
                        ContentValues values = new ContentValues();
                        values.put( DatabaseContract.CategoryEntry._ID, _id );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_NAME, name );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY, parent_id );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_STATION_KEY, station_id );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY, dlc_id );

                        vector.add( values );
                    } else {
                        //Log.e( TAG, "   -! moveToFirst() did not fire! (possibly because it wasn't added with proper dlc_id)" );
                    }

                    cursor.close();
                }
            }
        }

        bulkInsertWithUri( DatabaseContract.CategoryEntry.CONTENT_URI, vector );
    }

    void insertEngrams( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );
        Vector<ContentValues> compositionVector = new Vector<>();

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long _id = jsonObject.getLong( DatabaseContract.EngramEntry._ID );
            String name = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_NAME );
            String description = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION );
            String drawable = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DRAWABLE );
            int yield = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_YIELD );
            int level = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_LEVEL );
            long category_id = jsonObject.getLong( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY );
            JSONArray station_ids = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_STATION_KEY );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_DLC_KEY );

            //Log.w( TAG, "-- insertEngrams > " + name );

            // first go through dlc_ids...
            for ( int d = 0; d < dlc_ids.length(); d++ ) {
                long dlc_id = dlc_ids.getLong( d );

                // ...while going through station_ids
                for ( int s = 0; s < station_ids.length(); s++ ) {
                    long station_id = station_ids.getLong( s );

                    //Log.d( TAG, "   -? dlc_id: " + dlc_id + ", station_id: " + station_id );

                    // check if dlc_id and station_id's dlc_id match
                    Cursor cursor = getContentResolver().query(
                            DatabaseContract.StationEntry.buildUriWithId( dlc_id, station_id ),
                            null, null, null, null );

                    // no match found, try next station_id
                    if ( cursor == null ) {
                        //Log.e( TAG, "   -! cursor == null! (possibly because it doesn't belong in requested dlc_id)" );
                        //Log.e( TAG, "       > drawable: " + drawable );
                        continue;
                    }

                    // match found, adding values into vector
                    if ( cursor.moveToFirst() ) {
                        ContentValues values = new ContentValues();
                        values.put( DatabaseContract.EngramEntry._ID, _id );
                        values.put( DatabaseContract.EngramEntry.COLUMN_NAME, name );
                        values.put( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, description );
                        values.put( DatabaseContract.EngramEntry.COLUMN_DRAWABLE, drawable );
                        values.put( DatabaseContract.EngramEntry.COLUMN_YIELD, yield );
                        values.put( DatabaseContract.EngramEntry.COLUMN_LEVEL, level );
                        values.put( DatabaseContract.EngramEntry.COLUMN_STATION_KEY, station_id );
                        values.put( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, category_id );
                        values.put( DatabaseContract.EngramEntry.COLUMN_DLC_KEY, dlc_id );

                        vector.add( values );

                        compositionVector.addAll( insertComposition( _id, dlc_id, jsonObject.getJSONArray( DatabaseContract.CompositionEntry.TABLE_NAME ) ) );

                        //Log.w( TAG, "-- insertEngrams Success!" );
                    } else {
                        //Log.e( TAG, "   -! moveToFirst() did not fire! (possibly because it wasn't added with proper dlc_id)" );
                        //Log.e( TAG, "       > drawable: " + drawable );
                    }

                    cursor.close();
                }
            }
        }

        bulkInsertWithUri( DatabaseContract.EngramEntry.CONTENT_URI, vector );
        bulkInsertWithUri( DatabaseContract.CompositionEntry.CONTENT_URI, compositionVector );
    }

    Vector<ContentValues> insertComposition( long engram_id, long dlc_id, JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

        //Log.d( TAG, "   -- insertComposition > engram_id: " + engram_id );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            // Drawable of Resource that will tie itself to this Composition
            long resource_id = jsonObject.getLong( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY );
            int quantity = jsonObject.getInt( DatabaseContract.CompositionEntry.COLUMN_QUANTITY );

            ContentValues values = new ContentValues();
            values.put( DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY, engram_id );
            values.put( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resource_id );
            values.put( DatabaseContract.CompositionEntry.COLUMN_DLC_KEY, dlc_id );
            values.put( DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity );

            //Log.d( TAG, "      +> engram_id: " + engram_id + ", resource_id: " + resource_id + ", dlc_id: " + dlc_id + ", quantity: " + quantity );

            vector.add( values );
        }

//            bulkInsertWithUri( DatabaseContract.CompositionEntry.CONTENT_URI, vector );
        return vector;
    }
}
