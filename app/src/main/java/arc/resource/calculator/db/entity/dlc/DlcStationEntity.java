package arc.resource.calculator.db.entity.dlc;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import arc.resource.calculator.db.dao.dlc.DlcStationDao;
import arc.resource.calculator.db.entity.primary.StationEntity;

@Entity(tableName = DlcStationDao.tableName)
public class DlcStationEntity extends StationEntity {
    @NonNull
    private String dlcId;

    public DlcStationEntity(@NonNull String uuid,
                            @NonNull String name,
                            @NonNull String imageFile,
                            @NonNull String sourceId,
                            @NonNull Date lastUpdated,
                            @NonNull String gameId,
                            @NonNull String dlcId) {
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
