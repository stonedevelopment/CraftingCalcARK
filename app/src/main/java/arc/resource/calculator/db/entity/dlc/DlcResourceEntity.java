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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.dlc.DlcResourceDao;
import arc.resource.calculator.db.entity.primary.ResourceEntity;

import static arc.resource.calculator.util.Constants.cDescription;
import static arc.resource.calculator.util.Constants.cDlcId;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cUuid;

/**
 * Resource object for Primary game data
 * <p>
 * Resources for DLC game data should extend from this class
 */
@Entity(tableName = DlcResourceDao.tableName)
public class DlcResourceEntity extends ResourceEntity {
    @NonNull
    private String dlcId;

    @JsonCreator
    public DlcResourceEntity(@JsonProperty(cUuid) @NonNull String uuid,
                             @JsonProperty(cName) @NonNull String name,
                             @JsonProperty(cDescription) @NonNull String description,
                             @JsonProperty(cImageFile) @NonNull String imageFile,
                             @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                             @JsonProperty(cGameId) @NonNull String gameId,
                             @JsonProperty(cDlcId) @NonNull String dlcId) {
        super(uuid, name, description, imageFile, lastUpdated, gameId);
        this.dlcId = dlcId;
    }

    public static DlcResourceEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcResourceEntity.class);
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}