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

package arc.resource.calculator.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import arc.resource.calculator.listeners.QueueObserver;

public class CraftingQueueLayout extends LinearLayout {
    public static final String TAG = CraftingQueueLayout.class.getSimpleName();

    QueueSwitcher mQueueSwitcher;
    ClearQueueButton mClearQueueButton;

    public CraftingQueueLayout(Context context) {
        super(context);
    }

    public CraftingQueueLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CraftingQueueLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CraftingQueueLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void onCreate() {
//        mQueueSwitcher = findViewById(R.id.switcher_queue);
//        mQueueSwitcher.onCreate();
//
//        mClearQueueButton = findViewById(R.id.button_clear_queue);
//        mClearQueueButton.onCreate();

        QueueObserver.getInstance().registerListener(TAG, new QueueObserver.Listener() {
            @Override
            public void onItemChanged(long craftableId, int quantity) {
                if (isNotVisible())
                    setVisibility(VISIBLE);
            }

            @Override
            public void onDataSetPopulated() {
                if (isNotVisible())
                    setVisibility(VISIBLE);
            }

            @Override
            public void onDataSetEmpty() {
                setVisibility(GONE);
            }
        });

    }

    public void onResume() {
        mClearQueueButton.onResume();
        mQueueSwitcher.onResume();
    }

    public void onPause() {
        mClearQueueButton.onPause();
        mQueueSwitcher.onPause();
    }

    public void onDestroy() {
        mClearQueueButton.onDestroy();
        mQueueSwitcher.onDestroy();
    }

    private boolean isNotVisible() {
        return getVisibility() == GONE;
    }
}
