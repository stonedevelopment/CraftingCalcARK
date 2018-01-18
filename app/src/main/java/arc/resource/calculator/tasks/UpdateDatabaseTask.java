package arc.resource.calculator.tasks;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.entity.BaseComposite;
import arc.resource.calculator.db.entity.BaseDLC;
import arc.resource.calculator.db.entity.BaseEngram;
import arc.resource.calculator.db.entity.BaseFolder;
import arc.resource.calculator.db.entity.BaseResource;
import arc.resource.calculator.db.entity.BaseStation;
import arc.resource.calculator.db.entity.DLCEngram;
import arc.resource.calculator.db.entity.DLCFolder;
import arc.resource.calculator.db.entity.DLCName;
import arc.resource.calculator.db.entity.DLCResource;
import arc.resource.calculator.db.entity.DLCStation;
import arc.resource.calculator.db.entity.Description;
import arc.resource.calculator.db.entity.EngramDescription;
import arc.resource.calculator.db.entity.EngramName;
import arc.resource.calculator.db.entity.FolderEngram;
import arc.resource.calculator.db.entity.FolderName;
import arc.resource.calculator.db.entity.Name;
import arc.resource.calculator.db.entity.ResourceName;
import arc.resource.calculator.db.entity.StationEngram;
import arc.resource.calculator.db.entity.StationFolder;
import arc.resource.calculator.db.entity.StationName;
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

                // TODO: 1/6/2018 Test if baseObjects full constructor can be private

                //  scrub JSONDataObject for data to insert
                JSONDataObject dlcData = mapper.readValue(
                        getFileFromAssets( context, fileName ), JSONDataObject.class );

//                //  test if dlc exists, insert if not
//                String dlcId = db.dlcDao().getId( dlcData.getImage_folder(), dlcData.getImage_file() );
//                if ( dlcId.isEmpty() ) {
//                    // TODO: 1/6/2018 make assets for dlc imageFile
//                    BaseDLC baseDLC = new BaseDLC( dlcData.getImage_folder(), dlcData.getImage_file() );
//                    dlcId = baseDLC.getId();
//
//                        db.dlcDao().insert( baseDLC );
//                }
//
//                //  test if name exists, insert if not
//                String dlcNameId = db.nameDao().getId( dlcData.getName() );
//                if ( dlcNameId.isEmpty() ) {
//                    Name dlcName = new Name( dlcData.getName() );
//                    dlcNameId = dlcName.getId();
//                    db.nameDao().insert( dlcName );
//                }
//
//                //  insert DLCName object into database
//                db.dlcNameDao().insert( new DLCName( dlcId, dlcNameId ) );
//
//                //  iterate through list of Resource objects
//                for ( JSONDataObject.Resource resource :
//                        dlcData.getResources() ) {
//
//                    //  test if resource exists, insert if not
//                    String resourceId = db.resourceDao().getId(
//                            resource.getImage_folder(), resource.getImage_file() );
//                    if ( resourceId.isEmpty() ) {
//                        BaseResource baseResource = new BaseResource(
//                                resource.getImage_folder(), resource.getImage_file() );
//                        resourceId = baseResource.getId();
//                        db.resourceDao().insert( baseResource );
//                    }
//
//                    //  insert DLCResource (join) object
//                    db.dlcResourceDao().insert( new DLCResource( dlcId, resourceId ) );
//
//                    //  test if name exists, insert if not
//                    String resourceNameId = db.nameDao().getId( resource.getName() );
//                    if ( resourceNameId.isEmpty() ) {
//                        Name resourceName = new Name( resource.getName() );
//                        resourceNameId = resourceName.getId();
//                        db.nameDao().insert( resourceName );
//                    }
//
//                    //  insert ResourceName (join) object
//                    db.resourceNameDao().insert( new ResourceName( resourceId, resourceNameId ) );
//                }
//
//                //  iterate through list of station objects
//                for ( JSONDataObject.Station station : dlcData.getStations() ) {
//
//                    //  test if station exists, insert if not
//                    String stationId = db.stationDao().getId( station.getImage_folder(), station.getImage_file() );
//                    if ( stationId.isEmpty() ) {
//                        BaseStation baseStation = new BaseStation(
//                                station.getImage_folder(), station.getImage_file() );
//                        stationId = baseStation.getId();
//                        db.stationDao().insert( baseStation );
//                    }
//
//                    //  insert DLCStation (join) object
//                    db.dlcStationDao().insert( new DLCStation( dlcId, stationId ) );
//
//                    //  test if name exists, insert if not
//                    String stationNameId = db.nameDao().getId( station.getName() );
//                    if ( stationNameId.isEmpty() ) {
//                        Name stationName = new Name( station.getName() );
//                        stationNameId = stationName.getId();
//                        db.nameDao().insert( stationName );
//                    }
//
//                    //  insert StationName (join) object
//                    db.stationNameDao().insert( new StationName( stationId, stationNameId ) );
//
//                    //  iterate through list of category objects
//                    insertCategories( db, station.getCategories(), stationId, stationId, dlcId );
//                }
            }

            return true;
        }

        // If error, send error signal, onPause service
        catch ( Exception e ) {
            mException = e;
            return false;
        }
    }

    private void insertCategories( AppDatabase db, List<JSONDataObject.Category> folders,
                                   String parentId, String stationId, String dlcId ) {
        for ( JSONDataObject.Category folder : folders ) {
//            BaseFolder baseFolder = new BaseFolder( parentId );
//            String folderId = baseFolder.getId();
//            db.folderDao().insert( baseFolder );
//
//            //  insert DLCFolder (join) object
//            db.dlcFolderDao().insert( new DLCFolder( dlcId, folderId ) );
//
//            //  insert StationFolder (join) object
//            db.stationFolderDao().insert( new StationFolder( stationId, folderId ) );
//
//            //  test if name exists, insert if not
//            String folderNameId = db.nameDao().getId( folder.getName() );
//            if ( folderNameId.isEmpty() ) {
//                Name folderName = new Name( folder.getName() );
//                folderNameId = folderName.getId();
//                db.nameDao().insert( folderName );
//            }
//
//            //  insert FolderName (join) object
//            db.folderNameDao().insert( new FolderName( folderId, folderNameId ) );
//
//            //  iterate through list of engram objects
//            for ( JSONDataObject.Engram engram : folder.getEngrams() ) {
//
//                //  test if engram exists, insert if not
//                String engramId = db.engramDao().getId( engram.getImage_folder(),
//                        engram.getImage_file(), engram.getLevel() );
//                if ( engramId.isEmpty() ) {
//                    BaseEngram baseEngram = new BaseEngram( engram.getImage_folder(),
//                            engram.getImage_file(), engram.getLevel() );
//                    engramId = baseEngram.getId();
//                    db.engramDao().insert( baseEngram );
//                }
//
//                //  insert DLCEngram (join) object
//                db.dlcEngramDao().insert( new DLCEngram( dlcId, engramId ) );
//
//                //  insert StationEngram (join) object
//                db.stationEngramDao().insert( new StationEngram( stationId, engramId, engram.getYield() ) );
//
//                //  insert FolderEngram (join) object
//                db.folderEngramDao().insert( new FolderEngram( folderId, engramId ) );
//
//                //  test if name exists, insert if not
//                String engramNameId = db.nameDao().getId( engram.getName() );
//                if ( engramNameId.isEmpty() ) {
//                    Name engramName = new Name( engram.getName() );
//                    engramNameId = engramName.getId();
//                    db.nameDao().insert( engramName );
//                }
//
//                //  test if description exists, insert if not
//                String engramDescriptionId = db.descriptionDao().getId( engram.getDescription() );
//                if ( engramDescriptionId.isEmpty() ) {
//                    Description engramDescription = new Description( engram.getDescription() );
//                    engramDescriptionId = engramDescription.getId();
//                    db.descriptionDao().insert( engramDescription );
//                }
//
//                //  insert EngramName (join) object
//                db.engramNameDao().insert( new EngramName( engramId, engramNameId ) );
//
//                //  insert EngramDescription (join) object
//                db.engramDescriptionDao().insert( new EngramDescription( engramId, engramDescriptionId ) );
//
//                //  iterate through list of compositionElement objects
//                for ( JSONDataObject.Composite composite : engram.getComposition() ) {
//
//                    //  get name id by name
//                    String resourceNameId = db.nameDao().getId( composite.getName() );
//
//                    //  get resource id by name id
//                    String resourceId = db.resourceNameDao().getId( resourceNameId );
//
//                    //  test if composition exists, insert if not
//                    String compositeId = db.compositeDao().getId( resourceId, composite.getQuantity() );
//                    if ( compositeId.isEmpty() ) {
//                        BaseComposite baseComposite = new BaseComposite( resourceId, composite.getQuantity() );
//                        compositeId = baseComposite.getId();
//                        db.compositeDao().insert( baseComposite );
//                    }
//
//                    //  insert EngramComposition (join) object
////                    db.engramCompositeDao().insert( engramId, compositeId );
//                }
//            }
//
//            //  recursively sift through subcategories ftw
//            insertCategories( db, folder.getCategories(), folderId, stationId, dlcId );
        }
    }

    private InputStream getFileFromAssets( Context context, String fileName ) {
        try {
            return context.getAssets().open( "AppData/" + fileName );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteAllRecordsFromAllTables( AppDatabase db ) {
        // TODO: 1/7/2018 update this with all tables!
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