/*
 * Copyright (c) 2019 Jared Stone
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

package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;

public class CraftableSwitcher extends ViewSwitcher implements CraftableRecyclerView.Listener {
    private static final String TAG = CraftableSwitcher.class.getSimpleName();

    private TextView mTextView;
    private CraftableRecyclerView mRecyclerView;

    @Override
    public void onError(Exception e) {
        if (!isTextViewShown())
            showNext();

        onStatusUpdate("An error occurred while fetching Engram data.");

        ExceptionObserver.getInstance().notifyExceptionCaught(TAG, e);
    }

    @Override
    public void onInit() {
        if (!isTextViewShown())
            showNext();

        onStatusUpdate("Fetching Engram data..");
    }

    @Override
    public void onPopulated() {
        // switch view to recycler
        if (!isRecyclerViewShown())
            showNext();
    }

    @Override
    public void onEmpty() {
        // switch view to textview, display empty message
        if (!isTextViewShown())
            showNext();

        onStatusUpdate("Engram data is empty.");
    }

    public CraftableSwitcher(Context context) {
        super(context);
    }

    public CraftableSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onCreate() {
        // instantiate our textview for status updates
        mTextView = findViewById(R.id.textview_craftables);

        // instantiate recyclerView
        mRecyclerView = findViewById(R.id.gridview_craftables);
        mRecyclerView.create(this);
    }

    public void onResume() {
        mRecyclerView.resume();
    }

    public void onPause() {
        mRecyclerView.pause();
    }

    public void onDestroy() {
        mRecyclerView.destroy();
    }

    public void onSearch(String query) {
        mRecyclerView.search(query);
    }

    private void onStatusUpdate(String text) {
        mTextView.setText(text);
    }

    private boolean isTextViewShown() {
        return getCurrentView().getId() == mTextView.getId();
    }

    private boolean isRecyclerViewShown() {
        return getCurrentView().getId() == mRecyclerView.getId();
    }
}
