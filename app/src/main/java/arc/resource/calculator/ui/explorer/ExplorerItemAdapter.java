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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.primary.DirectoryEntity;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class ExplorerItemAdapter extends RecyclerView.Adapter<ExplorerItemViewHolder> {
    private final LayoutInflater mInflater;
    private List<ExplorerItem> mItems;

    ExplorerItemAdapter(Context context) {
        super();
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList<>();
    }

    private Context getContext() {
        return mInflater.getContext();
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
            itemView = mInflater.inflate(R.layout.explorer_item_engram, parent);
            return new EngramExplorerItemViewHolder(itemView);
        } else if (viewType == 1) {
            itemView = mInflater.inflate(R.layout.explorer_item_folder, parent);
        } else {
            itemView = mInflater.inflate(R.layout.explorer_item_station, parent);
        }

        return new ExplorerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplorerItemViewHolder holder, int position) {
        holder.bind(getContext(), mItems.get(position));
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

    void mapDirectorySnapshot(List<DirectoryEntity> directoryEntityList) {
        List<ExplorerItem> items = new ArrayList<>();
        // TODO: 5/24/2020 Add back folder - need current explorerItem for instantiation
        for (DirectoryEntity entity : directoryEntityList) {
            items.add(ExplorerItem.fromDirectoryEntity(entity));
        }
        setItems(items);
    }
}