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

public class DLCStation extends BaseStation {

  @NonNull
  private final String dlcId;

  @Ignore
  public DLCStation(String imageLocationId, String nameId, String dlcId) {
    this(Util.generateUUID(), imageLocationId, nameId, dlcId);
  }

  public DLCStation(@NonNull String id, @NonNull String imageLocationId,
      @NonNull String nameId, @NonNull String dlcId) {
    super(id, imageLocationId, nameId);
    this.dlcId = dlcId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }
}
