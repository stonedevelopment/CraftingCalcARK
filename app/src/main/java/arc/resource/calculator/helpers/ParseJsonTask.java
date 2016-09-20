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
package arc.resource.calculator.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;

public class ParseJsonTask extends AsyncTask<Void, Void, Vector<Vector<ContentValues>>> {
    private static final String TAG = ParseJsonTask.class.getSimpleName();

    private Context mContext;

    private Vector<ContentValues> mResourceVector;
    private Vector<ContentValues> mCategoryVector;
    private Vector<ContentValues> mEngramVector;
    private Vector<ContentValues> mCompositionVector;


    public ParseJsonTask( Context context ) {
        this.mResourceVector = new Vector<>();
        this.mCategoryVector = new Vector<>();
        this.mEngramVector = new Vector<>();
        this.mCompositionVector = new Vector<>();

        this.mContext = context;
    }

    @Override
    protected Vector<Vector<ContentValues>> doInBackground( Void... params ) {
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
                }
            }
        }

        try {
            return parseJsonString( jsonString );
        } catch ( JSONException e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute( Vector<Vector<ContentValues>> vVector ) {
        super.onPostExecute( vVector );
    }

    Vector<Vector<ContentValues>> parseJsonString( String jsonString ) throws JSONException {
        Vector<Vector<ContentValues>> vVector = new Vector<>();

        JSONObject jsonObject = new JSONObject( jsonString );
        parseJsonToResources( jsonObject.getJSONArray( ResourceEntry.TABLE_NAME ) );
        parseJsonToCategories( jsonObject.getJSONArray( CategoryEntry.TABLE_NAME ) );
        parseJsonToEngrams( jsonObject.getJSONArray( EngramEntry.TABLE_NAME ) );

        vVector.add( mResourceVector );
        vVector.add( mCategoryVector );
        vVector.add( mEngramVector );
        vVector.add( mCompositionVector );

        return vVector;
    }

    void parseJsonToResources( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> cVector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject resourceObject = jsonArray.getJSONObject( i );

            String name = resourceObject.getString( ResourceEntry.COLUMN_NAME );
            String drawable = resourceObject.getString( ResourceEntry.COLUMN_DRAWABLE );

            ContentValues values = new ContentValues();
            values.put( ResourceEntry.COLUMN_NAME, name );
            values.put( ResourceEntry.COLUMN_DRAWABLE, drawable );

            cVector.add( values );
        }

        if ( cVector.size() > 0 ) mResourceVector = cVector;
    }

    void parseJsonToCategories( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> cVector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            String name = jsonObject.getString( CategoryEntry.COLUMN_NAME );
            long parent_id = jsonObject.getLong( CategoryEntry.COLUMN_PARENT_KEY );

            ContentValues values = new ContentValues();
            values.put( CategoryEntry.COLUMN_NAME, name );
            values.put( CategoryEntry.COLUMN_PARENT_KEY, parent_id );

            cVector.add( values );
        }

        if ( cVector.size() > 0 ) mCategoryVector = cVector;
    }

    void parseJsonToEngrams( JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> cVector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            String name = jsonObject.getString( EngramEntry.COLUMN_NAME );
            String description = jsonObject.getString( EngramEntry.COLUMN_DESCRIPTION );
            String drawable = jsonObject.getString( EngramEntry.COLUMN_DRAWABLE );
            long category_id = jsonObject.getLong( EngramEntry.COLUMN_CATEGORY_KEY );

            ContentValues values = new ContentValues();
            values.put( EngramEntry.COLUMN_NAME, name );
            values.put( EngramEntry.COLUMN_DESCRIPTION, description );
            values.put( EngramEntry.COLUMN_DRAWABLE, drawable );
            values.put( EngramEntry.COLUMN_CATEGORY_KEY, category_id );

            parseJsonToComposition( drawable, jsonObject.getJSONArray( CompositionEntry.TABLE_NAME ) );

            cVector.add( values );
        }

        if ( cVector.size() > 0 ) mEngramVector = cVector;
    }

    void parseJsonToComposition( String eDrawable, JSONArray jsonArray ) throws JSONException {
        Vector<ContentValues> cVector = new Vector<>( jsonArray.length() );

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            // Drawable of Resource that will tie itself to this Composition
            String rDrawable = jsonObject.getString( CompositionEntry.COLUMN_DRAWABLE );
            int quantity = jsonObject.getInt( CompositionEntry.COLUMN_QUANTITY );

            ContentValues cValues = new ContentValues();
            cValues.put( CompositionEntry.COLUMN_RESOURCE_DRAWABLE, rDrawable );
            cValues.put( CompositionEntry.COLUMN_ENGRAM_DRAWABLE, eDrawable );
            cValues.put( CompositionEntry.COLUMN_QUANTITY, quantity );

            cVector.add( cValues );
        }

        if ( cVector.size() > 0 ) mCompositionVector.addAll( cVector );
    }

    Context getContext() {
        return mContext;
    }
}
