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
import com.fasterxml.jackson.databind.ObjectMapper;

import arc.resource.calculator.db.dao.primary.CompositeDao;

@Entity(tableName = CompositeDao.tableName)
public class CompositeEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    private String imageFile;
    private int quantity;
    private String sourceId;
    private String compositionId;
    private String gameId;
    private boolean isEngram;

    public CompositeEntity(@NonNull String uuid, String name, String imageFile, int quantity,
                           String sourceId, String compositionId, String gameId,
                           boolean isEngram) {
        this.uuid = uuid;
        this.name = name;
        this.imageFile = imageFile;
        this.quantity = quantity;
        this.sourceId = sourceId;
        this.compositionId = compositionId;
        this.gameId = gameId;
        this.isEngram = isEngram;
    }

    /**
     * "uuid": "994f8240-f9e0-4dba-987f-de5c31287ef8",
     * "name": "Absorbent Substrate",
     * "imageFile": "absorbent_substrate.webp",
     * "quantity": 10,
     * "sourceId": "72689ce4-92cc-47e2-95e3-05748c368983",
     * "compositionId": "6693c091-aa97-4d77-8267-f163fe71ae1f",
     * "gameId": "ce61547f-9ace-4a3b-b5c0-216f234339c7",
     * "isEngram": true
     */
    public static CompositeEntity fromJSON(JsonNode node) throws JsonProcessingException {
        return new ObjectMapper().treeToValue(node, CompositeEntity.class);
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

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(String compositionId) {
        this.compositionId = compositionId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isEngram() {
        return isEngram;
    }

    public void setIsEngram(boolean isEngram) {
        this.isEngram = isEngram;
    }
}