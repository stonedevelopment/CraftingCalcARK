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

import arc.resource.calculator.db.dao.StationDao;

/**
 * Engram object for base game data (vanilla)
 * <p>
 * Engrams for DLC game data should extend from this class
 */
@Entity(tableName = "engrams")
public class EngramEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private final int rowId;

    //  title of this engram
    private final String title;

    //  description for this engram
    private final String description;

    //  filename of image in /assets folder
    private final String image;

    //  in-game location where users can craft this engram
    private final String location;

    //  amount produced per craft, multiply this by crafting quantity
    private final int yield;

    //  value of required level to craft engram
    private final int level;

    //  crafting time in seconds    // TODO: 2/2/2020 will be used when calculating fuel usages
    private final int time;

    //  rowid of parent; station or folder
    private final int parentId;

    //  rowid of stations table, crafting station per in-game
    @ColumnInfo(name = StationDao.columnName)
    private final int stationId;

    public EngramEntity(int rowId, String name, String description, String image, String location,
                        int yield, int level, int time, int folderId, int stationId) {
        this.rowId = rowId;
        this.title = name;
        this.description = description;
        this.image = image;
        this.location = location;
        this.yield = yield;
        this.level = level;
        this.time = time;
        this.parentId = folderId;
        this.stationId = stationId;
    }

    public int getRowId() {
        return rowId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public int getYield() {
        return yield;
    }

    public int getLevel() {
        return level;
    }

    public int getTime() {
        return time;
    }

    public int getParentId() {
        return parentId;
    }

    public int getStationId() {
        return stationId;
    }
}
