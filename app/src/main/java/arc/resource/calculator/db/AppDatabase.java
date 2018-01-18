package arc.resource.calculator.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import arc.resource.calculator.db.dao.BaseCompositeDao;
import arc.resource.calculator.db.dao.BaseDLCDao;
import arc.resource.calculator.db.dao.BaseEngramDao;
import arc.resource.calculator.db.dao.BaseFolderDao;
import arc.resource.calculator.db.dao.BaseResourceDao;
import arc.resource.calculator.db.dao.BaseStationDao;
import arc.resource.calculator.db.dao.DLCEngramDao;
import arc.resource.calculator.db.dao.DLCFolderDao;
import arc.resource.calculator.db.dao.DLCNameDao;
import arc.resource.calculator.db.dao.DLCResourceDao;
import arc.resource.calculator.db.dao.DLCStationDao;
import arc.resource.calculator.db.dao.DescriptionDao;
import arc.resource.calculator.db.dao.EngramCompositeDao;
import arc.resource.calculator.db.dao.EngramDescriptionDao;
import arc.resource.calculator.db.dao.EngramNameDao;
import arc.resource.calculator.db.dao.FolderEngramDao;
import arc.resource.calculator.db.dao.FolderNameDao;
import arc.resource.calculator.db.dao.ImageLocationDao;
import arc.resource.calculator.db.dao.NameDao;
import arc.resource.calculator.db.dao.QueueDao;
import arc.resource.calculator.db.dao.ResourceNameDao;
import arc.resource.calculator.db.dao.StationEngramDao;
import arc.resource.calculator.db.dao.StationFolderDao;
import arc.resource.calculator.db.dao.StationNameDao;
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
import arc.resource.calculator.db.entity.EngramDescription;
import arc.resource.calculator.db.entity.EngramName;
import arc.resource.calculator.db.entity.FolderEngram;
import arc.resource.calculator.db.entity.FolderName;
import arc.resource.calculator.db.entity.ImageLocation;
import arc.resource.calculator.db.entity.Name;
import arc.resource.calculator.db.entity.Queue;
import arc.resource.calculator.db.entity.ResourceName;
import arc.resource.calculator.db.entity.StationEngram;
import arc.resource.calculator.db.entity.StationFolder;
import arc.resource.calculator.db.entity.StationName;

@Database(version = 7,
    entities = {
        BaseComposite.class,
        BaseDLC.class,
        BaseEngram.class,
        BaseFolder.class,
        BaseResource.class,
        BaseStation.class,
        Description.class,
        DLCEngram.class,
        DLCFolder.class,
        DLCName.class,
        DLCResource.class,
        DLCStation.class,
        EngramComposite.class,
        EngramDescription.class,
        EngramName.class,
        FolderEngram.class,
        FolderName.class,
        ImageLocation.class,
        Name.class,
        Queue.class,
        ResourceName.class,
        StationEngram.class,
        StationFolder.class,
        StationName.class
    })

public abstract class AppDatabase extends RoomDatabase {

  private static final String DB_NAME = "database.db";
  private static volatile AppDatabase sInstance;

  public static synchronized AppDatabase getInstance(Context context) {
    if (sInstance == null) {
      sInstance = createInstance(context);
    }

    return sInstance;
  }

  private static final Migration MIGRATION_6_7 = new Migration(6, 7) {
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
      // Since we didn't alter the table, there's nothing else to do here.
    }
  };

  private static AppDatabase createInstance(final Context context) {
    return Room.databaseBuilder(context.getApplicationContext(),
        AppDatabase.class, DB_NAME)
        .addMigrations(MIGRATION_6_7)
        .build();
  }

  public abstract BaseCompositeDao compositeDao();

  public abstract BaseDLCDao dlcDao();

  public abstract BaseEngramDao engramDao();

  public abstract BaseFolderDao folderDao();

  public abstract BaseResourceDao resourceDao();

  public abstract BaseStationDao stationDao();

  public abstract DescriptionDao descriptionDao();

  public abstract DLCEngramDao dlcEngramDao();

  public abstract DLCFolderDao dlcFolderDao();

  public abstract DLCNameDao dlcNameDao();

  public abstract DLCResourceDao dlcResourceDao();

  public abstract DLCStationDao dlcStationDao();

  public abstract EngramCompositeDao engramCompositeDao();

  public abstract EngramDescriptionDao engramDescriptionDao();

  public abstract EngramNameDao engramNameDao();

  public abstract FolderEngramDao folderEngramDao();

  public abstract FolderNameDao folderNameDao();

  public abstract ImageLocationDao imageLocationDao();

  public abstract NameDao nameDao();

  public abstract QueueDao queueDao();

  public abstract ResourceNameDao resourceNameDao();

  public abstract StationEngramDao stationEngramDao();

  public abstract StationFolderDao stationFolderDao();

  public abstract StationNameDao stationNameDao();
}
