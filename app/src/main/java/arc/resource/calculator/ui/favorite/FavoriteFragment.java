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

package arc.resource.calculator.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.InteractiveGameFragment;
import arc.resource.calculator.ui.favorite.model.FavoriteItem;

public class FavoriteFragment extends InteractiveGameFragment {
    public static final String TAG = FavoriteFragment.class.getCanonicalName();

    private MaterialTextView noFavorites;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return setViews(inflater.inflate(R.layout.favorites_fragment, container, false));
    }

    @Override
    protected View setViews(View rootView) {
        noFavorites = rootView.findViewById(R.id.noResultsTextView);
        return super.setViews(rootView);
    }

    @Override
    public FavoriteViewModel getViewModel() {
        return (FavoriteViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        setViewModel(new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class));
        super.setupViewModel();
    }

    @Override
    protected void setupViews() {
        FavoriteItemAdapter adapter = new FavoriteItemAdapter(this, getViewModel());
        FavoriteLayoutManager layoutManager = new FavoriteLayoutManager(this, getViewModel());
        setupViews(adapter, layoutManager);
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        getViewModel().getFavoritesListLiveData().observe(getViewLifecycleOwner(), this::handleFavoritesList);
    }

    // TODO: 2/10/2021 handleFavoritesList should observe an isEmpty boolean instead of a full list of data
    private void handleFavoritesList(List<FavoriteItem> favoriteItemList) {
        Log.d(TAG, "handleFavoritesList: " + favoriteItemList.size());
        if (favoriteItemList.size() == 0) {
            noFavorites.setVisibility(View.VISIBLE);
        } else {
            noFavorites.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void showLoading() {
        super.showLoading();
        noFavorites.setVisibility(View.INVISIBLE);
    }
}