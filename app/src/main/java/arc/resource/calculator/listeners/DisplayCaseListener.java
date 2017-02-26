package arc.resource.calculator.listeners;

import android.content.Context;

public interface DisplayCaseListener {
    void onRequestResetCategoryLevels( Context context );

    void onRequestSaveCategoryLevels( Context context );

    void onRequestCategoryHierarchy( Context context );

    void onItemChanged( int position );

    void onRequestDisplayCaseDataSetChange( Context context );

    void onDisplayCaseDataSetChanged( Context context );
}