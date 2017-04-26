package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.util.DisplayUtil;
import arc.resource.calculator.views.QueueRecyclerView;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {
    private static final String TAG = QueueAdapter.class.getSimpleName();

    private Context mContext;

    private static QueueRecyclerView.Observer mViewObserver;

    private boolean mDataSetEmpty;

    private int mViewSize;

    public class Observer extends QueueRecyclerView.Observer {
        @Override
        public void notifyExceptionCaught( Exception e ) {
            mViewObserver.notifyExceptionCaught( e );
        }

        @Override
        public void notifyInitializing() {
            mViewObserver.notifyInitializing();
        }

        @Override
        public void notifyDataSetPopulated() {
            mDataSetEmpty = false;

            notifyDataSetChanged();

            mViewObserver.notifyDataSetPopulated();
        }

        @Override
        public void notifyDataSetEmpty() {
            mDataSetEmpty = true;

            notifyDataSetChanged();

            mViewObserver.notifyDataSetEmpty();
        }

        public void notifyItemChanged( int position ) {
            notifyItemRangeChanged( position, 1 );

            if ( mDataSetEmpty ) {
                mDataSetEmpty = false;
                mViewObserver.notifyDataSetPopulated();
            }
        }

        public void notifyItemInserted( int position ) {
            notifyItemRangeInserted( position, 1 );

            if ( mDataSetEmpty ) {
                mDataSetEmpty = false;
                mViewObserver.notifyDataSetPopulated();
            }
        }

        public void notifyItemRemoved( int position ) {
            notifyItemRangeRemoved( position, 1 );

            if ( getItemCount() == 0 ) {
                mDataSetEmpty = true;
                mViewObserver.notifyDataSetEmpty();
            }
        }
    }

    public QueueAdapter( Context context ) {
        setContext( context );
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext( Context context ) {
        this.mContext = context.getApplicationContext();
    }

    public void create( QueueRecyclerView.Observer observer ) {
        Log.d( TAG, "create()" );

        mViewObserver = observer;
        mViewSize = DisplayUtil.getInstance().getImageSize();

        CraftingQueue.createInstance( getContext(), new Observer() );
    }

    public void resume() {
        Log.d( TAG, "resume()" );
        CraftingQueue.getInstance().resume();
    }

    public void pause() {
        Log.d( TAG, "pause()" );
        CraftingQueue.getInstance().pause();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_queue, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        QueueEngram craftable = CraftingQueue.getInstance().getCraftable( position );

        holder.itemView.getLayoutParams().width = mViewSize;

        String imagePath = "file:///android_asset/" + craftable.getImagePath();
        Picasso.with( getContext() )
                .load( imagePath )
                .error( R.drawable.placeholder_empty )
                .placeholder( R.drawable.placeholder_empty )
                .into( holder.getImageView() );

        holder.getNameView().setText( craftable.getName() );
        holder.getQuantityView().setText( String.format( Locale.getDefault(), "x%d", craftable.getQuantityWithYield() ) );
    }

    @Override
    public int getItemCount() {
        return CraftingQueue.getInstance().getSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private final String TAG = ViewHolder.class.getSimpleName();

        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        ViewHolder( View view ) {
            super( view );

            mImageView = ( ImageView ) view.findViewById( R.id.image_view );
            mNameView = ( TextView ) view.findViewById( R.id.name_view );
            mQuantityView = ( TextView ) view.findViewById( R.id.quantity_view );

            view.setOnClickListener( this );
            view.setOnLongClickListener( this );
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
        public void onClick( View view ) {
            CraftingQueue.getInstance().increaseQuantity( getAdapterPosition() );
        }

        @Override
        public boolean onLongClick( View view ) {
            return false;
        }
    }
}