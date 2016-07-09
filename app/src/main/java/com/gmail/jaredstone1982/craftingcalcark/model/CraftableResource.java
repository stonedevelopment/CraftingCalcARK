package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Establishes Resource object that will hold the ingredients for which to fill Engram Recipes
 */
public class CraftableResource extends Resource {
    // ID created by database
    private long id;

    // Quantity value per Engram
    private int quantity;

    public CraftableResource(long id, String name, int imageId, int quantity) {
        super(name, imageId);

        this.id = id;
        this.quantity = quantity;
    }

    public CraftableResource(long id, String name, int imageId) {
        super(name, imageId);

        this.id = id;
        this.quantity = 0;
    }

    public CraftableResource (String name, int imageId){
        super(name, imageId);
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
}

