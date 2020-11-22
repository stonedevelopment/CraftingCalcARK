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

import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.model.ui.InteractiveItem;

public class ExplorerItem extends InteractiveItem {
    private final String parentId;
    private final String sourceId;

    ExplorerItem(String uuid, String title, int viewType, String sourceId, String parentId, String gameId) {
        super(uuid, title, viewType, gameId);
        this.parentId = parentId;
        this.sourceId = sourceId;
    }

    public static ExplorerItem fromDirectoryEntity(DirectoryItemEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        int viewType = entity.getViewType();
        String sourceId = entity.getSourceId();
        String parentId = entity.getParentId();
        String gameId = entity.getGameId();
        return new ExplorerItem(uuid, title, viewType, sourceId, parentId, gameId);
    }

    public String getParentId() {
        return parentId;
    }

    public String getSourceId() {
        return sourceId;
    }
}