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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentActivity;

import arc.resource.calculator.R;

public class EngramExplorerItemViewHolder extends DescriptiveExplorerItemViewHolder {
    private final AppCompatImageButton favoriteButton;

    public EngramExplorerItemViewHolder(@NonNull View itemView) {
        super(itemView);
        favoriteButton = itemView.findViewById(R.id.favoriteButton);
    }

    @Override
    protected void setupViewModel(FragmentActivity activity) {
        super.setupViewModel(activity);

        getExplorerViewModel().getGameEntityLiveData().observe(activity, gameEntity -> {
            if (gameEntity.getFolderFile() == null)
                favoriteButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            else
                favoriteButton.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border_24));
        });

        getExplorerViewModel().fetchEngram(getExplorerItem().getSourceId()).observe(activity, engramEntity -> {
            if (engramEntity != null) {
                setDescriptionText(engramEntity.getDescription());
            } else {
                setDescriptionText("EngramEntity is null: " + getExplorerItem().getSourceId());
            }
        });
    }
}