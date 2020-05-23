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

import java.util.Date;

import arc.resource.calculator.db.dao.primary.CompositionDao;

@Entity(tableName = CompositionDao.tableName)
public class CompositionEntity {
    @PrimaryKey
    private final String uuid;
    private final String engramId;
    private final Date lastUpdated;
    private final String gameId;

    private CompositionEntity(String uuid, String engramId, Date lastUpdated, String gameId) {
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
    public static CompositionEntity fromJSON(JsonNode node) throws JsonProcessingException {
        return new ObjectMapper().treeToValue(node, CompositionEntity.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getEngramId() {
        return engramId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getGameId() {
        return gameId;
    }
}