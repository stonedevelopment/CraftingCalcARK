package arc.resource.calculator.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import arc.resource.calculator.R;

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
public class EngramGridViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mQuantityTextView;

    public EngramGridViewHolder( View itemView ) {
        super( itemView );

        mImageView = ( ImageView ) itemView.findViewById( R.id.list_item_engram_thumbnail_image_view );
        mNameTextView = ( TextView ) itemView.findViewById( R.id.list_item_engram_thumbnail_name_text_view );
        mQuantityTextView = ( TextView ) itemView.findViewById( R.id.list_item_engram_thumbnail_quantity_text_view );
    }

    public ImageView getImage() {
        return mImageView;
    }

    public TextView getNameText() {
        return mNameTextView;
    }

    public TextView getQuantityText() {
        return mQuantityTextView;
    }
}