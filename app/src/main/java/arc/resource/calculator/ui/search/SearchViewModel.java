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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.model.InteractiveViewModel;
import arc.resource.calculator.ui.search.model.SearchItem;

public class SearchViewModel extends InteractiveViewModel {
    public static final String TAG = SearchViewModel.class.getCanonicalName();

    private final SearchRepository repository;

    private String filterText = "";
    private MutableLiveData<String> filterTextEvent = new MutableLiveData<>();
    private LiveData<List<EngramEntity>> engramSearchResults;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new SearchRepository(application);
        engramSearchResults = Transformations.switchMap(filterTextEvent, input -> {
            filterText = input;
            return repository.searchEngrams(input);
        });
    }

    // TODO: 10/26/2020 this removes and resets search, consider adding to previous search results?
    void handleEditTextEvent(String text) {
        filterTextEvent.setValue(text);
    }

    void handleOnClickEvent(SearchItem searchItem) {
        Log.d(TAG, "handleOnClickEvent: " + searchItem.getTitle());
    }

    public String getFilterText() {
        return filterText;
    }

    public LiveData<List<EngramEntity>> getEngramSearchResults() {
        return engramSearchResults;
    }
}