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

package arc.resource.calculator.ui.explorer.view;

import android.content.Context;
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

import static arc.resource.calculator.util.Constants.cAssetsFilePath;
import static arc.resource.calculator.util.Constants.cBackFolderViewType;
import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class ExplorerItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ExplorerItemViewHolder.class.getSimpleName();

    private final Picasso picasso;
    private final MaterialCardView cardView;
    private final AppCompatImageView thumbnailImageView;
    private final MaterialTextView viewTypeTextView;
    private final MaterialTextView titleTextView;

    private ExplorerViewModel explorerViewModel;
    private ExplorerItem explorerItem;

    public ExplorerItemViewHolder(@NonNull View itemView) {
        super(itemView);
        picasso = Picasso.with(itemView.getContext());
        cardView = itemView.findViewById(R.id.cardView);
        thumbnailImageView = itemView.findViewById(R.id.thumbnail);
        titleTextView = itemView.findViewById(R.id.title);
        viewTypeTextView = itemView.findViewById(R.id.viewType);
    }

    public ExplorerItem getExplorerItem() {
        return explorerItem;
    }

    protected void setExplorerItem(ExplorerItem explorerItem) {
        this.explorerItem = explorerItem;
    }

    public ExplorerViewModel getExplorerViewModel() {
        return explorerViewModel;
    }

    protected void setExplorerViewModel(ExplorerViewModel explorerViewModel) {
        this.explorerViewModel = explorerViewModel;
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public AppCompatImageView getThumbnailImageView() {
        return thumbnailImageView;
    }

    public MaterialTextView getTitleTextView() {
        return titleTextView;
    }

    public void bind(FragmentActivity activity, ExplorerItem explorerItem) {
        setExplorerItem(explorerItem);
        setExplorerViewModel(new ViewModelProvider(activity).get(ExplorerViewModel.class));
        setupViewModel(activity);

        setupViewTypeTextView(activity);
        setupTitleTextView();
        setupCardView();
    }

    protected void setupViewModel(FragmentActivity activity) {
        getExplorerViewModel().getGameEntityLiveData().observe(activity, gameEntity -> {
            String filePath = gameEntity.getFilePath();
            String imagePath = cAssetsFilePath + filePath + getExplorerItem().getImageFile();
            picasso.load(imagePath).into(thumbnailImageView);
        });
    }

    protected void setupViewTypeTextView(Context context) {
        viewTypeTextView.setText(getViewTypeText(context, getExplorerItem().getViewType()));
    }

    protected void setupTitleTextView() {
        titleTextView.setText(getExplorerItem().getTitle());
    }

    protected void setupCardView() {
        cardView.setOnClickListener(v -> {
            getExplorerViewModel().handleOnClickEvent(getExplorerItem());
        });
    }

    protected String getViewTypeText(Context context, int viewType) {
        switch (viewType) {
            case cEngramViewType:
                return context.getString(R.string.search_item_view_type_text_engram);
            case cFolderViewType:
                return context.getString(R.string.search_item_view_type_text_folder);
            case cBackFolderViewType:
                return "";
            case cStationViewType:
                return context.getString(R.string.search_item_view_type_text_station);
            default:
                return context.getString(R.string.search_item_view_type_text_error);
        }
    }
}