package arc.resource.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.Helper;
import arc.resource.calculator.util.JsonUtil;
import arc.resource.calculator.util.PrefsUtil;

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
public class LoadScreenActivity extends AppCompatActivity implements UpdateReceiver.Receiver {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    private UpdateReceiver mReceiver;

    private boolean mIsActive;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_load_screen );

        mIsActive = true;

        if ( isNewVersion() ) {
            mReceiver = new UpdateReceiver( new Handler() );
            mReceiver.setReceiver( this );

            Intent intent = new Intent( this, UpdateService.class );
            intent.putExtra( UpdateService.PARAM_REQUEST_ID, 100 );
            intent.putExtra( UpdateService.PARAM_TIME, System.currentTimeMillis() );
            intent.putExtra( UpdateService.PARAM_RECEIVER, mReceiver );
            startService( intent );

            AdUtil.loadAdView( this );
        } else {
            startMainActivity( false );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActive = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsActive = false;
    }

    void startMainActivity( boolean didUpdate ) {
        if ( mIsActive ) {
            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
            intent.putExtra(
                    getString( R.string.intent_key_did_update ),
                    didUpdate );

            startActivity( intent );

            finish();
        }
    }

    boolean isNewVersion() {
        String oldVersion = new PrefsUtil( this ).getJSONVersion();
        String newVersion = getString( R.string.json_version );

        Helper.Log( TAG, oldVersion + " == " + newVersion + "?" );

        return !Objects.equals( oldVersion, newVersion );
    }

    @Override
    public void onReceiveResult( int resultCode, Bundle resultData ) {
        long startTime = resultData.getLong( UpdateService.PARAM_TIME );

        String message;
        switch ( resultCode ) {
            case UpdateService.STATUS_STARTED:
                message = getString( R.string.load_activity_status_message_started );
                break;

            case UpdateService.STATUS_UPDATING:
                message = getString( R.string.load_activity_status_message_updating );
                break;

            case UpdateService.STATUS_FINISHED:
                message = getString( R.string.load_activity_status_message_finished );

                new Timer( TAG ).schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                postExecution();
                            }
                        },
                        1000
                );
                break;

            case UpdateService.STATUS_ERROR:
                message = String.format(
                        getString( R.string.load_activity_status_message_error_format ),
                        resultData.getString( UpdateService.PARAM_MESSAGE ) );
                break;

            default:
                message = "Unknown message code: " + resultCode + ", " + resultData.toString();
        }

        UpdateStatusText( startTime, message );
    }

    void UpdateStatusText( long startTime, String message ) {
        TextView status = ( TextView ) findViewById( R.id.status );
        status.setText( message );
    }

    void postExecution() {
        // Save new version to preferences
        PrefsUtil prefs = new PrefsUtil( this );

        prefs.updateJSONVersion( getString( R.string.json_version ) );
        prefs.saveStationIdBackToDefault();
        prefs.saveCategoryLevelsBackToDefault();

        startMainActivity( true );
    }

    private class ParseInsertTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = ParseInsertTask.class.getSimpleName();

        private Context mContext;

        private ProgressBar mProgressBar;
        private TextView mStatus;
        private Timer mTimer;
        private TimerTask mTimerTask;

        private String mStatusText;
        private long mStartTime;

        ParseInsertTask( Context context, ProgressBar progressBar, TextView status ) {
            mContext = context;
            mProgressBar = progressBar;
            mStatus = status;
        }

        @Override
        protected void onPreExecute() {
            deleteAllRecordsFromProvider();

            mProgressBar.setMax( 1 );
            mProgressBar.setProgress( 0 );

            mStartTime = System.currentTimeMillis();
            mStatusText = "New version found, initializing..";

            mTimer = new Timer( getString( R.string.key_timer ) );
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    publishProgress();
                }
            };

            mTimer.scheduleAtFixedRate( mTimerTask, 100, 100 );
        }

        @Override
        protected void onProgressUpdate( Void... params ) {
            mStatus.setText( formatStatus( mStatusText ) );
        }

        String formatStatus( String message ) {
            long elapsed = System.currentTimeMillis() - mStartTime;
            String elapsedTime = DateUtils.formatElapsedTime( elapsed / 1000 );

            return String.format(
                    getString( R.string.load_activity_status_message_format ),
                    message,
                    elapsedTime );
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            // Save new version to preferences
            PrefsUtil prefs = new PrefsUtil( getContext() );

            prefs.updateJSONVersion( getString( R.string.json_version ) );
            prefs.saveStationIdBackToDefault();
            prefs.saveCategoryLevelsBackToDefault();

            startMainActivity( true );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            mStatusText = formatStatus( "Parsing JSON file..." );

            String jsonString = JsonUtil.readRawJsonFileToJsonString( getContext() );

            try {
                parseJsonString( jsonString );
            } catch ( JSONException e ) {
                e.printStackTrace();
            }
            return null;
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
            getContext().getContentResolver().delete( uri, null, null );
        }

        void parseJsonString( String jsonString ) throws JSONException {
            JSONObject jsonObject = new JSONObject( jsonString );

            insertDLC( jsonObject.getJSONArray( DatabaseContract.DLCEntry.TABLE_NAME ) );
            insertStations( jsonObject.getJSONArray( DatabaseContract.StationEntry.TABLE_NAME ) );
            insertCategories( jsonObject.getJSONArray( DatabaseContract.CategoryEntry.TABLE_NAME ) );
            insertResources( jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME ) );
            insertEngrams( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) );
            insertComplexResources( jsonObject.getJSONArray( DatabaseContract.ComplexResourceEntry.TABLE_NAME ) );

            mStatusText = formatStatus( "Initialization complete! Starting app..." );
        }

        void bulkInsertWithUri( Uri insertUri, Vector<ContentValues> vector ) {
            ContentValues[] contentValues = new ContentValues[vector.size()];

            getContext().getContentResolver().bulkInsert( insertUri, vector.toArray( contentValues ) );
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

            mStatusText = "Inserting " + vector.size() + " Complex Resources..";
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

            mStatusText = "Inserting " + vector.size() + " DLC versions...";
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

            mStatusText = "Inserting " + vector.size() + " Resources...";
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

            mStatusText = "Inserting " + vector.size() + " Stations...";
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

            mStatusText = "Inserting " + vector.size() + " Categories...";
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

            mStatusText = "Inserting " + vector.size() + " Engrams...";
            bulkInsertWithUri( DatabaseContract.EngramEntry.CONTENT_URI, vector );

            mStatusText = "Inserting " + compositionVector.size() + " Engram Compositions...";
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

        Context getContext() {
            return mContext;
        }
    }
}
