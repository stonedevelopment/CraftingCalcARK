package ark.resource.calculator.model;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: ARK:Resource Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
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

