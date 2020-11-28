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

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.interactive.InteractiveAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveItemViewHolder;
import arc.resource.calculator.ui.search.model.SearchItem;
import arc.resource.calculator.ui.search.view.EngramSearchItemViewHolder;
import arc.resource.calculator.ui.search.view.FolderSearchItemViewHolder;
import arc.resource.calculator.ui.search.view.ResourceSearchItemViewHolder;
import arc.resource.calculator.ui.search.view.SearchItemViewHolder;
import arc.resource.calculator.ui.search.view.StationSearchItemViewHolder;

import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cResourceViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class SearchItemAdapter extends InteractiveAdapter {
    public static final String TAG = SearchItemAdapter.class.getCanonicalName();

    SearchItemAdapter(SearchFragment fragment, SearchViewModel viewModel) {
        super(fragment, viewModel);
    }

    @NonNull
    @Override
    public InteractiveItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case cResourceViewType:
                itemView = getLayoutInflater().inflate(R.layout.search_item_resource, parent, false);
                return new ResourceSearchItemViewHolder(itemView);
            case cEngramViewType:
                itemView = getLayoutInflater().inflate(R.layout.search_item_engram, parent, false);
                return new EngramSearchItemViewHolder(itemView);
            case cFolderViewType:
                itemView = getLayoutInflater().inflate(R.layout.search_item_folder, parent, false);
                return new FolderSearchItemViewHolder(itemView);
            case cStationViewType:
                itemView = getLayoutInflater().inflate(R.layout.search_item_station, parent, false);
                return new StationSearchItemViewHolder(itemView);
            default:
                itemView = getLayoutInflater().inflate(R.layout.search_item_error, parent, false);
        }

        return new SearchItemViewHolder(itemView);
    }

    @Override
    protected SearchViewModel getViewModel() {
        return (SearchViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        super.setupViewModel();
        getViewModel().getSearchLiveData().observe(getActivity(), this::handleSearchResults);
    }

    @Override
    protected SearchItem getItem(int position) {
        return (SearchItem) super.getItem(position);
    }

    public void handleSearchResults(List<SearchItem> searchItems) {
        clearItemList();
        for (SearchItem searchItem : searchItems) {
            addToItemList(searchItem);
        }
        notifyDataSetChanged();
    }
}