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

package arc.resource.calculator.ui.queue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.repository.queue.QueueRepository;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> implements QueueObserver {
    private static final String TAG = QueueAdapter.class.getSimpleName();

    private Context mContext;
    private QueueRepository mQueueRepository;

    QueueAdapter(Context context) {
        setContext(context);
        setupRepository();
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    private void setupRepository() {
        mQueueRepository = QueueRepository.getInstance();
    }

    public void resume() {
        registerListeners();
        mQueueRepository.resume(getContext());
    }

    public void pause() {
        mQueueRepository.pause(getContext());
        unregisterListeners();
    }

    private void registerListeners() {
        mQueueRepository.addObserver(TAG, this);
    }

    private void unregisterListeners() {
        mQueueRepository.removeObserver(TAG);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_craftable, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QueueEngram craftable = mQueueRepository.getEngram(position);

        String imagePath = "file:///android_asset/" + craftable.getImagePath();
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(holder.getImageView());

        holder.getNameView().setText(craftable.getName());
        holder.getQuantityView().setText(String.format(Locale.getDefault(), "x%d", craftable.getQuantityWithYield()));

        holder.setPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return mQueueRepository.getEngram(position).getId();
    }

    @Override
    public int getItemCount() {
        return mQueueRepository.getItemCount();
    }

    @Override
    public void onItemChanged(@NonNull QueueEngram engram) {
        int position = mQueueRepository.getItemPosition(engram);

        notifyItemRangeChanged(position, 1);
    }

    @Override
    public void onQueueDataPopulating() {
        //  do nothing
    }

    @Override
    public void onItemAdded(@NonNull QueueEngram engram) {
        int position = mQueueRepository.getItemPosition(engram.getId());

        notifyItemRangeInserted(position, 1);
    }

    @Override
    public void onItemRemoved(@NonNull QueueEngram engram) {
        int position = mQueueRepository.getItemPosition(engram.getId());

        notifyItemRangeRemoved(position, 1);
    }

    @Override
    public void onQueueDataPopulated() {
        notifyDataSetChanged();
    }

    @Override
    public void onQueueDataEmpty() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private final String TAG = ViewHolder.class.getSimpleName();

        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        private int mPosition;

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

        void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            mQueueRepository.increaseQuantity(mPosition);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}