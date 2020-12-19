/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.ui.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.model.ui.interactive.InteractiveItem;
import arc.resource.calculator.model.ui.interactive.InteractiveItemViewHolder;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;
import arc.resource.calculator.model.ui.view.EngramItemViewHolder;
import arc.resource.calculator.model.ui.view.FolderItemViewHolder;
import arc.resource.calculator.model.ui.view.StationItemViewHolder;

import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class SearchAdapter extends RecyclerView.Adapter<InteractiveItemViewHolder> {
    public static final String TAG = SearchAdapter.class.getCanonicalName();

    private final InteractiveViewModel viewModel;
    private final LayoutInflater layoutInflater;
    private final FragmentActivity fragmentActivity;
    private final List<InteractiveItem> itemList = new ArrayList<>();

    protected SearchAdapter(Fragment fragment, InteractiveViewModel viewModel) {
        super();
        this.layoutInflater = LayoutInflater.from(fragment.getContext());
        this.fragmentActivity = fragment.requireActivity();
        this.viewModel = viewModel;

        setupViewModel();
    }

    protected void setupViewModel() {
        // do nothing
    }

    @NonNull
    @Override
    public InteractiveItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " + viewType);

        View itemView;
        if (viewType == cEngramViewType) {
            itemView = layoutInflater.inflate(R.layout.columnized_item_engram, parent, false);
            return createEngramItemViewHolder(itemView);
        } else if (viewType == cFolderViewType) {
            itemView = layoutInflater.inflate(R.layout.columnized_item_folder, parent, false);
            return createFolderItemViewHolder(itemView);
        } else if (viewType == cStationViewType) {
            itemView = layoutInflater.inflate(R.layout.columnized_item_station, parent, false);
            return createStationItemViewHolder(itemView);
        } else {
            itemView = layoutInflater.inflate(R.layout.columnized_item_error, parent, false);
        }

        return new InteractiveItemViewHolder(itemView);
    }

    protected EngramItemViewHolder createEngramItemViewHolder(View itemView) {
        return new EngramItemViewHolder(itemView);
    }

    protected FolderItemViewHolder createFolderItemViewHolder(View itemView) {
        return new FolderItemViewHolder(itemView);
    }

    protected StationItemViewHolder createStationItemViewHolder(View itemView) {
        return new StationItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractiveItemViewHolder holder, int position) {
        holder.bind(fragmentActivity, getItem(position), getViewModel());
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    protected InteractiveViewModel getViewModel() {
        return viewModel;
    }

    protected FragmentActivity getActivity() {
        return fragmentActivity;
    }

    protected LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    protected InteractiveItem getItem(int position) {
        return itemList.get(position);
    }

    protected List<InteractiveItem> getItemList() {
        return itemList;
    }

    protected void clearItemList() {
        itemList.clear();
    }

    protected void addToItemList(InteractiveItem item) {
        itemList.add(item);
    }

    protected void addToItemList(List<InteractiveItem> itemList) {
        this.itemList.addAll(itemList);
    }
}