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

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import arc.resource.calculator.R;
import arc.resource.calculator.views.RecyclerViewWithContextMenu;

public class QueueRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = QueueRecyclerView.class.getSimpleName();

    private QueueAdapter mAdapter;

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            scrollToPosition(1);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            scrollToPosition(positionStart);
        }
    };

    public QueueRecyclerView(Context context) {
        super(context);
    }

    public QueueRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QueueRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onCreate() {
        setupLayout();
        setupAdapter();
    }

    public void onResume() {
        getAdapter().registerAdapterDataObserver(mDataObserver);
        getAdapter().resume();
    }

    public void onPause() {
        getAdapter().unregisterAdapterDataObserver(mDataObserver);
        getAdapter().pause();
    }

    public void onDestroy() {
        getAdapter().destroy();
    }

    void setupLayout() {
        int numCols = getResources().getInteger(R.integer.gridview_column_count);
        setLayoutManager(new GridLayoutManager(getContext(), numCols));
    }

    void setupAdapter() {
        mAdapter = new QueueAdapter(getContext());
        setAdapter(mAdapter);
    }

    @NonNull
    @Override
    public QueueAdapter getAdapter() {
        if (mAdapter == null) setupAdapter();
        return mAdapter;
    }
}