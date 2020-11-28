package arc.resource.calculator.ui.favorites;

import arc.resource.calculator.model.ui.interactive.InteractiveLayoutManager;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

public class FavoritesLayoutManager extends InteractiveLayoutManager {
    public FavoritesLayoutManager(FavoritesFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected FavoritesViewModel getViewModel() {
        return (FavoritesViewModel) super.getViewModel();
    }
}