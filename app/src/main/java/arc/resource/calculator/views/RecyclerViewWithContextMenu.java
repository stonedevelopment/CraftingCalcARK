package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

import arc.resource.calculator.model.RecyclerContextMenuInfo;

public class RecyclerViewWithContextMenu extends RecyclerView {
    private RecyclerContextMenuInfo mContextMenuInfo;

    public RecyclerViewWithContextMenu( Context context ) throws Exception {
        super( context );
    }

    public RecyclerViewWithContextMenu( Context context, @Nullable AttributeSet attrs ) throws Exception {
        super( context, attrs );
    }

    public RecyclerViewWithContextMenu( Context context, @Nullable AttributeSet attrs, int defStyle ) throws Exception {
        super( context, attrs, defStyle );
    }

    @Override
    public boolean showContextMenuForChild( View originalView ) {
        int position = getChildAdapterPosition( originalView );

        if ( position != NO_POSITION ) {
            mContextMenuInfo = new RecyclerContextMenuInfo( originalView,
                    position, getAdapter().getItemId( position ) );

            return super.showContextMenuForChild( originalView );
        }

        return false;
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }
}