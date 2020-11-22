/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import arc.resource.calculator.db.dao.DlcDao;
import arc.resource.calculator.db.dao.GameDao;
import arc.resource.calculator.db.dao.dlc.DlcCompositeDao;
import arc.resource.calculator.db.dao.dlc.DlcCompositionDao;
import arc.resource.calculator.db.dao.dlc.DlcDirectoryDao;
import arc.resource.calculator.db.dao.dlc.DlcEngramDao;
import arc.resource.calculator.db.dao.dlc.DlcFolderDao;
import arc.resource.calculator.db.dao.dlc.DlcResourceDao;
import arc.resource.calculator.db.dao.dlc.DlcStationDao;
import arc.resource.calculator.db.dao.primary.CompositeDao;
import arc.resource.calculator.db.dao.primary.CompositionDao;
import arc.resource.calculator.db.dao.primary.DirectoryDao;
import arc.resource.calculator.db.dao.primary.EngramDao;
import arc.resource.calculator.db.dao.primary.FolderDao;
import arc.resource.calculator.db.dao.primary.ResourceDao;
import arc.resource.calculator.db.dao.primary.StationDao;
import arc.resource.calculator.db.entity.DlcEntity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.db.entity.dlc.DlcCompositeEntity;
import arc.resource.calculator.db.entity.dlc.DlcCompositionEntity;
import arc.resource.calculator.db.entity.dlc.DlcDirectoryItemEntity;
import arc.resource.calculator.db.entity.dlc.DlcEngramEntity;
import arc.resource.calculator.db.entity.dlc.DlcFolderEntity;
import arc.resource.calculator.db.entity.dlc.DlcResourceEntity;
import arc.resource.calculator.db.entity.dlc.DlcStationEntity;
import arc.resource.calculator.db.entity.primary.CompositeEntity;
import arc.resource.calculator.db.entity.primary.CompositionEntity;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;

@Database(
        entities = {
                GameEntity.class,
                StationEntity.class,
                FolderEntity.class,
                EngramEntity.class,
                ResourceEntity.class,
                CompositionEntity.class,
                CompositeEntity.class,
                DirectoryItemEntity.class,
                DlcEntity.class,
                DlcStationEntity.class,
                DlcFolderEntity.class,
                DlcEngramEntity.class,
                DlcResourceEntity.class,
                DlcCompositionEntity.class,
                DlcCompositeEntity.class,
                DlcDirectoryItemEntity.class
        },
        version = 3,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String cDatabaseName = "ark_database";
    private static final int cNumberOfThreads = 4;
    private static final ExecutorService mDatabaseWriteExecutor =
            Executors.newFixedThreadPool(cNumberOfThreads);
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, cDatabaseName)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return sInstance;
    }

    public static ExecutorService writeTo() {
        return mDatabaseWriteExecutor;
    }

    public abstract GameDao gameDao();

    public abstract StationDao stationDao();

    public abstract FolderDao folderDao();

    public abstract EngramDao engramDao();

    public abstract ResourceDao resourceDao();

    public abstract CompositionDao compositionDao();

    public abstract CompositeDao compositeDao();

    public abstract DirectoryDao directoryDao();

    public abstract DlcDao dlcDao();

    public abstract DlcStationDao dlcStationDao();

    public abstract DlcFolderDao dlcFolderDao();

    public abstract DlcEngramDao dlcEngramDao();

    public abstract DlcResourceDao dlcResourceDao();

    public abstract DlcCompositionDao dlcCompositionDao();

    public abstract DlcCompositeDao dlcCompositeDao();

    public abstract DlcDirectoryDao dlcDirectoryDao();
}
