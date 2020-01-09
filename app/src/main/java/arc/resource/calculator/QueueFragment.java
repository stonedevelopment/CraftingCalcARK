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

import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.views.QueueRecyclerView;

//  TODO:   Data states are not stable

public class QueueFragment extends Fragment implements QueueRecyclerView.Listener, QueueObserver.Listener {
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
                showSnackBar("Start Crafting!");    //  TODO:   String resource for hard-coded string "Start Crafting!"
            }
        });

        mFloatingActionButtonClear = rootView.findViewById(R.id.queueFloatingActionButtonClear);
        mFloatingActionButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  TODO:   Create request system for CraftingQueue, instead of communicating directly with it
                CraftingQueue.getInstance().clearQueue();   //  TODO:   Show confirmation dialog for clearing the queue?

                //  Lazy display a message to user
                showSnackBar("Crafting Queue has been cleared.");
            }
        });

        showEmpty();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();

        mRecyclerView.onCreate(this);
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
    public boolean onContextItemSelected(MenuItem item) {
        RecyclerContextMenuInfo menuInfo = (RecyclerContextMenuInfo) item.getMenuInfo();

        final long id = menuInfo.getId();

        final String name = CraftingQueue.getInstance().getCraftable(id).getName();

        switch (item.getItemId()) {
            case R.id.floating_action_remove_from_queue:
                CraftingQueue.getInstance().delete(id);

                showSnackBar(
                        String.format(getString(R.string.toast_remove_from_queue_success_format), name));
                break;

            case R.id.floating_action_edit_quantity:
                DialogUtil.EditQuantity(getActivity(), name, new DialogUtil.Callback() {
                    @Override
                    public void onResult(Object result) {
                        int quantity = (int) result;
                        CraftingQueue.getInstance().setQuantity(Objects.requireNonNull(getActivity()).getApplicationContext(), id, quantity);

                        showSnackBar(
                                String.format(getString(R.string.toast_edit_quantity_success_format), name));
                    }

                    @Override
                    public void onCancel() {
                        showSnackBar(String.format(getString(R.string.toast_edit_quantity_fail_format), name));
                    }
                }).show();
                break;
            case R.id.floating_action_view_details:
                MainViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
                viewModel.startActivityForResult(DetailActivity.buildIntentWithId(getActivity(), id));
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onError(Exception e) {
        ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);
    }

    @Override
    public void onInit() {
        showLoading();
    }

    @Override
    public void onPopulated() {
        showLoaded();
    }

    @Override
    public void onEmpty() {
        showEmpty();
    }

    @Override
    public void onItemChanged(long craftableId, int quantity) {
        //  intentionally left blank
    }

    @Override
    public void onItemRemoved(long craftableId) {
        // intentionally left blank
    }

    @Override
    public void onDataSetPopulated() {
        showLoaded();
    }

    @Override
    public void onDataSetEmpty() {
        showEmpty();
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(QueueViewModel.class);
        mViewModel.getSnackBar().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackBar(s);
            }
        });
    }

    private void registerListeners() {
        QueueObserver.getInstance().registerListener(TAG, this);
    }

    private void unregisterListeners() {
        QueueObserver.getInstance().unregisterListener(TAG);
    }

    private void showLoading() {
        mProgressBar.show();
    }

    private void showLoaded() {
        Log.d(TAG, "showLoaded: ");
        mFloatingActionButtonClear.show();
        mFloatingActionButtonStart.show();
        mProgressBar.hide();
        mTextView.setVisibility(View.GONE);
    }

    private void showEmpty() {
        Log.d(TAG, "showEmpty: ");
        mFloatingActionButtonClear.hide();
        mFloatingActionButtonStart.hide();
        mProgressBar.hide();
        mTextView.setVisibility(View.VISIBLE);
    }

    private void showSnackBar(String s) {
        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.queueCoordinatorLayout), s, Snackbar.LENGTH_SHORT).show();
    }
}
