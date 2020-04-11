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

package arc.resource.calculator.ui.explorer.folder;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.ui.explorer.ChildExplorerItem;

public class FolderExplorerItem extends ChildExplorerItem {

    private FolderExplorerItem(int rowId, String title, String image, int stationId, int parentId) {
        super(rowId, title, image, stationId, parentId);
    }

    private static FolderExplorerItem fromEntity(FolderEntity folder) {
        return new FolderExplorerItem(folder.getId(), folder.getName(),
                folder.getImage(), folder.getStationId(), folder.getParentId());
    }

    static List<FolderExplorerItem> fromEntities(List<FolderEntity> folders) {
        List<FolderExplorerItem> items = new ArrayList<>();
        for (FolderEntity folderEntity : folders) {
            items.add(fromEntity(folderEntity));
        }
        return items;
    }
}
