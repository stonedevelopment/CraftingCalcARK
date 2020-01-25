/*
 * Copyright (c) 2019 Jared Stone
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

package arc.resource.calculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.Showcase;
import arc.resource.calculator.model.resource.QueueResource;

public class ShowcaseResourceListAdapter extends RecyclerView.Adapter<ShowcaseResourceListAdapter.ViewHolder> {
    private static final String TAG = ShowcaseResourceListAdapter.class.getSimpleName();

    private Showcase mShowcase;

    public ShowcaseResourceListAdapter(Showcase showcase) {
        setShowcase(showcase);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_resource, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.getView().getContext();

        try {
            QueueResource resource = getResource(position);

            String imagePath = "file:///android_asset/" + resource.getImagePath();
            Picasso.with(context).load(imagePath).into(holder.getThumbnail());

            String name = resource.getName();
            holder.getName().setText(name);

            int productOfQuantities = resource.getProductOfQuantities();
            int quantity = resource.getQuantity();

            holder.getQuantity().setText(String.format(Locale.getDefault(), "%1$d (%2$d)", productOfQuantities, quantity));
        } catch (Exception e) {
            ExceptionObservable.getInstance().notifyFatalExceptionCaught(TAG, e);
        }
    }

    @Override
    public int getItemCount() {
        return getShowcase().getShowcaseEntry().getQuantifiableComposition().size();
    }

    private void setShowcase(Showcase showcase) {
        this.mShowcase = showcase;
    }

    private Showcase getShowcase() {
        return mShowcase;
    }

    private QueueResource getResource(int position) throws Exception {
        return getShowcase().getResource(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;

        private final ImageView mThumbnail;
        private final TextView mName;
        private final TextView mQuantity;

        ViewHolder(View view) {
            super(view);

            mView = view;

            mThumbnail = view.findViewById(R.id.resource_list_imageView);
            mName = view.findViewById(R.id.resource_list_nameText);
            mQuantity = view.findViewById(R.id.resource_list_quantityText);
        }

        public View getView() {
            return mView;
        }

        ImageView getThumbnail() {
            return mThumbnail;
        }

        public TextView getName() {
            return mName;
        }

        public TextView getQuantity() {
            return mQuantity;
        }
    }
}
