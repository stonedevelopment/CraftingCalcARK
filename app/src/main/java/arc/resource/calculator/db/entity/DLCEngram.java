package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(
    indices = {
        @Index(value = {"dlcId"})},
    inheritSuperIndices = true)

public class DLCEngram extends BaseEngram {

  @NonNull
  private final String dlcId;

  @Ignore
  public DLCEngram(String descriptionId, String imageLocationId, String nameId,
      String parentId, Integer requiredLevel, Integer yield, String dlcId) {
    this(Util.generateUUID(), descriptionId, imageLocationId, nameId, parentId, requiredLevel,
        yield, dlcId);
  }

  public DLCEngram(String id, @NonNull String descriptionId, String imageLocationId, String nameId,
      @NonNull String parentId, Integer requiredLevel, Integer yield, @NonNull String dlcId) {
    super(id, descriptionId, imageLocationId, nameId, parentId, requiredLevel,
        yield);
    this.dlcId = dlcId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }
}
