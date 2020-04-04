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

package arc.resource.calculator.ui.explorer.folder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.FolderEntity;

public class FolderExplorerAdapter extends ListAdapter<FolderEntity, FolderExplorerViewHolder> {
    private final LayoutInflater mInflater;

    public FolderExplorerAdapter(Context context, @NonNull DiffUtil.ItemCallback<FolderEntity> diffCallback) {
        super(diffCallback);

        mInflater = LayoutInflater.from(context);
    }

    private Context getContext() {
        return mInflater.getContext();
    }

    @NonNull
    @Override
    public FolderExplorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.explorer_item_folder, parent);
        return new FolderExplorerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderExplorerViewHolder holder, int position) {
        holder.bind(getContext(), getItem(position));
    }
}
