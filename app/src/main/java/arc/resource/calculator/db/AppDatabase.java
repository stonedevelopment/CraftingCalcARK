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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import arc.resource.calculator.db.dao.primary.CompositeDao;
import arc.resource.calculator.db.dao.primary.CompositionDao;
import arc.resource.calculator.db.dao.primary.DirectoryDao;
import arc.resource.calculator.db.dao.primary.EngramDao;
import arc.resource.calculator.db.dao.primary.FolderDao;
import arc.resource.calculator.db.dao.primary.ResourceDao;
import arc.resource.calculator.db.dao.primary.StationDao;
import arc.resource.calculator.db.entity.primary.CompositeEntity;
import arc.resource.calculator.db.entity.primary.CompositionEntity;
import arc.resource.calculator.db.entity.primary.DirectoryEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;

@Database(
        entities = {
                StationEntity.class,
                FolderEntity.class,
                EngramEntity.class,
                ResourceEntity.class,
                CompositionEntity.class,
                CompositeEntity.class,
                DirectoryEntity.class
        },
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final int cParentId = 0;

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

    public abstract StationDao stationDao();

    public abstract FolderDao folderDao();

    public abstract EngramDao engramDao();

    public abstract ResourceDao resourceDao();

    public abstract CompositionDao compositionDao();

    public abstract CompositeDao compositeDao();

    public abstract DirectoryDao directoryDao();
}
