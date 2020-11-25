package arc.resource.calculator.ui.filter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.DlcEntity;

public class FilterContentPreferenceItemAdapter extends ArrayAdapter<DlcEntity> {
    private final FilterDialogViewModel viewModel;
    private List<DlcEntity> itemList = new ArrayList<>();

    public FilterContentPreferenceItemAdapter(@NonNull Context context, int resource, FilterDialogViewModel viewModel) {
        super(context, resource);
        this.viewModel = viewModel;

        setupViewModel();
    }

    private void setupViewModel() {
    }


    @Nullable
    @Override
    public DlcEntity getItem(int position) {
        return super.getItem(position);
    }
}
