package arc.resource.calculator.ui.favorites;

import arc.resource.calculator.model.ui.InteractiveLayoutManager;
import arc.resource.calculator.model.ui.InteractiveViewModel;
import arc.resource.calculator.ui.explorer.ExplorerFragment;

public class FavoritesLayoutManager extends InteractiveLayoutManager {
    public FavoritesLayoutManager(FavoritesFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected FavoritesViewModel getViewModel() {
        return (FavoritesViewModel) super.getViewModel();
    }
}