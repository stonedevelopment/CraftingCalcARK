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
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.listeners.NavigationObserver;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.explorer.ExplorerObserver;
import arc.resource.calculator.repository.explorer.ExplorerRepository;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.model.map.CategoryMap;
import arc.resource.calculator.model.map.CraftableMap;
import arc.resource.calculator.model.Station;
import arc.resource.calculator.model.map.StationMap;
import arc.resource.calculator.model.category.BackCategory;
import arc.resource.calculator.model.category.Category;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.repository.queue.QueueRepository;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;

import static android.view.View.VISIBLE;
import static arc.resource.calculator.util.Util.NO_ID;
import static arc.resource.calculator.util.Util.NO_NAME;
import static arc.resource.calculator.util.Util.NO_PATH;
import static arc.resource.calculator.util.Util.NO_QUANTITY;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.ViewHolder> implements ExplorerObserver, QueueObserver {
    private static final String TAG = ExplorerAdapter.class.getSimpleName();

    private Context mContext;
    private ExplorerRepository mExplorerRepository;
    private QueueRepository mQueueRepository;

    @Override
    public void onDataSetPopulated() {
        updateQuantities();
    }

    @Override
    public void onDataSetEmpty() {
        clearQuantities();
    }

    @Override
    public void onItemChanged(long craftableId, int quantity) {
        if (getCraftableMap().contains(craftableId)) {
            int position = getCraftableMap().indexOfKey(craftableId);
            getCraftable(position).setQuantity(quantity);
            notifyItemChanged(adjustPositionFromCraftable(position));
        }
    }

    @Override
    public void onItemRemoved(long craftableId) {
        if (getCraftableMap().contains(craftableId)) {
            int position = getCraftableMap().indexOfKey(craftableId);
            getCraftable(position).resetQuantity();
            notifyItemChanged(adjustPositionFromCraftable(position));
        }
    }

    ExplorerAdapter(Context context) {
        setContext(context);

        setupRepositories();

        PrefsObserver.getInstance().registerListener(TAG, new PrefsObserver.Listener() {
            @Override
            public void onPreferencesChanged(boolean dlcValueChange, boolean categoryPrefChange,
                                             boolean stationPrefChange, boolean levelPrefChange, boolean levelValueChange,
                                             boolean refinedPrefChange) {
                if (dlcValueChange || categoryPrefChange || stationPrefChange || levelPrefChange
                        || levelValueChange) {
                    setCategoryLevelsToRoot();

                    unsetSearchQuery();

                    if (mViewStatus == VISIBLE) {
                        fetchData();
                    } else {
                        mNeedsFullUpdate = true;
                    }
                }
            }
        });

    }

    private void setupRepositories() {
        mExplorerRepository = ExplorerRepository.getInstance();
        mQueueRepository = QueueRepository.getInstance();
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext(Context context) {
        mContext = context;
    }

    public void resume(Context context) {
        registerListeners();
        mExplorerRepository.resume(context);
    }

    public void pause() {
        mExplorerRepository.pause();
        unregisterListeners();
    }

    private void registerListeners() {
        mExplorerRepository.addObserver(TAG, this);
        mQueueRepository.addObserver(TAG, this);
    }

    private void unregisterListeners() {
        mQueueRepository.removeObserver(TAG);
        mExplorerRepository.removeObserver(TAG);
    }

    @Override
    public void onItemAdded(@NonNull QueueEngram engram) {
        if (mExplorerRepository.doesContainCraftable(engram.getId())) {
            mExplorerRepository.updateQuantity(engram.getId(), engram.getQuantity());
            notifyItemChanged(mExplorerRepository.getAdjustedPositionFromCraftable(engram.getId()));
        } else {
            //  do nothing, engram not in list
        }
    }

    @Override
    public void onItemRemoved(@NonNull QueueEngram engram) {
        if (mExplorerRepository.doesContainCraftable(engram.getId())) {
            mExplorerRepository.updateQuantity(engram.getId(), 0);
            notifyItemChanged(mExplorerRepository.getAdjustedPositionFromCraftable(engram.getId()));
        } else {
            //  do nothing, engram not in list
        }
    }

    @Override
    public void onItemChanged(@NonNull QueueEngram engram) {
        if (mExplorerRepository.doesContainCraftable(engram.getId())) {
            mExplorerRepository.updateQuantity(engram.getId(), engram.getQuantity());
            notifyItemChanged(mExplorerRepository.getAdjustedPositionFromCraftable(engram.getId()));
        } else {
            //  do nothing, engram not in list
        }
    }

    @Override
    public void onQueueDataPopulating() {
        //  do nothing
    }

    @Override
    public void onQueueDataPopulated() {
        // TODO: 1/26/2020 Update engram maps and adjust all quantities
        mExplorerRepository.updateQuantities();
    }

    @Override
    public void onQueueDataEmpty() {
        // TODO: 1/26/2020 Update engram maps and clear all quantities
        mExplorerRepository.clearQuantities();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_craftable, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        View view = holder.itemView;

        // TODO: 1/26/2020 Change how Engrams are displayed to make their names easier to read

        final String imagePath = "file:///android_asset/" + getImagePathByPosition(position);
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(holder.getImageView());

        holder.getNameView().setText(getNameByPosition(position));

        if (isCraftable(position)) {
            int quantity = getQuantityWithYieldByPosition(position);

            if (quantity > 0) {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.frame_queue_item));
                holder.getQuantityView().setText(String.format(Locale.getDefault(), "x%d", quantity));
            } else {
                view.setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.frame_craftable_item));
                holder.getQuantityView().setText(null);
            }
        } else {
            view.setBackground(
                    ContextCompat.getDrawable(getContext(), R.drawable.frame_craftable_folder));
            holder.getQuantityView().setText(null);
        }
    }

    @Override
    public long getItemId(int position) {
        if (isStation(position)) {
            return getStation(position).getId();
        }

        if (isCategory(position)) {
            return getCategory(position).getId();
        }

        if (isCraftable(position)) {
            return getCraftable(adjustPositionForCraftable(position)).getId();
        }

        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mExplorerRepository.getItemCount();
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

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        private final String TAG = ViewHolder.class.getSimpleName();

        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        ViewHolder(View view) {
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

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            try {
                if (isCraftable(position)) {
                    increaseQuantity(position);
                } else if (isCategory(position)) {
                    changeCategory(position);
                } else if (isStation(position)) {
                    changeStation(position);
                }
            } catch (ExceptionUtil.CursorEmptyException | ExceptionUtil.CursorNullException e) {
                ExceptionObservable.getInstance().notifyFatalExceptionCaught(TAG, e);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return !isCraftable(getAdapterPosition());
        }
    }

}