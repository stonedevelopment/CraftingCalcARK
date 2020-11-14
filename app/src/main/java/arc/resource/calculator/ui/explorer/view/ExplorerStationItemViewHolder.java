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

package arc.resource.calculator.ui.explorer.view;

import android.view.View;

import androidx.annotation.NonNull;

import arc.resource.calculator.model.ui.view.StationItemViewHolder;
import arc.resource.calculator.ui.explorer.ExplorerViewModel;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;
import arc.resource.calculator.ui.explorer.model.StationExplorerItem;

public class ExplorerStationItemViewHolder extends StationItemViewHolder {
    public ExplorerStationItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public ExplorerItem getItem() {
        return (ExplorerItem) super.getItem();
    }

    @Override
    public ExplorerViewModel getViewModel() {
        return (ExplorerViewModel) super.getViewModel();
    }

    @Override
    protected void handleOnClickEvent() {
        getViewModel().handleOnClickEvent(getItem());
    }
}
