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
            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
            startActivity( intent );

            finish();
        }
    }

    boolean isNewVersion() {
        String oldVersion = new PreferenceHelper( this ).getStringPreference( getString( R.string.pref_json_version ) );
        String newVersion = getResources().getString( R.string.json_version );

        Helper.Log( TAG, oldVersion + " = " + newVersion + "?" );

        if ( !Objects.equals( oldVersion, newVersion ) ) {
            new PreferenceHelper( this ).setPreference( getString( R.string.pref_json_version ), newVersion );
            return true;
        }

        // TODO: return false
        return true;
    }

    private class ParseInsertTask extends AsyncTask<Void, String, Void> {
        private final String TAG = ParseInsertTask.class.getSimpleName();

        private Context mContext;

        private ProgressData mProgressData;

        public ParseInsertTask( Context context ) {
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            deleteAllRecordsFromProvider();

            mProgressData = new ProgressData( 0, 5 );

            progressBar.setMax( mProgressData.getMax() );
            progressBar.setProgress( mProgressData.getProgress() );
        }

        @Override
        protected void onProgressUpdate( String... strings ) {
            textView.setText( strings[0] );
            progressBar.setProgress( mProgressData.getProgress() );
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            textView.setText( "Initialization complete! Starting app..." );
            progressBar.setProgress( mProgressData.getMax() );

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

            publishProgress( "Inserting Categories..." );
            insertCategories( jsonObject.getJSONArray( DatabaseContract.CategoryEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Resources..." );
            insertResources( jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Engrams..." );
            insertEngrams( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) );
            mProgressData.incrementProgress();

            publishProgress( "Inserting Complex Resources..." );
            insertComplexResources();
            mProgressData.incrementProgress();
        }

        void insertComplexResources() {
            Cursor cursor = mContext.getContentResolver().query(
                    DatabaseContract.ComplexResourceEntry.buildUriWithDrawable(),
                    DatabaseContract.ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_PROJECTION,
                    DatabaseContract.ComplexResourceEntry.SQL_QUERY_WITH_DRAWABLE_SELECTION,
                    null, null
            );

            if ( cursor != null && cursor.getCount() > 0 ) {
                while ( cursor.moveToNext() ) {
                    long engramId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
                    long resourceId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engramId );
                    values.put( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resourceId );

                    getContext().getContentResolver().insert( DatabaseContract.ComplexResourceEntry.CONTENT_URI, values );
                }

                cursor.close();
            }
        }

        void insertResources( JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject resourceObject = jsonArray.getJSONObject( i );

                String name = resourceObject.getString( DatabaseContract.ResourceEntry.COLUMN_NAME );
                String drawable = resourceObject.getString( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.ResourceEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, drawable );

                getContext().getContentResolver().insert( DatabaseContract.ResourceEntry.CONTENT_URI, values );
            }
        }

        void insertCategories( JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                long _id = jsonObject.getLong( DatabaseContract.CategoryEntry._ID );
                String name = jsonObject.getString( DatabaseContract.CategoryEntry.COLUMN_NAME );
                long parent_id = jsonObject.getLong( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.CategoryEntry._ID, _id );
                values.put( DatabaseContract.CategoryEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY, parent_id );

                getContext().getContentResolver().insert( DatabaseContract.CategoryEntry.CONTENT_URI, values );
            }
        }

        void insertEngrams( JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                String name = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_NAME );
                String description = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION );
                String drawable = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DRAWABLE );
                long category_id = jsonObject.getLong( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY );

                ContentValues values = new ContentValues();
                values.put( DatabaseContract.EngramEntry.COLUMN_NAME, name );
                values.put( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, description );
                values.put( DatabaseContract.EngramEntry.COLUMN_DRAWABLE, drawable );
                values.put( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, category_id );

                Uri insertUri = getContext().getContentResolver().insert( DatabaseContract.EngramEntry.CONTENT_URI, values );
                long engramId = DatabaseContract.getIdFromUri( insertUri );

                insertComposition( engramId, jsonObject.getJSONArray( DatabaseContract.CompositionEntry.TABLE_NAME ) );
            }
        }

        void insertComposition( long engramId, JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                // Drawable of Resource that will tie itself to this Composition
                String drawable = jsonObject.getString( DatabaseContract.CompositionEntry.COLUMN_DRAWABLE );
                int quantity = jsonObject.getInt( DatabaseContract.CompositionEntry.COLUMN_QUANTITY );

                Cursor cursor = getContext().getContentResolver().query(
                        DatabaseContract.ResourceEntry.buildUriWithDrawable( drawable ),
                        null, null, null, null
                );

                if ( cursor != null ) {
                    cursor.moveToFirst();

                    long resourceId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );

                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put( DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY, engramId );
                    values.put( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resourceId );
                    values.put( DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity );

                    getContext().getContentResolver().insert( DatabaseContract.CompositionEntry.CONTENT_URI, values );
                }
            }
        }

        Context getContext() {
            return mContext;
        }
    }
}
