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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.DlcDao;

import static arc.resource.calculator.util.Constants.cBackFolderFile;
import static arc.resource.calculator.util.Constants.cDescription;
import static arc.resource.calculator.util.Constants.cFilePath;
import static arc.resource.calculator.util.Constants.cFolderFile;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cLogoFile;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cTotalConversion;
import static arc.resource.calculator.util.Constants.cUuid;

@Entity(tableName = DlcDao.tableName)
public class DlcEntity extends GameEntity {
    private boolean totalConversion;
    @NonNull
    private String gameId;

    @JsonCreator
    public DlcEntity(@JsonProperty(cUuid) @NonNull String uuid,
                     @JsonProperty(cName) @NonNull String name,
                     @JsonProperty(cDescription) @NonNull String description,
                     @JsonProperty(cFilePath) @NonNull String filePath,
                     @JsonProperty(cLogoFile) @NonNull String logoFile,
                     @JsonProperty(cFolderFile) @NonNull String folderFile,
                     @JsonProperty(cBackFolderFile) @NonNull String backFolderFile,
                     @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                     @JsonProperty(cTotalConversion) boolean totalConversion,
                     @JsonProperty(cGameId) @NonNull String gameId) {
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