import static org.junit.Assert.assertNotNull;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import arc.resource.calculator.R;
import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.entity.BaseComposite;
import arc.resource.calculator.db.entity.BaseDLC;
import arc.resource.calculator.db.entity.BaseEngram;
import arc.resource.calculator.db.entity.BaseFolder;
import arc.resource.calculator.db.entity.BaseResource;
import arc.resource.calculator.db.entity.BaseStation;
import arc.resource.calculator.db.entity.DLCEngram;
import arc.resource.calculator.db.entity.DLCFolder;
import arc.resource.calculator.db.entity.DLCResource;
import arc.resource.calculator.db.entity.DLCStation;
import arc.resource.calculator.db.entity.Description;
import arc.resource.calculator.db.entity.ImageLocation;
import arc.resource.calculator.db.entity.Name;
import arc.resource.calculator.model.json.JSONDLC;
import arc.resource.calculator.model.json.JSONPrimary;
import arc.resource.calculator.model.json.JSONPrimary.Category;
import arc.resource.calculator.model.json.JSONPrimary.Composite;
import arc.resource.calculator.model.json.JSONPrimary.Engram;
import arc.resource.calculator.model.json.JSONPrimary.Resource;
import arc.resource.calculator.model.json.JSONPrimary.Station;
import arc.resource.calculator.model.json.JSONUpdate;
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
    JSONUpdate jsonUpdate = mapper.readValue(
        getFileFromAssets(context, context.getString(R.string.appdata_update_json)),
        JSONUpdate.class);

    // #PRIMARY
    if (jsonUpdate.updatePrimary()) {
      insertPrimaryData(mapper);
    }

    //  #DLC
    if (jsonUpdate.getDlcFiles() != null) {
      insertDLCData(mapper, jsonUpdate.getDlcFiles());
    }

//    int max = 5;
//    for (int i = 1; i <= max; i++) {
//      printLn("--[ Printing Random Data " + i + "/" + max + " ]--");
//      printRandomData();
//    }
    String searchQuery = "metal d";
    printSearchData(searchQuery);
  }

  private void insertPrimaryData(ObjectMapper mapper) throws IOException {
    //  collect, parse, and insert Primary Data
    JSONPrimary jsonPrimary = mapper.readValue(
        getFileFromAssets(context, context.getString(R.string.appdata_primary_json)),
        JSONPrimary.class);

    String contentFolder = context.getString(R.string.appdata_primary_content_folder);

    //  insert resources
    if (jsonPrimary.getResources() != null) {
      insertResources(contentFolder, jsonPrimary.getResources());
    }

    //  begin iteration of stations to folders to engrams
    if (jsonPrimary.getStations() != null) {
      insertStations(contentFolder, jsonPrimary.getStations());
    }
  }

  private void insertDLCData(ObjectMapper mapper, List<String> filenames) throws IOException {
    for (String filename : filenames) {
      //  read json data to jackson object
      JSONDLC dlc = mapper.readValue(
          getFileFromAssets(context, filename), JSONDLC.class);

      String contentFolder = dlc.getContentFolder();

      //  insert image location, get id
      String dlcImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, null,
              dlc.getImageFile()));
      assertNotNull(dlcImageLocationId);

      //  insert name, get id
      String dlcNameId = insertName(new Name(dlc.getName()));
      assertNotNull(dlcNameId);

      //  insert dlc, get id if added
      BaseDLC baseDLC = new BaseDLC(contentFolder, dlcImageLocationId, dlcNameId);
      String dlcId = baseDLC.getId();
      db.dlcDao().insert(baseDLC);

      //  iterate through list of Resource objects
      if (dlc.getResources() != null) {
        insertResources(contentFolder, dlc.getResources(), dlcId);
      }

      //  iterate through list of override objects
      if (dlc.getOverride().getStations() != null) {
        overrideStations(contentFolder, dlc.getOverride().getStations(), dlcId);
      }

      //  iterate through list of station objects
      if (dlc.getStations() != null) {
        insertStations(contentFolder, dlc.getStations(), dlcId);
      }
    }
  }

  private String insertImageLocation(ImageLocation imageLocation) {
    return db.imageLocationDao().insert(imageLocation) != INVALID_ID
        ? imageLocation.getId() : db.imageLocationDao().getId(imageLocation.getContentFolder(),
        imageLocation.getImageFolder(), imageLocation.getImageFile());
  }

  private String insertName(Name name) {
    return db.nameDao().insert(name) != INVALID_ID
        ? name.getId() : db.nameDao().getId(name.getText());
  }

  private String insertDescription(Description description) {
    return db.descriptionDao().insert(description) != INVALID_ID
        ? description.getId() : db.descriptionDao().getId(description.getText());
  }

  private void insertEngrams(String contentFolder, List<Engram> engrams, String parentId) {
    for (Engram engram : engrams) {

      //  insert image location
      String engramImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, engram.getImageFolder(), engram.getImageFile()));
      assertNotNull(engramImageLocationId);

      //  insert name
      String engramNameId = insertName(new Name(engram.getName()));
      assertNotNull(engramNameId);

      //  insert description
      String engramDescriptionId = insertDescription(new Description(engram.getDescription()));
      assertNotNull(engramDescriptionId);

      //  insert engram
      BaseEngram baseEngram = new BaseEngram(engramDescriptionId, engramImageLocationId,
          engramNameId, parentId, engram.getLevel(), engram.getYield());
      String engramId = baseEngram.getId();
      db.engramDao().insert(baseEngram);

      //  iterate through list of compositionElement objects
      for (Composite composite : engram.getComposition()) {
        //  get name id by name text
        String resourceNameId = db.nameDao().getId(composite.getName());

        //  get resource id by name id
        String resourceId = db.resourceDao().getId(resourceNameId);

        //  insert composite
        BaseComposite baseComposite = new BaseComposite(engramId, resourceId,
            composite.getQuantity());
        String compositeId = baseComposite.getId();
        db.compositeDao().insert(baseComposite);
      }
    }
  }

  private void insertEngrams(String contentFolder, List<Engram> engrams, String parentId,
      String dlcId) {
    for (Engram engram : engrams) {
      //  insert image location
      String engramImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, engram.getImageFolder(), engram.getImageFile()));
      assertNotNull(engramImageLocationId);

      //  insert name
      String engramNameId = insertName(new Name(engram.getName()));
      assertNotNull(engramNameId);

      //  insert description
      String engramDescriptionId = insertDescription(new Description(engram.getDescription()));
      assertNotNull(engramDescriptionId);

      //  insert engram
      DLCEngram dlcEngram = new DLCEngram(engramDescriptionId, engramImageLocationId,
          engramNameId, parentId, engram.getLevel(), engram.getYield(), dlcId);
      String engramId = dlcEngram.getId();
      db.dlcEngramDao().insert(dlcEngram);

      //  iterate through list of compositionElement objects
      for (Composite composite : engram.getComposition()) {
        //  get name id by name text
        String resourceNameId = db.nameDao().getId(composite.getName());

        //  get resource id by name id
        String resourceId = db.resourceDao().getId(resourceNameId);
        if (resourceId == null) {
          resourceId = db.dlcResourceDao().getId(resourceNameId);
        }

        //  insert composite
        BaseComposite baseComposite = new BaseComposite(engramId, resourceId,
            composite.getQuantity());
        db.compositeDao().insert(baseComposite);
      }
    }
  }

  private void insertFolders(String contentFolder, List<Category> folders, String parentId) {
    for (Category folder : folders) {
      //  insert name
      String folderNameId = insertName(new Name(folder.getName()));
      assertNotNull(folderNameId);

      //  insert folder
      BaseFolder baseFolder = new BaseFolder(folderNameId, parentId);
      String folderId = baseFolder.getId();
      db.folderDao().insert(baseFolder);

      //  iterate through list of engram objects
      if (folder.getEngrams() != null) {
        insertEngrams(contentFolder, folder.getEngrams(), folderId);
      }

      //  recursively sift through subcategories ftw
      if (folder.getCategories() != null) {
        insertFolders(contentFolder, folder.getCategories(), folderId);
      }
    }
  }

  private void insertFolders(String contentFolder, List<Category> folders, String parentId,
      String dlcId) {
    for (Category folder : folders) {
      //  insert name
      String nameId = insertName(new Name(folder.getName()));
      assertNotNull(nameId);

      //  insert folder
      String folderId;
      BaseFolder baseFolder = db.folderDao().get(nameId, parentId);
      if (baseFolder == null) {
        DLCFolder dlcFolder = new DLCFolder(nameId, parentId, dlcId);
        folderId = dlcFolder.getId();
        db.dlcFolderDao().insert(dlcFolder);
      } else {
        folderId = baseFolder.getId();
      }

      //  iterate through list of engram objects
      if (folder.getEngrams() != null) {
        insertEngrams(contentFolder, folder.getEngrams(), folderId, dlcId);
      }

      //  recursively sift through subcategories ftw
      if (folder.getCategories() != null) {
        insertFolders(contentFolder, folder.getCategories(), folderId, dlcId);
      }
    }
  }

  private void insertResources(String contentFolder, List<Resource> resources) {
    for (Resource resource : resources) {
      //  insert image location
      String resourceImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, resource.getImageFolder(), resource.getImageFile()));
      assertNotNull(resourceImageLocationId);

      //  insert name
      String resourceNameId = insertName(new Name(resource.getName()));
      assertNotNull(resourceNameId);

      //  insert resource, get id if added
      BaseResource baseResource = new BaseResource(resourceImageLocationId, resourceNameId);
      db.resourceDao().insert(baseResource);
    }
  }

  private void insertResources(String contentFolder, List<Resource> resources, String dlcId) {
    for (Resource resource : resources) {
      //  insert image location
      String resourceImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, resource.getImageFolder(), resource.getImageFile()));
      assertNotNull(resourceImageLocationId);

      //  insert name
      String resourceNameId = insertName(new Name(resource.getName()));
      assertNotNull(resourceNameId);

      //  insert dlc resource
      DLCResource dlcResource = new DLCResource(resourceImageLocationId, resourceNameId, dlcId);
      db.dlcResourceDao().insert(dlcResource);
    }
  }

  private void insertStations(String contentFolder, List<Station> stations) {
    for (Station station : stations) {
      //  insert image location
      String stationImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, station.getImageFolder(), station.getImageFile()));
      assertNotNull(stationImageLocationId);

      //  insert name
      String stationNameId = insertName(new Name(station.getName()));
      assertNotNull(stationNameId);

      //  insert station, get id if conflict
      BaseStation baseStation = new BaseStation(stationImageLocationId, stationNameId);
      String stationId = baseStation.getId();
      db.stationDao().insert(baseStation);

      //  iterate through list of engram objects
      if (station.getEngrams() != null) {
        insertEngrams(contentFolder, station.getEngrams(), stationId);
      }

      //  iterate through list of category objects
      if (station.getCategories() != null) {
        insertFolders(contentFolder, station.getCategories(), stationId);
      }
    }
  }

  private void insertStations(String contentFolder, List<Station> stations, String dlcId) {
    for (Station station : stations) {

      //  insert image location
      String stationImageLocationId = insertImageLocation(
          new ImageLocation(contentFolder, station.getImageFolder(), station.getImageFile()));
      assertNotNull(stationImageLocationId);

      //  insert name
      String stationNameId = insertName(new Name(station.getName()));
      assertNotNull(stationNameId);

      //  insert station, get id if conflict
      DLCStation dlcStation = new DLCStation(stationImageLocationId, stationNameId, dlcId);
      String stationId = dlcStation.getId();
      db.dlcStationDao().insert(dlcStation);

      //  iterate through list of engram objects
      if (station.getEngrams() != null) {
        insertEngrams(contentFolder, station.getEngrams(), stationId, dlcId);
      }

      //  iterate through list of category objects
      if (station.getCategories() != null) {
        insertFolders(contentFolder, station.getCategories(), stationId, dlcId);
      }
    }
  }

  private void overrideStations(String contentFolder, List<JSONDLC.Station> stations,
      String dlcId) {
    for (JSONDLC.Station station : stations) {

      String stationNameId = db.nameDao().getId(station.getName());
      assertNotNull(stationNameId);

      BaseStation baseStation = db.stationDao().getByNameId(stationNameId);
      assertNotNull(baseStation);

      String stationId = baseStation.getId();
      assertNotNull(stationId);

      String stationImageLocationId = baseStation.getImageLocationId();
      assertNotNull(stationImageLocationId);

      //  iterate through list of engram objects
      if (station.getEngrams() != null) {
        insertEngrams(contentFolder, station.getEngrams(), stationId, dlcId);
      }

      //  iterate through list of category objects
      if (station.getCategories() != null) {
        insertFolders(contentFolder, station.getCategories(), stationId, dlcId);
      }
    }
  }

  private void printSearchData(String text) {
    String query = "%" + text + "%";

    printLn("Searching for " + query);

    List<String> nameIds = db.nameDao().getByLikeText(query);

    printLn("Found " + nameIds.size() + " matches by Name.");

    List<String> engramNameIds = db.engramDao().getAllByNameIds(nameIds);

    printLn("Found " + engramNameIds.size() + " matches by Engram.");

    List<Name> names = db.nameDao().getAll(engramNameIds);

    for (Name name : names) {
      printLn(name.getText());
    }
  }

  private void printRandomData() {
    if (new Random().nextInt(1) == 1) {
      printLn("Showing Primary Game Data..");

      List<BaseStation> stations = db.stationDao().getAll();
      BaseStation station = stations.get(new Random().nextInt(stations.size()));

      Name stationName = db.nameDao().get(station.getNameId());
      printLn("Station: " + stationName.getText());

      printEngrams(db.engramDao().getAll(station.getId()), "");

      printFolders(db.folderDao().getAll(station.getId()), "");
    } else {
      printRandomDlcData();
    }
  }

  private void printRandomDlcData() {
    List<BaseDLC> dlcs = db.dlcDao().getAll();
    BaseDLC dlc = dlcs.get(new Random().nextInt(dlcs.size()));

    Name dlcName = db.nameDao().get(dlc.getNameId());
    printLn("DLC: " + dlcName.getText());

    List<BaseStation> stations = db.stationDao().getAll();
    stations.addAll(db.dlcStationDao().getAll(dlc.getId()));

    if (stations.size() > 0) {
      BaseStation station = stations.get(new Random().nextInt(stations.size()));

      Name stationName = db.nameDao().get(station.getNameId());
      printLn("Station: " + stationName.getText());

      List<BaseEngram> engrams = db.engramDao().getAll(station.getId());
      engrams.addAll(db.dlcEngramDao().getAll(station.getId(), dlc.getId()));
      printEngrams(engrams, "");

      List<BaseFolder> folders = db.folderDao().getAll(station.getId());
      folders.addAll(db.dlcFolderDao().getAll(station.getId(), dlc.getId()));
      printDLCFolders(folders, "", dlc.getId());
    }
  }

  private void printEngrams(List<BaseEngram> engrams, String indention) {
    for (BaseEngram engram : engrams) {
      Name name = db.nameDao().get(engram.getNameId());
      printLn(indention + "  Engram: " + name.getText());
    }
  }

  private void printFolders(List<BaseFolder> folders, String indention) {
    for (BaseFolder folder : folders) {
      Name name = db.nameDao().get(folder.getNameId());
      printLn(indention + "  Folder: " + name.getText());

      printEngrams(db.engramDao().getAll(folder.getId()), indention + "  ");

      printFolders(db.folderDao().getAll(folder.getId()), indention + "  ");
    }
  }

  private void printDLCFolders(List<BaseFolder> baseFolders, String indention, String dlcId) {
    for (BaseFolder folder : baseFolders) {
      Name name = db.nameDao().get(folder.getNameId());
      printLn(indention + "  Folder: " + name.getText());

      List<BaseEngram> engrams = db.engramDao().getAll(folder.getId());
      engrams.addAll(db.dlcEngramDao().getAll(folder.getId(), dlcId));
      printEngrams(engrams, indention + "  ");

      List<BaseFolder> folders = db.folderDao().getAll(folder.getId());
      folders.addAll(db.dlcFolderDao().getAll(folder.getId(), dlcId));
      printDLCFolders(folders, indention + "  ", dlcId);
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

  private void printLn(String text) {
    System.out.println("PRINTLN/ " + text);
  }
}