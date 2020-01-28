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
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import arc.resource.calculator.repository.queue.QueueRepository;

public class ClearQueueButton extends AppCompatButton {
    private static final String TAG = ClearQueueButton.class.getSimpleName();

    public ClearQueueButton(Context context) {
        super(context);
    }

    public ClearQueueButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearQueueButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onCreate() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QueueRepository.getInstance().requestToClearQueue();
            }
        });
    }

    public void onResume() {
    }

    public void onPause() {
    }
}
