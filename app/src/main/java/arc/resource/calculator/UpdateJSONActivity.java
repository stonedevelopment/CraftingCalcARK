package arc.resource.calculator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.DLCEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.db.DatabaseContract.TotalConversionEntry;
import arc.resource.calculator.util.JSONUtil;

/**
 * -    Read JSON file
 * -    Convert all jsonArrays to LongSparseArrays
 * -    Create dlc object that will hold its own maps
 * -    Convert all LongSparseArrays to HashMaps<Name, Object>
 * -    Convert HashMaps to jsonArrays
 * -    Write new JSON file
 */

public class UpdateJSONActivity extends AppCompatActivity {
    private static final String TAG = UpdateJSONActivity.class.getSimpleName();

    private final String _ID = EngramEntry._ID;

    // column names
    private final String COLUMN_CATEGORY_KEY = EngramEntry.COLUMN_CATEGORY_KEY;
    private final String COLUMN_CONSOLE_ID = EngramEntry.COLUMN_CONSOLE_ID;
    private final String COLUMN_DESCRIPTION = EngramEntry.COLUMN_DESCRIPTION;
    private final String COLUMN_DLC_KEY = EngramEntry.COLUMN_DLC_KEY;
    private final String COLUMN_ENGRAM_KEY = CompositionEntry.COLUMN_ENGRAM_KEY;
    private final String COLUMN_FROM = TotalConversionEntry.COLUMN_FROM;
    private final String COLUMN_IMAGE_FILE = EngramEntry.COLUMN_IMAGE_FILE;
    private final String COLUMN_IMAGE_FOLDER = EngramEntry.COLUMN_IMAGE_FOLDER;
    private final String COLUMN_LEVEL = EngramEntry.COLUMN_LEVEL;
    private final String COLUMN_NAME = EngramEntry.COLUMN_NAME;
    private final String COLUMN_PARENT_KEY = CategoryEntry.COLUMN_PARENT_KEY;
    private final String COLUMN_POINTS = EngramEntry.COLUMN_POINTS;
    private final String COLUMN_QUANTITY = CompositionEntry.COLUMN_QUANTITY;
    private final String COLUMN_RESOURCE_KEY = CompositionEntry.COLUMN_RESOURCE_KEY;
    private final String COLUMN_STATION_KEY = EngramEntry.COLUMN_STATION_KEY;
    private final String COLUMN_STEAM_ID = EngramEntry.COLUMN_STEAM_ID;
    private final String COLUMN_TO = TotalConversionEntry.COLUMN_TO;
    private final String COLUMN_YIELD = EngramEntry.COLUMN_YIELD;
    private final String COLUMN_XP = EngramEntry.COLUMN_XP;

    // table names
    private final String JSON = "json";
    private final String DLC = DLCEntry.TABLE_NAME;
    private final String STATION = StationEntry.TABLE_NAME;
    private final String CATEGORY = CategoryEntry.TABLE_NAME;
    private final String RESOURCE = ResourceEntry.TABLE_NAME;
    private final String ENGRAM = EngramEntry.TABLE_NAME;
    private final String COMPOSITION = CompositionEntry.TABLE_NAME;
    private final String TOTAL_CONVERSION = TotalConversionEntry.TABLE_NAME;

    private final LongSparseArray<ComplexMap> mComplexMap = new LongSparseArray<>();

    private final LongSparseArray<Map> mEngramIdsByName = new LongSparseArray<>();
    private final LongSparseArray<Map> mResourceIdsByName = new LongSparseArray<>();
    private final LongSparseArray<Map> mStationIdsByName = new LongSparseArray<>();

    private final LongSparseArray<String> mResourceNamesById = new LongSparseArray<>();
    private final LongSparseArray<String> mStationNamesById = new LongSparseArray<>();

    private LongSparseArray<TotalConversion> mTotalConversion = new LongSparseArray<>();

    private SparseArray<Long> mDlcIds = new SparseArray<>();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update_json );

        updateConsole( "App started, executing task..." );

        new ParseConvertTask( this ).execute();
    }

    private void updateConsole( String text ) {
        TextView console = ( TextView ) findViewById( R.id.console );
        console.append( text + "\n" );
    }

    private class ParseConvertTask extends AsyncTask<Void, String, Void> {
        private final String TAG = ParseConvertTask.class.getSimpleName();

        private JSONObject mObject;

        private Uri mAttachmentUri;
        private String mVersion;

        private final Context mContext;

        ParseConvertTask( Context context ) {
            this.mContext = context;
        }

        Context getContext() {
            return mContext;
        }

        @Override
        protected void onProgressUpdate( String... values ) {
            super.onProgressUpdate( values );

            for ( String value : values ) {
                updateConsole( value );
            }
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            super.onPostExecute( aVoid );

            emailJSONFile();

            updateConsole( "Execution complete!" );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            try {
                // first, let's read our json file to a string
                publishProgress( "Reading JSON from file..." );
                String jsonString = JSONUtil.readRawJsonFileToJsonString( getContext(), R.raw.data_editable );

                // parse json string into a json object
                publishProgress( "Parsing JSON string into object..." );
                JSONObject oldObject = new JSONObject( jsonString );

                publishProgress( "** Beginning conversion..." );

                /***** EDIT JSON FILE CHANGES BETWEEN THESE COMMENTS *****/

                // convert json into new json layout
                JSONObject newObject = convertJSONObjectToIds( oldObject );

//                // update editable json with new table elements
//                JSONObject newObject = updateJSONObject( oldObject );

                /***** EDIT JSON FILE CHANGES BETWEEN THESE COMMENTS *****/

                publishProgress( "** Conversion complete!..." );

                // finally, write new json object to file
                writeJSONObjectToFile( newObject );

                mObject = newObject;
            } catch ( Exception e ) {
                publishException( e );
            }

            return null;
        }

        void publishException( Exception e ) {
            Log.e( TAG, "Error: ", e );
            Log.e( TAG, Arrays.toString( e.getStackTrace() ) );
            publishProgress( "Error: " + e.getMessage() );
        }

        void emailJSONFile() {
            publishProgress( "Emailing new JSON Object..." );

            Log.i( TAG, "Send email" );
            String[] TO = { "jaredstone1982@gmail.com" };
            String[] CC = { "" };
            Intent emailIntent = new Intent( Intent.ACTION_SEND );

            emailIntent.setData( Uri.parse( "mailto:" ) );
            emailIntent.setType( "text/plain" );
            emailIntent.putExtra( Intent.EXTRA_EMAIL, TO );
            emailIntent.putExtra( Intent.EXTRA_CC, CC );
            emailIntent.putExtra( Intent.EXTRA_SUBJECT, "New JSON Update " + mVersion );
            emailIntent.putExtra( Intent.EXTRA_TEXT, "New JSON Update " + mVersion );
            emailIntent.putExtra( Intent.EXTRA_STREAM, mAttachmentUri );

            try {
                startActivity( Intent.createChooser( emailIntent, "Send mail..." ) );
                finish();
                Log.i( TAG, "Finished sending email..." );
            } catch ( android.content.ActivityNotFoundException ex ) {
                Toast.makeText( UpdateJSONActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT ).show();
            }
        }

        void writeJSONObjectToFile( JSONObject object ) throws IOException {
            publishProgress( "Writing new JSON object to file..." );

            File path = getContext().getExternalFilesDir( null );
            File file = new File( path, mVersion + ".json" );

            FileOutputStream fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( object.toString().getBytes() );

            mAttachmentUri = Uri.parse( "file:///" + file.getPath() );

            Log.d( TAG, mAttachmentUri.toString() );
        }

        /**
         * Updates the editable json file with new table elements
         *
         * @param inObject json object of entire file
         * @return new json object with updated table elements
         * @throws JSONException
         */
        JSONObject updateJSONObject( JSONObject inObject ) throws Exception {
            JSONObject outObject = new JSONObject();

            JSONObject json = inObject.getJSONObject( JSON );
            JSONArray dlcs = inObject.getJSONArray( DLC );
            JSONArray stations = inObject.getJSONArray( STATION );
            JSONArray categories = inObject.getJSONArray( CATEGORY );
            JSONArray resources = inObject.getJSONArray( RESOURCE );
            JSONArray engrams = inObject.getJSONArray( ENGRAM );
            JSONArray totalConversion = inObject.getJSONArray( TOTAL_CONVERSION );

            /***** EDIT JSON FILE CHANGES BETWEEN THESE COMMENTS *****/

            outObject.put( JSON, json );
            outObject.put( DLC, dlcs );
            outObject.put( STATION, stations );
            outObject.put( CATEGORY, categories );
            outObject.put( RESOURCE, resources );
            outObject.put( ENGRAM, updateEngramJSONArray( engrams ) );
            outObject.put( TOTAL_CONVERSION, totalConversion );

            /***** EDIT JSON FILE CHANGES BETWEEN THESE COMMENTS *****/

            return outObject;
        }


        JSONArray updateEngramJSONArray( JSONArray inArray ) throws Exception {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                String name = object.getString( COLUMN_NAME );
                String description = object.getString( COLUMN_DESCRIPTION );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                Integer yield = object.getInt( COLUMN_YIELD );
                Integer level = object.getInt( COLUMN_LEVEL );
//                Integer points = object.getInt( COLUMN_POINTS );
//                Integer xp = object.getInt( COLUMN_XP );
//                Integer steam_id = object.getInt( COLUMN_STEAM_ID );
//                Integer console_id = object.getInt( COLUMN_CONSOLE_ID );
                Integer category_id = object.getInt( COLUMN_CATEGORY_KEY );
                JSONArray compositionArray = object.getJSONArray( COMPOSITION );
                JSONArray stationArray = object.getJSONArray( STATION );
                long dlc_id = object.getInt( COLUMN_DLC_KEY );

                // instantiate a new json object used to be updated
                JSONObject updateObject = new JSONObject();

                // insert standardized data
                updateObject.put( COLUMN_NAME, name );
                updateObject.put( COLUMN_DESCRIPTION, description );
                updateObject.put( COLUMN_YIELD, yield );
                updateObject.put( COLUMN_LEVEL, level );
                updateObject.put( COLUMN_CATEGORY_KEY, category_id );
                updateObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                updateObject.put( COLUMN_IMAGE_FILE, imageFile );
                updateObject.put( COLUMN_POINTS, 0 );
                updateObject.put( COLUMN_XP, 0 );
                updateObject.put( COLUMN_STEAM_ID, 0 );
                updateObject.put( COLUMN_CONSOLE_ID, 0 );
                updateObject.put( COLUMN_DLC_KEY, dlc_id );
                updateObject.put( COMPOSITION, compositionArray );
                updateObject.put( STATION, stationArray );

                // insert updated object into array
                outArray.put( updateObject );
            }

            return outArray;
        }

        /**
         * Convert editable json object into json data object
         *
         * @param inObject editable json object with names
         * @return new json object with ids as names
         * @throws JSONException
         */
        JSONObject convertJSONObjectToIds( JSONObject inObject ) throws Exception {
            JSONObject outObject = new JSONObject();

            // insert json data with updated versioning
            mVersion = inObject.getJSONObject( JSON ).getString( "version" );
            outObject.put( JSON, inObject.getJSONObject( JSON ) );

            // insert old dlc json data, no need to convert at this time
            publishProgress( "Inserting DLC values..." );
            outObject.put( DLC,
                    inObject.getJSONArray( DLC ) );

            mDlcIds = mapDLCJSONArray( inObject.getJSONArray( DLC ) );
            mTotalConversion = mapTotalConversion( inObject.getJSONArray( TOTAL_CONVERSION ) );

            // convert, then insert new station json data
            publishProgress( "Converting station values..." );
            outObject.put( STATION,
                    convertStationJSONArrayToIds( inObject.getJSONArray( STATION ) ) );

            // convert, then insert new category json data
            publishProgress( "Converting category values..." );
            outObject.put( CATEGORY,
                    convertCategoryJSONArrayToIds( inObject.getJSONArray( CATEGORY ) ) );

            // convert, then insert new resource json data
            publishProgress( "Converting resource values..." );
            outObject.put( RESOURCE,
                    convertResourceJSONArrayToIds( inObject.getJSONArray( RESOURCE ) ) );

            // convert, then insert new engram json data
            publishProgress( "Converting engram values..." );
            outObject.put( ENGRAM,
                    convertEngramJSONArrayToIds( inObject.getJSONArray( ENGRAM ) ) );

            // convert, then insert new complex resource json data
            publishProgress( "Converting complex resource values..." );
            outObject.put( ComplexResourceEntry.TABLE_NAME,
                    convertComplexResourceMapIdsToJSONArray() );

            // convert, then insert new total conversion json data
            publishProgress( "Converting total conversion values..." );
            outObject.put( TOTAL_CONVERSION,
                    convertTotalConversionJSONArrayToIds( inObject.getJSONArray( TOTAL_CONVERSION ) ) );

            return outObject;
        }

        SparseArray<Long> mapDLCJSONArray( JSONArray inArray ) throws JSONException {
            SparseArray<Long> map = new SparseArray<>();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long _id = object.getLong( _ID );
                String name = object.getString( COLUMN_NAME );

                map.put( map.size(), _id );
            }

            return map;
        }

        JSONArray convertStationJSONArrayToIds( JSONArray inArray ) throws Exception {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                String name = object.getString( COLUMN_NAME );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                long dlc_id = object.getInt( COLUMN_DLC_KEY );

                // let's, first, create an array of qualified dlc versions, minus total conversions
                List<Long> dlc_ids = new ArrayList<>();
                if ( dlc_id == mDlcIds.valueAt( 0 ) ) {
                    for ( int j = 0; j < mDlcIds.size(); j++ )
                        dlc_ids.add( mDlcIds.valueAt( j ) );
                } else {
                    dlc_ids.add( dlc_id );
                }

                // next, let's create an id based on counter variable, i
                long _id = i;

                // insert its id into a map for other methods to use later
                addStationIdByName( dlc_id, name, _id );

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( _ID, _id );
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                convertedObject.put( COLUMN_IMAGE_FILE, imageFile );
                convertedObject.put( COLUMN_DLC_KEY, new JSONArray( dlc_ids.toArray() ) );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertCategoryJSONArrayToIds( JSONArray inArray ) throws Exception {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long _id = object.getLong( _ID );
                String name = object.getString( COLUMN_NAME );
                long parent_id = object.getLong( COLUMN_PARENT_KEY );
                JSONArray stationArray = object.getJSONArray( STATION );
                long dlc_id = object.getInt( COLUMN_DLC_KEY );

                // let's, first, create an array of qualified dlc versions, minus total conversions
                List<Long> dlc_ids = new ArrayList<>();
                if ( dlc_id == mDlcIds.valueAt( 0 ) ) {
                    for ( int j = 0; j < mDlcIds.size(); j++ )
                        dlc_ids.add( mDlcIds.valueAt( j ) );
                } else {
                    dlc_ids.add( dlc_id );
                }

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( _ID, _id );
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_PARENT_KEY, parent_id );
                convertedObject.put( COLUMN_DLC_KEY, new JSONArray( dlc_ids.toArray() ) );

                // convert station object array from ids to names
                List<Long> stations = mapLocalStationIds( stationArray );

                // insert converted station array into engram json object
                convertedObject.put( CategoryEntry.COLUMN_STATION_KEY, new JSONArray( stations.toArray() ) );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertResourceJSONArrayToIds( JSONArray inArray ) throws Exception {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                String name = object.getString( COLUMN_NAME );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                long dlc_id = object.getInt( COLUMN_DLC_KEY );
                boolean hasComplexity = object.getBoolean( ComplexResourceEntry.TABLE_NAME );

                // let's, first, create an array of qualified dlc versions, minus total conversions
                List<Long> dlc_ids = new ArrayList<>();
                if ( dlc_id == mDlcIds.valueAt( 0 ) ) {
                    for ( int j = 0; j < mDlcIds.size(); j++ ) {
                        long _id = mDlcIds.valueAt( j );
                        if ( mTotalConversion.get( _id ) == null )
                            dlc_ids.add( _id );
                        else if ( mTotalConversion.get( _id ).resources.get( name ) == null )
                            dlc_ids.add( _id );
                    }
                } else {
                    dlc_ids.add( dlc_id );
                }

                // let's, first, create an id based on counter variable, i
                long _id = i;

                // insert its id into a map for other methods to use later
                addResourceIdByName( dlc_id, name, _id );

                // check if resource has complexity
                if ( hasComplexity )
                    addComplexResourceIdByName( dlc_id, name, _id );

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( _ID, _id );
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                convertedObject.put( COLUMN_IMAGE_FILE, imageFile );
                convertedObject.put( COLUMN_DLC_KEY, new JSONArray( dlc_ids.toArray() ) );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertEngramJSONArrayToIds( JSONArray inArray ) throws Exception {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                String name = object.getString( COLUMN_NAME );
                String description = object.getString( COLUMN_DESCRIPTION );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                Integer yield = object.getInt( COLUMN_YIELD );
                Integer level = object.getInt( COLUMN_LEVEL );
                Integer points = object.getInt( COLUMN_POINTS );
                Integer xp = object.getInt( COLUMN_XP );
                Integer steam_id = object.getInt( COLUMN_STEAM_ID );
                Integer console_id = object.getInt( COLUMN_CONSOLE_ID );
                Integer category_id = object.getInt( COLUMN_CATEGORY_KEY );
                JSONArray compositionArray = object.getJSONArray( COMPOSITION );
                JSONArray stationArray = object.getJSONArray( STATION );
                long dlc_id = object.getInt( COLUMN_DLC_KEY );

                // let's, first, create an array of qualified dlc versions, minus total conversions
                List<Long> dlc_ids = new ArrayList<>();
                if ( dlc_id == mDlcIds.valueAt( 0 ) ) {
                    for ( int j = 0; j < mDlcIds.size(); j++ ) {
                        long _id = mDlcIds.valueAt( j );
                        if ( mTotalConversion.get( _id ) == null )
                            dlc_ids.add( _id );
                        else if ( !mTotalConversion.get( _id ).engrams.contains( name ) )
                            dlc_ids.add( _id );
                    }
                } else {
                    dlc_ids.add( dlc_id );
                }

                // next, let's create an id based on counter variable, i
                long _id = i;

                // insert its id into a map for other methods to use later
                addEngramIdByName( dlc_id, name, _id );

                // check if complex by finding its name in the complex resource map for its dlc version
                if ( mComplexMap.get( dlc_id ).contains( name ) )
                    // add if not already added
                    addComplexEngramIdByName( dlc_id, name, _id );

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( _ID, _id );
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_DESCRIPTION, description );
                convertedObject.put( COLUMN_YIELD, yield );
                convertedObject.put( COLUMN_LEVEL, level );
                convertedObject.put( COLUMN_CATEGORY_KEY, category_id );
                convertedObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                convertedObject.put( COLUMN_IMAGE_FILE, imageFile );
                convertedObject.put( COLUMN_POINTS, points );
                convertedObject.put( COLUMN_XP, xp );
                convertedObject.put( COLUMN_STEAM_ID, steam_id );
                convertedObject.put( COLUMN_CONSOLE_ID, console_id );
                convertedObject.put( COLUMN_DLC_KEY, new JSONArray( dlc_ids.toArray() ) );

                // convert composition object array from names to ids
                JSONArray convertedComposition = new JSONArray();
                for ( int j = 0; j < compositionArray.length(); j++ ) {
                    JSONObject oldObject = compositionArray.getJSONObject( j );

                    String resource_name = oldObject.getString( COLUMN_RESOURCE_KEY );
                    int quantity = oldObject.getInt( COLUMN_QUANTITY );

                    // attempt to grab id of resource from previously mapped list
                    long resource_id = getResourceIdByName( dlc_id, resource_name );

                    // if invalid (which should never happen) continue to next resource
                    if ( resource_id == -1 )
                        continue;

                    // build new json object with its matched name and quantity
                    JSONObject newObject = new JSONObject();
                    newObject.put( COLUMN_RESOURCE_KEY, resource_id );
                    newObject.put( COLUMN_QUANTITY, quantity );

                    // insert object into composition
                    convertedComposition.put( newObject );
                }

                // insert converted composition array into engram json object
                convertedObject.put( COMPOSITION, convertedComposition );

                // convert station object array from ids to names
                List<Long> stations = mapLocalStationIds( stationArray );

                // insert converted station array into engram json object
                convertedObject.put( EngramEntry.COLUMN_STATION_KEY, new JSONArray( stations.toArray() ) );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertTotalConversionJSONArrayToIds( JSONArray inArray ) throws Exception {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long dlc_id = object.getInt( COLUMN_DLC_KEY );
                JSONArray resourceArray = object.getJSONArray( RESOURCE );
//                JSONArray engramArray = object.getJSONArray( ENGRAM );

                JSONArray convertedResourceArray = new JSONArray();
                for ( int j = 0; j < resourceArray.length(); j++ ) {
                    JSONObject oldObject = resourceArray.getJSONObject( j );

                    String name = oldObject.getString( COLUMN_FROM );
                    String converted_name = oldObject.getString( COLUMN_TO );

                    // attempt to grab ids from previously mapped list
                    long _id = getResourceIdByName( dlc_id, name );
                    long converted_id = getResourceIdByName( dlc_id, converted_name );

                    // if invalid (which should never happen) continue to next json object
                    if ( _id == -1 || converted_id == -1 )
                        continue;

                    // build new json object with its matched ids
                    JSONObject newObject = new JSONObject();
                    newObject.put( COLUMN_FROM, _id );
                    newObject.put( COLUMN_TO, converted_id );

                    // insert new object into json array
                    convertedResourceArray.put( newObject );
                }

//                JSONArray convertedEngramArray = new JSONArray();
//                for ( int j = 0; j < engramArray.length(); j++ ) {
//                    JSONObject oldObject = engramArray.getJSONObject( j );
//
//                    String name = oldObject.getString( COLUMN_FROM );
//                    String converted_name = oldObject.getString( COLUMN_TO );
//
//                    // attempt to grab ids from previously mapped list
//                    long _id = getEngramIdByName( dlc_id, name );
//                    long converted_id = getEngramIdByName( dlc_id, converted_name );
//
//                    // if invalid (which should never happen) continue to next json object
//                    if ( _id == -1 || converted_id == -1 )
//                        continue;
//
//                    // build new json object with its matched ids
//                    JSONObject newObject = new JSONObject();
//                    newObject.put( COLUMN_FROM, _id );
//                    newObject.put( COLUMN_TO, converted_id );
//
//                    // insert new object into json array
//                    convertedEngramArray.put( newObject );
//                }

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( COLUMN_DLC_KEY, dlc_id );
                convertedObject.put( RESOURCE, convertedResourceArray );
//                convertedObject.put( ENGRAM, convertedEngramArray );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertComplexResourceMapIdsToJSONArray() throws JSONException {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < mComplexMap.size(); i++ ) {
                ComplexMap complexMap = mComplexMap.valueAt( i );

                for ( String name : complexMap.resources.keySet() ) {
                    long resource_id = complexMap.getResourceId( name );
                    long engram_id = complexMap.getEngramId( name );

                    JSONObject object = new JSONObject();

                    // insert standardized data
                    object.put( COLUMN_RESOURCE_KEY, resource_id );
                    object.put( COLUMN_ENGRAM_KEY, engram_id );

                    // insert converted object into converted array
                    outArray.put( object );
                }
            }

            return outArray;
        }

        /**
         * Convert old json object into new version of json object
         *
         * @param inObject old json object with ids
         * @return new json object with names as ids
         * @throws JSONException
         */
        JSONObject convertJSONObjectToNames( JSONObject inObject ) throws JSONException {
            JSONObject outObject = new JSONObject();

            // insert old dlc json data, no need to convert at this time
            publishProgress( "Inserting DLC values..." );
            outObject.put( DLC,
                    inObject.getJSONArray( DLC ) );

            // convert, then insert new station json data
            publishProgress( "Converting station values..." );
            outObject.put( STATION,
                    convertStationJSONArrayToNames( inObject.getJSONArray( STATION ) ) );

            // convert, then insert new category json data
            publishProgress( "Converting category values..." );
            outObject.put( CATEGORY,
                    convertCategoryJSONArrayToNames( inObject.getJSONArray( CATEGORY ) ) );

            // convert, then insert new resource json data
            publishProgress( "Converting resource values..." );
            outObject.put( RESOURCE,
                    convertResourceJSONArrayToNames( inObject.getJSONArray( RESOURCE ) ) );

            // convert, then insert new engram json data
            publishProgress( "Converting engram values..." );
            outObject.put( ENGRAM,
                    convertEngramJSONArrayToNames( inObject.getJSONArray( ENGRAM ) ) );

            // TODO: 3/1/2017 Don't forget to manually add total_conversion

            return outObject;
        }

        JSONArray convertStationJSONArrayToNames( JSONArray inArray ) throws JSONException {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long _id = object.getLong( _ID );
                String name = object.getString( COLUMN_NAME );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                JSONArray dlcArray = object.getJSONArray( COLUMN_DLC_KEY );

                // let's, first, insert its name into a map for other methods to use later
                mStationNamesById.put( _id, name );

                // grab id of root dlc version to use in querying for names
                long dlc_id = dlcArray.getLong( 0 );

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                convertedObject.put( COLUMN_IMAGE_FILE, imageFile );
                convertedObject.put( COLUMN_DLC_KEY, dlc_id );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertCategoryJSONArrayToNames( JSONArray inArray ) throws JSONException {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long _id = object.getLong( _ID );
                String name = object.getString( COLUMN_NAME );
                JSONArray dlcArray = object.getJSONArray( COLUMN_DLC_KEY );
                JSONArray stationArray = object.getJSONArray( COLUMN_STATION_KEY );

                // grab id of root dlc version to use in querying for names
                long dlc_id = dlcArray.getLong( 0 );

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( _ID, _id );
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_DLC_KEY, dlc_id );

                // convert station object array from ids to names
                String[] stations = mapLocalStationNames( stationArray );

                // insert converted station array into engram json object
                convertedObject.put( STATION, new JSONArray( stations ) );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertResourceJSONArrayToNames( JSONArray inArray ) throws JSONException {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long _id = object.getLong( _ID );
                String name = object.getString( COLUMN_NAME );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                JSONArray dlcArray = object.getJSONArray( COLUMN_DLC_KEY );

                // let's, first, insert its name into a map for other methods to use later
                mResourceNamesById.put( _id, name );

                // grab id of root dlc version
                long dlc_id = dlcArray.getLong( 0 );

                // check if resource has complexity
                boolean hasComplexity = mComplexMap.get( _id ) != null;

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                convertedObject.put( COLUMN_IMAGE_FILE, imageFile );
                convertedObject.put( COLUMN_DLC_KEY, dlc_id );
                convertedObject.put( ComplexResourceEntry.TABLE_NAME, hasComplexity );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        JSONArray convertEngramJSONArrayToNames( JSONArray inArray ) throws JSONException {
            JSONArray outArray = new JSONArray();

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                long _id = object.getLong( _ID );
                String name = object.getString( COLUMN_NAME );
                String description = object.getString( COLUMN_DESCRIPTION );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                Integer yield = object.getInt( COLUMN_YIELD );
                Integer level = object.getInt( COLUMN_LEVEL );
                Integer category_id = object.getInt( COLUMN_CATEGORY_KEY );
                JSONArray compositionArray = object.getJSONArray( COMPOSITION );
                JSONArray dlcArray = object.getJSONArray( COLUMN_DLC_KEY );
                JSONArray stationArray = object.getJSONArray( COLUMN_STATION_KEY );

                // grab id of root dlc version to use in querying for names
                long dlc_id = dlcArray.getLong( 0 );

                // instantiate a new json object used to be converted into new layout
                JSONObject convertedObject = new JSONObject();

                // insert standardized data
                convertedObject.put( COLUMN_NAME, name );
                convertedObject.put( COLUMN_DESCRIPTION, description );
                convertedObject.put( COLUMN_YIELD, yield );
                convertedObject.put( COLUMN_LEVEL, level );
                convertedObject.put( COLUMN_CATEGORY_KEY, category_id );
                convertedObject.put( COLUMN_IMAGE_FOLDER, imageFolder );
                convertedObject.put( COLUMN_IMAGE_FILE, imageFile );
                convertedObject.put( COLUMN_DLC_KEY, dlc_id );

                // convert composition object array from ids to names
                JSONArray convertedComposition = new JSONArray();
                for ( int j = 0; j < compositionArray.length(); j++ ) {
                    JSONObject oldObject = compositionArray.getJSONObject( j );

                    long resource_id = oldObject.getInt( COLUMN_RESOURCE_KEY );
                    int quantity = oldObject.getInt( COLUMN_QUANTITY );

                    // attempt to grab name of resource from previously mapped list
                    String resource_name = getResourceNameById( resource_id );

                    // if null (which should never happen) continue to next resource
                    if ( resource_name == null )
                        continue;

                    // build new json object with its matched name and quantity
                    JSONObject newObject = new JSONObject();
                    newObject.put( COLUMN_RESOURCE_KEY, resource_name );
                    newObject.put( COLUMN_QUANTITY, quantity );

                    // insert object into composition
                    convertedComposition.put( newObject );
                }

                // insert converted composition array into engram json object
                convertedObject.put( COMPOSITION, convertedComposition );

                // convert station object array from ids to names
                String[] stations = mapLocalStationNames( stationArray );

                // insert converted station array into engram json object
                convertedObject.put( STATION, new JSONArray( stations ) );

                // insert converted object into converted array
                outArray.put( convertedObject );
            }

            return outArray;
        }

        String trimChars( String incoming ) {
            return incoming.toLowerCase()
                    .replace( " ", "_" )
                    .replace( "\'", "" )
                    .replace( "-", "" )
                    .replace( "(", "" )
                    .replace( ")", "" )
                    + ".png";
        }

        String getResourceNameById( long _id ) {
            String name = mResourceNamesById.get( _id );

            if ( name == null )
                publishException( new Exception( "Resource name is null for _id: " + _id ) );

            return name;
        }

        long getStationIdByName( String name ) throws Exception {
            for ( int i = 0; i < mStationIdsByName.size(); i++ ) {
                if ( mStationIdsByName.valueAt( i ).contains( name ) )
                    return mStationIdsByName.valueAt( i ).get( name );
            }

            throw new Exception( "getStationIdByName() didn't find name: " + name );
        }

        long getResourceIdByName( long dlc_id, String name ) throws Exception {
            try {
                return mResourceIdsByName.get( dlc_id ).get( name );
            } catch ( Exception e ) {
                return mResourceIdsByName.get( mDlcIds.valueAt( 0 ) ).get( name );
            }
        }

        long getEngramIdByName( long dlc_id, String name ) throws Exception {
            try {
                return mEngramIdsByName.get( dlc_id ).get( name );
            } catch ( Exception e ) {
                return mEngramIdsByName.get( mDlcIds.valueAt( 0 ) ).get( name );
            }
        }

        void addComplexEngramIdByName( long dlc_id, String name, long _id ) throws Exception {
            if ( mComplexMap.indexOfKey( dlc_id ) < 0 )
                mComplexMap.put( dlc_id, new ComplexMap() );

            mComplexMap.get( dlc_id ).addEngram( name, _id );
        }

        void addComplexResourceIdByName( long dlc_id, String name, long _id ) throws Exception {
            if ( mComplexMap.indexOfKey( dlc_id ) < 0 )
                mComplexMap.put( dlc_id, new ComplexMap() );

            mComplexMap.get( dlc_id ).addResource( name, _id );
        }

        void addStationIdByName( long dlc_id, String name, long _id ) throws Exception {
            if ( mStationIdsByName.get( dlc_id ) == null )
                mStationIdsByName.put( dlc_id, new Map() );

            mStationIdsByName.get( dlc_id ).add( name, _id );
        }

        void addResourceIdByName( long dlc_id, String name, long _id ) throws Exception {
            if ( mResourceIdsByName.get( dlc_id ) == null )
                mResourceIdsByName.put( dlc_id, new Map() );

            mResourceIdsByName.get( dlc_id ).add( name, _id );
        }

        void addEngramIdByName( long dlc_id, String name, long _id ) throws Exception {
            if ( mEngramIdsByName.get( dlc_id ) == null )
                mEngramIdsByName.put( dlc_id, new Map() );

            mEngramIdsByName.get( dlc_id ).add( name, _id );
        }

        String[] mapLocalStationNames( JSONArray inArray ) throws JSONException {
            String[] names = new String[inArray.length()];

            for ( int i = 0; i < inArray.length(); i++ ) {
                long _id = inArray.getInt( i );

                String name = mStationNamesById.get( _id );

                if ( name != null ) {
                    names[i] = name;
                } else {
                    publishException( new Exception( "Station name is null for _id: " + _id ) );
                }
            }

            return names;
        }

        /**
         * Maps all station ids within incoming json array by sifting through all dlc ids
         *
         * @param inArray json array containing a list of station names
         * @return id of matched station, in all dlc ids
         */
        List<Long> mapLocalStationIds( JSONArray inArray ) throws Exception {
            List<Long> ids = new ArrayList<>();

            for ( int i = 0; i < inArray.length(); i++ ) {
                String name = inArray.getString( i );
                long station_id = getStationIdByName( name );

                ids.add( station_id );
            }

            return ids;
        }
    }

    private LongSparseArray<TotalConversion> mapTotalConversion( JSONArray jsonArray ) throws JSONException {
        LongSparseArray<TotalConversion> map = new LongSparseArray<>();

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long dlc_id = jsonObject.getLong( COLUMN_DLC_KEY );
            JSONArray resourceArray = jsonObject.getJSONArray( RESOURCE );
            JSONArray engramArray = jsonObject.getJSONArray( ENGRAM );

            TotalConversion totalConversion = new TotalConversion();
            for ( int j = 0; j < resourceArray.length(); j++ ) {
                JSONObject object = resourceArray.getJSONObject( j );

                String from = object.getString( COLUMN_FROM );
                String to = object.getString( COLUMN_TO );

                totalConversion.resources.put( from, to );
            }

            for ( int j = 0; j < engramArray.length(); j++ )
                totalConversion.engrams.add( engramArray.getString( j ) );

            map.put( dlc_id, totalConversion );
        }

        return map;
    }

    private class TotalConversion {
        final List<String> engrams;
        final HashMap<String, String> resources;

        TotalConversion() {
            engrams = new ArrayList<>();
            resources = new HashMap<>();
        }
    }

    private class ComplexMap {
        private final HashMap<String, Long> resources;
        private final HashMap<String, Long> engrams;

        ComplexMap() {
            this.resources = new HashMap<>();
            this.engrams = new HashMap<>();
        }

        long getEngramId( String name ) {
            return engrams.get( name );
        }

        long getResourceId( String name ) {
            return resources.get( name );
        }

        boolean contains( String name ) {
            return containsResource( name );
        }

        boolean containsEngram( String name ) {
            return engrams.containsKey( name );
        }

        boolean containsResource( String name ) {
            return resources.containsKey( name );
        }

        void addEngram( String name, long _id ) throws Exception {
            if ( containsEngram( name ) )
                throw new Exception( "Found duplicate complex engram name for " + name + ", id: " + _id );
            else
                engrams.put( name, _id );
        }

        void addResource( String name, long _id ) throws Exception {
            if ( containsResource( name ) )
                throw new Exception( "Found duplicate complex resource name for " + name + ", id: " + _id );
            else
                resources.put( name, _id );
        }
    }

    private class Map {
        private final HashMap<String, Long> map;

        Map() {
            this.map = new HashMap<>();
        }

        boolean contains( String name ) {
            return map.containsKey( name );
        }

        void add( String name, long _id ) throws Exception {
            if ( contains( name ) )
                throw new Exception( "Found duplicate name for " + name + ", id: " + _id );
            else
                map.put( name, _id );
        }

        long get( String name ) throws Exception {
            if ( contains( name ) )
                return map.get( name );
            else
                throw new Exception( "_id is null for name: " + name );
        }
    }

    class Category {
        final String imageFile;
        final String imageFolder;
        final String name;
        final String parent;

        final int[] dlc_ids;
        int[] station_ids;

        Category( String name, String parent, String imageFolder, String imageFile,
                  int[] station_ids, int[] dlc_ids ) {
            this.name = name;
            this.parent = parent;
            this.imageFolder = imageFolder;
            this.imageFile = imageFile;
            this.dlc_ids = dlc_ids;
        }
    }

    class Engram {
        final String category;
        final String description;
        final String imageFile;
        final String imageFolder;
        final String name;

        final Integer level;
        final Integer yield;

        final LongSparseArray<Integer> composition;

        final int[] dlc_ids;
        final int[] station_ids;

        Engram( String name, String description, String imageFolder, String imageFile,
                int yield, int level, String category, LongSparseArray<Integer> composition,
                int[] station_ids, int[] dlc_ids ) {
            this.name = name;
            this.description = description;
            this.imageFolder = imageFolder;
            this.imageFile = imageFile;
            this.yield = yield;
            this.level = level;
            this.category = category;
            this.composition = composition;
            this.station_ids = station_ids;
            this.dlc_ids = dlc_ids;
        }
    }

    class Resource {
        final String imageFile;
        final String imageFolder;
        final String name;

        final int[] dlc_ids;

        Resource( String name, String imageFolder, String imageFile, int[] dlc_ids ) {
            this.name = name;
            this.imageFolder = imageFolder;
            this.imageFile = imageFile;
            this.dlc_ids = dlc_ids;
        }
    }

    class Station {
        final String imageFile;
        final String imageFolder;
        final String name;

        final int[] dlc_ids;

        Station( String name, String imageFolder, String imageFile, int[] dlc_ids ) {
            this.name = name;
            this.imageFolder = imageFolder;
            this.imageFile = imageFile;
            this.dlc_ids = dlc_ids;
        }
    }
}
