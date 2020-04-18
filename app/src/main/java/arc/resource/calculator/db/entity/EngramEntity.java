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
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import arc.resource.calculator.db.dao.EngramDao;

/**
 * Engram object for base game data (vanilla)
 * <p>
 * Engrams for DLC game data should extend from this class
 */
@Entity(tableName = EngramDao.tableName)
public class EngramEntity {

    @PrimaryKey(autoGenerate = true)
    private int rowId;

    //  title of this engram
    private String name;

    //  description for this engram
    private String description;

    //  filename of image in /assets folder
    private String image;

    //  amount produced per craft, multiply this by crafting quantity
    private int yield;

    //  value of required level to craft engram
    private int level;

    //  crafting time in seconds    // TODO: 2/2/2020 will be used when calculating fuel usages
    private int time;

    //  rowid of parent; station or folder
    private int parentId;

    //  rowid of stations table, crafting station per in-game
    private int stationId;

    public EngramEntity(String name, String description, String image,
                        int yield, int level, int time, int parentId, int stationId) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.yield = yield;
        this.level = level;
        this.time = time;
        this.parentId = parentId;
        this.stationId = stationId;
    }

    public int getRowId() {
        return rowId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public int getParentId() {
        return parentId;
    }

    public int getStationId() {
        return stationId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EngramEntity)) return false;

        EngramEntity engramEntity = (EngramEntity) obj;
        return rowId == engramEntity.getRowId() &&
                name.equals(engramEntity.getName()) &&
                parentId == engramEntity.getParentId() &&
                stationId == engramEntity.getStationId();

    }
}
