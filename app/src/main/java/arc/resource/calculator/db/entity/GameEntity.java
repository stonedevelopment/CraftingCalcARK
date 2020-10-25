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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.GameDao;

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

    public GameEntity(@NonNull String uuid, String name, String description, String filePath, String logoFile, String folderFile, String backFolderFile, Date lastUpdated) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.filePath = filePath;
        this.logoFile = logoFile;
        this.folderFile = folderFile;
        this.backFolderFile = backFolderFile;
        this.lastUpdated = lastUpdated;
    }

    /**
     * "uuid": "9a26130d-a985-4bc6-b9da-2b092f22a277",
     * "name": "ARK:Survival Evolved",
     * "description": "As a man or woman stranded, naked, freezing, and starving on the unforgiving shores of a mysterious island called \"ARK\", use your skill and cunning to kill or tame and ride the plethora of leviathan dinosaurs and other primeval creatures roaming the land. Hunt, harvest resources, craft items, grow crops, research technologies, and build shelters to withstand the elements and store valuables, all while teaming up with (or preying upon) hundreds of other players to survive, dominate... and escape!",
     * "filePath": "Primary/",
     * "logoFile": "logo.webp",
     * "folderFile": "folder.webp",
     * "backFolderFile": "backFolder.webp",
     * "lastUpdated": 1589725368121,
     */
    public static GameEntity fromJSON(JsonNode node) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(String logoFile) {
        this.logoFile = logoFile;
    }

    public String getFolderFile() {
        return folderFile;
    }

    public void setFolderFile(String folderFile) {
        this.folderFile = folderFile;
    }

    public String getBackFolderFile() {
        return backFolderFile;
    }

    public void setBackFolderFile(String backFolderFile) {
        this.backFolderFile = backFolderFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}