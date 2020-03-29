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

package arc.resource.calculator.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import arc.resource.calculator.db.dao.FolderDao;
import arc.resource.calculator.db.dao.StationDao;

/**
 * Folder object for base game data (vanilla)
 * <p>
 * Folders for DLC game data should extend from this class
 */
@Entity(tableName = FolderDao.tableName)
public class FolderEntity {
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private final int rowId;

    //  name of folder
    private final String title;

    //  rowid of parent; station or folder
    private final int parentId;

    //  rowid of stations table, crafting station per in-game
    @ColumnInfo(name = StationDao.columnName)
    private final int stationId;

    public FolderEntity(int rowId, String name, int parentId, int stationId) {
        this.rowId = rowId;
        this.title = name;
        this.parentId = parentId;
        this.stationId = stationId;
    }

    public int getRowId() {
        return rowId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return "folder.webp";
    }

    public int getParentId() {
        return parentId;
    }

    public int getStationId() {
        return stationId;
    }
}