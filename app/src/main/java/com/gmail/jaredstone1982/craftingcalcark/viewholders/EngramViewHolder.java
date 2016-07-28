package com.gmail.jaredstone1982.craftingcalcark.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.jaredstone1982.craftingcalcark.R;

public class EngramViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView nameText;
    private TextView quantityText;

    public EngramViewHolder(View itemView) {
        super(itemView);

        this.image = (ImageView) itemView.findViewById(R.id.list_item_engram_imageView);
        this.nameText = (TextView) itemView.findViewById(R.id.list_item_engram_nameText);
        this.quantityText = (TextView) itemView.findViewById(R.id.list_item_engram_quantityText);
//        this.imageButton = (ImageButton) itemView.findViewById(R.id.list_item_engram_remove_imageButton);
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