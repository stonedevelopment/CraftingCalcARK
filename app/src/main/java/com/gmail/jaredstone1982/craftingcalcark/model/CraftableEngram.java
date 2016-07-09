package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Description: CraftableEngram object that extends Engram to include a quantity.
 * Usage: Store quantifiable Engram data to easily display objects pulled from Database
 * Used by: CraftableEngramListAdapter, QueueDataSource
 * Variables: id, imageId, name, quantity
 */
public class CraftableEngram extends Engram {
    // Total amount of Engram objects per request of User
    private int quantity;

    public CraftableEngram(long id, String name, int imageId, int quantity) {
        super(name, imageId);

        this.quantity = quantity;
        this.setId(id);
    }

    public CraftableEngram(long id, String name, int imageId) {
        super(name, imageId);

        this.quantity = 0;
        this.setId(id);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
}
