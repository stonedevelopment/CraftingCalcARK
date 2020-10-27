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
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;
import java.util.Objects;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.ui.main.MainViewModel;

public class SearchFragment extends Fragment {
    public static final String TAG = SearchFragment.class.getCanonicalName();

    private SearchViewModel viewModel;
    private SearchItemAdapter adapter;

    private GameEntity gameEntity;

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MaterialTextView textView;
    private ContentLoadingProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return setViews(inflater.inflate(R.layout.search_fragment, container, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupViews();
    }

    private View setViews(View rootView) {
        searchView = rootView.findViewById(R.id.searchView);
        recyclerView = rootView.findViewById(R.id.searchRecyclerView);
        textView = rootView.findViewById(R.id.searchResultsTextView);
        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    private void setupViews() {
        searchView.setQuery(viewModel.getFilterText(), false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.handleEditTextEvent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        int numCols = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numCols));
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        viewModel.getSnackBarMessageEvent().observe(getViewLifecycleOwner(), this::showSnackBar);
        viewModel.getLoadingEvent().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) showLoading();
            else showLoaded();
        });

        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.getGameEntityEvent().observe(getViewLifecycleOwner(), this::setupAdapter);
    }

    private void setupAdapter(GameEntity gameEntity) {
        adapter = new SearchItemAdapter(this, gameEntity.getFilePath());
        recyclerView.setAdapter(adapter);

        viewModel.getEngramSearchResults().observe(getViewLifecycleOwner(), engramEntityList -> {
            adapter.mapSearchResults(engramEntityList);
            textView.setText(String.format(Locale.ENGLISH, "%d results found", engramEntityList.size()));
        });
    }

    private void showLoading() {
        progressBar.show();
    }

    private void showLoaded() {
        progressBar.hide();
    }

    private void showSnackBar(String message) {
        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.searchCoordinatorLayout), message, Snackbar.LENGTH_SHORT).show();
    }
}
