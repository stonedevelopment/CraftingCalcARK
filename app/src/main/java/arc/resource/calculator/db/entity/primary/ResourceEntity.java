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

import java.util.Date;

import arc.resource.calculator.db.dao.primary.ResourceDao;

import static arc.resource.calculator.util.JSONUtil.cDescription;
import static arc.resource.calculator.util.JSONUtil.cGameId;
import static arc.resource.calculator.util.JSONUtil.cImageFile;
import static arc.resource.calculator.util.JSONUtil.cLastUpdated;
import static arc.resource.calculator.util.JSONUtil.cName;
import static arc.resource.calculator.util.JSONUtil.cUuid;

/**
 * Resource object for Primary game data
 * <p>
 * Resources for DLC game data should extend from this class
 */
@Entity(tableName = ResourceDao.tableName)
public class ResourceEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    private String description;
    private String imageFile;
    private Date lastUpdated;
    private String gameId;

    public ResourceEntity(@NonNull String uuid, String name, String description, String imageFile, Date lastUpdated, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.imageFile = imageFile;
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
    public static ResourceEntity fromJSON(JsonNode node) {
        String uuid = node.get(cUuid).asText();
        String name = node.get(cName).asText();
        String description = node.get(cDescription).asText();
        String imageFile = node.get(cImageFile).asText();
        Date lastUpdated = new Date(node.get(cLastUpdated).asLong());
        String gameId = node.get(cGameId).asText();
        return new ResourceEntity(uuid, name, description, imageFile, lastUpdated, gameId);
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
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

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResourceEntity)) return false;

        ResourceEntity resource = (ResourceEntity) obj;
        return uuid.equals(resource.getUuid()) &&
                name.equals(resource.getName()) &&
                description.equals(resource.getDescription()) &&
                imageFile.equals(resource.getImageFile()) &&
                gameId.equals(resource.getGameId());
    }
}
