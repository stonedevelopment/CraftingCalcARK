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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.ui.explorer.ExplorerFragment;
import arc.resource.calculator.ui.search.model.SearchItem;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {
    private final LayoutInflater layoutInflater;
    private final FragmentActivity fragmentActivity;
    private String filePath;
    private List<SearchItem> itemlist;

    SearchItemAdapter(SearchFragment fragment, String filePath) {
        super();
        this.layoutInflater = LayoutInflater.from(fragment.getContext());
        this.fragmentActivity = fragment.requireActivity();
        this.itemlist = new ArrayList<>();
        this.filePath = filePath;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == 2) {
            itemView = layoutInflater.inflate(R.layout.explorer_item_engram, parent, false);
            return new EngramSearchItemViewHolder(itemView, filePath);
        } else if (viewType == 1) {
            itemView = layoutInflater.inflate(R.layout.explorer_item_folder, parent, false);
        } else if (viewType == 0) {
            itemView = layoutInflater.inflate(R.layout.explorer_item_station, parent, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.explorer_item_folder, parent, false);
        }

        return new SearchItemViewHolder(itemView, filePath);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        holder.bind(fragmentActivity, itemlist.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    private SearchItem getItem(int position) {
        return itemlist.get(position);
    }

    private void setItems(List<SearchItem> itemList) {
        this.itemlist = itemList;
        notifyDataSetChanged();
    }

    void mapSearchResults(List<EngramEntity> engramEntityList) {
        List<SearchItem> items = new ArrayList<>();
        for (EngramEntity entity : engramEntityList) {
            items.add(SearchItem.fromEngramEntity(entity));
        }
        setItems(items);
    }
}