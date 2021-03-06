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
import arc.resource.calculator.adapters.CraftableAdapter;

public class CraftableRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = CraftableRecyclerView.class.getSimpleName();

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

    interface Listener {
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

    public CraftableRecyclerView(Context context) {
        super(context);
    }

    public CraftableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CraftableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private static Listener getListener() {
        return mListener;
    }

    private void setListener(Listener listener) {
        mListener = listener;
    }

    public void create(Listener listener) {
        int numCols = getResources().getInteger(R.integer.gridview_column_count);
        setLayoutManager(new GridLayoutManager(getContext(), numCols));
        setListener(listener);
        setAdapter(new CraftableAdapter(getContext(), new Observer()));
    }

    public void resume() {
        Objects.requireNonNull(getAdapter()).registerAdapterDataObserver(mDataObserver);
        getAdapter().resume();
    }

    public void pause() {
        Objects.requireNonNull(getAdapter()).unregisterAdapterDataObserver(mDataObserver);
        getAdapter().pause();
    }

    public void destroy() {
        Objects.requireNonNull(getAdapter()).destroy();
    }

    public void search(String query) {
        Objects.requireNonNull(getAdapter()).searchData(query);
    }

    @Override
    public CraftableAdapter getAdapter() {
        return (CraftableAdapter) super.getAdapter();
    }
}