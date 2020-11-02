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

    SearchRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        engramDao = db.engramDao();
        resourceDao = db.resourceDao();
        stationDao = db.stationDao();
        folderDao = db.folderDao();
    }

    LiveData<List<EngramEntity>> searchEngrams(@Nullable String searchCriteria) {
        return engramDao.searchByName("%" + searchCriteria + "%");
    }

    LiveData<List<ResourceEntity>> searchResources(@Nullable String searchCriteria) {
        return resourceDao.searchByName("%" + searchCriteria + "%");
    }

    LiveData<List<StationEntity>> searchStations(@Nullable String searchCriteria) {
        return stationDao.searchByName("%" + searchCriteria + "%");
    }

    LiveData<List<FolderEntity>> searchFolders(@Nullable String searchCriteria) {
        return folderDao.searchByName("%" + searchCriteria + "%");
    }
}