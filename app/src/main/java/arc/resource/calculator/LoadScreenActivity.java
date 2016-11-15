package arc.resource.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Vector;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.helpers.PreferenceHelper;

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
public class LoadScreenActivity extends AppCompatActivity {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    TextView textView;
    ProgressBar progressBar;

    static class ProgressData {
        private int progress;
        private int max;

        ProgressData( int progress, int max ) {
            this.progress = progress;
            this.max = max;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress( int progress ) {
            this.progress = progress;
        }

        public int getMax() {
            return max;
        }

        public void setMax( int max ) {
            this.max = max;
        }

        public void incrementProgress() {
            progress++;
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_load_screen );

        textView = ( TextView ) findViewById( R.id.textView_status );
        progressBar = ( ProgressBar ) findViewById( R.id.progressBar );

        if ( isNewVersion() ) {
            new ParseInsertTask( this ).execute();
        } else {
            // Start MainActivity
            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
            startActivity( intent );

            finish();
        }
    }

    boolean isNewVersion() {
        String oldVersion = new PreferenceHelper( this ).getStringPreference( getResources().getString( R.string.pref_json_version ) );
        String newVersion = getResources().getString( R.string.json_version );

        Helper.Log( TAG, oldVersion + " == " + newVersion + "?" );

        return !Objects.equals( oldVersion, newVersion );
    }

    private class ParseInsertTask extends AsyncTask<Void, String, Void> {
        private final String TAG = ParseInsertTask.class.getSimpleName();

        private Context mContext;

        private ProgressData mProgressData;

        ParseInsertTask( Context context ) {
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            deleteAllRecordsFromProvider();

            mProgressData = new ProgressData( 0, 6 );

            progressBar.setMax( mProgressData.getMax() );
            progressBar.setProgress( mProgressData.getProgress() );

        }

        @Override
        protected void onProgressUpdate( String... strings ) {
            String message = strings[0];

            Helper.Log( TAG, message );
            textView.setText( message );
            progressBar.setProgress( mProgressData.getProgress() );
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            // Save new version to preferences
            new PreferenceHelper( getContext() )
                    .setPreference(
                            getString( R.string.pref_json_version ),
                            getString( R.string.json_version ) );

            // Start MainActivity
            Intent intent = new Intent( getContext(), MainActivity.class );
            startActivity( intent );

            finish();
        }

        @Override
        protected Void doInBackground( Void... params ) {
            publishProgress( "Parsing JSON file..." );
            mProgressData.incrementProgress();

            BufferedReader fileReader = null;
            String jsonString;

            try {
                InputStream fileStream = getContext().getResources().openRawResource( R.raw.jsonrawdata );
                StringBuffer buffer = new StringBuffer();

                fileReader = new BufferedReader( new InputStreamReader( fileStream ) );

                String line;
                while ( ( line = fileReader.readLine() ) != null ) {
                    buffer.append( line + "\n" );
                }

                // If empty string, no need to parse.
                if ( buffer.length() == 0 ) {
                    return null;
                }

                jsonString = buffer.toString();

            } catch ( IOException e ) {
                Log.e( TAG, "Error: ", e );
                return null;
            } finally {
                if ( fileReader != null ) {
                    try {
                        fileReader.close();
                    } catch ( IOException e ) {
                        Log.e( TAG, "Error closing stream: ", e );
                        return null;
                    }
                }
            }

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
            delete( DatabaseContract.EngramEntry.CONTENT_URI );
            delete( DatabaseContract.QueueEntry.CONTENT_URI );
        }

        void delete( Uri uri ) {
            getContext().getContentResolver().delete( uri, null, null );
        }

        void parseJsonString( String jsonString ) throws JSONException {
            JSONObject jsonObject = new JSONObject( jsonString );

            publishProgress( "Inserting DLC Versions..." );
            insertVersions( jsonObject.getJSONArray( DatabaseContract.DLCEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Stations..." );
            insertStations( jsonObject.getJSONArray( DatabaseContract.StationEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Categories..." );
            insertCategories( jsonObject.getJSONArray( DatabaseContract.CategoryEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Resources..." );
            insertResources( jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Engrams... (This may take awhile...)" );
            insertEngrams( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Complex Resources..." );
            insertComplexResources();
            mProgressData.incrementProgress();

            publishProgress( "Initialization complete! Starting app..." );
        }

        void bulkInsertWithUri( Uri insertUri, Vector<ContentValues> vector ) {
            ContentValues[] contentValues = new ContentValues[vector.size()];

            getContext().getContentResolver().bulkInsert( insertUri, vector.toArray( contentValues ) );
        }

        void insertComplexResources() {
            Cursor cursor = mContext.getContentResolver().query(
                    DatabaseContract.ComplexResourceEntry.buildUriWithDrawable(),
                    DatabaseContract.ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_PROJECTION,
                    DatabaseContract.ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_SELECTION,
                    null, null
            );

            if ( cursor != null && cursor.getCount() > 0 ) {
                Vector<ContentValues> vector = new Vector<>( cursor.getCount() );

                while ( cursor.moveToNext() ) {
                    long engram_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
                    long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engram_id );
                    values.put( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resource_id );

                    vector.add( values );
                }

                cursor.close();
                bulkInsertWithUri( DatabaseContract.ComplexResourceEntry.CONTENT_URI, vector );
            }
        }

        void insertVersions( JSONArray jsonArray ) throws JSONException {
            Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                long _id = jsonObject.getLong( DatabaseContract.DLCEntry._ID );
                String name = jsonObject.getString( DatabaseContract.DLCEntry.COLUMN_NAME );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.DLCEntry._ID, _id );
                values.put( DatabaseContract.DLCEntry.COLUMN_NAME, name );

                vector.add( values );
            }

            bulkInsertWithUri( DatabaseContract.DLCEntry.CONTENT_URI, vector );
        }

        void insertResources( JSONArray jsonArray ) throws JSONException {
            Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                String name = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_NAME );
                String drawable = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE );
                JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY );

                for ( int d = 0; d < dlc_ids.length(); d++ ) {
                    long dlc_id = dlc_ids.getLong( d );

                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                    values.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, drawable );
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
                String drawable = jsonObject.getString( DatabaseContract.StationEntry.COLUMN_DRAWABLE );
                JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.StationEntry.COLUMN_DLC_KEY );

                for ( int d = 0; d < dlc_ids.length(); d++ ) {
                    long dlc_id = dlc_ids.getLong( d );

                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.StationEntry._ID, _id );
                    values.put( DatabaseContract.StationEntry.COLUMN_NAME, name );
                    values.put( DatabaseContract.StationEntry.COLUMN_DRAWABLE, drawable );
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

                // first go through dlc_ids...
                for ( int d = 0; d <= dlc_ids.length(); d++ ) {
                    long dlc_id = dlc_ids.getLong( d );

                    // ...while going through station_ids
                    for ( int s = 0; s <= station_ids.length(); s++ ) {
                        long station_id = station_ids.getLong( s );

                        // check if dlc_id and station_id's dlc_id match
                        Cursor cursor = getContentResolver().query(
                                DatabaseContract.StationEntry.buildUriWithId( station_id, dlc_id ),
                                null, null, null, null );

                        // no match found, try next station_id
                        if ( cursor == null ) {
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
                        }

                        cursor.close();
                    }
                }
            }

            bulkInsertWithUri( DatabaseContract.CategoryEntry.CONTENT_URI, vector );
        }

        void insertEngrams( JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                String name = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_NAME );
                String description = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION );
                String drawable = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DRAWABLE );
                int yield = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_YIELD );
                int level = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_LEVEL );
                long category_id = jsonObject.getLong( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY );
                JSONArray station_ids = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_STATION_KEY );
                JSONArray dlc_ids = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_DLC_KEY );

                // first go through dlc_ids...
                for ( int d = 0; d <= dlc_ids.length(); d++ ) {
                    long dlc_id = dlc_ids.getLong( d );

                    // ...while going through station_ids
                    for ( int s = 0; s <= station_ids.length(); s++ ) {
                        long station_id = station_ids.getLong( s );

                        // check if dlc_id and station_id's dlc_id match
                        Cursor cursor = getContentResolver().query(
                                DatabaseContract.StationEntry.buildUriWithId( station_id, dlc_id ),
                                null, null, null, null );

                        // no match found, try next station_id
                        if ( cursor == null ) {
                            continue;
                        }

                        // match found, adding values into vector
                        if ( cursor.moveToFirst() ) {
                            ContentValues values = new ContentValues();
                            values.put( DatabaseContract.EngramEntry.COLUMN_NAME, name );
                            values.put( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, description );
                            values.put( DatabaseContract.EngramEntry.COLUMN_DRAWABLE, drawable );
                            values.put( DatabaseContract.EngramEntry.COLUMN_YIELD, yield );
                            values.put( DatabaseContract.EngramEntry.COLUMN_LEVEL, level );
                            values.put( DatabaseContract.EngramEntry.COLUMN_STATION_KEY, station_id );
                            values.put( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, category_id );
                            values.put( DatabaseContract.EngramEntry.COLUMN_DLC_KEY, dlc_id );

                            Uri insertUri = getContext().getContentResolver().insert( DatabaseContract.EngramEntry.CONTENT_URI, values );
                            long engram_id = DatabaseContract.getIdFromUri( insertUri );

                            insertComposition( engram_id, dlc_id, jsonObject.getJSONArray( DatabaseContract.CompositionEntry.TABLE_NAME ) );
                        }

                        cursor.close();
                    }
                }
            }
        }

        void insertComposition( long engram_id, long dlc_id, JSONArray jsonArray ) throws JSONException {
            Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                // Drawable of Resource that will tie itself to this Composition
                String drawable = jsonObject.getString( DatabaseContract.CompositionEntry.COLUMN_DRAWABLE );
                int quantity = jsonObject.getInt( DatabaseContract.CompositionEntry.COLUMN_QUANTITY );

                Cursor cursor = getContext().getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithDrawable( drawable, dlc_id ),
                        null, null, null, null
                );

                if ( cursor == null ) {
                    continue;
                }

                long resource_id = 0;
                if ( cursor.moveToFirst() ) {
                    resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );
                } else {
                    Log.e( TAG, " -! Resource not found, adding Engram as resource, drawable: " + drawable + ", dlc: " + dlc_id );

                    cursor = getContext().getContentResolver().query(
                            DatabaseContract.EngramEntry.buildUriWithDrawable( drawable, dlc_id ),
                            null, null, null, null
                    );

                    if ( cursor == null ) {
                        Log.e( TAG, "   -! cursor == null! (possibly because it wasn't added with proper dlc_id)" );
                        continue;
                    }

                    if ( cursor.moveToFirst() ) {
                        String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );

                        // Add Engram as resource
                        ContentValues values = new ContentValues();
                        values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                        values.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, drawable );
                        values.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, dlc_id );

                        Uri uri = getContext().getContentResolver().insert( DatabaseContract.ResourceEntry.CONTENT_URI, values );
                        resource_id = DatabaseContract.getIdFromUri( uri );
                    } else {
                        Log.e( TAG, "   -! moveToFirst() did not fire!  (possibly because it wasn't added with proper dlc_id)" );
                    }
                }

                if ( resource_id > 0 ) {
                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY, engram_id );
                    values.put( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resource_id );
                    values.put( DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity );

                    vector.add( values );
                } else {
                    Log.e( TAG, "   -- insertComposition Failed: engram_id: " + engram_id + ", drawable: " + drawable );
                }

                cursor.close();
            }

            // Bulk insert current composition
            if ( vector.size() > 0 ) {
                getContext().getContentResolver().bulkInsert( DatabaseContract.CompositionEntry.CONTENT_URI, ( ContentValues[] ) vector.toArray() );
            } else {
                Log.e( TAG, "-! Vector is empty for engram_id: " + engram_id + "/" + jsonArray.toString() );
            }
        }

        Context getContext() {
            return mContext;
        }
    }
}
