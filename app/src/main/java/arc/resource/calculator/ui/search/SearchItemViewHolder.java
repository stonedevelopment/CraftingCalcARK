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
import arc.resource.calculator.ui.search.model.SearchItem;

class SearchItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = SearchItemViewHolder.class.getSimpleName();

    private final String filePath;

    private final MaterialCardView cardView;
    private final AppCompatImageView imageView;
    private final MaterialTextView textView;

    SearchItemViewHolder(@NonNull View itemView, String filePath) {
        super(itemView);

        this.filePath = filePath;
        this.cardView = itemView.findViewById(R.id.cardView);
        this.imageView = itemView.findViewById(R.id.thumbnail);
        this.textView = itemView.findViewById(R.id.title);
    }

    void bind(FragmentActivity activity, SearchItem searchItem) {
        final String imagePath = "file:///android_asset/" + filePath + searchItem.getImageFile();

        Context context = activity.getApplicationContext();

        Picasso.with(context)
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(imageView);

        textView.setText(searchItem.getTitle());

        cardView.setOnClickListener(v -> {
            Log.d(TAG, "cardView onClickListener: " + searchItem.getTitle());
            SearchViewModel viewModel = new ViewModelProvider(activity).get(SearchViewModel.class);
            viewModel.handleOnClickEvent(searchItem);
        });
    }
}
