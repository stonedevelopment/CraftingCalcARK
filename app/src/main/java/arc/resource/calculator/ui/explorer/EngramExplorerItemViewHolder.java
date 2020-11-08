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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentActivity;

import arc.resource.calculator.R;
import arc.resource.calculator.ui.explorer.model.EngramExplorerItem;

class EngramExplorerItemViewHolder extends ExplorerItemViewHolder {
    private final AppCompatImageButton favoriteButton;

    EngramExplorerItemViewHolder(@NonNull View itemView) {
        super(itemView);
        favoriteButton = itemView.findViewById(R.id.favoriteButton);
    }

    void bind(FragmentActivity activity, EngramExplorerItem explorerItem, ExplorerViewModel explorerViewModel) {
        super.bind(activity, explorerItem, explorerViewModel);

        explorerViewModel.getGameEntityLiveData().observe(activity, gameEntity -> {
            if (gameEntity.getFolderFile() == null)
                favoriteButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            else
                favoriteButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border_24));
        });
    }
}