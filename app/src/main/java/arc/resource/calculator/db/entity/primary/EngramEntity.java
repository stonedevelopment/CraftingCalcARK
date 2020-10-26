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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.primary.EngramDao;

import static arc.resource.calculator.util.Constants.cCraftingTime;
import static arc.resource.calculator.util.Constants.cDescription;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cLevel;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cPoints;
import static arc.resource.calculator.util.Constants.cUuid;
import static arc.resource.calculator.util.Constants.cXp;
import static arc.resource.calculator.util.Constants.cYield;

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
    @NonNull
    private String gameId;

    @JsonCreator
    public EngramEntity(@JsonProperty(cUuid) @NonNull String uuid,
                        @JsonProperty(cName) @NonNull String name,
                        @JsonProperty(cDescription) @NonNull String description,
                        @JsonProperty(cImageFile) @NonNull String imageFile,
                        @JsonProperty(cLevel) int level,
                        @JsonProperty(cYield) int yield,
                        @JsonProperty(cPoints) int points,
                        @JsonProperty(cXp) int xp,
                        @JsonProperty(cCraftingTime) int craftingTime,
                        @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                        @JsonProperty(cGameId) @NonNull String gameId) {
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

    public static EngramEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, EngramEntity.class);
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

    public void setLastUpdated(@NonNull Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @NonNull
    public String getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull String gameId) {
        this.gameId = gameId;
    }
}
