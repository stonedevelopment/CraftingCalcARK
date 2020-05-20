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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private final String imageFile;
    private final String engramId;
    private final Date lastUpdated;
    private final String gameId;

    public StationEntity(String uuid, String name, String imageFile, String engramId, Date lastUpdated, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.imageFile = imageFile;
        this.engramId = engramId;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    /**
     * "uuid": "1c387d37-7c9f-43ef-b154-f7fd2b935525",
     * "name": "Beer Barrel",
     * "imageFile": "beer_barrel.webp",
     * "engramId": "8e572762-5b55-44c3-ae97-ef2087ae20e3",
     * "lastUpdated": 1589725368336
     * "gameId": "4fbb5cdf-9b17-4f03-a73a-038449b1bf32"
     */
    public static StationEntity fromJSON(JsonNode node) throws IOException {
        return new ObjectMapper().treeToValue(node, StationEntity.class);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationEntity)) return false;

        StationEntity station = (StationEntity) obj;
        return uuid.equals(station.getUuid()) &&
                name.equals(station.getName()) &&
                engramId.equals(station.getEngramId()) &&
                imageFile.equals(station.getImageFile()) &&
                gameId.equals(station.getGameId());
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getEngramId() {
        return engramId;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getGameId() {
        return gameId;
    }
}
