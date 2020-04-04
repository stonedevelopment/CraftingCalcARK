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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.FolderEntity;

public class FolderExplorerAdapter extends ListAdapter<FolderEntity, FolderExplorerAdapter.FolderExplorerViewHolder> {
    private static final DiffUtil.ItemCallback<FolderEntity> cDiffCallback = new DiffUtil.ItemCallback<FolderEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull FolderEntity oldItem, @NonNull FolderEntity newItem) {
            return oldItem.getRowId() == newItem.getRowId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FolderEntity oldItem, @NonNull FolderEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final LayoutInflater mInflater;

    public FolderExplorerAdapter(Context context) {
        super(cDiffCallback);

        mInflater = LayoutInflater.from(context);
    }

    Context getContext() {
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
        holder.bindTo(getItem(position));
    }

    class FolderExplorerViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView mThumbnail;
        private final MaterialTextView mTitle;

        FolderExplorerViewHolder(@NonNull View itemView) {
            super(itemView);

            mThumbnail = itemView.findViewById(R.id.thumbnail);
            mTitle = itemView.findViewById(R.id.title);
        }

        void bindTo(FolderEntity folderEntity) {
            final String imagePath = "file:///android_asset/" + folderEntity.getImage();

            Picasso.with(getContext())
                    .load(imagePath)
                    .error(R.drawable.placeholder_empty)
                    .placeholder(R.drawable.placeholder_empty)
                    .into(mThumbnail);

            mTitle.setText(folderEntity.getName());
        }
    }
}
