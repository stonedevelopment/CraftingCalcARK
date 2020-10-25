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

import androidx.annotation.Nullable;

import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;

public class ExplorerItem {
    private final String uuid;
    private final String title;
    private final String imageFile;
    private final int viewType;
    private final String sourceId;
    private final String parentId;
    private final String gameId;

    ExplorerItem(String uuid, String title, String imageFile, int viewType, String sourceId, String parentId, String gameId) {
        this.uuid = uuid;
        this.title = title;
        this.imageFile = imageFile;
        this.viewType = viewType;
        this.sourceId = sourceId;
        this.parentId = parentId;
        this.gameId = gameId;
    }

    public static ExplorerItem fromDirectoryEntity(DirectoryItemEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        String imageFile = entity.getImageFile();
        int viewType = entity.getViewType();
        String sourceId = entity.getSourceId();
        String parentId = entity.getParentId();
        String gameId = entity.getGameId();
        return new ExplorerItem(uuid, title, imageFile, viewType, sourceId, parentId, gameId);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ExplorerItem)) return false;

        ExplorerItem explorerItem = (ExplorerItem) obj;
        return uuid.equals(explorerItem.getUuid()) &&
                title.equals(explorerItem.getTitle()) &&
                imageFile.equals(explorerItem.getImageFile()) &&
                viewType == explorerItem.getViewType() &&
                sourceId.equals(explorerItem.getSourceId()) &&
                parentId.equals(explorerItem.getParentId()) &&
                gameId.equals(explorerItem.getGameId());
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getParentId() {
        return parentId;
    }

    public int getViewType() {
        return viewType;
    }

    public String getGameId() {
        return gameId;
    }
}