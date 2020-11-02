package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.StationEntity;

import static arc.resource.calculator.util.Constants.cStationViewType;

public class StationSearchItem extends SearchItem {
    StationSearchItem(String uuid, String name, String description, String imageFile, int viewType, String gameId) {
        super(uuid, name, description, imageFile, viewType, gameId);
    }

    public static StationSearchItem fromEntity(StationEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        String imageFile = entity.getImageFile();
        String gameId = entity.getGameId();
        String description = "";
        return new StationSearchItem(uuid, title, description, imageFile, cStationViewType, gameId);
    }
}