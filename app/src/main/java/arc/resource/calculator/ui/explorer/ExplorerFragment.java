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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

import arc.resource.calculator.DetailActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.ui.detail.DetailFragment;
import arc.resource.calculator.ui.main.MainViewModel;

import static android.app.Activity.RESULT_OK;
import static arc.resource.calculator.DetailActivity.ADD;
import static arc.resource.calculator.DetailActivity.ERROR;
import static arc.resource.calculator.DetailActivity.REMOVE;
import static arc.resource.calculator.DetailActivity.RESULT_CODE;
import static arc.resource.calculator.DetailActivity.RESULT_EXTRA_NAME;
import static arc.resource.calculator.DetailActivity.UPDATE;

public class ExplorerFragment extends Fragment implements ExceptionObservable.Observer {
    public static final String TAG = ExplorerFragment.class.getSimpleName();

    private ExplorerViewModel viewModel;
    private ExplorerItemAdapter adapter;

    private GameEntity gameEntity;

    private RecyclerView recyclerView;
    private MaterialTextView textView;
    private ContentLoadingProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.explorer_fragment, container, false);

        setupViews(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();
    }

    private void setupAdapter(GameEntity gameEntity) {
        adapter = new ExplorerItemAdapter(this, gameEntity.getFilePath());
        recyclerView.setAdapter(adapter);
    }

    private void setupViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.explorerRecyclerView);
        int numCols = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numCols));

        textView = rootView.findViewById(R.id.explorerNavigationTextView);
        progressBar = rootView.findViewById(R.id.explorerProgressBar);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ExplorerViewModel.class);

        viewModel.getSnackBarMessageEvent().observe(getViewLifecycleOwner(), this::showSnackBar);
        viewModel.getIsLoadingEvent().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) showLoading();
            else showLoaded();
        });

        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.getGameEntityEvent().observe(getViewLifecycleOwner(), gameEntity -> {
            Log.d(TAG, "gameEntity found: " + gameEntity.getName());
            setupAdapter(gameEntity);
            viewModel.getDirectorySnapshot().observe(getViewLifecycleOwner(), snapshot -> adapter.mapDirectorySnapshot(snapshot));
            viewModel.start();
        });
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
                        viewModel.setSnackBarMessage(String.format(getString(R.string.snackbar_message_item_removed_success_format), name));
                        break;
                    case UPDATE:
                        viewModel.setSnackBarMessage(String.format(getString(R.string.snackbar_message_item_updated_success_format), name));
                        break;

                    case ADD:
                        viewModel.setSnackBarMessage(String.format(getString(R.string.snackbar_message_item_added_success_format), name));
                        break;
                }
            } else {
                if (extraResultCode == ERROR) {
                    Exception e = (Exception) extras.get(RESULT_EXTRA_NAME);

                    if (e != null) {
                        viewModel.setSnackBarMessage(getString(R.string.snackbar_message_details_error));

                        ExceptionObservable.getInstance().notifyExceptionCaught(TAG, e);
                    }
                } else {
                    viewModel.setSnackBarMessage(getString(R.string.snackbar_message_details_no_change));
                }
            }
        }
    }

    private void showLoading() {
        progressBar.show();
    }

    private void showLoaded() {
        progressBar.hide();
    }

    private void showEmpty() {
        progressBar.hide();    // TODO: 1/27/2020 what do we do with an empty explorer data set?
    }

    private void showSnackBar(String message) {
        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.explorerCoordinatorLayout), message, Snackbar.LENGTH_SHORT).show();
    }

    private void showDialogFragment(DetailFragment fragment) {
        fragment.show(getChildFragmentManager(), DetailFragment.TAG);
    }

    @Override
    public void onException(String tag, Exception e) {
        // TODO: 1/25/2020 handle exception
//        mViewModel.showSnackBarMessage("An error occurred.");
    }

    @Override
    public void onFatalException(String tag, Exception e) {
        // TODO: 1/25/2020 handle fatal exception
//        mViewModel.showSnackBarMessage("A fatal error occurred.");
    }
}
