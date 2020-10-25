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

import arc.resource.calculator.db.dao.primary.CompositionDao;

@Entity(tableName = CompositionDao.tableName)
public class CompositionEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String engramId;
    private Date lastUpdated;
    private String gameId;

    public CompositionEntity(@NonNull String uuid,
                             @NonNull String engramId,
                             @NonNull Date lastUpdated,
                             @NonNull String gameId) {
        this.uuid = uuid;
        this.engramId = engramId;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    public static CompositionEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, CompositionEntity.class);
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public String getEngramId() {
        return engramId;
    }

    public void setEngramId(@NonNull String engramId) {
        this.engramId = engramId;
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