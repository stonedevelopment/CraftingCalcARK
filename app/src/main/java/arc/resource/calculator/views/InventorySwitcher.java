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

import arc.resource.calculator.listeners.ExceptionObservable;

public class InventorySwitcher extends ViewSwitcher implements InventoryRecyclerView.Listener {
    private static final String TAG = InventorySwitcher.class.getSimpleName();

    private TextView mTextView;
    private InventoryRecyclerView mRecyclerView;

    @Override
    public void onError(Exception e) {
        if (!isTextViewShown())
            showNext();

        onStatusUpdate("An error occurred while fetching Inventory.");

        ExceptionObservable.getInstance().notifyExceptionCaught(TAG, e);
    }

    @Override
    public void onInit() {
        if (!isTextViewShown())
            showNext();

        onStatusUpdate("Fetching inventory..");
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

        onStatusUpdate("Inventory is empty.");
    }

    public InventorySwitcher(Context context) {
        super(context);
    }

    public InventorySwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onCreate() {
//        // instantiate our textview for status updates
//        mTextView = findViewById(R.id.textview_inventory);
//
//        // instantiate recyclerView
//        mRecyclerView = findViewById(R.id.gridview_inventory);
//        mRecyclerView.create(this);
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
