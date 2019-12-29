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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.util.PrefsUtil;

public class MainSwitcher extends ViewSwitcher {
    private static final String TAG = MainSwitcher.class.getSimpleName();

    public static final int CRAFTABLE_SCREEN = 0;
    public static final int INVENTORY_SCREEN = 1;
    public static final int DEFAULT_SCREEN = CRAFTABLE_SCREEN;

    private int mCurrentScreenId;

    CraftableLayout mCraftableLayout;
    InventoryLayout mInventoryLayout;

    public MainSwitcher(Context context) {
        super(context);
    }

    public MainSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        mCraftableLayout = findViewById(R.id.layout_craftables);
        mCraftableLayout.onCreate();

        Button viewCraftables = findViewById(R.id.button_view_craftables);
        viewCraftables.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCraftableScreen();
            }
        });

        mInventoryLayout = findViewById(R.id.layout_inventory);
        mInventoryLayout.onCreate();

        Button viewInventory = findViewById(R.id.button_view_inventory);
        viewInventory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showInventoryScreen();
            }
        });

        int screenId = PrefsUtil.getInstance(getContext()).getMainSwitcherScreenId();

        if (screenId == INVENTORY_SCREEN) {
            showInventoryScreen();
        }
    }

    public void onPause() {
        Log.d(TAG, "onPause: ");
        if (isCurrentView(mCraftableLayout.getId())) {
            mCraftableLayout.onPause();
        } else {
            mInventoryLayout.onPause();
        }

        PrefsUtil.getInstance(getContext()).saveMainSwitcherScreenId(getCurrentScreenId());
    }

    public void onResume() {
        Log.d(TAG, "onResume: ");
        if (isCurrentView(mCraftableLayout.getId())) {
            mCraftableLayout.onResume();
        } else {
            mInventoryLayout.onResume();
        }
    }

    public void onDestroy() {
        mCraftableLayout.onDestroy();
        mInventoryLayout.onDestroy();
    }

    public void onSearch(String searchQuery) {
        // toggle craftable recyclerView back if not current view, then execute search
        showCraftableScreen();

        mCraftableLayout.onSearch(searchQuery);
    }

    private boolean isCurrentView(int id) {
        return getCurrentView().getId() == id;
    }

    public void showInventoryScreen() {
        // toggle view to inventory
        if (!isCurrentView(mInventoryLayout.getId())) {
            showNext();

            // onPause craftable view
            mCraftableLayout.onPause();

            // onResume inventory view
            mInventoryLayout.onResume();
        }
    }

    public void showCraftableScreen() {
        // toggle view to craftables
        if (!isCurrentView(mCraftableLayout.getId())) {
            showNext();

            // onPause inventory view
            mInventoryLayout.onPause();

            // onResume craftable view
            mCraftableLayout.onResume();
        }
    }

    private int getCurrentScreenId() {
        return isCurrentView(mCraftableLayout.getId()) ? CRAFTABLE_SCREEN : INVENTORY_SCREEN;
    }
}
