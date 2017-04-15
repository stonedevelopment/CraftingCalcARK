package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.Util;

public class RecyclerViewWithContextMenu extends RecyclerView {
    private RecyclerContextMenuInfo mContextMenuInfo;

    public RecyclerViewWithContextMenu( Context context ) {
        super( context );
    }

    public RecyclerViewWithContextMenu( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public RecyclerViewWithContextMenu( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }

    @Override
    public boolean showContextMenuForChild( View originalView ) {
        int position = getChildAdapterPosition( originalView );

        if ( Util.isValidPosition( position, getAdapter().getItemCount() ) ) {
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