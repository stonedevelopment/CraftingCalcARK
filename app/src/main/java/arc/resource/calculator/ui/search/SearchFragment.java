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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.InteractiveGameFragment;

public class SearchFragment extends InteractiveGameFragment {
    public static final String TAG = SearchFragment.class.getCanonicalName();

    private SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return setViews(inflater.inflate(R.layout.search_fragment, container, false));
    }

    @Override
    protected View setViews(View rootView) {
        searchView = rootView.findViewById(R.id.searchView);
        return super.setViews(rootView);
    }

    @Override
    public SearchViewModel getViewModel() {
        return (SearchViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        setViewModel(new ViewModelProvider(requireActivity()).get(SearchViewModel.class));
        super.setupViewModel();
    }

    @Override
    protected void setupViews() {
        searchView.setQuery(getViewModel().getFilterText(), false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                getViewModel().handleEditTextEvent(queryText);
                return false;
            }
        });

        SearchItemAdapter adapter = new SearchItemAdapter(this, getViewModel());
        SearchLayoutManager layoutManager = new SearchLayoutManager(this, getViewModel());
        setupViews(adapter, layoutManager);
    }

    @Override
    protected void showLoading() {
        super.showLoading();
        searchView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void showLoaded() {
        super.showLoaded();
        searchView.setVisibility(View.VISIBLE);
    }
}
