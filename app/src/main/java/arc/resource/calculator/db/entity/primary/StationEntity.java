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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.primary.StationDao;

import static arc.resource.calculator.util.JSONUtil.cEngramId;
import static arc.resource.calculator.util.JSONUtil.cGameId;
import static arc.resource.calculator.util.JSONUtil.cImageFile;
import static arc.resource.calculator.util.JSONUtil.cLastUpdated;
import static arc.resource.calculator.util.JSONUtil.cName;
import static arc.resource.calculator.util.JSONUtil.cUuid;

/**
 * Station object for base game data (vanilla)
 * <p>
 * Stations for DLC game data should extend from this class
 */
@Entity(tableName = StationDao.tableName)
public class StationEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    @NonNull
    private String name;
    @NonNull
    private String imageFile;
    private String sourceId;
    private Date lastUpdated;
    @NonNull
    private String gameId;

    public StationEntity(@NonNull String uuid, @NonNull String name, @NonNull String imageFile, String sourceId, Date lastUpdated, @NonNull String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.imageFile = imageFile;
        this.sourceId = sourceId;
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
    public static StationEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, StationEntity.class);
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(@NonNull String imageFile) {
        this.imageFile = imageFile;
    }

    @NonNull
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(@NonNull String sourceId) {
        this.sourceId = sourceId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @NonNull
    public String getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull String gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationEntity)) return false;

        StationEntity station = (StationEntity) obj;
        return uuid.equals(station.getUuid()) &&
                name.equals(station.getName()) &&
                sourceId.equals(station.getSourceId()) &&
                imageFile.equals(station.getImageFile()) &&
                gameId.equals(station.getGameId());
    }
}
