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
import arc.resource.calculator.db.dao.BaseModDao;
import arc.resource.calculator.db.dao.BaseQualityDao;
import arc.resource.calculator.db.dao.BaseResourceDao;
import arc.resource.calculator.db.dao.BaseStationDao;
import arc.resource.calculator.db.dao.DLCEngramDao;
import arc.resource.calculator.db.dao.DLCFolderDao;
import arc.resource.calculator.db.dao.DLCResourceDao;
import arc.resource.calculator.db.dao.DLCStationDao;
import arc.resource.calculator.db.dao.DescriptionDao;
import arc.resource.calculator.db.dao.ImageLocationDao;
import arc.resource.calculator.db.dao.ModEngramDao;
import arc.resource.calculator.db.dao.ModFolderDao;
import arc.resource.calculator.db.dao.ModResourceDao;
import arc.resource.calculator.db.dao.ModStationDao;
import arc.resource.calculator.db.dao.NameDao;
import arc.resource.calculator.db.dao.PrimaryEngramDao;
import arc.resource.calculator.db.dao.PrimaryFolderDao;
import arc.resource.calculator.db.dao.PrimaryResourceDao;
import arc.resource.calculator.db.dao.PrimaryStationDao;
import arc.resource.calculator.db.dao.QueueDao;
import arc.resource.calculator.db.dao.SearchDao;
import arc.resource.calculator.db.entity.BaseComposite;
import arc.resource.calculator.db.entity.BaseDLC;
import arc.resource.calculator.db.entity.BaseEngram;
import arc.resource.calculator.db.entity.BaseFolder;
import arc.resource.calculator.db.entity.BaseMod;
import arc.resource.calculator.db.entity.BaseQuality;
import arc.resource.calculator.db.entity.BaseResource;
import arc.resource.calculator.db.entity.BaseStation;
import arc.resource.calculator.db.entity.BlueprintEngram;
import arc.resource.calculator.db.entity.DLCEngram;
import arc.resource.calculator.db.entity.DLCFolder;
import arc.resource.calculator.db.entity.DLCResource;
import arc.resource.calculator.db.entity.DLCStation;
import arc.resource.calculator.db.entity.Description;
import arc.resource.calculator.db.entity.ImageLocation;
import arc.resource.calculator.db.entity.ModEngram;
import arc.resource.calculator.db.entity.ModFolder;
import arc.resource.calculator.db.entity.ModResource;
import arc.resource.calculator.db.entity.ModStation;
import arc.resource.calculator.db.entity.Name;
import arc.resource.calculator.db.entity.PrimaryEngram;
import arc.resource.calculator.db.entity.PrimaryFolder;
import arc.resource.calculator.db.entity.PrimaryResource;
import arc.resource.calculator.db.entity.PrimaryStation;
import arc.resource.calculator.db.entity.Queue;

@Database(version = 7,
    entities = {
        BaseComposite.class,
        BaseDLC.class,
        BaseEngram.class,
        BaseFolder.class,
        BaseMod.class,
        BaseQuality.class,
        BaseResource.class,
        BaseStation.class,
        BlueprintEngram.class,
        Description.class,
        DLCEngram.class,
        DLCFolder.class,
        DLCResource.class,
        DLCStation.class,
        ImageLocation.class,
        ModEngram.class,
        ModFolder.class,
        ModResource.class,
        ModStation.class,
        Name.class,
        PrimaryEngram.class,
        PrimaryFolder.class,
        PrimaryResource.class,
        PrimaryStation.class,
        Queue.class
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

  public abstract BaseModDao modDao();

  public abstract BaseQualityDao qualityDao();

  public abstract BaseResourceDao resourceDao();

  public abstract BaseStationDao stationDao();

  public abstract DescriptionDao descriptionDao();

  public abstract DLCEngramDao dlcEngramDao();

  public abstract DLCFolderDao dlcFolderDao();

  public abstract DLCResourceDao dlcResourceDao();

  public abstract DLCStationDao dlcStationDao();

  public abstract ImageLocationDao imageLocationDao();

  public abstract ModEngramDao modEngramDao();

  public abstract ModFolderDao modFolderDao();

  public abstract ModResourceDao modResourceDao();

  public abstract ModStationDao modStationDao();

  public abstract NameDao nameDao();

  public abstract PrimaryEngramDao primaryEngramDao();

  public abstract PrimaryFolderDao primaryFolderDao();

  public abstract PrimaryResourceDao primaryResourceDao();

  public abstract PrimaryStationDao primaryStationDao();

  public abstract QueueDao queueDao();

  public abstract SearchDao searchDao();
}
