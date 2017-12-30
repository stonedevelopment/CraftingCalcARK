package arc.resource.calculator;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.json.JSONDataObject;

public class ConvertJsonFromDBActivity extends AppCompatActivity {
    private static final long STATION_ROOT = -1;
    private static final long ROOT = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_convert_json_from_db );

        TextView textView = findViewById( R.id.textView );

        new ConvertTask().execute();
    }

    static class ConvertTask extends AsyncTask<Context, Void, Void> {
        private static final String TAG = ConvertTask.class.getSimpleName();

        @Override
        protected Void doInBackground( Context... contexts ) {
            Context context = contexts[0];

            ContentResolver r = context.getContentResolver();

            Log.d( TAG, "--[ Begin Conversion ]--" );

            // Start with DLC
            try ( Cursor cDlc = r.query( DatabaseContract.DLCEntry.CONTENT_URI,
                    null, null, null, null ) ) {
                while ( cDlc.moveToNext() ) {
                    Log.d( TAG, "--[ Begin of DLC ]--" );

                    long dlcId = cDlc.getLong( cDlc.getColumnIndex( DatabaseContract.DLCEntry._ID ) );
                    String dlcName = cDlc.getString( cDlc.getColumnIndex(
                            DatabaseContract.DLCEntry.COLUMN_NAME ) );

                    JSONDataObject jsonDataObject = new JSONDataObject( dlcId, dlcName );

                    Log.d( TAG, "------" );

                    // Add Station
                    try ( Cursor cStation = r.query( DatabaseContract.StationEntry.buildUriWithDLCId( dlcId ),
                            null, null, null,
                            DatabaseContract.StationEntry.COLUMN_NAME ) ) {
                        while ( cStation.moveToNext() ) {
                            Log.d( TAG, "--[ Begin of Station ]--" );

                            long stationId = cStation.getLong( cStation.getColumnIndex( DatabaseContract.StationEntry._ID ) );
                            String stationName = cStation.getString( cStation.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );
                            String stationImageFolder = cStation.getString( cStation.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER ) );
                            String stationImageFile = cStation.getString( cStation.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE ) );

                            JSONDataObject.Station station = new JSONDataObject.Station( stationName, stationImageFolder, stationImageFile );

                            Log.d( TAG, "Found " + stationName );

                            Log.d( TAG, "Adding Categories.." );

                            // Add Category within Station
                            station.setCategories( getCategories( r, dlcId, ROOT, stationId ) );

                            Log.d( TAG, "Adding Categories completed." );

                            // Add Station to jsonDataObject
                            jsonDataObject.addStation( station );

                            Log.d( TAG, "--[ End of Station ]--" );
                        }

                        Log.d( TAG, "--[ End of Stations ]--" );
                    }

                    String fileName = dlcName + ".json";
                    File path = context.getExternalFilesDir( null );
                    File file = new File( path, fileName );

                    Log.d( TAG, "Writing to file, " + fileName );

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue( file, jsonDataObject );

                    Log.d( TAG, "Writing to file completed." );

                    Log.d( TAG, "--[ End of DLC ]--" );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }

            return null;
        }

        List<JSONDataObject.Category> getCategories( ContentResolver r, long dlcId, long parentId, long stationId ) {
            List<JSONDataObject.Category> categories = new ArrayList<>();

            try ( Cursor cCategory = r.query( DatabaseContract.CategoryEntry.buildUriWithStationId( dlcId, parentId, stationId ),
                    null, null, null,
                    DatabaseContract.CategoryEntry.COLUMN_NAME ) ) {
                while ( cCategory.moveToNext() ) {
                    Log.d( TAG, "--[ Begin of Category ]--" );

                    long categoryId = cCategory.getLong( cCategory.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
                    String categoryName = cCategory.getString( cCategory.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );

                    JSONDataObject.Category category = new JSONDataObject.Category( categoryName );

                    Log.d( TAG, "Found " + categoryName );

                    Log.d( TAG, "Adding Engrams.." );

                    // Add Engrams within Category
                    try ( Cursor cEngram = r.query( DatabaseContract.EngramEntry.buildUriWithCategoryIdAndStationId( dlcId, categoryId, stationId ),
                            null, null, null,
                            DatabaseContract.EngramEntry.COLUMN_NAME ) ) {
                        while ( cEngram.moveToNext() ) {
                            long engramId = cEngram.getLong( cEngram.getColumnIndex( DatabaseContract.EngramEntry._ID ) );
                            String engramName = cEngram.getString( cEngram.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
                            String engramDescription = cEngram.getString( cEngram.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION ) );
                            String engramImageFolder = cEngram.getString( cEngram.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
                            String engramImageFile = cEngram.getString( cEngram.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );
                            int engramYield = cEngram.getInt( cEngram.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
                            int engramLevel = cEngram.getInt( cEngram.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_LEVEL ) );

                            category.addEngram( new JSONDataObject.Engram( engramName, engramDescription, engramImageFolder, engramImageFile, engramYield, engramLevel ) );

                            Log.d( TAG, "++ " + engramName );
                        }
                    }

                    Log.d( TAG, "Adding Engrams completed." );

                    Log.d( TAG, "Adding Sub-Categories.." );

                    // Add Sub-Categories within Category
                    category.setCategories( getCategories( r, dlcId, categoryId, stationId ) );

                    Log.d( TAG, "Adding Sub-Categories completed." );

                    // Add now full Category to List of Categories
                    categories.add( category );

                    Log.d( TAG, "--[ End of Category ]--" );
                }
            }

            return categories;
        }
    }
}
