package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Description: Craftable Resource object
 * Usage: Establishes Resource object that will hold the ingredients for which to fill Engram Recipes
 * Used by: CraftableEngram, DetailEngram
 * Variables: quantity
 * <p/>
 * Last Edit: Removed constructors with just name and imageId, added Description Comment
 */
public class CraftableResource extends Resource {
    private int quantity;

    public CraftableResource(Resource resource, int quantity) {
        super(resource.getId(), resource.getName(), resource.getImageId());

        this.quantity = quantity;
    }

    public CraftableResource(Resource resource) {
        super(resource.getId(), resource.getName(), resource.getImageId());

        this.quantity = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
}

