package arc.resource.calculator.tasks;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.json.JSONDataObject;

/**
 * Converts data_editable.json's old configuration into the new tree style
 * Still editable
 *
 * @see UpdateDatabaseTask todo update to new tree style
 */
public class ConvertTask extends AsyncTask<Context, Void, Void> {
    private static final String TAG = ConvertTask.class.getSimpleName();
    private static final long STATION_ROOT = -1;
    private static final long ROOT = 0;

    @Override
    protected Void doInBackground( Context... contexts ) {
        Context context = contexts[0];

        ContentResolver r = context.getContentResolver();

        Log.d( TAG, "--[ Begin Conversion ]--" );

        // Start with DLC
//        try ( Cursor cDlcs = r.query( DatabaseContract.buildUriWithId( DatabaseContract.DLCEntry.CONTENT_URI, 1 ),
//                null, null, null, null ) ) {
        try ( Cursor cDlcs = r.query( DatabaseContract.DLCEntry.CONTENT_URI,
                null, null, null, null ) ) {
            Log.d( TAG, "Adding DLCs.." );

            if ( cDlcs != null ) {
                while ( cDlcs.moveToNext() ) {
                    Log.d( TAG, "--[ Begin of DLC ]--" );

                    long dlcId = cDlcs.getLong( cDlcs.getColumnIndex( DatabaseContract.DLCEntry._ID ) );
                    String dlcName = cDlcs.getString( cDlcs.getColumnIndex(
                            DatabaseContract.DLCEntry.COLUMN_NAME ) );

                    JSONDataObject jsonDataObject = new JSONDataObject( dlcName );

                    Log.d( TAG, "Found " + dlcName );

                    // Add Station
                    try ( Cursor cStations = r.query( DatabaseContract.StationEntry.buildUriWithDLCId( dlcId ),
                            null, null, null,
                            DatabaseContract.StationEntry.COLUMN_NAME ) ) {
                        Log.d( TAG, "Adding Stations.." );

                        if ( cStations != null ) {
                            while ( cStations.moveToNext() ) {
                                Log.d( TAG, "--[ Begin of Station ]--" );

                                long stationId = cStations.getLong( cStations.getColumnIndex( DatabaseContract.StationEntry._ID ) );
                                String stationName = cStations.getString( cStations.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );
                                String stationImageFolder = cStations.getString( cStations.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER ) );
                                String stationImageFile = cStations.getString( cStations.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE ) );

                                JSONDataObject.Station station = new JSONDataObject.Station( stationName, stationImageFolder, stationImageFile );

                                Log.d( TAG, "Found " + stationName );

                                // Add Category within Station
                                station.setCategories( getCategories( r, dlcId, ROOT, stationId ) );

                                // Add Station to jsonDataObject
                                jsonDataObject.addStation( station );

                                Log.d( TAG, "--[ End of Station ]--" );
                            }
                        } else {
                            Log.d( TAG, "No Stations found." );
                        }

                        Log.d( TAG, "Adding Stations completed." );
                    }

                    // Add Resources
                    try ( Cursor cResources = r.query( DatabaseContract.ResourceEntry.buildUriWithDlcId( dlcId ),
                            null, null, null,
                            DatabaseContract.StationEntry.COLUMN_NAME ) ) {
                        Log.d( TAG, "Adding Resources.." );

                        if ( cResources != null ) {
                            while ( cResources.moveToNext() ) {
                                long resourceId = cResources.getLong( cResources.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );
                                String resourceName = cResources.getString( cResources.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                                String resourceImageFolder = cResources.getString( cResources.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER ) );
                                String resourceImageFile = cResources.getString( cResources.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE ) );

                                jsonDataObject.addResource( new JSONDataObject.Resource( resourceName, resourceImageFolder, resourceImageFile ) );

                                Log.d( TAG, "++ " + resourceName );
                            }
                        } else {
                            Log.d( TAG, "No Resources found." );
                        }

                        Log.d( TAG, "Adding Resources completed." );
                    }

                    String fileName = dlcName.toLowerCase() + ".json";
                    File path = context.getExternalFilesDir( null );
                    File file = new File( path, fileName );

                    Log.d( TAG, "Writing to file, " + fileName );

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue( file, jsonDataObject );

                    Log.d( TAG, "Writing to file completed." );

                    Log.d( TAG, "--[ End of DLC ]--" );
                }
            } else {
                Log.d( TAG, "No DLC found." );
            }

            Log.d( TAG, "Adding DLCs completed." );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return null;
    }

    private List<JSONDataObject.Category> getCategories( ContentResolver r, long dlcId, long parentId, long stationId ) {
        List<JSONDataObject.Category> categories = new ArrayList<>();

        try ( Cursor cCategories = r.query( DatabaseContract.CategoryEntry.buildUriWithStationId( dlcId, parentId, stationId ),
                null, null, null,
                DatabaseContract.CategoryEntry.COLUMN_NAME ) ) {
            Log.d( TAG, "Adding Categories.." );

            if ( cCategories != null ) {
                while ( cCategories.moveToNext() ) {
                    Log.d( TAG, "--[ Begin of Category ]--" );

                    long categoryId = cCategories.getLong( cCategories.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
                    String categoryName = cCategories.getString( cCategories.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );

                    JSONDataObject.Category category = new JSONDataObject.Category( categoryName );

                    Log.d( TAG, "Found " + categoryName );

                    // Add Engrams within Category
                    try ( Cursor cEngrams = r.query( DatabaseContract.EngramEntry.buildUriWithCategoryIdAndStationId( dlcId, categoryId, stationId ),
                            null, null, null,
                            DatabaseContract.EngramEntry.COLUMN_NAME ) ) {
                        Log.d( TAG, "Adding Engrams.." );

                        if ( cEngrams != null ) {
                            while ( cEngrams.moveToNext() ) {
                                long engramId = cEngrams.getLong( cEngrams.getColumnIndex( DatabaseContract.EngramEntry._ID ) );
                                String engramName = cEngrams.getString( cEngrams.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );
                                String engramDescription = cEngrams.getString( cEngrams.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION ) );
                                String engramImageFolder = cEngrams.getString( cEngrams.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
                                String engramImageFile = cEngrams.getString( cEngrams.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );
                                int engramYield = cEngrams.getInt( cEngrams.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
                                int engramLevel = cEngrams.getInt( cEngrams.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_LEVEL ) );

                                JSONDataObject.Engram engram = new JSONDataObject.Engram( engramName, engramDescription, engramImageFolder, engramImageFile, engramYield, engramLevel );

                                // Add Composition for Engram
                                try ( Cursor cComposition = r.query( DatabaseContract.CompositionEntry.buildUriWithEngramId( dlcId, engramId ),
                                        null, null, null, null ) ) {
//                                    Log.d( TAG, "Adding Composition.." );

                                    if ( cComposition != null ) {
                                        List<Long> resourceIds = new ArrayList<>();
                                        while ( cComposition.moveToNext() ) {
                                            long resourceId = cComposition.getLong( cComposition.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                                            int resourceQuantity = cComposition.getInt( cComposition.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                                            if ( !resourceIds.contains( resourceId ) ) {
                                                // Grab Resource Name
                                                String resourceName = getResourceName( r, dlcId, resourceId );

                                                if ( resourceName != null ) {
//                                                    Log.d( TAG, "Found " + resourceName );
                                                    engram.addCompositionElement( new JSONDataObject.Composite( resourceName, resourceQuantity ) );
                                                } else {
//                                                    Log.d( TAG, "No Resource found." );
                                                }

                                                resourceIds.add( resourceId );
                                            } else {
//                                                Log.d( TAG, "Skipping duplicate." );
                                            }
                                        }
                                    } else {
                                        Log.d( TAG, "No Composition found." );
                                    }

//                                    Log.d( TAG, "Adding Composition completed." );
                                }

                                category.addEngram( engram );

                                Log.d( TAG, "++ " + engramName );
                            }
                        } else {
                            Log.d( TAG, "No Engrams found." );
                        }

                        Log.d( TAG, "Adding Engrams completed." );
                    }

                    // Add Sub-Categories within Category
                    category.setCategories( getCategories( r, dlcId, categoryId, stationId ) );

                    // Add now full Category to List of Categories
                    categories.add( category );

                    Log.d( TAG, "--[ End of Category ]--" );
                }
            } else {
                Log.d( TAG, "No Categories found." );
            }

            Log.d( TAG, "Adding Categories completed." );
        }

        return categories;
    }

    private String getResourceName( ContentResolver r, long dlcId, long resourceId ) {
        try ( Cursor cResource = r.query( DatabaseContract.ResourceEntry.buildUriWithId( dlcId, resourceId ),
                null, null, null, null ) ) {
            if ( cResource != null ) {
                if ( cResource.moveToFirst() ) {
                    long _id = cResource.getLong( cResource.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );
                    String name = cResource.getString( cResource.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
                    String folder = cResource.getString( cResource.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER ) );
                    String file = cResource.getString( cResource.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE ) );

                    return name;
                }
            }
        }

        return null;
    }
}