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

package arc.resource.calculator.ui.search;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import arc.resource.calculator.model.ui.InteractiveItem;
import arc.resource.calculator.model.ui.InteractiveViewModel;
import arc.resource.calculator.model.ui.view.InteractiveItemViewHolder;

class SearchItemViewHolder extends InteractiveItemViewHolder {
    public static final String TAG = SearchItemViewHolder.class.getSimpleName();

    SearchItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(FragmentActivity activity, InteractiveItem item, InteractiveViewModel viewModel) {
        super.bind(activity, item, viewModel);
    }
}
