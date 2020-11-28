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

package arc.resource.calculator.ui.explorer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.InteractiveGameFragment;

public class ExplorerFragment extends InteractiveGameFragment {
    public static final String TAG = ExplorerFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return setViews(inflater.inflate(R.layout.explorer_fragment, container, false));
    }

    @Override
    public ExplorerViewModel getViewModel() {
        return (ExplorerViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        setViewModel(new ViewModelProvider(requireActivity()).get(ExplorerViewModel.class));
        super.setupViewModel();
    }

    @Override
    protected void setupViews() {
        ExplorerItemAdapter adapter = new ExplorerItemAdapter(this, getViewModel());
        ExplorerLayoutManager layoutManager = new ExplorerLayoutManager(this, getViewModel());
        setupViews(adapter, layoutManager);
    }
}
