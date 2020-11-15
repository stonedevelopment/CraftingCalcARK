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
import arc.resource.calculator.model.ui.InteractiveViewModel;
import arc.resource.calculator.ui.search.model.SearchItem;

public class SearchViewModel extends InteractiveViewModel {
    public static final String TAG = SearchViewModel.class.getCanonicalName();
    private static final int SOURCE_TOTAL = 3;

    private final SearchRepository repository;
    private final MediatorLiveData<List<SearchItem>> searchLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<String> filterTextEvent = new SingleLiveEvent<>();
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

    void handleEditTextEvent(String text) {
        if (text.length() >= 1) {
            filterTextEvent.setValue(text);
        } else {
            clearSearch();
        }
    }

    void handleOnClickEvent(SearchItem searchItem) {
        Log.d(TAG, "handleOnClickEvent: " + searchItem.getTitle());
    }

    void clearSearch() {
        searchItemList.clear();
        endSearch();
    }

    void beginSearch(String searchText) {
        setIsLoading(true);
        searchItemList.clear();

        remainingSources = SOURCE_TOTAL;

        engramLiveData = repository.searchEngrams(searchText);
        resourceLiveData = repository.searchResources(searchText);
        stationLiveData = repository.searchStations(searchText);
        folderLiveData = repository.searchFolders(searchText);

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
//
//        searchLiveData.addSource(folderLiveData, entities -> {
//            for (FolderEntity entity : entities) {
//                searchItemList.add(SearchItem.fromFolderEntity(entity, gameEntity.getFolderFile()));
//            }
//
//            searchLiveData.removeSource(folderLiveData);
//            if (--remainingSources == 0)
//                searchLiveData.setValue(searchItemList);
//        });
    }

    void endSearch() {
        searchLiveData.setValue(searchItemList);
        setIsLoading(false);
    }
}