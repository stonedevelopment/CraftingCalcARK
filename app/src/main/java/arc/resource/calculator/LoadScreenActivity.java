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

            publishProgress( "Inserting Versions..." );
            insertVersions( jsonObject.getJSONArray( DatabaseContract.DLCEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Categories..." );
            insertCategories( jsonObject.getJSONArray( DatabaseContract.CategoryEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Resources..." );
            insertResources( jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Engrams... (Takes the longest to load.)" );
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
                    long engramId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
                    long resourceId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engramId );
                    values.put( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resourceId );

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
                long dlc_id = jsonObject.getLong( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, drawable );
                values.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, dlc_id );

                vector.add( values );
            }

            bulkInsertWithUri( DatabaseContract.ResourceEntry.CONTENT_URI, vector );
        }

        void insertCategories( JSONArray jsonArray ) throws JSONException {
            Vector<ContentValues> vector = new Vector<>( jsonArray.length() );

            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                long _id = jsonObject.getLong( DatabaseContract.CategoryEntry._ID );
                String name = jsonObject.getString( DatabaseContract.CategoryEntry.COLUMN_NAME );
                long parent_id = jsonObject.getLong( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY );
                long dlc_id = jsonObject.getLong( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.CategoryEntry._ID, _id );
                values.put( DatabaseContract.CategoryEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY, parent_id );
                values.put( DatabaseContract.CategoryEntry.COLUMN_DLC_KEY, dlc_id );

                Helper.Log( TAG, values.toString() );

                vector.add( values );
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
                long category_id = jsonObject.getLong( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY );
                long dlc_id = jsonObject.getLong( DatabaseContract.EngramEntry.COLUMN_DLC_KEY );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.EngramEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, description );
                values.put( DatabaseContract.EngramEntry.COLUMN_DRAWABLE, drawable );
                values.put( DatabaseContract.EngramEntry.COLUMN_YIELD, yield );
                values.put( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, category_id );
                values.put( DatabaseContract.EngramEntry.COLUMN_DLC_KEY, dlc_id );

                Uri insertUri = getContext().getContentResolver().insert( DatabaseContract.EngramEntry.CONTENT_URI, values );
                long engramId = DatabaseContract.getIdFromUri( insertUri );

//                Log.v( TAG, "** insertComposition: " + values.toString() );

                insertComposition( engramId, jsonObject.getJSONArray( DatabaseContract.CompositionEntry.TABLE_NAME ) );
            }
        }

        void insertComposition( long engram_id, JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                // Drawable of Resource that will tie itself to this Composition
                String drawable = jsonObject.getString( DatabaseContract.CompositionEntry.COLUMN_DRAWABLE );
                int quantity = jsonObject.getInt( DatabaseContract.CompositionEntry.COLUMN_QUANTITY );

                Cursor cursor = getContext().getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithDrawable( drawable ),
                        null, null, null, null
                );

                if ( cursor == null ) {
                    continue;
                }

                long resource_id = 0;
                if ( cursor.moveToFirst() ) {
                    resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );

                    cursor.close();
                } else {
                    Log.e( TAG, " -! Resource not found, adding Engram as resource, drawable: " + drawable );

                    cursor = getContext().getContentResolver().query(
                            DatabaseContract.EngramEntry.buildUriWithDrawable( drawable ),
                            null, null, null, null
                    );

                    if ( cursor == null ) {
                        Log.e( TAG, "   -- cursor == null" );
                        continue;
                    }

                    if ( cursor.moveToFirst() ) {
                        String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
                        long dlc_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DLC_KEY ) );

                        cursor.close();

                        // Add Engram as resource
                        ContentValues values = new ContentValues();
                        values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                        values.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, drawable );
                        values.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, dlc_id );

//                        Log.v( TAG, "   -- " + values.toString() );

                        Uri uri = getContext().getContentResolver().insert( DatabaseContract.ResourceEntry.CONTENT_URI, values );
                        resource_id = DatabaseContract.getIdFromUri( uri );
                    } else {
                        Log.e( TAG, "   -- moveToFirst() did not fire" );
                    }
                }

                if ( resource_id > 0 ) {
                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY, engram_id );
                    values.put( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resource_id );
                    values.put( DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity );

                    getContext().getContentResolver().insert( DatabaseContract.CompositionEntry.CONTENT_URI, values );
                } else {
                    Log.e( TAG, "   -- insertComposition Failed: engram_id: " + engram_id + ", drawable: " + drawable );
                }
            }
        }

        Context getContext() {
            return mContext;
        }
    }
}
