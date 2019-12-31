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
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import arc.resource.calculator.R;

public class InventoryLayout extends LinearLayout {
    InventorySwitcher mSwitcher;
    RawMaterialCheckBox mCheckBox;

    public InventoryLayout(Context context) {
        super(context);
    }

    public InventoryLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InventoryLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InventoryLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void onCreate() {
        mSwitcher = findViewById(R.id.switcher_inventory);
        mSwitcher.onCreate();

        mCheckBox = findViewById(R.id.checkbox_inventory_raw_materials);
        mCheckBox.create();
    }

    public void onResume() {
//        mCheckBox.resume();
        mSwitcher.onResume();
    }

    public void onPause() {
//        mCheckBox.pause();
        mSwitcher.onPause();
    }

    public void onDestroy() {
//        mCheckBox.destroy();
        mSwitcher.onDestroy();
    }

}
