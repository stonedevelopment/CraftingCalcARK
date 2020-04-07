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

package arc.resource.calculator.ui.explorer.engram;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.EngramDao;
import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;

import static arc.resource.calculator.db.AppDatabase.cParentId;

public class EngramExplorerRepository {
    private final EngramDao mDao;
    private MutableLiveData<List<EngramEntity>> mEngrams;

    public EngramExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.engramDao();
    }

    public LiveData<List<EngramEntity>> getEngrams() {
        return mEngrams;
    }

    /**
     * User-derived action to "open" a crafting station and view its contents
     */
    public void selectStation(StationEntity stationEntity) {
        fetchEngrams(stationEntity.getId(), cParentId);
    }

    /**
     * User-derived "back" action to "close" current station and view all stations
     */
    public void deselectStation() {
        clearEngrams();
    }

    /**
     * User-derived action to "open" a folder and view its contents
     */
    public void selectFolder(FolderEntity folderEntity) {
        fetchEngrams(folderEntity.getStationId(), folderEntity.getParentId());
    }

    private void fetchEngrams(int stationId, int parentId) {
        mEngrams = mDao.getEngrams(stationId, parentId);
    }

    private void clearEngrams() {
        mEngrams.setValue(new ArrayList<>());
    }
}
