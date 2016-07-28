package com.gmail.jaredstone1982.craftingcalcark.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.jaredstone1982.craftingcalcark.R;

public class ResourceViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView nameText;
    private TextView quantityText;

    public ResourceViewHolder(View itemView) {
        super(itemView);

        this.image = (ImageView) itemView.findViewById(R.id.resource_list_imageView);
        this.nameText = (TextView) itemView.findViewById(R.id.resource_list_nameText);
        this.quantityText = (TextView) itemView.findViewById(R.id.resource_list_quantityText);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getNameText() {
        return nameText;
    }

    public TextView getQuantityText() {
        return quantityText;
    }
}
