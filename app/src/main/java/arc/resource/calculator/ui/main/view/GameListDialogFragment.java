package arc.resource.calculator.ui.main.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.ui.main.MainViewModel;

public class GameListDialogFragment extends DialogFragment {
    List<GameEntity> gameEntityList = new ArrayList<>();

    protected GameListDialogFragment(FragmentActivity activity) {
        MainViewModel mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        mainViewModel.getGameEntityListLiveData().observe(activity, this::handleGameEntityList);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void handleGameEntityList(List<GameEntity> gameEntityList) {
        this.gameEntityList = gameEntityList;
    }
}
