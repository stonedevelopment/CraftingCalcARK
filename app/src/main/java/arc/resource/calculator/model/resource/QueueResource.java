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
 * QueueResource object that uses an external quantity for working with totals in the CraftingQueue
 */
public class QueueResource extends CompositeResource {
    // External quantity used to compute the 'actual' quantity per Engram's Composition
    private int queueQuantity;

    public QueueResource( long id, String name, String drawable, int quantity, int queueQuantity ) {
        super( id, name, drawable, quantity );

        this.queueQuantity = queueQuantity;
    }

    public int getQueueQuantity() {
        return queueQuantity;
    }

    public int getProductOfQuantities() {
        return getQuantity() * getQueueQuantity();
    }

    @Override
    public String toString() {
        return super.toString() + ", queueQuantity=" + getQueueQuantity();
    }
}

