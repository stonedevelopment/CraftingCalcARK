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

package arc.resource.calculator.db.entity.dlc;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

import arc.resource.calculator.db.dao.dlc.DlcDao;

@Entity(tableName = DlcDao.tableName)
public class DlcEntity {
    @PrimaryKey
    private final String uuid;
    private final String name;
    private final String description;
    private final String filePath;
    private final String logoFile;
    private final String folderFile;
    private final String backFolderFile;
    private final Date lastUpdated;
    private final Boolean totalConversion;
    private final String gameId;

    private DlcEntity(String uuid, String name, String description, String filePath, String logoFile, String folderFile, String backFolderFile, Date lastUpdated, Boolean totalConversion, String gameId) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.filePath = filePath;
        this.logoFile = logoFile;
        this.folderFile = folderFile;
        this.backFolderFile = backFolderFile;
        this.lastUpdated = lastUpdated;
        this.totalConversion = totalConversion;
        this.gameId = gameId;
    }

    /**
     * "uuid": "34a540f9-3c0d-4f04-aa46-4c4093f619cd",
     * "name": "Aberration",
     * "description": "Waking up on â€˜Aberrationâ€™, a derelict, malfunctioning ARK with an elaborate underground biome system, survivors face exotic new challenges unlike anything before: extreme radioactive sunlight and environmental hazards, ziplines, wingsuits, climbing gear, cave dwellings, charge-batteries, and far more, along with a stable of extraordinary new creatures await within the mysterious depths. But beware the â€˜Namelessâ€™: unrelenting, Element-infused humanoids which have evolved into vicious light-hating monstrosities! On Aberration, survivors will uncover the ultimate secrets of the ARKs, and discover what the future holds in store for those strong and clever enough to survive!",
     * "filePath": "DLC/Aberration/",
     * "logoFile": "logo.webp",
     * "folderFile": "folder.webp",
     * "backFolderFile": "backFolder.webp",
     * "lastUpdated": 1589728432355,
     * "totalConversion": false,
     * "gameId": "e4c35b4d-b81c-4e77-8d12-1ff33b4fe96a"
     */
    public static DlcEntity fromJSON(JsonNode node) throws IOException {
        return new ObjectMapper().treeToValue(node, DlcEntity.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLogoFile() {
        return logoFile;
    }

    public String getFolderFile() {
        return folderFile;
    }

    public String getBackFolderFile() {
        return backFolderFile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Boolean getTotalConversion() {
        return totalConversion;
    }

    public String getGameId() {
        return gameId;
    }
}