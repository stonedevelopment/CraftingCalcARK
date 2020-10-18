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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.GameDao;

import static arc.resource.calculator.util.JSONUtil.cBackFolderFile;
import static arc.resource.calculator.util.JSONUtil.cDescription;
import static arc.resource.calculator.util.JSONUtil.cFilePath;
import static arc.resource.calculator.util.JSONUtil.cFolderFile;
import static arc.resource.calculator.util.JSONUtil.cLastUpdated;
import static arc.resource.calculator.util.JSONUtil.cLogoFile;
import static arc.resource.calculator.util.JSONUtil.cName;
import static arc.resource.calculator.util.JSONUtil.cUuid;

@Entity(tableName = GameDao.tableName)
public class GameDlcEntity extends GameEntity {
    @NonNull
    @PrimaryKey
    private String uuid;

    private String gameId;

    public GameDlcEntity(@NonNull String uuid,
                         String name,
                         String description,
                         String filePath,
                         String logoFile,
                         String folderFile,
                         String backFolderFile,
                         Date lastUpdated,
                         String gameId) {
        super(uuid, name, description, filePath, logoFile, folderFile, backFolderFile, lastUpdated);
        this.gameId = gameId;
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
    public static GameDlcEntity fromJSON(JsonNode node) {
        return new ObjectMapper().convertValue(node, GameDlcEntity.class);
    }
}