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
import arc.resource.calculator.ui.explorer.ExplorerItem;
import arc.resource.calculator.ui.explorer.ExplorerItemType;

public class FolderExplorerItem extends ExplorerItem {
    private FolderExplorerItem(int rowid, String title, String image) {
        super(rowid, title, image, ExplorerItemType.Folder);
    }

    public static FolderExplorerItem fromEntity(FolderEntity folderEntity) {
        return new FolderExplorerItem(folderEntity.getRowId(), folderEntity.getTitle(), folderEntity.getImage());
    }

    public static List<FolderExplorerItem> fromEntities(List<FolderEntity> folderEntities) {
        List<FolderExplorerItem> items = new ArrayList<>();
        for (FolderEntity folderEntity : folderEntities) {
            items.add(fromEntity(folderEntity));
        }
        return items;
    }
}
