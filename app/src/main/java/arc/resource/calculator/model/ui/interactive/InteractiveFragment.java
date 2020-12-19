package arc.resource.calculator.model.ui.interactive;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.ui.main.MainViewModel;

public class InteractiveFragment extends Fragment implements ExceptionObservable.Observer {
    public static final String TAG = InteractiveFragment.class.getCanonicalName();

    private InteractiveViewModel viewModel;
    private MainViewModel mainViewModel;

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar loadingProgressBar;
    private MaterialTextView loadingTextView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupViews();
        observeViewModel();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ContentLoadingProgressBar getLoadingProgressBar() {
        return loadingProgressBar;
    }

    public InteractiveViewModel getViewModel() {
        return viewModel;
    }

    protected void setViewModel(InteractiveViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    protected View setViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        loadingProgressBar = rootView.findViewById(R.id.loadingProgressBar);
        loadingTextView = rootView.findViewById(R.id.loadingTextView);
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
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    protected void observeViewModel() {
        getViewModel().getSnackBarMessageEvent().observe(getViewLifecycleOwner(), this::showSnackBar);
        getViewModel().getLoadingEvent().observe(getViewLifecycleOwner(), this::handleLoadingEvent);
    }

    protected void startViewModel() {
        getViewModel().start();
    }

    protected void handleLoadingEvent(InteractiveLoadState loadState) {
        switch (loadState) {
            case Loading:
                showLoading();
                break;
            case Loaded:
                showLoaded();
                break;
            case Error:
                showError();
                break;
        }
    }

    protected void showLoading() {
        loadingProgressBar.show();
        loadingTextView.setVisibility(View.VISIBLE);
    }

    protected void showLoaded() {
        loadingProgressBar.hide();
        loadingTextView.setVisibility(View.INVISIBLE);
    }

    protected void showError() {
        // TODO: 11/29/2020 handle load state error
        Log.d(TAG, "handleLoadingEvent: Error");
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