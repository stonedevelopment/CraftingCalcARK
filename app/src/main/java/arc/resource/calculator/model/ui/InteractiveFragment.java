package arc.resource.calculator.model.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;

public class InteractiveFragment extends Fragment implements ExceptionObservable.Observer {
    private InteractiveViewModel viewModel;

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupViews();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ContentLoadingProgressBar getProgressBar() {
        return progressBar;
    }

    public InteractiveViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(InteractiveViewModel viewModel) {
        this.viewModel = viewModel;
    }

    protected View setViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);
        return rootView;
    }

    protected void setupViews() {
        //  do nothing
    }

    protected void setupViews(InteractiveAdapter adapter, InteractiveLayoutManager layoutManager) {
        getRecyclerView().setAdapter(adapter);
        getRecyclerView().setLayoutManager(layoutManager);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
    }

    protected void setupViewModel() {
        getViewModel().getSnackBarMessageEvent().observe(getViewLifecycleOwner(), this::showSnackBar);
        getViewModel().getLoadingEvent().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) showLoading();
            else showLoaded();
        });
    }

    private void showLoading() {
        progressBar.show();
    }

    private void showLoaded() {
        progressBar.hide();
    }

    // TODO: 1/27/2020 what do we do with an empty explorer data set?
    private void showEmpty() {
        progressBar.hide();
    }

    protected void showSnackBar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onException(String tag, Exception e) {
        // TODO: 1/25/2020 handle exception
        showSnackBar("An error occurred.");
    }

    @Override
    public void onFatalException(String tag, Exception e) {
        // TODO: 1/25/2020 handle fatal exception
        showSnackBar("A fatal error occurred.");
    }
}