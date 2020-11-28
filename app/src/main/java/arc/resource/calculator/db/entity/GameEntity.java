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
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.GameDao;

import static arc.resource.calculator.util.Constants.cBackFolderFile;
import static arc.resource.calculator.util.Constants.cDescription;
import static arc.resource.calculator.util.Constants.cFilePath;
import static arc.resource.calculator.util.Constants.cFolderFile;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cLogoFile;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cUuid;

@Entity(tableName = GameDao.tableName)
public class GameEntity {
    @NonNull
    @PrimaryKey
    private String uuid;
    private String name;
    private String description;
    private String filePath;
    private String logoFile;
    private String folderFile;
    private String backFolderFile;
    private Date lastUpdated;

    @JsonCreator
    public GameEntity(@JsonProperty(cUuid) @NonNull String uuid,
                      @JsonProperty(cName) @NonNull String name,
                      @JsonProperty(cDescription) @NonNull String description,
                      @JsonProperty(cFilePath) @NonNull String filePath,
                      @JsonProperty(cLogoFile) @NonNull String logoFile,
                      @JsonProperty(cFolderFile) @NonNull String folderFile,
                      @JsonProperty(cBackFolderFile) @NonNull String backFolderFile,
                      @JsonProperty(cLastUpdated) @NonNull Date lastUpdated) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.filePath = filePath;
        this.logoFile = logoFile;
        this.folderFile = folderFile;
        this.backFolderFile = backFolderFile;
        this.lastUpdated = lastUpdated;
    }

    public static GameEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, GameEntity.class);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(@NonNull String filePath) {
        this.filePath = filePath;
    }

    public String getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(@NonNull String logoFile) {
        this.logoFile = logoFile;
    }

    public String getFolderFile() {
        return folderFile;
    }

    public void setFolderFile(@NonNull String folderFile) {
        this.folderFile = folderFile;
    }

    public String getBackFolderFile() {
        return backFolderFile;
    }

    public void setBackFolderFile(@NonNull String backFolderFile) {
        this.backFolderFile = backFolderFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(@NonNull Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", filePath='" + filePath + '\'' +
                ", logoFile='" + logoFile + '\'' +
                ", folderFile='" + folderFile + '\'' +
                ", backFolderFile='" + backFolderFile + '\'' +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}