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

import arc.resource.calculator.db.dao.StationDao;

/**
 * Station object for base game data (vanilla)
 * <p>
 * Stations for DLC game data should extend from this class
 */
@Entity(tableName = StationDao.tableName)
public class StationEntity {
    @PrimaryKey(autoGenerate = true)
    private int rowId;

    //  name of crafting station
    private String name;

    //  description of crafting station, pulled from engram description
    private String description;

    //  filename of image in /assets folder
    private String image;

    public StationEntity() {
    }

    public StationEntity(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationEntity)) return false;

        StationEntity stationEntity = (StationEntity) obj;
        return getRowId() == stationEntity.getRowId() &&
                getName().equals(stationEntity.getName()) &&
                getDescription().equals(stationEntity.getDescription()) &&
                getImage().equals(stationEntity.getImage());
    }

    public int getRowId() {
        return rowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
