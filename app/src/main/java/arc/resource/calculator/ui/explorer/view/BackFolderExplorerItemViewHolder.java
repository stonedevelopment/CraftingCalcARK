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
        // do nothing
    }

    @Override
    public void bind(FragmentActivity activity, ExplorerItem explorerItem) {
        super.bind(activity, explorerItem);
        getTitleTextView().setText(activity.getString(R.string.explorer_item_back_folder_title));
        setDescriptionText(activity.getString(R.string.explorer_item_back_folder_description_format, explorerItem.getTitle()));
    }
}