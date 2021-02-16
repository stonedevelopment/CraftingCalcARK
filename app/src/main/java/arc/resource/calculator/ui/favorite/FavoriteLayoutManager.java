package arc.resource.calculator.ui.favorite;

import arc.resource.calculator.model.ui.interactive.InteractiveLayoutManager;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

public class FavoriteLayoutManager extends InteractiveLayoutManager {
    public FavoriteLayoutManager(FavoriteFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected FavoriteViewModel getViewModel() {
        return (FavoriteViewModel) super.getViewModel();
    }
}