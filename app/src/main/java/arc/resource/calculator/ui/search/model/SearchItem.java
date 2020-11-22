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

package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;
import arc.resource.calculator.model.ui.InteractiveItem;

public class SearchItem extends InteractiveItem {
    protected SearchItem(String uuid, String title, String description, String imageFile, int viewType, String gameId) {
        super(uuid, title, description, imageFile, viewType, gameId);
    }

    public static EngramSearchItem fromEngramEntity(EngramEntity entity) {
        return EngramSearchItem.fromEntity(entity);
    }

    public static ResourceSearchItem fromResourceEntity(ResourceEntity entity) {
        return ResourceSearchItem.fromEntity(entity);
    }

    public static StationSearchItem fromStationEntity(StationEntity entity) {
        return StationSearchItem.fromEntity(entity);
    }

    public static FolderSearchItem fromFolderEntity(FolderEntity entity, String imageFile) {
        return FolderSearchItem.fromEntity(entity, imageFile);
    }
}