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

import com.fasterxml.jackson.databind.JsonNode;

import static arc.resource.calculator.util.JSONUtil.cFilePath;
import static arc.resource.calculator.util.JSONUtil.cName;
import static arc.resource.calculator.util.JSONUtil.cUuid;
import static arc.resource.calculator.util.JSONUtil.cVersion;

public class DLCVersioning extends Versioning {
    private DLCVersioning(String uuid, String name, String version, String filePath) {
        super(uuid, name, version, filePath);
    }

    public static DLCVersioning fromJSON(JsonNode node) {
        String uuid = node.get(cUuid).asText();
        String name = node.get(cName).asText();
        String version = node.get(cVersion).asText();
        String filePath = node.get(cFilePath).asText();
        return new DLCVersioning(uuid, name, version, filePath);
    }
}
