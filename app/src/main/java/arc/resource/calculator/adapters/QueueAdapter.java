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
import arc.resource.calculator.model.exception.PositionOutOfBoundsException;
import arc.resource.calculator.views.switchers.listeners.Observer;

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
    //    private CraftingQueue mCraftingQueue;
    private Observer mViewObserver;
    private boolean mDataSetEmpty;

    public class QueueAdapterObserver implements Observer {
        @Override
        public void notifyExceptionCaught( Exception e ) {
            if ( getObserver() != null )
                getObserver().notifyExceptionCaught( e );
        }

        @Override
        public void notifyInitializing() {
            if ( getObserver() != null )
                getObserver().notifyInitializing();
        }

        @Override
        public void notifyDataSetPopulated() {
            mDataSetEmpty = false;

            notifyDataSetChanged();

            if ( getObserver() != null )
                getObserver().notifyDataSetPopulated();
        }

        @Override
        public void notifyDataSetEmpty() {
            mDataSetEmpty = true;

            notifyDataSetChanged();

            if ( getObserver() != null )
                getObserver().notifyDataSetEmpty();
        }

        public void notifyItemChanged( int position ) {
            notifyItemRangeChanged( position, 1 );

            if ( mDataSetEmpty ) {
                mDataSetEmpty = false;
                if ( getObserver() != null )
                    getObserver().notifyDataSetPopulated();
            }
        }

        public void notifyItemInserted( int position ) {
            notifyItemRangeInserted( position, 1 );

            if ( mDataSetEmpty ) {
                mDataSetEmpty = false;
                if ( getObserver() != null )
                    getObserver().notifyDataSetPopulated();
            }
        }

        public void notifyItemRemoved( int position ) {
            notifyItemRangeRemoved( position, 1 );

            if ( getItemCount() == 0 ) {
                mDataSetEmpty = true;

                if ( getObserver() != null )
                    getObserver().notifyDataSetEmpty();
            }
        }
    }

    public QueueAdapter( Context context, Observer observer ) {
        setContext( context );
        setObserver( observer );

        getCraftingQueue().setObserver( new QueueAdapterObserver() );
        getCraftingQueue().fetch( context );
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext( Context context ) {
        this.mContext = context.getApplicationContext();
    }

    private CraftingQueue getCraftingQueue() {
        return CraftingQueue.getInstance();
    }

    private Observer getObserver() {
        return mViewObserver;
    }

    private void setObserver( Observer observer ) {
        this.mViewObserver = observer;
    }

    public void resume() {
        getCraftingQueue().resume();
    }

    public void pause() {
        getCraftingQueue().pause( getContext() );
    }

    public void destroy() {
        getCraftingQueue().destroy();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_craftable_queue, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        try {
            QueueEngram craftable = getCraftingQueue().getCraftable( position );

            String imagePath = "file:///android_asset/" + craftable.getImagePath();
            Picasso.with( getContext() )
                    .load( imagePath )
                    .error( R.drawable.placeholder_empty )
                    .placeholder( R.drawable.placeholder_empty )
                    .into( holder.getImageView() );

            holder.getNameView().setText( craftable.getName() );
            holder.getQuantityView().setText( String.format( Locale.getDefault(), "x%d", craftable.getQuantityWithYield() ) );

            holder.setPosition( position );
        } catch ( PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }
    }

    @Override
    public long getItemId( int position ) {
        try {
            return getCraftingQueue().getCraftable( position ).getId();
        } catch ( PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }

        return super.getItemId( position );
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

        void setPosition( int position ) {
            mPosition = position;
        }

        @Override
        public void onClick( View view ) {
            try {
                getCraftingQueue().increaseQuantity( mPosition );
            } catch ( PositionOutOfBoundsException e ) {
                getObserver().notifyExceptionCaught( e );
            }
        }

        @Override
        public boolean onLongClick( View view ) {
            return false;
        }
    }
}