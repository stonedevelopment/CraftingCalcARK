package arc.resource.calculator.ui.explorer;

import arc.resource.calculator.model.ui.InteractiveLayoutManager;
import arc.resource.calculator.model.ui.InteractiveViewModel;

public class ExplorerLayoutManager extends InteractiveLayoutManager {
    public ExplorerLayoutManager(ExplorerFragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @Override
    protected ExplorerViewModel getViewModel() {
        return (ExplorerViewModel) super.getViewModel();
    }
}