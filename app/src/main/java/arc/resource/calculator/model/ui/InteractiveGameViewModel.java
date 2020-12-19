package arc.resource.calculator.model.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import arc.resource.calculator.db.entity.DlcEntity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

public class InteractiveGameViewModel extends InteractiveViewModel {
    public static final String TAG = InteractiveGameViewModel.class.getCanonicalName();

    private GameEntity gameEntity;
    private DlcEntity dlcEntity;

    public InteractiveGameViewModel(@NonNull Application application) {
        super(application);
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

    public String getGameEntityId() {
        return getGameEntity().getUuid();
    }

    public DlcEntity getDlcEntity() {
        return dlcEntity;
    }

    public void setDlcEntity(DlcEntity dlcEntity) {
        this.dlcEntity = dlcEntity;
    }

    public String getDlcEntityId() {
        return getDlcEntity().getUuid();
    }
}
