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

package arc.resource.calculator.ui.explorer.engram;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.ui.explorer.ChildExplorerItem;

public class EngramExplorerItem extends ChildExplorerItem {
    private int mQuantity;

    private EngramExplorerItem(int rowId, String title, String image, int stationId, int parentId, int quantity) {
        super(rowId, title, image, stationId, parentId);
        mQuantity = quantity;
    }

    private static EngramExplorerItem fromEntity(EngramEntity engramEntity) {
        return new EngramExplorerItem(engramEntity.getRowId(), engramEntity.getName(),
                engramEntity.getImage(), engramEntity.getStationId(), engramEntity.getParentId(), 0);
    }

    static List<EngramExplorerItem> fromEntities(List<EngramEntity> engramEntities) {
        List<EngramExplorerItem> items = new ArrayList<>();
        for (EngramEntity engramEntity : engramEntities) {
            items.add(EngramExplorerItem.fromEntity(engramEntity));
        }
        return items;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }
}