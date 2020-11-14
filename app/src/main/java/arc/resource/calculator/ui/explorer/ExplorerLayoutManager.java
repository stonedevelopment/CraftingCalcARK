package arc.resource.calculator.ui.explorer;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

public class ExplorerLayoutManager extends GridLayoutManager {
    private final static int SPAN_COUNT_ROOT = 1;
    private final static int SPAN_COUNT_CHILD = 3;
    private final ExplorerViewModel viewModel;

    public ExplorerLayoutManager(ExplorerFragment fragment, ExplorerViewModel viewModel) {
        super(fragment.getContext(), SPAN_COUNT_ROOT);
        this.viewModel = viewModel;

        setupViewModel(fragment.getActivity());
    }

    // TODO: 11/14/2020 Listen for changes from Grid to List per User
    private void setupViewModel(FragmentActivity activity) {
//        viewModel.getDirectorySnapshot().observe(activity, directorySnapshot -> {
//            if (directorySnapshot.hasParent()) {
//                setSpanCount(SPAN_COUNT_CHILD);
//            } else {
//                setSpanCount(SPAN_COUNT_ROOT);
//            }
//        });
    }
}