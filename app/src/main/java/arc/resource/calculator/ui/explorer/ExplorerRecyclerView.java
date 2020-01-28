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

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import arc.resource.calculator.R;
import arc.resource.calculator.views.RecyclerViewWithContextMenu;

public class ExplorerRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = ExplorerRecyclerView.class.getSimpleName();

    private ExplorerAdapter mAdapter;

    public ExplorerRecyclerView(Context context) {
        super(context);
    }

    public ExplorerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExplorerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onCreate() {
        setupLayout();
        setupAdapter();
    }

    private void setupLayout() {
        int numCols = getResources().getInteger(R.integer.gridview_column_count);
        setLayoutManager(new GridLayoutManager(getContext(), numCols));
    }

    private void setupAdapter() {
        mAdapter = new ExplorerAdapter(getContext());
        setAdapter(mAdapter);
    }

    public void onResume() {
        getAdapter().resume(getContext());
    }

    public void onPause() {
        getAdapter().pause();
    }

    @NonNull
    @Override
    public ExplorerAdapter getAdapter() {
        if (mAdapter == null) setupAdapter();
        return mAdapter;
    }
}