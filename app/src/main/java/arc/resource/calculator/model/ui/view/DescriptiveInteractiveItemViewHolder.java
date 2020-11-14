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

import com.google.android.material.textview.MaterialTextView;

import arc.resource.calculator.R;

public abstract class DescriptiveInteractiveItemViewHolder extends InteractiveItemViewHolder {
    public static final String TAG = DescriptiveInteractiveItemViewHolder.class.getSimpleName();

    private final MaterialTextView descriptionTextView;

    public DescriptiveInteractiveItemViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionTextView = itemView.findViewById(R.id.description);
    }

    protected void setDescriptionText(String description) {
        descriptionTextView.setText(description);
    }
}