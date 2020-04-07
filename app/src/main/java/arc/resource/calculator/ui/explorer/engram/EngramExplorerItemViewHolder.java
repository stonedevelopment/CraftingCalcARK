/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.ui.explorer.engram;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.ui.explorer.ExplorerViewModel;

public class EngramExplorerItemViewHolder extends RecyclerView.ViewHolder {
    private final MaterialCardView mCardView;
    private final AppCompatImageView mThumbnail;
    private final MaterialTextView mTitle;
    private final MaterialTextView mQuantity;

    public EngramExplorerItemViewHolder(@NonNull View itemView) {
        super(itemView);

        mCardView = itemView.findViewById(R.id.cardView);
        mThumbnail = itemView.findViewById(R.id.thumbnail);
        mTitle = itemView.findViewById(R.id.title);
        mQuantity = itemView.findViewById(R.id.quantity);
    }

    void bind(Context context, EngramEntity engramEntity) {
        final String imagePath = "file:///android_asset/" + engramEntity.getImage();

        Picasso.with(context)
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(mThumbnail);

        mTitle.setText(engramEntity.getName());

        mCardView.setOnClickListener(v -> {
            ExplorerViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ExplorerViewModel.class);
            viewModel.selectEngram(engramEntity);
        });
    }
}
