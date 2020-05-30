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

package arc.resource.calculator.ui.explorer.model;

public class BackFolderExplorerItem extends ExplorerItem {
    private BackFolderExplorerItem(String uuid, String title, String imageFile, int viewType, String sourceId, String parentId, String gameId) {
        super(uuid, title, imageFile, viewType, sourceId, parentId, gameId);
    }

    public static BackFolderExplorerItem fromExplorerItem(ExplorerItem explorerItem) {
        String imageFile = "back_folder.webp";  // TODO: 5/30/2020 pull folder image data from GameDetails object
        int viewType = -1;  // TODO: 5/30/2020 establish constants for viewTypes
        return new BackFolderExplorerItem(explorerItem.getUuid(), explorerItem.getTitle(),
                imageFile, viewType, explorerItem.getSourceId(), explorerItem.getParentId(),
                explorerItem.getGameId());
    }
}