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

package arc.resource.calculator.model.ui.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.interactive.InteractiveItemViewHolder;

public class EngramItemViewHolder extends InteractiveItemViewHolder {
    private final AppCompatImageButton favoriteButton;

    public EngramItemViewHolder(@NonNull View itemView) {
        super(itemView);
        favoriteButton = itemView.findViewById(R.id.favoriteButton);
    }
}