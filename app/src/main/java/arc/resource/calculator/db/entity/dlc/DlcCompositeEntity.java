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

import arc.resource.calculator.db.dao.dlc.DlcCompositeDao;
import arc.resource.calculator.db.entity.primary.CompositeEntity;

import static arc.resource.calculator.util.Constants.cCompositionId;
import static arc.resource.calculator.util.Constants.cDlcId;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cIsEngram;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cQuantity;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;

@Entity(tableName = DlcCompositeDao.tableName)
public class DlcCompositeEntity extends CompositeEntity {
    @NonNull
    private String dlcId;

    @JsonCreator
    public DlcCompositeEntity(@JsonProperty(cUuid) @NonNull String uuid,
                              @JsonProperty(cName) @NonNull String name,
                              @JsonProperty(cImageFile) @NonNull String imageFile,
                              @JsonProperty(cQuantity) int quantity,
                              @JsonProperty(cSourceId) @NonNull String sourceId,
                              @JsonProperty(cIsEngram) boolean isEngram,
                              @JsonProperty(cCompositionId) @NonNull String compositionId,
                              @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                              @JsonProperty(cGameId) @NonNull String gameId,
                              @JsonProperty(cDlcId) @NonNull String dlcId) {
        super(uuid, name, imageFile, quantity, sourceId, isEngram, compositionId, lastUpdated, gameId);
        this.dlcId = dlcId;
    }

    public static DlcCompositeEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcCompositeEntity.class);
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}