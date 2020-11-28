package arc.resource.calculator.ui.explorer;

import arc.resource.calculator.model.ui.interactive.InteractiveLayoutManager;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

public class ExplorerLayoutManager extends InteractiveLayoutManager {
    public ExplorerLayoutManager(ExplorerFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected ExplorerViewModel getViewModel() {
        return (ExplorerViewModel) super.getViewModel();
    }
}