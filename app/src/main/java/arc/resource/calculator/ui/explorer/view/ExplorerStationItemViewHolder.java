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
import androidx.fragment.app.FragmentActivity;

import arc.resource.calculator.model.ui.interactive.InteractiveItem;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;
import arc.resource.calculator.model.ui.view.StationItemViewHolder;
import arc.resource.calculator.ui.explorer.ExplorerViewModel;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

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
    protected void setupViewModel(FragmentActivity activity) {
        super.setupViewModel(activity);
        getViewModel().fetchStation(getItem().getSourceId()).observe(activity, entity -> {
            String imageFile = entity.getImageFile();
            loadImage(imageFile);
        });
    }

    @Override
    public void bind(FragmentActivity activity, InteractiveItem item, InteractiveViewModel viewModel) {
        super.bind(activity, item, viewModel);
        setImagePath(getViewModel().getGameEntity().getFilePath());
    }

    @Override
    protected void handleOnClickEvent() {
        getViewModel().handleOnClickEvent(getItem());
    }
}
