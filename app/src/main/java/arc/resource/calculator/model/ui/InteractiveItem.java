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

package arc.resource.calculator.model.ui;

public class InteractiveItem {
    private final String uuid;
    private final String title;
    private final String imageFile;
    private final int viewType;
    private final String sourceId;
    private final String parentId;
    private final String gameId;

    protected InteractiveItem(String uuid, String title, String imageFile, int viewType, String sourceId, String parentId, String gameId) {
        this.uuid = uuid;
        this.title = title;
        this.imageFile = imageFile;
        this.viewType = viewType;
        this.sourceId = sourceId;
        this.parentId = parentId;
        this.gameId = gameId;
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

    @Override
    public String toString() {
        return "InteractiveItem{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", viewType=" + viewType +
                ", sourceId='" + sourceId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", gameId='" + gameId + '\'' +
                '}';
    }
}