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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Versioning {
    private final String uuid;
    private final String name;
    private final String filePath;
    private final Date lastUpdate;

    public Versioning(String uuid, String name, String filePath, Date lastUpdate) {
        this.uuid = uuid;
        this.name = name;
        this.filePath = filePath;
        this.lastUpdate = lastUpdate;
    }

    public static Versioning fromJSON(JsonNode node) {
        return new ObjectMapper().convertValue(node, Versioning.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}