package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.EngramEntity;

import static arc.resource.calculator.util.Constants.cEngramViewType;

public class EngramSearchItem extends SearchItem {
    private int quantity;

    EngramSearchItem(String uuid, String title, String description, String imageFile, int viewType, String gameId, int quantity) {
        super(uuid, title, description, imageFile, viewType, gameId);
        this.quantity = quantity;
    }


    public static EngramSearchItem fromEntity(EngramEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        String imageFile = entity.getImageFile();
        String gameId = entity.getGameId();
        String description = entity.getDescription();
        return new EngramSearchItem(uuid, title, description, imageFile, cEngramViewType, gameId, 0);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}