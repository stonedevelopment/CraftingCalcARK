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

import org.json.JSONException;
import org.json.JSONObject;

import static arc.resource.calculator.util.JSONUtil.cFilePath;
import static arc.resource.calculator.util.JSONUtil.cVersion;

public class PrimaryVersioning extends Versioning {
    public PrimaryVersioning(String version, String filePath) {
        super(version, filePath);
    }

    public static PrimaryVersioning fromJSON(JSONObject jsonObject) throws JSONException {
        String version = jsonObject.getString(cVersion);
        String filePath = jsonObject.getString(cFilePath);
        return new PrimaryVersioning(version, filePath);
    }
}
