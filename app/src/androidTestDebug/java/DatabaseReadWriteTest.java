import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
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
import arc.resource.calculator.db.entity.EngramComposite;
import arc.resource.calculator.db.entity.FolderEngram;
import arc.resource.calculator.db.entity.FolderName;
import arc.resource.calculator.db.entity.ImageLocation;
import arc.resource.calculator.db.entity.Name;
import arc.resource.calculator.db.entity.ResourceName;
import arc.resource.calculator.db.entity.StationEngram;
import arc.resource.calculator.db.entity.StationName;
import arc.resource.calculator.model.json.JSONDataObject;
import arc.resource.calculator.model.json.JSONDataObject.Category;
import arc.resource.calculator.model.json.JSONVersion;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseReadWriteTest {

  private static final long INVALID_ID = -1;

  private AppDatabase db;
  private Context context;

  @Before
  public void createDb() {
    this.context = InstrumentationRegistry.getTargetContext();
    this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
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
        getFileFromAssets(context, "test_version.json"), JSONVersion.class);

    //  iterate through list of files to grab data from
    for (String fileName : jsonVersion.getFiles()) {

      // TODO: 1/16/2018 Think about how to structure to accommodate bare-bones DLC json files
      //    Example: Scorched Earth Stone Wall, just use the Name and only override if it's included
      //    Or, have an option to include primary game version
      // TODO: 1/16/2018 Separate primary game data from DLC. Think about how to implement Mods in the future.

      //  scrub JSONDataObject for data to insert
      JSONDataObject data = mapper.readValue(
          getFileFromAssets(context, fileName), JSONDataObject.class);

      //  insert dlc, get id if added
      // TODO: 1/6/2018 make assets for dlc imageFile
      BaseDLC baseDLC = new BaseDLC(data.getImage_folder(), data.getImage_file());
      String dlcId =
          db.dlcDao().insert(baseDLC) != INVALID_ID ? baseDLC.getId() :
              db.dlcDao().getId(data.getImage_folder(), data.getImage_file());
      assertNotNull(dlcId);

      //  insert name, get id if added
      Name dlcName = new Name(dlcId, data.getName());
      String dlcNameId = db.nameDao().insert(dlcName) != INVALID_ID ? dlcName.getId() :
          db.nameDao().getId(dlcName.getText());
      assertNotNull(dlcNameId);

      //  insert DLCName object into database
      db.dlcNameDao().insert(new DLCName(dlcId, dlcNameId));

      //  iterate through list of Resource objects
      for (JSONDataObject.Resource resource :
          data.getResources()) {

        //  insert resource, get id if added
        BaseResource baseResource = new BaseResource(resource.getImage_folder(),
            resource.getImage_file());
        String resourceId =
            db.resourceDao().insert(baseResource) != INVALID_ID ? baseResource.getId() :
                db.resourceDao().getId(resource.getImage_folder(),
                    resource.getImage_file());
        assertNotNull(resourceId);

        //  insert DLCResource (join) object
        DLCResource dlcResource = new DLCResource(dlcId, resourceId);
        String dlcResourceId =
            db.dlcResourceDao().insert(dlcResource) != INVALID_ID ? dlcResource.getId()
                : db.dlcResourceDao().getId(dlcId, resourceId);
        assertNotNull(dlcResourceId);

        //  insert name, get id if added
        Name resourceName = new Name(resourceId, resource.getName());
        String resourceNameId =
            db.nameDao().insert(resourceName) != INVALID_ID ? resourceName.getId() :
                db.nameDao().getId(resource.getName());
        assertNotNull(resourceNameId);

        assertEquals(resourceName.getText(), db.nameDao().get(resourceNameId).getText());

        //  insert ResourceName (join) object
        db.resourceNameDao().insert(new ResourceName(dlcResourceId, resourceNameId));
      }

      //  iterate through list of station objects
      for (JSONDataObject.Station station : data.getStations()) {

        //  insert image location
        ImageLocation stationImageLocation = new ImageLocation(station.getImage_folder(),
            station.getImage_file());
        String stationImageLocationId =
            db.imageLocationDao().insert(stationImageLocation) != INVALID_ID
                ? stationImageLocation.getId()
                : db.imageLocationDao().getId(station.getImage_folder(),
                    station.getImage_file());
        assertNotNull(stationImageLocationId);

        //  insert name
        Name stationName = new Name(station.getName());
        String stationNameId = db.nameDao().insert(stationName) != INVALID_ID ? stationName.getId()
            : db.nameDao().getId(station.getName());
        assertNotNull(stationNameId);

        //  insert station, get id if conflict
        BaseStation baseStation = new BaseStation(stationImageLocationId, stationNameId);
        String stationId = db.stationDao().insert(baseStation) != INVALID_ID ? baseStation.getId() :
            db.stationDao().getId(stationImageLocationId, stationNameId);
        assertNotNull(stationId);

        //  insert DLCStation (join) object
//        DLCStation dlcStation = new DLCStation(dlcId, stationId);
//        String dlcStationId =
//            db.dlcStationDao().insert(dlcStation) != INVALID_ID ? dlcStation.getId() :
//                db.dlcStationDao().getId(dlcId, stationId);
//        assertNotNull(dlcStationId);

        //  insert name, get id if conflict
//        Name stationName = new Name(stationId, station.getName());
//        String stationNameId =
//            db.nameDao().insert(stationName) != INVALID_ID ? stationName.getId() :
//                db.nameDao().getId(station.getName());
//        assertNotNull(stationNameId);

        //  insert StationName (join) object
//        db.stationNameDao().insert(new StationName(dlcStationId, stationNameId));

        //  iterate through list of engram objects
        insertEngrams(db, station.getEngrams(), dlcStation);

        //  iterate through list of category objects
        insertFolders(db, station.getCategories(), dlcStationId, dlcStation);
      }
    }

    for (int i = 0; i < 10; i++) {
      println("--[ Printing Random Data " + i + "/" + 5 + " ]--");
      printRandomData();
    }
  }

  private void insertFolders(AppDatabase db, List<Category> folders, String parentId,
      DLCStation dlcStation) {

    String dlcId = dlcStation.getDlcId();
    String dlcStationId = dlcStation.getId();

    for (JSONDataObject.Category folder : folders) {
      //  insert folder, get id if conflict
      BaseFolder baseFolder = new BaseFolder(parentId);
      String folderId = db.folderDao().insert(baseFolder) != INVALID_ID ? baseFolder.getId() :
          db.folderDao().getId(parentId);
      assertNotNull(folderId);

      //  insert DLCFolder (join) object
      DLCFolder dlcFolder = new DLCFolder(dlcId, folderId);
      String dlcFolderId = db.dlcFolderDao().insert(dlcFolder) != INVALID_ID ? dlcFolder.getId()
          : db.dlcFolderDao().getId(dlcId, folderId);
      assertNotNull(dlcFolderId);

      //  insert StationFolder (join) object
//      StationFolder stationFolder = new StationFolder(dlcStationId, dlcFolderId);
//      String stationFolderId =
//          db.stationFolderDao().insert(stationFolder) != INVALID_ID ? stationFolder.getId() :
//              db.stationFolderDao().getId(dlcStationId, dlcFolderId);
//      assertNotNull(stationFolderId);

      //  test if name exists, insert if not
      Name folderName = new Name(folder.getName());
      String folderNameId = db.nameDao().insert(folderName) != INVALID_ID ? folderName.getId()
          : db.nameDao().getId(folderName.getText());
      assertNotNull(folderNameId);

      assertEquals(folderName.getText(), db.nameDao().get(folderNameId).getText());

      //  insert FolderName (join) object
      db.folderNameDao().insert(new FolderName(dlcFolderId, folderNameId));

      //  iterate through list of engram objects
      for (JSONDataObject.Engram engram : folder.getEngrams()) {

        // TODO: 1/16/2018 Engram should have a parentId, same as category. If not a category, then it must be a station.
        // TODO: 1/16/2018 No more folders that are not in-game folders. (Ex: Refined)

        //  insert image location, retrieve id on conflict or get id from object
        ImageLocation engramImageLocation = new ImageLocation(engram.getImage_folder(),
            engram.getImage_file());
        String engramImageLocationId =
            db.imageLocationDao().insert(engramImageLocation) != INVALID_ID
                ? engramImageLocation.getId()
                : db.imageLocationDao().getId(engram.getImage_folder(), engram.getImage_file());
        assertNotNull(engramImageLocationId);

        //  insert name, retrieve id if conflict or get id from object
        Name engramName = new Name(engram.getName());
        String engramNameId = db.nameDao().insert(engramName) != INVALID_ID
            ? engramName.getId() : db.nameDao().getId(engram.getName());
        assertNotNull(engramNameId);

        //  insert description, retrieve id on conflict or get id from object
        Description engramDescription = new Description(engram.getDescription());
        String engramDescriptionId = db.descriptionDao().insert(engramDescription) != INVALID_ID
            ? engramDescription.getId() : db.nameDao().getId(engram.getDescription());
        assertNotNull(engramDescriptionId);

        //  test if engram exists, insert if not
        BaseEngram baseEngram = new BaseEngram(engramDescriptionId, engramImageLocationId,
            engramNameId, engram.getLevel());
        String engramId = db.engramDao().insert(baseEngram) != INVALID_ID
            ? baseEngram.getId()
            : db.engramDao().getId(engram.getImage_folder(), engram.getImage_file(),
                engram.getLevel());
        assertNotNull(engramId);

        //  insert DLCEngram (join) object
//        DLCEngram dlcEngram = new DLCEngram(dlcId, engramId);
//        String dlcEngramId = db.dlcEngramDao().insert(dlcEngram) != INVALID_ID ? dlcEngram.getId() :
//            db.dlcEngramDao().getId(dlcId, engramId);
//        assertNotNull(dlcEngramId);

        //  insert StationEngram (join) object
        StationEngram stationEngram = new StationEngram(dlcStationId, engramId,
            engram.getYield());
        String stationEngramId =
            db.stationEngramDao().insert(stationEngram) != INVALID_ID ? stationEngram.getId()
                : db.stationEngramDao().getId(dlcStationId, dlcEngramId);
        assertNotNull(stationEngramId);

        assertEquals(stationEngram.getYield(),
            db.stationEngramDao().get(dlcStationId, dlcEngramId).getYield());

        //  insert FolderEngram (join) object
        db.folderEngramDao().insert(new FolderEngram(dlcFolderId, dlcEngramId));

        //  test if name exists, insert if not
//        Name engramName = new Name(engram.getName());
//        String engramNameId = db.nameDao().insert(engramName) != INVALID_ID ? engramName.getId()
//            : db.nameDao().getId(engram.getName());
//        assertNotNull(engramNameId);
//
//        assertEquals(engramName.getText(), db.nameDao().get(engramNameId).getText());

        //  test if description exists, insert if not
//        Description engramDescription = new Description(engram.getDescription());
//        String engramDescriptionId =
//            db.descriptionDao().insert(engramDescription) != INVALID_ID ? engramDescription.getId()
//                : db.descriptionDao().getId(engram.getDescription());
//        assertNotNull(engramDescriptionId);
//
//        assertEquals(engramDescription.getText(),
//            db.descriptionDao().get(engramDescriptionId).getText());

        //  insert EngramName (join) object
//        db.engramNameDao().insert(new EngramName(dlcEngramId, engramNameId));

        //  insert EngramDescription (join) object
//        db.engramDescriptionDao().insert(new EngramDescription(dlcEngramId, engramDescriptionId));

        //  iterate through list of compositionElement objects
        for (JSONDataObject.Composite composite : engram.getComposition()) {
          String compositeName = composite.getName();

          //  get name id by name
          String resourceNameId = db.nameDao().getId(compositeName);
          assertNotNull(resourceNameId);

          Name resourceName = db.nameDao().get(resourceNameId);
          assertEquals(compositeName, resourceName.getText());

          //  get resource id by name id
          String dlcResourceId = db.resourceNameDao().getResourceId(resourceNameId);
          assertNotNull(dlcResourceId);

          //  test if composition exists, insert if not
          BaseComposite baseComposite = new BaseComposite(dlcResourceId, composite.getQuantity());
          String compositeId =
              db.compositeDao().insert(baseComposite) != INVALID_ID ? baseComposite.getId()
                  : db.compositeDao().getId(dlcResourceId, composite.getQuantity());
          assertNotNull(compositeId);

          //  insert EngramComposition (join) object
          EngramComposite engramComposite = new EngramComposite(stationEngramId, compositeId);
          String engramCompositeId =
              db.engramCompositeDao().insert(engramComposite) != INVALID_ID ? engramComposite
                  .getId() : db.engramCompositeDao().getId(stationEngramId, compositeId);
          assertNotNull(engramCompositeId);
        }
      }

      //  recursively sift through subcategories ftw
      insertFolders(db, folder.getCategories(), dlcFolderId, dlcStation);
    }
  }

  private void randomEngram() {
    List<DLCEngram> dlcEngrams = db.dlcEngramDao().getAll();
    DLCEngram dlcEngram = dlcEngrams.get(new Random().nextInt(dlcEngrams.size()));

    String dlcEngramId = dlcEngram.getId();

    String engramNameId = db.engramNameDao().getNameId(dlcEngramId);
    Name engramName = db.nameDao().get(engramNameId);

    println("Engram: " + engramName.getText());

    List<String> dlcFolderIds = db.folderEngramDao().getFolderIds(dlcEngramId);

    println("> Found " + dlcFolderIds.size() + " locations.");

    for (String dlcFolderId : dlcFolderIds) {
      String dlcStationId = db.stationFolderDao().getStationId(dlcFolderId);

      Name stationName = db.nameDao().get(db.stationNameDao().getNameId(dlcStationId));
      println("Station: " + stationName.getText());

      Name folderName = db.nameDao().get(db.folderNameDao().getNameId(dlcFolderId));
      println("  Folder: " + folderName.getText());

      while (db.folderDao().get(dlcFolderId).getParentId() != null) {

        String stationEngramId = db.stationEngramDao().getId(dlcStationId, dlcEngramId);
        List<String> compositeIds = db.engramCompositeDao().getCompositeIds(stationEngramId);
        for (BaseComposite composite :
            db.compositeDao().getAll(compositeIds)) {
          String resourceNameId = db.resourceNameDao().getNameId(composite.getResourceId());
          Name resourceName = db.nameDao().get(resourceNameId);

          println("        " + resourceName.getText() + " x" + composite.getQuantity());
        }
      }
    }
  }

  private void printRandomData() {
    List<BaseDLC> dlcIds = db.dlcDao().getAll();
    BaseDLC dlc = dlcIds.get(new Random().nextInt(dlcIds.size()));
    String dlcId = dlc.getId();

    String dlcNameId = db.dlcNameDao().getNameId(dlcId);
    Name dlcName = db.nameDao().get(dlcNameId);

    println("DLC: " + dlcName.getText());

    List<DLCStation> dlcStations = db.dlcStationDao().getAll(dlcId);
    DLCStation dlcStation = dlcStations.get(new Random().nextInt(dlcStations.size()));
    String dlcStationId = dlcStation.getId();

    String stationNameId = db.stationNameDao().getNameId(dlcStationId);
    Name stationName = db.nameDao().get(stationNameId);

    println("Station: " + stationName.getText());

    printRandomFolderData(dlcStationId, dlcId, dlcStationId);
  }

  private void printRandomFolderData(String parentId, String dlcId, String dlcStationId) {
    List<BaseFolder> folders = db.folderDao().getAll(parentId);

    if (folders.size() == 0) {
      return;
    }

    BaseFolder folder = folders.get(new Random().nextInt(folders.size()));

    String dlcFolderId = db.dlcFolderDao().getId(dlcId, folder.getId());

    String folderNameId = db.folderNameDao().getNameId(dlcFolderId);
    Name folderName = db.nameDao().get(folderNameId);

    println("  Folder: " + folderName.getText());

//    StationFolder stationFolder = db.stationFolderDao().get(dlcStationId, folder.getId());
//    String stationFolderId = stationFolder.getId();

    List<FolderEngram> folderEngrams = db.folderEngramDao().getAllByFolderId(dlcFolderId);
    for (FolderEngram folderEngram :
        folderEngrams) {
//      FolderEngram folderEngram = folderEngrams.get(new Random().nextInt(folderEngrams.size()));
      String dlcEngramId = folderEngram.getEngramId();

      String engramId = db.dlcEngramDao().getEngramId(dlcEngramId);

      String engramNameId = db.engramNameDao().getNameId(dlcEngramId);
      Name engramName = db.nameDao().get(engramNameId);

      String engramDescriptionId = db.engramDescriptionDao().getDescriptionId(dlcEngramId);
      Description engramDescription = db.descriptionDao().get(engramDescriptionId);

      println("      Engram: " + engramName.getText() + " / " + engramDescription.getText());

      String stationEngramId = db.stationEngramDao().getId(dlcStationId, engramId);
      List<String> compositeIds = db.engramCompositeDao().getCompositeIds(stationEngramId);
      for (BaseComposite composite :
          db.compositeDao().getAll(compositeIds)) {
        String resourceNameId = db.resourceNameDao().getNameId(composite.getResourceId());
        Name resourceName = db.nameDao().get(resourceNameId);

        println("        " + resourceName.getText() + " x" + composite.getQuantity());
      }
    }

    printRandomFolderData(dlcFolderId, dlcId, dlcStationId);
  }

  private void printTableData() {

    for (BaseDLC dlc
        : db.dlcDao().getAll()) {
      String dlcId = dlc.getId();

      String dlcNameId = db.dlcNameDao().getNameId(dlcId);
      Name dlcName = db.nameDao().get(dlcNameId);
      println("DLC: " + dlcName.getText());

      List<String> stationIds = db.dlcStationDao().getStationIds(dlcId);
      for (BaseStation station
          : db.stationDao().getAll(stationIds)) {
        String stationId = station.getId();

        DLCStation dlcStation = db.dlcStationDao().get(dlcId, stationId);
        String dlcStationId = dlcStation.getId();

        String stationNameId = db.stationNameDao().getNameId(dlcStationId);
        Name stationName = db.nameDao().get(stationNameId);
        println("  Station: " + stationName.getText());

        printFolderData(dlcStationId, dlcStation);
      }
    }
  }

  private void printFolderData(String parentId, DLCStation dlcStation) {
    String dlcId = dlcStation.getDlcId();
    String dlcStationId = dlcStation.getId();

    for (BaseFolder folder
        : db.folderDao().getAll(parentId)) {
      String folderId = folder.getId();

      String dlcFolderId = db.dlcFolderDao().getId(dlcId, folderId);
      String folderNameId = db.folderNameDao().getNameId(dlcFolderId);
      Name folderName = db.nameDao().get(folderNameId);
      println("    Folder: " + folderName.getText());

      String stationFolderId = db.stationFolderDao().getId(dlcStationId, folderId);
      List<String> engramIds = db.folderEngramDao().getEngramIds(stationFolderId);
      for (BaseEngram engram
          : db.engramDao().getAll(engramIds)) {
        String engramId = engram.getId();

        String dlcEngramId = db.dlcEngramDao().getId(dlcId, engramId);
        String engramNameId = db.engramNameDao().getNameId(dlcEngramId);
        Name engramName = db.nameDao().get(engramNameId);

        String engramDescriptionId = db.engramDescriptionDao().getDescriptionId(dlcEngramId);
        Description engramDescription = db.descriptionDao().get(engramDescriptionId);

        println("      Engram: " + engramName.getText() + " / " + engramDescription.getText());

        String stationEngramId = db.stationEngramDao().getId(dlcStationId, engramId);
        List<String> compositeIds = db.engramCompositeDao().getCompositeIds(stationEngramId);
        for (BaseComposite composite :
            db.compositeDao().getAll(compositeIds)) {
          String resourceNameId = db.resourceNameDao().getNameId(composite.getResourceId());
          Name resourceName = db.nameDao().get(resourceNameId);

          println("        " + resourceName.getText() + " x" + composite.getQuantity());
        }
      }

      printFolderData(dlcFolderId, dlcStation);
    }
  }

  private InputStream getFileFromAssets(Context context, String fileName) {
    try {
      return context.getAssets().open("AppData/" + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void println(String text) {
    System.out.println("PRINTLN/ " + text);
  }
}