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

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import arc.resource.calculator.db.dao.dlc.DlcFolderDao;
import arc.resource.calculator.db.entity.primary.FolderEntity;

/**
 * Folder object for base game data (vanilla)
 * <p>
 * Folders for DLC game data should extend from this class
 */
@Entity(tableName = DlcFolderDao.tableName)
public class DlcFolderEntity extends FolderEntity {
    @NonNull
    private String dlcId;

    public DlcFolderEntity(@NonNull String uuid,
                           @NonNull String name,
                           @NonNull String gameId,
                           @NonNull String dlcId) {
        super(uuid, name, gameId);
        this.dlcId = dlcId;
    }

    public static DlcFolderEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcFolderEntity.class);
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}