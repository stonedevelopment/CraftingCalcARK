/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.util.JSONUtil;
import arc.resource.calculator.util.PrefsUtil;

public class SplashScreenViewModel extends AndroidViewModel {
    private SingleLiveEvent<SplashScreenPhaseType> phaseEvent = new SingleLiveEvent<>();
    private List<SplashScreenPhaseType> phaseTypes = new ArrayList<>(Arrays.asList(SplashScreenPhaseType.values()));
    private int phaseIndex;

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);

        initializePhases();
    }

    SingleLiveEvent<SplashScreenPhaseType> getPhaseEvent() {
        return phaseEvent;
    }

    private void setPhaseEvent(SplashScreenPhaseType viewPhase) {
        getPhaseEvent().setValue(viewPhase);
    }

    int getPhaseIndex() {
        return phaseIndex;
    }

    int getPhaseTotal() {
        return phaseTypes.size();
    }

    private void initializePhases() {
        phaseIndex = 0;
        startPhase();
    }

    private void startPhase() {
        SplashScreenPhaseType phaseType = phaseTypes.get(phaseIndex);
        setPhaseEvent(phaseType);

        switch (phaseType) {
            case CheckVersion:
                checkVersion();
                break;
            case UpdateDatabase:
                break;
            case UpdatePreferences:
                break;
            case Finalize:
                break;
        }
    }

    private void nextPhase() {
        phaseIndex++;
        startPhase();
    }

    private void endPhase() {
        nextPhase();
    }

    private void checkVersion() {
        try {
            String json = JSONUtil.readRawJsonFileToJsonString(getApplication(), "Primary/data.json");
            JSONObject jsonObject = new JSONObject(json);
            String newVersion = jsonObject.getString("version");
            String oldVersion = PrefsUtil.getInstance(getApplication()).getJSONVersion();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
