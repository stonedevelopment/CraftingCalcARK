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

import javax.annotation.Nullable;

import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;

enum ExplorerItemType {CraftingStation, Folder, Engram}

public class ExplorerItem {
    private final long id;
    private final String title;
    private final String imagePath;
    private final ExplorerItemType itemType;
    private Integer quantity;

    ExplorerItem(long id, String title, String imagePath, ExplorerItemType itemType, @Nullable Integer quantity) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public static ExplorerItem fromStation(StationEntity stationEntity) {
        return new ExplorerItem(stationEntity.getRowId(), stationEntity.getName(), stationEntity.getImage(), ExplorerItemType.CraftingStation, null);
    }

    public static ExplorerItem fromFolder(FolderEntity folderEntity) {
        return new ExplorerItem(folderEntity.getRowId(), folderEntity.getName(), folderEntity.getImage(), ExplorerItemType.Folder, null);
    }

    public static ExplorerItem fromEngram(EngramEntity engramEntity) {
        return new ExplorerItem(engramEntity.getRowId(), engramEntity.getName(), engramEntity.getImage(), ExplorerItemType.Engram, 0);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ExplorerItemType getItemType() {
        return itemType;
    }

    public Integer getQuantity() {
        return quantity;
    }
}