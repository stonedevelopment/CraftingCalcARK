import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.entity.Category;
import arc.resource.calculator.db.entity.Composition;
import arc.resource.calculator.db.entity.DLC;
import arc.resource.calculator.db.entity.Engram;
import arc.resource.calculator.db.entity.Resource;
import arc.resource.calculator.db.entity.Station;
import arc.resource.calculator.model.json.JSONDataObject;
import arc.resource.calculator.model.json.JSONVersion;

@RunWith( AndroidJUnit4.class )
public class DatabaseReadWriteTest {
    private AppDatabase db;
    private Context context;

    @Before
    public void createDb() {
        this.context = InstrumentationRegistry.getTargetContext();
        this.db = Room.inMemoryDatabaseBuilder( context, AppDatabase.class ).build();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertDataFromJsonFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // read json version file for current version
        // TODO: 12/30/2017 "version.json" to string resource
        JSONVersion jsonVersion = mapper.readValue(
                getFileFromFilename( context, "version.json" ), JSONVersion.class );

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
    }

    private File getFileFromFilename( Context context, String fileName ) {
        return new File( context.getExternalFilesDir( null ), fileName );
    }
}