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
public class CraftableEngram extends Engram {
    // Total amount of Engram objects per request of User
    private int quantity;

    public CraftableEngram(long id, String name, int imageId, int quantity) {
        super(id, name, imageId);

        this.quantity = quantity;
    }

    public CraftableEngram(long id, String name, int imageId) {
        super(id, name, imageId);

        this.quantity = 0;
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

    @Override
    public String toString() {
        return super.toString() + ", quantity=" + quantity;
    }
}
