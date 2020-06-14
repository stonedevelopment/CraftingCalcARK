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

package arc.resource.calculator.ui.explorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.primary.DirectoryEntity;
import arc.resource.calculator.ui.explorer.model.BackFolderExplorerItem;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class ExplorerItemAdapter extends RecyclerView.Adapter<ExplorerItemViewHolder> {
    private final LayoutInflater mInflater;
    private final FragmentActivity mActivity;
    private List<ExplorerItem> mItems;

    ExplorerItemAdapter(ExplorerFragment fragment) {
        super();
        mInflater = LayoutInflater.from(fragment.getContext());
        mActivity = fragment.requireActivity();
        mItems = new ArrayList<>();
    }

    private void setItems(List<ExplorerItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExplorerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == 2) {
            itemView = mInflater.inflate(R.layout.explorer_item_engram, parent, false);
            return new EngramExplorerItemViewHolder(itemView);
        } else if (viewType == 1) {
            itemView = mInflater.inflate(R.layout.explorer_item_folder, parent, false);
        } else if (viewType == 0) {
            itemView = mInflater.inflate(R.layout.explorer_item_station, parent, false);
        } else {
            itemView = mInflater.inflate(R.layout.explorer_item_folder, parent, false);
        }

        return new ExplorerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplorerItemViewHolder holder, int position) {
        holder.bind(mActivity, mItems.get(position));
    }

    private ExplorerItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    void mapDirectorySnapshot(DirectorySnapshot directorySnapshot) {
        List<ExplorerItem> items = new ArrayList<>();
        if (directorySnapshot.hasParent()) {
            items.add(BackFolderExplorerItem.fromExplorerItem(directorySnapshot.getParent()));
        }
        for (DirectoryEntity entity : directorySnapshot.getDirectory()) {
            items.add(ExplorerItem.fromDirectoryEntity(entity));
        }
        setItems(items);
    }
}