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

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;

public class ExplorerItem {
    private final int rowid;
    private final String title;
    private final String image;
    private final ExplorerItemType itemType;
    private int quantity;

    private ExplorerItem(int rowid, String title, String image, ExplorerItemType itemType) {
        this.rowid = rowid;
        this.title = title;
        this.image = image;
        this.itemType = itemType;
        this.quantity = 0;
    }

    private ExplorerItem(int rowid, String title, String image, ExplorerItemType itemType, int quantity) {
        this.rowid = rowid;
        this.title = title;
        this.image = image;
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public static ExplorerItem fromStation(StationEntity stationEntity) {
        return new ExplorerItem(stationEntity.getRowId(), stationEntity.getName(), stationEntity.getImage(), ExplorerItemType.CraftingStation);
    }

    public static List<ExplorerItem> fromStations(List<StationEntity> stationEntities) {
        List<ExplorerItem> items = new ArrayList<>();
        for (StationEntity stationEntity : stationEntities) {
            items.add(ExplorerItem.fromStation(stationEntity));
        }
        return items;
    }

    static ExplorerItem fromFolder(FolderEntity folderEntity) {
        return new ExplorerItem(folderEntity.getRowId(), folderEntity.getTitle(), folderEntity.getImage(), ExplorerItemType.Folder);
    }

    public static List<ExplorerItem> fromFolders(List<FolderEntity> folderEntities) {
        List<ExplorerItem> items = new ArrayList<>();
        for (FolderEntity folderEntity : folderEntities) {
            items.add(ExplorerItem.fromFolder(folderEntity));
        }
        return items;
    }

    public static ExplorerItem backFolder(ExplorerItem explorerItem) {
        return new ExplorerItem(explorerItem.getId(), String.format("Back to %s", explorerItem.getTitle()), "back_folder.webp", ExplorerItemType.Folder);
    }

    static ExplorerItem fromEngram(EngramEntity engramEntity) {
        return new ExplorerItem(engramEntity.getRowId(), engramEntity.getTitle(), engramEntity.getImage(), ExplorerItemType.Engram, 0);
    }

    public static List<ExplorerItem> fromEngrams(List<EngramEntity> engramEntities) {
        List<ExplorerItem> items = new ArrayList<>();
        for (EngramEntity engramEntity : engramEntities) {
            items.add(ExplorerItem.fromEngram(engramEntity));
        }
        return items;
    }

    public int getId() {
        return rowid;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public ExplorerItemType getItemType() {
        return itemType;
    }

    public Integer getQuantity() {
        return quantity;
    }
}