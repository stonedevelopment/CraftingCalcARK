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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import static arc.resource.calculator.util.Constants.cFilePath;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cUuid;

public class Versioning {
    private final String uuid;
    private final String name;
    private final String filePath;
    private final Date lastUpdated;

    @JsonCreator
    public Versioning(@JsonProperty(cUuid) String uuid,
                      @JsonProperty(cName) String name,
                      @JsonProperty(cFilePath) String filePath,
                      @JsonProperty(cLastUpdated) Date lastUpdated) {
        this.uuid = uuid;
        this.name = name;
        this.filePath = filePath;
        this.lastUpdated = lastUpdated;
    }

    public static Versioning fromJson(JsonNode node) {
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

    public Date getLastUpdated() {
        return lastUpdated;
    }
}