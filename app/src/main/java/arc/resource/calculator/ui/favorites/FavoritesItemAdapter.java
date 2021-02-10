package arc.resource.calculator.ui.favorites;

import java.util.List;

import arc.resource.calculator.model.ui.interactive.InteractiveItemAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;
import arc.resource.calculator.ui.favorites.model.FavoritesItem;

public class FavoritesItemAdapter extends InteractiveItemAdapter {
    protected FavoritesItemAdapter(FavoritesFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected FavoritesViewModel getViewModel() {
        return (FavoritesViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        super.setupViewModel();
        getViewModel().getFavoritesList().observe(getActivity(), this::handleFavoritesList);
    }

    @Override
    protected FavoritesItem getItem(int position) {
        return (FavoritesItem) super.getItem(position);
    }

    private void handleFavoritesList(List<FavoritesItem> favoritesItemList) {
        clearItemList();
        for (FavoritesItem favoritesItem : favoritesItemList) {
            addToItemList(favoritesItem);
        }
        notifyDataSetChanged();
    }
}