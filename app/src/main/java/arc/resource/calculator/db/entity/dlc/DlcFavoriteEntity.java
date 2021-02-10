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

import java.util.Date;

import arc.resource.calculator.db.dao.dlc.DlcFavoriteDao;
import arc.resource.calculator.db.entity.primary.FavoriteEntity;

import static arc.resource.calculator.util.Constants.cDlcId;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;
import static arc.resource.calculator.util.Constants.cViewType;

/**
 * Favorite object for dlc game data
 */
@Entity(tableName = DlcFavoriteDao.tableName)
public class DlcFavoriteEntity extends FavoriteEntity {
    @NonNull
    private String dlcId;

    @JsonCreator
    public DlcFavoriteEntity(@JsonProperty(cUuid) @NonNull String uuid,
                             @JsonProperty(cName) @NonNull String name,
                             @JsonProperty(cViewType) int viewType,
                             @JsonProperty(cSourceId) @NonNull String sourceId,
                             @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                             @JsonProperty(cGameId) @NonNull String gameId,
                             @JsonProperty(cDlcId) @NonNull String dlcId) {
        super(uuid, name, viewType, sourceId, lastUpdated, gameId);
        this.dlcId = gameId;
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}