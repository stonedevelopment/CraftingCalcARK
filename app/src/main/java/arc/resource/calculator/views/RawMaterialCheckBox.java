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
import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;

import arc.resource.calculator.util.PrefsUtil;

public class RawMaterialCheckBox extends AppCompatCheckBox {
    private static final String TAG = RawMaterialCheckBox.class.getSimpleName();

    public RawMaterialCheckBox(Context context) {
        super(context);
    }

    public RawMaterialCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RawMaterialCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void create() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefsUtil.getInstance(getContext()).saveRefinedFilterPreference(isChecked());
            }
        });

        setChecked(PrefsUtil.getInstance(getContext()).getRefinedFilterPreference());
    }

    public void resume() {
    }

    public void pause() {
    }

    public void destroy() {
    }

}
