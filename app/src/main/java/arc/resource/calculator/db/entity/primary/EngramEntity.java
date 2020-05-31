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

import arc.resource.calculator.db.dao.primary.EngramDao;

import static arc.resource.calculator.util.JSONUtil.cDescription;
import static arc.resource.calculator.util.JSONUtil.cGameId;
import static arc.resource.calculator.util.JSONUtil.cImageFile;
import static arc.resource.calculator.util.JSONUtil.cLastUpdated;
import static arc.resource.calculator.util.JSONUtil.cName;
import static arc.resource.calculator.util.JSONUtil.cUuid;

/**
 * Engram object for base game data (vanilla)
 * <p>
 * Engrams for DLC game data should extend from this class
 */
@Entity(tableName = EngramDao.tableName)
public class EngramEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    private String description;
    private String imageFile;
    private int level;
    private int yield;
    private int points;
    private int xp;
    private int craftingTime;
    private Date lastUpdated;
    private String gameId;

    public EngramEntity(@NonNull String uuid, String name, String description, String imageFile, int level,
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
    public static EngramEntity fromJSON(JsonNode node) {
        String uuid = node.get(cUuid).asText();
        String name = node.get(cName).asText();
        String description = node.get(cDescription).asText();
        String imageFile = node.get(cImageFile).asText();
        int level = node.get(cImageFile).asInt();
        int yield = node.get(cImageFile).asInt();
        int points = node.get(cImageFile).asInt();
        int xp = node.get(cImageFile).asInt();
        int craftingTime = node.get(cImageFile).asInt();
        Date lastUpdated = new Date(node.get(cLastUpdated).asLong());
        String gameId = node.get(cGameId).asText();
        return new EngramEntity(uuid, name, description, imageFile, level, yield, points, xp, craftingTime, lastUpdated, gameId);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    public void setCraftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
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
}
