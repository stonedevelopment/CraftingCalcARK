package arc.resource.calculator.model.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.db.entity.DlcEntity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveRepository;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

public class InteractiveGameViewModel extends InteractiveViewModel {
    public static final String TAG = InteractiveGameViewModel.class.getCanonicalName();

    private final MutableLiveData<DlcEntity> dlcEntityLiveData = new MutableLiveData<>();

    private InteractiveRepository repository;
    private GameEntity gameEntity;
    private DlcEntity dlcEntity;

    public InteractiveGameViewModel(@NonNull Application application) {
        super(application);
        setupRepository(application);
    }

    protected void setupRepository(Application application) {
        repository = new InteractiveRepository(application);
    }

    @Override
    public void observe(FragmentActivity activity) {
        super.observe(activity);
        String dlcId = getPrefs().getDlcIdPreference();
        if (dlcId == null) {
            Log.d(TAG, "observe: dlcId is null.");
            //  do nothing, until the user chooses a dlc
        } else {
            Log.d(TAG, "observe: dlcId is saved! " + dlcId);
            //  trigger dlc load event observations
            fetchDlcEntity(dlcId).observe(activity, this::setDlcEntityLiveData);
        }
    }

    protected InteractiveRepository getRepository() {
        return repository;
    }

    protected void setRepository(InteractiveRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<DlcEntity> getDlcEntityLiveData() {
        return dlcEntityLiveData;
    }

    private void setDlcEntityLiveData(DlcEntity dlcEntity) {
        dlcEntityLiveData.setValue(dlcEntity);
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

    private LiveData<DlcEntity> fetchDlcEntity(String dlcId) {
        return getRepository().getDlcEntity(dlcId);
    }

    public void saveDlcEntity(DlcEntity gameEntity) {
        getPrefs().saveDlcIdPreference(gameEntity.getUuid());
    }
}
