package arc.resource.calculator.db.entity.dlc;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import arc.resource.calculator.db.dao.dlc.DlcDirectoryDao;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;

@Entity(tableName = DlcDirectoryDao.tableName)
public class DlcDirectoryItemEntity extends DirectoryItemEntity {
    private String dlcId;

    public DlcDirectoryItemEntity(@NonNull String uuid,
                                  @NonNull String name,
                                  @NonNull String imageFile,
                                  int viewType,
                                  @NonNull String parentId,
                                  @NonNull String sourceId,
                                  @NonNull String gameId,
                                  @NonNull String dlcId) {
        super(uuid, name, imageFile, viewType, parentId, sourceId, gameId);
        this.dlcId = dlcId;
    }

    public static DlcDirectoryItemEntity fromJson(JsonNode node) {
        return new ObjectMapper().convertValue(node, DlcDirectoryItemEntity.class);
    }

    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(String dlcId) {
        this.dlcId = dlcId;
    }
}