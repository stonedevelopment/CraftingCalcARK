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
    private long id;
    private long engramId;
    private int quantity;

    public Queue(long id, long engramId, int quantity) {
        this.id = id;
        this.engramId = engramId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEngramId() {
        return engramId;
    }

    public void setEngramId(long engramId) {
        this.engramId = engramId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;

        if (this.quantity > CraftingQueue.MAX) {
            this.quantity = CraftingQueue.MAX;
        }
    }

    public void decreaseQuantity(int amount) {
        if (amount <= quantity) {
            this.quantity -= amount;
        } else {
            this.quantity = 0;
        }
    }

    @Override
    public String toString() {
        return "id=" + id + ", engramId=" + engramId + ", quantity=" + quantity;
    }
}
