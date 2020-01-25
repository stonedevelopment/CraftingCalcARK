/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */
package arc.resource.calculator.model.resource;

import androidx.annotation.NonNull;

/**
 * QueueResource object that uses an external quantity for working with totals in the QueueRepository
 */
public class QueueResource extends CompositeResource {
    // External quantity used to compute the 'actual' quantity per Engram's Composition
    private int mQueueQuantity;

    public QueueResource(long id, String name, String folder, String file, int quantity, int queueQuantity) {
        super(id, name, folder, file, quantity);

        setQueueQuantity(queueQuantity);
    }

    public QueueResource(CompositeResource resource, int queueQuantity) {
        super(resource.getId(), resource.getName(), resource.getFolder(), resource.getFile(), resource.getQuantity());

        setQueueQuantity(queueQuantity);
    }

    private int getQueueQuantity() {
        return mQueueQuantity;
    }

    private void setQueueQuantity(int quantity) {
        mQueueQuantity = quantity;
    }

    public void increaseQueueQuantity(int amount) {
        mQueueQuantity += amount;
    }

    public int getProductOfQuantities() {
        return getQuantity() * getQueueQuantity();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + ", mQueueQuantity=" + getQueueQuantity();
    }
}

