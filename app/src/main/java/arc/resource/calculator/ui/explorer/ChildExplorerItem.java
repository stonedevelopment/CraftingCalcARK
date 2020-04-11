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

import androidx.annotation.Nullable;

public class ChildExplorerItem extends ExplorerItem {
    private final int mStationId;
    private final int mParentId;

    public ChildExplorerItem(int rowId, String title, String image, int stationId, int parentId) {
        super(rowId, title, image);
        mStationId = stationId;
        mParentId = parentId;
    }

    public int getStationId() {
        return mStationId;
    }

    public int getParentId() {
        return mParentId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChildExplorerItem)) return false;

        ChildExplorerItem explorerItem = (ChildExplorerItem) obj;
        return getId() == explorerItem.getId() &&
                getTitle().equals(explorerItem.getTitle()) &&
                getImage().equals(explorerItem.getImage()) &&
                getStationId() == explorerItem.getStationId() &&
                getParentId() == explorerItem.getParentId();
    }
}