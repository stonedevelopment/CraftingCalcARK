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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.views.QueueRecyclerView;

public class QueueFragment extends Fragment implements QueueRecyclerView.Listener {
    public static final String TAG = QueueFragment.class.getSimpleName();

    private QueueViewModel mViewModel;

    private QueueRecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButtonStart;
    private FloatingActionButton mFloatingActionButtonClear;
    private ContentLoadingProgressBar mProgressBar;

    public static QueueFragment newInstance() {
        return new QueueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.queue_fragment, container, false);

        mRecyclerView = rootView.findViewById(R.id.queueSwitcher);
        mProgressBar = rootView.findViewById(R.id.queueProgressBar);

        mFloatingActionButtonStart = rootView.findViewById(R.id.queueFloatingActionButtonStart);
        mFloatingActionButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setSnackBarMessage("Start crafting!");
            }
        });

        mFloatingActionButtonClear = rootView.findViewById(R.id.queueFloatingActionButtonClear);
        mFloatingActionButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CraftingQueue.getInstance().clearQueue();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(QueueViewModel.class);
        mViewModel.getSnackBar().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackBar(s);
            }
        });

        mRecyclerView.onCreate(this);
        mProgressBar.hide();
    }

    private void showSnackBar(String s) {
        Log.d(TAG, "showSnackBar: " + s);
        Snackbar.make(getActivity().findViewById(R.id.queueCoordinatorLayout), s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.onResume();
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onPause() {
        mRecyclerView.onPause();
        unregisterForContextMenu(mRecyclerView);

        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRecyclerView.onDestroy();

        super.onDestroy();
    }

    @Override
    public void onError(Exception e) {
        ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);
    }

    @Override
    public void onInit() {
        mProgressBar.show();
    }

    @Override
    public void onPopulated() {

    }

    @Override
    public void onEmpty() {

    }
}
