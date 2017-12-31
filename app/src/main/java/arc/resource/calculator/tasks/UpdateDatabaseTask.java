package arc.resource.calculator.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.entity.Category;
import arc.resource.calculator.db.entity.Composition;
import arc.resource.calculator.db.entity.DLC;
import arc.resource.calculator.db.entity.Engram;
import arc.resource.calculator.db.entity.Resource;
import arc.resource.calculator.db.entity.Station;
import arc.resource.calculator.model.json.JSONDataObject;
import arc.resource.calculator.model.json.JSONVersion;
import arc.resource.calculator.util.PrefsUtil;

public class UpdateDatabaseTask extends AsyncTask<Context, Void, Boolean> {
    private final String TAG = UpdateDatabaseTask.class.getSimpleName();

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

        void onFinishWithUpdate();
    }

    public UpdateDatabaseTask( Listener listener ) {
        setListener( listener );
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
                getListener().onFinishWithUpdate();
            else
                getListener().onFinish();
        else
            getListener().onError( mException );
    }

    @Override
    protected Boolean doInBackground( Context... params ) {
        try {
            Context context = params[0];

            ObjectMapper mapper = new ObjectMapper();

            // read json version file for current version
            // TODO: 12/30/2017 "version.json" to string resource
            JSONVersion jsonVersion = mapper.readValue(
                    getFileFromFilename( context, "version.json" ), JSONVersion.class );

            // grab persistent version saved on phone
            String currentVersion = PrefsUtil.getInstance( context ).getJSONVersion();

            // now, let's check if we even need to update.
            // if not, return out of task
            if ( !isNewVersion( currentVersion, jsonVersion.getCurrent() ) ) {
                return false;
            }

            // emit new version event
            getListener().onNewVersion( currentVersion, jsonVersion.getCurrent() );

            // new version! let's get cracking!
            // let user know that we've begun the process.
            getListener().onStart();

            //  get an instance of AppDatabase object
            AppDatabase db = AppDatabase.getInstance( context );

            //  delete all records from all tables
            deleteAllRecordsFromAllTables( db );

            //  iterate through list of files to grab data from
            for ( String fileName : jsonVersion.getFiles() ) {

                //  create lists of objects for batch inserting
                List<Resource> resources = new ArrayList<>();
                List<Composition> compositions = new ArrayList<>();

                //  scrub JSONDataObject for data to insert
                JSONDataObject dlcData = mapper.readValue(
                        getFileFromFilename( context, fileName ), JSONDataObject.class );

                //  insert DLC object into database
                long dlcId = db.dlcDao().insert( new DLC( dlcData.getId(), dlcData.getName() ) );

                //  iterate through list of Resource objects
                for ( JSONDataObject.Resource resource :
                        dlcData.getResources() ) {

                    //  add converted Resource object to bulk insert list
                    resources.add(
                            new Resource( resource.getName(),
                                    resource.getImage_folder(),
                                    resource.getImage_file(),
                                    dlcId ) );
                }

                //  bulk insert list of Resource objects
                db.resourceDao().insert( resources );

                //  iterate through list of station objects
                for ( JSONDataObject.Station station : dlcData.getStations() ) {

                    //  insert converted Station object into database
                    long stationId = db.stationDao().insert(
                            new Station( station.getName(),
                                    station.getImage_folder(),
                                    station.getImage_file(),
                                    dlcId ) );

                    //  iterate through list of category objects
                    for ( JSONDataObject.Category category : station.getCategories() ) {

                        //  insert converted Category object into database
                        long categoryId = db.categoryDao().insert(
                                new Category( category.getName(),
                                        0,
                                        stationId,
                                        dlcId ) );

                        //  iterate through list of engram objects
                        for ( JSONDataObject.Engram engram : category.getEngrams() ) {

                            //  insert converted Engram object into database
                            long engramId = db.engramDao().insert(
                                    new Engram( engram.getName(),
                                            engram.getDescription(),
                                            engram.getImage_folder(),
                                            engram.getImage_file(),
                                            engram.getYield(),
                                            engram.getLevel(),
                                            stationId,
                                            categoryId,
                                            dlcId ) );

                            //  iterate through list of compositionElement objects
                            for ( JSONDataObject.CompositionElement composition :
                                    engram.getComposition() ) {

                                //  cull Resource object from database
                                Resource resource = db.resourceDao().get( composition.getName(), dlcId );

                                //  finally, add converted Composition object to bulk insert list
                                compositions.add(
                                        new Composition( engramId,
                                                resource.getId(),
                                                composition.getQuantity() ) );
                            }
                        }
                    }
                }

                //  finally, bulk insert all lists of converted objects
                db.compositionDao().insert( compositions );
            }

            return true;
        }

        // If error, send error signal, onPause service
        catch ( Exception e ) {
            mException = e;
            return false;
        }
    }

    private void deleteAllRecordsFromAllTables( AppDatabase db ) {
        db.categoryDao().deleteAll();
        db.complexResourceDao().deleteAll();
        db.compositionDao().deleteAll();
        db.dlcDao().deleteAll();
        db.engramDao().deleteAll();
        db.queueDao().deleteAll();
        db.resourceDao().deleteAll();
        db.stationDao().deleteAll();
    }

    private File getFileFromFilename( Context context, String fileName ) {
        return new File( context.getExternalFilesDir( null ), fileName );
    }

    private boolean isNewVersion( String oldVersion, String newVersion ) {
        return !Objects.equals( oldVersion, newVersion );
    }
}