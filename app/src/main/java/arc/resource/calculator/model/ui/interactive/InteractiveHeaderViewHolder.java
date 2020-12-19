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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import arc.resource.calculator.R;

public class InteractiveHeaderViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = InteractiveHeaderViewHolder.class.getSimpleName();

    private final MaterialCardView cardView;
    private final MaterialTextView titleTextView;

    private InteractiveViewModel viewModel;
    private InteractiveItem item;

    public InteractiveHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        titleTextView = itemView.findViewById(R.id.title);
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

    public void setTitleText(String title) {
        titleTextView.setText(title);
    }

    public void bind(FragmentActivity activity, InteractiveItem item, InteractiveViewModel viewModel) {
        setItem(item);
        setViewModel(viewModel);

        setupViewModel(activity);
        setupTitleTextView();
        setupCardView();
    }

    protected void setupViewModel(FragmentActivity activity) {
        //  I do nothing
    }

    protected void setupTitleTextView() {
        setTitleText(getItem().getTitle());
    }

    protected void setupCardView() {
        cardView.setOnClickListener(v -> handleOnClickEvent());
    }

    protected void handleOnClickEvent() {
        //  I do nothing
    }
}