package arc.resource.calculator.ui.filter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.DlcEntity;

public class FilterContentPreferenceDlcItemAdapter extends ArrayAdapter<DlcEntity> {
    public FilterContentPreferenceDlcItemAdapter(@NonNull Context context) {
        super(context, R.layout.item_title_layout, new ArrayList<>());
    }

    public void setItems(List<DlcEntity> dlcEntities) {
        clear();
        addAll(dlcEntities);
    }
}
