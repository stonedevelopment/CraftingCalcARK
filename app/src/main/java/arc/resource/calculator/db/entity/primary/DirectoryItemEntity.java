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

import arc.resource.calculator.db.dao.primary.DirectoryDao;

import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cParentId;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;
import static arc.resource.calculator.util.Constants.cViewType;

@Entity(tableName = DirectoryDao.tableName)
public class DirectoryItemEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    private String imageFile;
    private int viewType;
    private String parentId;
    private String sourceId;
    @NonNull
    private String gameId;

    @JsonCreator
    public DirectoryItemEntity(@JsonProperty(cUuid) @NonNull String uuid,
                               @JsonProperty(cName) @NonNull String name,
                               @JsonProperty(cImageFile) @NonNull String imageFile,
                               @JsonProperty(cViewType) int viewType,
                               @JsonProperty(cParentId) @NonNull String parentId,
                               @JsonProperty(cSourceId) @NonNull String sourceId,
                               @JsonProperty(cGameId) @NonNull String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.imageFile = imageFile;
        this.viewType = viewType;
        this.parentId = parentId;
        this.sourceId = sourceId;
        this.gameId = gameId;
    }

    public static DirectoryItemEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DirectoryItemEntity.class);
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(@NonNull String parentId) {
        this.parentId = parentId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(@NonNull String sourceId) {
        this.sourceId = sourceId;
    }

    @NonNull
    public String getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull String gameId) {
        this.gameId = gameId;
    }
}