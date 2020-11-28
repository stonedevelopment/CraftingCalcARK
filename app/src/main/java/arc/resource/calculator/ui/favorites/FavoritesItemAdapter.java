package arc.resource.calculator.ui.favorites;

import arc.resource.calculator.model.ui.interactive.InteractiveAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

public class FavoritesItemAdapter extends InteractiveAdapter {
    protected FavoritesItemAdapter(FavoritesFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected FavoritesViewModel getViewModel() {
        return (FavoritesViewModel) super.getViewModel();
    }
}