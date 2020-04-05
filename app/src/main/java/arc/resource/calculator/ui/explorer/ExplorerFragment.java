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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import arc.resource.calculator.DetailActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.ui.detail.DetailFragment;
import arc.resource.calculator.ui.explorer.engram.EngramExplorerItemAdapter;
import arc.resource.calculator.ui.explorer.engram.EngramExplorerItemCallback;
import arc.resource.calculator.ui.explorer.folder.FolderExplorerItemAdapter;
import arc.resource.calculator.ui.explorer.folder.FolderExplorerItemCallback;
import arc.resource.calculator.ui.explorer.station.StationExplorerItemAdapter;
import arc.resource.calculator.ui.explorer.station.StationExplorerItemCallback;

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

    private ExplorerViewModel mViewModel;

    private StationExplorerItemAdapter mStationAdapter;
    private FolderExplorerItemAdapter mFolderAdapter;
    private EngramExplorerItemAdapter mEngramAdapter;

    private ExplorerNavigationTextView mTextView;
    private ContentLoadingProgressBar mProgressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.explorer_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.explorerRecyclerView);

        //  setup layout manager
        int numCols = getResources().getInteger(R.integer.gridview_column_count);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numCols));

        //  setup view adapter
        mStationAdapter = new StationExplorerItemAdapter(getContext(), new StationExplorerItemCallback());
        mFolderAdapter = new FolderExplorerItemAdapter(getContext(), new FolderExplorerItemCallback());
        mEngramAdapter = new EngramExplorerItemAdapter(getContext(), new EngramExplorerItemCallback());

        MergeAdapter adapter = new MergeAdapter(mStationAdapter, mFolderAdapter, mEngramAdapter);
        recyclerView.setAdapter(adapter);

        mTextView = rootView.findViewById(R.id.explorerNavigationTextView);
        mProgressBar = rootView.findViewById(R.id.explorerProgressBar);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = Objects.requireNonNull(data).getExtras();


        // TODO: 3/28/2020 connect with new DetailFragment
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
        mViewModel = new ViewModelProvider(this).get(ExplorerViewModel.class);
        mViewModel.getViewModelState().observe(getViewLifecycleOwner(), viewModelState -> {
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
        });

        mViewModel.getStations().observe(getViewLifecycleOwner(), stationEntities -> mStationAdapter.submitList(stationEntities));
        mViewModel.getFolders().observe(getViewLifecycleOwner(), folderEntities -> mFolderAdapter.submitList(folderEntities));
        mViewModel.getEngrams().observe(getViewLifecycleOwner(), engramEntities -> mEngramAdapter.submitList(engramEntities));
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
