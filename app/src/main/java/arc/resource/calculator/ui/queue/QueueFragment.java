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

package arc.resource.calculator.ui.queue;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import arc.resource.calculator.DetailActivity;
import arc.resource.calculator.MainViewModel;
import arc.resource.calculator.R;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.util.DialogUtil;

public class QueueFragment extends Fragment {
    public static final String TAG = QueueFragment.class.getSimpleName();

    private QueueViewModel mViewModel;

    private TextView mTextView;
    private QueueRecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButtonClear;
    private FloatingActionButton mFloatingActionButtonStart;
    private ContentLoadingProgressBar mProgressBar;

    public static QueueFragment newInstance() {
        return new QueueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.queue_fragment, container, false);

        mTextView = rootView.findViewById(R.id.queueTextView);
        mRecyclerView = rootView.findViewById(R.id.queueSwitcher);
        mProgressBar = rootView.findViewById(R.id.queueProgressBar);

        mFloatingActionButtonStart = rootView.findViewById(R.id.queueFloatingActionButtonStart);
        mFloatingActionButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.startCrafting();
            }
        });

        mFloatingActionButtonClear = rootView.findViewById(R.id.queueFloatingActionButtonClear);
        mFloatingActionButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.requestToClearQueue();   //  TODO:   Show confirmation dialog for clearing the queue?
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();

        mRecyclerView.onCreate();
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.onResume();
        registerForContextMenu(mRecyclerView);
        registerListeners();
    }

    @Override
    public void onPause() {
        mRecyclerView.onPause();
        unregisterForContextMenu(mRecyclerView);
        unregisterListeners();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRecyclerView.onDestroy();

        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.craftable_in_queue_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        RecyclerContextMenuInfo menuInfo = (RecyclerContextMenuInfo) menuItem.getMenuInfo();

        final long engramId = menuInfo.getId();

        switch (menuItem.getItemId()) {
            case R.id.floating_action_remove_from_queue:
                mViewModel.requestToRemoveEngram(engramId);
                break;

            case R.id.floating_action_edit_quantity:
                showEditQuantityDialog(engramId);
                break;
            case R.id.floating_action_view_details:
                MainViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
                viewModel.startActivityForResult(DetailActivity.buildIntentWithId(getActivity(), engramId));
                break;
        }

        return super.onContextItemSelected(menuItem);
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(QueueViewModel.class);
        mViewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackBar(s);
            }
        });
        mViewModel.getViewModelState().observe(this, new Observer<QueueViewModelState>() {
            @Override
            public void onChanged(QueueViewModelState viewModelState) {
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
    }

    private void registerListeners() {
        mViewModel.registerListeners();
    }

    private void unregisterListeners() {
        mViewModel.unregisterListeners();
    }

    private void showLoading() {
        Log.d(TAG, "showLoading: ");

        //  hide views
        mTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mFloatingActionButtonClear.hide();
        mFloatingActionButtonStart.hide();

        //  show views
        mProgressBar.show();
    }

    private void showLoaded() {
        Log.d(TAG, "showLoaded: ");

        //  hide views
        mProgressBar.hide();
        mTextView.setVisibility(View.GONE);

        //  show views
        mRecyclerView.setVisibility(View.VISIBLE);
        mFloatingActionButtonClear.show();
        mFloatingActionButtonStart.show();
    }

    private void showEmpty() {
        Log.d(TAG, "showEmpty: ");

        //  hide views
        mProgressBar.hide();
        mRecyclerView.setVisibility(View.GONE);
        mFloatingActionButtonClear.hide();
        mFloatingActionButtonStart.hide();

        //  show views
        mTextView.setVisibility(View.VISIBLE);
    }

    private void showEditQuantityDialog(final long engramId) {
        DialogUtil.EditQuantity(getActivity(), engramId, new DialogUtil.Callback() {
            @Override
            public void onResult(Object result) {
                int quantity = (int) result;

                mViewModel.requestToUpdateEngramQuantity(engramId, quantity);
            }

            @Override
            public void onCancel(Object obj) {
                QueueEngram engram = (QueueEngram) obj;

                if (engram != null)
                    showSnackBar(String.format(getString(R.string.snackbar_message_edit_quantity_fail_format), engram.getName()));
                else
                    showSnackBar(getString(R.string.snackbar_message_edit_quantity_fail));

            }
        }).show();
    }

    private void showSnackBar(String message) {
        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.queueCoordinatorLayout), message, Snackbar.LENGTH_SHORT).show();
    }
}
