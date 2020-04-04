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

package arc.resource.calculator.ui.explorer.station;

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
import arc.resource.calculator.db.entity.StationEntity;

public class StationExplorerAdapter extends ListAdapter<StationEntity, StationExplorerAdapter.StationExplorerViewHolder> {
    private static final DiffUtil.ItemCallback<StationEntity> cDiffCallback = new DiffUtil.ItemCallback<StationEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull StationEntity oldItem, @NonNull StationEntity newItem) {
            return oldItem.getRowId() == newItem.getRowId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StationEntity oldItem, @NonNull StationEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final LayoutInflater mInflater;

    public StationExplorerAdapter(Context context) {
        super(cDiffCallback);

        mInflater = LayoutInflater.from(context);
    }

    private Context getContext() {
        return mInflater.getContext();
    }

    @NonNull
    @Override
    public StationExplorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.explorer_item_station, parent);
        return new StationExplorerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StationExplorerViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    class StationExplorerViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView mThumbnail;
        private final MaterialTextView mTitle;

        StationExplorerViewHolder(@NonNull View itemView) {
            super(itemView);

            mThumbnail = itemView.findViewById(R.id.thumbnail);
            mTitle = itemView.findViewById(R.id.title);
        }

        void bindTo(StationEntity stationEntity) {
            final String imagePath = "file:///android_asset/" + stationEntity.getImage();

            Picasso.with(getContext())
                    .load(imagePath)
                    .error(R.drawable.placeholder_empty)
                    .placeholder(R.drawable.placeholder_empty)
                    .into(mThumbnail);

            mTitle.setText(stationEntity.getName());
        }
    }
}
