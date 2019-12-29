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
import android.content.Intent;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import arc.resource.calculator.R;

import static arc.resource.calculator.util.FeedbackUtil.TYPE.BUG_REPORT;
import static arc.resource.calculator.util.FeedbackUtil.TYPE.CORRECTION;
import static arc.resource.calculator.util.FeedbackUtil.TYPE.FEEDBACK;

/**
 * Utility class to help user send feedback directly from app.
 * <p>
 * Methods: Email, Twitter, Discord
 * <p>
 * To do:
 * First, Email.
 */
public class FeedbackUtil {
    private static String[] emailAddresses = new String[]{
            "stonedevs@gmail.com"
    };

    /**
     * BUG_REPORT   Send bug report.
     * CORRECTION   Send correction, be it typo or incorrect composition.
     * FEEDBACK     Send basic email, solely to send feedback.
     */
    enum TYPE {
        BUG_REPORT, CORRECTION, FEEDBACK
    }

    private static String[] subjectsByType = new String[]{
            "Bug Report", "Correction for %1$s", "User Feedback"
    };

    private static String getSubjectByType(TYPE type) {
        return subjectsByType[type.ordinal()];
    }

    public static void composeFeedbackEmail(Context context) {
        composeEmail(context, FEEDBACK, getSubjectByType(FEEDBACK));
    }

    public static void composeBugReportEmail(Context context) {
        composeEmail(context, BUG_REPORT, getSubjectByType(BUG_REPORT));
    }

    public static void composeCorrectionEmail(Context context, String name) {
        String subject = String.format(getSubjectByType(CORRECTION), name);
        composeEmail(context, CORRECTION, subject);
    }

    private static void composeEmail(Context context, TYPE type, @Nullable String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddresses);

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static AlertDialog Dialog(final Context context, final DialogUtil.Callback callback) {
        final EditText editText = new EditText(context);
        editText.setTextColor(context.getResources().getColor(R.color.colorWhite));

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        builder.setTitle(context.getString(R.string.dialog_feedback_full_title))
                .setIcon(android.R.drawable.ic_menu_send)
                .setView(editText)
                .setNegativeButton(context.getString(R.string.dialog_feedback_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancel();
                    }
                })
                .setPositiveButton(context.getString(R.string.dialog_feedback_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();

                        if (!text.equals(""))
                            callback.onResult(text);
                        else
                            callback.onCancel();
                    }
                });

        return builder.create();
    }
}
