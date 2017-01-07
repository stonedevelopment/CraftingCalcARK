package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.util.ExceptionUtil;
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
public class CraftableResourceListAdapter extends RecyclerView.Adapter<ResourceViewHolder> {
    private static final String TAG = CraftableResourceListAdapter.class.getSimpleName();

    private CraftingQueue mCraftingQueue;
    private Context mContext;

    public CraftableResourceListAdapter( Context context ) throws Exception {
        mContext = context;

        mCraftingQueue = CraftingQueue.getInstance( context );
    }

    @Override
    public ResourceViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_resource, parent, false );

        return new ResourceViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ResourceViewHolder holder, int position ) {
        try {
            int imageId = getContext().getResources().getIdentifier(
                    mCraftingQueue.getResource( position ).getDrawable(),
                    "drawable",
                    getContext().getPackageName() );
            String name = mCraftingQueue.getResource( position ).getName();
            int quantity = mCraftingQueue.getResource( position ).getQuantity();

            holder.getThumbnail().setImageResource( imageId );
            holder.getName().setText( name );
            holder.getQuantity().setText( String.format( Locale.US, "%1$d", quantity ) );
        } catch ( Exception e ) {
            ExceptionUtil.SendErrorReportWithAlertDialog( getContext(), TAG, e );
        }
    }

    @Override
    public int getItemCount() {
        return mCraftingQueue.getResourceItemCount();
    }

    private Context getContext() {
        return mContext;
    }

    public void NotifyDataChanged() {
        notifyDataSetChanged();
    }

    public void RefreshData() throws Exception {
        mCraftingQueue.UpdateResources(getContext());
    }
}
