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
package arc.resource.calculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.SendErrorReportListener;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.JsonUtil;
import arc.resource.calculator.util.ListenerUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;

public class LoadScreenActivity extends AppCompatActivity
        implements SendErrorReportListener {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    private static final String STATUS_ERROR = "STATUS_ERROR";
    private static final String STATUS_ERROR_WITH_MESSAGE = "STATUS_ERROR_WITH_MESSAGE";
    private static final String STATUS_STARTED = "STATUS_STARTED";
    private static final String STATUS_UPDATING = "STATUS_UPDATING";
    private static final String STATUS_UPDATING_WITH_MESSAGE = "STATUS_UPDATING_WITH_MESSAGE";
    private static final String STATUS_FINISHED = "STATUS_FINISHED";

    private ListenerUtil mCallback;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_load_screen );

        mCallback = ListenerUtil.getInstance();
        mCallback.setSendErrorReportListener( this );

        if ( isNewVersion() ) {
            new InitializationTask().execute();
        } else {
            startMainActivity( false );
        }
    }

    private void startMainActivity( boolean didUpdate ) {
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
        intent.putExtra( getString( R.string.intent_key_did_update ), didUpdate );

        startActivity( intent );

        finish();
    }

    private boolean isNewVersion() {
        String oldVersion = new PrefsUtil( this ).getJSONVersion();
        String newVersion = getString( R.string.json_version );

        Util.Log( TAG, oldVersion + " == " + newVersion + "?" );

        return !Objects.equals( oldVersion, newVersion );
    }

    private void UpdateStatusText( String message ) {
        TextView status = ( TextView ) findViewById( R.id.status );
        status.append( "\n" + message );
    }

    private void postExecution() {
        // Save new version to preferences
        PrefsUtil prefs = new PrefsUtil( this );

        prefs.updateJSONVersion( getString( R.string.json_version ) );
        prefs.saveStationIdBackToDefault();
        prefs.saveCategoryLevelsBackToDefault();

        startMainActivity( true );
    }

    @Override
    public void onSendErrorReport( final String tag, final Exception e, boolean showAlertDialog ) {
        if ( showAlertDialog )
            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    ExceptionUtil.SendErrorReportWithAlertDialog( LoadScreenActivity.this, tag, e );
                }
            } );
        else
            ExceptionUtil.SendErrorReport( LoadScreenActivity.this, tag, e );
    }

    private class InitializationTask extends AsyncTask<Void, String, Void> {

        private LongSparseArray<TotalConversion> mTotalConversion;
        private SparseArray<Long> mDlcIds;

        private boolean mHandledError;

        InitializationTask() {
            mTotalConversion = new LongSparseArray<>();
            mDlcIds = new SparseArray<>();

            mHandledError = false;
        }

        @Override
        protected void onProgressUpdate( String... values ) {
            String progressCode = values[0];
            String messageExtra = values.length > 1 ? values[1] : null;

            String message;
            switch ( progressCode ) {
                case STATUS_STARTED:
                    message = getString( R.string.load_activity_status_message_started );
                    break;

                case STATUS_UPDATING:
                    message = getString( R.string.load_activity_status_message_updating );
                    break;

                case STATUS_UPDATING_WITH_MESSAGE:
                    message = messageExtra;
                    break;

                case STATUS_FINISHED:
                    message = getString( R.string.load_activity_status_message_finished );
                    break;

                case STATUS_ERROR:
                    message = getString( R.string.load_activity_status_message_error );
                    break;

                case STATUS_ERROR_WITH_MESSAGE:
                    message = String.format(
                            getString( R.string.load_activity_status_message_error_format ), messageExtra );
                    break;

                default:
                    message = String.format(
                            getString( R.string.load_activity_status_message_unknown_format ), progressCode );
            }

            UpdateStatusText( message );
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            if ( !mHandledError ) {
                new Timer( TAG ).schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                postExecution();
                            }
                        },
                        1000
                );
            }
        }

        @Override
        protected Void doInBackground( Void... params ) {
            // remove all records from database
            deleteAllRecordsFromProvider();

            try {
                // Read local json file into a string
                String jsonString = JsonUtil.readRawJsonFileToJsonString( getApplicationContext(), R.raw.data_finalized );

                // Tell user that database initialization is in progress
                sendStatusUpdate( STATUS_UPDATING );

                JSONObject jsonObject = new JSONObject( jsonString );

                mTotalConversion = mapTotalConversion( jsonObject.getJSONArray( DatabaseContract.TotalConversionEntry.TABLE_NAME ) );

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
                mHandledError = true;

                sendStatusErrorWithMessage( e );
                sendStatusError();

                mCallback.emitSendErrorReportWithAlertDialog( TAG, e );
            }

            return null;
        }

        private void sendStatusError() {
            sendStatusUpdate( STATUS_ERROR );
        }

        private void sendStatusErrorWithMessage( Exception e ) {
            sendStatusUpdate( STATUS_ERROR_WITH_MESSAGE, e.getLocalizedMessage() );
        }

        private void sendStatusUpdateWithMessage( String messageExtra ) {
            sendStatusUpdate( STATUS_UPDATING_WITH_MESSAGE, messageExtra );
        }

        private void sendStatusUpdate( String statusCode ) {
            publishProgress( statusCode );
        }

        private void sendStatusUpdate( String statusCode, String messageExtra ) {
            publishProgress( statusCode, messageExtra );
        }

        private int delete( Uri uri ) {
            return getContentResolver().delete( uri, null, null );
        }

        private void deleteAllRecordsFromProvider() {
            delete( DatabaseContract.DLCEntry.CONTENT_URI );
            delete( DatabaseContract.ResourceEntry.CONTENT_URI );
            delete( DatabaseContract.ComplexResourceEntry.CONTENT_URI );
            delete( DatabaseContract.CompositionEntry.CONTENT_URI );
            delete( DatabaseContract.CategoryEntry.CONTENT_URI );
            delete( DatabaseContract.StationEntry.CONTENT_URI );
            delete( DatabaseContract.EngramEntry.CONTENT_URI );
            delete( DatabaseContract.QueueEntry.CONTENT_URI );
        }

        private void bulkInsertWithUri( Uri insertUri, Vector<ContentValues> vector ) {
            ContentValues[] contentValues = new ContentValues[vector.size()];

            getContentResolver().bulkInsert( insertUri, vector.toArray( contentValues ) );
        }

        private void insertComplexResources( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Inserting " + jsonArray.length() + " Complex Resources.." );

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

        private void insertDLC( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Inserting " + jsonArray.length() + " DLC Versions.." );

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

        private void insertResources( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Inserting " + jsonArray.length() + " Resources.." );

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

        private void insertStations( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Inserting " + jsonArray.length() + " Stations.." );

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

        private void insertCategories( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Inserting " + jsonArray.length() + " Categories.." );

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

        private void insertEngrams( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Inserting " + jsonArray.length() + " Engrams.." );

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

        private Vector<ContentValues> insertComposition( long engram_id, long dlc_id, JSONArray jsonArray ) throws JSONException {
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

        private LongSparseArray<TotalConversion> mapTotalConversion( JSONArray jsonArray ) throws JSONException {
            sendStatusUpdateWithMessage( "Mapping Total Conversion entries.." );

            LongSparseArray<TotalConversion> map = new LongSparseArray<>();

            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                long dlc_id = jsonObject.getLong( DatabaseContract.TotalConversionEntry.COLUMN_DLC_KEY );
                JSONArray resourceArray = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME );

                TotalConversion totalConversion = new TotalConversion();
                for ( int j = 0; j < resourceArray.length(); j++ ) {
                    JSONObject object = resourceArray.getJSONObject( j );

                    long from = object.getLong( DatabaseContract.TotalConversionEntry.COLUMN_FROM );
                    long to = object.getLong( DatabaseContract.TotalConversionEntry.COLUMN_TO );

                    totalConversion.conversionIds.put( from, to );
                }

                map.put( dlc_id, totalConversion );
            }

            return map;
        }

        private boolean hasConversion( long dlc_id, long resource_id ) {
            return mTotalConversion.get( dlc_id ) != null &&
                    mTotalConversion.get( dlc_id ).conversionIds.get( resource_id ) != null;
        }

        private long getConversion( long dlc_id, long resource_id ) {
            return mTotalConversion.get( dlc_id ).conversionIds.get( resource_id );
        }

        private class TotalConversion {
            LongSparseArray<Long> conversionIds;

            TotalConversion() {
                conversionIds = new LongSparseArray<>();
            }
        }
    }
}