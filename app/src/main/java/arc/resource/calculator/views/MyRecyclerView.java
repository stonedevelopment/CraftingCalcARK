package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import arc.resource.calculator.R;

public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView( Context context ) {
        super( context );
        onInitialize();
    }

    public MyRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        onInitialize();
    }

    public MyRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        onInitialize();
    }

    private void onInitialize() {
        int numCols = getResources().getInteger( R.integer.display_case_column_count );

        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );
    }

    @Override
    protected void onMeasure( int widthSpec, int heightSpec ) {
        super.onMeasure( widthSpec, heightSpec );

        int colCount = getContext().getResources().getInteger( R.integer.display_case_column_count );
        int rowCount = getContext().getResources().getInteger( R.integer.display_case_row_count );
    }
}
