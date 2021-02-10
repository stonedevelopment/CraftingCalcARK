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
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveItemAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveItemViewHolder;
import arc.resource.calculator.ui.explorer.model.BackFolderExplorerItem;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;
import arc.resource.calculator.ui.explorer.view.ExplorerBackFolderItemViewHolder;
import arc.resource.calculator.ui.explorer.view.ExplorerEngramItemViewHolder;
import arc.resource.calculator.ui.explorer.view.ExplorerFolderItemViewHolder;
import arc.resource.calculator.ui.explorer.view.ExplorerStationItemViewHolder;

import static arc.resource.calculator.util.Constants.cBackFolderViewType;

public class ExplorerItemAdapter extends InteractiveItemAdapter {

    protected ExplorerItemAdapter(ExplorerFragment fragment, ExplorerViewModel viewModel) {
        super(fragment, viewModel);
    }

    @NonNull
    @Override
    public InteractiveItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == cBackFolderViewType) {
            return createBackFolderItemViewHolder(getLayoutInflater()
                    .inflate(R.layout.columnized_item_back_folder, parent, false));
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected ExplorerEngramItemViewHolder createEngramItemViewHolder(View itemView) {
        return new ExplorerEngramItemViewHolder(itemView);
    }

    @Override
    protected ExplorerFolderItemViewHolder createFolderItemViewHolder(View itemView) {
        return new ExplorerFolderItemViewHolder(itemView);
    }

    @Override
    protected ExplorerStationItemViewHolder createStationItemViewHolder(View itemView) {
        return new ExplorerStationItemViewHolder(itemView);
    }

    protected ExplorerBackFolderItemViewHolder createBackFolderItemViewHolder(View itemView) {
        return new ExplorerBackFolderItemViewHolder(itemView);
    }

    @Override
    public ExplorerViewModel getViewModel() {
        return (ExplorerViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        super.setupViewModel();
        getViewModel().getDirectorySnapshot().observe(getActivity(), this::mapDirectorySnapshot);
    }

    @Override
    protected ExplorerItem getItem(int position) {
        return (ExplorerItem) getItemList().get(position);
    }

    private void mapDirectorySnapshot(DirectorySnapshot directorySnapshot) {
        clearItemList();
        if (directorySnapshot.hasParent()) {
            addToItemList(BackFolderExplorerItem.fromExplorerItem(directorySnapshot.getParent()));
        }
        for (DirectoryItemEntity entity : directorySnapshot.getDirectory()) {
            addToItemList(ExplorerItem.fromDirectoryEntity(entity));
        }
        notifyDataSetChanged();
    }
}