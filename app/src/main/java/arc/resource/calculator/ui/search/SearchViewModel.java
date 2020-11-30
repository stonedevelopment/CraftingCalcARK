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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.model.ui.InteractiveGameViewModel;
import arc.resource.calculator.ui.search.model.SearchItem;

public class SearchViewModel extends InteractiveGameViewModel {
    public static final String TAG = SearchViewModel.class.getCanonicalName();
    private static final int SOURCE_TOTAL = 4;

    private final SearchRepository repository;
    private final MediatorLiveData<List<SearchItem>> searchLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<String> filterTextEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> totalMatchesEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Boolean> clearSearchEvent = new SingleLiveEvent<>();
    private List<SearchItem> searchItemList = new ArrayList<>();
    private int remainingSources = 0;
    private LiveData<List<EngramEntity>> engramLiveData;
    private LiveData<List<ResourceEntity>> resourceLiveData;
    private LiveData<List<StationEntity>> stationLiveData;
    private LiveData<List<FolderEntity>> folderLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new SearchRepository(application);
    }

    String getFilterText() {
        return filterTextEvent.getValue();
    }

    MutableLiveData<String> getFilterTextEvent() {
        return filterTextEvent;
    }

    MediatorLiveData<List<SearchItem>> getSearchLiveData() {
        return searchLiveData;
    }

    public SingleLiveEvent<Boolean> getClearSearchEvent() {
        return clearSearchEvent;
    }

    public SingleLiveEvent<Integer> getTotalMatchesEvent() {
        return totalMatchesEvent;
    }

    void handleEditTextEvent(String text) {
        if (text.length() >= 1) {
            filterTextEvent.setValue(text);
        } else {
            clearSearch();
        }
    }

    void clearSearch() {
        searchItemList.clear();
        endSearch();
        clearSearchEvent.setValue(true);
    }

    void beginSearch(String searchText) {
        searchItemList.clear();

        remainingSources = SOURCE_TOTAL;

        String gameId = getGameEntityId();
        String dlcId = null;
//        String dlcId = getDlcEntityId();

        engramLiveData = repository.searchEngrams(searchText, gameId, dlcId);
        resourceLiveData = repository.searchResources(searchText, gameId, dlcId);
        stationLiveData = repository.searchStations(searchText, gameId, dlcId);
        folderLiveData = repository.searchFolders(searchText, gameId, dlcId);

        searchLiveData.addSource(engramLiveData, entities -> {
            for (EngramEntity entity : entities) {
                searchItemList.add(SearchItem.fromEngramEntity(entity));
            }

            searchLiveData.removeSource(engramLiveData);
            if (--remainingSources == 0) endSearch();
        });

        searchLiveData.addSource(resourceLiveData, entities -> {
            for (ResourceEntity entity : entities) {
                searchItemList.add(SearchItem.fromResourceEntity(entity));
            }

            searchLiveData.removeSource(resourceLiveData);
            if (--remainingSources == 0) endSearch();
        });

        searchLiveData.addSource(stationLiveData, entities -> {
            for (StationEntity entity : entities) {
                searchItemList.add(SearchItem.fromStationEntity(entity));
            }

            searchLiveData.removeSource(stationLiveData);
            if (--remainingSources == 0) endSearch();
        });

        searchLiveData.addSource(folderLiveData, entities -> {
            for (FolderEntity entity : entities) {
                searchItemList.add(SearchItem.fromFolderEntity(entity));
            }

            searchLiveData.removeSource(folderLiveData);
            if (--remainingSources == 0) endSearch();
        });
    }

    void endSearch() {
        getSearchLiveData().setValue(searchItemList);
        getTotalMatchesEvent().setValue(searchItemList.size());
    }
}