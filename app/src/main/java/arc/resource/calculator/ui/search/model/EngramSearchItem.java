package arc.resource.calculator.ui.search.model;

import arc.resource.calculator.db.entity.primary.EngramEntity;

import static arc.resource.calculator.util.Constants.cEngramViewType;

public class EngramSearchItem extends SearchItem {
    private int quantity;

    EngramSearchItem(String uuid, String title, String imageFile, int viewType, String gameId, int quantity) {
        super(uuid, title, imageFile, viewType, gameId);
        this.quantity = quantity;
    }

    public static EngramSearchItem fromEntity(EngramEntity entity) {
        String uuid = entity.getUuid();
        String title = entity.getName();
        String imageFile = entity.getImageFile();
        String gameId = entity.getGameId();
        return new EngramSearchItem(uuid, title, imageFile, cEngramViewType, gameId, 0);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}