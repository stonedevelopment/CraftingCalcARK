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

package arc.resource.calculator.ui.search;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.dlc.DlcEngramDao;
import arc.resource.calculator.db.dao.dlc.DlcFolderDao;
import arc.resource.calculator.db.dao.dlc.DlcResourceDao;
import arc.resource.calculator.db.dao.dlc.DlcStationDao;
import arc.resource.calculator.db.dao.primary.EngramDao;
import arc.resource.calculator.db.dao.primary.FolderDao;
import arc.resource.calculator.db.dao.primary.ResourceDao;
import arc.resource.calculator.db.dao.primary.StationDao;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;

public class SearchRepository {
    public static final String TAG = SearchRepository.class.getSimpleName();

    private final EngramDao engramDao;
    private final ResourceDao resourceDao;
    private final StationDao stationDao;
    private final FolderDao folderDao;

    private final DlcEngramDao dlcEngramDao;
    private final DlcResourceDao dlcResourceDao;
    private final DlcStationDao dlcStationDao;
    private final DlcFolderDao dlcFolderDao;

    SearchRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        engramDao = db.engramDao();
        resourceDao = db.resourceDao();
        stationDao = db.stationDao();
        folderDao = db.folderDao();

        dlcEngramDao = db.dlcEngramDao();
        dlcResourceDao = db.dlcResourceDao();
        dlcStationDao = db.dlcStationDao();
        dlcFolderDao = db.dlcFolderDao();
    }

    LiveData<List<EngramEntity>> searchEngrams(String searchText, String gameId, String dlcId) {
        return engramDao.searchByName("%" + searchText + "%", gameId);
    }

    LiveData<List<ResourceEntity>> searchResources(String searchText, String gameId, String dlcId) {
        return resourceDao.searchByName("%" + searchText + "%", gameId);
    }

    LiveData<List<StationEntity>> searchStations(String searchText, String gameId, String dlcId) {
        return stationDao.searchByName("%" + searchText + "%", gameId);
    }

    LiveData<List<FolderEntity>> searchFolders(String searchText, String gameId, String dlcId) {
        return folderDao.searchByName("%" + searchText + "%", gameId);
    }
}