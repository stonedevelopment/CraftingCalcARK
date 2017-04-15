package arc.resource.calculator.listeners;

public interface MainActivityListener {
    void onCategoryHierarchyResolved( String hierarchicalCategoryTree );

    void onRequestLayoutUpdate(boolean updateNeeded);
}