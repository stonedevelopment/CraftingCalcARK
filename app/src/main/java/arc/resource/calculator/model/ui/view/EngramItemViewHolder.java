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

package arc.resource.calculator.model.ui.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.interactive.InteractiveItem;
import arc.resource.calculator.model.ui.interactive.InteractiveItemViewHolder;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;
import arc.resource.calculator.ui.favorite.FavoriteViewModel;

public class EngramItemViewHolder extends InteractiveItemViewHolder {
    private final AppCompatImageButton favoriteButton;

    private FavoriteViewModel favoriteViewModel;
    private boolean isFavorite = false;

    public EngramItemViewHolder(@NonNull View itemView) {
        super(itemView);
        favoriteButton = itemView.findViewById(R.id.favoriteButton);
    }

    @Override
    public void bind(FragmentActivity activity, InteractiveItem item, InteractiveViewModel viewModel) {
        super.bind(activity, item, viewModel);
        setFavoriteViewModel(activity);
    }

    protected FavoriteViewModel getFavoriteViewModel() {
        return favoriteViewModel;
    }

    protected void setFavoriteViewModel(FragmentActivity activity) {
        favoriteViewModel = new ViewModelProvider(activity).get(FavoriteViewModel.class);
    }

    protected AppCompatImageButton getFavoriteButton() {
        return favoriteButton;
    }

    private boolean isFavorite() {
        return isFavorite;
    }

    private void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    protected void setupFavoriteButton() {
        //  I do nothing, for now.
    }

    protected void updateFavoriteButton(boolean isFavorite) {
        setIsFavorite(isFavorite);
        favoriteButton.setImageResource(getFavoriteImageResource());
    }

    protected void handleFavoriteButtonClick() {
        toggleFavoriteButton();
    }

    private int getFavoriteImageResource() {
        int resIdFavorite = R.drawable.ic_favorite_black_24dp;
        int resIdNotFavorite = R.drawable.ic_favorite_border_24;
        return isFavorite() ? resIdFavorite : resIdNotFavorite;
    }

    private void toggleFavoriteButton() {
        updateFavoriteButton(!isFavorite());
    }
}