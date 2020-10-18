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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import arc.resource.calculator.db.dao.primary.FolderDao;

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
    private String gameId;

    public FolderEntity(@NonNull String uuid, String name, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.gameId = gameId;
    }

    /**
     * "uuid": "16294807-c086-4977-ad87-899aed7ec199",
     * "name": "Misc",
     * "gameId": "4fbb5cdf-9b17-4f03-a73a-038449b1bf32"
     */
    public static FolderEntity fromJson(JsonNode node) throws IOException {
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

    public void setName(String name) {
        this.name = name;
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
        if (!(obj instanceof FolderEntity)) return false;

        FolderEntity folder = (FolderEntity) obj;
        return uuid.equals(folder.getUuid()) &&
                name.equals(folder.getName()) &&
                gameId.equals(folder.getGameId());
    }
}