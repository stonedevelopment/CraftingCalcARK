/*
 * Copyright (c) 2020 Jared Stone
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

package arc.resource.calculator.ui.search.view;

import android.view.View;

import androidx.annotation.NonNull;

import arc.resource.calculator.ui.search.model.FolderSearchItem;

public class FolderSearchItemViewHolder extends SearchItemViewHolder {
    public static final String TAG = FolderSearchItemViewHolder.class.getSimpleName();

    public FolderSearchItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public FolderSearchItem getItem() {
        return (FolderSearchItem) super.getItem();
    }
}