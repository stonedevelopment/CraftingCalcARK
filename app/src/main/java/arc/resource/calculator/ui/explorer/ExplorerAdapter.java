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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.repository.explorer.ExplorerObserver;

import static androidx.core.content.ContextCompat.getDrawable;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.ExplorerItemViewHolder> implements ExplorerObserver {
    private static final String TAG = ExplorerAdapter.class.getSimpleName();

    private List<ExplorerItem> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    ExplorerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    Context getContext() {
        return mInflater.getContext();
    }

    void setItems(List<ExplorerItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExplorerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: 3/28/2020 refactor names of list item layouts, see issue #35
        View itemView = mInflater.inflate(R.layout.list_item_craftable, parent, false);
        return new ExplorerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ExplorerItemViewHolder holder, int position) {
        ExplorerItem explorerItem = mItems.get(position);
        holder.bindView(explorerItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onEngramUpdated(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void onExplorerDataPopulating() {
        //  do nothing
    }

    @Override
    public void onExplorerDataPopulated() {
        notifyDataSetChanged();
    }

    @Override
    public void onExplorerDataEmpty() {
        // TODO: 1/26/2020 what to do with an empty explorer repository?
    }

    class ExplorerItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private final String TAG = ExplorerItemViewHolder.class.getSimpleName();
        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        private ExplorerItem mExplorerItem;

        ExplorerItemViewHolder(View view) {
            super(view);

            mImageView = view.findViewById(R.id.image_view);
            mNameView = view.findViewById(R.id.name_view);
            mQuantityView = view.findViewById(R.id.quantity_view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        ImageView getImageView() {
            return mImageView;
        }

        TextView getNameView() {
            return mNameView;
        }

        TextView getQuantityView() {
            return mQuantityView;
        }

        void bindView(ExplorerItem explorerItem) {
            mExplorerItem = explorerItem;

            final String imagePath = "file:///android_asset/" + explorerItem.getImage();
            Picasso.with(getContext())
                    .load(imagePath)
                    .error(R.drawable.placeholder_empty)
                    .placeholder(R.drawable.placeholder_empty)
                    .into(getImageView());

            getNameView().setText(explorerItem.getTitle());

            if (explorerItem.getItemType() == ExplorerItemType.Engram) {
                int quantity = explorerItem.getQuantity();

                if (quantity > 0) {
                    itemView.setBackground(getDrawable(getContext(), R.drawable.frame_queue_item));
                    getQuantityView().setText(String.format(Locale.getDefault(), "x%d", quantity));
                } else {
                    itemView.setBackground(getDrawable(getContext(), R.drawable.frame_craftable_item));
                    getQuantityView().setText(null);
                }
            } else {
                itemView.setBackground(getDrawable(getContext(), R.drawable.frame_craftable_folder));
                getQuantityView().setText(null);
            }
        }

        @Override
        public void onClick(View view) {
            ExplorerViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).
                    get(ExplorerViewModel.class);
            viewModel.onExplorerItemClick(getAdapterPosition(), mExplorerItem);
        }

        @Override
        public boolean onLongClick(View view) {
//            return mExplorerViewModel.handleViewHolderLongClick(getAdapterPosition());
            return true;
        }
    }

}