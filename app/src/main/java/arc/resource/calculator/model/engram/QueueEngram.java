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
package arc.resource.calculator.model.engram;

/**
 * Engram object that uses a quantity for working with totals in the CraftingQueue
 */
public class QueueEngram extends Engram {
    // Quantity used to keep track of how many Engrams in CraftingQueue
    private int quantity;

    public QueueEngram( long id, String name, String drawable, int quantity ) {
        super( id, name, drawable );

        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return super.toString() + ", quantity=" + quantity;
    }
}
