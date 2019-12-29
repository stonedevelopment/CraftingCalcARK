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
import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.Util;

public class RecyclerViewWithContextMenu extends RecyclerView {
    private RecyclerContextMenuInfo mContextMenuInfo;

    public RecyclerViewWithContextMenu(Context context) {
        super(context);
    }

    public RecyclerViewWithContextMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithContextMenu(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        int position = getChildAdapterPosition(originalView);

        if (Util.isValidPosition(position, Objects.requireNonNull(getAdapter()).getItemCount())) {
            mContextMenuInfo = new RecyclerContextMenuInfo(originalView,
                    position, getAdapter().getItemId(position));

            return super.showContextMenuForChild(originalView);
        }

        return false;
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }
}