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

package arc.resource.calculator.ui.explorer.station;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.StationEntity;
import arc.resource.calculator.ui.explorer.ExplorerItem;

public class StationExplorerItem extends ExplorerItem {

    private StationExplorerItem(int rowid, String title, String image) {
        super(rowid, title, image);
    }

    private static StationExplorerItem fromEntity(StationEntity stationEntity) {
        return new StationExplorerItem(stationEntity.getId(), stationEntity.getName(), stationEntity.getImage());
    }

    static List<StationExplorerItem> fromEntities(List<StationEntity> stationEntities) {
        List<StationExplorerItem> items = new ArrayList<>();
        for (StationEntity stationEntity : stationEntities) {
            items.add(fromEntity(stationEntity));
        }
        return items;
    }
}
