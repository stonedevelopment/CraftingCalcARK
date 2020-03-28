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

package arc.resource.calculator.ui.explorer;

enum ExplorerItemType {CraftingStation, Folder, Engram}

public class ExplorerItem {
    private final long id;
    private final String title;
    private final String imagePath;
    private final ExplorerItemType itemType;

    ExplorerItem(long id, String title, String imagePath, ExplorerItemType itemType) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.itemType = itemType;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ExplorerItemType getItemType() {
        return itemType;
    }
}

class StationExplorerItem extends ExplorerItem {

    public StationExplorerItem(long id, String title, String imagePath) {
        super(id, title, imagePath, ExplorerItemType.CraftingStation);
    }
}

class ChildExplorerItem extends ExplorerItem {

    private final ExplorerItem parentExplorerItem;

    ChildExplorerItem(long id, String title, String imagePath, ExplorerItemType itemType, ExplorerItem parentExplorerItem) {
        super(id, title, imagePath, itemType);
        this.parentExplorerItem = parentExplorerItem;
    }

    public ExplorerItem getParentExplorerItem() {
        return parentExplorerItem;
    }
}

class FolderExplorerItem extends ChildExplorerItem {

    public FolderExplorerItem(long id, String title, String imagePath, ExplorerItem parentExplorerItem) {
        super(id, title, imagePath, ExplorerItemType.Folder, parentExplorerItem);
    }
}

class EngramExplorerItem extends ChildExplorerItem {
    private int quantity;

    public EngramExplorerItem(long id, String title, String imagePath, ExplorerItem parentExplorerItem) {
        super(id, title, imagePath, ExplorerItemType.Engram, parentExplorerItem);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}