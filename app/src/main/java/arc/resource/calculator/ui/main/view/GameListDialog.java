package arc.resource.calculator.ui.main.view;

import android.app.AlertDialog;
import android.content.Context;

public class GameListDialog extends AlertDialog {
    protected GameListDialog(Context context) {
        super(context);
    }

    protected GameListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected GameListDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
