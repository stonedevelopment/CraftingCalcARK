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
 * Resource object that contains the mQuantity used to compose an Engram.Composition
 */
public class CompositeResource extends Resource {
    // Quantity taken on a per Engram basis, used in conjunction with QueueResource to compute an actual mQuantity later.
    private int mQuantity;

    public CompositeResource(long id, String name, String folder, String file, int quantity) {
        super(id, name, folder, file);

        setQuantity(quantity);
    }

    public CompositeResource(Resource resource, int quantity) {
        super(resource.getId(), resource.getName(), resource.getFolder(), resource.getFile());

        setQuantity(quantity);
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void increaseQuantity(int amount) {
        mQuantity += amount;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + ", mQuantity=" + getQuantity();
    }
}

