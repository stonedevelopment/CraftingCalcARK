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

import androidx.annotation.Nullable;
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
    private final int mId;

    //  name of folder
    @ColumnInfo(name = "name")
    private final String mName;

    //  rowid of parent; station or folder
    @ColumnInfo(name = "parentid")
    private final int mParentId;

    //  rowid of stations table, crafting station per in-game
    @ColumnInfo(name = StationDao.columnName)
    private final int mStationId;

    public FolderEntity(int rowId, String name, int parentId, int stationId) {
        mId = rowId;
        mName = name;
        mParentId = parentId;
        mStationId = stationId;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getParentId() {
        return mParentId;
    }

    public int getStationId() {
        return mStationId;
    }

    public String getImage() {
        return "folder.webp";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FolderEntity)) return false;

        FolderEntity folderEntity = (FolderEntity) obj;
        return mId == folderEntity.getId() &&
                mName.equals(folderEntity.getName()) &&
                mParentId == folderEntity.getParentId() &&
                mStationId == folderEntity.getStationId();
    }
}