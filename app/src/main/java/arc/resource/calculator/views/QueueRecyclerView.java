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

package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Objects;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.QueueAdapter;

public class QueueRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = QueueRecyclerView.class.getSimpleName();

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            // TODO: 4/18/2017 When one is increasing quantity, have it scroll to position, but if its just updating due to data fetching
            scrollToPosition(1);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            Log.d(TAG, "onItemRangeChanged: " + positionStart + ", " + itemCount);
            scrollToPosition(positionStart);
        }
    };

    private static Listener mListener;

    public interface Listener {
        void onError(Exception e);

        void onInit();

        void onPopulated();

        void onEmpty();
    }

    public static class Observer {
        public void notifyExceptionCaught(Exception e) {
            getListener().onError(e);
        }

        public void notifyInitializing() {
            getListener().onInit();
        }

        public void notifyDataSetPopulated() {
            getListener().onPopulated();
        }

        public void notifyDataSetEmpty() {
            getListener().onEmpty();
        }
    }

    public QueueRecyclerView(Context context) {
        super(context);
    }

    public QueueRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QueueRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private static Listener getListener() {
        return mListener;
    }

    private void setListener(Listener listener) {
        mListener = listener;
    }

    public void onCreate(Listener listener) {
        int numCols = getResources().getInteger(R.integer.gridview_column_count);
        setLayoutManager(new GridLayoutManager(getContext(), numCols));
        setListener(listener);
        setAdapter(new QueueAdapter(getContext(), new Observer()));
    }

    public void onResume() {
        Objects.requireNonNull(getAdapter()).registerAdapterDataObserver(mDataObserver);
        getAdapter().resume();
    }

    public void onPause() {
        Objects.requireNonNull(getAdapter()).unregisterAdapterDataObserver(mDataObserver);
        getAdapter().pause();
    }

    public void onDestroy() {
        Objects.requireNonNull(getAdapter()).destroy();
    }

    @Override
    public QueueAdapter getAdapter() {
        return (QueueAdapter) super.getAdapter();
    }
}