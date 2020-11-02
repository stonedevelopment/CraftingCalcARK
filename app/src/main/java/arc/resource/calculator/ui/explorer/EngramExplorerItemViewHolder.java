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
import arc.resource.calculator.ui.explorer.model.EngramExplorerItem;

class EngramExplorerItemViewHolder extends ExplorerItemViewHolder {
//    private final MaterialTextView quantityView;

    EngramExplorerItemViewHolder(@NonNull View itemView, String filePath) {
        super(itemView, filePath);
//        quantityView = itemView.findViewById(R.id.quantity);
    }

    void bind(FragmentActivity activity, EngramExplorerItem explorerItem) {
        super.bind(activity, explorerItem);

//        quantityView.setText(explorerItem.getQuantity());
    }
}