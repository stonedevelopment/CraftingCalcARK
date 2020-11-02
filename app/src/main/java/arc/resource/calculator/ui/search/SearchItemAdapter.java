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
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.ui.search.model.SearchItem;

import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cResourceViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {
    private final LayoutInflater layoutInflater;
    private final FragmentActivity fragmentActivity;
    private final GameEntity gameEntity;
    private final List<SearchItem> itemList;

    SearchItemAdapter(SearchFragment fragment, GameEntity gameEntity) {
        super();
        this.layoutInflater = LayoutInflater.from(fragment.getContext());
        this.fragmentActivity = fragment.requireActivity();
        this.itemList = new ArrayList<>();
        this.gameEntity = gameEntity;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType) {
            case cResourceViewType:
                itemView = layoutInflater.inflate(R.layout.search_item_resource, parent, false);
                break;
            case cEngramViewType:
                itemView = layoutInflater.inflate(R.layout.search_item_engram, parent, false);
                break;
            case cFolderViewType:
                itemView = layoutInflater.inflate(R.layout.search_item_folder, parent, false);
                break;
            case cStationViewType:
                itemView = layoutInflater.inflate(R.layout.search_item_station, parent, false);
                break;
            default:
                itemView = layoutInflater.inflate(R.layout.search_item_error, parent, false);
        }

        return new SearchItemViewHolder(itemView, gameEntity.getFilePath());
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        holder.bind(fragmentActivity, itemList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private SearchItem getItem(int position) {
        return itemList.get(position);
    }

    public void setItems(List<SearchItem> searchItems) {
        itemList.clear();
        itemList.addAll(searchItems);
        notifyDataSetChanged();
    }
}