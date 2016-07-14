package com.gmail.jaredstone1982.craftingcalcark.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.jaredstone1982.craftingcalcark.R;

// TODO: 7/13/2016 Convert all Engram named objects to DisplayCase or CraftingQueue
public class EngramViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView nameText;
    private TextView quantityText;

    public EngramViewHolder(View itemView) {
        super(itemView);

        this.image = (ImageView) itemView.findViewById(R.id.list_engram_overlay_imageView);
        this.nameText = (TextView) itemView.findViewById(R.id.list_engram_overlay_nameText);
        this.quantityText = (TextView) itemView.findViewById(R.id.list_engram_overlay_quantityText);
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getNameText() {
        return nameText;
    }

    public TextView getQuantityText() {
        return quantityText;
    }
}