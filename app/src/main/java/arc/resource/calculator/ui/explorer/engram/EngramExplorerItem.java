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

import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.ui.explorer.ExplorerItem;
import arc.resource.calculator.ui.explorer.ExplorerItemType;

public class EngramExplorerItem extends ExplorerItem {
    private int quantity;

    private EngramExplorerItem(int rowid, String title, String image, int quantity) {
        super(rowid, title, image, ExplorerItemType.Engram);
        this.quantity = quantity;
    }

    public static EngramExplorerItem fromEntity(EngramEntity engramEntity) {
        return new EngramExplorerItem(engramEntity.getId(), engramEntity.getTitle(), engramEntity.getImage(), 0);
    }

    public static List<EngramExplorerItem> fromEntities(List<EngramEntity> engramEntities) {
        List<EngramExplorerItem> items = new ArrayList<>();
        for (EngramEntity engramEntity : engramEntities) {
            items.add(EngramExplorerItem.fromEntity(engramEntity));
        }
        return items;
    }

    public int getQuantity() {
        return quantity;
    }
}
