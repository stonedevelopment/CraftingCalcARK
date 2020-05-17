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

import arc.resource.calculator.db.dao.primary.ResourceDao;

/**
 * Resource object for base game data (vanilla)
 * <p>
 * Resources for DLC game data should extend from this class
 */
@Entity(tableName = ResourceDao.tableName)
public class ResourceEntity {
    @PrimaryKey
    private final String uuid;
    private final String name;
    private final String description;
    private final String imageFile;
    private final Date lastUpdated;
    private final String gameId;

    public ResourceEntity(String uuid, String name, String description, String image, Date lastUpdated, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.imageFile = image;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    /**
     * "uuid": "8d6c5c32-0565-49cd-8f7c-fc5b73bd4021",
     * "name": "Allosaurus Brain",
     * "description": "",
     * "imageFile": "allosaurus_brain.webp",
     * "lastUpdated": 1589732742758,
     * "gameId": "4fbb5cdf-9b17-4f03-a73a-038449b1bf32"
     */
    public static ResourceEntity fromJSON(JsonNode jsonObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonObject.toString(), ResourceEntity.class);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResourceEntity)) return false;

        ResourceEntity entity = (ResourceEntity) obj;
        return uuid.equals(entity.getUuid()) &&
                name.equals(entity.getName()) &&
                description.equals(entity.getDescription()) &&
                imageFile.equals(entity.getImageFile()) &&
                gameId.equals(entity.getGameId());
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

    public String getGameId() {
        return gameId;
    }
}
