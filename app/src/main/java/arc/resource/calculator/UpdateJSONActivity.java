package arc.resource.calculator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.widget.TextView;

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

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.DLCEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.db.DatabaseContract.TotalConversionEntry;
import arc.resource.calculator.util.JsonUtil;

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

    final String _ID = EngramEntry._ID;
    final String COLUMN_CATEGORY_KEY = EngramEntry.COLUMN_CATEGORY_KEY;
    final String COLUMN_DESCRIPTION = EngramEntry.COLUMN_DESCRIPTION;
    final String COLUMN_DLC_KEY = EngramEntry.COLUMN_DLC_KEY;
    final String COLUMN_ENGRAM_KEY = CompositionEntry.COLUMN_ENGRAM_KEY;
    final String COLUMN_FROM = TotalConversionEntry.COLUMN_FROM;
    final String COLUMN_IMAGE_FILE = EngramEntry.COLUMN_IMAGE_FILE;
    final String COLUMN_IMAGE_FOLDER = EngramEntry.COLUMN_IMAGE_FOLDER;
    final String COLUMN_LEVEL = EngramEntry.COLUMN_LEVEL;
    final String COLUMN_NAME = EngramEntry.COLUMN_NAME;
    final String COLUMN_PARENT_KEY = CategoryEntry.COLUMN_PARENT_KEY;
    final String COLUMN_QUANTITY = CompositionEntry.COLUMN_QUANTITY;
    final String COLUMN_RESOURCE_KEY = CompositionEntry.COLUMN_RESOURCE_KEY;
    final String COLUMN_STATION_KEY = EngramEntry.COLUMN_STATION_KEY;
    final String COLUMN_TO = TotalConversionEntry.COLUMN_TO;
    final String COLUMN_YIELD = EngramEntry.COLUMN_YIELD;

    LongSparseArray<ComplexMap> mComplexMap = new LongSparseArray<>();

    LongSparseArray<Map> mEngramIdsByName = new LongSparseArray<>();
    LongSparseArray<Map> mResourceIdsByName = new LongSparseArray<>();
    LongSparseArray<Map> mStationIdsByName = new LongSparseArray<>();

    LongSparseArray<String> mResourceNamesById = new LongSparseArray<>();
    LongSparseArray<String> mStationNamesById = new LongSparseArray<>();

    LongSparseArray<TotalConversion> mTotalConversion = new LongSparseArray<>();

    SparseArray<Long> mDlcIds = new SparseArray<>();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update_json );

        updateConsole( "App started, executing task..." );

        new ParseConvertTask( this ).execute();
    }

    void updateConsole( String text ) {
        TextView console = ( TextView ) findViewById( R.id.console );
        console.append( text + "\n" );
    }

    private class ParseConvertTask extends AsyncTask<Void, String, Void> {
        private final String TAG = ParseConvertTask.class.getSimpleName();

        private Context mContext;

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

            updateConsole( "Execution complete!" );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            try {
                // first, let's read our json file to a string
                publishProgress( "Reading JSON from file..." );
                String jsonString = JsonUtil.readRawJsonFileToJsonString( getContext(), R.raw.data_editable );

                // parse json string into a json object
                publishProgress( "Parsing JSON string into object..." );
                JSONObject oldObject = new JSONObject( jsonString );

                /***** EDIT JSON FILE CHANGES BETWEEN THESE COMMENTS *****/

                publishProgress( "** Beginning conversion..." );

                // convert json into new json layout
                JSONObject newObject = convertJSONObjectToIds( oldObject );

                publishProgress( "** Conversion complete!..." );

                /***** EDIT JSON FILE CHANGES BETWEEN THESE COMMENTS *****/

                // finally, write new json object to file
                publishProgress( "Writing new JSON object to file..." );
                writeJSONObjectToFile( newObject );
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

        void writeJSONObjectToFile( JSONObject object ) throws IOException {

            File path = getContext().getExternalFilesDir( null );
            File file = new File( path, "jsonExport.txt" );

            FileOutputStream fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( object.toString().getBytes() );
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

            // insert old dlc json data, no need to convert at this time
            publishProgress( "Inserting DLC values..." );
            outObject.put( DLCEntry.TABLE_NAME,
                    inObject.getJSONArray( DLCEntry.TABLE_NAME ) );

            mDlcIds = mapDLCJSONArray( inObject.getJSONArray( DLCEntry.TABLE_NAME ) );
            mTotalConversion = mapTotalConversion( inObject.getJSONArray( TotalConversionEntry.TABLE_NAME ) );

            // convert, then insert new station json data
            publishProgress( "Converting station values..." );
            outObject.put( StationEntry.TABLE_NAME,
                    convertStationJSONArrayToIds( inObject.getJSONArray( StationEntry.TABLE_NAME ) ) );

            // convert, then insert new category json data
            publishProgress( "Converting category values..." );
            outObject.put( CategoryEntry.TABLE_NAME,
                    convertCategoryJSONArrayToIds( inObject.getJSONArray( CategoryEntry.TABLE_NAME ) ) );

            // convert, then insert new resource json data
            publishProgress( "Converting resource values..." );
            outObject.put( ResourceEntry.TABLE_NAME,
                    convertResourceJSONArrayToIds( inObject.getJSONArray( ResourceEntry.TABLE_NAME ) ) );

            // convert, then insert new engram json data
            publishProgress( "Converting engram values..." );
            outObject.put( EngramEntry.TABLE_NAME,
                    convertEngramJSONArrayToIds( inObject.getJSONArray( EngramEntry.TABLE_NAME ) ) );

            // convert, then insert new complex resource json data
            publishProgress( "Converting complex resource values..." );
            outObject.put( ComplexResourceEntry.TABLE_NAME,
                    convertComplexResourceMapIdsToJSONArray() );

            // convert, then insert new total conversion json data
            publishProgress( "Converting total conversion values..." );
            outObject.put( TotalConversionEntry.TABLE_NAME,
                    convertTotalConversionJSONArrayToIds( inObject.getJSONArray( TotalConversionEntry.TABLE_NAME ) ) );

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
                JSONArray stationArray = object.getJSONArray( StationEntry.TABLE_NAME );
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

            // TODO: 3/5/2017 Check Engram names based off of dlc_id (ex: Broth of Enlightenment)

            for ( int i = 0; i < inArray.length(); i++ ) {
                JSONObject object = inArray.getJSONObject( i );

                String name = object.getString( COLUMN_NAME );
                String description = object.getString( COLUMN_DESCRIPTION );
                String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
                String imageFile = object.getString( COLUMN_IMAGE_FILE );
                Integer yield = object.getInt( COLUMN_YIELD );
                Integer level = object.getInt( COLUMN_LEVEL );
                Integer category_id = object.getInt( COLUMN_CATEGORY_KEY );
                JSONArray compositionArray = object.getJSONArray( CompositionEntry.TABLE_NAME );
                JSONArray stationArray = object.getJSONArray( StationEntry.TABLE_NAME );
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
                convertedObject.put( CompositionEntry.TABLE_NAME, convertedComposition );

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
                JSONArray resourceArray = object.getJSONArray( ResourceEntry.TABLE_NAME );
//                JSONArray engramArray = object.getJSONArray( EngramEntry.TABLE_NAME );

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
                convertedObject.put( ResourceEntry.TABLE_NAME, convertedResourceArray );
//                convertedObject.put( EngramEntry.TABLE_NAME, convertedEngramArray );

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
            outObject.put( DLCEntry.TABLE_NAME,
                    inObject.getJSONArray( DLCEntry.TABLE_NAME ) );

            // convert, then insert new station json data
            publishProgress( "Converting station values..." );
            outObject.put( StationEntry.TABLE_NAME,
                    convertStationJSONArrayToNames( inObject.getJSONArray( StationEntry.TABLE_NAME ) ) );

            // convert, then insert new category json data
            publishProgress( "Converting category values..." );
            outObject.put( CategoryEntry.TABLE_NAME,
                    convertCategoryJSONArrayToNames( inObject.getJSONArray( CategoryEntry.TABLE_NAME ) ) );

            // convert, then insert new resource json data
            publishProgress( "Converting resource values..." );
            outObject.put( ResourceEntry.TABLE_NAME,
                    convertResourceJSONArrayToNames( inObject.getJSONArray( ResourceEntry.TABLE_NAME ) ) );

            // convert, then insert new engram json data
            publishProgress( "Converting engram values..." );
            outObject.put( EngramEntry.TABLE_NAME,
                    convertEngramJSONArrayToNames( inObject.getJSONArray( EngramEntry.TABLE_NAME ) ) );

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
                convertedObject.put( StationEntry.TABLE_NAME, new JSONArray( stations ) );

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
                JSONArray compositionArray = object.getJSONArray( CompositionEntry.TABLE_NAME );
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
                convertedObject.put( CompositionEntry.TABLE_NAME, convertedComposition );

                // convert station object array from ids to names
                String[] stations = mapLocalStationNames( stationArray );

                // insert converted station array into engram json object
                convertedObject.put( StationEntry.TABLE_NAME, new JSONArray( stations ) );

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

//            for ( int i = 0; i < mDlcIds.size(); i++ ) {
//                long d = mDlcIds.valueAt( i );
//
//                if ( mEngramIdsByName.get( d ) == null )
//                    continue;
//
//                if ( !mEngramIdsByName.get( d ).contains( name ) )
//                    continue;
//
//                Log.d( TAG, d + ", " + _id + ", " + name );
//            }

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

    LongSparseArray<TotalConversion> mapTotalConversion( JSONArray jsonArray ) throws JSONException {
        LongSparseArray<TotalConversion> map = new LongSparseArray<>();

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject jsonObject = jsonArray.getJSONObject( i );

            long dlc_id = jsonObject.getLong( DatabaseContract.TotalConversionEntry.COLUMN_DLC_KEY );
            JSONArray resourceArray = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.TABLE_NAME );
            JSONArray engramArray = jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME );

            TotalConversion totalConversion = new TotalConversion();
            for ( int j = 0; j < resourceArray.length(); j++ ) {
                JSONObject object = resourceArray.getJSONObject( j );

                String from = object.getString( DatabaseContract.TotalConversionEntry.COLUMN_FROM );
                String to = object.getString( DatabaseContract.TotalConversionEntry.COLUMN_TO );

                totalConversion.resources.put( from, to );
            }

            for ( int j = 0; j < engramArray.length(); j++ )
                totalConversion.engrams.add( engramArray.getString( j ) );

            map.put( dlc_id, totalConversion );
        }

        return map;
    }

    class TotalConversion {
        List<String> engrams;
        HashMap<String, String> resources;

        TotalConversion() {
            engrams = new ArrayList<>();
            resources = new HashMap<>();
        }
    }

    class ComplexMap {
        private HashMap<String, Long> resources;
        private HashMap<String, Long> engrams;

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

    class Map {
        private HashMap<String, Long> map;

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
        String imageFile;
        String imageFolder;
        String name;
        String parent;

        int[] dlc_ids;
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
        String category;
        String description;
        String imageFile;
        String imageFolder;
        String name;

        Integer level;
        Integer yield;

        LongSparseArray<Integer> composition;

        int[] dlc_ids;
        int[] station_ids;

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
        String imageFile;
        String imageFolder;
        String name;

        int[] dlc_ids;

        Resource( String name, String imageFolder, String imageFile, int[] dlc_ids ) {
            this.name = name;
            this.imageFolder = imageFolder;
            this.imageFile = imageFile;
            this.dlc_ids = dlc_ids;
        }
    }

    class Station {
        String imageFile;
        String imageFolder;
        String name;

        int[] dlc_ids;

        Station( String name, String imageFolder, String imageFile, int[] dlc_ids ) {
            this.name = name;
            this.imageFolder = imageFolder;
            this.imageFile = imageFile;
            this.dlc_ids = dlc_ids;
        }
    }
}
