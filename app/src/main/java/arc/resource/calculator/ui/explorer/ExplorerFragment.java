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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import arc.resource.calculator.DetailActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.RecyclerContextMenuInfo;

import static android.app.Activity.RESULT_OK;
import static arc.resource.calculator.DetailActivity.ADD;
import static arc.resource.calculator.DetailActivity.ERROR;
import static arc.resource.calculator.DetailActivity.REMOVE;
import static arc.resource.calculator.DetailActivity.RESULT_CODE;
import static arc.resource.calculator.DetailActivity.RESULT_EXTRA_NAME;
import static arc.resource.calculator.DetailActivity.UPDATE;

// TODO: 1/26/2020 Extract out ExplorerRepository features, give view model more control

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
                                String.format(getString(R.string.toast_details_removed_format), name));
                        break;

                    case UPDATE:
                        showSnackBar(
                                String.format(getString(R.string.toast_details_updated_format), name));
                        break;

                    case ADD:
                        showSnackBar(String.format(getString(R.string.toast_details_added_format), name));
                        break;
                }
            } else {
                if (extraResultCode == ERROR) {
                    Exception e = (Exception) extras.get(RESULT_EXTRA_NAME);

                    if (e != null) {
                        showSnackBar(getString(R.string.toast_details_error));

                        ExceptionObservable.getInstance().notifyExceptionCaught(TAG, e);
                    }
                } else {
                    showSnackBar(getString(R.string.toast_details_no_change));
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.registerListeners();

        mTextView.onResume();
        mRecyclerView.onResume();
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onPause() {
        mTextView.onPause();
        mRecyclerView.onPause();
        unregisterForContextMenu(mRecyclerView);

        mViewModel.unregisterListeners();

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
        ExceptionObservable.getInstance().notifyExceptionCaught(TAG, e);
    }

    @Override
    public void onLoading() {
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

    private void showSnackBar(String s) {
        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.explorerCoordinatorLayout), s, Snackbar.LENGTH_SHORT).show();
    }
}
