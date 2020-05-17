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

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

import arc.resource.calculator.db.dao.primary.FolderDao;

/**
 * Folder object for base game data (vanilla)
 * <p>
 * Folders for DLC game data should extend from this class
 */
@Entity(tableName = FolderDao.tableName)
public class FolderEntity {
    @PrimaryKey
    private final String uuid;
    private final String name;

    public FolderEntity(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * "uuid": "639795e0-5dbe-49c9-9264-95e202af1aab",
     * "name": "Misc"
     */
    public static FolderEntity fromJSON(JSONObject jsonObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonObject.toString(), FolderEntity.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FolderEntity)) return false;

        FolderEntity entity = (FolderEntity) obj;
        return uuid.equals(entity.getUuid()) &&
                name.equals(entity.getName());
    }
}