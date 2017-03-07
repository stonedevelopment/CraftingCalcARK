package arc.resource.calculator.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.util.JsonUtil;

import static arc.resource.calculator.service.ServiceUtil.PARAM_MESSAGE;
import static arc.resource.calculator.service.ServiceUtil.PARAM_RECEIVER;
import static arc.resource.calculator.service.ServiceUtil.STATUS_ERROR;
import static arc.resource.calculator.service.ServiceUtil.STATUS_FINISHED;
import static arc.resource.calculator.service.ServiceUtil.STATUS_STARTED;
import static arc.resource.calculator.service.ServiceUtil.STATUS_UPDATING;

public class InitializationService extends IntentService {
    private static final String TAG = InitializationService.class.getSimpleName();

    private Bundle mBundle;
    private ResultReceiver mReceiver;

    private LongSparseArray<TotalConversion> mTotalConversion;
    private SparseArray<Long> mDlcIds = new SparseArray<>();

    public InitializationService() {
        super( TAG );
    }

    @Override
    protected void onHandleIntent( Intent intent ) {
        // Register receiver to receive progress signals
        mReceiver = intent.getParcelableExtra( PARAM_RECEIVER );

        // Fill bundle with data used to display status
        mBundle = new Bundle();

        // Send an update to LoadActivity to tell user that initialization has started!
        sendStatusUpdate( STATUS_STARTED );

        // removeQueueById all records from database
        deleteAllRecordsFromProvider();

        try {
            // Read local json file into a string
            String jsonString = JsonUtil.readRawJsonFileToJsonString( this, R.raw.data_finalized );

            // Tell user that database initialization is in progress
            sendStatusUpdate( STATUS_UPDATING );

            JSONObject jsonObject = new JSONObject( jsonString );

            mTotalConversion = mapTotalConversion( jsonObject.getJSONArray( DatabaseContract.TotalConversionEntry.TABLE_NAME ) );

            // ids are added in insertDLC method
            mDlcIds = new SparseArray<>();

            insertDLC( jsonObject.getJSONArray( DatabaseContract.DLCEntry.TABLE_NAME ) );
            insertStations( jsonObject.getJSONArray( DatabaseContract.StationEntry.TABLE_NAME ) );
            insertCategories( jsonObject.getJSONArray( DatabaseContract.CategoryEntry.TABLE_NAME ) );
            insertResources( jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME ) );
            insertEngrams( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) );
            insertComplexResources( jsonObject.getJSONArray( DatabaseContract.ComplexResourceEntry.TABLE_NAME ) );

            // Tell user that database initialization has completed.
            sendStatusUpdate( STATUS_FINISHED );
        }

        // If error, send error signal, stop service
        catch ( Exception e ) {
            mBundle.putString( PARAM_MESSAGE, e.getLocalizedMessage() );
            sendStatusUpdate( STATUS_ERROR );

            e.printStackTrace();
        }

        stopSelf();
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
        int rows = getContentResolver().delete( uri, null, null );
        Log.d( TAG, rows + "" );
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

            ContentValues values = new ContentValues();
            values.put( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resource_id );
            values.put( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engram_id );

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

            ContentValues values = new ContentValues();
            values.put( DatabaseContract.DLCEntry._ID, _id );
            values.put( DatabaseContract.DLCEntry.COLUMN_NAME, name );

            mDlcIds.put( mDlcIds.size(), _id );

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
            String imageFolder = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER );
            String imageFile = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY );

            for ( int j = 0; j < dlc_ids.length(); j++ ) {
                long dlc_id = dlc_ids.getLong( j );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.ResourceEntry._ID, _id );
                values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER, imageFolder );
                values.put( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE, imageFile );
                values.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, dlc_id );

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
            String imageFolder = jsonObject.getString( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER );
            String imageFile = jsonObject.getString( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.StationEntry.COLUMN_DLC_KEY );

            for ( int j = 0; j < dlc_ids.length(); j++ ) {
                long dlc_id = dlc_ids.getLong( j );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.StationEntry._ID, _id );
                values.put( DatabaseContract.StationEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER, imageFolder );
                values.put( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE, imageFile );
                values.put( DatabaseContract.StationEntry.COLUMN_DLC_KEY, dlc_id );

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

            // first check dlc_id
            for ( int j = 0; j < dlc_ids.length(); j++ ) {
                long dlc_id = dlc_ids.getLong( j );

                // against station_ids
                for ( int s = 0; s < station_ids.length(); s++ ) {
                    long station_id = station_ids.getLong( s );

                    // check if dlc_id and station_id's dlc_id match
                    Cursor cursor = getContentResolver().query(
                            DatabaseContract.StationEntry.buildUriWithId( dlc_id, station_id ),
                            null, null, null, null );

                    // no match found, try next station_id
                    if ( cursor == null )
                        continue;

                    // match found, adding values into vector
                    if ( cursor.moveToFirst() ) {
                        ContentValues values = new ContentValues();
                        values.put( DatabaseContract.CategoryEntry._ID, _id );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_NAME, name );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY, parent_id );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_STATION_KEY, station_id );
                        values.put( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY, dlc_id );

                        vector.add( values );
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
            String imageFolder = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER );
            String imageFile = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE );
            int yield = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_YIELD );
            int level = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_LEVEL );
            long category_id = jsonObject.getLong( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY );
            JSONArray station_ids = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_STATION_KEY );
            JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_DLC_KEY );

            // first check dlc_id
            for ( int j = 0; j < dlc_ids.length(); j++ ) {
                long dlc_id = dlc_ids.getLong( j );

                // against station_ids
                for ( int s = 0; s < station_ids.length(); s++ ) {
                    long station_id = station_ids.getLong( s );

                    // check if dlc_id and station_id's dlc_id match
                    Cursor cursor = getContentResolver().query(
                            DatabaseContract.StationEntry.buildUriWithId( dlc_id, station_id ),
                            null, null, null, null );

                    // no match found, try next station_id
                    if ( cursor == null )
                        continue;

                    // match found, add values into vector
                    if ( cursor.moveToFirst() ) {
                        ContentValues values = new ContentValues();
                        values.put( DatabaseContract.EngramEntry._ID, _id );
                        values.put( DatabaseContract.EngramEntry.COLUMN_NAME, name );
                        values.put( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, description );
                        values.put( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER, imageFolder );
                        values.put( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE, imageFile );
                        values.put( DatabaseContract.EngramEntry.COLUMN_YIELD, yield );
                        values.put( DatabaseContract.EngramEntry.COLUMN_LEVEL, level );
                        values.put( DatabaseContract.EngramEntry.COLUMN_STATION_KEY, station_id );
                        values.put( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, category_id );
                        values.put( DatabaseContract.EngramEntry.COLUMN_DLC_KEY, dlc_id );

                        vector.add( values );

                        compositionVector.addAll( insertComposition( _id, dlc_id, jsonObject.getJSONArray( DatabaseContract.CompositionEntry.TABLE_NAME ) ) );
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

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long resource_id = jsonObject.getLong( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY );
            int quantity = jsonObject.getInt( DatabaseContract.CompositionEntry.COLUMN_QUANTITY );

            // convert resource id if specific dlc version calls for it
            if ( hasConversion( dlc_id, resource_id ) )
                resource_id = getConversion( dlc_id, resource_id );

            ContentValues values = new ContentValues();
            values.put( DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY, engram_id );
            values.put( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resource_id );
            values.put( DatabaseContract.CompositionEntry.COLUMN_DLC_KEY, dlc_id );
            values.put( DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity );

            vector.add( values );
        }

        return vector;
    }

    boolean hasConversion( long dlc_id, long resource_id ) {
        return mTotalConversion.get( dlc_id ) != null &&
                mTotalConversion.get( dlc_id ).resources.get( resource_id ) != null;
    }

    long getConversion( long dlc_id, long resource_id ) {
        return mTotalConversion.get( dlc_id ).resources.get( resource_id );
    }

    LongSparseArray<TotalConversion> mapTotalConversion( JSONArray jsonArray ) throws JSONException {
        LongSparseArray<TotalConversion> map = new LongSparseArray<>();

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long dlc_id = jsonObject.getLong( DatabaseContract.TotalConversionEntry.COLUMN_DLC_KEY );
            JSONArray resourceArray = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME );
//            JSONArray engramArray = jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME );

            TotalConversion totalConversion = new TotalConversion();
            for ( int j = 0; j < resourceArray.length(); j++ ) {
                JSONObject object = resourceArray.getJSONObject( j );

                long from = object.getLong( DatabaseContract.TotalConversionEntry.COLUMN_FROM );
                long to = object.getLong( DatabaseContract.TotalConversionEntry.COLUMN_TO );

                totalConversion.resources.put( from, to );
            }

//            for ( int j = 0; j < engramArray.length(); j++ ) {
//                JSONObject object = engramArray.getJSONObject( j );
//
//                long from = object.getLong( DatabaseContract.TotalConversionEntry.COLUMN_FROM );
//                long to = object.getLong( DatabaseContract.TotalConversionEntry.COLUMN_TO );
//
//                totalConversion.engrams.put( from, to );
//            }

            map.put( dlc_id, totalConversion );
        }

        return map;
    }

    class TotalConversion {
        //        LongSparseArray<Long> engrams;
        LongSparseArray<Long> resources;

        TotalConversion() {
//            engrams = new LongSparseArray<>();
            resources = new LongSparseArray<>();
        }
    }
}
