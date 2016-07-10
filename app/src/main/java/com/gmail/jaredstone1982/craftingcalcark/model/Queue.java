package com.gmail.jaredstone1982.craftingcalcark.model;

/**
 * Queue object that will hold database information for Crafting Queue
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

    public Queue() {
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
    }

    @Override
    public String toString() {
        return "id=" + id + ", engramId=" + engramId + ", quantity=" + quantity;
    }
}
