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

package arc.resource.calculator.db.entity.primary;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import arc.resource.calculator.db.dao.primary.StationDao;

/**
 * Station object for base game data (vanilla)
 * <p>
 * Stations for DLC game data should extend from this class
 */
@Entity(tableName = StationDao.tableName)
public class StationEntity {
    @PrimaryKey
    private final String uuid;
    private final String name;
    private final String description;
    private final String imageFile;
    private final Date lastUpdated;

    public StationEntity(String rowId, String name, String description, String image, Date lastUpdated) {
        this.uuid = rowId;
        this.name = name;
        this.description = description;
        this.imageFile = image;
        this.lastUpdated = lastUpdated;
    }

    /**
     * "uuid": "1c387d37-7c9f-43ef-b154-f7fd2b935525",
     * "name": "Beer Barrel",
     * "imageFile": "beer_barrel.webp",
     * "engramId": "8e572762-5b55-44c3-ae97-ef2087ae20e3",
     * "lastUpdated": 1589725368336
     */
    public static StationEntity fromJSON(JSONObject jsonObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonObject.toString(), StationEntity.class);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationEntity)) return false;

        StationEntity stationEntity = (StationEntity) obj;
        return getUuid().equals(stationEntity.getUuid()) &&
                getName().equals(stationEntity.getName()) &&
                getDescription().equals(stationEntity.getDescription()) &&
                getImageFile().equals(stationEntity.getImageFile());
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
}
