package arc.resource.calculator.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.util.JSONUtil;
import arc.resource.calculator.util.PrefsUtil;

public class ParseConvertTask extends AsyncTask<Void, Void, Boolean> {
    private final String TAG = ParseConvertTask.class.getSimpleName();

    private static final long INVALID_ID = -1;

    private final String _ID = DatabaseContract.EngramEntry._ID;

    // column names
    private final String COLUMN_CATEGORY_KEY = DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY;
    private final String COLUMN_CONSOLE_ID = DatabaseContract.EngramEntry.COLUMN_CONSOLE_ID;
    private final String COLUMN_DESCRIPTION = DatabaseContract.EngramEntry.COLUMN_DESCRIPTION;
    private final String COLUMN_DLC_KEY = DatabaseContract.EngramEntry.COLUMN_DLC_KEY;
    private final String COLUMN_ENGRAM_KEY = DatabaseContract.CompositionEntry.COLUMN_ENGRAM_KEY;
    private final String COLUMN_FROM = DatabaseContract.TotalConversionEntry.COLUMN_FROM;
    private final String COLUMN_IMAGE_FILE = DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE;
    private final String COLUMN_IMAGE_FOLDER = DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER;
    private final String COLUMN_LEVEL = DatabaseContract.EngramEntry.COLUMN_LEVEL;
    private final String COLUMN_NAME = DatabaseContract.EngramEntry.COLUMN_NAME;
    private final String COLUMN_PARENT_KEY = DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY;
    private final String COLUMN_POINTS = DatabaseContract.EngramEntry.COLUMN_POINTS;
    private final String COLUMN_QUANTITY = DatabaseContract.CompositionEntry.COLUMN_QUANTITY;
    private final String COLUMN_RESOURCE_KEY = DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY;
    private final String COLUMN_STATION_KEY = DatabaseContract.EngramEntry.COLUMN_STATION_KEY;
    private final String COLUMN_STEAM_ID = DatabaseContract.EngramEntry.COLUMN_STEAM_ID;
    private final String COLUMN_TO = DatabaseContract.TotalConversionEntry.COLUMN_TO;
    private final String COLUMN_VERSION = "version";
    private final String COLUMN_YIELD = DatabaseContract.EngramEntry.COLUMN_YIELD;
    private final String COLUMN_XP = DatabaseContract.EngramEntry.COLUMN_XP;
    private final String COLUMN_FAVORITE = DatabaseContract.EngramEntry.COLUMN_FAVORITE;

    // table names
    private final String JSON = "json";
    private final String DLC = DatabaseContract.DLCEntry.TABLE_NAME;
    private final String STATION = DatabaseContract.StationEntry.TABLE_NAME;
    private final String CATEGORY = DatabaseContract.CategoryEntry.TABLE_NAME;
    private final String RESOURCE = DatabaseContract.ResourceEntry.TABLE_NAME;
    private final String ENGRAM = DatabaseContract.EngramEntry.TABLE_NAME;
    private final String COMPLEX_RESOURCE = DatabaseContract.ComplexResourceEntry.TABLE_NAME;
    private final String COMPOSITION = DatabaseContract.CompositionEntry.TABLE_NAME;
    private final String TOTAL_CONVERSION = DatabaseContract.TotalConversionEntry.TABLE_NAME;
    private final String RECIPES = "recipes";
    private final String KIBBLE = "kibble";

    // specific json filenames
    private final class JSONFile {
        private static final int MAIN = R.raw.data;
        private static final int KIBBLE = R.raw.kibble;
        private static final int DYES = R.raw.dyes;
        private static final int SADDLES = R.raw.saddles;
        private static final int PLATFORM_SADDLES = R.raw.saddles_platform;
        private static final int TEK_SADDLES = R.raw.saddles_tek;
    }

    private final LongSparseArray<ComplexMap> mComplexMap = new LongSparseArray<>();

    private final LongSparseArray<Map> mEngramIdsByName = new LongSparseArray<>();
    private final LongSparseArray<Map> mResourceIdsByName = new LongSparseArray<>();
    private final LongSparseArray<Map> mStationIdsByName = new LongSparseArray<>();

    private LongSparseArray<TotalConversion> mTotalConversion = new LongSparseArray<>();

    private SparseArray<Long> mDlcIds = new SparseArray<>();

    private Context mContext;
    private String mVersion;
    private JSONObject mJSONObject;
    private Listener mListener;

    // caught exception
    private Exception mException;

    public interface Listener {

        /**
         * Event handler for caught exceptions
         *
         * @param e Caught exception
         */
        void onError( Exception e );

        /**
         * Pre-initialization event, can be used to alert user of preparations
         */
        void onInit();


        void onNewVersion( String oldVersion, String newVersion );

        void onStart();

        void onUpdate( String message );

        void onFinish();

        void onFinish( JSONObject newObject );
    }

    public ParseConvertTask( Context context, Listener listener ) {
        setContext( context );
        setListener( listener );
    }

    private void setContext( Context context ) {
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    private void setVersion( String version ) {
        mVersion = version;
    }

    private String getVersion() {
        return mVersion;
    }

    private void setListener( Listener listener ) {
        mListener = listener;
    }

    private Listener getListener() {
        return mListener;
    }

    @Override
    protected void onPreExecute() {
        getListener().onInit();
    }

    @Override
    protected void onPostExecute( Boolean didUpdate ) {
        if ( mException == null )
            if ( didUpdate )
                getListener().onFinish( mJSONObject );
            else
                getListener().onFinish();
        else
            getListener().onError( mException );
    }

    @Override
    protected Boolean doInBackground( Void... params ) {
        try {
            // read the local converted json file into a string
            String jsonString = JSONUtil.readRawJsonFileToJsonString( getContext(), JSONFile.MAIN );

            // build a json object based on the read json string
            JSONObject jsonObject = new JSONObject( jsonString );

            JSONObject json = jsonObject.getJSONObject( JSON );
            String oldVersion = PrefsUtil.getInstance( mContext ).getJSONVersion();
            String newVersion = json.getString( COLUMN_VERSION );

            // now, let's check if we even need to update.
            if ( !isNewVersion( oldVersion, newVersion ) )
                return false;

            setVersion( newVersion );

            // emit new version event
            getListener().onNewVersion( oldVersion, newVersion );

            // new version! let's get cracking!
            // let user know that we've begun the process.
            getListener().onStart();

            mJSONObject = convertJSONObjectToIds( jsonObject );

            return true;
        }

        // If error, send error signal, onPause service
        catch ( Exception e ) {
            mException = e;
            return false;
        }
    }

    private boolean isNewVersion( String oldVersion, String newVersion ) {
        return !Objects.equals( oldVersion, newVersion );
    }

    private void updateStatus( String statusMessage ) {
        getListener().onUpdate( statusMessage );
    }

    /**
     * Convert editable json object into json data object
     *
     * @param inObject editable json object with names
     * @return new json object with ids as names
     * @throws JSONException
     */
    private JSONObject convertJSONObjectToIds( JSONObject inObject ) throws Exception {
        JSONObject outObject = new JSONObject();

        // insert json data with updated versioning
        outObject.put( JSON, inObject.getJSONObject( JSON ) );

        // insert old dlc json data, no need to convert at this time
        outObject.put( DLC,
                inObject.getJSONArray( DLC ) );

        mDlcIds = mapDLCJSONArray( inObject.getJSONArray( DLC ) );
        mTotalConversion = mapTotalConversion( inObject.getJSONArray( TOTAL_CONVERSION ) );

        // convert, then insert new station json data
        outObject.put( STATION,
                convertStationJSONArrayToIds( inObject.getJSONArray( STATION ) ) );

        // convert, then insert new category json data
        outObject.put( CATEGORY,
                convertCategoryJSONArrayToIds( inObject.getJSONArray( CATEGORY ) ) );

        // convert, then insert new resource json data
        outObject.put( RESOURCE,
                convertResourceJSONArrayToIds( inObject.getJSONArray( RESOURCE ) ) );

        // convert, then insert new engram json data

        // convert raw engram json data into json array
        JSONArray engramArray = convertEngramJSONArrayToIds( inObject.getJSONArray( ENGRAM ) );

        // instantiate an index counter for additional data to be created where engram json data left off
        int index = engramArray.length();

        // convert kibble json data into json array
        JSONArray kibbleArray = convertKibbleJSONObjectToIds( index );

        // increment index to accommodate for kibble array
        index += kibbleArray.length();

        // convert dyes json data into json array
        // convert saddles json data into json array
        // convert platform saddles json data into json array
        // convert tek saddles json data into json array

        outObject.put( ENGRAM, engramArray );

        // convert, then insert new complex resource json data
        outObject.put( COMPLEX_RESOURCE,
                convertComplexResourceMapIdsToJSONArray() );

        // convert, then insert new total conversion json data
        outObject.put( TOTAL_CONVERSION,
                convertTotalConversionJSONArrayToIds( inObject.getJSONArray( TOTAL_CONVERSION ) ) );

        return outObject;
    }

    private SparseArray<Long> mapDLCJSONArray( JSONArray inArray ) throws JSONException {
        SparseArray<Long> map = new SparseArray<>();

        for ( int i = 0; i < inArray.length(); i++ ) {
            JSONObject object = inArray.getJSONObject( i );

            long _id = object.getLong( _ID );
            String name = object.getString( COLUMN_NAME );

            map.put( map.size(), _id );
        }

        return map;
    }

    private JSONArray convertStationJSONArrayToIds( JSONArray inArray ) throws Exception {
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

    private JSONArray convertCategoryJSONArrayToIds( JSONArray inArray ) throws Exception {
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
            convertedObject.put( DatabaseContract.CategoryEntry.COLUMN_STATION_KEY, new JSONArray( stations.toArray() ) );

            // insert converted object into converted array
            outArray.put( convertedObject );
        }

        return outArray;
    }

    private JSONArray convertResourceJSONArrayToIds( JSONArray inArray ) throws Exception {
        JSONArray outArray = new JSONArray();

        for ( int i = 0; i < inArray.length(); i++ ) {
            JSONObject object = inArray.getJSONObject( i );

            String name = object.getString( COLUMN_NAME );
            String imageFolder = object.getString( COLUMN_IMAGE_FOLDER );
            String imageFile = object.getString( COLUMN_IMAGE_FILE );
            long dlc_id = object.getInt( COLUMN_DLC_KEY );
            boolean hasComplexity = object.getBoolean( DatabaseContract.ComplexResourceEntry.TABLE_NAME );

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

    private JSONArray convertEngramJSONArrayToIds( JSONArray inArray ) throws Exception {
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

            // insert blank favorite data, used only for kibble
            convertedObject.put( COLUMN_FAVORITE, new JSONArray( "[\"\"]" ) );

            // convert composition object array from names to ids
            JSONArray convertedComposition = new JSONArray();
            for ( int j = 0; j < compositionArray.length(); j++ ) {
                JSONObject oldObject = compositionArray.getJSONObject( j );

                String resource_name = oldObject.getString( COLUMN_RESOURCE_KEY );
                int quantity = oldObject.getInt( COLUMN_QUANTITY );

                // attempt to grab id of resource from previously mapped list
                long resource_id = getResourceIdByName( dlc_id, resource_name );

                // if invalid (which should never happen) continue to next resource
                if ( resource_id == INVALID_ID ) {
                    Log.e( TAG, "convertEngramJSONArrayToIds: resource_name " + resource_name + " does not exist in dlc_id " + dlc_id );
                    continue;
                }

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
            convertedObject.put( DatabaseContract.EngramEntry.COLUMN_STATION_KEY, new JSONArray( stations.toArray() ) );

            // insert converted object into converted array
            outArray.put( convertedObject );
        }

        return outArray;
    }

    private JSONArray convertKibbleJSONObjectToIds( int index ) throws Exception {
        // read the local converted json file into a string
        String jsonString = JSONUtil.readRawJsonFileToJsonString( getContext(), JSONFile.KIBBLE );

        // build a json object based on the read json string
        JSONObject inObject = new JSONObject( jsonString );

        // Kibble (%1$s)
        String nameFormat = inObject.getString( COLUMN_NAME );

        String description = inObject.getString( COLUMN_DESCRIPTION );
        String imageFolder = inObject.getString( COLUMN_IMAGE_FOLDER );
        String imageFile = inObject.getString( COLUMN_IMAGE_FILE );
        Integer yield = inObject.getInt( COLUMN_YIELD );
        Integer level = inObject.getInt( COLUMN_LEVEL );
        Integer category_id = inObject.getInt( COLUMN_CATEGORY_KEY );
        long dlc_id = inObject.getLong( COLUMN_DLC_KEY );
        JSONArray compositionArray = inObject.getJSONArray( COMPOSITION );
        JSONArray stationArray = inObject.getJSONArray( STATION );
        JSONArray recipeArray = inObject.getJSONArray( RECIPES );

        // instantiate a JSONArray to export
        JSONArray outArray = new JSONArray();
        for ( int i = 0; i < recipeArray.length(); i++ ) {
            JSONObject object = recipeArray.getJSONObject( i );

            // format name Kibble (%1$s) -> Kibble (Gallimimus Egg)
            String objectName = object.getString( COLUMN_NAME );
            String name = String.format( nameFormat, objectName );

            // check if dlc_id should be overridden or not
            long objectDlc_id = object.optLong( COLUMN_DLC_KEY, INVALID_ID );
            if ( objectDlc_id > dlc_id )
                dlc_id = objectDlc_id;

            // create an array of qualified dlc versions, minus total conversions
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

            // create an id based on counter variable, i, plus index variable, index
            long _id = i + index;

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

            // insert favorites, could possibly be ""
            convertedObject.put( COLUMN_FAVORITE, object.getJSONArray( "favorite" ) );

            // append to global composition array with local composition
            JSONArray objectComposition = object.getJSONArray( COMPOSITION );
            for ( int j = 0; j < objectComposition.length(); j++ )
                compositionArray.put( objectComposition.getJSONObject( j ) );

            // convert composition object array from names to ids
            JSONArray convertedComposition = new JSONArray();
            for ( int j = 0; j < compositionArray.length(); j++ ) {
                JSONObject oldObject = compositionArray.getJSONObject( j );

                String resource_name = oldObject.getString( COLUMN_RESOURCE_KEY );
                int quantity = oldObject.getInt( COLUMN_QUANTITY );

                // attempt to grab id of resource from previously mapped list
                long resource_id = getResourceIdByName( dlc_id, resource_name );

                // if resource_id is invalid, continue to next
                // this should only occur if a resource wasn't added into json file
                if ( resource_id == INVALID_ID ) {
                    Log.e( TAG, "convertKibbleJSONObjectToIds: resource_name " + resource_name + " does not exist in dlc_id " + dlc_id );
                    continue;
                }

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
            convertedObject.put( DatabaseContract.EngramEntry.COLUMN_STATION_KEY, new JSONArray( stations.toArray() ) );

            // insert converted object into converted array
            outArray.put( convertedObject );
        }

        return outArray;
    }

    private JSONArray convertTotalConversionJSONArrayToIds( JSONArray inArray ) throws Exception {
        JSONArray outArray = new JSONArray();

        for ( int i = 0; i < inArray.length(); i++ ) {
            JSONObject object = inArray.getJSONObject( i );

            long dlc_id = object.getInt( COLUMN_DLC_KEY );
            JSONArray resourceArray = object.getJSONArray( RESOURCE );

            JSONArray convertedResourceArray = new JSONArray();
            for ( int j = 0; j < resourceArray.length(); j++ ) {
                JSONObject oldObject = resourceArray.getJSONObject( j );

                String name = oldObject.getString( COLUMN_FROM );
                String converted_name = oldObject.getString( COLUMN_TO );

                // attempt to grab ids from previously mapped list
                long _id = getResourceIdByName( dlc_id, name );
                long converted_id = getResourceIdByName( dlc_id, converted_name );

                // if invalid (which should never happen) continue to next json object
                if ( _id == INVALID_ID || converted_id == INVALID_ID ) {
                    if ( _id == INVALID_ID )
                        Log.e( TAG, "convertTotalConversionJSONArrayToIds: name " + name + " does not exist in dlc_id " + dlc_id );

                    if ( converted_id == INVALID_ID )
                        Log.e( TAG, "convertTotalConversionJSONArrayToIds: converted_name " + converted_name + " does not exist in dlc_id " + dlc_id );

                    continue;
                }

                // build new json object with its matched ids
                JSONObject newObject = new JSONObject();
                newObject.put( COLUMN_FROM, _id );
                newObject.put( COLUMN_TO, converted_id );

                // insert new object into json array
                convertedResourceArray.put( newObject );
            }

            // instantiate a new json object used to be converted into new layout
            JSONObject convertedObject = new JSONObject();

            // insert standardized data
            convertedObject.put( COLUMN_DLC_KEY, dlc_id );
            convertedObject.put( RESOURCE, convertedResourceArray );

            // insert converted object into converted array
            outArray.put( convertedObject );
        }

        return outArray;
    }

    private JSONArray convertComplexResourceMapIdsToJSONArray() throws JSONException {
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

    private long getStationIdByName( String name ) throws Exception {
        for ( int i = 0; i < mStationIdsByName.size(); i++ ) {
            if ( mStationIdsByName.valueAt( i ).contains( name ) )
                return mStationIdsByName.valueAt( i ).get( name );
        }

        throw new Exception( "getStationIdByName() didn't find name: " + name );
    }

    private long getResourceIdByName( long dlc_id, String name ) throws Exception {
        try {
            return mResourceIdsByName.get( dlc_id ).get( name );
        } catch ( Exception e ) {
            return mResourceIdsByName.get( mDlcIds.valueAt( 0 ) ).get( name, INVALID_ID );
        }
    }

    private long getEngramIdByName( long dlc_id, String name ) throws Exception {
        try {
            return mEngramIdsByName.get( dlc_id ).get( name );
        } catch ( Exception e ) {
            return mEngramIdsByName.get( mDlcIds.valueAt( 0 ) ).get( name );
        }
    }

    private void addComplexEngramIdByName( long dlc_id, String name, long _id ) throws Exception {
        if ( mComplexMap.indexOfKey( dlc_id ) < 0 )
            mComplexMap.put( dlc_id, new ComplexMap() );

        mComplexMap.get( dlc_id ).addEngram( name, _id );
    }

    private void addComplexResourceIdByName( long dlc_id, String name, long _id ) throws Exception {
        if ( mComplexMap.indexOfKey( dlc_id ) < 0 )
            mComplexMap.put( dlc_id, new ComplexMap() );

        mComplexMap.get( dlc_id ).addResource( name, _id );
    }

    private void addStationIdByName( long dlc_id, String name, long _id ) throws Exception {
        if ( mStationIdsByName.get( dlc_id ) == null )
            mStationIdsByName.put( dlc_id, new Map() );

        mStationIdsByName.get( dlc_id ).add( name, _id );
    }

    private void addResourceIdByName( long dlc_id, String name, long _id ) throws Exception {
        if ( mResourceIdsByName.get( dlc_id ) == null )
            mResourceIdsByName.put( dlc_id, new Map() );

        mResourceIdsByName.get( dlc_id ).add( name, _id );
    }

    private void addEngramIdByName( long dlc_id, String name, long _id ) throws Exception {
        if ( mEngramIdsByName.get( dlc_id ) == null )
            mEngramIdsByName.put( dlc_id, new Map() );

        mEngramIdsByName.get( dlc_id ).add( name, _id );
    }

    /**
     * Maps all station ids within incoming json array by sifting through all dlc ids
     *
     * @param inArray json array containing a list of station names
     * @return id of matched station, in all dlc ids
     */
    private List<Long> mapLocalStationIds( JSONArray inArray ) throws Exception {
        List<Long> ids = new ArrayList<>();

        for ( int i = 0; i < inArray.length(); i++ ) {
            String name = inArray.getString( i );
            long station_id = getStationIdByName( name );

            ids.add( station_id );
        }

        return ids;
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
                throw new Exception( "name doesn't exist: " + name );
        }

        long get( String name, long returnable ) {
            if ( contains( name ) )
                return map.get( name );
            else
                return returnable;
        }
    }
}