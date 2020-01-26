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

package arc.resource.calculator.tasks.fetch;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class FetchDataTask extends AsyncTask<Void, Void, Boolean> {
    private final String TAG = FetchDataTask.class.getSimpleName();

    private WeakReference<Context> mContext;
    private FetchDataTaskObservable mDataTaskObservable;

    public FetchDataTask(Context context, FetchDataTaskObserver observer) {
        mContext = new WeakReference<>(context);
        mDataTaskObservable = new FetchDataTaskObservable(observer);
    }

    protected Context getContext() {
        return mContext.get();
    }

    public FetchDataTaskObservable getObservable() {
        return mDataTaskObservable;
    }

    @Override
    protected void onPreExecute() {
        mDataTaskObservable.notifyPreFetch();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        mDataTaskObservable.notifyFetching();
        return true;
    }

    @Override
    protected void onPostExecute(Boolean querySuccessful) {
        if (querySuccessful) {
            mDataTaskObservable.notifyFetchSuccess();
        } else {
            mDataTaskObservable.notifyFetchFail();
        }
    }
}