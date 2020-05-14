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

package arc.resource.calculator.ui.load.versioning;

public interface CheckVersionListener {
    void onError(Exception e);

    void onInit();

    void onCheckPrimaryVersion();

    void onNewPrimaryVersion(String oldVersion, String newVersion);

    void onCheckDLCVersion(String dlcName);

    void onNewDLCVersion(String dlcName, String oldVersion, String newVersion);

    void onFinish();
}