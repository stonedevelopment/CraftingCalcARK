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

import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveRepository;

public class SearchRepository extends InteractiveRepository {
    public static final String TAG = SearchRepository.class.getSimpleName();

    SearchRepository(Application application) {
        super(application);
    }

    LiveData<List<EngramEntity>> searchEngrams(String searchText, String gameId, String dlcId) {
        return getEngramDao().searchByName("%" + searchText + "%", gameId);
    }

    LiveData<List<ResourceEntity>> searchResources(String searchText, String gameId, String dlcId) {
        return getResourceDao().searchByName("%" + searchText + "%", gameId);
    }

    LiveData<List<StationEntity>> searchStations(String searchText, String gameId, String dlcId) {
        return getStationDao().searchByName("%" + searchText + "%", gameId);
    }

    LiveData<List<FolderEntity>> searchFolders(String searchText, String gameId, String dlcId) {
        return getFolderDao().searchByName("%" + searchText + "%", gameId);
    }
}