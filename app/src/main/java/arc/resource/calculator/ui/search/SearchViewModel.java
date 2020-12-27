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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.model.ui.InteractiveGameViewModel;
import arc.resource.calculator.ui.search.model.SearchItem;

public class SearchViewModel extends InteractiveGameViewModel {
    public static final String TAG = SearchViewModel.class.getCanonicalName();

    private static final int cMinimumSearchLength = 1;

    private final SingleLiveEvent<String> filterTextEvent = new SingleLiveEvent<>();
    private LiveData<List<SearchItem>> searchResultsLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void setupRepository(Application application) {
        setRepository(new SearchRepository(application));
    }

    @Override
    protected SearchRepository getRepository() {
        return (SearchRepository) super.getRepository();
    }

    String getFilterText() {
        return filterTextEvent.getValue();
    }

    LiveData<List<SearchItem>> getSearchLiveData() {
        return transformResults();
    }

    void handleEditTextEvent(String text) {
        if (text.length() >= cMinimumSearchLength) {
            startSearch(text);
        } else {
            clearSearch();
        }
    }

    void clearSearch() {
        filterTextEvent.setValue("");
    }

    void startSearch(String filterText) {
        filterTextEvent.setValue(filterText);
    }

    protected LiveData<List<EngramEntity>> transformFilterText() {
        return Transformations.switchMap(filterTextEvent, filterText -> {
            String gameId = getGameEntityId();
            String dlcId = null;
//          String dlcId = getDlcEntityId();
            return getRepository().searchEngrams(filterText, gameId, dlcId);
        });
    }

    protected LiveData<List<SearchItem>> transformResults() {
        return Transformations.map(transformFilterText(), engramEntityList -> {
            List<SearchItem> outList = new ArrayList<>();
            for (EngramEntity engramEntity : engramEntityList) {
                outList.add(SearchItem.fromEngramEntity(engramEntity));
            }
            return outList;
        });
    }
}