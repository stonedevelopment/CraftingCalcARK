package arc.resource.calculator.db.entity.dlc;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import arc.resource.calculator.db.dao.dlc.DlcDirectoryDao;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;

import static arc.resource.calculator.util.Constants.cDlcId;
import static arc.resource.calculator.util.Constants.cGameId;
import static arc.resource.calculator.util.Constants.cImageFile;
import static arc.resource.calculator.util.Constants.cName;
import static arc.resource.calculator.util.Constants.cParentId;
import static arc.resource.calculator.util.Constants.cSourceId;
import static arc.resource.calculator.util.Constants.cUuid;
import static arc.resource.calculator.util.Constants.cViewType;

@Entity(tableName = DlcDirectoryDao.tableName)
public class DlcDirectoryItemEntity extends DirectoryItemEntity {
    @NonNull
    private String dlcId;

    @JsonCreator
    public DlcDirectoryItemEntity(@JsonProperty(cUuid) @NonNull String uuid,
                                  @JsonProperty(cName) @NonNull String name,
                                  @JsonProperty(cImageFile) @NonNull String imageFile,
                                  @JsonProperty(cViewType) int viewType,
                                  @JsonProperty(cParentId) @NonNull String parentId,
                                  @JsonProperty(cSourceId) @NonNull String sourceId,
                                  @JsonProperty(cGameId) @NonNull String gameId,
                                  @JsonProperty(cDlcId) @NonNull String dlcId) {
        super(uuid, name, imageFile, viewType, parentId, sourceId, gameId);
        this.dlcId = dlcId;
    }

    public static DlcDirectoryItemEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcDirectoryItemEntity.class);
    }

    @NonNull
    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(@NonNull String dlcId) {
        this.dlcId = dlcId;
    }
}