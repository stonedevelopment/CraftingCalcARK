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

package arc.resource.calculator.ui.explorer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.primary.DirectoryDao;
import arc.resource.calculator.db.dao.primary.EngramDao;
import arc.resource.calculator.db.dao.primary.FolderDao;
import arc.resource.calculator.db.dao.primary.StationDao;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;

public class ExplorerRepository {
    public static final String TAG = ExplorerRepository.class.getSimpleName();

    private final DirectoryDao directoryDao;
    private final EngramDao engramDao;
    private final FolderDao folderDao;
    private final StationDao stationDao;

    ExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        directoryDao = db.directoryDao();
        engramDao = db.engramDao();
        folderDao = db.folderDao();
        stationDao = db.stationDao();
    }

    LiveData<List<DirectoryItemEntity>> fetchDirectory(@Nullable String parentId) {
        Log.d(TAG, "fetchDirectory: " + parentId);
        return directoryDao.getDirectoryItemList(parentId);
    }

    LiveData<EngramEntity> fetchEngram(@NonNull String uuid) {
        return engramDao.getEngram(uuid);
    }

    LiveData<FolderEntity> fetchFolder(@NonNull String uuid) {
        return folderDao.getFolder(uuid);
    }

    LiveData<StationEntity> fetchStation(@NonNull String uuid) {
        return stationDao.getStation(uuid);
    }
}