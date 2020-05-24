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

package arc.resource.calculator.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import arc.resource.calculator.BuildConfig;
import arc.resource.calculator.R;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.queue.QueueRepository;

public class DialogUtil {
    static AlertDialog Error(Context c, final Callback cb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(c, R.style.AlertDialogCustom));
        builder.setTitle(c.getString(R.string.dialog_error_title))
                .setIcon(R.drawable.dialog_icons_error)
                .setMessage(c.getString(R.string.dialog_error_message))
                .setPositiveButton(c.getString(R.string.dialog_error_button_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cb.onOk();
                    }
                })
                .setNegativeButton(c.getString(R.string.dialog_error_button_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cb.onCancel(null);
                    }
                });

        return builder.create();
    }

    public static AlertDialog EditQuantity(final Context context, long engramId, final Callback callback) {
        final EditText editText = new EditText(context);
        editText.setTextColor(context.getResources().getColor(R.color.colorWhite));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        final QueueEngram engram = QueueRepository.getInstance().getEngram(engramId);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        builder.setTitle(engram.getName())
                .setIcon(android.R.drawable.ic_menu_edit)
                .setMessage(context.getString(R.string.dialog_edit_quantity_message))
                .setView(editText)
                .setNegativeButton(context.getString(R.string.dialog_edit_quantity_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancel(engram);
                    }
                })
                .setPositiveButton(context.getString(R.string.dialog_edit_quantity_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String quantityText = editText.getText().toString();

                        if (!quantityText.equals("") && quantityText.length() <= 6) {
                            int quantity = Integer.parseInt(quantityText);

                            if (quantity > 0)
                                callback.onResult(engram);
                            else
                                callback.onCancel(engram);
                        } else {
                            callback.onCancel(engram);
                        }
                    }
                });

        return builder.create();
    }

    public static AlertDialog Search(final Context context, final Callback callback) {
        final EditText editText = new EditText(context);
        editText.setTextColor(context.getResources().getColor(R.color.colorWhite));

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        builder.setTitle(context.getString(R.string.search_dialog_full_title))
                .setIcon(android.R.drawable.ic_menu_search)
                .setView(editText)
                .setNegativeButton(context.getString(R.string.search_dialog_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancel(null);
                    }
                })
                .setPositiveButton(context.getString(R.string.search_dialog_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String searchText = editText.getText().toString();

                        callback.onResult(searchText);
                    }
                });

        return builder.create();
    }

    public static AlertDialog About(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        builder.setTitle(context.getString(R.string.dialog_about_full_title))
                .setIcon(R.drawable.dialog_icons_about)
                .setMessage(
                        "Passionately developed by Shane Stone.\n\n" +
                                "Email:\n  stonedevs@gmail.com\n\n" +
                                "Twitter:\n  @MasterxOfxNone\n  @ARKResourceCalc\n\n" +
                                "Steam:\n  MasterxOfxNone\n" +
                                "Xbox Live:\n  MasterxOfxNone\n\n" +
                                "App Version: " + BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE + "\n" +
                                "Screen Size: " + context.getString(R.string.dimens));

        return builder.create();
    }

    public abstract static class Callback {
        public void onResult(@Nullable Object result) {
            // do nothing
        }

        public void onOk() {
            // do nothing
        }

        public void onCancel(@Nullable Object obj) {
            // do nothing
        }
    }
}