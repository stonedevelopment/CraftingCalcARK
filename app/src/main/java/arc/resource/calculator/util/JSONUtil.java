package arc.resource.calculator.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONUtil {
    private static final String TAG = JSONUtil.class.getSimpleName();

    public static String readRawJsonFileToJsonString( Context context, int json_resource_file ) throws IOException {
        String jsonString;

        InputStream fileStream = context.getResources().openRawResource( json_resource_file );
        try ( BufferedReader fileReader = new BufferedReader( new InputStreamReader( fileStream ) ) ) {
            StringBuffer buffer = new StringBuffer();

            String line;
            while ( ( line = fileReader.readLine() ) != null ) {
                buffer.append( line + "\n" );
            }

            jsonString = buffer.toString();
        }

        return jsonString;
    }

    public static String readRawJsonFileToJsonString( Context context, String json_resource_file ) throws IOException {
        String jsonString;

        InputStream fileStream = context.getAssets().open( json_resource_file );
        try ( BufferedReader fileReader = new BufferedReader( new InputStreamReader( fileStream ) ) ) {
            StringBuffer buffer = new StringBuffer();

            String line;
            while ( ( line = fileReader.readLine() ) != null ) {
                buffer.append( line + "\n" );
            }

            jsonString = buffer.toString();
        }

        return jsonString;
    }
}
