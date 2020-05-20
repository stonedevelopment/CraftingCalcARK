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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.primary.EngramDao;

/**
 * Engram object for base game data (vanilla)
 * <p>
 * Engrams for DLC game data should extend from this class
 */
@Entity(tableName = EngramDao.tableName)
public class EngramEntity {

    @PrimaryKey
    private final String uuid;
    private final String name;
    private final String description;
    private final String imageFile;
    private final int level;
    private final int yield;
    private final int points;
    private final int xp;
    private final int craftingTime;
    private final Date lastUpdated;
    private final String gameId;

    public EngramEntity(String uuid, String name, String description, String imageFile, int level,
                        int yield, int points, int xp, int craftingTime, Date lastUpdated, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.imageFile = imageFile;
        this.level = level;
        this.yield = yield;
        this.points = points;
        this.xp = xp;
        this.craftingTime = craftingTime;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    /**
     * "uuid": "76d95c88-db9f-4fc0-936f-0c778ab499d4",
     * "name": "Absorbent Substrate",
     * "description": "This sticky compound excels at absorbing other chemicals.",
     * "imageFile": "absorbent_substrate.webp",
     * "level": 84,
     * "yield": 1,
     * "points": 0,
     * "xp": 0,
     * "craftingTime": 0,
     * "lastUpdated": 1589732742789,
     * "gameId": "4fbb5cdf-9b17-4f03-a73a-038449b1bf32"
     */
    public static EngramEntity fromJSON(JsonNode node) throws JsonProcessingException {
        return new ObjectMapper().treeToValue(node, EngramEntity.class);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EngramEntity)) return false;

        EngramEntity engram = (EngramEntity) obj;
        return uuid.equals(engram.uuid) &&
                name.equals(engram.name) &&
                description.equals(engram.description) &&
                imageFile.equals(engram.imageFile) &&
                level == engram.level &&
                yield == engram.yield &&
                points == engram.points &&
                xp == engram.xp &&
                craftingTime == engram.craftingTime &&
                gameId.equals(engram.gameId);


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

    public int getLevel() {
        return level;
    }

    public int getYield() {
        return yield;
    }

    public int getPoints() {
        return points;
    }

    public int getXp() {
        return xp;
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getGameId() {
        return gameId;
    }
}
