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
public class ResourceViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView nameText;
    private TextView quantityText;

    public ResourceViewHolder(View itemView) {
        super(itemView);

        this.imageView = (ImageView) itemView.findViewById(R.id.resource_list_imageView);
        this.nameText = (TextView) itemView.findViewById(R.id.resource_list_nameText);
        this.quantityText = (TextView) itemView.findViewById(R.id.resource_list_quantityText);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getNameText() {
        return nameText;
    }

    public TextView getQuantityText() {
        return quantityText;
    }
}
