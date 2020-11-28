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

package arc.resource.calculator.model.ui.interactive;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.R;

import static arc.resource.calculator.util.Constants.cAssetsFilePath;

public class InteractiveItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = InteractiveItemViewHolder.class.getSimpleName();

    private final Picasso picasso;
    private final MaterialCardView cardView;
    private final AppCompatImageView thumbnailImageView;
    private final MaterialTextView titleTextView;
    private final MaterialTextView descriptionTextView;

    private InteractiveViewModel viewModel;
    private InteractiveItem item;
    private String imagePath;

    public InteractiveItemViewHolder(@NonNull View itemView) {
        super(itemView);
        picasso = Picasso.with(itemView.getContext());
        cardView = itemView.findViewById(R.id.cardView);
        thumbnailImageView = itemView.findViewById(R.id.thumbnail);
        titleTextView = itemView.findViewById(R.id.title);
        descriptionTextView = itemView.findViewById(R.id.description);
    }

    public InteractiveViewModel getViewModel() {
        return viewModel;
    }

    private void setViewModel(InteractiveViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public InteractiveItem getItem() {
        return item;
    }

    private void setItem(InteractiveItem item) {
        this.item = item;
    }

    protected void setImagePath(String imagePath) {
        this.imagePath = cAssetsFilePath.concat(imagePath);
    }

    protected void loadImage(String imageFile) {
        picasso.load(imagePath.concat(imageFile))
                .into(thumbnailImageView);
    }

    public void setTitleText(String title) {
        titleTextView.setText(title);
    }

    protected void setDescriptionText(String description) {
        descriptionTextView.setText(description);
    }

    public void bind(FragmentActivity activity, InteractiveItem item, InteractiveViewModel viewModel) {
        setItem(item);
        setViewModel(viewModel);

        setupViewModel(activity);
        setupTitleTextView();
        setupCardView();
    }

    protected void setupViewModel(FragmentActivity activity) {
        //  do nothing
    }

    protected void setupTitleTextView() {
        setTitleText(getItem().getTitle());
    }

    protected void setupCardView() {
        cardView.setOnClickListener(v -> handleOnClickEvent());
    }

    protected void handleOnClickEvent() {
        //  do nothing
    }
}