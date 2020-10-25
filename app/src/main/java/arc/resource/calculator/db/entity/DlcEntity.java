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

package arc.resource.calculator.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.DlcDao;

@Entity(tableName = DlcDao.tableName)
public class DlcEntity extends GameEntity {
    private boolean totalConversion;
    @NonNull
    private String gameId;

    public DlcEntity(@NonNull String uuid,
                     @NonNull String name,
                     @NonNull String description,
                     @NonNull String filePath,
                     @NonNull String logoFile,
                     @NonNull String folderFile,
                     @NonNull String backFolderFile,
                     @NonNull Date lastUpdated,
                     boolean totalConversion,
                     @NonNull String gameId) {
        super(uuid, name, description, filePath, logoFile, folderFile, backFolderFile, lastUpdated);
        this.totalConversion = totalConversion;
        this.gameId = gameId;
    }

    public static DlcEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcEntity.class);
    }

    public boolean isTotalConversion() {
        return totalConversion;
    }

    public void setTotalConversion(boolean totalConversion) {
        this.totalConversion = totalConversion;
    }

    @NonNull
    public String getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull String gameId) {
        this.gameId = gameId;
    }
}