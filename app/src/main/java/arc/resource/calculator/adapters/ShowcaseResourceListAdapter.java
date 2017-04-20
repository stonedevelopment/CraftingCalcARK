package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.model.Showcase;
import arc.resource.calculator.model.resource.QueueResource;

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
public class ShowcaseResourceListAdapter extends RecyclerView.Adapter<ShowcaseResourceListAdapter.ViewHolder> {
    private static final String TAG = ShowcaseResourceListAdapter.class.getSimpleName();

    private Showcase mShowcase;

    public ShowcaseResourceListAdapter( Showcase showcase ) {
        setShowcase( showcase );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_resource, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        Context context = holder.getView().getContext();

        try {
            QueueResource resource = getResource( position );

            String imagePath = "file:///android_asset/" + resource.getImagePath();
            Picasso.with( context ).load( imagePath ).into( holder.getThumbnail() );

            String name = resource.getName();
            holder.getName().setText( name );

            int quantity = resource.getProductOfQuantities();
            holder.getQuantity().setText( String.format( Locale.US, "%1$d", quantity ) );
        } catch ( Exception e ) {
            ExceptionObserver.getInstance().notifyFatalExceptionCaught( TAG, e );
        }
    }

    @Override
    public int getItemCount() {
        return getShowcase().getShowcaseEntry().getQuantifiableComposition().size();
    }

    private void setShowcase( Showcase showcase ) {
        this.mShowcase = showcase;
    }

    private Showcase getShowcase() {
        return mShowcase;
    }

    private QueueResource getResource( int position ) throws Exception {
        return getShowcase().getResource( position );
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;

        private final ImageView mThumbnail;
        private final TextView mName;
        private final TextView mQuantity;

        public ViewHolder( View view ) {
            super( view );

            mView = view;

            mThumbnail = ( ImageView ) view.findViewById( R.id.resource_list_imageView );
            mName = ( TextView ) view.findViewById( R.id.resource_list_nameText );
            mQuantity = ( TextView ) view.findViewById( R.id.resource_list_quantityText );
        }

        public View getView() {
            return mView;
        }

        public ImageView getThumbnail() {
            return mThumbnail;
        }

        public TextView getName() {
            return mName;
        }

        public TextView getQuantity() {
            return mQuantity;
        }
    }
}
