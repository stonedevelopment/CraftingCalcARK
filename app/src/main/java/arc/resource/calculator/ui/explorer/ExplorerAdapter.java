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

package arc.resource.calculator.ui.explorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.repository.explorer.ExplorerObserver;
import arc.resource.calculator.repository.explorer.ExplorerRepository;
import arc.resource.calculator.ui.detail.DetailFragment;
import arc.resource.calculator.util.ExceptionUtil;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.ViewHolder> implements ExplorerObserver {
    private static final String TAG = ExplorerAdapter.class.getSimpleName();

    private Context mContext;
    private ExplorerRepository mExplorerRepository;
    private ExplorerViewModel mExplorerViewModel;

    ExplorerAdapter(Context context) {
        setContext(context);

        setupViewModel(context);

        setupRepositories();
    }

    private void setupViewModel(Context context) {
        mExplorerViewModel = ViewModelProviders.of((FragmentActivity) context).get(ExplorerViewModel.class);
    }

    private void setupRepositories() {
        mExplorerRepository = ExplorerRepository.getInstance();
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext(Context context) {
        mContext = context;
    }

    public void resume(Context context) {
        registerListeners();
        mExplorerRepository.resume(context);
    }

    public void pause() {
        mExplorerRepository.pause();
        unregisterListeners();
    }

    private void registerListeners() {
        mExplorerRepository.addObserver(TAG, this);
    }

    private void unregisterListeners() {
        mExplorerRepository.removeObserver(TAG);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_craftable, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        View view = holder.itemView;

        // TODO: 1/26/2020 Change how Engrams are displayed to make their names easier to read

        final String imagePath = "file:///android_asset/" + mExplorerRepository.getImagePathByPosition(position);
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(holder.getImageView());

        holder.getNameView().setText(mExplorerRepository.getNameByPosition(position));

        if (mExplorerRepository.isCraftable(position)) {
            int quantity = mExplorerRepository.getQuantityWithYieldByPosition(position);

            if (quantity > 0) {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.frame_queue_item));
                holder.getQuantityView().setText(String.format(Locale.getDefault(), "x%d", quantity));
            } else {
                view.setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.frame_craftable_item));
                holder.getQuantityView().setText(null);
            }
        } else {
            view.setBackground(
                    ContextCompat.getDrawable(getContext(), R.drawable.frame_craftable_folder));
            holder.getQuantityView().setText(null);
        }
    }

    @Override
    public long getItemId(int position) {
        if (mExplorerRepository.isStation(position)) {
            return mExplorerRepository.getStation(position).getId();
        }

        if (mExplorerRepository.isCategory(position)) {
            return mExplorerRepository.getCategory(position).getId();
        }

        if (mExplorerRepository.isCraftable(position)) {
            return mExplorerRepository.getCraftableByGlobalPosition(position).getId();
        }

        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mExplorerRepository.getItemCount();
    }

    @Override
    public void onEngramUpdated(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void onExplorerDataPopulating() {
        //  do nothing
    }

    @Override
    public void onExplorerDataPopulated() {
        notifyDataSetChanged();
    }

    @Override
    public void onExplorerDataEmpty() {
        // TODO: 1/26/2020 what to do with an empty explorer repository?
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        private final String TAG = ViewHolder.class.getSimpleName();

        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        ViewHolder(View view) {
            super(view);

            mImageView = view.findViewById(R.id.image_view);
            mNameView = view.findViewById(R.id.name_view);
            mQuantityView = view.findViewById(R.id.quantity_view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        ImageView getImageView() {
            return mImageView;
        }

        TextView getNameView() {
            return mNameView;
        }

        TextView getQuantityView() {
            return mQuantityView;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            try {
                if (mExplorerRepository.isCraftable(position)) {
                    mExplorerViewModel.setDialogFragment(new DetailFragment());
                } else if (mExplorerRepository.isCategory(position)) {
                    mExplorerRepository.changeCategory(position);
                } else if (mExplorerRepository.isStation(position)) {
                    mExplorerRepository.changeStation(position);
                }
            } catch (ExceptionUtil.CursorEmptyException | ExceptionUtil.CursorNullException e) {
                ExceptionObservable.getInstance().notifyFatalExceptionCaught(TAG, e);
            }

        }

        @Override
        public boolean onLongClick(View view) {
            return !mExplorerRepository.isCraftable(getAdapterPosition());
        }
    }

}