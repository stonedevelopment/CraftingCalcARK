package arc.resource.calculator.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import arc.resource.calculator.R;

public class JsonUtil {
    private static final String TAG = JsonUtil.class.getSimpleName();

    public static String readRawJsonFileToJsonString( Context context ) {
        BufferedReader fileReader = null;

        try {
            InputStream fileStream = context.getResources().openRawResource( R.raw.jsonrawdata_test );
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

            return buffer.toString();
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
    }
}
