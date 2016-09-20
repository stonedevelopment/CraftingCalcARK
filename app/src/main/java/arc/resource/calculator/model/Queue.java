package arc.resource.calculator.model;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class Queue {
    // Primary Key created by Database used to track its Row
    private long id;

    // Foreign Key that references an Engram's Primary Key created by Database to track its row location
    private long engramId;

    // Quantity adjusted per User
    private int quantity;

    public Queue(long id, long engramId, int quantity) {
        this.id = id;
        this.engramId = engramId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public long getEngramId() {
        return engramId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "id=" + id + ", engramId=" + engramId + ", quantity=" + quantity;
    }
}
