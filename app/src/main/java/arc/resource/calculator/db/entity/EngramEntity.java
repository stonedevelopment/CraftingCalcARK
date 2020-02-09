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

import arc.resource.calculator.db.dao.DescriptionDao;
import arc.resource.calculator.db.dao.FolderDao;
import arc.resource.calculator.db.dao.NameDao;
import arc.resource.calculator.db.dao.StationDao;

@Entity(tableName = "engrams")
public class EngramEntity {

    @PrimaryKey(autoGenerate = true)
    private final int id;

    //  rowid of name table
    @ColumnInfo(name = NameDao.columnName)
    private final int nameId;

    //  rowid of description table
    @ColumnInfo(name = DescriptionDao.columnName)
    private final int descriptionId;

    //  rowid of folders table, used as its parent location in app: NOT per in-game navigation
    @ColumnInfo(name = FolderDao.columnName)
    private final int folderId;

    //  rowid of stations table, crafting station per in-game
    @ColumnInfo(name = StationDao.columnName)
    private final int stationId;

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

    public EngramEntity(int id, int nameId, int descriptionId, int folderId, int stationId, String image, String location, int yield, int level, int time) {
        this.id = id;
        this.nameId = nameId;
        this.descriptionId = descriptionId;
        this.folderId = folderId;
        this.stationId = stationId;
        this.image = image;
        this.location = location;
        this.yield = yield;
        this.level = level;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getNameId() {
        return nameId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public int getFolderId() {
        return folderId;
    }

    public int getStationId() {
        return stationId;
    }

    public String getImage() {
        return image;
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

    public String getLocation() {
        return location;
    }
}
