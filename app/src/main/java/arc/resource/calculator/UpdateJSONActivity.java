package arc.resource.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

import arc.resource.calculator.db.DatabaseContract;

public class UpdateJSONActivity extends AppCompatActivity {
    private static final String TAG = UpdateJSONActivity.class.getSimpleName();

    JSONArray mUpdatedJsonArray = new JSONArray();
    JSONArray mResourceJsonArray = new JSONArray();
    JSONArray mEngramJsonArray = new JSONArray();
    JSONArray mComplexResourceJsonArray = new JSONArray();
    JSONArray mConversionJsonArray = new JSONArray();

    SparseArray<Resource> mResourceMap = new SparseArray<>();
    SparseArray<Engram> mEngramMap = new SparseArray<>();
    SparseArray<Engram> mIncompleteEngramMap = new SparseArray<>();

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
                String jsonString =
                        readJSONFileToString();

                JSONObject jsonObject =
                        parseStringToJSONObject( jsonString );

                publishProgress( "Writing JSON to file..." );
                File path = getContext().getExternalFilesDir( null );
                File file = new File( path, "jsonExport.txt" );

                try ( FileOutputStream fileOutputStream = new FileOutputStream( file ) ) {
                    fileOutputStream.write( jsonObject.toString().getBytes() );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            } catch ( JSONException e ) {
                e.printStackTrace();
            }
            return null;
        }

        String readJSONFileToString() {
            publishProgress( "Reading JSON file..." );

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
                publishProgress( "Error: " + e.getMessage() );
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

            return jsonString;
        }

        JSONObject parseStringToJSONObject( String jsonString ) throws JSONException {
            publishProgress( "Parsing JSON String into Objects..." );

            JSONObject jsonObject = new JSONObject( jsonString );

            // Pull conversion table from file
            mConversionJsonArray = jsonObject.getJSONArray( "conversion" );

            // Place what tables to update here
            publishProgress( "Updating Engrams..." );
            mEngramJsonArray = updateEngramJSONArray( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) );

            jsonObject.put(
                    DatabaseContract.EngramEntry.TABLE_NAME,
                    updateEngramJSONArray( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) ) );

            return jsonObject;
        }

        JSONArray updateResourceJSONArray( JSONArray jsonArray ) throws JSONException {
            publishProgress( " - Parsing JSONObjects into SparseArray.." );

            SparseArray<Resource> resources = new SparseArray<>();

            // Fill Resource SparseArray with JSONArray data
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                String name = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_NAME );
                String drawable = jsonObject.getString( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE );
                JSONArray dlcJsonArray = jsonObject.getJSONArray( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY );

                int[] dlc_ids = new int[dlcJsonArray.length()];
                for ( int j = 0; j < dlcJsonArray.length(); j++ ) {
                    dlc_ids[j] = dlcJsonArray.getInt( j );
                }

                resources.put(
                        resources.size(),
                        new Resource( name, drawable, dlc_ids )
                );
            }

            // Sort Resource SparseArray by its name
            publishProgress( " - Sorting Resource SparseArray.." );
            resources = sortResourcesByName( resources.clone() );

            // Create new JSONArray, add _id element
            publishProgress( " - Converting SparseArray into JSONArray.." );
            JSONArray updatedJsonArray = new JSONArray();
            for ( int i = 0; i < resources.size(); i++ ) {
                Resource resource = resources.valueAt( i );

                JSONObject jsonObject = new JSONObject();
                jsonObject.put( DatabaseContract.ResourceEntry._ID, i );
                jsonObject.put( DatabaseContract.ResourceEntry.COLUMN_NAME, resource.name );
                jsonObject.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, resource.drawable );
                jsonObject.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, new JSONArray( resource.dlc_ids ) );

                publishProgress( "  - " + jsonObject.toString() );
                updatedJsonArray.put( jsonObject );

                mResourceMap.put( i, resource );
            }

            publishProgress( "Updating complete!" );
            return updatedJsonArray;
        }

        SparseArray<Resource> sortResourcesByName( SparseArray<Resource> resources ) {
            boolean swapped = true;
            while ( swapped ) {
                swapped = false;
                for ( int i = 0; i < resources.size() - 1; i++ ) {
                    String first = resources.valueAt( i ).name;
                    String second = resources.valueAt( i + 1 ).name;
                    if ( first.compareTo( second ) > 0 ) {
                        Resource tempResource = resources.valueAt( i + 1 );
                        resources.put( i + 1, resources.valueAt( i ) );
                        resources.put( i, tempResource );
                        swapped = true;
                    }
                }
            }

            return resources;
        }

        private int getResourceId( String drawable, int[] dlc_ids ) {
            boolean breakLoop = false;

            for ( int i = 0; i < mResourceMap.size(); i++ ) {
                Resource resource = mResourceMap.valueAt( i );

                if ( drawable.equals( resource.drawable ) ) {
                    for ( int dlc_id : dlc_ids ) {
                        for ( int k = 0; k < resource.dlc_ids.length; k++ ) {
                            if ( dlc_id == resource.dlc_ids[k] ) {
                                return i;
                            } else if ( resource.dlc_ids.length > 1 ) {
                                breakLoop = false;
                            } else {
                                breakLoop = true;
                                break;
                            }
                        }

                        if ( breakLoop ) {
                            break;
                        }
                    }
                }
            }

            return -1;
        }

        JSONArray updateEngramJSONArray( JSONArray jsonArray ) throws JSONException {
            publishProgress( " - Parsing JSONObjects into SparseArray.." );

            SparseArray<Engram> engrams = new SparseArray<>();

            // Fill Engram SparseArray with JSONArray data
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject( i );

                String name = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_NAME );
                String drawable = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DRAWABLE );
                String description = jsonObject.getString( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION );
                Integer yield = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_YIELD );
                Integer level = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_LEVEL );
                Integer category_id = jsonObject.getInt( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY );
                JSONArray dJsonArray = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_DLC_KEY );
                JSONArray sJsonArray = jsonObject.getJSONArray( DatabaseContract.EngramEntry.COLUMN_STATION_KEY );
                JSONArray cJsonArray = jsonObject.getJSONArray( DatabaseContract.CompositionEntry.TABLE_NAME );

                int[] dlc_ids = new int[dJsonArray.length()];
                for ( int j = 0; j < dJsonArray.length(); j++ ) {
                    dlc_ids[j] = dJsonArray.getInt( j );
                }

                int[] station_ids = new int[sJsonArray.length()];
                for ( int j = 0; j < sJsonArray.length(); j++ ) {
                    station_ids[j] = sJsonArray.getInt( j );
                }

                HashMap<String, Integer> composition = new HashMap<>();
                for ( int j = 0; j < cJsonArray.length(); j++ ) {
                    JSONObject compositionJsonObject = cJsonArray.getJSONObject( j );
                    int cQuantity = compositionJsonObject.getInt( DatabaseContract.CompositionEntry.COLUMN_QUANTITY );
                    String cDrawable = compositionJsonObject.getString( DatabaseContract.CompositionEntry.COLUMN_DRAWABLE );

                    composition.put( cDrawable, cQuantity );
                }

                engrams.put(
                        engrams.size(),
                        new Engram( name, description, drawable, yield, level, composition,
                                category_id, station_ids, dlc_ids )
                );
            }

            // Sort Engram SparseArray by its name
            publishProgress( " - Sorting SparseArray.." );
            mEngramMap = sortEngramsByName( engrams.clone() );

            // Create new JSONArray, add _id element
            publishProgress( " - Converting SparseArray into JSONArray.." );
            JSONArray updatedJsonArray = convertEngramSparseArray( mEngramMap );

            publishProgress( "Updating complete!" );
            Log.d( TAG, "Updating complete!" );
            return updatedJsonArray;
        }

        JSONArray convertEngramSparseArray( SparseArray<Engram> engrams ) throws JSONException {
            mIncompleteEngramMap = new SparseArray<>();

            JSONArray updatedJsonArray = new JSONArray();
            for ( int i = 0; i < engrams.size(); i++ ) {
                int _id = engrams.keyAt( i );
                Engram engram = engrams.valueAt( i );

                JSONObject jsonObject = new JSONObject();
                jsonObject.put( DatabaseContract.EngramEntry._ID, _id );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_NAME, engram.name );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION, engram.description );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_DRAWABLE, engram.drawable );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_YIELD, engram.yield );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_LEVEL, engram.level );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY, engram.category_id );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_DLC_KEY, new JSONArray( engram.dlc_ids ) );
                jsonObject.put( DatabaseContract.EngramEntry.COLUMN_STATION_KEY, new JSONArray( engram.station_ids ) );

                JSONArray cJsonArray = new JSONArray();
                for ( String drawable : engram.composition.keySet() ) {
                    int quantity = engram.composition.get( drawable );

                    String resource_drawable = drawable;
                    int resource_id = getResourceId( drawable, engram.dlc_ids );
                    if ( resource_id < 0 ) {
                        Log.w( TAG, "Resource not found, checking " + drawable + " from Engram: " + engram.name );
                        int engram_id = getEngramId( drawable, engram.dlc_ids );
                        if ( engram_id < 0 ) {
                            publishProgress( "Incomplete Composition for Engram: " + engram.name + ", culprit: " + drawable );
                            Log.e( TAG, "Incomplete Composition for Engram: " + engram.name + ", culprit: " + drawable );
                            break;
                        }

                        resource_id = mResourceMap.size();

                        Engram cEngram = mEngramMap.get( engram_id );

                        resource_drawable = cEngram.drawable;

                        // create new resource, add it to resource jsonarray and resource map
                        JSONObject rJsonObject = new JSONObject();
                        rJsonObject.put( DatabaseContract.ResourceEntry._ID, resource_id );
                        rJsonObject.put( DatabaseContract.ResourceEntry.COLUMN_NAME, cEngram.name );
                        rJsonObject.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, cEngram.drawable );
                        rJsonObject.put( DatabaseContract.ResourceEntry.COLUMN_DLC_KEY, new JSONArray( cEngram.dlc_ids ) );
                        mResourceJsonArray.put( rJsonObject );

                        publishProgress( "- Adding New Complex Resource! _id: " + resource_id + ", name: " + cEngram.name );

                        JSONObject crJsonObject = new JSONObject();
                        crJsonObject.put( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY, resource_id );
                        crJsonObject.put( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY, engram_id );
                        mComplexResourceJsonArray.put( crJsonObject );

                        mResourceMap.put( resource_id, new Resource( cEngram.name, cEngram.drawable, cEngram.dlc_ids ) );
                    }

                    JSONObject cJsonObject = new JSONObject();
//                    cJsonObject.put( DatabaseContract.ResourceEntry.COLUMN_DRAWABLE, resource_drawable );
                    cJsonObject.put( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY, resource_id );
                    cJsonObject.put( DatabaseContract.CompositionEntry.COLUMN_QUANTITY, quantity );

                    cJsonArray.put( cJsonObject );
                }
                jsonObject.put( DatabaseContract.CompositionEntry.TABLE_NAME, cJsonArray );

                publishProgress( "  - " + jsonObject.toString() );
                updatedJsonArray.put( jsonObject );
            }

            return updatedJsonArray;
        }

        SparseArray<Engram> sortEngramsByName( SparseArray<Engram> engrams ) {
            boolean swapped = true;
            while ( swapped ) {
                swapped = false;
                for ( int i = 0; i < engrams.size() - 1; i++ ) {
                    String first = engrams.valueAt( i ).name;
                    String second = engrams.valueAt( i + 1 ).name;
                    if ( first.compareTo( second ) > 0 ) {
                        Engram tempEngram = engrams.valueAt( i + 1 );
                        engrams.put( i + 1, engrams.valueAt( i ) );
                        engrams.put( i, tempEngram );
                        swapped = true;
                    }
                }
            }

            return engrams;
        }

        // FIXME: Needs to match all dlc_ids, create separate Engram entries to match exact dlc_ids
        private int getEngramId( String drawable, int[] dlc_ids ) {
            Log.d( TAG, "** Incoming check: " + drawable + ", " + Arrays.toString( dlc_ids ) );

            boolean breakLoop = false;
            for ( int i = 0; i < mEngramMap.size(); i++ ) {
                Engram engram = mEngramMap.valueAt( i );

                if ( drawable.equals( engram.drawable ) ) {
                    Log.d( TAG, "  - Checking " + engram.drawable + " from Engram: " + engram.name );
                    for ( int dlc_id : dlc_ids ) {
                        for ( int k = 0; k < engram.dlc_ids.length; k++ ) {
                            Log.d( TAG, "    - drawable: " + drawable + ", dlc_id: " + dlc_id + ", dlc_ids: " + Arrays.toString( dlc_ids ) + ", engram.dlc_id: " + engram.dlc_ids[k] + ", engram.dlc_ids: " + Arrays.toString( engram.dlc_ids ) );
                            if ( dlc_id == engram.dlc_ids[k] ) {
                                Log.d( TAG, "    ** FOUND " + engram.name + ", " + Arrays.toString( engram.dlc_ids ) );
                                return i;
                            } else if ( engram.dlc_ids.length > 1 ) {
                                Log.e( TAG, "     - Not found, checking next engram.dlc_id since there's more than 1 available." );
                                breakLoop = false;
                            } else {
                                Log.e( TAG, "    - Not found, breaking.." );
                                breakLoop = true;
                                break;
                            }
                        }

                        if ( breakLoop ) {
                            break;
                        }
                    }
                }
            }

            return -1;
        }

        Context getContext() {
            return mContext;
        }
    }

    class Resource {
        String name;
        String drawable;
        int[] dlc_ids;

        public Resource( String name, String drawable, int[] dlc_ids ) {
            this.name = name;
            this.drawable = drawable;
            this.dlc_ids = dlc_ids.clone();
        }
    }

    class Engram {
        String name;
        String description;
        String drawable;
        Integer yield;
        Integer level;
        HashMap<String, Integer> composition;

        Integer category_id;
        int[] station_ids;
        int[] dlc_ids;

        public Engram(
                String name,
                String description,
                String drawable,
                int yield,
                int level,
                HashMap<String, Integer> composition,
                int category_id,
                int[] station_ids,
                int[] dlc_ids ) {
            this.name = name;
            this.description = description;
            this.drawable = drawable;
            this.yield = yield;
            this.level = level;
            this.composition = composition;
            this.category_id = category_id;
            this.station_ids = station_ids;
            this.dlc_ids = dlc_ids;
        }

        @Override
        public String toString() {
            return name + ", " + drawable + ", " + Arrays.toString( dlc_ids );
        }
    }
}
