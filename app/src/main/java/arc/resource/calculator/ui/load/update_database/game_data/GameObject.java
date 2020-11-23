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

package arc.resource.calculator.ui.load.update_database.game_data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

@Entity
public class GameObject {
    @PrimaryKey
    private final String uuid;
    private final String name;
    private final String description;
    private final String filePath;
    private final String logoFile;
    private final String folderFile;
    private final String backFolderFile;
    private final Date lastUpdated;

    private GameObject(String uuid, String name, String description, String filePath, String logoFile, String folderFile, String backFolderFile, Date lastUpdated) {
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
     * "logoFile": "dlc_logo.webp",
     * "folderFile": "folder.webp",
     * "backFolderFile": "backFolder.webp",
     * "lastUpdated": 1589725368121,
     */
    public static GameObject fromJSON(JsonNode node) throws IOException {
        return new ObjectMapper().treeToValue(node, GameObject.class);
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
}