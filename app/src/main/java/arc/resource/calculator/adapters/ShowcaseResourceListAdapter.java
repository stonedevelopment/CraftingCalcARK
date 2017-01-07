package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.resource.QueueResource;
import arc.resource.calculator.viewholders.ResourceViewHolder;

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
public class ShowcaseResourceListAdapter extends RecyclerView.Adapter {
    private static final String TAG = ShowcaseResourceListAdapter.class.getSimpleName();

    private SparseArray<QueueResource> mComposition;
    private Context mContext;

    public ShowcaseResourceListAdapter( Context context, SparseArray<QueueResource> composition ) {
        this.mContext = context;
        this.mComposition = composition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_resource, parent, false );

        return new ResourceViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {
        ResourceViewHolder viewHolder = ( ResourceViewHolder ) holder;

        QueueResource resource = getComposition().valueAt( position );

        int imageId = getContext().getResources().getIdentifier( resource.getDrawable(), "drawable", getContext().getPackageName() );
        String name = resource.getName();
        int quantity = resource.getProductOfQuantities();

        viewHolder.getThumbnail().setImageResource( imageId );
        viewHolder.getName().setText( name );
        viewHolder.getQuantity().setText( String.format( Locale.US, "%1$d", quantity ) );
    }

    @Override
    public int getItemCount() {
        return mComposition.size();
    }

    public SparseArray<QueueResource> getComposition() {
        return mComposition;
    }

    public void setResources( SparseArray<QueueResource> resources ) {
        this.mComposition = resources;
    }

    public Context getContext() {
        return mContext;
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
