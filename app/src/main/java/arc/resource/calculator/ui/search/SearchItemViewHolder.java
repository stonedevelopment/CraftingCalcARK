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

import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cResourceViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

class SearchItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = SearchItemViewHolder.class.getSimpleName();

    private final String filePath;

    private final MaterialCardView cardView;
    private final AppCompatImageView imageView;
    private final MaterialTextView viewTypeView;
    private final MaterialTextView titleView;
    private final MaterialTextView descriptionView;

    SearchItemViewHolder(@NonNull View itemView, String filePath) {
        super(itemView);

        this.filePath = filePath;
        this.cardView = itemView.findViewById(R.id.cardView);
        this.imageView = itemView.findViewById(R.id.thumbnail);
        this.viewTypeView = itemView.findViewById(R.id.viewType);
        this.titleView = itemView.findViewById(R.id.title);
        this.descriptionView = itemView.findViewById(R.id.description);
    }

    void bind(FragmentActivity activity, SearchItem searchItem) {
        final String imagePath = "file:///android_asset/" + filePath + searchItem.getImageFile();

        Context context = activity.getApplicationContext();

        Picasso.with(context)
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(imageView);

        viewTypeView.setText(getViewTypeText(context, searchItem.getViewType()));
        titleView.setText(searchItem.getTitle());
        descriptionView.setText(searchItem.getDescription());

        cardView.setOnClickListener(v -> {
            Log.d(TAG, "cardView onClickListener: " + searchItem.getTitle());
            SearchViewModel viewModel = new ViewModelProvider(activity).get(SearchViewModel.class);
            viewModel.handleOnClickEvent(searchItem);
        });
    }

    public MaterialTextView getViewTypeView() {
        return viewTypeView;
    }

    public MaterialTextView getDescriptionView() {
        return descriptionView;
    }

    private String getViewTypeText(Context context, int viewType) {
        switch (viewType) {
            case cStationViewType:
                return context.getString(R.string.search_item_view_type_text_station);
            case cEngramViewType:
                return context.getString(R.string.search_item_view_type_text_engram);
            case cFolderViewType:
                return context.getString(R.string.search_item_view_type_text_folder);
            case cResourceViewType:
                return context.getString(R.string.search_item_view_type_text_resource);
            default:
                return context.getString(R.string.search_item_view_type_text_error);
        }
    }
}
