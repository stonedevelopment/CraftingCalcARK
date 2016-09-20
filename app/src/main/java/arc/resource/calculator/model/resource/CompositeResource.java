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
package arc.resource.calculator.model.resource;

/**
 * Resource object that contains the quantity used to compose an Engram.Composition
 */
public class CompositeResource extends Resource {
    // Quantity taken on a per Engram basis, used in conjunction with QueueResource to compute an actual quantity later.
    private int quantity;

    public CompositeResource( long id, String name, String drawable, int quantity ) {
        super( id, name, drawable );

        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return super.toString() + ", quantity=" + getQuantity();
    }
}

