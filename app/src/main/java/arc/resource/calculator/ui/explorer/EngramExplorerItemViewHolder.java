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

package arc.resource.calculator.ui.explorer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textview.MaterialTextView;

import arc.resource.calculator.R;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

class EngramExplorerItemViewHolder extends ExplorerItemViewHolder {
    private final MaterialTextView mQuantity;

    EngramExplorerItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mQuantity = itemView.findViewById(R.id.title);
    }

    void bind(FragmentActivity activity, ExplorerItem explorerItem) {
        super.bind(activity, explorerItem);

        mQuantity.setText(explorerItem.getTitle());
    }
}