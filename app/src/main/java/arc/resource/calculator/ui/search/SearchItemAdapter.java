package arc.resource.calculator.ui.search;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.interactive.InteractiveItemAdapter;
import arc.resource.calculator.model.ui.interactive.InteractiveItemViewHolder;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;
import arc.resource.calculator.ui.search.model.SearchItem;
import arc.resource.calculator.ui.search.view.EngramSearchItemViewHolder;

/**
 * Base class for SearchItem types that get fed from SearchItemHeader
 */
public class SearchItemAdapter extends InteractiveItemAdapter {
    protected SearchItemAdapter(Fragment fragment, InteractiveViewModel viewModel) {
        super(fragment, viewModel);
    }

    @NonNull
    @Override
    public InteractiveItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getLayoutInflater().inflate(R.layout.search_item_engram, parent, false);
        return new EngramSearchItemViewHolder(itemView);
    }

    @Override
    protected SearchViewModel getViewModel() {
        return (SearchViewModel) super.getViewModel();
    }

    @Override
    protected void setupViewModel() {
        super.setupViewModel();
        getViewModel().getSearchLiveData().observe(getActivity(), this::handleSearchResults);
    }

    @Override
    protected SearchItem getItem(int position) {
        return (SearchItem) super.getItem(position);
    }

    public void handleSearchResults(List<SearchItem> searchItems) {
        clearItemList();
        for (SearchItem searchItem : searchItems) {
            addToItemList(searchItem);
        }
        notifyDataSetChanged();
    }
}