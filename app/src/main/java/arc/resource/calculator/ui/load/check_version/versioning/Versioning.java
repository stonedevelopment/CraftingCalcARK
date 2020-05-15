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

package arc.resource.calculator.ui.load.check_version.versioning;

public class Versioning {
    private final String version;
    private final String filePath;

    public Versioning(String version, String filePath) {
        this.version = version;
        this.filePath = filePath;
    }

    public String getVersion() {
        return version;
    }

    public String getFilePath() {
        return filePath;
    }
}