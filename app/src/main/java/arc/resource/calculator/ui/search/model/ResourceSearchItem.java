package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.ResourceEntity;

import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cResourceViewType;

public class ResourceSearchItem extends SearchItem {
    ResourceSearchItem(String uuid, String name, String description, String imageFile, int viewType, String gameId) {
        super(uuid, name, description, imageFile, viewType, gameId);
    }

    public static ResourceSearchItem fromEntity(ResourceEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        String imageFile = entity.getImageFile();
        String gameId = entity.getGameId();
        String description = entity.getDescription();
        return new ResourceSearchItem(uuid, title, description, imageFile, cResourceViewType, gameId);
    }
}