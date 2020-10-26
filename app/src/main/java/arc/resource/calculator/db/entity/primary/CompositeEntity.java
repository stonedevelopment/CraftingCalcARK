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

import arc.resource.calculator.db.dao.primary.CompositeDao;

import static arc.resource.calculator.util.Constants.cCompositionId;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cIsEngram;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cQuantity;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;

@Entity(tableName = CompositeDao.tableName)
public class CompositeEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    private String imageFile;
    private int quantity;
    private String sourceId;
    private boolean isEngram;
    private String compositionId;
    private Date lastUpdated;
    @NonNull
    private String gameId;

    @JsonCreator
    public CompositeEntity(@JsonProperty(cUuid) @NonNull String uuid,
                           @JsonProperty(cName) @NonNull String name,
                           @JsonProperty(cImageFile) @NonNull String imageFile,
                           @JsonProperty(cQuantity) int quantity,
                           @JsonProperty(cSourceId) @NonNull String sourceId,
                           @JsonProperty(cIsEngram) boolean isEngram,
                           @JsonProperty(cCompositionId) @NonNull String compositionId,
                           @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                           @JsonProperty(cGameId) @NonNull String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.imageFile = imageFile;
        this.quantity = quantity;
        this.sourceId = sourceId;
        this.isEngram = isEngram;
        this.compositionId = compositionId;
        this.lastUpdated = lastUpdated;
        this.gameId = gameId;
    }

    public static CompositeEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, CompositeEntity.class);
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

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(@NonNull String imageFile) {
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

    public void setSourceId(@NonNull String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isEngram() {
        return isEngram;
    }

    public void setIsEngram(boolean isEngram) {
        this.isEngram = isEngram;
    }

    public String getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(@NonNull String compositionId) {
        this.compositionId = compositionId;
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