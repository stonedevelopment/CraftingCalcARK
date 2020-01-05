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

package arc.resource.calculator;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.views.ExplorerNavigationTextView;
import arc.resource.calculator.views.ExplorerRecyclerView;

//  TODO:   Data states are not stable

public class ExplorerFragment extends Fragment implements ExplorerRecyclerView.Listener {
    public static final String TAG = ExplorerFragment.class.getSimpleName();

    private ExplorerViewModel mViewModel;

    private ExplorerRecyclerView mRecyclerView;
    private ExplorerNavigationTextView mTextView;
    private ContentLoadingProgressBar mProgressBar;

    public static ExplorerFragment newInstance() {
        return new ExplorerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.explorer_fragment, container, false);

        mRecyclerView = rootView.findViewById(R.id.explorerRecyclerView);
        mTextView = rootView.findViewById(R.id.explorerNavigationTextView);
        mProgressBar = rootView.findViewById(R.id.explorerProgressBar);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExplorerViewModel.class);
        // TODO: Use the ViewModel

        mProgressBar.hide();
        mRecyclerView.onCreate(this);
        mTextView.onCreate();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.displaycase_menu, menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        mTextView.onResume();
        mRecyclerView.onResume();
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onPause() {
        mTextView.onPause();
        mRecyclerView.onPause();
        unregisterForContextMenu(mRecyclerView);

        super.onPause();
    }

    @Override
    public void onDestroy() {
        mTextView.onDestroy();
        mRecyclerView.onDestroy();

        super.onDestroy();
    }

    @Override
    public void onError(Exception e) {
        mProgressBar.hide();
        ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);
    }

    @Override
    public void onInit() {
        mProgressBar.show();
    }

    @Override
    public void onPopulated() {
        mProgressBar.hide();
    }

    @Override
    public void onEmpty() {
        mProgressBar.hide();
    }
}
