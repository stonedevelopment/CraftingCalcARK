package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(
    indices = {
        @Index(value = {"dlcId"})},
    inheritSuperIndices = true)

public class DLCFolder extends BaseFolder {

  @NonNull
  private final String dlcId;

  @Ignore
  public DLCFolder(String nameId, String parentId, String dlcId) {
    this(Util.generateUUID(), nameId, parentId, dlcId);
  }

  public DLCFolder(@NonNull String id, @NonNull String nameId, @NonNull String parentId,
      @NonNull String dlcId) {
    super(id, nameId, parentId);
    this.dlcId = dlcId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }
}
