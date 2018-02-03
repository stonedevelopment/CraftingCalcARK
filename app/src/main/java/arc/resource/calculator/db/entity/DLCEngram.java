package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseDLC.class,
            parentColumns = "id",
            childColumns = "dlcId")},
    indices = {
        @Index(value = {"dlcId"})},
    inheritSuperIndices = true)

public class DLCEngram extends PrimaryEngram {

  @NonNull
  private final String dlcId;

  public DLCEngram(String engramId, String parentId, Integer yield, @NonNull String dlcId) {
    super(engramId, parentId, yield);
    this.dlcId = dlcId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }
}
