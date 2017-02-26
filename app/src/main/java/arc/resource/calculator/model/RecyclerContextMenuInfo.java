package arc.resource.calculator.model;

import android.view.ContextMenu;
import android.view.View;

public class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {
    final private int mPosition;
    final private long mId;
    final private View mView;

    public RecyclerContextMenuInfo( View view, int position, long id ) {
        mView = view;
        mPosition = position;
        mId = id;
    }

    public View getView() {
        return mView;
    }

    public int getPosition() {
        return mPosition;
    }

    public long getId() {
        return mId;
    }
}