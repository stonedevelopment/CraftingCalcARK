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

public class DlcVersioning extends Versioning {
    @JsonCreator
    public DlcVersioning(@JsonProperty(cUuid) String uuid,
                         @JsonProperty(cName) String name,
                         @JsonProperty(cFilePath) String filePath,
                         @JsonProperty(cLastUpdated) Date lastUpdated) {
        super(uuid, name, filePath, lastUpdated);
    }

    public static DlcVersioning fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcVersioning.class);
    }
}