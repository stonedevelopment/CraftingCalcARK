package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.FolderEntity;

import static arc.resource.calculator.util.Constants.cFolderViewType;

public class FolderSearchItem extends SearchItem {
    FolderSearchItem(String uuid, String name, String description, String imageFile, int viewType, String gameId) {
        super(uuid, name, description, imageFile, viewType, gameId);
    }

    public static FolderSearchItem fromEntity(FolderEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        String gameId = entity.getGameId();
        String description = "";
        return new FolderSearchItem(uuid, title, description, null, cFolderViewType, gameId);
    }
}