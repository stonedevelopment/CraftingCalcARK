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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import arc.resource.calculator.DetailActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.ui.detail.DetailFragment;

import static android.app.Activity.RESULT_OK;
import static arc.resource.calculator.DetailActivity.ADD;
import static arc.resource.calculator.DetailActivity.ERROR;
import static arc.resource.calculator.DetailActivity.REMOVE;
import static arc.resource.calculator.DetailActivity.RESULT_CODE;
import static arc.resource.calculator.DetailActivity.RESULT_EXTRA_NAME;
import static arc.resource.calculator.DetailActivity.UPDATE;

// TODO: 1/26/2020 Extract out ExplorerRepository features, give view model more control

public class ExplorerFragment extends Fragment implements ExceptionObservable.Observer {
    public static final String TAG = ExplorerFragment.class.getSimpleName();

    private static ExplorerFragment sInstance;

    private ExplorerViewModel mViewModel;

    private ExplorerRecyclerView mRecyclerView;
    private ExplorerNavigationTextView mTextView;
    private ContentLoadingProgressBar mProgressBar;

    private ExceptionObservable mExceptionObservable;

    public static ExplorerFragment getInstance() {
        if (sInstance == null) sInstance = newInstance();

        return sInstance;
    }

    private static ExplorerFragment newInstance() {
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

        setupViewModel();
        mRecyclerView.onCreate(mViewModel);
        mTextView.onCreate();
        mExceptionObservable = ExceptionObservable.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();

        registerListeners();

        mRecyclerView.onResume();
        mTextView.onResume();
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onPause() {
        mRecyclerView.onPause();
        mTextView.onPause();
        unregisterForContextMenu(mRecyclerView);

        unregisterListeners();

        super.onPause();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.displaycase_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        RecyclerContextMenuInfo menuInfo = (RecyclerContextMenuInfo) item.getMenuInfo();

        final long id = menuInfo.getId();

        if (item.getItemId() == R.id.floating_action_view_details) {
            //  TODO:   Engram detail screen should be popup window?
            startActivityForResult(DetailActivity.buildIntentWithId(getActivity(), id), DetailActivity.REQUEST_CODE);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = Objects.requireNonNull(data).getExtras();

        if (requestCode == DetailActivity.REQUEST_CODE) {
            assert extras != null;
            int extraResultCode = extras.getInt(RESULT_CODE);

            if (resultCode == RESULT_OK) {
                String name = extras.getString(RESULT_EXTRA_NAME);

                switch (extraResultCode) {
                    case REMOVE:
                        showSnackBar(
                                String.format(getString(R.string.snackbar_message_item_removed_success_format), name));
                        break;

                    case UPDATE:
                        showSnackBar(
                                String.format(getString(R.string.snackbar_message_item_updated_success_format), name));
                        break;

                    case ADD:
                        showSnackBar(String.format(getString(R.string.snackbar_message_item_added_success_format), name));
                        break;
                }
            } else {
                if (extraResultCode == ERROR) {
                    Exception e = (Exception) extras.get(RESULT_EXTRA_NAME);

                    if (e != null) {
                        showSnackBar(getString(R.string.snackbar_message_details_error));

                        ExceptionObservable.getInstance().notifyExceptionCaught(TAG, e);
                    }
                } else {
                    showSnackBar(getString(R.string.snackbar_message_details_no_change));
                }
            }
        }
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ExplorerViewModel.class);
        mViewModel.getViewModelState().observe(this, new Observer<ExplorerViewModelState>() {
            @Override
            public void onChanged(ExplorerViewModelState viewModelState) {
                switch (viewModelState) {
                    case POPULATING:
                        showLoading();
                        break;
                    case POPULATED:
                        showLoaded();
                        break;
                    case EMPTY:
                        showEmpty();
                        break;
                }
            }
        });
        mViewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged: getSnackBarMessage");
                showSnackBar(s);
            }
        });
        mViewModel.getShowDialogFragment().observe(this, new Observer<DisplayEngram>() {
            @Override
            public void onChanged(DisplayEngram position) {
                showDialogFragment(DetailFragment.newInstance(position));
            }
        });
    }

    private void setupRecyclerView() {
        int numCols = getResources().getInteger(R.integer.gridview_column_count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numCols));
        mRecyclerView.setAdapter(new ExplorerAdapter(getContext(), mViewModel));
    }

    private void registerListeners() {
        mExceptionObservable.registerObserver(this);
    }

    private void unregisterListeners() {
        mExceptionObservable.unregisterObserver();
    }

    private void showLoading() {
        mProgressBar.show();
    }

    private void showLoaded() {
        mProgressBar.hide();
    }

    private void showEmpty() {
        mProgressBar.hide();    // TODO: 1/27/2020 what do we do with an empty explorer data set?
    }

    private void showSnackBar(String s) {
        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.explorerCoordinatorLayout), s, Snackbar.LENGTH_SHORT).show();
    }

    private void showDialogFragment(DetailFragment fragment) {
        fragment.show(getChildFragmentManager(), DetailFragment.TAG);
    }

    @Override
    public void onException(String tag, Exception e) {
        // TODO: 1/25/2020 handle exception
        mViewModel.showSnackBarMessage("An error occurred.");
    }

    @Override
    public void onFatalException(String tag, Exception e) {
        // TODO: 1/25/2020 handle fatal exception
        mViewModel.showSnackBarMessage("A fatal error occurred.");
    }
}
