package arc.resource.calculator.db.entity.dlc;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.dlc.DlcStationDao;
import arc.resource.calculator.db.entity.primary.StationEntity;

import static arc.resource.calculator.util.Constants.cDlcId;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cLastUpdated;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;

@Entity(tableName = DlcStationDao.tableName)
public class DlcStationEntity extends StationEntity {
    @NonNull
    private String dlcId;

    @JsonCreator
    public DlcStationEntity(@JsonProperty(cUuid) @NonNull String uuid,
                            @JsonProperty(cName) @NonNull String name,
                            @JsonProperty(cImageFile) @NonNull String imageFile,
                            @JsonProperty(cSourceId) @NonNull String sourceId,
                            @JsonProperty(cLastUpdated) @NonNull Date lastUpdated,
                            @JsonProperty(cGameId) @NonNull String gameId,
                            @JsonProperty(cDlcId) @NonNull String dlcId) {
        super(uuid, name, imageFile, sourceId, lastUpdated, gameId);
        this.dlcId = dlcId;
    }

    public static DlcStationEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcStationEntity.class);
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}
