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

package arc.resource.calculator.ui.explorer.folder;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.FolderDao;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;

import static arc.resource.calculator.db.AppDatabase.cParentId;

public class FolderExplorerRepository {
    private final FolderDao mDao;
    private MutableLiveData<List<FolderEntity>> mFolders = new MutableLiveData<>();

    public FolderExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.folderDao();
    }

    public LiveData<List<FolderEntity>> getFolders() {
        return mFolders;
    }

    /**
     * User-derived action to "open" a crafting station and view its contents
     */
    public void selectStation(StationEntity station) {
        fetchFolders(station.getId(), cParentId);
    }

    /**
     * User-derived "back" action to "close" current station and view all stations
     */
    public void deselectStation() {
        clearFolders();
    }

    /**
     * User-derived action to "open" a folder and view its contents
     */
    public void selectFolder(FolderEntity folder) {
        fetchFolders(folder.getStationId(), folder.getParentId());
    }

    private void fetchFolders(int stationId, int parentId) {
        mFolders = mDao.getFolders(stationId, parentId);
    }

    private void clearFolders() {
        mFolders.setValue(new ArrayList<>());
    }
}