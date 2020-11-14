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

package arc.resource.calculator.model.ui.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.ui.InteractiveItem;
import arc.resource.calculator.model.ui.InteractiveViewModel;

import static arc.resource.calculator.util.Constants.cAssetsFilePath;
import static arc.resource.calculator.util.Constants.cBackFolderViewType;
import static arc.resource.calculator.util.Constants.cEngramViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class InteractiveItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = InteractiveItemViewHolder.class.getSimpleName();

    private final Picasso picasso;
    private final MaterialCardView cardView;
    private final AppCompatImageView thumbnailImageView;
    private final MaterialTextView viewTypeTextView;
    private final MaterialTextView titleTextView;

    private InteractiveViewModel viewModel;
    private InteractiveItem item;

    public InteractiveItemViewHolder(@NonNull View itemView) {
        super(itemView);
        picasso = Picasso.with(itemView.getContext());
        cardView = itemView.findViewById(R.id.cardView);
        thumbnailImageView = itemView.findViewById(R.id.thumbnail);
        titleTextView = itemView.findViewById(R.id.title);
        viewTypeTextView = itemView.findViewById(R.id.viewType);
    }

    public InteractiveViewModel getViewModel() {
        return viewModel;
    }

    protected void setViewModel(InteractiveViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public InteractiveItem getItem() {
        return item;
    }

    public void setItem(InteractiveItem item) {
        this.item = item;
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public AppCompatImageView getThumbnailImageView() {
        return thumbnailImageView;
    }

    public MaterialTextView getViewTypeTextView() {
        return viewTypeTextView;
    }

    public MaterialTextView getTitleTextView() {
        return titleTextView;
    }

    public void bind(FragmentActivity activity, InteractiveItem item, InteractiveViewModel viewModel) {
        setItem(item);
        setViewModel(viewModel);
        setupViewModel(activity);

        setupViewTypeTextView(activity);
        setupTitleTextView();
        setupCardView();
    }

    protected void setupViewModel(FragmentActivity activity) {
        getViewModel().getGameEntityLiveData().observe(activity, this::handleGameEntityLiveData);
    }

    private void handleGameEntityLiveData(GameEntity gameEntity) {
        String filePath = gameEntity.getFilePath();
        String imagePath = cAssetsFilePath + filePath + getItem().getImageFile();
        loadImage(imagePath);
    }

    protected void loadImage(String imagePath) {
        getPicasso().load(imagePath).into(getThumbnailImageView());
    }

    protected void setupViewTypeTextView(Context context) {
        getViewTypeTextView().setText(getViewTypeText(context, getItem().getViewType()));
    }

    protected void setupTitleTextView() {
        getTitleTextView().setText(getItem().getTitle());
    }

    protected void setupCardView() {
        cardView.setOnClickListener(v -> handleOnClickEvent());
    }

    protected void handleOnClickEvent() {
        //  do nothing
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