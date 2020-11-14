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

import arc.resource.calculator.R;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class BackFolderExplorerItemViewHolder extends DescriptiveExplorerItemViewHolder {
    public BackFolderExplorerItemViewHolder(@NonNull View itemView) {
        super(itemView);
//        int imagePath = R.drawable.ic_baseline_reply_256;
//        getPicasso().load(imagePath).into(getThumbnailImageView());
    }

    @Override
    protected void setupViewModel(FragmentActivity activity) {
        String parentTitle = "";
        String parentOfParentTitle = "";
        String descriptionText = "";

        if (getExplorerViewModel().hasParentExplorerItem()) {
            parentTitle = getExplorerViewModel().getParentExplorerItem().getTitle();

            if (getExplorerViewModel().hasParentOfParentExplorerItem()) {
                parentOfParentTitle = getExplorerViewModel().getParentOfParentExplorerItem().getTitle();
                descriptionText = activity.getString(R.string.explorer_item_back_folder_back_to_2_string_format,
                        parentOfParentTitle, parentTitle);
            } else {
                descriptionText = activity.getString(R.string.explorer_item_back_folder_back_to_1_string_format,
                        parentTitle);
            }

        } else {
            // does not have parent
            descriptionText = activity.getString(R.string.explorer_item_back_folder_back_to_crafting_stations);
        }

        setDescriptionText(descriptionText);
    }

    @Override
    public void bind(FragmentActivity activity, ExplorerItem explorerItem) {
        super.bind(activity, explorerItem);
    }
}