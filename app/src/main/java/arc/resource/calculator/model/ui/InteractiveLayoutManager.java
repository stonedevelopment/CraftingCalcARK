package arc.resource.calculator.model.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

public class InteractiveLayoutManager extends GridLayoutManager {
    private final static int LINEAR_SPAN_COUNT = 1;
    private final static int GRID_SPAN_COUNT = 3;

    private final InteractiveViewModel viewModel;

    public InteractiveLayoutManager(Fragment fragment, InteractiveViewModel viewModel) {
        super(fragment.getContext(), LINEAR_SPAN_COUNT);
        this.viewModel = viewModel;

        setupViewModel(fragment.getActivity());
    }

    protected InteractiveViewModel getViewModel() {
        return viewModel;
    }

    // TODO: 11/14/2020 Listen for changes from Grid to List per User
    protected void setupViewModel(FragmentActivity activity) {
//        viewModel.getDirectorySnapshot().observe(activity, directorySnapshot -> {
//            if (directorySnapshot.hasParent()) {
//                setSpanCount(SPAN_COUNT_CHILD);
//            } else {
//                setSpanCount(SPAN_COUNT_ROOT);
//            }
//        });
    }
}