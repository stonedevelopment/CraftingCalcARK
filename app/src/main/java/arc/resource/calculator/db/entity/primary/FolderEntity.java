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

import arc.resource.calculator.db.dao.primary.FolderDao;

import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cUuid;

/**
 * Folder object for base game data (vanilla)
 * <p>
 * Folders for DLC game data should extend from this class
 */
@Entity(tableName = FolderDao.tableName)
public class FolderEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    @NonNull
    private String gameId;

    @JsonCreator
    public FolderEntity(@JsonProperty(cUuid) @NonNull String uuid,
                        @JsonProperty(cName) @NonNull String name,
                        @JsonProperty(cGameId) @NonNull String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.gameId = gameId;
    }

    public static FolderEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, FolderEntity.class);
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

    @NonNull
    public String getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull String gameId) {
        this.gameId = gameId;
    }
}