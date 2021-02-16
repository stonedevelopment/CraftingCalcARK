package arc.resource.calculator.ui.favorite;

import java.util.List;

import arc.resource.calculator.model.ui.interactive.InteractiveItemAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;
import arc.resource.calculator.ui.favorite.model.FavoriteItem;

public class FavoriteItemAdapter extends InteractiveItemAdapter {
    protected FavoriteItemAdapter(FavoriteFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected FavoriteViewModel getViewModel() {
        return (FavoriteViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        super.setupViewModel();
        getViewModel().getFavoritesListLiveData().observe(getActivity(), this::handleFavoritesList);
    }

    @Override
    protected FavoriteItem getItem(int position) {
        return (FavoriteItem) super.getItem(position);
    }

    private void handleFavoritesList(List<FavoriteItem> favoriteItemList) {
        clearItemList();
        for (FavoriteItem favoriteItem : favoriteItemList) {
            addToItemList(favoriteItem);
        }
        notifyDataSetChanged();
    }
}