package arc.resource.calculator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.helpers.Helper;

public class UpdateJSONActivity extends AppCompatActivity {
    private static final String TAG = UpdateJSONActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update_json );

        Helper.Log( TAG, "OnCreate()" );
        new ParseConvertTask( this ).execute();
    }

    private class ParseConvertTask extends AsyncTask<Void, Void, Void> {
        private final String TAG = ParseConvertTask.class.getSimpleName();

        private Context mContext;

        public ParseConvertTask( Context context ) {
            this.mContext = context;
        }

        @Override
        protected void onPostExecute( Void aVoid ) {
            super.onPostExecute( aVoid );

            Helper.Log( TAG, "onPostExecute()" );
        }

        @Override
        protected Void doInBackground( Void... params ) {
            try {
                String jsonString =
                        readJSONFileToString();

                JSONObject jsonObject =
                        parseStringToJSONObject( jsonString );

                File path = mContext.getExternalFilesDir( null );
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

            return jsonString;
        }

        JSONObject parseStringToJSONObject( String jsonString ) throws JSONException {
            JSONObject jsonObject = new JSONObject( jsonString );

            // Place what tables to update here
            jsonObject.put(
                    DatabaseContract.EngramEntry.TABLE_NAME,
                    updateJSONArray( jsonObject.getJSONArray( DatabaseContract.EngramEntry.TABLE_NAME ) )
            );

            return jsonObject;
        }

        JSONArray updateJSONArray( JSONArray jsonArray ) throws JSONException {
            for ( int i = 0; i < jsonArray.length(); i++ ) {
                jsonArray.getJSONObject( i ).put( DatabaseContract.EngramEntry.COLUMN_YIELD, 1 );
            }

            Helper.Log( TAG, jsonArray.toString() );

            return jsonArray;
        }

        Context getContext() {
            return mContext;
        }
    }
}
