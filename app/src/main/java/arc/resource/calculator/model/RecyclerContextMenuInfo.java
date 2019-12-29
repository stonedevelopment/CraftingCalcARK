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

package arc.resource.calculator.model;

import android.view.ContextMenu;
import android.view.View;

public class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {
    final private int mPosition;
    final private long mId;
    final private View mView;

    public RecyclerContextMenuInfo(View view, int position, long id) {
        mView = view;
        mPosition = position;
        mId = id;
    }

    public View getView() {
        return mView;
    }

    public int getPosition() {
        return mPosition;
    }

    public long getId() {
        return mId;
    }
}