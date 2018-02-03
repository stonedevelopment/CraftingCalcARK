package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "dlc",
    inheritSuperIndices = true)

public class BaseDLC extends BaseGame {

  @Ignore
  public BaseDLC(String contentFolder, String imageLocationId, String nameId) {
    this(Util.generateUUID(), contentFolder, imageLocationId, nameId);
  }

  public BaseDLC(@NonNull String id, @NonNull String contentFolder, @NonNull String imageLocationId,
      @NonNull String nameId) {
    super(id, contentFolder, imageLocationId, nameId);
  }
}
