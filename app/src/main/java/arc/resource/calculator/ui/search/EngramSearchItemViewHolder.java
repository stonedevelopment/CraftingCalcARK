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

package arc.resource.calculator.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.R;
import arc.resource.calculator.ui.explorer.ExplorerViewModel;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;
import arc.resource.calculator.ui.search.model.EngramSearchItem;
import arc.resource.calculator.ui.search.model.SearchItem;

class EngramSearchItemViewHolder extends SearchItemViewHolder {
    public static final String TAG = EngramSearchItemViewHolder.class.getSimpleName();

    private final MaterialTextView quantityView;

    EngramSearchItemViewHolder(@NonNull View itemView, String filePath) {
        super(itemView, filePath);
        quantityView = itemView.findViewById(R.id.quantity);
    }

    void bind(FragmentActivity activity, EngramSearchItem searchItem) {
        super.bind(activity, searchItem);

        quantityView.setText(searchItem.getQuantity());
    }
}
