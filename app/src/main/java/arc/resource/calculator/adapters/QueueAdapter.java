/*
 * Copyright (c) 2019 Jared Stone
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

package arc.resource.calculator.adapters;

import android.content.Context;
import android.util.Log;
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
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.views.QueueRecyclerView;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {
    private static final String TAG = QueueAdapter.class.getSimpleName();

    private Context mContext;
    //    private CraftingQueue mCraftingQueue;
    private QueueRecyclerView.Observer mViewObserver;
    private boolean mDataSetEmpty;

    public class Observer extends QueueRecyclerView.Observer {
        @Override
        public void notifyExceptionCaught(Exception e) {
            if (getObserver() != null)
                getObserver().notifyExceptionCaught(e);
        }

        @Override
        public void notifyInitializing() {
            if (getObserver() != null)
                getObserver().notifyInitializing();
        }

        @Override
        public void notifyDataSetPopulated() {
            mDataSetEmpty = false;

            notifyDataSetChanged();

            if (getObserver() != null)
                getObserver().notifyDataSetPopulated();
        }

        @Override
        public void notifyDataSetEmpty() {
            mDataSetEmpty = true;

            notifyDataSetChanged();

            if (getObserver() != null)
                getObserver().notifyDataSetEmpty();
        }

        public void notifyItemChanged(int position) {
            notifyItemRangeChanged(position, 1);

            if (mDataSetEmpty) {
                mDataSetEmpty = false;
                if (getObserver() != null)
                    getObserver().notifyDataSetPopulated();
            }
        }

        public void notifyItemInserted(int position) {
            notifyItemRangeInserted(position, 1);

            if (mDataSetEmpty) {
                mDataSetEmpty = false;
                if (getObserver() != null)
                    getObserver().notifyDataSetPopulated();
            }
        }

        public void notifyItemRemoved(int position) {
            notifyItemRangeRemoved(position, 1);

            if (getItemCount() == 0) {
                mDataSetEmpty = true;

                if (getObserver() != null)
                    getObserver().notifyDataSetEmpty();
            }
        }
    }

    public QueueAdapter(Context context, QueueRecyclerView.Observer observer) {
        Log.d(TAG, "QueueAdapter: ");
        setContext(context);
        setObserver(observer);

//        setCraftingQueue( CraftingQueue.getInstance() );
        getCraftingQueue().setObserver(new Observer());
        getCraftingQueue().fetch(context);
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private CraftingQueue getCraftingQueue() {
        return CraftingQueue.getInstance();
    }

//    private void setCraftingQueue( CraftingQueue craftingQueue ) {
//        this.mCraftingQueue = craftingQueue;
//    }

    private QueueRecyclerView.Observer getObserver() {
        return mViewObserver;
    }

    private void setObserver(QueueRecyclerView.Observer observer) {
        this.mViewObserver = observer;
    }

    public void resume() {
        getCraftingQueue().resume();
    }

    public void pause() {
        getCraftingQueue().pause(getContext());
    }

    public void destroy() {
        getCraftingQueue().destroy();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_queue, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QueueEngram craftable = getCraftingQueue().getCraftable(position);

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
        return getCraftingQueue().getCraftable(position).getId();
    }

    @Override
    public int getItemCount() {
        return getCraftingQueue().getSize();
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
            getCraftingQueue().increaseQuantity(mPosition);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}