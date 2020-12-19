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

package arc.resource.calculator.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.interactive.InteractiveItemAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveFragment;
import arc.resource.calculator.model.ui.interactive.InteractiveLayoutManager;

public class FavoritesFragment extends InteractiveFragment {
    public static final String TAG = FavoritesFragment.class.getCanonicalName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return setViews(inflater.inflate(R.layout.favorites_fragment, container, false));
    }

    @Override
    public FavoritesViewModel getViewModel() {
        return (FavoritesViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        setViewModel(new ViewModelProvider(requireActivity()).get(FavoritesViewModel.class));
        getViewModel().setup(requireActivity());
        super.setupViewModel();
    }

    @Override
    protected void setupViews() {
        FavoritesItemAdapter adapter = new FavoritesItemAdapter(this, getViewModel());
        FavoritesLayoutManager layoutManager = new FavoritesLayoutManager(this, getViewModel());
        setupViews(adapter, layoutManager);
    }

    @Override
    protected void setupViews(InteractiveItemAdapter adapter, InteractiveLayoutManager layoutManager) {
        super.setupViews(adapter, layoutManager);
    }
}