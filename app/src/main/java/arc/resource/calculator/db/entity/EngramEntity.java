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
    private final int mId;

    //  title of this engram
    @ColumnInfo(name = "name")
    private final String mName;

    //  description for this engram
    @ColumnInfo(name = "description")
    private final String mDescription;

    //  filename of image in /assets folder
    @ColumnInfo(name = "image")
    private final String mImage;

    //  in-game location where users can craft this engram
    @ColumnInfo(name = "location")
    private final String mLocation;

    //  amount produced per craft, multiply this by crafting quantity
    @ColumnInfo(name = "yield")
    private final int mYield;

    //  value of required level to craft engram
    @ColumnInfo(name = "level")
    private final int mLevel;

    //  crafting time in seconds    // TODO: 2/2/2020 will be used when calculating fuel usages
    @ColumnInfo(name = "time")
    private final int mTime;

    //  rowid of parent; station or folder
    @ColumnInfo(name = "parentid")
    private final int mParentId;

    //  rowid of stations table, crafting station per in-game
    @ColumnInfo(name = StationDao.columnName)
    private final int mStationId;

    public EngramEntity(int rowId, String name, String description, String image, String location,
                        int yield, int level, int time, int parentId, int stationId) {
        this.mId = rowId;
        this.mName = name;
        this.mDescription = description;
        this.mImage = image;
        this.mLocation = location;
        this.mYield = yield;
        this.mLevel = level;
        this.mTime = time;
        this.mParentId = parentId;
        this.mStationId = stationId;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImage() {
        return mImage;
    }

    public String getLocation() {
        return mLocation;
    }

    public int getYield() {
        return mYield;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getTime() {
        return mTime;
    }

    public int getParentId() {
        return mParentId;
    }

    public int getStationId() {
        return mStationId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EngramEntity)) return false;

        EngramEntity engramEntity = (EngramEntity) obj;
        return mId == engramEntity.getId() &&
                mName.equals(engramEntity.getName()) &&
                mParentId == engramEntity.getParentId() &&
                mStationId == engramEntity.getStationId();

    }
}
