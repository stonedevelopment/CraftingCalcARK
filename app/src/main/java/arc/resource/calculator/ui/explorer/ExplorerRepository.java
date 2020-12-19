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
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.dao.dlc.DlcDirectoryDao;
import arc.resource.calculator.db.dao.primary.DirectoryDao;
import arc.resource.calculator.db.entity.dlc.DlcDirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveRepository;

public class ExplorerRepository extends InteractiveRepository {
    public static final String TAG = ExplorerRepository.class.getSimpleName();

    private final DirectoryDao directoryDao;
    private final DlcDirectoryDao dlcDirectoryDao;

    ExplorerRepository(Application application) {
        super(application);
        directoryDao = getAppDatabase().directoryDao();
        dlcDirectoryDao = getAppDatabase().dlcDirectoryDao();
    }

    LiveData<List<DirectoryItemEntity>> fetchDirectory(String gameId, String parentId) {
        Log.d(TAG, "fetchDirectory: " + parentId);
        return directoryDao.getDirectoryItemList(gameId, parentId);
    }

    LiveData<List<DlcDirectoryItemEntity>> fetchDirectory(String gameId, String dlcId, String parentId) {
        Log.d(TAG, "fetchDirectory: " + parentId);
        return dlcDirectoryDao.getDirectoryItemList(gameId, dlcId, parentId);
    }

    LiveData<EngramEntity> fetchEngram(@NonNull String uuid) {
        return getEngramDao().getEngram(uuid);
    }

    LiveData<FolderEntity> fetchFolder(@NonNull String uuid) {
        return getFolderDao().getFolder(uuid);
    }

    LiveData<StationEntity> fetchStation(@NonNull String uuid) {
        return getStationDao().getStation(uuid);
    }
}