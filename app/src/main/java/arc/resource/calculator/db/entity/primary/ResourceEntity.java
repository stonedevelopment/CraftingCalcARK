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
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.primary.ResourceDao;

/**
 * Resource object for Primary game data
 * <p>
 * Resources for DLC game data should extend from this class
 */
@Entity(tableName = ResourceDao.tableName)
public class ResourceEntity {
    @PrimaryKey
    private String uuid;
    private String name;
    private String description;
    private String imageFile;
    private Date lastUpdated;
    private String gameId;

    public ResourceEntity(@NonNull String uuid,
                          @NonNull String name,
                          @NonNull String description,
                          @NonNull String imageFile,
                          @NonNull Date lastUpdated,
                          @NonNull String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.imageFile = imageFile;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    public static ResourceEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, ResourceEntity.class);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(@NonNull String imageFile) {
        this.imageFile = imageFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(@NonNull Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull String gameId) {
        this.gameId = gameId;
    }
}
