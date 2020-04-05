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
 * Station object for base game data (vanilla)
 * <p>
 * Stations for DLC game data should extend from this class
 */
@Entity(tableName = StationDao.tableName)
public class StationEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private final int mId;

    //  name of crafting station
    @ColumnInfo(name = "name")
    private final String mName;

    //  description of crafting station, pulled from engram description
    @ColumnInfo(name = "description")
    private final String mDescription;

    //  filename of image in /assets folder
    @ColumnInfo(name = "image")
    private final String mImage;

    public StationEntity(int rowId, String name, String description, String image) {
        mId = rowId;
        mName = name;
        mDescription = description;
        mImage = image;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationEntity)) return false;

        StationEntity stationEntity = (StationEntity) obj;
        return getId() == stationEntity.getId() &&
                getName().equals(stationEntity.getName()) &&
                getDescription().equals(stationEntity.getDescription()) &&
                getImage().equals(stationEntity.getImage());
    }
}
