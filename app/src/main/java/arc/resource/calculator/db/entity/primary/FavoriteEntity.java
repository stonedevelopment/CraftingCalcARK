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

import java.util.Date;

import arc.resource.calculator.db.dao.primary.FavoriteDao;

import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;
import static arc.resource.calculator.util.Constants.cViewType;

/**
 * Favorite object for base game data (vanilla)
 */
@Entity(tableName = FavoriteDao.tableName)
public class FavoriteEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private int viewType;
    @NonNull
    private String sourceId;
    private Date lastUpdated;
    @NonNull
    private String gameId;

    @JsonCreator
    public FavoriteEntity(@JsonProperty(cUuid) @NonNull String uuid,
                          @JsonProperty(cViewType) int viewType,
                          @JsonProperty(cSourceId) @NonNull String sourceId,
                          @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                          @JsonProperty(cGameId) @NonNull String gameId) {
        this.uuid = uuid;
        this.viewType = viewType;
        this.sourceId = sourceId;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
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
}