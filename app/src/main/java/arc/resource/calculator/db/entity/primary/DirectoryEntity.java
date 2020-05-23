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

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import arc.resource.calculator.db.dao.primary.DirectoryDao;

@Entity(tableName = DirectoryDao.tableName)
public class DirectoryEntity {
    @PrimaryKey
    private final String uuid;
    private final String name;
    private final String imageFile;
    private final String parentId;
    private final String sourceId;
    private final String gameId;

    private DirectoryEntity(String uuid, String name, String imageFile, String parentId, String sourceId, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.imageFile = imageFile;
        this.parentId = parentId;
        this.sourceId = sourceId;
        this.gameId = gameId;
    }

    /**
     * "uuid": "503ec34c-d61f-43d6-bf84-54d5a27f3a17",
     * "name": "Absorbent Substrate",
     * "imageFile": "absorbent_substrate.webp",
     * "parentId": "6bf03190-f6ab-42f9-98b6-79094803dae0",
     * "sourceId": "72689ce4-92cc-47e2-95e3-05748c368983",
     * "gameId": "ce61547f-9ace-4a3b-b5c0-216f234339c7"
     */
    public static DirectoryEntity fromJSON(JsonNode node) throws JsonProcessingException {
        return new ObjectMapper().treeToValue(node, DirectoryEntity.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getParentId() {
        return parentId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getGameId() {
        return gameId;
    }
}
