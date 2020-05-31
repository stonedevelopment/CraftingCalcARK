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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

import arc.resource.calculator.db.dao.primary.CompositionDao;

import static arc.resource.calculator.util.JSONUtil.cEngramId;
import static arc.resource.calculator.util.JSONUtil.cGameId;
import static arc.resource.calculator.util.JSONUtil.cLastUpdated;
import static arc.resource.calculator.util.JSONUtil.cUuid;

@Entity(tableName = CompositionDao.tableName)
public class CompositionEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String engramId;
    private Date lastUpdated;
    private String gameId;

    public CompositionEntity(@NonNull String uuid, String engramId, Date lastUpdated, String gameId) {
        this.uuid = uuid;
        this.engramId = engramId;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    /**
     * "uuid": "0cc0a7ff-e3e5-4f62-b5d1-4e049cd5dd95",
     * "engramId": "54fb9fa0-4e4f-4d3c-ba93-b5d5bc0a9d0d",
     * "lastUpdated": 1590242216057,
     * "gameId": "d3ed5cd5-9dc7-4f10-b988-444b19abd554"
     */
    public static CompositionEntity fromJSON(JsonNode node) {
        String uuid = node.get(cUuid).asText();
        String engramId = node.get(cEngramId).asText();
        Date lastUpdated = new Date(node.get(cLastUpdated).asLong());
        String gameId = node.get(cGameId).asText();
        return new CompositionEntity(uuid, engramId, lastUpdated, gameId);
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

    public void setEngramId(String engramId) {
        this.engramId = engramId;
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
}